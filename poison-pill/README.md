---
title: Poison Pill
category: Concurrency
language: en
tag:
    - Decoupling
    - Fault tolerance
    - Messaging
    - Thread management
---

## Also known as

* Shutdown Signal

## Intent

The Poison Pill design pattern is used to gracefully shut down a service or a producer-consumer system by sending a special message (the "poison pill") which indicates that no more messages will be sent, allowing the consumers to terminate.

## Explanation

Real-world example

> A real-world analogy for the Poison Pill design pattern is the use of a "closed" sign in a retail store. When the store is ready to close for the day, the manager places a "closed" sign on the door. This sign acts as a signal to any new customers that no more customers will be admitted, but it doesn't immediately force out the customers already inside. The store staff will then attend to the remaining customers, allowing them to complete their purchases before finally locking up and turning off the lights. Similarly, in the Poison Pill pattern, a special "poison pill" message signals consumers to stop accepting new tasks while allowing them to finish processing the current tasks before shutting down gracefully. 

In plain words

> Poison Pill is a known message structure that ends the message exchange.   

**Programmatic Example**

Let's define the message structure first. There's interface `Message` and implementation `SimpleMessage`.

```java
public interface Message {
    
  // Other properties and methods...

  enum Headers {
    DATE, SENDER
  }

  void addHeader(Headers header, String value);

  String getHeader(Headers header);

  Map<Headers, String> getHeaders();

  void setBody(String body);

  String getBody();
}

public class SimpleMessage implements Message {

  private final Map<Headers, String> headers = new HashMap<>();
  private String body;

  @Override
  public void addHeader(Headers header, String value) {
    headers.put(header, value);
  }

  @Override
  public String getHeader(Headers header) {
    return headers.get(header);
  }

  @Override
  public Map<Headers, String> getHeaders() {
    return Collections.unmodifiableMap(headers);
  }

  @Override
  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String getBody() {
    return body;
  }
}
```

To pass messages we are using message queues. Here we define the types related to the message queue: `MqPublishPoint`, `MqSubscribePoint` and `MessageQueue`. `SimpleMessageQueue` implements all these interfaces.

```java
public interface MqPublishPoint {
  void put(Message msg) throws InterruptedException;
}

public interface MqSubscribePoint {
  Message take() throws InterruptedException;
}

public interface MessageQueue extends MqPublishPoint, MqSubscribePoint {}

public class SimpleMessageQueue implements MessageQueue {

  private final BlockingQueue<Message> queue;

  public SimpleMessageQueue(int bound) {
    queue = new ArrayBlockingQueue<>(bound);
  }

  @Override
  public void put(Message msg) throws InterruptedException {
    queue.put(msg);
  }

  @Override
  public Message take() throws InterruptedException {
    return queue.take();
  }
}
```

Next, we need message `Producer` and `Consumer`. Internally they use the message queues from above. It's important to notice that when `Producer` stops, it sends out the poison pill to inform `Consumer` that the messaging has finished. 

```java
public class Producer {

  // Other properties and methods...

  public void send(String body) {
    if (isStopped) {
      throw new IllegalStateException(String.format(
          "Producer %s was stopped and fail to deliver requested message [%s].", body, name));
    }
    var msg = new SimpleMessage();
    msg.addHeader(Headers.DATE, new Date().toString());
    msg.addHeader(Headers.SENDER, name);
    msg.setBody(body);

    try {
      queue.put(msg);
    } catch (InterruptedException e) {
      // allow thread to exit
      LOGGER.error("Exception caught.", e);
    }
  }

  public void stop() {
    isStopped = true;
    try {
      queue.put(Message.POISON_PILL);
    } catch (InterruptedException e) {
      // allow thread to exit
      LOGGER.error("Exception caught.", e);
    }
  }
}

public class Consumer {

  // Other properties and methods...

  public void consume() {
    while (true) {
      try {
        var msg = queue.take();
        if (Message.POISON_PILL.equals(msg)) {
          LOGGER.info("Consumer {} receive request to terminate.", name);
          break;
        }
        var sender = msg.getHeader(Headers.SENDER);
        var body = msg.getBody();
        LOGGER.info("Message [{}] from [{}] received by [{}]", body, sender, name);
      } catch (InterruptedException e) {
        // allow thread to exit
        LOGGER.error("Exception caught.", e);
        return;
      }
    }
  }
}
```

Finally, we are ready to present the whole example in action.

```java
  public static void main(String[] args) {
    var queue = new SimpleMessageQueue(10000);

    final var producer = new Producer("PRODUCER_1", queue);
    final var consumer = new Consumer("CONSUMER_1", queue);

    new Thread(consumer::consume).start();

    new Thread(() -> {
        producer.send("hand shake");
        producer.send("some very important information");
        producer.send("bye!");
        producer.stop();
    }).start();
}
```

Program output:

```
07:43:01.518 [Thread-0] INFO com.iluwatar.poison.pill.Consumer -- Message [hand shake] from [PRODUCER_1] received by [CONSUMER_1]
07:43:01.520 [Thread-0] INFO com.iluwatar.poison.pill.Consumer -- Message [some very important information] from [PRODUCER_1] received by [CONSUMER_1]
07:43:01.520 [Thread-0] INFO com.iluwatar.poison.pill.Consumer -- Message [bye!] from [PRODUCER_1] received by [CONSUMER_1]
07:43:01.520 [Thread-0] INFO com.iluwatar.poison.pill.Consumer -- Consumer CONSUMER_1 receive request to terminate.
```

## Class diagram

![Poison Pill](./etc/poison-pill.png "Poison Pill")

## Applicability

Use the Poison Pill idiom when:

* When there is a need to gracefully shut down a multithreaded application.
* In producer-consumer scenarios where consumers need to be informed about the end of message processing.
* To ensure that consumers can finish processing remaining messages before shutting down.

## Known Uses

* Java ExecutorService shutdown using a special task to signal shutdown.
* Messaging systems where a specific message indicates the end of the queue processing.
* [Akka framework](https://doc.akka.io/japi/akka/2.5/akka/actor/typed/internal/PoisonPill.html)

## Consequences

Benefits:

* Simplifies the shutdown process of consumers.
* Ensures that all pending tasks are completed before termination.
* Decouples the shutdown logic from the main processing logic.

Trade-offs:

* Requires consumers to check for the poison pill, adding some overhead.
* If not managed properly, could lead to consumers not recognizing the poison pill, causing indefinite blocking.

## Related Patterns

* [Producer-Consumer](https://java-design-patterns.com/patterns/producer-consumer/): Works in tandem with the Poison Pill pattern to handle the communication and shutdown of consumers.
* Message Queue: Often uses poison pills to signal the end of message processing in the queue.
* [Observer](https://java-design-patterns.com/patterns/observer/): Can be used to notify subscribers about the shutdown event.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
* [Effective Java](https://amzn.to/4cGk2Jz)
