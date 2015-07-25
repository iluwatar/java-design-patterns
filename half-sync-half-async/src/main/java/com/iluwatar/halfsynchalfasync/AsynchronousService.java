package com.iluwatar.halfsynchalfasync;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * This is the asynchronous layer which does not block when a new request arrives. It just passes
 * the request to the synchronous layer which consists of a queue i.e. a {@link BlockingQueue} and
 * a pool of threads i.e. {@link ThreadPoolExecutor}. Out of this pool of threads one of the thread
 * picks up the task and executes it in background and the result is posted back to the caller via 
 * {@link Future}.
 */
public abstract class AsynchronousService<I, O> {
	
	/*
	 * This is the synchronous layer to which request to do work is submitted.
	 */
	private SynchronousLayer syncLayer = new SynchronousLayer();
	
	/**
	 * Computes arithmetic sum for n
	 * 
	 * @return future representing arithmetic sum of n
	 */
	public Future<O> execute(final I input) {
		/*
		 * This is the key part of this pattern where the caller thread does not block until
		 * the result of work is computed but is delegated to the synchronous layer which
		 * computes the task in background. This is useful if caller thread is an UI thread, 
		 * which MUST remain responsive to user inputs.
		 */
		return syncLayer.submit(new Callable<O>() {

			@Override
			public O call() throws Exception {
				return doInBackground(input);
			}

		});
	}
	
	/**
	 * This method is called in context of background thread where the implementation should compute
	 * and return the result for input.
	 * 
	 * @return computed result
	 */
	protected abstract O doInBackground(I input);
}
