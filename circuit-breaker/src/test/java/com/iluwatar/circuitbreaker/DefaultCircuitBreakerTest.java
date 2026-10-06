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
package com.iluwatar.circuitbreaker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

/** Circuit Breaker test */
class DefaultCircuitBreakerTest {

  // long timeout, int failureThreshold, long retryTimePeriod
  @Test
  void testEvaluateState() {
    var circuitBreaker = new DefaultCircuitBreaker(null, 1, 1, 60_000_000_000L);
    // Right now, failureCount<failureThreshold, so state should be closed
    assertEquals("CLOSED", circuitBreaker.getState());
    circuitBreaker.failureCount = 4;
    circuitBreaker.lastFailureTime = System.nanoTime();
    circuitBreaker.evaluateState();
    // Since failureCount>failureThreshold, and lastFailureTime is nearly equal to current time,
    // the retry period has not elapsed yet, so state should be open
    assertEquals("OPEN", circuitBreaker.getState());
    // Since failureCount>failureThreshold, and lastFailureTime is older than the retry period,
    // state should be half-open
    circuitBreaker.lastFailureTime = System.nanoTime() - 120_000_000_000L;
    circuitBreaker.evaluateState();
    assertEquals("HALF_OPEN", circuitBreaker.getState());
    // Now set it back again to closed to test idempotency
    circuitBreaker.failureCount = 0;
    circuitBreaker.evaluateState();
    assertEquals("CLOSED", circuitBreaker.getState());
  }

  @Test
  void testSetStateForBypass() {
    var circuitBreaker = new DefaultCircuitBreaker(null, 1, 1, 2000 * 1000 * 1000);
    // Right now, failureCount<failureThreshold, so state should be closed
    // Bypass it and set it to open
    circuitBreaker.setState(State.OPEN);
    assertEquals(circuitBreaker.getState(), "OPEN");
  }

  @Test
  void testApiResponses() throws RemoteServiceException {
    RemoteService mockService =
        new RemoteService() {
          @Override
          public String call() throws RemoteServiceException {
            return "Remote Success";
          }
        };
    var circuitBreaker = new DefaultCircuitBreaker(mockService, 1, 1, 100);
    // Call with the parameter start_time set to huge amount of time in past so that service
    // replies with "Ok". Also, state is CLOSED in start
    var serviceStartTime = System.nanoTime() - 60 * 1000 * 1000 * 1000;
    var response = circuitBreaker.attemptRequest();
    assertEquals(response, "Remote Success");
  }

  @Test
  void testOpenCircuitReturnsCachedResponseWithoutCallingService() throws RemoteServiceException {
    var calls = new AtomicInteger();
    RemoteService failingService =
        new RemoteService() {
          @Override
          public String call() throws RemoteServiceException {
            throw new RemoteServiceException("Failure #" + calls.incrementAndGet());
          }
        };
    var circuitBreaker = new DefaultCircuitBreaker(failingService, 1, 1, 60_000_000_000L);

    // The first failure reaches the threshold and opens the circuit
    assertThrows(RemoteServiceException.class, circuitBreaker::attemptRequest);

    // While open, the cached failure response is returned without calling the service again
    assertEquals("Failure #1", circuitBreaker.attemptRequest());
    assertEquals(1, calls.get());
    assertEquals("OPEN", circuitBreaker.getState());
  }
}
