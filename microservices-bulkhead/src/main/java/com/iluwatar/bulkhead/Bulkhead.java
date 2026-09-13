/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.bulkhead;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Isolates the calls made to one downstream dependency inside a dedicated, bounded thread pool.
 *
 * <p>The pool has a fixed number of worker threads and a bounded waiting queue. When both are full
 * the bulkhead does not block the caller and it cannot borrow threads from anywhere else: the call
 * fails fast with a {@link BulkheadFullException}. Each dependency gets its own instance, so a slow
 * or hanging dependency can only exhaust its own compartment while the rest of the system keeps
 * serving requests.
 */
@Slf4j
public class Bulkhead implements AutoCloseable {

  @Getter private final String name;
  @Getter private final int maxConcurrentCalls;
  @Getter private final int maxQueueSize;
  private final ThreadPoolExecutor executor;
  private final AtomicLong rejectedCalls = new AtomicLong();

  /**
   * Creates a bulkhead with its own thread pool.
   *
   * @param name name of the compartment, used in logs and worker thread names
   * @param maxConcurrentCalls number of calls that may run at the same time
   * @param maxQueueSize number of calls that may wait for a free thread; zero disables queueing
   */
  public Bulkhead(String name, int maxConcurrentCalls, int maxQueueSize) {
    if (maxConcurrentCalls < 1) {
      throw new IllegalArgumentException("maxConcurrentCalls must be at least 1");
    }
    if (maxQueueSize < 0) {
      throw new IllegalArgumentException("maxQueueSize must not be negative");
    }
    this.name = name;
    this.maxConcurrentCalls = maxConcurrentCalls;
    this.maxQueueSize = maxQueueSize;
    BlockingQueue<Runnable> queue =
        maxQueueSize == 0 ? new SynchronousQueue<>() : new ArrayBlockingQueue<>(maxQueueSize);
    var threadCounter = new AtomicInteger();
    this.executor =
        new ThreadPoolExecutor(
            maxConcurrentCalls,
            maxConcurrentCalls,
            0L,
            TimeUnit.MILLISECONDS,
            queue,
            runnable ->
                new Thread(runnable, "bulkhead-" + name + "-" + threadCounter.incrementAndGet()),
            new ThreadPoolExecutor.AbortPolicy());
  }

  /**
   * Submits a call to this compartment.
   *
   * @param task the call to execute
   * @param <T> type of the result
   * @return a future that completes with the result of the call
   * @throws BulkheadFullException if every thread is busy and the queue is full
   * @throws IllegalStateException if the bulkhead has been shut down
   */
  public <T> Future<T> submit(Callable<T> task) {
    if (executor.isShutdown()) {
      throw new IllegalStateException("Bulkhead '" + name + "' is shut down");
    }
    try {
      var future = executor.submit(task);
      LOGGER.debug(
          "Bulkhead '{}' accepted call ({} active, {} queued)",
          name,
          executor.getActiveCount(),
          executor.getQueue().size());
      return future;
    } catch (RejectedExecutionException e) {
      if (executor.isShutdown()) {
        throw new IllegalStateException("Bulkhead '" + name + "' is shut down");
      }
      rejectedCalls.incrementAndGet();
      LOGGER.warn(
          "Bulkhead '{}' is full ({} active, {} queued), rejecting call",
          name,
          executor.getActiveCount(),
          executor.getQueue().size());
      throw new BulkheadFullException(name);
    }
  }

  /** Number of calls currently running. */
  public int getActiveCalls() {
    return executor.getActiveCount();
  }

  /** Number of calls waiting for a free thread. */
  public int getQueuedCalls() {
    return executor.getQueue().size();
  }

  /** Number of calls rejected since the bulkhead was created. */
  public long getRejectedCalls() {
    return rejectedCalls.get();
  }

  /**
   * Stops the compartment, interrupting calls that are still running and cancelling the calls that
   * were still waiting in the queue, so that callers blocked on their future are released instead
   * of waiting for a result that will never be produced.
   */
  public void shutdown() {
    for (var pending : executor.shutdownNow()) {
      if (pending instanceof Future<?> future) {
        future.cancel(false);
      }
    }
  }

  @Override
  public void close() {
    shutdown();
  }
}
