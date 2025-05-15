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
package com.iluwatar.threadpoolexecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class FrontDeskServiceTest {

  /**
   * Tests that the constructor correctly sets the number of employees (threads). This verifies the
   * basic initialization of the thread pool.
   */
  @Test
  void testConstructorSetsCorrectNumberOfEmployees() {
    int expectedEmployees = 3;

    FrontDeskService frontDesk = new FrontDeskService(expectedEmployees);

    assertEquals(expectedEmployees, frontDesk.getNumberOfEmployees());
  }

  /**
   * Tests that the submitGuestCheckIn method returns a non-null Future object. This verifies the
   * basic task submission functionality.
   */
  @Test
  void testSubmitGuestCheckInReturnsNonNullFuture() {
    FrontDeskService frontDesk = new FrontDeskService(1);

    Runnable task =
        () -> {
          // Task that completes quickly
        };

    Future<?> future = frontDesk.submitGuestCheckIn(task);

    assertNotNull(future);
  }

  /**
   * Tests that the submitVipGuestCheckIn method returns a non-null Future object. This verifies
   * that tasks with return values can be submitted correctly.
   */
  @Test
  void testSubmitVipGuestCheckInReturnsNonNullFuture() {
    FrontDeskService frontDesk = new FrontDeskService(1);
    Callable<String> task = () -> "VIP Check-in complete";

    Future<String> future = frontDesk.submitVipGuestCheckIn(task);

    assertNotNull(future);
  }

  /**
   * Tests that the shutdown and awaitTermination methods work correctly. This verifies the basic
   * shutdown functionality of the thread pool.
   */
  @Test
  void testShutdownAndAwaitTermination() throws InterruptedException {
    FrontDeskService frontDesk = new FrontDeskService(2);
    CountDownLatch taskLatch = new CountDownLatch(1);

    Runnable task = taskLatch::countDown;

    frontDesk.submitGuestCheckIn(task);
    frontDesk.shutdown();
    boolean terminated = frontDesk.awaitTermination(1, TimeUnit.SECONDS);

    assertTrue(terminated);
    assertTrue(taskLatch.await(100, TimeUnit.MILLISECONDS));
  }

  /**
   * Tests the thread pool's behavior under load with multiple tasks. This verifies that the thread
   * pool limits concurrent execution to the number of threads, all submitted tasks are eventually
   * completed, and threads are reused for multiple tasks.
   */
  @Test
  void testMultipleTasksUnderLoad() throws InterruptedException {
    FrontDeskService frontDesk = new FrontDeskService(2);
    int taskCount = 10;
    CountDownLatch tasksCompletedLatch = new CountDownLatch(taskCount);
    AtomicInteger concurrentTasks = new AtomicInteger(0);
    AtomicInteger maxConcurrentTasks = new AtomicInteger(0);

    for (int i = 0; i < taskCount; i++) {
      frontDesk.submitGuestCheckIn(
          () -> {
            try {
              int current = concurrentTasks.incrementAndGet();
              maxConcurrentTasks.updateAndGet(max -> Math.max(max, current));

              Thread.sleep(100);

              concurrentTasks.decrementAndGet();
              tasksCompletedLatch.countDown();
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
          });
    }

    boolean allTasksCompleted = tasksCompletedLatch.await(2, TimeUnit.SECONDS);

    frontDesk.shutdown();
    frontDesk.awaitTermination(1, TimeUnit.SECONDS);

    assertTrue(allTasksCompleted);
    assertEquals(2, maxConcurrentTasks.get());
    assertEquals(0, concurrentTasks.get());
  }

  /**
   * Tests proper shutdown behavior under load. This verifies that after shutdown no new tasks are
   * accepted, all previously submitted tasks are completed, and the executor terminates properly
   * after all tasks complete.
   */
  @Test
  void testProperShutdownUnderLoad() throws InterruptedException {
    FrontDeskService frontDesk = new FrontDeskService(2);
    int taskCount = 5;
    CountDownLatch startedTasksLatch = new CountDownLatch(2);
    CountDownLatch tasksCompletionLatch = new CountDownLatch(taskCount);

    for (int i = 0; i < taskCount; i++) {
      frontDesk.submitGuestCheckIn(
          () -> {
            try {
              startedTasksLatch.countDown();
              Thread.sleep(100);
              tasksCompletionLatch.countDown();
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
          });
    }

    assertTrue(startedTasksLatch.await(1, TimeUnit.SECONDS));

    frontDesk.shutdown();

    assertThrows(
        RejectedExecutionException.class,
        () -> {
          frontDesk.submitGuestCheckIn(() -> {});
        });

    boolean allTasksCompleted = tasksCompletionLatch.await(2, TimeUnit.SECONDS);

    boolean terminated = frontDesk.awaitTermination(1, TimeUnit.SECONDS);

    assertTrue(allTasksCompleted);
    assertTrue(terminated);
  }

  /**
   * Tests concurrent execution of different task types (regular and VIP). This verifies that both
   * Runnable and Callable tasks can be processed concurrently, all tasks complete successfully, and
   * Callable tasks return their results correctly.
   */
  @Test
  void testConcurrentRegularAndVipTasks() throws Exception {
    FrontDeskService frontDesk = new FrontDeskService(3);
    int regularTaskCount = 4;
    int vipTaskCount = 3;
    CountDownLatch allTasksLatch = new CountDownLatch(regularTaskCount + vipTaskCount);

    List<Future<?>> regularResults = new ArrayList<>();
    for (int i = 0; i < regularTaskCount; i++) {
      Future<?> result =
          frontDesk.submitGuestCheckIn(
              () -> {
                try {
                  Thread.sleep(50);
                  allTasksLatch.countDown();
                } catch (InterruptedException e) {
                  Thread.currentThread().interrupt();
                }
              });
      regularResults.add(result);
    }

    List<Future<String>> vipResults = new ArrayList<>();
    for (int i = 0; i < vipTaskCount; i++) {
      final int guestNum = i;
      Future<String> result =
          frontDesk.submitVipGuestCheckIn(
              () -> {
                Thread.sleep(25);
                allTasksLatch.countDown();
                return "VIP-" + guestNum + " checked in";
              });
      vipResults.add(result);
    }

    boolean allCompleted = allTasksLatch.await(2, TimeUnit.SECONDS);

    frontDesk.shutdown();
    frontDesk.awaitTermination(1, TimeUnit.SECONDS);

    assertTrue(allCompleted);

    for (Future<?> result : regularResults) {
      assertTrue(result.isDone());
    }

    for (int i = 0; i < vipTaskCount; i++) {
      Future<String> result = vipResults.get(i);
      assertTrue(result.isDone());
      assertEquals("VIP-" + i + " checked in", result.get());
    }
  }
}
