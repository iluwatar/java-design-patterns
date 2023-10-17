---
title: Queue based load leveling
category: Concurrency
language: en
tag:
 - Decoupling
 - Performance
 - Cloud distributed
---

## Intent
Use a queue that acts as a buffer between a task and a service that it invokes in order to smooth 
intermittent heavy loads that may otherwise cause the service to fail or the task to time out. 
This pattern can help to minimize the impact of peaks in demand on availability and responsiveness 
for both the task and the service.

## Explanation

Real world example
> A Microsoft Azure web role stores data by using a separate storage service. If a large number of instances of the web 
> role run concurrently, it is possible that the storage service could be overwhelmed and be unable to respond to requests 
> quickly enough to prevent these requests from timing out or failing.

In plain words
> Makes resource-load balanced by ensuring an intermediate data structure like queue that makes bridge 
> between service-takers and service-givers. Where both takers and givers are running asynchronously and 
> service-takers can tolerate some amount of delay to get feedback. 
>

Wikipedia says

> In computing, load balancing is the process of distributing a set of tasks over a set of resources 
> (computing units), with the aim of making their overall processing more efficient. Load balancing can
> optimize the response time and avoid unevenly overloading some compute nodes while other compute nodes
> are left idle.

**Programmatic Example**

TaskGenerator implements Task, runnable interfaces. Hence, It runs asynchronously.

```java
/**
 * Task Interface.
 */
public interface Task {
  void submit(Message msg);
}
```
It submits tasks to ServiceExecutor to serve tasks. 
```java
/**
 * TaskGenerator class. Each TaskGenerator thread will be a Worker which submit's messages to the
 * queue. We need to mention the message count for each of the TaskGenerator threads.
 */
@Slf4j
public class TaskGenerator implements Task, Runnable {

  // MessageQueue reference using which we will submit our messages.
  private final MessageQueue msgQueue;

  // Total message count that a TaskGenerator will submit.
  private final int msgCount;

  // Parameterized constructor.
  public TaskGenerator(MessageQueue msgQueue, int msgCount) {
    this.msgQueue = msgQueue;
    this.msgCount = msgCount;
  }

  /**
   * Submit messages to the Blocking Queue.
   */
  public void submit(Message msg) {
    try {
      this.msgQueue.submitMsg(msg);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }

  /**
   * Each TaskGenerator thread will submit all the messages to the Queue. After every message
   * submission TaskGenerator thread will sleep for 1 second.
   */
  public void run() {
    var count = this.msgCount;

    try {
      while (count > 0) {
        var statusMsg = "Message-" + count + " submitted by " + Thread.currentThread().getName();
        this.submit(new Message(statusMsg));

        LOGGER.info(statusMsg);

        // reduce the message count.
        count--;

        // Make the current thread to sleep after every Message submission.
        Thread.sleep(1000);
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }
}
```
It also implements runnable interface and run asynchronously. It retrieves tasks one by one 
from blockingQueue to serve. 
```java
/**
 * ServiceExecuotr class. This class will pick up Messages one by one from the Blocking Queue and
 * process them.
 */
@Slf4j
public class ServiceExecutor implements Runnable {

  private final MessageQueue msgQueue;

  public ServiceExecutor(MessageQueue msgQueue) {
    this.msgQueue = msgQueue;
  }

  /**
   * The ServiceExecutor thread will retrieve each message and process it.
   */
  public void run() {
    try {
      while (!Thread.currentThread().isInterrupted()) {
        var msg = msgQueue.retrieveMsg();

        if (null != msg) {
          LOGGER.info(msg.toString() + " is served.");
        } else {
          LOGGER.info("Service Executor: Waiting for Messages to serve .. ");
        }

        Thread.sleep(1000);
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }
}
```

BlockingQueue data-structure is used in MessageQueue class for acting buffer
between TaskGenerator to ServiceExecutor.

```java
public class MessageQueue {

  private final BlockingQueue<Message> blkQueue;

  // Default constructor when called creates Blocking Queue object. 
  public MessageQueue() {
    this.blkQueue = new ArrayBlockingQueue<>(1024);
  }

  /**
   * All the TaskGenerator threads will call this method to insert the Messages in to the Blocking
   * Queue.
   */
  public void submitMsg(Message msg) {
    try {
      if (null != msg) {
        blkQueue.add(msg);
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }

  /**
   * All the messages will be retrieved by the ServiceExecutor by calling this method and process
   * them. Retrieves and removes the head of this queue, or returns null if this queue is empty.
   */
  public Message retrieveMsg() {
    try {
      return blkQueue.poll();
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
    return null;
  }
}
```
TaskGenerator submit message object to ServiceExecutor for serving.
```java
/**
 * Message class with only one parameter.
 */
@Getter
@RequiredArgsConstructor
public class Message {
  private final String msg;

  @Override
  public String toString() {
    return msg;
  }
}
```
To simulate the situation ExecutorService is used here. ExecutorService automatically provides a pool of threads and 
an API for assigning tasks to it.
```java
public class App {

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
      var msgQueue = new MessageQueue();

      LOGGER.info("Submitting TaskGenerators and ServiceExecutor threads.");

      // Create three TaskGenerator threads. Each of them will submit different number of jobs.
      final var taskRunnable1 = new TaskGenerator(msgQueue, 5);
      final var taskRunnable2 = new TaskGenerator(msgQueue, 1);
      final var taskRunnable3 = new TaskGenerator(msgQueue, 2);

      // Create e service which should process the submitted jobs.
      final var srvRunnable = new ServiceExecutor(msgQueue);

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
```

The console output
```
[main] INFO App - Submitting TaskGenerators and ServiceExecutor threads.
[main] INFO App - Initiating shutdown. Executor will shutdown only after all the Threads are completed.
[pool-1-thread-2] INFO TaskGenerator - Message-1 submitted by pool-1-thread-2
[pool-1-thread-1] INFO TaskGenerator - Message-5 submitted by pool-1-thread-1
[pool-1-thread-1] INFO TaskGenerator - Message-4 submitted by pool-1-thread-1
[pool-1-thread-2] INFO TaskGenerator - Message-2 submitted by pool-1-thread-2
[pool-1-thread-1] INFO TaskGenerator - Message-3 submitted by pool-1-thread-1
[pool-1-thread-2] INFO TaskGenerator - Message-1 submitted by pool-1-thread-2
[pool-1-thread-1] INFO TaskGenerator - Message-2 submitted by pool-1-thread-1
[pool-1-thread-2] INFO ServiceExecutor - Message-1 submitted by pool-1-thread-2 is served.
[pool-1-thread-1] INFO TaskGenerator - Message-1 submitted by pool-1-thread-1
[pool-1-thread-2] INFO ServiceExecutor - Message-5 submitted by pool-1-thread-1 is served.
[pool-1-thread-2] INFO ServiceExecutor - Message-4 submitted by pool-1-thread-1 is served.
[pool-1-thread-2] INFO ServiceExecutor - Message-2 submitted by pool-1-thread-2 is served.
[pool-1-thread-2] INFO ServiceExecutor - Message-3 submitted by pool-1-thread-1 is served.
[pool-1-thread-2] INFO ServiceExecutor - Message-1 submitted by pool-1-thread-2 is served.
[pool-1-thread-2] INFO ServiceExecutor - Message-2 submitted by pool-1-thread-1 is served.
[pool-1-thread-2] INFO ServiceExecutor - Message-1 submitted by pool-1-thread-1 is served.
[pool-1-thread-2] INFO ServiceExecutor - Service Executor: Waiting for Messages to serve .. 
[pool-1-thread-2] INFO ServiceExecutor - Service Executor: Waiting for Messages to serve .. 
[pool-1-thread-2] INFO ServiceExecutor - Service Executor: Waiting for Messages to serve .. 
[pool-1-thread-2] INFO ServiceExecutor - Service Executor: Waiting for Messages to serve .. 
[main] INFO App - Executor was shut down and Exiting.
[pool-1-thread-2] ERROR ServiceExecutor - sleep interrupted
```

## Class diagram
![alt text](./etc/queue-load-leveling.gif "queue-load-leveling")

## Applicability

* This pattern is ideally suited to any type of application that uses services that may be subject to overloading.
* This pattern might not be suitable if the application expects a response from the service with minimal latency.

## Tutorials
* [Queue-Based Load Leveling Pattern](http://java-design-patterns.com/blog/queue-load-leveling/)


## Credits

* [Queue-Based Load Leveling pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/queue-based-load-leveling)
* [Load-Balancing](https://www.wikiwand.com/en/Load_balancing_(computing))
