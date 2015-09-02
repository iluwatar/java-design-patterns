package com.iluwatar.reactor;

import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDispatcher extends SameThreadDispatcher {

	private ExecutorService executorService;

	public ThreadPoolDispatcher(int poolSize) {
		this.executorService = Executors.newFixedThreadPool(poolSize);
	}
	
	@Override
	public void onChannelReadEvent(AbstractNioChannel channel, Object readObject, SelectionKey key) {
		executorService.execute(new Runnable() {
			
			@Override
			public void run() {
				ThreadPoolDispatcher.super.onChannelReadEvent(channel, readObject, key);
			}
		});
	}
	
	@Override
	public void stop() {
		executorService.shutdownNow();
		try {
			executorService.awaitTermination(1000, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
