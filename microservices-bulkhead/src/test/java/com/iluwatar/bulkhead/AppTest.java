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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class AppTest {

  private static final long TIMEOUT_SECONDS = 5;

  @AfterEach
  void clearInterruptFlag() {
    Thread.interrupted();
  }

  @Test
  void shouldLaunchApp() {
    assertDoesNotThrow(() -> App.main(new String[] {}));
  }

  @Test
  void shouldBeInstantiable() {
    assertNotNull(new App(), "App should be instantiable");
  }

  @Test
  void callInventoryShouldReportFailureOfTheRemoteCall() {
    try (var bulkhead = new Bulkhead("inventory", 1, 1)) {
      RemoteService failing =
          request -> {
            throw new IllegalStateException("inventory system down");
          };

      assertDoesNotThrow(() -> App.callInventory(bulkhead, failing, "order-1"));
    }
  }

  @Test
  void callInventoryShouldReportRejectionWhenBulkheadIsFull() throws Exception {
    var started = new CountDownLatch(1);
    var gate = new CountDownLatch(1);
    try (var bulkhead = new Bulkhead("inventory", 1, 0)) {
      var blocking = bulkhead.submit(blockOn(started, gate));
      assertTrue(started.await(TIMEOUT_SECONDS, TimeUnit.SECONDS));

      assertDoesNotThrow(() -> App.callInventory(bulkhead, new InventoryService(), "order-1"));

      gate.countDown();
      blocking.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }
  }

  @Test
  void callInventoryShouldKeepInterruptFlagWhenWaitingIsInterrupted() throws Exception {
    var started = new CountDownLatch(1);
    var gate = new CountDownLatch(1);
    try (var bulkhead = new Bulkhead("inventory", 1, 1)) {
      RemoteService slow =
          request -> {
            started.countDown();
            awaitQuietly(gate);
            return "late";
          };
      Thread.currentThread().interrupt();

      assertDoesNotThrow(() -> App.callInventory(bulkhead, slow, "order-1"));

      assertTrue(Thread.interrupted());
      gate.countDown();
      assertTrue(started.await(TIMEOUT_SECONDS, TimeUnit.SECONDS));
    }
  }

  @Test
  void awaitAllShouldReportFailedCalls() {
    try (var bulkhead = new Bulkhead("payment", 1, 1)) {
      Future<String> failed =
          bulkhead.submit(
              () -> {
                throw new IllegalStateException("payment provider down");
              });

      assertDoesNotThrow(() -> App.awaitAll(List.of(failed)));
    }
  }

  @Test
  void awaitAllShouldStopAndKeepInterruptFlagWhenWaitingIsInterrupted() throws Exception {
    var started = new CountDownLatch(1);
    var gate = new CountDownLatch(1);
    try (var bulkhead = new Bulkhead("payment", 1, 1)) {
      var blocking = bulkhead.submit(blockOn(started, gate));
      assertTrue(started.await(TIMEOUT_SECONDS, TimeUnit.SECONDS));
      Thread.currentThread().interrupt();

      assertDoesNotThrow(() -> App.awaitAll(List.of(blocking)));

      assertTrue(Thread.interrupted());
      gate.countDown();
      blocking.get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }
  }

  private static Callable<String> blockOn(CountDownLatch started, CountDownLatch gate) {
    return () -> {
      started.countDown();
      awaitQuietly(gate);
      return "done";
    };
  }

  private static void awaitQuietly(CountDownLatch gate) {
    try {
      gate.await(TIMEOUT_SECONDS, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
