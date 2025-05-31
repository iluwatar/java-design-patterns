package com.iluwatar.rate.limiting.pattern;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public abstract class RateLimiterTest {
  protected abstract RateLimiter createRateLimiter(int limit, long windowMillis);

  @Test
  void shouldAllowRequestsWithinLimit() throws Exception {
    RateLimiter limiter = createRateLimiter(5, 1000);
    for (int i = 0; i < 5; i++) {
      limiter.check("test", "op");
    }
  }

  @Test
  void shouldThrowWhenLimitExceeded() throws Exception {
    RateLimiter limiter = createRateLimiter(2, 1000);
    limiter.check("test", "op");
    limiter.check("test", "op");
    assertThrows(RateLimitException.class, () -> limiter.check("test", "op"));
  }
}