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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar;

import java.net.http.HttpClient;
import java.time.Duration;  // Add this import
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * App class that demonstrates the use of Circuit Breaker pattern with fallback mechanism.
 */
public class App {
  private static final Logger LOGGER = Logger.getLogger(App.class.getName());
  private static final int TIMEOUT = 2;
  private static final int MAX_ATTEMPTS = 3;
  private static final int RETRY_DELAY = 1000;
  private static final String DEFAULT_API_URL = "https://jsonplaceholder.typicode.com/todos";

  private final CircuitBreaker circuitBreaker;
  private final ExecutorService executor;
  private final Service primaryService;
  private final Service fallbackService;

  /**
   * Constructs an App with default primary and fallback services.
   */
  public App() {
    HttpClient httpClient;
    try {
      httpClient = HttpClient.newBuilder()
          .connectTimeout(Duration.ofSeconds(TIMEOUT))
          .build();
    } catch (Exception e) {
      LOGGER.severe("Failed to create HTTP client: " + e.getMessage());
      httpClient = HttpClient.newHttpClient(); // Fallback to default client
    }

    this.primaryService = new RemoteService(DEFAULT_API_URL, httpClient);
    this.fallbackService = new LocalCacheService();
    this.circuitBreaker = new DefaultCircuitBreaker(MAX_ATTEMPTS);
    this.executor = Executors.newSingleThreadExecutor();
  }

  /**
   * Constructs an App with the specified primary and fallback services and a circuit breaker.
   *
   * @param primaryService the primary service to use
   * @param fallbackService the fallback service to use
   * @param circuitBreaker the circuit breaker to use
   */
  public App(final Service primaryService, final Service fallbackService, final CircuitBreaker circuitBreaker) {
    this.circuitBreaker = circuitBreaker;
    this.executor = Executors.newSingleThreadExecutor();
    this.primaryService = primaryService;
    this.fallbackService = fallbackService;
  }

  /**
   * Main method to run the application.
   *
   * @param args command line arguments
   */
  public static void main(final String[] args) {
    App app = new App();
    for (int i = 0; i < 5; i++) {
      try {
        String result = app.executeWithFallback();
        System.out.println("Attempt " + (i + 1) + ": Result = " + result);
      } catch (Exception e) {
        System.err.println("Attempt " + (i + 1) + " failed: " + e.getMessage());
      }
      try {
        Thread.sleep(RETRY_DELAY);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.err.println("Thread was interrupted: " + e.getMessage());
      }
    }
    app.shutdown();
  }

  /**
   * Executes the primary service with a fallback mechanism.
   *
   * @return the result from the primary or fallback service
   */
  public String executeWithFallback() {
    if (circuitBreaker.isOpen()) {
      LOGGER.info("Circuit breaker is open, using cached data");
      return getFallbackData();
    }

    try {
      Future<String> future = executor.submit(primaryService::getData);
      String result = future.get(TIMEOUT, TimeUnit.SECONDS);
      circuitBreaker.recordSuccess();
      if (fallbackService instanceof LocalCacheService) {
        ((LocalCacheService) fallbackService).updateCache("default", result);
      }
      return result;
    } catch (Exception e) {
      LOGGER.warning("Primary service failed, using fallback. Exception: " + e.getMessage());
      circuitBreaker.recordFailure();
      return getFallbackData();
    }
  }

  /**
   * Retrieves data from the fallback service.
   *
   * @return the data from the fallback service
   */
  private String getFallbackData() {
    try {
      return fallbackService.getData();
    } catch (Exception e) {
      LOGGER.warning("Fallback service failed. Exception: " + e.getMessage());
      return "System is currently unavailable";
    }
  }

  /**
   * Shuts down the executor service.
   */
  public void shutdown() {
    executor.shutdown();
  }
}