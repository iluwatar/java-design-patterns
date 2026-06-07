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
  private final long checkInterval;
  private final TimeUnit timeUnit;

  public AdaptiveRateLimiter(int initialLimit, int maxLimit) {
    this(initialLimit, maxLimit, 10, TimeUnit.SECONDS);
  }

  /**
   * Constructor with option to set custom interval.
   *
   * @param initialLimit initial rate limit
   * @param maxLimit maximum rate limit
   * @param interval check interval
   * @param timeUnit check interval time unit
   */
  public AdaptiveRateLimiter(int initialLimit, int maxLimit, long interval, TimeUnit timeUnit) {
    this.initialLimit = initialLimit;
    this.maxLimit = maxLimit;
    this.currentLimit = new AtomicInteger(initialLimit);
    this.checkInterval = interval;
    this.timeUnit = timeUnit;
    healthChecker.scheduleAtFixedRate(this::adjustLimits, interval, interval, timeUnit);
  }

  @Override
  public void check(String serviceName, String operationName) throws RateLimitException {
    String key = serviceName + ":" + operationName;
    int current = currentLimit.get();

    // Reuse or create TokenBucket for this key using currentLimit and checkInterval
    RateLimiter limiter =
        limiters.computeIfAbsent(
            key, k -> new TokenBucketRateLimiter(current, current, checkInterval, timeUnit));

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

  /** Gracefully shut down the health checker executor and child limiters. */
  public void shutdown() {
    healthChecker.shutdown();
    limiters
        .values()
        .forEach(
            limiter -> {
              if (limiter instanceof TokenBucketRateLimiter) {
                ((TokenBucketRateLimiter) limiter).shutdown();
              }
            });
  }
}
