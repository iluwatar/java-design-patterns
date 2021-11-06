---
layout: pattern
title: Poison Pill
folder: poison-pill
permalink: /patterns/poison-pill/
categories: Behavioral
language: en
tags:
 - Cloud distributed
 - Reactive
---

## Intent

Poison Pill is known predefined data item that allows to provide graceful shutdown for separate 
distributed consumption process.

## Explanation

Real world example

> Let's think about a message queue with one producer and one consumer. The producer keeps pushing 
> new messages in the queue and the consumer keeps reading them. Finally when it's time to 
> gracefully shut down the producer sends the poison pill message.             

In plain words

> Poison Pill is a known message structure that ends the message exchange.   

**Programmatic Example**

Let's define the message structure first. There's interface `Message` and implementation 
`SimpleMessage`.

```java
public interface Message {

  ...

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

To pass messages we are using message queues. Here we define the types related to the message queue: 
`MqPublishPoint`, `MqSubscribePoint` and `MessageQueue`. `SimpleMessageQueue` implements all these 
interfaces.

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

Next we need message `Producer` and `Consumer`. Internally they use the message queues from above.
It's important to notice that when `Producer` stops, it sends out the poison pill to inform 
`Consumer` that the messaging has finished. 

```java
public class Producer {
  
  ... 

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

  ...

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

Program output:

```
Message [hand shake] from [PRODUCER_1] received by [CONSUMER_1]
Message [some very important information] from [PRODUCER_1] received by [CONSUMER_1]
Message [bye!] from [PRODUCER_1] received by [CONSUMER_1]
Consumer CONSUMER_1 receive request to terminate.
```

## Class diagram

![alt text](./etc/poison-pill.png "Poison Pill")

## Applicability

Use the Poison Pill idiom when:

* There's a need to send signal from one thread/process to another to terminate.

## Real world examples

* [akka.actor.PoisonPill](http://doc.akka.io/docs/akka/2.1.4/java/untyped-actors.html)
