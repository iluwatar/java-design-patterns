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
package com.iluwatar.timeout;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

/**
 * Runs downstream calls under the time limit declared by their {@link TimeoutPolicy}.
 *
 * <p>The call is executed on a separate thread while the caller waits for at most the configured
 * duration. When the limit is exceeded the call is cancelled with an interrupt, the event is logged
 * and counted in {@link TimeoutMetrics}, and the supplied fallback provides the answer instead.
 * Failures raised by the service itself are not treated as timeouts; they surface as {@link
 * ServiceCallException}.
 */
@Slf4j
public class TimeoutExecutor implements AutoCloseable {

  private final ExecutorService executor;
  private final TimeoutMetrics metrics = new TimeoutMetrics();

  /** Creates an executor that runs every call on its own virtual thread. */
  public TimeoutExecutor() {
    this(Executors.newVirtualThreadPerTaskExecutor());
  }

  /**
   * Creates an executor backed by the given thread pool.
   *
   * @param executor pool used to run the calls
   */
  public TimeoutExecutor(ExecutorService executor) {
    this.executor = Objects.requireNonNull(executor, "executor");
  }

  /**
   * Executes a call within the limit of its policy.
   *
   * @param policy limit that applies to the call
   * @param call the downstream invocation
   * @param fallback answer to use when the call does not complete in time
   * @param <T> type of the response
   * @return the response of the call, or the fallback if the limit was exceeded
   * @throws ServiceCallException if the call cannot be submitted, fails, or the waiting thread is
   *     interrupted
   */
  public <T> T execute(TimeoutPolicy policy, Callable<T> call, Supplier<T> fallback) {
    var serviceName = policy.serviceName();
    var limitMillis = policy.timeout().toMillis();
    Future<T> future = null;
    try {
      future = executor.submit(call);
      var result = future.get(limitMillis, TimeUnit.MILLISECONDS);
      LOGGER.info("{} responded within its {} ms limit", serviceName, limitMillis);
      return result;
    } catch (TimeoutException e) {
      future.cancel(true);
      metrics.recordTimeout(serviceName);
      LOGGER.warn(
          "{} exceeded its {} ms limit; call cancelled, using fallback", serviceName, limitMillis);
      return fallback.get();
    } catch (ExecutionException e) {
      throw new ServiceCallException(serviceName, e.getCause());
    } catch (InterruptedException e) {
      future.cancel(true);
      Thread.currentThread().interrupt();
      throw new ServiceCallException(serviceName, e);
    } catch (RejectedExecutionException e) {
      throw new ServiceCallException(serviceName, e);
    }
  }

  /**
   * Exposes the timeout counters.
   *
   * @return the metrics collected so far
   */
  public TimeoutMetrics metrics() {
    return metrics;
  }

  /** Stops the underlying thread pool, interrupting any call that is still running. */
  @Override
  public void close() {
    executor.shutdownNow();
  }
}
