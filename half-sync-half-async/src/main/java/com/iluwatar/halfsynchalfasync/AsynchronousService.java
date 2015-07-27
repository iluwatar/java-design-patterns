package com.iluwatar.halfsynchalfasync;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * This is the asynchronous layer which does not block when a new request arrives. It just passes
 * the request to the synchronous layer which consists of a queue i.e. a {@link BlockingQueue} and
 * a pool of threads i.e. {@link ThreadPoolExecutor}. Out of this pool of threads one of the thread
 * picks up the task and executes it in background and the result is posted back to the caller via 
 * {@link Future}.
 */
public class AsynchronousService {
	
	/*
	 * This is the synchronous layer to which request to do work is delegated.
	 */
	private SynchronousLayer syncLayer;
	
	public AsynchronousService(QueuingLayer queuingLayer) {
		this.syncLayer = new SynchronousLayer(queuingLayer);
	}
	
	/**
	 * 
	 */
	public void execute(final AsyncTask<?> task) {
		/*
		 * This is the key part of this pattern where the caller thread does not block until
		 * the result of work is computed but is delegated to the synchronous layer which
		 * computes the task in background. This is useful if caller thread is an UI thread, 
		 * which MUST remain responsive to user inputs.
		 */
		syncLayer.execute(task);
	}
}
