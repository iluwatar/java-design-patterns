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

/** A simplified circuit breaker implementation for tracking remote service health. */
public class SimpleCircuitBreaker {
  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCircuitBreaker.class);

  private final int failureThreshold;
  private final long retryTimePeriodMs;
  private int failureCount = 0;
  private long lastFailureTime = 0;
  private State state = State.CLOSED;

  /** The state of the circuit breaker. */
  public enum State {
    CLOSED,
    OPEN,
    HALF_OPEN
  }

  /**
   * Constructor for SimpleCircuitBreaker.
   *
   * @param failureThreshold consecutive failure count threshold to trip the breaker
   * @param retryTimePeriodMs time duration to wait in OPEN state before trying again (HALF_OPEN)
   */
  public SimpleCircuitBreaker(int failureThreshold, long retryTimePeriodMs) {
    this.failureThreshold = failureThreshold;
    this.retryTimePeriodMs = retryTimePeriodMs;
  }

  /**
   * Get the current state of the circuit breaker after evaluating transitions.
   *
   * @return current state
   */
  public synchronized State getState() {
    evaluateState();
    return state;
  }

  private void evaluateState() {
    if (state == State.OPEN) {
      if (System.currentTimeMillis() - lastFailureTime > retryTimePeriodMs) {
        state = State.HALF_OPEN;
        LOGGER.info("Circuit Breaker transitioned to HALF_OPEN");
      }
    }
  }

  /** Records a successful operation, resetting the failure counter and closing the circuit. */
  public synchronized void recordSuccess() {
    failureCount = 0;
    state = State.CLOSED;
    LOGGER.info("Circuit Breaker transitioned to CLOSED (success recorded)");
  }

  /** Records a failure, potentially tripping the circuit to OPEN if threshold is met. */
  public synchronized void recordFailure() {
    failureCount++;
    lastFailureTime = System.currentTimeMillis();
    if (state == State.CLOSED && failureCount >= failureThreshold) {
      state = State.OPEN;
      LOGGER.warn("Circuit Breaker transitioned to OPEN (failure threshold reached)");
    } else if (state == State.HALF_OPEN) {
      state = State.OPEN;
      LOGGER.warn("Circuit Breaker transitioned to OPEN (failed during HALF_OPEN)");
    }
  }
}
