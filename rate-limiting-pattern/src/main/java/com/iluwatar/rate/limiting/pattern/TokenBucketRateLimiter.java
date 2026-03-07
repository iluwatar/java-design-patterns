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
 * Token Bucket rate limiter implementation. Allows requests to proceed as long as there are tokens
 * available in the bucket. Tokens are added at a fixed interval up to a defined capacity.
 */
public class TokenBucketRateLimiter implements RateLimiter {
  private final int capacity;
  private final int refillRate;
  private final ConcurrentHashMap<String, TokenBucket> buckets = new ConcurrentHashMap<>();
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  public TokenBucketRateLimiter(int capacity, int refillRate) {
    this.capacity = capacity;
    this.refillRate = refillRate;
    // Refill tokens in all buckets every second
    scheduler.scheduleAtFixedRate(this::refillBuckets, 1, 1, TimeUnit.SECONDS);
  }

  @Override
  public void check(String serviceName, String operationName) throws RateLimitException {
    String key = serviceName + ":" + operationName;
    TokenBucket bucket = buckets.computeIfAbsent(key, k -> new TokenBucket(capacity));

    if (!bucket.tryConsume()) {
      System.out.printf(
          "[TokenBucket] Throttled %s.%s - No tokens available%n", serviceName, operationName);
      throw new ThrottlingException(serviceName, operationName, 1000);
    } else {
      System.out.printf(
          "[TokenBucket] Allowed %s.%s - Tokens remaining%n", serviceName, operationName);
    }
  }

  private void refillBuckets() {
    buckets.forEach((k, b) -> b.refill(refillRate));
  }

  public void shutdown() {
    scheduler.shutdown();
  }

  /** Inner class that represents the bucket holding tokens for each service-operation. */
  private static class TokenBucket {
    private final int capacity;
    private final AtomicInteger tokens;
    private long lastRefillTime;

    TokenBucket(int capacity) {
      this.capacity = capacity;
      this.tokens = new AtomicInteger(capacity);
      this.lastRefillTime = System.currentTimeMillis();
    }

    synchronized boolean tryConsume() {
      refillIfNeeded();

      if(tokens.get() <= 0) {
        return false;
      }

      tokens.decrementAndGet();
      return true;
    }

    private void refillIfNeeded() {
      long now = System.currentTimeMillis();
      if(now - lastRefillTime >= 1000) {
        tokens.set(capacity);
        lastRefillTime = now;
      }
    }

    void refill(int amount) {
      tokens.getAndUpdate(current -> Math.min(current + amount, capacity));
    }
  }
}
