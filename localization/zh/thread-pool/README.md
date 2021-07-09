---
layout: pattern
title: Thread Pool
folder: thread-pool
permalink: /patterns/thread-pool/zh
categories: Concurrency
language: zh
tags:
 - Performance
---

## 目的
经常会出现要执行的任务时间很短，任务数量很多的情况。
为每个任务创建一个新线程会使系统花费更多的时间来创建和销毁
线程而不是执行实际任务。线程池通过重用现有的来解决这个问题
线程并消除创建新线程的延迟。

## 解释

真实世界的例子

> 我们手头有大量相对较短的任务。我们需要去皮大量的土豆
> 并提供大量咖啡杯。为每个任务创建一个新线程将是一种浪费，所以我们
> 建立线程池。

简单来说

> 线程池是一种并发模式，其中线程被分配一次并在任务之间重用。

维基百科说

> 在计算机编程中，线程池是一种实现并发的软件设计模式
> 在计算机程序中执行。通常也称为复制工人或工人-船员模型，
> 一个线程池维护多个线程等待分配并发任务
> 由监督程序执行。通过维护线程池，模型增加
> 性能并避免由于频繁创建和销毁线程而导致的执行延迟
> 用于短期任务。可用线程数根据计算资源进行调整
> 可供程序使用，例如执行完成后的并行任务队列。

**程序示例**

让我们首先看看我们的任务层次结构。我们有一个基类，然后是具体的`CoffeeMakingTask`
和`PotatoPeelingTask`。
```java
public abstract class Task {

  private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

  private final int id;
  private final int timeMs;

  public Task(final int timeMs) {
    this.id = ID_GENERATOR.incrementAndGet();
    this.timeMs = timeMs;
  }

  public int getId() {
    return id;
  }

  public int getTimeMs() {
    return timeMs;
  }

  @Override
  public String toString() {
    return String.format("id=%d timeMs=%d", id, timeMs);
  }
}

public class CoffeeMakingTask extends Task {

  private static final int TIME_PER_CUP = 100;

  public CoffeeMakingTask(int numCups) {
    super(numCups * TIME_PER_CUP);
  }

  @Override
  public String toString() {
    return String.format("%s %s", this.getClass().getSimpleName(), super.toString());
  }
}

public class PotatoPeelingTask extends Task {

  private static final int TIME_PER_POTATO = 200;

  public PotatoPeelingTask(int numPotatoes) {
    super(numPotatoes * TIME_PER_POTATO);
  }

  @Override
  public String toString() {
    return String.format("%s %s", this.getClass().getSimpleName(), super.toString());
  }
}
```

接下来我们展示一个可运行的 `Worker` 类，线程池将利用它来处理所有的土豆
剥皮和咖啡制作。
```java
@Slf4j
public class Worker implements Runnable {

  private final Task task;

  public Worker(final Task task) {
    this.task = task;
  }

  @Override
  public void run() {
    LOGGER.info("{} processing {}", Thread.currentThread().getName(), task.toString());
    try {
      Thread.sleep(task.getTimeMs());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
```

现在我们准备好展示完整的示例。
```java
    LOGGER.info("Program started");

    // Create a list of tasks to be executed
    var tasks = List.of(
        new PotatoPeelingTask(3),
        new PotatoPeelingTask(6),
        new CoffeeMakingTask(2),
        new CoffeeMakingTask(6),
        new PotatoPeelingTask(4),
        new CoffeeMakingTask(2),
        new PotatoPeelingTask(4),
        new CoffeeMakingTask(9),
        new PotatoPeelingTask(3),
        new CoffeeMakingTask(2),
        new PotatoPeelingTask(4),
        new CoffeeMakingTask(2),
        new CoffeeMakingTask(7),
        new PotatoPeelingTask(4),
        new PotatoPeelingTask(5));

    // Creates a thread pool that reuses a fixed number of threads operating off a shared
    // unbounded queue. At any point, at most nThreads threads will be active processing
    // tasks. If additional tasks are submitted when all threads are active, they will wait
    // in the queue until a thread is available.
    var executor = Executors.newFixedThreadPool(3);

    // Allocate new worker for each task
    // The worker is executed when a thread becomes
    // available in the thread pool
    tasks.stream().map(Worker::new).forEach(executor::execute);
    // All tasks were executed, now shutdown
    executor.shutdown();
    while (!executor.isTerminated()) {
      Thread.yield();
    }
    LOGGER.info("Program finished");
```

## 类图

![alt text](./etc/thread_pool_urm.png "Thread Pool")

## 适用性
使用线程池模式时

* 你有大量的短期任务要并行执行

## 鸣谢
* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0134685997&linkId=e1b9ddd5e669591642c4f30d40cd9f6b)
* [Java Concurrency in Practice](https://www.amazon.com/gp/product/0321349601/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321349601&linkId=fbedb3bad3c6cbead5afa56eea39ed59)
