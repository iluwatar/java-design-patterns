---
layout: pattern
title: Priority Queue Pattern
folder: priority-queue
permalink: /patterns/priority-queue/zh
categories: Behavioral
language: zh
tags:
 - Decoupling
 - Cloud distributed
---

## 目的
优先处理发送到服务的请求，以便接收和接收具有更高优先级的请求
处理速度比低优先级的要快。这种模式在应用程序中很有用
为个别客户提供不同的服务水平保证。

## 解释

应用程序可以将特定任务委托给其他服务；例如，执行背景
处理或与其他应用程序或服务集成。在云端，消息队列是
通常用于将任务委托给后台处理。在许多情况下，请求的顺序
是否收到服务并不重要。但是，在某些情况下，可能需要优先考虑
具体要求。这些请求应该比其他优先级较低的请求更早处理
可能已由应用程序先前发送。

真实世界的例子

> 想象一下有免费和高级客户的视频处理服务。来自
> 付费高级客户应优先于其他客户。

简单来说

> 优先队列允许首先处理高优先级消息，无论队列大小或
> 消息时代。

维基百科说

> 在计算机科学中，优先级队列是一种类似于常规队列或堆栈的抽象数据类型
> 数据结构，其中每个元素还具有与其关联的“优先级”。在一个
> 优先级队列，优先级高的元素在优先级低的元素之前被服务。

**程序示例**

从上面看视频处理示例，让我们先看看 `Message` 结构。
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
  ...
}
```
这是“PriorityMessageQueue”，它处理存储消息并优先为它们提供服务
命令。

```java
public class PriorityMessageQueue<T extends Comparable> {

  ...

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

  ...
}
```

`QueueManager` 有一个 `PriorityMessageQueue`，可以很容易地发布 `publishMessage` 和
`接收消息`。

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
`Worker` 不断轮询 `QueueManager` 以获得最高优先级的消息并对其进行处理。

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

这是我们如何创建 QueueManager 的实例并使用以下方法处理消息的完整示例
'工人'。

```java
    var queueManager = new QueueManager(100);

    for (var i = 0; i < 100; i++) {
      queueManager.publishMessage(new Message("Low Message Priority", 0));
    }

    for (var i = 0; i < 100; i++) {
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


## 类图

![alt text](./etc/priority-queue.urm.png "Priority Queue pattern class diagram")

## 适用性
在以下情况下使用优先队列模式：

* 系统必须处理可能具有不同优先级的多个任务。
* 不同的用户或租户应该有不同的优先级。

## 鸣谢

* [Priority Queue pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/priority-queue)
