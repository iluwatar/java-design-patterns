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
