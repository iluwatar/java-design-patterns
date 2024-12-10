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

import java.time.Duration;
import java.time.Instant;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Circuit breaker implementation with three states:
 * - CLOSED: Normal operation, requests flow through
 * - OPEN: Failing fast, no attempts to call primary service
 * - HALF_OPEN: Testing if service has recovered.
 *
 * <p>Features:
 * - Thread-safe operation
 * - Sliding window failure counting
 * - Automatic state transitions
 * - Configurable thresholds and timeouts
 * - Recovery validation period
 */
public class DefaultCircuitBreaker implements CircuitBreaker {
  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCircuitBreaker.class);
  
  // Circuit breaker configuration
  private static final long RESET_TIMEOUT = 5000; // 5 seconds
  private static final Duration MIN_HALF_OPEN_DURATION = Duration.ofSeconds(30);

  private volatile State state;
  private final int failureThreshold;
  private volatile long lastFailureTime;
  private final Queue<Long> failureTimestamps;
  private final Duration windowSize;
  private volatile Instant halfOpenStartTime;

  /**
   * Constructs a DefaultCircuitBreaker with the given failure threshold.
   *
   * @param failureThreshold the number of failures to trigger the circuit breaker
   */
  public DefaultCircuitBreaker(final int failureThreshold) {
    this.failureThreshold = failureThreshold;
    this.state = State.CLOSED;
    this.failureTimestamps = new ConcurrentLinkedQueue<>();
    this.windowSize = Duration.ofMinutes(1);
  }

  /**
   * Checks if a request should be allowed through the circuit breaker.
   * @return true if request should be allowed, false if it should be blocked
   */
  @Override
  public synchronized boolean allowRequest() {
    if (state == State.CLOSED) {
      return true;
    }
    if (state == State.OPEN) {
      if (System.currentTimeMillis() - lastFailureTime > RESET_TIMEOUT) {
        transitionToHalfOpen();
        return true;
      }
      return false;
    }
    // In HALF_OPEN state, allow limited testing
    return true;
  }

  @Override
  public synchronized boolean isOpen() {
    if (state == State.OPEN) {
      if (System.currentTimeMillis() - lastFailureTime > RESET_TIMEOUT) {
        transitionToHalfOpen();
        return false;
      }
      return true;
    }
    return false;
  }

  /**
   * Transitions circuit breaker to half-open state.
   * Clears failure history and starts recovery monitoring.
   */
  private synchronized void transitionToHalfOpen() {
    state = State.HALF_OPEN;
    halfOpenStartTime = Instant.now();
    failureTimestamps.clear();
    LOGGER.info("Circuit breaker transitioning to HALF_OPEN state");
  }

  /**
   * Records successful operation.
   * In half-open state, requires sustained success before closing circuit.
   */
  @Override
  public synchronized void recordSuccess() {
    if (state == State.HALF_OPEN) {
      if (Duration.between(halfOpenStartTime, Instant.now())
          .compareTo(MIN_HALF_OPEN_DURATION) >= 0) {
        LOGGER.info("Circuit breaker recovering - transitioning to CLOSED");
        state = State.CLOSED;
      }
    }
    failureTimestamps.clear();
  }

  @Override
  public synchronized void recordFailure() {
    long now = System.currentTimeMillis();
    failureTimestamps.offer(now);

    // Cleanup old timestamps outside window
    while (!failureTimestamps.isEmpty() 
        && failureTimestamps.peek() < now - windowSize.toMillis()) {
      failureTimestamps.poll();
    }

    if (failureTimestamps.size() >= failureThreshold) {
      LOGGER.warn("Failure threshold reached - opening circuit breaker");
      state = State.OPEN;
      lastFailureTime = now;
    }
  }

  @Override
  public void reset() {
    failureTimestamps.clear();
    lastFailureTime = 0;
    state = State.CLOSED;
  }

  /**
   * Returns the current state of the circuit breaker mapped to the public enum.
   * @return Current CircuitState value
   */
  @Override
  public synchronized CircuitState getState() {
    if (state == State.OPEN && System.currentTimeMillis() - lastFailureTime > RESET_TIMEOUT) {
      transitionToHalfOpen();
    }
    return switch (state) {
      case CLOSED -> CircuitState.CLOSED;
      case OPEN -> CircuitState.OPEN;
      case HALF_OPEN -> CircuitState.HALF_OPEN;
    };
  }

  /**
   * Internal states of the circuit breaker.
   * Maps to the public CircuitState enum for external reporting.
   */
  private enum State {
    CLOSED,
    OPEN,
    HALF_OPEN
  }
}