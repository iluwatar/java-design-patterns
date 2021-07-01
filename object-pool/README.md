---
layout: pattern
title: Object Pool
folder: object-pool
permalink: /patterns/object-pool/
categories: Creational
language: en
tags:
 - Game programming
 - Performance
---

## Also known as

Resource Pool

## Intent

When objects are expensive to create and they are needed only for short periods of time it is 
advantageous to utilize the Object Pool pattern. The Object Pool provides a cache for instantiated 
objects tracking which ones are in use and which are available.

## Explanation

Real world example

> In our war game we need to use oliphaunts, massive and mythic beasts, but the problem is that they 
> are extremely expensive to create. The solution is to create a pool of them, track which ones are 
> in-use, and instead of disposing them re-use the instances.   

In plain words

> Object Pool manages a set of instances instead of creating and destroying them on demand. 

Wikipedia says

> The object pool pattern is a software creational design pattern that uses a set of initialized 
> objects kept ready to use – a "pool" – rather than allocating and destroying them on demand.

**Programmatic Example**

Here's the basic `Oliphaunt` class. These giants are very expensive to create.

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

Finally, here's how we utilize the pool.

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

## Class diagram

![alt text](./etc/object-pool.png "Object Pool")

## Applicability

Use the Object Pool pattern when

* The objects are expensive to create (allocation cost).
* You need a large number of short-lived objects (memory fragmentation).
