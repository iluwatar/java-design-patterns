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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/** Unit and integration tests for the Fallback design pattern. */
class FallbackPatternTest {

  @Test
  void testHealthyServiceCall() {
    try (var executor = new FallbackExecutor()) {
      var primary = new PrimaryService("Primary Response", 0, false);
      var fallback = new FallbackService("Fallback Response");
      var circuitBreaker = new SimpleCircuitBreaker(2, 1000);

      String response = executor.execute(primary, fallback, circuitBreaker, 100);
      assertEquals("Primary Response", response);
      assertEquals(SimpleCircuitBreaker.State.CLOSED, circuitBreaker.getState());
    }
  }

  @Test
  void testFailingServiceCall() {
    try (var executor = new FallbackExecutor()) {
      var primary = new PrimaryService("Primary Response", 0, true);
      var fallback = new FallbackService("Fallback Response");
      var circuitBreaker = new SimpleCircuitBreaker(2, 1000);

      // First failure: should call fallback, state remains CLOSED since threshold is 2
      String response1 = executor.execute(primary, fallback, circuitBreaker, 100);
      assertEquals("Fallback Response", response1);
      assertEquals(SimpleCircuitBreaker.State.CLOSED, circuitBreaker.getState());

      // Second failure: should call fallback, state becomes OPEN
      String response2 = executor.execute(primary, fallback, circuitBreaker, 100);
      assertEquals("Fallback Response", response2);
      assertEquals(SimpleCircuitBreaker.State.OPEN, circuitBreaker.getState());
    }
  }

  @Test
  void testTimeoutServiceCall() {
    try (var executor = new FallbackExecutor()) {
      // Primary takes 300ms, timeout limit is 50ms
      var primary = new PrimaryService("Primary Response", 300, false);
      var fallback = new FallbackService("Fallback Response");
      var circuitBreaker = new SimpleCircuitBreaker(1, 1000);

      String response = executor.execute(primary, fallback, circuitBreaker, 50);
      assertEquals("Fallback Response", response);
      assertEquals(SimpleCircuitBreaker.State.OPEN, circuitBreaker.getState());
    }
  }

  @Test
  void testCircuitBreakerOpenFastFail() {
    try (var executor = new FallbackExecutor()) {
      // Configure primary service to throw exception if called
      var primary = new PrimaryService("Primary Response", 0, true);
      var fallback = new FallbackService("Fallback Response");
      var circuitBreaker = new SimpleCircuitBreaker(1, 1000);

      // Force open by registering a failure
      circuitBreaker.recordFailure();
      assertEquals(SimpleCircuitBreaker.State.OPEN, circuitBreaker.getState());

      // Now call execute. It should short-circuit and not call the failing primary (fast-fail).
      String response = executor.execute(primary, fallback, circuitBreaker, 100);
      assertEquals("Fallback Response", response);
    }
  }

  @Test
  void testCircuitBreakerRecovery() throws InterruptedException {
    try (var executor = new FallbackExecutor()) {
      var primary = new PrimaryService("Primary Response", 0, false);
      var fallback = new FallbackService("Fallback Response");
      // Failure threshold = 1, retry period = 100ms
      var circuitBreaker = new SimpleCircuitBreaker(1, 100);

      // Trip the breaker
      circuitBreaker.recordFailure();
      assertEquals(SimpleCircuitBreaker.State.OPEN, circuitBreaker.getState());

      // Wait 150ms to exceed retry period
      Thread.sleep(150);

      // State should evaluate to HALF_OPEN
      assertEquals(SimpleCircuitBreaker.State.HALF_OPEN, circuitBreaker.getState());

      // Execute. Healthy primary service succeeds, state transitions to CLOSED
      String response = executor.execute(primary, fallback, circuitBreaker, 100);
      assertEquals("Primary Response", response);
      assertEquals(SimpleCircuitBreaker.State.CLOSED, circuitBreaker.getState());
    }
  }
}
