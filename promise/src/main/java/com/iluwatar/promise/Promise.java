package com.iluwatar.promise;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Implements the promise pattern.
 * @param <T> type of result.
 */
public class Promise<T> extends PromiseSupport<T> {

  private Runnable fulfillmentAction;

  /**
   * Creates a promise that will be fulfilled in future.
   */
  public Promise() {
  }

  /**
   * Fulfills the promise with the provided value.
   * @param value the fulfilled value that can be accessed using {@link #get()}.
   */
  @Override
  public void fulfill(T value) {
    super.fulfill(value);
    postFulfillment();
  }

  /**
   * Fulfills the promise with exception due to error in execution.
   * @param exception the exception will be wrapped in {@link ExecutionException}
   *        when accessing the value using {@link #get()}.
   */
  @Override
  public void fulfillExceptionally(Exception exception) {
    super.fulfillExceptionally(exception);
    postFulfillment();
  }

  void postFulfillment() {
    if (fulfillmentAction == null) {
      return;
    }
    fulfillmentAction.run();
  }

  /**
   * Executes the task using the executor in other thread and fulfills the promise returned
   * once the task completes either successfully or with an exception.
   * 
   * @param task the task that will provide the value to fulfill the promise.
   * @param executor the executor in which the task should be run.
   * @return a promise that represents the result of running the task provided.
   */
  public Promise<T> fulfillInAsync(final Callable<T> task, Executor executor) {
    executor.execute(() -> {
      try {
        fulfill(task.call());
      } catch (Exception e) {
        fulfillExceptionally(e);
      }
    });
    return this;
  }

  /**
   * Returns a new promise that, when this promise is fulfilled normally, is fulfilled with 
   * result of this promise as argument to the action provided.
   * @param action action to be executed.
   * @return a new promise.
   */
  public Promise<Void> then(Consumer<? super T> action) {
    Promise<Void> dest = new Promise<>();
    fulfillmentAction = new ConsumeAction(this, dest, action);
    return dest;
  }

  /**
   * Returns a new promise that, when this promise is fulfilled normally, is fulfilled with 
   * result of this promise as argument to the function provided.
   * @param func function to be executed.
   * @return a new promise.
   */
  public <V> Promise<V> then(Function<? super T, V> func) {
    Promise<V> dest = new Promise<>();
    fulfillmentAction = new TransformAction<V>(this, dest, func);
    return dest;
  }

  /**
   * A consume action provides the action, the value from source promise and fulfills the
   * destination promise.
   */
  private class ConsumeAction implements Runnable {

    private Promise<T> src;
    private Promise<Void> dest;
    private Consumer<? super T> action;

    ConsumeAction(Promise<T> src, Promise<Void> dest, Consumer<? super T> action) {
      this.src = src;
      this.dest = dest;
      this.action = action;
    }

    @Override
    public void run() {
      try {
        action.accept(src.get());
        dest.fulfill(null);
      } catch (Throwable e) {
        dest.fulfillExceptionally((Exception) e.getCause());
      }
    }
  }

  /**
   * A function action provides transformation function, value from source promise and fulfills the
   * destination promise with the transformed value.
   */
  private class TransformAction<V> implements Runnable {

    private Promise<T> src;
    private Promise<V> dest;
    private Function<? super T, V> func;

    TransformAction(Promise<T> src, Promise<V> dest, Function<? super T, V> func) {
      this.src = src;
      this.dest = dest;
      this.func = func;
    }

    @Override
    public void run() {
      try {
        V result = func.apply(src.get());
        dest.fulfill(result);
      } catch (Throwable e) {
        dest.fulfillExceptionally((Exception) e.getCause());
      }
    }
  }
}


/**
 * A really simplified implementation of future that allows completing it successfully with a value 
 * or exceptionally with an exception.
 */
class PromiseSupport<T> implements Future<T> {

  static final int RUNNING = 1;
  static final int FAILED = 2;
  static final int COMPLETED = 3;

  final Object lock;

  volatile int state = RUNNING;
  T value;
  Exception exception;

  PromiseSupport() {
    this.lock = new Object();
  }

  void fulfill(T value) {
    this.value = value;
    this.state = COMPLETED;
    synchronized (lock) {
      lock.notifyAll();
    }
  }

  void fulfillExceptionally(Exception exception) {
    this.exception = exception;
    this.state = FAILED;
    synchronized (lock) {
      lock.notifyAll();
    }
  }

  @Override
  public boolean cancel(boolean mayInterruptIfRunning) {
    return false;
  }

  @Override
  public boolean isCancelled() {
    return false;
  }

  @Override
  public boolean isDone() {
    return state > RUNNING;
  }

  @Override
  public T get() throws InterruptedException, ExecutionException {
    if (state == COMPLETED) {
      return value;
    } else if (state == FAILED) {
      throw new ExecutionException(exception);
    } else {
      synchronized (lock) {
        lock.wait();
        if (state == COMPLETED) {
          return value;
        } else {
          throw new ExecutionException(exception);
        }
      }
    }
  }

  @Override
  public T get(long timeout, TimeUnit unit)
      throws InterruptedException, ExecutionException, TimeoutException {
    if (state == COMPLETED) {
      return value;
    } else if (state == FAILED) {
      throw new ExecutionException(exception);
    } else {
      synchronized (lock) {
        lock.wait(unit.toMillis(timeout));
        if (state == COMPLETED) {
          return value;
        } else if (state == FAILED) {
          throw new ExecutionException(exception);
        } else {
          throw new TimeoutException();
        }
      }
    }
  }
}