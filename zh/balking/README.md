---
layout: pattern
title: Balking
folder: balking
permalink: /patterns/balking/
categories: Concurrency
tags:
 - Decoupling
---

## 含义

阻止模式用于防止一个对象在不完整或不适当的状态下执行某段代码。

## 解释

真实世界的案例

> 洗衣机里有一个用于启动衣物洗涤的启动按钮。当洗衣机没有启动时，该按钮可以正常按下生效，但如果洗衣机已经在洗衣服了，再按下按钮就不生效了。

简而言之

> 使用阻止模式，只有当对象处于特定状态时，才会执行某段代码。

维基百科的解释

> 阻止模式是一种软件设计模式，它只在对象处于特定状态时对其执行动作。例如，如果一个对象读取 ZIP 文件，当 ZIP 文件没有打开时，如果一个方法在该对象上调用一个获取方法，该对象就会对阻止这个请求。

**编程示例**

在这个例子的实现中，`WashingMachine` 对象存在 2 种状态： `ENABLED` 和 `WASHING`。如果该对象处于 `ENABLED` 状态，则使用一个线程安全的方法可以其状态改变为 `WASHING`。在另一方面，如果它已经处于 `WASHING` 状态，而任何其他线程执行了 `wash()`，它不会执行该指令，而是什么都不做就返回。

以下是 `WashingMachine` 类的相关代码。

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

以下是 `WashingMachine` 使用的简单 `DelayProvider` 接口。

```java
public interface DelayProvider {
  void executeAfterDelay(long interval, TimeUnit timeUnit, Runnable task);
}
```

现在我们介绍一下使用  `WashingMachine` 的应用。

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

以下是程序的控制台输出。

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

![alt text](../../balking/etc/balking.png "Balking")

## 适用场景

在以下情况下可以使用阻止模式：

* 你想要在某个对象上调用一个动作，只有当该对象处于特定状态时才允许该调用。
* 对象一般只处于容易暂时阻止的状态，只不过该时间是未知的。

## 教学

* [Guarded Suspension Pattern](https://java-design-patterns.com/patterns/guarded-suspension/)
* [Double Checked Locking Pattern](https://java-design-patterns.com/patterns/double-checked-locking/)

## 引用

* [Patterns in Java: A Catalog of Reusable Design Patterns Illustrated with UML, 2nd Edition, Volume 1](https://www.amazon.com/gp/product/0471227293/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0471227293&linkId=0e39a59ffaab93fb476036fecb637b99)
