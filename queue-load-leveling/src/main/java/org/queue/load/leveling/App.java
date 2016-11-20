/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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

package org.queue.load.leveling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Many solutions in the cloud involve running tasks that invoke services. In this environment, 
 * if a service is subjected to intermittent heavy loads, it can cause performance or reliability issues.
 * <p>
 * A service could be a component that is part of the same solution as the tasks that utilize it, or it 
 * could be a third-party service providing access to frequently used resources such as a cache or a storage service.
 * If the same service is utilized by a number of tasks running concurrently, it can be difficult to predict the 
 * volume of requests to which the service might be subjected at any given point in time.
 * <p>
 * We will build a queue-based-load-leveling to solve above problem. 
 * Refactor the solution and introduce a queue between the task and the service. 
 * The task and the service run asynchronously. The task posts a message containing the data required 
 * by the service to a queue. The queue acts as a buffer, storing the message until it is retrieved 
 * by the service. The service retrieves the messages from the queue and processes them. 
 * Requests from a number of tasks, which can be generated at a highly variable rate, can be passed 
 * to the service through the same message queue.
 * <p>
 * The queue effectively decouples the tasks from the service, and the service can handle the messages 
 * at its own pace irrespective of the volume of requests from concurrent tasks. Additionally, 
 * there is no delay to a task if the service is not available at the time it posts a message to the queue.
 * <p>
 * In this example we have a class {@link MessageQueue} to hold the message {@link Message} objects. 
 * All the worker threads {@link TaskGenerator} will submit the messages to the MessageQueue. 
 * The service executor class {@link ServiceExecutor} will pick up one task at a time from the Queue and 
 * execute them.
 *  
 */
public class App {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
  
  /**
   * Program entry point
   * 
   * @param args command line args
   */
  public static void main(String[] args) {
    try {
      // Create a MessageQueue object.
      MessageQueue msgQueue = new MessageQueue();
        
      LOGGER.info("All the TaskGenerators started.");
      
      // Create three TaskGenerator threads. Each of them will submit different number of jobs.
      Runnable taskRunnable1 = new TaskGenerator(msgQueue, 5);
      Runnable taskRunnable2 = new TaskGenerator(msgQueue, 1);
      Runnable taskRunnable3 = new TaskGenerator(msgQueue, 2);
      
      Thread taskGenerator1 = new Thread(taskRunnable1, "Task_Generator_1");
      Thread taskGenerator2 = new Thread(taskRunnable2, "Task_Generator_2");
      Thread taskGenerator3 = new Thread(taskRunnable3, "Task_Generator_3");
      
      taskGenerator1.start();
      taskGenerator2.start();
      taskGenerator3.start();
      
      LOGGER.info("Service Executor started.");
      
      // First create e service which will process all the jobs.
      Runnable srvRunnable = new ServiceExecutor(msgQueue);
      Thread srvExec = new Thread(srvRunnable, "Service_Executor_Thread");
      srvExec.start();
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }
}