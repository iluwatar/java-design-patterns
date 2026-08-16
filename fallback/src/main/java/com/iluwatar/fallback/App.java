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
package com.iluwatar.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Fallback design pattern is a resiliency pattern used in microservices architecture to handle
 * failures gracefully. When a service is unavailable, fails, or times out, the system responds with
 * a pre-configured fallback mechanism (like cached data or a simplified response).
 *
 * <p>This App demonstrates: 1. Healthy calls returning standard responses. 2. Failing calls
 * (throwing exception) falling back to a fallback handler. 3. Latent calls (timing out) falling
 * back. 4. Circuit Breaker tripping and immediately fast-failing to the fallback handler. 5.
 * Recovery after a retry duration, returning the system to a healthy CLOSED state.
 */
public class App {
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Main entry point for the application.
   *
   * @param args Command line arguments (not used)
   */
  public static void main(String[] args) {
    try (var executor = new FallbackExecutor()) {
      var healthyPrimary = new PrimaryService("Healthy data from primary service", 10, false);
      var failingPrimary = new PrimaryService("Failing service", 0, true);
      var slowPrimary = new PrimaryService("Slow response from primary service", 500, false);
      var fallback = new FallbackService("Fallback degraded/cached response");

      // Failure threshold is 2 failures, retry time period is 1 second (1000ms)
      var circuitBreaker = new SimpleCircuitBreaker(2, 1000);

      // Scenario 1: Healthy primary service call
      LOGGER.info("Scenario 1: Executing request to healthy primary service...");
      String response1 = executor.execute(healthyPrimary, fallback, circuitBreaker, 100);
      LOGGER.info("Response received: {}", response1);
      LOGGER.info("Circuit Breaker State: {}\n", circuitBreaker.getState());

      // Scenario 2: Failing primary service call (fails and increments failure count to 1)
      LOGGER.info("Scenario 2: Executing request to failing primary service (throws exception)...");
      String response2 = executor.execute(failingPrimary, fallback, circuitBreaker, 100);
      LOGGER.info("Response received: {}", response2);
      LOGGER.info("Circuit Breaker State: {}\n", circuitBreaker.getState());

      // Scenario 3: Slow primary service call (times out and increments failure count to 2,
      // tripping breaker)
      LOGGER.info("Scenario 3: Executing request to slow primary service (triggers timeout)...");
      String response3 = executor.execute(slowPrimary, fallback, circuitBreaker, 100);
      LOGGER.info("Response received: {}", response3);
      LOGGER.info("Circuit Breaker State: {}\n", circuitBreaker.getState());

      // Scenario 4: Fast failing when circuit is OPEN
      LOGGER.info("Scenario 4: Executing request while Circuit Breaker is OPEN...");
      String response4 = executor.execute(healthyPrimary, fallback, circuitBreaker, 100);
      LOGGER.info("Response received: {}", response4);
      LOGGER.info("Circuit Breaker State: {}\n", circuitBreaker.getState());

      // Scenario 5: Recovery from OPEN state
      LOGGER.info("Scenario 5: Waiting for retry period to elapse...");
      try {
        Thread.sleep(1100); // Wait longer than retryTimePeriodMs (1000ms)
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
      LOGGER.info(
          "Circuit Breaker State (should be HALF_OPEN on next check): {}",
          circuitBreaker.getState());
      LOGGER.info("Executing request to healthy primary service to reset breaker...");
      String response5 = executor.execute(healthyPrimary, fallback, circuitBreaker, 100);
      LOGGER.info("Response received: {}", response5);
      LOGGER.info("Circuit Breaker State (should be CLOSED): {}\n", circuitBreaker.getState());
    }
  }
}
