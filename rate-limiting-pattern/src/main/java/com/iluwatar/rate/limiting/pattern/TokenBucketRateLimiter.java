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
    this(capacity, refillRate, 1, TimeUnit.SECONDS);
  }

  /**
   * Constructor with custom refill interval.
   *
   * @param capacity token bucket capacity
   * @param refillRate token refill rate
   * @param refillInterval refill interval value
   * @param timeUnit refill interval time unit
   */
  public TokenBucketRateLimiter(
      int capacity, int refillRate, long refillInterval, TimeUnit timeUnit) {
    this.capacity = capacity;
    this.refillRate = refillRate;
    scheduler.scheduleAtFixedRate(this::refillBuckets, refillInterval, refillInterval, timeUnit);
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

  /** Inner class that represents the bucket holding tokens for each service-operation. */
  private static class TokenBucket {
    private final int capacity;
    private final AtomicInteger tokens;

    TokenBucket(int capacity) {
      this.capacity = capacity;
      this.tokens = new AtomicInteger(capacity);
    }

    boolean tryConsume() {
      while (true) {
        int current = tokens.get();
        if (current <= 0) return false;
        if (tokens.compareAndSet(current, current - 1)) return true;
      }
    }

    void refill(int amount) {
      tokens.getAndUpdate(current -> Math.min(current + amount, capacity));
    }
  }

  public void shutdown() {
    scheduler.shutdown();
  }
}
