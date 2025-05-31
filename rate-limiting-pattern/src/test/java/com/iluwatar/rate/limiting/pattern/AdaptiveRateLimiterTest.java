package com.iluwatar.rate.limiting.pattern;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdaptiveRateLimiterTest {
  @Test
  void shouldDecreaseLimitWhenThrottled() throws Exception {
    AdaptiveRateLimiter limiter = new AdaptiveRateLimiter(10, 20);

    // Exceed initial limit
    for (int i = 0; i < 11; i++) {
      try {
        limiter.check("test", "op");
      } catch (RateLimitException e) {
        // Expected after 10 requests
      }
    }

    // Verify limit was reduced
    assertThrows(RateLimitException.class, () -> {
      for (int i = 0; i < 6; i++) { // New limit should be 5 (10/2)
        limiter.check("test", "op");
      }
    });
  }

  @Test
  void shouldGraduallyIncreaseLimitWhenHealthy() throws Exception {
    AdaptiveRateLimiter limiter = new AdaptiveRateLimiter(10, 20);

    // Force limit reduction
    for (int i = 0; i < 11; i++) {
      try {
        limiter.check("test", "op");
      } catch (RateLimitException e) {}
    }

    // Wait for health check (adjustment happens every 10 seconds)
    Thread.sleep(10500);

    // Verify limit was increased
    for (int i = 0; i < 8; i++) { // New limit should be 10 (5 + 5)
      limiter.check("test", "op");
    }
    assertThrows(RateLimitException.class, () -> limiter.check("test", "op"));
  }
}