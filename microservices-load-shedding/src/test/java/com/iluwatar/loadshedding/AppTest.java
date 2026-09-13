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
package com.iluwatar.loadshedding;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class AppTest {

  private static final Duration SHORT = Duration.ofMillis(10);
  private static final Duration GENEROUS = Duration.ofSeconds(1);
  private static final Request REQUEST = new Request("r", Priority.NORMAL, "place order");

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
  void paymentProviderProcessesImmediatelyWhenHealthy() {
    var entered = new Semaphore(0);
    var handler =
        App.simulatedPaymentProvider(
            new AtomicBoolean(false), new CountDownLatch(1), entered, GENEROUS);
    assertEquals("processed place order", handler.handle(REQUEST));
    assertEquals(0, entered.availablePermits());
  }

  @Test
  void paymentProviderResumesOnceRecovered() {
    var entered = new Semaphore(0);
    var recovered = new CountDownLatch(0);
    var handler =
        App.simulatedPaymentProvider(new AtomicBoolean(true), recovered, entered, GENEROUS);
    assertEquals("processed place order", handler.handle(REQUEST));
    assertEquals(1, entered.availablePermits());
  }

  @Test
  void paymentProviderFailsWhenRecoveryTimesOut() {
    var handler =
        App.simulatedPaymentProvider(
            new AtomicBoolean(true), new CountDownLatch(1), new Semaphore(0), SHORT);
    var exception = assertThrows(IllegalStateException.class, () -> handler.handle(REQUEST));
    assertEquals("payment provider never recovered", exception.getMessage());
  }

  @Test
  void paymentProviderRestoresInterruptFlagWhenInterruptedWhileSlow() {
    var entered = new Semaphore(0);
    var handler =
        App.simulatedPaymentProvider(
            new AtomicBoolean(true), new CountDownLatch(1), entered, GENEROUS);
    Thread.currentThread().interrupt();
    var exception = assertThrows(IllegalStateException.class, () -> handler.handle(REQUEST));
    assertInstanceOf(InterruptedException.class, exception.getCause());
    assertTrue(Thread.interrupted());
    assertEquals(1, entered.availablePermits());
  }

  @Test
  void awaitEnteredReturnsOncePermitsAreAvailable() {
    var entered = new Semaphore(2);
    assertDoesNotThrow(() -> App.awaitEntered(entered, 2, GENEROUS));
    assertEquals(0, entered.availablePermits());
  }

  @Test
  void awaitEnteredFailsWhenNobodyEnters() {
    var entered = new Semaphore(0);
    var exception =
        assertThrows(IllegalStateException.class, () -> App.awaitEntered(entered, 1, SHORT));
    assertEquals("requests did not enter the service in time", exception.getMessage());
  }

  @Test
  void resultReturnsCompletedResponse() throws InterruptedException {
    var response = Response.accepted(REQUEST, "done");
    assertEquals(response, App.result(CompletableFuture.completedFuture(response), GENEROUS));
  }

  @Test
  void resultWrapsFailedWorker() {
    var failed = CompletableFuture.<Response>failedFuture(new RuntimeException("boom"));
    var exception = assertThrows(IllegalStateException.class, () -> App.result(failed, GENEROUS));
    assertEquals("worker failed", exception.getMessage());
    assertInstanceOf(RuntimeException.class, exception.getCause().getCause());
  }

  @Test
  void resultWrapsWorkerThatNeverFinishes() {
    var pending = new CompletableFuture<Response>();
    var exception = assertThrows(IllegalStateException.class, () -> App.result(pending, SHORT));
    assertEquals("worker failed", exception.getMessage());
    assertInstanceOf(TimeoutException.class, exception.getCause());
  }

  @Test
  void shutdownWaitsForIdleExecutor() throws InterruptedException {
    var executor = Executors.newSingleThreadExecutor();
    App.shutdown(executor, GENEROUS);
    assertTrue(executor.isTerminated());
  }

  @Test
  void shutdownForcesStopWhenWorkersIgnoreTheTimeout() throws InterruptedException {
    var executor = Executors.newSingleThreadExecutor();
    var started = new CountDownLatch(1);
    var interrupted = new CountDownLatch(1);
    var release = new CountDownLatch(1);
    executor.execute(
        () -> {
          started.countDown();
          while (release.getCount() > 0) {
            try {
              release.await();
            } catch (InterruptedException e) {
              // A stubborn worker that keeps going despite the interrupt.
              interrupted.countDown();
            }
          }
        });
    assertTrue(started.await(1, TimeUnit.SECONDS));
    App.shutdown(executor, SHORT);
    assertTrue(executor.isShutdown());
    assertTrue(interrupted.await(1, TimeUnit.SECONDS));
    release.countDown();
    assertTrue(executor.awaitTermination(1, TimeUnit.SECONDS));
  }
}
