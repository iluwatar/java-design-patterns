---
title: Producer-Consumer
category: Concurrency
language: en
tag:
    - Asynchronous
    - Buffering
    - Decoupling
    - Messaging
    - Synchronization
    - Thread management
---

## Also known as

* Bounded Buffer
* Consumer-Producer

## Intent

The Producer-Consumer design pattern is used to decouple the tasks of producing and consuming data, enabling a producer to generate data and a consumer to process that data concurrently without direct dependency on each other.

## Explanation

Real-world example

> Consider a manufacturing process of item, the producer will need to pause the production when manufacturing pipeline is full and the consumer will need to pause the consumption of item when the manufacturing pipeline is empty. We can separate the process of production and consumption which work together and pause at separate times.

In plain words

> It provides a way to share data between multiple loops running at different rates.

Wikipedia says

> Dijkstra wrote about the case: "We consider two processes, which are called the 'producer' and the 'consumer' respectively. The producer is a cyclic process that produces a certain portion of information, that has to be processed by the consumer. The consumer is also a cyclic process that needs to process the next portion of information, as has been produced by the producer. We assume the two processes to be connected for this purpose via a buffer with unbounded capacity."

**Programmatic Example**

Take our Producer and Consumer example from above. Consider we have a `Item` class that is produced by `Producer` class and is added to the `ItemQueue`.

```java
public class Producer {

  private static final SecureRandom RANDOM = new SecureRandom();

  private final ItemQueue queue;

  private final String name;

  private int itemId;

  public Producer(String name, ItemQueue queue) {
    this.name = name;
    this.queue = queue;
  }

  /**
   * Put item in the queue.
   */
  public void produce() throws InterruptedException {

    var item = new Item(name, itemId++);
    queue.put(item);
    Thread.sleep(RANDOM.nextInt(2000));
  }
}

```

Then, we have the `Consumer` class that takes the item from the item queue.

```java

@Slf4j
public class Consumer {

  private final ItemQueue queue;

  private final String name;

  public Consumer(String name, ItemQueue queue) {
    this.name = name;
    this.queue = queue;
  }

  /**
   * Consume item from the queue.
   */
  public void consume() throws InterruptedException {
    var item = queue.take();
    LOGGER.info("Consumer [{}] consume item [{}] produced by [{}]", name,
        item.getId(), item.getProducer());

  }
}
``` 

Now, during the manufacturing pipeline, we can instantiate objects from both the `Producer` and `Consumer` classes as they produce and consume items from the queue.

```java
var queue = new ItemQueue();
var executorService = Executors.newFixedThreadPool(5);
for (var i = 0; i < 2; i++) {

    final var producer = new Producer("Producer_" + i, queue);
    executorService.submit(() -> {
        while (true) {
            producer.produce();
        }
    });
}

for (var i = 0; i < 3; i++) {
    final var consumer = new Consumer("Consumer_" + i, queue);
    executorService.submit(() -> {
        while (true) {
            consumer.consume();
        }
    });
}
```

## Class diagram

![Producer-Consumer](./etc/producer-consumer.png "Producer-Consumer")

## Applicability

* When you need to manage a buffer or queue where producers add data and consumers take data, often in a multithreaded environment.
* When decoupling the production and consumption of data is beneficial for the application's design, performance, or maintainability.
* Suitable for scenarios requiring synchronized access to a shared resource or data structure.

## Known Uses

* Thread pools where worker threads act as consumers processing tasks produced by another thread.
* Logging frameworks where log messages are produced by various parts of an application and consumed by a logging service.
* Message queues in distributed systems for asynchronous communication between services.

## Consequences

Benefits:

* Decoupling: Producers and consumers can operate independently, simplifying the system design.
* Improved Performance: Allows multiple producer and consumer threads to work concurrently, improving throughput.
* Flexibility: Easily extendable to add more producers or consumers without significant changes to the existing system.

Trade-offs:

* Complexity: Requires careful handling of synchronization and potential deadlocks.
* Resource Management: Properly managing the buffer size to avoid overflow or underflow conditions.

## Related Patterns

* [Observer](https://java-design-patterns.com/patterns/observer/): While both deal with notifying or handling events, the Observer pattern is more about event subscription and notification, whereas Producer-Consumer focuses on decoupled data production and consumption.
* [Thread Pool](https://java-design-patterns.com/patterns/thread-pool/): Uses a similar decoupling approach where tasks are produced and consumed by a pool of worker threads.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
