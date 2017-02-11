---
layout: post
title: Queue-Based Load Leveling Pattern
author: cha
---

![Queue-Based Load Leveling]({{ site.baseurl }}/assets/queue-load-leveling-title.png)

## Problem
Many solutions in the cloud involve running tasks that invoke services. In this environment, if a service is subjected to intermittent heavy loads, it can cause performance or reliability issues. If the same service is utilized by a number of tasks running concurrently, it can be difficult to predict the volume of requests to which the service might be subjected at any given point in time. It is possible that a service might experience peaks in demand that cause it to become overloaded and unable to respond to requests in a timely manner.

## Solution
Introduce a queue between the task and the service. The task and the service run asynchronously. The task posts a message containing the data required by the service to a queue. The queue acts as a buffer, storing the message until it is retrieved by the service. The service retrieves the messages from the queue and processes them. Requests from a number of tasks, which can be generated at a highly variable rate, can be passed to the service through the same message queue.

![Queue-Based Load Leveling Pattern]({{ site.baseurl }}/assets/Queue-load-level-pattern.png)

The queue effectively decouples the tasks from the service, and the service can handle the messages at its own pace irrespective of the volume of requests from concurrent tasks. Additionally, there is no delay to a task if the service is not available at the time it posts a message to the queue.

![Using a queue and a worker role to level the load between instances of the web role and the service]({{ site.baseurl }}/assets/Queue-load-level-scenario.png)

## Benefits
* It can help to maximize availability because delays arising in services will not have an immediate and direct impact on the application, which can continue to post messages to the queue even when the service is not available or is not currently processing messages.
* It can help to maximize scalability because both the number of queues and the number of services can be varied to meet demand.
* It can help to control costs because the number of service instances deployed needs only to be sufficient to meet average
load rather than the peak load.

## Implementation
* We will demonstrate Queue-Based Load Leveling Pattern by developing a simple application.
* Following are the core classes in the application,
  - TaskGenerator: This is the service requester class where we create any number of requests and submit them to the Task Queue. Each TaskGenerator instance is a Thread. Each requester submits requests at its own rate.

    <script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/queue-load-leveling/src/main/java/com/iluwatar/queue/load/leveling/TaskGenerator.java?slice=27:89"></script>

  - Message: TaskGenerators create objects of Message class and submit them to the MessageQueue.

    <script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/queue-load-leveling/src/main/java/com/iluwatar/queue/load/leveling/Message.java?slice=28:47"></script>

  - MessageQueue: In this class we have a BlockingQueue which takes messages submitted from the TaskGenerators. This class is just used for storing and retreiving tasks from the Queue.

    <script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/queue-load-leveling/src/main/java/com/iluwatar/queue/load/leveling/MessageQueue.java?slice=30:77"></script>

  - ServiceExecutor: Picks up the tasks from the MessageQueue and serves them. The ServiceRequester picks up requests at constant rate even though the TaksGenerators submitted at a different rate.

    <script src="http://gist-it.appspot.com/http://github.com/iluwatar/java-design-patterns/raw/master/queue-load-leveling/src/main/java/com/iluwatar/queue/load/leveling/ServiceExecutor.java?slice=28:67"></script>

## Output
   Running the test application produces the following output:

```
21:04:03.897 [main] INFO org.queue.load.leveling.App - Submitting TaskGenerators and ServiceExecutor threads.
21:04:03.907 [main] INFO org.queue.load.leveling.App - Intiating shutdown. Executor will shutdown only after all the Threads are completed.
21:04:03.909 [pool-1-thread-1] INFO org.queue.load.leveling.App - Message-5 submitted by pool-1-thread-1
21:04:03.909 [pool-1-thread-2] INFO org.queue.load.leveling.App - Message-1 submitted by pool-1-thread-2
21:04:04.909 [pool-1-thread-1] INFO org.queue.load.leveling.App - Message-4 submitted by pool-1-thread-1
21:04:04.909 [pool-1-thread-2] INFO org.queue.load.leveling.App - Message-2 submitted by pool-1-thread-2
21:04:05.910 [pool-1-thread-1] INFO org.queue.load.leveling.App - Message-3 submitted by pool-1-thread-1
21:04:05.910 [pool-1-thread-2] INFO org.queue.load.leveling.App - Message-1 submitted by pool-1-thread-2
21:04:06.910 [pool-1-thread-1] INFO org.queue.load.leveling.App - Message-2 submitted by pool-1-thread-1
21:04:06.910 [pool-1-thread-2] INFO org.queue.load.leveling.App - Message-5 submitted by pool-1-thread-1 is served.
21:04:07.911 [pool-1-thread-1] INFO org.queue.load.leveling.App - Message-1 submitted by pool-1-thread-1
21:04:07.911 [pool-1-thread-2] INFO org.queue.load.leveling.App - Message-1 submitted by pool-1-thread-2 is served.
21:04:08.912 [pool-1-thread-2] INFO org.queue.load.leveling.App - Message-4 submitted by pool-1-thread-1 is served.
21:04:09.912 [pool-1-thread-2] INFO org.queue.load.leveling.App - Message-2 submitted by pool-1-thread-2 is served.
21:04:10.913 [pool-1-thread-2] INFO org.queue.load.leveling.App - Message-1 submitted by pool-1-thread-2 is served.
21:04:11.913 [pool-1-thread-2] INFO org.queue.load.leveling.App - Message-3 submitted by pool-1-thread-1 is served.
21:04:12.914 [pool-1-thread-2] INFO org.queue.load.leveling.App - Message-2 submitted by pool-1-thread-1 is served.
21:04:13.914 [pool-1-thread-2] INFO org.queue.load.leveling.App - Message-1 submitted by pool-1-thread-1 is served.
21:04:14.915 [pool-1-thread-2] INFO org.queue.load.leveling.App - Service Executor: Waiting for Messages to serve ..
21:04:15.915 [pool-1-thread-2] INFO org.queue.load.leveling.App - Service Executor: Waiting for Messages to serve ..
```

## Conclusion
Some services may implement throttling if demand reaches a threshold beyond which the system could fail. Throttling may reduce the functionality available. You might be able to implement load leveling with these services to ensure that this threshold is not reached.

The full demo application of **Queue-Based Load Leveling Pattern** is available in [Java Design Patterns](https://github.com/iluwatar/java-design-patterns/tree/master/queue-load-leveling) Github repository.

## Credits
* [Microsoft Cloud Design Patterns: Queue-Based Load Leveling Pattern](https://msdn.microsoft.com/en-us/library/dn589783.aspx)
