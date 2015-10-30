package com.iluwatar.async.method.invocation;

import java.util.concurrent.ExecutionException;

/**
 * 
 * AsyncResult interface
 *
 * @param <T>
 */
public interface AsyncResult<T> {

	/**
	 * Status of the async task execution.
	 *
	 * @return <code>true</code> if execution is completed or failed
	 */
	boolean isCompleted();

	/**
	 * Gets the value of completed async task.
	 *
	 * @return evaluated value or throws ExecutionException if execution has failed
	 * @throws ExecutionException if execution has failed, containing the root cause
	 * @throws IllegalStateException if execution is not completed
	 */
	T getValue() throws ExecutionException;

	/**
	 * Blocks the current thread until the async task is completed.
	 *
	 * @throws InterruptedException if the execution is interrupted
	 */
	void await() throws InterruptedException;
}
