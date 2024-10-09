---
title: Balking
shortTitle: Balking
category: Concurrency
language: zh
tag:
 - Decoupling
---

## 意图

止步模式用于防止对象在不完整或不合适的状态下执行某些代码。

## 解释

真实世界例子

> 洗衣机中有一个开始按钮，用于启动衣物洗涤。当洗衣机处于非活动状态时，按钮将按预期工作，但是如果已经在洗涤，则按钮将不起任何作用。

通俗地说

> 使用止步模式，仅当对象处于特定状态时才执行特定代码。

维基百科说

> 禁止模式是一种软件设计模式，仅当对象处于特定状态时才对对象执行操作。例如，一个对象读取zip压缩文件并在压缩文件没打开的时候调用get方法，对象将在请求的时候”止步“。

**程序示例**

在此示例中，` WashingMachine`是一个具有两个状态的对象，可以处于两种状态：ENABLED和WASHING。 如果机器已启用，则使用线程安全方法将状态更改为WASHING。 另一方面，如果已经进行了清洗并且任何其他线程执行`wash（）`，则它将不执行该操作，而是不执行任何操作而返回。

这里是`WashingMachine` 类相关的部分。

```java
@Slf4j
public class WashingMachine {

  private final DelayProvider delayProvider;
  private WashingMachineState washingMachineState;

  public WashingMachine(DelayProvider delayProvider) {
    this.delayProvider = delayProvider;
    this.washingMachineState = WashingMachineState.ENABLED;
  }

  public WashingMachineState getWashingMachineState() {
    return washingMachineState;
  }

  public void wash() {
    synchronized (this) {
      var machineState = getWashingMachineState();
      LOGGER.info("{}: Actual machine state: {}", Thread.currentThread().getName(), machineState);
      if (this.washingMachineState == WashingMachineState.WASHING) {
        LOGGER.error("Cannot wash if the machine has been already washing!");
        return;
      }
      this.washingMachineState = WashingMachineState.WASHING;
    }
    LOGGER.info("{}: Doing the washing", Thread.currentThread().getName());
    this.delayProvider.executeAfterDelay(50, TimeUnit.MILLISECONDS, this::endOfWashing);
  }

  public synchronized void endOfWashing() {
    washingMachineState = WashingMachineState.ENABLED;
    LOGGER.info("{}: Washing completed.", Thread.currentThread().getId());
  }
}
```

这里是一个`WashingMachine`所使用的`DelayProvider`简单接口。

```java
public interface DelayProvider {
  void executeAfterDelay(long interval, TimeUnit timeUnit, Runnable task);
}
```

现在，我们使用`WashingMachine`介绍该应用程序。

```java
  public static void main(String... args) {
    final var washingMachine = new WashingMachine();
    var executorService = Executors.newFixedThreadPool(3);
    for (int i = 0; i < 3; i++) {
      executorService.execute(washingMachine::wash);
    }
    executorService.shutdown();
    try {
      executorService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException ie) {
      LOGGER.error("ERROR: Waiting on executor service shutdown!");
      Thread.currentThread().interrupt();
    }
  }
```

下面是程序的输出。

```
14:02:52.268 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-2: Actual machine state: ENABLED
14:02:52.272 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-2: Doing the washing
14:02:52.272 [pool-1-thread-3] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-3: Actual machine state: WASHING
14:02:52.273 [pool-1-thread-3] ERROR com.iluwatar.balking.WashingMachine - Cannot wash if the machine has been already washing!
14:02:52.273 [pool-1-thread-1] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-1: Actual machine state: WASHING
14:02:52.273 [pool-1-thread-1] ERROR com.iluwatar.balking.WashingMachine - Cannot wash if the machine has been already washing!
14:02:52.324 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - 14: Washing completed.
```

## 类图

![alt text](./etc/balking.png "Balking")

## 适用性


使用止步模式当

* 您只想在对象处于特定状态时才对其调用操作
* 对象通常仅处于容易暂时停止但状态未知的状态

## 相关模式

* [保护性暂挂模式](https://java-design-patterns.com/patterns/guarded-suspension/)
* [双重检查锁模式](https://java-design-patterns.com/patterns/double-checked-locking/)

## 鸣谢

* [Patterns in Java: A Catalog of Reusable Design Patterns Illustrated with UML, 2nd Edition, Volume 1](https://www.amazon.com/gp/product/0471227293/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0471227293&linkId=0e39a59ffaab93fb476036fecb637b99)
