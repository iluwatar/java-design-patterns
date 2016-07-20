package com.iluwatar.promise;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

public class ThreadAsyncExecutor implements PromiseAsyncExecutor {
  
  /** Index for thread naming */
  private final AtomicInteger idx = new AtomicInteger(0);
  
  @Override
  public <T> ListenableAsyncResult<T> execute(Callable<T> task) {
    Promise<T> promise = new Promise<>();
    new Thread(() -> {
      try {
        promise.setValue(task.call());
        promise.postComplete();
      } catch (Exception ex) {
        promise.setException(ex);
      }
    } , "executor-" + idx.incrementAndGet()).start();
    return promise;
  }
  
  // TODO there is scope of extending the completable future from async method invocation project. Do that.
  private class Promise<T> implements ListenableAsyncResult<T> {

    static final int RUNNING = 1;
    static final int FAILED = 2;
    static final int COMPLETED = 3;

    final Object lock;
    volatile int state = RUNNING;
    T value;
    Exception exception;
    Runnable fulfilmentAction;

    public Promise() {
      this.lock = new Object();
    }

    void postComplete() {
      fulfilmentAction.run();
    }

    /**
     * Sets the value from successful execution and executes callback if available. Notifies any thread waiting for
     * completion.
     *
     * @param value
     *          value of the evaluated task
     */
    public void setValue(T value) {
      this.value = value;
      this.state = COMPLETED;
      synchronized (lock) {
        lock.notifyAll();
      }
    }

    /**
     * Sets the exception from failed execution and executes callback if available. Notifies any thread waiting for
     * completion.
     *
     * @param exception
     *          exception of the failed task
     */
    public void setException(Exception exception) {
      this.exception = exception;
      this.state = FAILED;
      synchronized (lock) {
        lock.notifyAll();
      }
    }

    @Override
    public boolean isCompleted() {
      return state > RUNNING;
    }

    @Override
    public T getValue() throws ExecutionException {
      if (state == COMPLETED) {
        return value;
      } else if (state == FAILED) {
        throw new ExecutionException(exception);
      } else {
        throw new IllegalStateException("Execution not completed yet");
      }
    }

    @Override
    public void await() throws InterruptedException {
      synchronized (lock) {
        if (!isCompleted()) {
          lock.wait();
        }
      }
    }

    @Override
    public ListenableAsyncResult<Void> then(Consumer<? super T> action) {
      Promise<Void> dest = new Promise<>();
      fulfilmentAction = new ConsumeAction(this, dest, action);
      return dest;
    }

    @Override
    public <V> ListenableAsyncResult<V> then(Function<? super T, V> func) {
      Promise<V> dest = new Promise<>();
      fulfilmentAction = new FunctionAction<V>(this, dest, func);
      return dest;
    }

    @Override
    public ListenableAsyncResult<T> error(Consumer<? extends Throwable> action) {
      return null;
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
        } catch (ExecutionException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        dest.postComplete();
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
        } catch (ExecutionException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        dest.postComplete();
      }
    }
  }
 }