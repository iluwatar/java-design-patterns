package com.iluwatar.monitor;

/**
 * The runnable interface to be executed by a thread which returns a result.
 *  
 * @param <T> Parameter to specify the return type
 */
public interface RunnableWithResult<T> {
  T run();
}
