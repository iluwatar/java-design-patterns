package com.iluwatar.async.method.invocation;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * 
 * AsyncExecutor interface
 *
 */
public interface AsyncExecutor {

	/**
	 * Starts processing of an async task. Returns immediately with async result.
	 *
	 * @param task task to be executed asynchronously
	 * @return async result for the task
	 */
	<T> AsyncResult<T> startProcess(Callable<T> task);

	/**
	 * Starts processing of an async task. Returns immediately with async result. Executes callback
	 * when the task is completed.
	 *
	 * @param task task to be executed asynchronously
	 * @param callback callback to be executed on task completion
	 * @return async result for the task
	 */
	<T> AsyncResult<T> startProcess(Callable<T> task, AsyncCallback<T> callback);

	/**
	 * Ends processing of an async task. Blocks the current thread if necessary and returns the
	 * evaluated value of the completed task.
	 *
	 * @param asyncResult async result of a task
	 * @return evaluated value of the completed task
	 * @throws ExecutionException if execution has failed, containing the root cause
	 * @throws InterruptedException if the execution is interrupted
	 */
	<T> T endProcess(AsyncResult<T> asyncResult) throws ExecutionException, InterruptedException;

}
