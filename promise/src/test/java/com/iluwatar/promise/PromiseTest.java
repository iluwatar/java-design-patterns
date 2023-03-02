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
package com.iluwatar.promise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests Promise class.
 */
class PromiseTest {

  private Executor executor;
  private Promise<Integer> promise;

  @BeforeEach
  void setUp() {
    executor = Executors.newSingleThreadExecutor();
    promise = new Promise<>();
  }

  @Test
  void promiseIsFulfilledWithTheResultantValueOfExecutingTheTask()
      throws InterruptedException, ExecutionException {
    promise.fulfillInAsync(new NumberCrunchingTask(), executor);

    assertEquals(NumberCrunchingTask.CRUNCHED_NUMBER, promise.get());
    assertTrue(promise.isDone());
    assertFalse(promise.isCancelled());
  }

  @Test
  void promiseIsFulfilledWithAnExceptionIfTaskThrowsAnException()
      throws InterruptedException {
    testWaitingForeverForPromiseToBeFulfilled();
    testWaitingSomeTimeForPromiseToBeFulfilled();
  }

  private void testWaitingForeverForPromiseToBeFulfilled() throws InterruptedException {
    var promise = new Promise<Integer>();
    promise.fulfillInAsync(() -> {
      throw new RuntimeException("Barf!");
    }, executor);

    try {
      promise.get();
      fail("Fetching promise should result in exception if the task threw an exception");
    } catch (ExecutionException ex) {
      assertTrue(promise.isDone());
      assertFalse(promise.isCancelled());
    }

    try {
      promise.get(1000, TimeUnit.SECONDS);
      fail("Fetching promise should result in exception if the task threw an exception");
    } catch (ExecutionException ex) {
      assertTrue(promise.isDone());
      assertFalse(promise.isCancelled());
    }
  }

  private void testWaitingSomeTimeForPromiseToBeFulfilled() throws InterruptedException {
    var promise = new Promise<Integer>();
    promise.fulfillInAsync(() -> {
      throw new RuntimeException("Barf!");
    }, executor);

    try {
      promise.get(1000, TimeUnit.SECONDS);
      fail("Fetching promise should result in exception if the task threw an exception");
    } catch (ExecutionException ex) {
      assertTrue(promise.isDone());
      assertFalse(promise.isCancelled());
    }

    try {
      promise.get();
      fail("Fetching promise should result in exception if the task threw an exception");
    } catch (ExecutionException ex) {
      assertTrue(promise.isDone());
      assertFalse(promise.isCancelled());
    }

  }

  @Test
  void dependentPromiseIsFulfilledAfterTheConsumerConsumesTheResultOfThisPromise()
      throws InterruptedException, ExecutionException {
    var dependentPromise = promise
        .fulfillInAsync(new NumberCrunchingTask(), executor)
        .thenAccept(value -> assertEquals(NumberCrunchingTask.CRUNCHED_NUMBER, value));

    dependentPromise.get();
    assertTrue(dependentPromise.isDone());
    assertFalse(dependentPromise.isCancelled());
  }

  @Test
  void dependentPromiseIsFulfilledWithAnExceptionIfConsumerThrowsAnException()
      throws InterruptedException {
    var dependentPromise = promise
        .fulfillInAsync(new NumberCrunchingTask(), executor)
        .thenAccept(value -> {
          throw new RuntimeException("Barf!");
        });

    try {
      dependentPromise.get();
      fail("Fetching dependent promise should result in exception "
          + "if the action threw an exception");
    } catch (ExecutionException ex) {
      assertTrue(promise.isDone());
      assertFalse(promise.isCancelled());
    }

    try {
      dependentPromise.get(1000, TimeUnit.SECONDS);
      fail("Fetching dependent promise should result in exception "
          + "if the action threw an exception");
    } catch (ExecutionException ex) {
      assertTrue(promise.isDone());
      assertFalse(promise.isCancelled());
    }
  }

  @Test
  void dependentPromiseIsFulfilledAfterTheFunctionTransformsTheResultOfThisPromise()
      throws InterruptedException, ExecutionException {
    var dependentPromise = promise
        .fulfillInAsync(new NumberCrunchingTask(), executor)
        .thenApply(value -> {
          assertEquals(NumberCrunchingTask.CRUNCHED_NUMBER, value);
          return String.valueOf(value);
        });


    assertEquals(String.valueOf(NumberCrunchingTask.CRUNCHED_NUMBER), dependentPromise.get());
    assertTrue(dependentPromise.isDone());
    assertFalse(dependentPromise.isCancelled());
  }

  @Test
  void dependentPromiseIsFulfilledWithAnExceptionIfTheFunctionThrowsException()
      throws InterruptedException {
    var dependentPromise = promise
        .fulfillInAsync(new NumberCrunchingTask(), executor)
        .thenApply(value -> {
          throw new RuntimeException("Barf!");
        });

    try {
      dependentPromise.get();
      fail("Fetching dependent promise should result in exception "
          + "if the function threw an exception");
    } catch (ExecutionException ex) {
      assertTrue(promise.isDone());
      assertFalse(promise.isCancelled());
    }

    try {
      dependentPromise.get(1000, TimeUnit.SECONDS);
      fail("Fetching dependent promise should result in exception "
          + "if the function threw an exception");
    } catch (ExecutionException ex) {
      assertTrue(promise.isDone());
      assertFalse(promise.isCancelled());
    }
  }

  @Test
  void fetchingAnAlreadyFulfilledPromiseReturnsTheFulfilledValueImmediately()
      throws ExecutionException {
    var promise = new Promise<Integer>();
    promise.fulfill(NumberCrunchingTask.CRUNCHED_NUMBER);

    Integer result = promise.get(1000, TimeUnit.SECONDS);
    assertEquals(NumberCrunchingTask.CRUNCHED_NUMBER, result);
  }

  @SuppressWarnings("unchecked")
  @Test
  void exceptionHandlerIsCalledWhenPromiseIsFulfilledExceptionally() {
    var promise = new Promise<>();
    var exceptionHandler = mock(Consumer.class);
    promise.onError(exceptionHandler);

    var exception = new Exception("barf!");
    promise.fulfillExceptionally(exception);

    verify(exceptionHandler).accept(eq(exception));
  }

  private static class NumberCrunchingTask implements Callable<Integer> {

    private static final Integer CRUNCHED_NUMBER = Integer.MAX_VALUE;

    @Override
    public Integer call() throws Exception {
      // Do number crunching
      Thread.sleep(100);
      return CRUNCHED_NUMBER;
    }
  }
}
