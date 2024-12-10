package com.iluwatar.fallback;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates the Fallback pattern with Circuit Breaker implementation.
 * 
 * <p>This application shows how to:
 * - Handle failures gracefully using fallback mechanisms.
 * - Implement circuit breaker pattern to prevent cascade failures.
 * - Configure timeouts and retries for resilient service calls.
 * - Monitor service health and performance.
 * 
 * <p>The app uses a primary remote service with a local cache fallback.
 */
public class App {
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
  
  /** Service call timeout in seconds. */
  private static final int TIMEOUT = 2;
  
  /** Maximum number of retry attempts. */
  private static final int MAX_ATTEMPTS = 3;
  
  /** Delay between retry attempts in milliseconds. */
  private static final int RETRY_DELAY = 1000;
  
  /** Default API endpoint for remote service. */
  private static final String DEFAULT_API_URL = "https://jsonplaceholder.typicode.com/todos";

  /** Service execution state tracking. */
  private enum ExecutionState {
    READY, RUNNING, FAILED, SHUTDOWN
  }

  private final CircuitBreaker circuitBreaker;
  private final ExecutorService executor;
  private final Service primaryService;
  private final Service fallbackService;
  private volatile ExecutionState state;

  /**
   * Constructs an App with default configuration.
   * Creates HTTP client with timeout, remote service, and local cache fallback.
   */
  public App() {
    HttpClient httpClient = createHttpClient();
    this.primaryService = new RemoteService(DEFAULT_API_URL, httpClient);
    this.fallbackService = new LocalCacheService();
    this.circuitBreaker = new DefaultCircuitBreaker(MAX_ATTEMPTS);
    this.executor = Executors.newSingleThreadExecutor();
    this.state = ExecutionState.READY;
  }

  private HttpClient createHttpClient() {
    try {
      return HttpClient.newBuilder()
          .connectTimeout(Duration.ofSeconds(TIMEOUT))
          .build();
    } catch (Exception e) {
      LOGGER.warn("Failed to create custom HTTP client, using default", e);
      return HttpClient.newHttpClient();
    }
  }

  /**
   * Constructs an App with custom services and circuit breaker.
   *
   * @param primaryService Primary service implementation
   * @param fallbackService Fallback service implementation
   * @param circuitBreaker Circuit breaker implementation
   * @throws IllegalArgumentException if any parameter is null
   */
  public App(Service primaryService, Service fallbackService, CircuitBreaker circuitBreaker) {
    if (primaryService == null || fallbackService == null || circuitBreaker == null) {
      throw new IllegalArgumentException("All services must be non-null");
    }
    this.circuitBreaker = circuitBreaker;
    this.executor = Executors.newSingleThreadExecutor();
    this.primaryService = primaryService;
    this.fallbackService = fallbackService;
    this.state = ExecutionState.READY;
  }

  /**
   * Executes the service with fallback mechanism.
   *
   * @return Result from primary or fallback service
   * @throws IllegalStateException if app is shutdown
   */
  public String executeWithFallback() {
    if (state == ExecutionState.SHUTDOWN) {
      throw new IllegalStateException("Application is shutdown");
    }

    state = ExecutionState.RUNNING;
    if (circuitBreaker.isOpen()) {
      LOGGER.info("Circuit breaker is open, using cached data");
      return getFallbackData();
    }

    try {
      Future<String> future = executor.submit(primaryService::getData);
      String result = future.get(TIMEOUT, TimeUnit.SECONDS);
      circuitBreaker.recordSuccess();
      updateFallbackCache(result);
      return result;
    } catch (Exception e) {
      LOGGER.warn("Primary service failed: {}", e.getMessage());
      circuitBreaker.recordFailure();
      state = ExecutionState.FAILED;
      return getFallbackData();
    }
  }

  private String getFallbackData() {
    try {
      return fallbackService.getData();
    } catch (Exception e) {
      LOGGER.error("Fallback service failed: {}", e.getMessage());
      return "System is currently unavailable";
    }
  }

  private void updateFallbackCache(String result) {
    if (fallbackService instanceof LocalCacheService) {
      ((LocalCacheService) fallbackService).updateCache("default", result);
    }
  }

  /**
   * Shuts down the executor service and cleans up resources.
   */
  public void shutdown() {
    state = ExecutionState.SHUTDOWN;
    executor.shutdown();
    try {
      if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
        executor.shutdownNow();
      }
    } catch (InterruptedException e) {
      executor.shutdownNow();
      Thread.currentThread().interrupt();
      LOGGER.error("Shutdown interrupted", e);
    }
  }

  /**
   * Main method demonstrating the fallback pattern.
   */
  public static void main(String[] args) {
    App app = new App();
    try {
      for (int i = 0; i < MAX_ATTEMPTS; i++) {
        try {
          String result = app.executeWithFallback();
          LOGGER.info("Attempt {}: Result = {}", i + 1, result);
        } catch (Exception e) {
          LOGGER.error("Attempt {} failed: {}", i + 1, e.getMessage());
        }
        Thread.sleep(RETRY_DELAY);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Main thread interrupted", e);
    } finally {
      app.shutdown();
    }
  }
}
