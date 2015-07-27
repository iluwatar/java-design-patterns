package com.iluwatar.halfsynchalfasync;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This represents the Queuing and Synchronous layer of Half-Sync/Half-Async pattern. 
 * The {@link ThreadPoolExecutor} plays role of both Queuing layer as well as Synchronous layer
 * of the pattern, where incoming tasks are queued if no worker is available.
 */
public class SynchronousLayer {
	
	/*
	 * This is the synchronous layer where background threads execute the work.
	 */
	private ExecutorService service;
	
	/**
	 * Creates synchronous layer which uses queuing layer to wait for incoming tasks to execute.
	 */
	public SynchronousLayer(QueuingLayer queuingLayer) {
		service = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, queuingLayer.incomingQueue);
	}
	/**
	 * Submit new work for backgrounds threads to compute 
	 * @return the result after executing the work
	 */
	public <T> void execute(final AsyncTask<T> work) {
		work.preExecute();
		
		service.submit(new FutureTask<T>(work) {
			@Override
			protected void done() {
				super.done();
				try {
					/* called in context of background thread. There is other variant possible
					 * where result is posted back and sits in the queue of caller thread which
					 * then picks it up for processing. An example of such a system is Android OS,
					 * where the UI elements can only be updated using UI thread. So result must be
					 * posted back in UI thread.
					 */
					work.onResult(get());
				} catch (InterruptedException e) {
					// should not occur
				} catch (ExecutionException e) {
					work.onError(e.getCause());
				}
			}
		});
	}
}
