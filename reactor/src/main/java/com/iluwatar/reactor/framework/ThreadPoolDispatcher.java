package com.iluwatar.reactor.framework;

import java.nio.channels.SelectionKey;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * An implementation that uses a pool of worker threads to dispatch the events. This provides better
 * scalability as the application specific processing is not performed in the context of I/O
 * (reactor) thread.
 */
public class ThreadPoolDispatcher implements Dispatcher {

  private final ExecutorService executorService;

  /**
   * Creates a pooled dispatcher with tunable pool size.
   * 
   * @param poolSize number of pooled threads
   */
  public ThreadPoolDispatcher(int poolSize) {
    this.executorService = Executors.newFixedThreadPool(poolSize);
  }

  /**
   * Submits the work of dispatching the read event to worker pool, where it gets picked up by
   * worker threads. <br/>
   * Note that this is a non-blocking call and returns immediately. It is not guaranteed that the
   * event has been handled by associated handler.
   */
  @Override
  public void onChannelReadEvent(AbstractNioChannel channel, Object readObject, SelectionKey key) {
    executorService.execute(() -> channel.getHandler().handleChannelRead(channel, readObject, key));
  }

  /**
   * Stops the pool of workers.
   * 
   * @throws InterruptedException if interrupted while stopping pool of workers.
   */
  @Override
  public void stop() throws InterruptedException {
    executorService.shutdown();
    executorService.awaitTermination(4, TimeUnit.SECONDS);
  }
}
