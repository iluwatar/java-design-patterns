package com.iluwatar.halfsynchalfasync;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This represents the Queuing and Synchronous layer of Half-Sync/Half-Async pattern. 
 * The incoming requests are queued and then picked up by the background threads for execution.
 */
public class SynchronousLayer {
	
	/*
	 * This is the queuing layer where incoming work is queued 
	 */
	private LinkedBlockingQueue<Runnable> tasks = new LinkedBlockingQueue<Runnable>();
	/*
	 * This is the synchronous layer where background threads execute the work
	 */
	private ExecutorService service = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, tasks);
	
	/**
	 * Submit new work for backgrounds threads to compute 
	 * @return the result after executing the work
	 */
	public <T> Future<T> submit(Callable<T> work) {
		return service.submit(work);
	}
}
