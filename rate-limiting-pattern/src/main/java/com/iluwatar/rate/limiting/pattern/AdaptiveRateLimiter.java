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
package com.iluwatar.rate.limiting.pattern;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/** Adaptive rate limiter that adjusts limits based on system health. */
public class AdaptiveRateLimiter implements RateLimiter {
  private final int initialLimit;
  private final int maxLimit;
  private final AtomicInteger currentLimit;
  private final ConcurrentHashMap<String, RateLimiter> limiters = new ConcurrentHashMap<>();
  private final ScheduledExecutorService healthChecker = Executors.newScheduledThreadPool(1);

  public AdaptiveRateLimiter(int initialLimit, int maxLimit) {
    this.initialLimit = initialLimit;
    this.maxLimit = maxLimit;
    this.currentLimit = new AtomicInteger(initialLimit);
    // Periodically increase limit to recover if system appears healthy
    healthChecker.scheduleAtFixedRate(this::adjustLimits, 10, 10, TimeUnit.SECONDS);
  }

  @Override
  public void check(String serviceName, String operationName) throws RateLimitException {
    String key = serviceName + ":" + operationName;
    int current = currentLimit.get();

    // Reuse or create TokenBucket for this key using currentLimit
    RateLimiter limiter =
        limiters.computeIfAbsent(key, k -> new TokenBucketRateLimiter(current, current));

    try {
      limiter.check(serviceName, operationName);
      System.out.printf(
          "[Adaptive] Allowed %s.%s - CurrentLimit: %d%n", serviceName, operationName, current);
    } catch (RateLimitException e) {
      // On throttling, reduce system limit to reduce load
      currentLimit.updateAndGet(curr -> Math.max(initialLimit, curr / 2));
      System.out.printf(
          "[Adaptive] Throttled %s.%s - Decreasing limit to %d%n",
          serviceName, operationName, currentLimit.get());
      throw e;
    }
  }

  // Periodic recovery mechanism to raise limits when the system is under control
  private void adjustLimits() {
    int updated = currentLimit.updateAndGet(curr -> Math.min(maxLimit, curr + (initialLimit / 2)));
    System.out.printf("[Adaptive] Health check passed - Increasing limit to %d%n", updated);
  }
}
