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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.health.check.RetryConfig;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.support.RetryTemplate;

/**
 * Unit tests for the {@link RetryConfig} class.
 *
 */
@SpringBootTest(classes = RetryConfig.class)
class RetryConfigTest {

  /** Injected RetryTemplate instance. */
  @Autowired private RetryTemplate retryTemplate;

  /**
   * Tests that the retry template retries three times with a two-second delay.
   *
   * <p>Verifies that the retryable operation is executed three times before throwing an exception,
   * and that the total elapsed time for the retries is at least four seconds.
   */
  @Test
  void shouldRetryThreeTimesWithTwoSecondDelay() {
    AtomicInteger attempts = new AtomicInteger();
    Runnable retryableOperation =
        () -> {
          attempts.incrementAndGet();
          throw new RuntimeException("Test exception for retry");
        };

    long startTime = System.currentTimeMillis();
    try {
      retryTemplate.execute(
          context -> {
            retryableOperation.run();
            return null;
          });
    } catch (Exception e) {
      // Expected exception
    }
    long endTime = System.currentTimeMillis();

    assertEquals(3, attempts.get(), "Should have retried three times");
    assertTrue(
        (endTime - startTime) >= 4000,
        "Should have waited at least 4 seconds in total for backoff");
  }
}
