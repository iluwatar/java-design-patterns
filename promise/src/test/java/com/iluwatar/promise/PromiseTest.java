package com.iluwatar.promise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests Promise class.
 */
public class PromiseTest {

  private Executor executor;
  private Promise<Integer> promise;
  @Rule public ExpectedException exception = ExpectedException.none();

  @Before
  public void setUp() {
    executor = Executors.newSingleThreadExecutor();
    promise = new Promise<>();
  }

  @Test
  public void promiseIsFulfilledWithTheResultantValueOfExecutingTheTask() 
      throws InterruptedException, ExecutionException {
    promise.fulfillInAsync(new NumberCrunchingTask(), executor);

    assertEquals(NumberCrunchingTask.CRUNCHED_NUMBER, promise.get());
    assertTrue(promise.isDone());
    assertFalse(promise.isCancelled());
  }
  
  @Test
  public void promiseIsFulfilledWithAnExceptionIfTaskThrowsAnException() 
      throws InterruptedException, ExecutionException, TimeoutException {
    testWaitingForeverForPromiseToBeFulfilled();
    testWaitingSomeTimeForPromiseToBeFulfilled();
  }

  private void testWaitingForeverForPromiseToBeFulfilled() throws InterruptedException, TimeoutException {
    Promise<Integer> promise = new Promise<>();
    promise.fulfillInAsync(new Callable<Integer>() {

      @Override
      public Integer call() throws Exception {
        throw new RuntimeException("Barf!");
      }
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
  
  private void testWaitingSomeTimeForPromiseToBeFulfilled() 
      throws InterruptedException, TimeoutException {
    Promise<Integer> promise = new Promise<>();
    promise.fulfillInAsync(new Callable<Integer>() {

      @Override
      public Integer call() throws Exception {
        throw new RuntimeException("Barf!");
      }
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
  public void dependentPromiseIsFulfilledAfterTheConsumerConsumesTheResultOfThisPromise() 
      throws InterruptedException, ExecutionException {
    Promise<Void> dependentPromise = promise
        .fulfillInAsync(new NumberCrunchingTask(), executor)
        .then(value -> {
          assertEquals(NumberCrunchingTask.CRUNCHED_NUMBER, value);
        });


    dependentPromise.get();
    assertTrue(dependentPromise.isDone());
    assertFalse(dependentPromise.isCancelled());
  }

  @Test
  public void dependentPromiseIsFulfilledWithAnExceptionIfConsumerThrowsAnException() 
      throws InterruptedException, ExecutionException, TimeoutException {
    Promise<Void> dependentPromise = promise
        .fulfillInAsync(new NumberCrunchingTask(), executor)
        .then(new Consumer<Integer>() {

          @Override
          public void accept(Integer t) {
            throw new RuntimeException("Barf!");
          }
        });

    try {
      dependentPromise.get();
      fail("Fetching dependent promise should result in exception if the action threw an exception");
    } catch (ExecutionException ex) {
      assertTrue(promise.isDone());
      assertFalse(promise.isCancelled());
    }
    
    try {
      dependentPromise.get(1000, TimeUnit.SECONDS);
      fail("Fetching dependent promise should result in exception if the action threw an exception");
    } catch (ExecutionException ex) {
      assertTrue(promise.isDone());
      assertFalse(promise.isCancelled());
    }
  }

  @Test
  public void dependentPromiseIsFulfilledAfterTheFunctionTransformsTheResultOfThisPromise() 
      throws InterruptedException, ExecutionException {
    Promise<String> dependentPromise = promise
        .fulfillInAsync(new NumberCrunchingTask(), executor)
        .then(value -> {
          assertEquals(NumberCrunchingTask.CRUNCHED_NUMBER, value); 
          return String.valueOf(value);
        });


    assertEquals(String.valueOf(NumberCrunchingTask.CRUNCHED_NUMBER), dependentPromise.get());
    assertTrue(dependentPromise.isDone());
    assertFalse(dependentPromise.isCancelled());
  }
  
  @Test
  public void dependentPromiseIsFulfilledWithAnExceptionIfTheFunctionThrowsException() 
      throws InterruptedException, ExecutionException, TimeoutException {
    Promise<String> dependentPromise = promise
        .fulfillInAsync(new NumberCrunchingTask(), executor)
        .then(new Function<Integer, String>() {

          @Override
          public String apply(Integer t) {
            throw new RuntimeException("Barf!");
          }
        });

    try {
      dependentPromise.get();
      fail("Fetching dependent promise should result in exception if the function threw an exception");
    } catch (ExecutionException ex) {
      assertTrue(promise.isDone());
      assertFalse(promise.isCancelled());
    }
    
    try {
      dependentPromise.get(1000, TimeUnit.SECONDS);
      fail("Fetching dependent promise should result in exception if the function threw an exception");
    } catch (ExecutionException ex) {
      assertTrue(promise.isDone());
      assertFalse(promise.isCancelled());
    }
  }
  
  @Test
  public void fetchingAnAlreadyFulfilledPromiseReturnsTheFulfilledValueImmediately() 
      throws InterruptedException, ExecutionException, TimeoutException {
    Promise<Integer> promise = new Promise<>();
    promise.fulfill(NumberCrunchingTask.CRUNCHED_NUMBER);
    
    promise.get(1000, TimeUnit.SECONDS);
  }

  private static class NumberCrunchingTask implements Callable<Integer> {

    private static final Integer CRUNCHED_NUMBER = Integer.MAX_VALUE;

    @Override
    public Integer call() throws Exception {
      // Do number crunching
      Thread.sleep(1000);
      return CRUNCHED_NUMBER;
    }
  }
}
