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
package com.iluwatar.bulkhead;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

class BulkheadTest {

  private static final long TIMEOUT_SECONDS = 5;

  @Test
  void shouldExecuteCallWhenCapacityIsAvailable() throws Exception {
    try (var bulkhead = new Bulkhead("test", 1, 1)) {
      var future = bulkhead.submit(() -> "ok");

      assertEquals("ok", future.get(TIMEOUT_SECONDS, TimeUnit.SECONDS));
      assertEquals(0, bulkhead.getRejectedCalls());
    }
  }

  @Test
  void shouldRejectCallWhenThreadsAndQueueAreFull() throws Exception {
    var started = new CountDownLatch(1);
    var gate = new CountDownLatch(1);
    try (var bulkhead = new Bulkhead("payment", 1, 1)) {
      var running = bulkhead.submit(blockOn(started, gate));
      assertTrue(started.await(TIMEOUT_SECONDS, TimeUnit.SECONDS));
      var queued = bulkhead.submit(() -> "queued");

      var exception =
          assertThrows(BulkheadFullException.class, () -> bulkhead.submit(() -> "rejected"));

      assertEquals("payment", exception.getBulkheadName());
      assertEquals("Bulkhead 'payment' is full, call rejected", exception.getMessage());
      assertEquals(1, bulkhead.getActiveCalls());
      assertEquals(1, bulkhead.getQueuedCalls());
      assertEquals(1, bulkhead.getRejectedCalls());

      gate.countDown();
      assertEquals("running", running.get(TIMEOUT_SECONDS, TimeUnit.SECONDS));
      assertEquals("queued", queued.get(TIMEOUT_SECONDS, TimeUnit.SECONDS));
    }
  }

  @Test
  void shouldAcceptCallsAgainAfterCapacityIsReleased() throws Exception {
    var started = new CountDownLatch(1);
    var gate = new CountDownLatch(1);
    try (var bulkhead = new Bulkhead("payment", 1, 1)) {
      var running = bulkhead.submit(blockOn(started, gate));
      assertTrue(started.await(TIMEOUT_SECONDS, TimeUnit.SECONDS));
      var queued = bulkhead.submit(() -> "queued");
      assertThrows(BulkheadFullException.class, () -> bulkhead.submit(() -> "rejected"));

      gate.countDown();
      assertEquals("running", running.get(TIMEOUT_SECONDS, TimeUnit.SECONDS));
      assertEquals("queued", queued.get(TIMEOUT_SECONDS, TimeUnit.SECONDS));

      // The queue is empty again, so the next call is accepted even if the worker thread has not
      // yet returned to polling the queue. A bulkhead without a queue would race here, because the
      // hand-off to the single thread only succeeds once that thread is idle.
      var afterRelease = bulkhead.submit(() -> "accepted");
      assertEquals("accepted", afterRelease.get(TIMEOUT_SECONDS, TimeUnit.SECONDS));
      assertEquals(1, bulkhead.getRejectedCalls());
    }
  }

  @Test
  void shouldPropagateFailureOfTheCallThroughTheFuture() {
    try (var bulkhead = new Bulkhead("test", 1, 1)) {
      var future =
          bulkhead.submit(
              () -> {
                throw new IllegalStateException("downstream failure");
              });

      var exception =
          assertThrows(
              ExecutionException.class, () -> future.get(TIMEOUT_SECONDS, TimeUnit.SECONDS));

      assertInstanceOf(IllegalStateException.class, exception.getCause());
      assertEquals(0, bulkhead.getRejectedCalls());
    }
  }

  @Test
  void shouldRunCallsOnThreadsNamedAfterTheBulkhead() throws Exception {
    try (var bulkhead = new Bulkhead("payment", 1, 1)) {
      var threadName = bulkhead.submit(() -> Thread.currentThread().getName());

      assertTrue(threadName.get(TIMEOUT_SECONDS, TimeUnit.SECONDS).startsWith("bulkhead-payment-"));
    }
  }

  @Test
  void shouldRejectSubmissionAfterShutdown() {
    var bulkhead = new Bulkhead("test", 1, 1);
    bulkhead.shutdown();

    assertThrows(IllegalStateException.class, () -> bulkhead.submit(() -> "late"));
  }

  @Test
  void shouldCancelQueuedCallsOnShutdown() throws Exception {
    var started = new CountDownLatch(1);
    var gate = new CountDownLatch(1);
    Future<String> queued;
    try (var bulkhead = new Bulkhead("payment", 1, 1)) {
      bulkhead.submit(blockOn(started, gate));
      assertTrue(started.await(TIMEOUT_SECONDS, TimeUnit.SECONDS));
      queued = bulkhead.submit(() -> "queued");
    }

    // Without the cancellation the queued task would simply be dropped and this call would block
    // until the timeout expires, because nobody ever completes its future.
    assertTrue(queued.isCancelled());
    assertThrows(CancellationException.class, () -> queued.get(1, TimeUnit.SECONDS));
  }

  @Test
  void shouldRejectInvalidConfiguration() {
    assertThrows(IllegalArgumentException.class, () -> new Bulkhead("test", 0, 1));
    assertThrows(IllegalArgumentException.class, () -> new Bulkhead("test", 1, -1));
  }

  @Test
  void shouldExposeConfiguration() {
    try (var bulkhead = new Bulkhead("inventory", 3, 4)) {
      assertEquals("inventory", bulkhead.getName());
      assertEquals(3, bulkhead.getMaxConcurrentCalls());
      assertEquals(4, bulkhead.getMaxQueueSize());
    }
  }

  private static Callable<String> blockOn(CountDownLatch started, CountDownLatch gate) {
    return () -> {
      started.countDown();
      gate.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);
      return "running";
    };
  }
}
