/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

import org.junit.jupiter.api.Test;

/**
 * Circuit Breaker test
 */
public class CircuitBreakerTest {

  //long timeout, int failureThreshold, long retryTimePeriod
  @Test
  public void testSetState() {
    var circuitBreaker = new CircuitBreaker(1, 1, 100);
    //Right now, failureCount<failureThreshold, so state should be closed
    assertEquals(circuitBreaker.getState(), "CLOSED");
    circuitBreaker.failureCount = 4;
    circuitBreaker.lastFailureTime = System.nanoTime();
    circuitBreaker.setState();
    //Since failureCount>failureThreshold, and lastFailureTime is nearly equal to current time,
    //state should be half-open
    assertEquals(circuitBreaker.getState(), "HALF_OPEN");
    //Since failureCount>failureThreshold, and lastFailureTime is much lesser current time,
    //state should be open
    circuitBreaker.lastFailureTime = System.nanoTime() - 1000 * 1000 * 1000 * 1000;
    circuitBreaker.setState();
    assertEquals(circuitBreaker.getState(), "OPEN");
    //Now set it back again to closed to test idempotency
    circuitBreaker.failureCount = 0;
    circuitBreaker.setState();
    assertEquals(circuitBreaker.getState(), "CLOSED");
  }

  @Test
  public void testSetStateForBypass() {
    var circuitBreaker = new CircuitBreaker(1, 1, 100);
    //Right now, failureCount<failureThreshold, so state should be closed
    //Bypass it and set it to open
    circuitBreaker.setStateForBypass(State.OPEN);
    assertEquals(circuitBreaker.getState(), "OPEN");
  }

  @Test
  public void testApiResponses() {
    var circuitBreaker = new CircuitBreaker(1, 1, 100);
    try {
      //Call with the paramater start_time set to huge amount of time in past so that service 
      //replies with "Ok". Also, state is CLOSED in start
      var serviceStartTime = System.nanoTime() - 60 * 1000 * 1000 * 1000;
      var response = circuitBreaker.call("delayedService", serviceStartTime);
      assertEquals(response, "Delayed service is working");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
