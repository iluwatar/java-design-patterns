---
layout: pattern
title: Poison Pill
folder: poison-pill
permalink: /patterns/poison-pill/
categories: Behavioral
tags:
 - Cloud distributed
 - Reactive
---

## Intent
Poison Pill is known predefined data item that allows to provide graceful shutdown for separate distributed consumption 
process.

## Explanation

Real world example

> Let's think about a message queue with one producer and one consumer. The producer keeps pushing new messages in the queue and the consumer keeps reading them. Finally when it's time to gracefully shut down the producer sends the poison pill message.             

In plain words

> Poison Pill is a known message structure that ends the message exchange.   

**Programmatic Example**

Let's define the message structure first.

```java
public interface Message {

  Message POISON_PILL = new Message() {

    @Override
    public void addHeader(Headers header, String value) {
      throw poison();
    }

    @Override
    public String getHeader(Headers header) {
      throw poison();
    }

    @Override
    public Map<Headers, String> getHeaders() {
      throw poison();
    }

    @Override
    public void setBody(String body) {
      throw poison();
    }

    @Override
    public String getBody() {
      throw poison();
    }

    private RuntimeException poison() {
      return new UnsupportedOperationException("Poison");
    }

  };

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

Next we define the types related to the message queue.

```java
public interface MqPublishPoint {

  void put(Message msg) throws InterruptedException;
}

public interface MqSubscribePoint {

  Message take() throws InterruptedException;
}

public interface MessageQueue extends MqPublishPoint, MqSubscribePoint {
}

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

Now we need to create the message producer and consumer.

```java
public class Producer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

  private final MqPublishPoint queue;
  private final String name;
  private boolean isStopped;

  /**
   * Constructor.
   */
  public Producer(String name, MqPublishPoint queue) {
    this.name = name;
    this.queue = queue;
    this.isStopped = false;
  }

  /**
   * Send message to queue.
   */
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

  /**
   * Stop system by sending poison pill.
   */
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

  private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

  private final MqSubscribePoint queue;
  private final String name;

  public Consumer(String name, MqSubscribePoint queue) {
    this.name = name;
    this.queue = queue;
  }

  /**
   * Consume message.
   */
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

Finally we are ready to present the whole example in action.

```java
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
```

## Class diagram
![alt text](./etc/poison-pill.png "Poison Pill")

## Applicability
Use the Poison Pill idiom when

* Need to send signal from one thread/process to another to terminate

## Real world examples

* [akka.actor.PoisonPill](http://doc.akka.io/docs/akka/2.1.4/java/untyped-actors.html)
