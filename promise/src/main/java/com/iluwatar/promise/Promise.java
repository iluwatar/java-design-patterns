package com.iluwatar.promise;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Function;

import com.iluwatar.async.method.invocation.AsyncExecutor;
import com.iluwatar.async.method.invocation.internal.CompletableResult;

/**
 * Implements the promise pattern.
 * @param <T> type of result.
 */
public class Promise<T> extends CompletableResult<T> {

  private Runnable fulfillmentAction;

  /**
   * Creates a promise that will be fulfilled in future.
   */
  public Promise() {
    super(null);
  }

  /**
   * Fulfills the promise with the provided value.
   * @param value the fulfilled value that can be accessed using {@link #getValue()}.
   */
  @Override
  public void setValue(T value) {
    super.setValue(value);
    postComplete();
  }

  /**
   * Fulfills the promise with exception due to error in execution.
   * @param exception the exception will be wrapped in {@link ExecutionException}
   *        when accessing the value using {@link #getValue()}.
   */
  @Override
  public void setException(Exception exception) {
    super.setException(exception);
    postComplete();
  }

  void postComplete() {
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
  public Promise<T> fulfillInAsync(final Callable<T> task, AsyncExecutor executor) {
    executor.startProcess(new Callable<Void>() {

      @Override
      public Void call() throws Exception {
        setValue(task.call());
        return null;
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
    fulfillmentAction = new FunctionAction<V>(this, dest, func);
    return dest;
  }

  private class ConsumeAction implements Runnable {

    private Promise<T> current;
    private Promise<Void> dest;
    private Consumer<? super T> action;

    public ConsumeAction(Promise<T> current, Promise<Void> dest, Consumer<? super T> action) {
      this.current = current;
      this.dest = dest;
      this.action = action;
    }

    @Override
    public void run() {
      try {
        action.accept(current.getValue());
        dest.setValue(null);
      } catch (Throwable e) {
        dest.setException((Exception) e.getCause());
      }
    }
  }

  private class FunctionAction<V> implements Runnable {

    private Promise<T> current;
    private Promise<V> dest;
    private Function<? super T, V> func;

    public FunctionAction(Promise<T> current, Promise<V> dest, Function<? super T, V> func) {
      this.current = current;
      this.dest = dest;
      this.func = func;
    }

    @Override
    public void run() {
      try {
        V result = func.apply(current.getValue());
        dest.setValue(result);
      } catch (Throwable e) {
        dest.setException((Exception) e.getCause());
      }
    }
  }
}
