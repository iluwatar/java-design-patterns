package com.iluwatar.rate.limiting.pattern;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class TokenBucketRateLimiterTest extends RateLimiterTest {
  @Override
  protected RateLimiter createRateLimiter(int limit, long windowMillis) {
    return new TokenBucketRateLimiter(limit, (int) (limit * 1000 / windowMillis));
  }

  @Test
  void shouldAllowBurstRequests() throws Exception {
    TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(10, 5);
    for (int i = 0; i < 10; i++) {
      limiter.check("test", "op");
    }
  }

  @Test
  void shouldRefillTokensAfterTime() throws Exception {
    TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(1, 1);
    limiter.check("test", "op");
    assertThrows(RateLimitException.class, () -> limiter.check("test", "op"));

    TimeUnit.SECONDS.sleep(1);
    limiter.check("test", "op");
  }

  @Test
  void shouldHandleMultipleServicesSeparately() throws Exception {
    TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(1, 1);
    limiter.check("service1", "op");
    limiter.check("service2", "op");
    assertThrows(RateLimitException.class, () -> limiter.check("service1", "op"));
  }
}
