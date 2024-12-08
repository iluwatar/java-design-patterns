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

import java.time.Duration;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * DefaultCircuitBreaker class that implements the CircuitBreaker interface.
 * It manages the state of a circuit breaker with a failure threshold and reset timeout.
 */
public final class DefaultCircuitBreaker implements CircuitBreaker {
  private final int failureThreshold;
  private long lastFailureTime;
  private State state;
  private static final long RESET_TIMEOUT = 5000;
  private final Queue<Long> failureTimestamps;
  private final Duration windowSize;

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

  @Override
  public boolean isOpen() {
    if (state == State.OPEN) {
      if (System.currentTimeMillis() - lastFailureTime > RESET_TIMEOUT) {
        state = State.HALF_OPEN;
        return false;
      }
      return true;
    }
    return false;
  }

  @Override
  public void recordSuccess() {
    failureTimestamps.clear();
    state = State.CLOSED;
  }

  @Override
  public void recordFailure() {
    long now = System.currentTimeMillis();
    failureTimestamps.offer(now);

    while (!failureTimestamps.isEmpty()
        && failureTimestamps.peek() < now - windowSize.toMillis()) {
      failureTimestamps.poll();
    }

    if (failureTimestamps.size() >= failureThreshold) {
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

  private enum State {
    CLOSED,
    OPEN,
    HALF_OPEN
  }
}