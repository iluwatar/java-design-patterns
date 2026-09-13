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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class LoadShedderTest {

  private static final int CAPACITY = 5;
  private static final int LOW_LIMIT = 3;
  private static final int RESERVE = 1;

  private final LoadShedder shedder = new LoadShedder(CAPACITY, LOW_LIMIT, RESERVE);

  private static Request request(Priority priority) {
    return new Request("req-" + priority, priority, "test");
  }

  private void fill(int count) {
    for (var i = 0; i < count; i++) {
      shedder.acquire(request(Priority.CRITICAL));
    }
  }

  @Test
  void admitsEveryPriorityBelowLowPriorityLimit() {
    fill(LOW_LIMIT - 1);
    assertDoesNotThrow(() -> shedder.acquire(request(Priority.LOW)));
    assertEquals(LOW_LIMIT, shedder.getInFlight());
    assertEquals(LOW_LIMIT, shedder.getAccepted());
    assertEquals(0, shedder.getTotalShed());
  }

  @Test
  void shedsLowPriorityFirst() {
    fill(LOW_LIMIT);
    assertThrows(LoadShedException.class, () -> shedder.acquire(request(Priority.LOW)));
    assertDoesNotThrow(() -> shedder.acquire(request(Priority.NORMAL)));
    assertEquals(LOW_LIMIT + 1, shedder.getInFlight());
    assertEquals(1, shedder.getShed(Priority.LOW));
    assertEquals(0, shedder.getShed(Priority.NORMAL));
  }

  @Test
  void keepsReserveForCriticalRequests() {
    fill(CAPACITY - RESERVE);
    assertThrows(LoadShedException.class, () -> shedder.acquire(request(Priority.NORMAL)));
    assertDoesNotThrow(() -> shedder.acquire(request(Priority.CRITICAL)));
    assertEquals(CAPACITY, shedder.getInFlight());
    assertEquals(1, shedder.getShed(Priority.NORMAL));
    assertEquals(0, shedder.getShed(Priority.CRITICAL));
  }

  @Test
  void shedsCriticalRequestsAtHardCapacity() {
    fill(CAPACITY);
    assertThrows(LoadShedException.class, () -> shedder.acquire(request(Priority.CRITICAL)));
    assertEquals(CAPACITY, shedder.getInFlight());
    assertEquals(1, shedder.getShed(Priority.CRITICAL));
  }

  @Test
  void releaseFreesCapacity() {
    fill(LOW_LIMIT);
    assertThrows(LoadShedException.class, () -> shedder.acquire(request(Priority.LOW)));
    shedder.release();
    assertDoesNotThrow(() -> shedder.acquire(request(Priority.LOW)));
    assertEquals(LOW_LIMIT, shedder.getInFlight());
  }

  @Test
  void unmatchedReleaseDoesNotDriveInFlightNegative() {
    shedder.release();
    assertEquals(0, shedder.getInFlight());
    fill(CAPACITY);
    assertThrows(LoadShedException.class, () -> shedder.acquire(request(Priority.CRITICAL)));
  }

  @Test
  void acquireReturnsInFlightCountIncludingTheAdmittedRequest() {
    assertEquals(1, shedder.acquire(request(Priority.NORMAL)));
    assertEquals(2, shedder.acquire(request(Priority.NORMAL)));
  }

  @Test
  void countsShedRequestsPerPriority() {
    fill(CAPACITY);
    assertThrows(LoadShedException.class, () -> shedder.acquire(request(Priority.LOW)));
    assertThrows(LoadShedException.class, () -> shedder.acquire(request(Priority.LOW)));
    assertThrows(LoadShedException.class, () -> shedder.acquire(request(Priority.NORMAL)));
    assertThrows(LoadShedException.class, () -> shedder.acquire(request(Priority.CRITICAL)));
    assertEquals(CAPACITY, shedder.getAccepted());
    assertEquals(2, shedder.getShed(Priority.LOW));
    assertEquals(1, shedder.getShed(Priority.NORMAL));
    assertEquals(1, shedder.getShed(Priority.CRITICAL));
    assertEquals(4, shedder.getTotalShed());
  }

  @Test
  void exceptionDescribesTheDecision() {
    fill(LOW_LIMIT);
    var request = new Request("low-42", Priority.LOW, "test");
    var exception = assertThrows(LoadShedException.class, () -> shedder.acquire(request));
    assertEquals("low-42", exception.getRequestId());
    assertEquals(Priority.LOW, exception.getPriority());
    assertTrue(exception.getMessage().contains("low-42"));
    assertTrue(exception.getMessage().contains("LOW"));
  }

  @Test
  void neverExceedsCapacityUnderConcurrentAdmission() throws InterruptedException {
    var callers = 50;
    var start = new CountDownLatch(1);
    var done = new CountDownLatch(callers);
    var admitted = new AtomicInteger();
    var executor = Executors.newFixedThreadPool(callers);
    try {
      for (var i = 0; i < callers; i++) {
        executor.execute(
            () -> {
              try {
                start.await();
                shedder.acquire(request(Priority.CRITICAL));
                admitted.incrementAndGet();
              } catch (LoadShedException expected) {
                // shed
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
              } finally {
                done.countDown();
              }
            });
      }
      start.countDown();
      assertTrue(done.await(10, TimeUnit.SECONDS));
    } finally {
      executor.shutdownNow();
    }
    assertEquals(CAPACITY, admitted.get());
    assertEquals(CAPACITY, shedder.getInFlight());
    assertEquals(callers - CAPACITY, shedder.getShed(Priority.CRITICAL));
  }

  @Test
  void rejectsInvalidConfiguration() {
    // maxInFlight must be positive
    assertThrows(IllegalArgumentException.class, () -> new LoadShedder(0, 1, 0));
    // criticalReserve must be between 0 and maxInFlight - 1
    assertThrows(IllegalArgumentException.class, () -> new LoadShedder(5, 3, -1));
    assertThrows(IllegalArgumentException.class, () -> new LoadShedder(5, 3, 5));
    // lowPriorityLimit must be between 1 and maxInFlight - criticalReserve
    assertThrows(IllegalArgumentException.class, () -> new LoadShedder(5, 0, 1));
    assertThrows(IllegalArgumentException.class, () -> new LoadShedder(5, 5, 1));
  }

  @Test
  void acceptsBoundaryConfiguration() {
    assertDoesNotThrow(() -> new LoadShedder(5, 4, 1));
    var noReserve = new LoadShedder(5, 5, 0);
    fillWith(noReserve, Priority.LOW, 5);
    assertEquals(5, noReserve.getInFlight());
    assertThrows(LoadShedException.class, () -> noReserve.acquire(request(Priority.CRITICAL)));
  }

  private static void fillWith(LoadShedder target, Priority priority, int count) {
    for (var i = 0; i < count; i++) {
      target.acquire(request(priority));
    }
  }
}
