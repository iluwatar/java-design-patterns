/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Implements the promise pattern.
 * 
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

  private void postFulfillment() {
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

    private final Promise<T> src;
    private final Promise<Void> dest;
    private final Consumer<? super T> action;

    private ConsumeAction(Promise<T> src, Promise<Void> dest, Consumer<? super T> action) {
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

    private final Promise<T> src;
    private final Promise<V> dest;
    private final Function<? super T, V> func;

    private TransformAction(Promise<T> src, Promise<V> dest, Function<? super T, V> func) {
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