package com.iluwatar.promise;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.iluwatar.async.method.invocation.ThreadAsyncExecutor;

/**
 * Tests Promise class.
 */
public class PromiseTest {

  private ThreadAsyncExecutor executor;
  private Promise<Integer> promise;
  @Rule public ExpectedException exception = ExpectedException.none();

  @Before
  public void setUp() {
    executor = new ThreadAsyncExecutor();
    promise = new Promise<>();
  }

  @Test
  public void promiseIsFulfilledWithTheResultantValueOfExecutingTheTask() 
      throws InterruptedException, ExecutionException {
    promise.fulfillInAsync(new NumberCrunchingTask(), executor);

    // await fulfillment
    promise.await();

    assertEquals(NumberCrunchingTask.CRUNCHED_NUMBER, promise.getValue());
  }

  @Test
  public void dependentPromiseIsFulfilledAfterTheConsumerConsumesTheResultOfThisPromise() 
      throws InterruptedException, ExecutionException {
    Promise<Void> dependentPromise = promise
        .fulfillInAsync(new NumberCrunchingTask(), executor)
        .then(value -> {
          assertEquals(NumberCrunchingTask.CRUNCHED_NUMBER, value);
        });


    // await fulfillment
    dependentPromise.await();
  }

  @Test
  public void dependentPromiseIsFulfilledWithAnExceptionIfConsumerThrowsAnException() 
      throws InterruptedException, ExecutionException {
    Promise<Void> dependentPromise = promise
        .fulfillInAsync(new NumberCrunchingTask(), executor)
        .then(new Consumer<Integer>() {

          @Override
          public void accept(Integer t) {
            throw new RuntimeException("Barf!");
          }
        });


    // await fulfillment
    dependentPromise.await();

    exception.expect(ExecutionException.class);

    dependentPromise.getValue();
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


    // await fulfillment
    dependentPromise.await();

    assertEquals(String.valueOf(NumberCrunchingTask.CRUNCHED_NUMBER), dependentPromise.getValue());
  }
  
  @Test
  public void dependentPromiseIsFulfilledWithAnExceptionIfTheFunctionThrowsException() 
      throws InterruptedException, ExecutionException {
    Promise<String> dependentPromise = promise
        .fulfillInAsync(new NumberCrunchingTask(), executor)
        .then(new Function<Integer, String>() {

          @Override
          public String apply(Integer t) {
            throw new RuntimeException("Barf!");
          }
        });

    // await fulfillment
    dependentPromise.await();

    exception.expect(ExecutionException.class);

    dependentPromise.getValue();
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
