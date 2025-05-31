package com.iluwatar.rate.limiting.pattern;

import org.junit.jupiter.api.Test;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class ConcurrencyTests {
  @Test
  void tokenBucketShouldHandleConcurrentRequests() throws Exception {
    int threadCount = 10;
    int requestLimit = 5;
    RateLimiter limiter = new TokenBucketRateLimiter(requestLimit, requestLimit);
    ExecutorService executor = Executors.newFixedThreadPool(threadCount);
    CountDownLatch latch = new CountDownLatch(threadCount);

    AtomicInteger successCount = new AtomicInteger();
    AtomicInteger failureCount = new AtomicInteger();

    for (int i = 0; i < threadCount; i++) {
      executor.submit(() -> {
        try {
          limiter.check("test", "op");
          successCount.incrementAndGet();
        } catch (RateLimitException e) {
          failureCount.incrementAndGet();
        }
        latch.countDown();
      });
    }

    latch.await();
    assertEquals(requestLimit, successCount.get());
    assertEquals(threadCount - requestLimit, failureCount.get());
  }

  @Test
  void adaptiveLimiterShouldAdjustUnderLoad() throws Exception {
    AdaptiveRateLimiter limiter = new AdaptiveRateLimiter(10, 20);
    ExecutorService executor = Executors.newFixedThreadPool(20);

    // Flood with requests to trigger throttling
    for (int i = 0; i < 30; i++) {
      executor.submit(() -> {
        try {
          limiter.check("test", "op");
        } catch (RateLimitException ignored) {}
      });
    }

    Thread.sleep(15000); // Wait for adjustment

    // Verify new limit is in effect
    int allowed = 0;
    for (int i = 0; i < 20; i++) {
      try {
        limiter.check("test", "op");
        allowed++;
      } catch (RateLimitException ignored) {}
    }

    assertTrue(allowed > 5 && allowed < 15); // Should be between initial and max
  }
}