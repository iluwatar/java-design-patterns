/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.queue.load.leveling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Many solutions in the cloud involve running tasks that invoke services. In this environment, if a
 * service is subjected to intermittent heavy loads, it can cause performance or reliability
 * issues.
 *
 * <p>A service could be a component that is part of the same solution as the tasks that utilize
 * it, or it could be a third-party service providing access to frequently used resources such as a
 * cache or a storage service. If the same service is utilized by a number of tasks running
 * concurrently, it can be difficult to predict the volume of requests to which the service might be
 * subjected at any given point in time.
 *
 * <p>We will build a queue-based-load-leveling to solve above problem. Refactor the solution and
 * introduce a queue between the task and the service. The task and the service run asynchronously.
 * The task posts a message containing the data required by the service to a queue. The queue acts
 * as a buffer, storing the message until it is retrieved by the service. The service retrieves the
 * messages from the queue and processes them. Requests from a number of tasks, which can be
 * generated at a highly variable rate, can be passed to the service through the same message
 * queue.
 *
 * <p>The queue effectively decouples the tasks from the service, and the service can handle the
 * messages at its own pace irrespective of the volume of requests from concurrent tasks.
 * Additionally, there is no delay to a task if the service is not available at the time it posts a
 * message to the queue.
 *
 * <p>In this example we have a class {@link MessageQueue} to hold the message {@link Message}
 * objects. All the worker threads {@link TaskGenerator} will submit the messages to the
 * MessageQueue. The service executor class {@link ServiceExecutor} will pick up one task at a time
 * from the Queue and execute them.
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  //Executor shut down time limit.
  private static final int SHUTDOWN_TIME = 15;

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    // An Executor that provides methods to manage termination and methods that can 
    // produce a Future for tracking progress of one or more asynchronous tasks.
    ExecutorService executor = null;

    try {
      // Create a MessageQueue object.
      MessageQueue msgQueue = new MessageQueue();

      LOGGER.info("Submitting TaskGenerators and ServiceExecutor threads.");

      // Create three TaskGenerator threads. Each of them will submit different number of jobs.
      final Runnable taskRunnable1 = new TaskGenerator(msgQueue, 5);
      final Runnable taskRunnable2 = new TaskGenerator(msgQueue, 1);
      final Runnable taskRunnable3 = new TaskGenerator(msgQueue, 2);

      // Create e service which should process the submitted jobs.
      final Runnable srvRunnable = new ServiceExecutor(msgQueue);

      // Create a ThreadPool of 2 threads and
      // submit all Runnable task for execution to executor..
      executor = Executors.newFixedThreadPool(2);
      executor.submit(taskRunnable1);
      executor.submit(taskRunnable2);
      executor.submit(taskRunnable3);

      // submitting serviceExecutor thread to the Executor service.
      executor.submit(srvRunnable);

      // Initiates an orderly shutdown.
      LOGGER.info("Initiating shutdown."
          + " Executor will shutdown only after all the Threads are completed.");
      executor.shutdown();

      // Wait for SHUTDOWN_TIME seconds for all the threads to complete 
      // their tasks and then shut down the executor and then exit. 
      if (!executor.awaitTermination(SHUTDOWN_TIME, TimeUnit.SECONDS)) {
        LOGGER.info("Executor was shut down and Exiting.");
        executor.shutdownNow();
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }
}