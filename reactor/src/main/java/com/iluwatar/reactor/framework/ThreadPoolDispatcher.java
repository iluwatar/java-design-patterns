package com.iluwatar.reactor.framework;

import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * An implementation that uses a pool of worker threads to dispatch the events. This provides
 * better scalability as the application specific processing is not performed in the context
 * of I/O (reactor) thread.
 * 
 * @author npathai
 *
 */
public class ThreadPoolDispatcher extends SameThreadDispatcher {

	private ExecutorService executorService;

	/**
	 * Creates a pooled dispatcher with tunable pool size.
	 * 
	 * @param poolSize number of pooled threads
	 */
	public ThreadPoolDispatcher(int poolSize) {
		this.executorService = Executors.newFixedThreadPool(poolSize);
	}

	/**
	 * Submits the work of dispatching the read event to worker pool, where it gets picked
	 * up by worker threads. 
	 * <br/>
	 * Note that this is a non-blocking call and returns immediately. It is not guaranteed 
	 * that the event has been handled by associated handler.
	 */
	@Override
	public void onChannelReadEvent(AbstractNioChannel channel, Object readObject, SelectionKey key) {
		executorService.execute(() -> 
			ThreadPoolDispatcher.super.onChannelReadEvent(channel, readObject, key));
	}

	/**
	 * Stops the pool of workers.
	 */
	@Override
	public void stop() {
		executorService.shutdownNow();
		try {
			executorService.awaitTermination(4, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
