/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
