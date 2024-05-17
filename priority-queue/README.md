---
title: Priority Queue
category: Concurrency
language: en
tag:
    - Performance
    - Scalability
    - Synchronization
    - Thread management
---

## Also known as

* Priority Heap
* Priority List

## Intent

The Priority Queue design pattern provides a way to manage a collection of elements where each element has a priority, and elements are accessed and removed based on their priority rather than their insertion order.

## Explanation

Real world example

> Imagine an emergency room in a hospital. Patients arrive with varying degrees of urgency: some have minor injuries, while others have life-threatening conditions. The hospital uses a priority queue system to manage these patients. Instead of treating patients on a first-come, first-served basis, the medical staff assigns a priority level to each patient based on the severity of their condition. Patients with more critical conditions (higher priority) are treated before those with less severe issues, ensuring that urgent cases receive immediate attention. This system efficiently manages resources and prioritizes care, similar to how a priority queue handles elements based on their priority.

In plain words

> Priority Queue enables processing of high priority messages first, regardless of queue size or message age.

Wikipedia says

> In computer science, a priority queue is an abstract data type similar to regular queue or stack data structure in which each element additionally has a "priority" associated with it. In a priority queue, an element with high priority is served before an element with low priority.

**Programmatic Example**

Looking at a video processing example, let's first see the `Message` structure.

```java
public class Message implements Comparable<Message> {

    private final String message;
    private final int priority; // define message priority in queue

    public Message(String message, int priority) {
        this.message = message;
        this.priority = priority;
    }

    @Override
    public int compareTo(Message o) {
        return priority - o.priority;
    }
    // ...
}
```

Here's `PriorityMessageQueue` that handles storing the messages and serving them in priority order.

```java
public class PriorityMessageQueue<T extends Comparable> {

    // ...

    public T remove() {
        if (isEmpty()) {
            return null;
        }

        final var root = queue[0];
        queue[0] = queue[size - 1];
        size--;
        maxHeapifyDown();
        return root;
    }

    public void add(T t) {
        ensureCapacity();
        queue[size] = t;
        size++;
        maxHeapifyUp();
    }

    // ...
}
```

`QueueManager` has a `PriorityMessageQueue` and makes it easy to `publishMessage` and `receiveMessage`.

```java
public class QueueManager {

    private final PriorityMessageQueue<Message> messagePriorityMessageQueue;

    public QueueManager(int initialCapacity) {
        messagePriorityMessageQueue = new PriorityMessageQueue<>(new Message[initialCapacity]);
    }

    public void publishMessage(Message message) {
        messagePriorityMessageQueue.add(message);
    }

    public Message receiveMessage() {
        if (messagePriorityMessageQueue.isEmpty()) {
            return null;
        }
        return messagePriorityMessageQueue.remove();
    }
}
```

`Worker` constantly polls `QueueManager` for highest priority message and processes it.

```java

@Slf4j
public class Worker {

    private final QueueManager queueManager;

    public Worker(QueueManager queueManager) {
        this.queueManager = queueManager;
    }

    public void run() throws Exception {
        while (true) {
            var message = queueManager.receiveMessage();
            if (message == null) {
                LOGGER.info("No Message ... waiting");
                Thread.sleep(200);
            } else {
                processMessage(message);
            }
        }
    }

    private void processMessage(Message message) {
        LOGGER.info(message.toString());
    }
}
```

Here's the full example how we create an instance of `QueueManager` and process messages using `Worker`.

```java
var queueManager = new QueueManager(100);

for (var i = 0; i< 100; i++) {
    queueManager.publishMessage(new Message("Low Message Priority", 0));
}

for(var i = 0; i< 100; i++) {
    queueManager.publishMessage(new Message("High Message Priority", 1));
}

var worker = new Worker(queueManager);
worker.run();
```

Program output:

```
Message{message='High Message Priority', priority=1}
Message{message='High Message Priority', priority=1}
Message{message='High Message Priority', priority=1}
Message{message='High Message Priority', priority=1}
Message{message='High Message Priority', priority=1}
Message{message='High Message Priority', priority=1}
Message{message='High Message Priority', priority=1}
Message{message='High Message Priority', priority=1}
Message{message='High Message Priority', priority=1}
Message{message='High Message Priority', priority=1}
Message{message='Low Message Priority', priority=0}
Message{message='Low Message Priority', priority=0}
Message{message='Low Message Priority', priority=0}
Message{message='Low Message Priority', priority=0}
Message{message='Low Message Priority', priority=0}
Message{message='Low Message Priority', priority=0}
Message{message='Low Message Priority', priority=0}
Message{message='Low Message Priority', priority=0}
Message{message='Low Message Priority', priority=0}
Message{message='Low Message Priority', priority=0}
No Message ... waiting
No Message ... waiting
No Message ... waiting
```

## Class diagram

![alt text](./etc/priority-queue.urm.png "Priority Queue pattern class diagram")

## Applicability

Use the Priority Queue pattern when:

* You need to manage tasks or elements that have different priorities.
* Applicable in scheduling systems where tasks need to be executed based on their priority.
* Useful in scenarios where you need to handle a large number of elements with varying importance levels efficiently.

## Known Uses

* Task scheduling in operating systems.
* Job scheduling in servers and batch processing systems.
* Event simulation systems where events are processed based on their scheduled time.
* Pathfinding algorithms (e.g., Dijkstra's or A* algorithms).

## Consequences

Benefits:

* Efficient management of elements based on priority.
* Enhanced performance for priority-based access and removal operations.
* Improved scalability in systems requiring prioritized task execution.

Trade-offs:

* Increased complexity in implementation and maintenance compared to simple queues.
* Potential for higher memory usage due to the underlying data structures.
* Requires careful synchronization in a multi-threaded environment to avoid concurrency issues.

## Related Patterns

* Observer: Can be used alongside Priority Queue to notify when elements with high priority are processed.

## Credits

* [Priority Queue pattern - Microsoft](https://docs.microsoft.com/en-us/azure/architecture/patterns/priority-queue)
