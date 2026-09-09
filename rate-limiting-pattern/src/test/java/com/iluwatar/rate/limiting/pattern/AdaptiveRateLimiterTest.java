package com.iluwatar.rate.limiting.pattern;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AdaptiveRateLimiterTest {
  @Test
  void shouldDecreaseLimitWhenThrottled() throws Exception {
    AdaptiveRateLimiter limiter = new AdaptiveRateLimiter(10, 20);
    try {
      // Exceed initial limit
      for (int i = 0; i < 11; i++) {
        try {
          limiter.check("test", "op");
        } catch (RateLimitException e) {
          // Expected after 10 requests
        }
      }

      // Verify limit was reduced
      assertThrows(
          RateLimitException.class,
          () -> {
            for (int i = 0; i < 6; i++) { // New limit should be 5 (10/2)
              limiter.check("test", "op");
            }
          });
    } finally {
      limiter.shutdown();
    }
  }

  @Test
  void shouldGraduallyIncreaseLimitWhenHealthy() throws Exception {
    AdaptiveRateLimiter limiter =
        new AdaptiveRateLimiter(
            4,
            10,
            100,
            java.util.concurrent.TimeUnit
                .MILLISECONDS); // Start from 4 → expect 2 → expect increase to 4
    try {
      // Force throttling to reduce limit
      for (int i = 0; i < 5; i++) {
        try {
          limiter.check("test", "op");
        } catch (RateLimitException e) {
          // Expected to throttle and reduce limit
        }
      }

      // Wait for health check to increase limit
      Thread.sleep(150); // Wait slightly more than 100 milliseconds

      // Allow up to 4 requests again (limit should've increased to 4)
      for (int i = 0; i < 4; i++) {
        limiter.check("test", "op");
      }

      // 5th should throw exception again
      assertThrows(RateLimitException.class, () -> limiter.check("test", "op"));
    } finally {
      limiter.shutdown();
    }
  }
}
