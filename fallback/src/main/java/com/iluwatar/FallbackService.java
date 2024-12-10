/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar;

import java.time.Duration;
import java.time.Instant;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FallbackService implements a resilient service pattern with circuit breaking,
 * rate limiting, and fallback capabilities. It manages service degradation gracefully
 * by monitoring service health and automatically switching to fallback mechanisms
 * when the primary service is unavailable or performing poorly.
 *
 * <p>Features:
 * - Circuit breaking to prevent cascading failures.
 * - Rate limiting to protect from overload.
 * - Automatic fallback to backup service.
 * - Health monitoring and metrics collection.
 * - Retry mechanism with exponential backoff.
 */
public class FallbackService implements Service, AutoCloseable {
  
  /** Logger for this class. */
  private static final Logger LOGGER = LoggerFactory.getLogger(FallbackService.class);
  
  /** Timeout in seconds for primary service calls. */
  private static final int TIMEOUT = 2;
  
  /** Maximum number of retry attempts for failed requests. */
  private static final int MAX_RETRIES = 3;
  
  /** Base delay between retries in milliseconds. */
  private static final long RETRY_DELAY_MS = 1000;
  
  /** Minimum success rate threshold before triggering warnings. */
  private static final double MIN_SUCCESS_RATE = 0.6;
  
  /** Maximum requests allowed per minute for rate limiting. */
  private static final int MAX_REQUESTS_PER_MINUTE = 60;

  /** Service state tracking. */
  private enum ServiceState {
    STARTING, RUNNING, DEGRADED, CLOSED
  }

  private volatile ServiceState state = ServiceState.STARTING;
  
  private final CircuitBreaker circuitBreaker;
  private final ExecutorService executor;
  private final Service primaryService;
  private final Service fallbackService;
  private final ServiceMonitor monitor;
  private final ScheduledExecutorService healthChecker;
  private final RateLimiter rateLimiter;

  /**
   * Constructs a new FallbackService with the specified components.
   *
   * @param primaryService Main service implementation
   * @param fallbackService Backup service for failover
   * @param circuitBreaker Circuit breaker for failure detection
   * @throws IllegalArgumentException if any parameter is null
   */
  public FallbackService(Service primaryService, Service fallbackService, CircuitBreaker circuitBreaker) {
    // Validate parameters
    if (primaryService == null || fallbackService == null || circuitBreaker == null) {
      throw new IllegalArgumentException("All service components must be non-null");
    }

    // Initialize components
    this.primaryService = primaryService;
    this.fallbackService = fallbackService;
    this.circuitBreaker = circuitBreaker;
    this.executor = Executors.newCachedThreadPool();
    this.monitor = new ServiceMonitor();
    this.healthChecker = Executors.newSingleThreadScheduledExecutor();
    this.rateLimiter = new RateLimiter(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1));
    
    startHealthChecker();
    state = ServiceState.RUNNING;
  }

  /**
   * Starts the health monitoring schedule.
   * Monitors service health metrics and logs warnings when thresholds are exceeded.
   */
  private void startHealthChecker() {
    healthChecker.scheduleAtFixedRate(() -> {
      try {
        if (monitor.getSuccessRate() < MIN_SUCCESS_RATE) {
          LOGGER.warn("Success rate below threshold: {}", monitor.getSuccessRate());
        }
        if (Duration.between(monitor.getLastSuccessTime(), Instant.now()).toMinutes() > 5) {
          LOGGER.warn("No successful requests in last 5 minutes");
        }
      } catch (Exception e) {
        LOGGER.error("Health check failed: {}", e.getMessage());
      }
    }, 1, 1, TimeUnit.MINUTES);
  }

  /**
   * Retrieves data from the primary service with a fallback mechanism.
   *
   * @return the data from the primary or fallback service
   * @throws Exception if an error occurs while retrieving the data
   */
  @Override
  public String getData() throws Exception {
    // Validate service state
    if (state == ServiceState.CLOSED) {
      throw new ServiceException("Service is closed");
    }

    // Apply rate limiting
    if (!rateLimiter.tryAcquire()) {
      state = ServiceState.DEGRADED;
      LOGGER.warn("Rate limit exceeded, switching to fallback");
      monitor.recordFallback();
      return executeFallback();
    }

    // Check circuit breaker
    if (!circuitBreaker.allowRequest()) {
      state = ServiceState.DEGRADED;
      LOGGER.warn("Circuit breaker open, switching to fallback");
      monitor.recordFallback();
      return executeFallback();
    }

    Instant start = Instant.now();
    Exception lastException = null;

    for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
      try {
        if (attempt > 0) {
          // Exponential backoff with jitter
          long delay = RETRY_DELAY_MS * (long) Math.pow(2, attempt - 1);
          delay += ThreadLocalRandom.current().nextLong(delay / 2);
          Thread.sleep(delay);
        }

        String result = executeWithTimeout(primaryService::getData);
        Duration responseTime = Duration.between(start, Instant.now());
        
        circuitBreaker.recordSuccess();
        monitor.recordSuccess(responseTime);
        updateFallbackCache(result);
        
        return result;
      } catch (Exception e) {
        lastException = e;
        LOGGER.warn("Attempt {} failed: {}", attempt + 1, e.getMessage());
        
        // Don't retry certain exceptions
        if (e instanceof ServiceException 
            || e instanceof IllegalArgumentException) {
          break;
        }

        circuitBreaker.recordFailure();
        monitor.recordError();
      }
    }
    
    monitor.recordFallback();
    if (lastException != null) {
      LOGGER.error("All attempts failed. Last error: {}", lastException.getMessage());
    }
    return executeFallback();
  }

  /**
   * Executes a service call with timeout protection.
   * @param task The service call to execute
   * @return The service response
   * @throws Exception if the call fails or times out
   */
  private String executeWithTimeout(Callable<String> task) throws Exception {
    Future<String> future = executor.submit(task);
    try {
      return future.get(TIMEOUT, TimeUnit.SECONDS);
    } catch (TimeoutException e) {
      future.cancel(true);
      throw new ServiceException("Service timeout after " + TIMEOUT + " seconds", e);
    }
  }

  private String executeFallback() {
    try {
      return fallbackService.getData();
    } catch (Exception e) {
      LOGGER.error("Fallback service failed: {}", e.getMessage());
      return "Service temporarily unavailable";
    }
  }

  private void updateFallbackCache(String result) {
    try {
      if (fallbackService instanceof LocalCacheService) {
        ((LocalCacheService) fallbackService).updateCache("default", result);
      }
    } catch (Exception e) {
      LOGGER.warn("Failed to update fallback cache: {}", e.getMessage());
    }
  }

  /**
   * Shuts down the executor service.
   */
  @Override
  public void close() throws Exception {
    state = ServiceState.CLOSED;
    executor.shutdown();
    healthChecker.shutdown();
    try {
      if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
        executor.shutdownNow();
      }
      if (!healthChecker.awaitTermination(5, TimeUnit.SECONDS)) {
        healthChecker.shutdownNow();
      }
    } catch (InterruptedException e) {
      executor.shutdownNow();
      healthChecker.shutdownNow();
      Thread.currentThread().interrupt();
      throw new Exception("Failed to shutdown executors", e);
    }
  }
  
  public ServiceMonitor getMonitor() {
    return monitor;
  }

  /**
   * Returns the current service state.
   * @return Current ServiceState enum value
   */
  public ServiceState getState() {
    return state;
  }

  /**
   * Rate limiter implementation using a sliding window approach.
   * Manages request rate by tracking timestamps within a rolling time window.
   */
  private static class RateLimiter {
    private final int maxRequests;
    private final Duration window;
    private final Queue<Long> requestTimestamps = new ConcurrentLinkedQueue<>();

    RateLimiter(int maxRequests, Duration window) {
      this.maxRequests = maxRequests;
      this.window = window;
    }

    boolean tryAcquire() {
      long now = System.currentTimeMillis();
      long windowStart = now - window.toMillis();
      
      // Removing expired timestamps
      while (!requestTimestamps.isEmpty() && requestTimestamps.peek() < windowStart) {
        requestTimestamps.poll();
      }
      
      if (requestTimestamps.size() < maxRequests) {
        requestTimestamps.offer(now);
        return true;
      }
      return false;
    }
  }
}