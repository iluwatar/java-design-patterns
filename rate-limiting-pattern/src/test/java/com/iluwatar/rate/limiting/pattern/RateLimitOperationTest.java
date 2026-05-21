package com.iluwatar.rate.limiting.pattern;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

interface RateLimitOperationTest<T> {

  RateLimitOperation<T> createOperation(RateLimiter limiter);

  @Test
  default void shouldThrowWhenRateLimited() {
    RateLimiter limiter = new TokenBucketRateLimiter(0, 0); // Always throttled
    RateLimitOperation<T> operation = createOperation(limiter);
    assertThrows(RateLimitException.class, operation::execute);
  }

  // ✅ No @Test here, just a helper method
  default void shouldExecuteWhenUnderLimit(RateLimitOperation<T> operation) throws Exception {
    assertNotNull(operation.execute());
  }
}
