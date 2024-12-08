/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

/**
 * FallbackService class that implements the fallback pattern.
 */
public class FallbackService implements Service, AutoCloseable {
  private static final Logger LOGGER = Logger.getLogger(FallbackService.class.getName());
  private static final int TIMEOUT = 2;
  private static final int MAX_RETRIES = 3;
  private static final long RETRY_DELAY_MS = 1000;
  private static final double MIN_SUCCESS_RATE = 0.6;
  private static final int MAX_REQUESTS_PER_MINUTE = 60;
  
  private final CircuitBreaker circuitBreaker;
  private final ExecutorService executor;
  private final Service primaryService;
  private final Service fallbackService;
  private final ServiceMonitor monitor;
  private final ScheduledExecutorService healthChecker;
  private final RateLimiter rateLimiter;
  private volatile boolean closed = false;

  /**
   * Constructs a FallbackService with the specified primary and fallback services and a circuit breaker.
   *
   * @param primaryService the primary service to use
   * @param fallbackService the fallback service to use
   * @param circuitBreaker the circuit breaker to use
   */
  public FallbackService(Service primaryService, Service fallbackService, CircuitBreaker circuitBreaker) {
    this.primaryService = primaryService;
    this.fallbackService = fallbackService;
    this.circuitBreaker = circuitBreaker;
    this.executor = Executors.newCachedThreadPool();
    this.monitor = new ServiceMonitor();
    this.healthChecker = Executors.newSingleThreadScheduledExecutor();
    this.rateLimiter = new RateLimiter(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1));
    
    startHealthChecker();
  }

  private void startHealthChecker() {
    healthChecker.scheduleAtFixedRate(() -> {
      try {
        if (monitor.getSuccessRate() < MIN_SUCCESS_RATE) {
          LOGGER.warning("Success rate below threshold: " + monitor.getSuccessRate());
        }
        if (Duration.between(monitor.getLastSuccessTime(), Instant.now()).toMinutes() > 5) {
          LOGGER.warning("No successful requests in last 5 minutes");
        }
      } catch (Exception e) {
        LOGGER.severe("Health check failed: " + e.getMessage());
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
    if (closed) {
      throw new ServiceException("Service is closed");
    }
    
    if (!rateLimiter.tryAcquire()) {
      monitor.recordFallback();
      return executeFallback();
    }
    
    if (circuitBreaker.isOpen()) {
      LOGGER.info("Circuit breaker is open, using fallback");
      monitor.recordFallback();
      return executeFallback();
    }

    Instant start = Instant.now();
    Exception lastException = null;

    for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
      try {
        String result = executeWithTimeout(primaryService::getData);
        Duration responseTime = Duration.between(start, Instant.now());
        
        circuitBreaker.recordSuccess();
        monitor.recordSuccess(responseTime);
        updateFallbackCache(result);
        
        return result;
      } catch (Exception e) {
        lastException = e;
        LOGGER.warning("Attempt " + (attempt + 1) + " failed: " + e.getMessage());
        circuitBreaker.recordFailure();
        monitor.recordError();
        
        if (attempt < MAX_RETRIES - 1) {
          Thread.sleep(RETRY_DELAY_MS * (attempt + 1)); // Exponential backoff
        }
      }
    }
    
    monitor.recordFallback();
    if (lastException != null) {
      LOGGER.severe("All attempts failed. Last error: " + lastException.getMessage());
    }
    return executeFallback();
  }

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
      LOGGER.severe("Fallback service failed: " + e.getMessage());
      return "Service temporarily unavailable";
    }
  }

  private void updateFallbackCache(String result) {
    try {
      if (fallbackService instanceof LocalCacheService) {
        ((LocalCacheService) fallbackService).updateCache("default", result);
      }
    } catch (Exception e) {
      LOGGER.warning("Failed to update fallback cache: " + e.getMessage());
    }
  }

  /**
   * Shuts down the executor service.
   */
  @Override
  public void close() {
    closed = true;
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
    }
  }
  
  public ServiceMonitor getMonitor() {
    return monitor;
  }

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
      
      // Remove expired timestamps
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