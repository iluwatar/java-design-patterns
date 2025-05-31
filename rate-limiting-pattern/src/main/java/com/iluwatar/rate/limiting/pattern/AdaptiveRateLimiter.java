package com.iluwatar.rate.limiting.pattern;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Adaptive rate limiter that adjusts limits based on system health.
 */
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
    healthChecker.scheduleAtFixedRate(this::adjustLimits, 10, 10, TimeUnit.SECONDS);
  }

  @Override
  public void check(String serviceName, String operationName) throws RateLimitException {
    String key = serviceName + ":" + operationName;
    int current = currentLimit.get();

    RateLimiter limiter = limiters.computeIfAbsent(key,
        k -> new TokenBucketRateLimiter(current, current));

    try {
      limiter.check(serviceName, operationName);
      System.out.printf("[Adaptive] Allowed %s.%s - CurrentLimit: %d%n", serviceName, operationName, current);
    } catch (RateLimitException e) {
      currentLimit.updateAndGet(curr -> Math.max(initialLimit, curr / 2));
      System.out.printf("[Adaptive] Throttled %s.%s - Decreasing limit to %d%n",
          serviceName, operationName, currentLimit.get());
      throw e;
    }
  }

  private void adjustLimits() {
    int updated = currentLimit.updateAndGet(curr -> Math.min(maxLimit, curr + (initialLimit / 2)));
    System.out.printf("[Adaptive] Health check passed - Increasing limit to %d%n", updated);
  }
}