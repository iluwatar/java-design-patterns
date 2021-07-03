---
layout: pattern
title: Object Pool
folder: object-pool
permalink: /patterns/object-pool/zh
categories: Creational
language: zh
tags:
 - Game programming
 - Performance
---

## 又称为
资源池

## 意图

当创建对象的成本很高并且只在短时间内需要它们时，
有利于利用对象池模式。对象池为实例化提供了缓存
对象跟踪哪些正在使用，哪些可用。

## 解释

真实世界的例子

> 在我们的战争游戏中，我们需要使用 oliphaunts，巨大的和神话般的野兽，但问题是它们
> 创建起来非常昂贵。解决方案是创建一个池，跟踪哪些是
> 使用中，而不是处理它们，而是重新使用实例。

简单来说

> 对象池管理一组实例，而不是按需创建和销毁它们。

维基百科说

> 对象池模式是一种软件创建设计模式，它使用一组初始化
> 对象随时可用——一个“池”——而不是按需分配和销毁它们。

**程序示例**

这是基本的`Oliphaunt` 类。这些巨人的创造成本非常高。

```java
public class Oliphaunt {

  private static final AtomicInteger counter = new AtomicInteger(0);

  private final int id;

  public Oliphaunt() {
    id = counter.incrementAndGet();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public int getId() {
    return id;
  }

  @Override
  public String toString() {
    return String.format("Oliphaunt id=%d", id);
  }
}
```

Next we present the `ObjectPool` and more specifically `OliphauntPool`.

```java
public abstract class ObjectPool<T> {

  private final Set<T> available = new HashSet<>();
  private final Set<T> inUse = new HashSet<>();

  protected abstract T create();

  public synchronized T checkOut() {
    if (available.isEmpty()) {
      available.add(create());
    }
    var instance = available.iterator().next();
    available.remove(instance);
    inUse.add(instance);
    return instance;
  }

  public synchronized void checkIn(T instance) {
    inUse.remove(instance);
    available.add(instance);
  }

  @Override
  public synchronized String toString() {
    return String.format("Pool available=%d inUse=%d", available.size(), inUse.size());
  }
}

public class OliphauntPool extends ObjectPool<Oliphaunt> {

  @Override
  protected Oliphaunt create() {
    return new Oliphaunt();
  }
}
```

最后，这是我们如何利用池。

```java
    var pool = new OliphauntPool();
    var oliphaunt1 = pool.checkOut();
    var oliphaunt2 = pool.checkOut();
    var oliphaunt3 = pool.checkOut();
    pool.checkIn(oliphaunt1);
    pool.checkIn(oliphaunt2);
    var oliphaunt4 = pool.checkOut();
    var oliphaunt5 = pool.checkOut();
```

Program output:

```
Pool available=0 inUse=0
Checked out Oliphaunt id=1
Pool available=0 inUse=1
Checked out Oliphaunt id=2
Checked out Oliphaunt id=3
Pool available=0 inUse=3
Checking in Oliphaunt id=1
Checking in Oliphaunt id=2
Pool available=2 inUse=1
Checked out Oliphaunt id=2
Checked out Oliphaunt id=1
Pool available=0 inUse=3
```

## 类图

![alt text](./etc/object-pool.png "Object Pool")

## 适用性
在以下情况下使用对象池模式

* 创建对象的成本很高（分配成本）。
* 你需要大量的短期对象（内存碎片）。
