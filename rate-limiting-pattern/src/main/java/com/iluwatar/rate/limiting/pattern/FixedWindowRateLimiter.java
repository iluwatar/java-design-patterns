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

/**
 * Implements a fixed window rate limiter. It allows up to 'limit' number of requests within a time
 * window of fixed size.
 */
public class FixedWindowRateLimiter implements RateLimiter {
  private final int limit;
  private final long windowMillis;
  private final ConcurrentHashMap<String, WindowCounter> counters = new ConcurrentHashMap<>();

  public FixedWindowRateLimiter(int limit, long windowSeconds) {
    this.limit = limit;
    this.windowMillis = TimeUnit.SECONDS.toMillis(windowSeconds);
  }

  @Override
  public synchronized void check(String serviceName, String operationName)
      throws RateLimitException {
    String key = serviceName + ":" + operationName;
    WindowCounter counter = counters.computeIfAbsent(key, k -> new WindowCounter());

    if (!counter.tryIncrement()) {
      System.out.printf(
          "[FixedWindow] Throttled %s.%s - Limit %d reached in window%n",
          serviceName, operationName, limit);
      throw new RateLimitException("Rate limit exceeded for " + key, windowMillis);
    } else {
      System.out.printf(
          "[FixedWindow] Allowed %s.%s - Count within window%n", serviceName, operationName);
    }
  }

  /** Tracks the count of requests within the current window. */
  private class WindowCounter {
    private AtomicInteger count = new AtomicInteger(0);
    private volatile long windowStart = System.currentTimeMillis();

    synchronized boolean tryIncrement() {
      long now = System.currentTimeMillis();
      // Reset window if expired
      if (now - windowStart > windowMillis) {
        count.set(0);
        windowStart = now;
      }
      // Enforce the request limit within window
      return count.incrementAndGet() <= limit;
    }
  }
}
