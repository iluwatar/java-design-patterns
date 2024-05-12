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
package com.iluwatar.async.method.invocation;

import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;

/**
 * In this example, we are launching space rockets and deploying lunar rovers.
 *
 * <p>The application demonstrates the async method invocation pattern. The key parts of the
 * pattern are <code>AsyncResult</code> which is an intermediate container for an asynchronously
 * evaluated value, <code>AsyncCallback</code> which can be provided to be executed on task
 * completion and <code>AsyncExecutor</code> that manages the execution of the async tasks.
 *
 * <p>The main method shows example flow of async invocations. The main thread starts multiple
 * tasks with variable durations and then continues its own work. When the main thread has done it's
 * job it collects the results of the async tasks. Two of the tasks are handled with callbacks,
 * meaning the callbacks are executed immediately when the tasks complete.
 *
 * <p>Noteworthy difference of thread usage between the async results and callbacks is that the
 * async results are collected in the main thread but the callbacks are executed within the worker
 * threads. This should be noted when working with thread pools.
 *
 * <p>Java provides its own implementations of async method invocation pattern. FutureTask,
 * CompletableFuture and ExecutorService are the real world implementations of this pattern. But due
 * to the nature of parallel programming, the implementations are not trivial. This example does not
 * take all possible scenarios into account but rather provides a simple version that helps to
 * understand the pattern.
 *
 * @see AsyncResult
 * @see AsyncCallback
 * @see AsyncExecutor
 * @see java.util.concurrent.FutureTask
 * @see java.util.concurrent.CompletableFuture
 * @see java.util.concurrent.ExecutorService
 */
@Slf4j
public class App {

  private static final String ROCKET_LAUNCH_LOG_PATTERN = "Space rocket <%s> launched successfully";

  /**
   * Program entry point.
   */

  public static void main(String[] args) throws Exception {
    // construct a new executor that will run async tasks
    var executor = new ThreadAsyncExecutor();

    // start few async tasks with varying processing times, two last with callback handlers
    final var asyncResult1 = executor.startProcess(lazyval(10, 500));
    final var asyncResult2 = executor.startProcess(lazyval("test", 300));
    final var asyncResult3 = executor.startProcess(lazyval(50L, 700));
    final var asyncResult4 = executor.startProcess(lazyval(20, 400),
        callback("Deploying lunar rover"));
    final var asyncResult5 =
        executor.startProcess(lazyval("callback", 600), callback("Deploying lunar rover"));

    // emulate processing in the current thread while async tasks are running in their own threads
    Thread.sleep(350); // Oh boy, we are working hard here
    log("Mission command is sipping coffee");

    // wait for completion of the tasks
    final var result1 = executor.endProcess(asyncResult1);
    final var result2 = executor.endProcess(asyncResult2);
    final var result3 = executor.endProcess(asyncResult3);
    asyncResult4.await();
    asyncResult5.await();

    // log the results of the tasks, callbacks log immediately when complete
    log(String.format(ROCKET_LAUNCH_LOG_PATTERN, result1));
    log(String.format(ROCKET_LAUNCH_LOG_PATTERN, result2));
    log(String.format(ROCKET_LAUNCH_LOG_PATTERN, result3));
  }

  /**
   * Creates a callable that lazily evaluates to given value with artificial delay.
   *
   * @param value       value to evaluate
   * @param delayMillis artificial delay in milliseconds
   * @return new callable for lazy evaluation
   */
  private static <T> Callable<T> lazyval(T value, long delayMillis) {
    return () -> {
      Thread.sleep(delayMillis);
      log(String.format(ROCKET_LAUNCH_LOG_PATTERN, value));
      return value;
    };
  }

  /**
   * Creates a simple callback that logs the complete status of the async result.
   *
   * @param name callback name
   * @return new async callback
   */
  private static <T> AsyncCallback<T> callback(String name) {
    return new AsyncCallback<>() {
      @Override
      public void onComplete(T value) {
        log(name + " <" + value + ">");
      }

      @Override
      public void onError(Exception ex) {
        log(name + " failed: " + ex.getMessage());
      }
    };
  }

  private static void log(String msg) {
    LOGGER.info(msg);
  }
}
