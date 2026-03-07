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

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class FixedWindowRateLimiterTest extends RateLimiterTest {
  @Override
  protected RateLimiter createRateLimiter(int limit, long windowMillis) {
    return new FixedWindowRateLimiter(limit, windowMillis / 1000);
  }

  @Test
  void shouldResetCounterAfterWindow() throws Exception {
    FixedWindowRateLimiter limiter =
        new FixedWindowRateLimiter(1, 1); // 1 request per 1 second window

    // First request should pass
    limiter.check("test", "op");

    // Second request in same window should be throttled
    assertThrows(RateLimitException.class, () -> limiter.check("test", "op"));

    // Wait a bit more than 1 second to ensure window resets
    TimeUnit.MILLISECONDS.sleep(1100);

    // After window reset, this should pass again
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
