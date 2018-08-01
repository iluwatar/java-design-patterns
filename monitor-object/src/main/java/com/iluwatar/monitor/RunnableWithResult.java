package com.iluwatar.monitor;

/**
 * The runnable interface to be executed by a thread which returns a result.
 */
public interface RunnableWithResult<T> {
  public T run();
}
