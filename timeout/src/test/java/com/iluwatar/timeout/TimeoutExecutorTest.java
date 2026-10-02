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
package com.iluwatar.timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class TimeoutExecutorTest {

  private static final TimeoutPolicy GENEROUS = TimeoutPolicy.of("generous", 5_000);
  private static final TimeoutPolicy STRICT = TimeoutPolicy.of("strict", 50);

  private final TimeoutExecutor executor = new TimeoutExecutor();

  @AfterEach
  void tearDown() {
    executor.close();
  }

  @Test
  void returnsResultWhenCallCompletesWithinLimit() {
    var result = executor.execute(GENEROUS, () -> "fresh", () -> "fallback");

    assertEquals("fresh", result);
    assertEquals(0, executor.metrics().timeoutCount(GENEROUS.serviceName()));
  }

  @Test
  void returnsFallbackAndCancelsCallWhenLimitExceeded() throws InterruptedException {
    var interrupted = new CountDownLatch(1);
    Callable<String> hangingCall =
        () -> {
          try {
            Thread.sleep(60_000);
          } catch (InterruptedException e) {
            interrupted.countDown();
            throw e;
          }
          return "too late";
        };

    var result = executor.execute(STRICT, hangingCall, () -> "fallback");

    assertEquals("fallback", result);
    assertTrue(interrupted.await(5, TimeUnit.SECONDS), "slow call should have been interrupted");
    assertEquals(1, executor.metrics().timeoutCount(STRICT.serviceName()));
  }

  @Test
  void propagatesServiceFailureInsteadOfFallingBack() {
    var failure = new IllegalStateException("backend down");

    var thrown =
        assertThrows(
            ServiceCallException.class,
            () ->
                executor.execute(
                    GENEROUS,
                    () -> {
                      throw failure;
                    },
                    () -> "fallback"));

    assertSame(failure, thrown.getCause());
    assertEquals(0, executor.metrics().timeoutCount(GENEROUS.serviceName()));
  }

  @Test
  void appliesDifferentLimitsPerService() {
    Callable<String> slowCall =
        () -> {
          Thread.sleep(200);
          return "slow but done";
        };

    var withinBudget = executor.execute(GENEROUS, slowCall, () -> "fallback");
    var overBudget = executor.execute(STRICT, slowCall, () -> "fallback");

    assertEquals("slow but done", withinBudget);
    assertEquals("fallback", overBudget);
  }

  @Test
  void countsTimeoutsPerService() {
    var other = TimeoutPolicy.of("other", 50);
    Callable<String> slowCall =
        () -> {
          Thread.sleep(60_000);
          return "never";
        };

    executor.execute(STRICT, slowCall, () -> "fallback");
    executor.execute(STRICT, slowCall, () -> "fallback");
    executor.execute(other, slowCall, () -> "fallback");

    assertEquals(Map.of("strict", 2, "other", 1), executor.metrics().snapshot());
  }

  @Test
  void keepsSnapshotSortedByServiceName() {
    var metrics = executor.metrics();
    metrics.recordTimeout("payments");
    metrics.recordTimeout("analytics");
    metrics.recordTimeout("catalog");

    assertEquals(
        List.of("analytics", "catalog", "payments"), List.copyOf(metrics.snapshot().keySet()));
  }

  @Test
  void propagatesInterruptionOfTheCaller() {
    Callable<String> slowCall =
        () -> {
          Thread.sleep(60_000);
          return "never";
        };

    Thread.currentThread().interrupt();
    var thrown =
        assertThrows(
            ServiceCallException.class,
            () -> executor.execute(GENEROUS, slowCall, () -> "fallback"));

    assertTrue(Thread.interrupted(), "interrupt flag should be preserved for the caller");
    assertInstanceOf(InterruptedException.class, thrown.getCause());
    assertEquals(0, executor.metrics().timeoutCount(GENEROUS.serviceName()));
  }

  @Test
  void rejectsCallsAfterClose() {
    executor.close();

    var thrown =
        assertThrows(
            ServiceCallException.class,
            () -> executor.execute(GENEROUS, () -> "ignored", () -> "fallback"));

    assertInstanceOf(RejectedExecutionException.class, thrown.getCause());
    assertEquals(0, executor.metrics().timeoutCount(GENEROUS.serviceName()));
  }
}
