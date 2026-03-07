/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.rate.limiting.pattern;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

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
      executor.submit(
          () -> {
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
      executor.submit(
          () -> {
            try {
              limiter.check("test", "op");
            } catch (RateLimitException ignored) {
            }
          });
    }

    Thread.sleep(15000); // Wait for adjustment

    // Verify new limit is in effect
    int allowed = 0;
    for (int i = 0; i < 20; i++) {
      try {
        limiter.check("test", "op");
        allowed++;
      } catch (RateLimitException ignored) {
      }
    }

    assertTrue(allowed > 5 && allowed < 15); // Should be between initial and max
  }
}
