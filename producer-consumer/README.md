---
title: Producer Consumer
category: Concurrency
language: en
tags:
 - Reactive
---

## Intent
Producer Consumer Design pattern is a classic concurrency pattern which reduces
 coupling between Producer and Consumer by separating Identification of work with Execution of
 Work.

## Explanation 

Real-world example

> Consider a manufacturing process of item, the producer will need to pause the production when 
> manufacturing pipeline is full and the consumer will need to pause the consumption of item 
> when the manufacturing pipeline is empty. We can separate the process of production and consumption 
> which work together and pause at separate times. 

In plain words

> It provides a way to share data between multiple loops running at different rates. 

Wikipedia says
> Dijkstra wrote about the case: "We consider two processes, which are called the 'producer' 
> and the 'consumer' respectively. The producer is a cyclic process that produces a certain 
> portion of information, that has to be processed by the consumer. The consumer is also a cyclic 
> process that needs to process the next portion of information, as has been produced by the producer 
> We assume the two processes to be connected for this purpose via a buffer with unbounded capacity."

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

Now, during the manufacturing pipeline, we can instantiate objects from both the `Producer` and `Consumer` clasess as they produce and consumer items from the queue. 

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
![alt text](./etc/producer-consumer.png "Producer Consumer")

## Applicability
Use the Producer Consumer idiom when

* Decouple system by separate work in two process produce and consume.
* Addresses the issue of different timing require to produce work or consuming work
