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

import org.junit.jupiter.api.Test;

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
    assertThrows(
        RateLimitException.class,
        () -> {
          for (int i = 0; i < 6; i++) { // New limit should be 5 (10/2)
            limiter.check("test", "op");
          }
        });
  }

  @Test
  void shouldGraduallyIncreaseLimitWhenHealthy() throws Exception {
    AdaptiveRateLimiter limiter =
        new AdaptiveRateLimiter(4, 10); // Start from 4 → expect 2 → expect increase to 4

    // Force throttling to reduce limit
    for (int i = 0; i < 5; i++) {
      try {
        limiter.check("test", "op");
      } catch (RateLimitException e) {
        // Expected to throttle and reduce limit
      }
    }

    // Wait for health check to increase limit
    Thread.sleep(11000); // Wait slightly more than 10 seconds

    // Allow up to 4 requests again (limit should've increased to 4)
    for (int i = 0; i < 4; i++) {
      limiter.check("test", "op");
    }

    // 5th should throw exception again
    assertThrows(RateLimitException.class, () -> limiter.check("test", "op"));
  }
}
