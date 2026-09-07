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
package com.iluwatar.timeout;

import java.time.Duration;
import java.util.Objects;

/**
 * Declares how long a call to a named downstream service may take before it is abandoned.
 *
 * <p>Each service gets its own policy so that a fast catalog lookup and a slow recommendation
 * engine can be governed by different limits.
 *
 * @param serviceName name of the downstream service the policy applies to
 * @param timeout maximum time the caller is willing to wait for a response
 */
public record TimeoutPolicy(String serviceName, Duration timeout) {

  /** Validates that the policy names a service and carries a positive limit. */
  public TimeoutPolicy {
    Objects.requireNonNull(serviceName, "serviceName");
    Objects.requireNonNull(timeout, "timeout");
    if (serviceName.isBlank()) {
      throw new IllegalArgumentException("serviceName must not be blank");
    }
    if (timeout.isZero() || timeout.isNegative()) {
      throw new IllegalArgumentException("timeout must be positive");
    }
  }

  /**
   * Convenience factory for millisecond based limits.
   *
   * @param serviceName name of the downstream service
   * @param millis limit in milliseconds
   * @return the policy
   */
  public static TimeoutPolicy of(String serviceName, long millis) {
    return new TimeoutPolicy(serviceName, Duration.ofMillis(millis));
  }
}
