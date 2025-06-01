package com.iluwatar.rate.limiting.pattern;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class FixedWindowRateLimiterTest extends RateLimiterTest {
  @Override
  protected RateLimiter createRateLimiter(int limit, long windowMillis) {
    return new FixedWindowRateLimiter(limit, windowMillis / 1000);
  }

  @Test
  void shouldResetCounterAfterWindow() throws Exception {
    FixedWindowRateLimiter limiter = new FixedWindowRateLimiter(1, 1);
    limiter.check("test", "op");
    assertThrows(RateLimitException.class, () -> limiter.check("test", "op"));

    TimeUnit.SECONDS.sleep(1);
    limiter.check("test", "op");
  }

  @Test
  void shouldNotAllowMoreThanLimitInWindow() throws Exception {
    FixedWindowRateLimiter limiter = new FixedWindowRateLimiter(3, 1);
    for (int i = 0; i < 3; i++) {
      limiter.check("test", "op");
    }
    assertThrows(RateLimitException.class, () -> limiter.check("test", "op"));
  }
}
