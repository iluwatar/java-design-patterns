---
title: Double Checked Locking
category: Idiom
language: en
tag:
  - Performance
---

## Intent

Reduce the overhead of acquiring a lock by first testing the
locking criterion (the "lock hint") without actually acquiring the lock. Only
if the locking criterion check indicates that locking is required does the
actual locking logic proceed.

## Class diagram

![alt text](./etc/double_checked_locking_1.png "Double Checked Locking")

## Applicability

Use the Double Checked Locking pattern when

* there is a concurrent access in object creation, e.g. singleton, where you want to create single instance of the same
  class and checking if it's null or not maybe not be enough when there are two or more threads that checks if instance
  is null or not.
* there is a concurrent access on a method where method's behaviour changes according to the some constraints and these
  constraint change within this method.

## Explanation

Real-world example

> Picture a computer store where customers can purchase computers and accessories. In this busy store, several cashiers
> assist multiple clients simultaneously. When you request a product, the cashier first checks if it's in stock. If it is,
> they retrieve the storage key. Before heading to the storage area, they double-check that no one else has purchased the
> product in the meantime

In plain words

> Double-checked locking pattern limits the overhead of acquiring lock only to the cases when there is a chance that
> requirements for the operation to be executed are met.

Wikipedia says

> In software engineering, double-checked locking (also known as "double-checked locking optimization") is a software
> design pattern used to reduce the overhead of acquiring a lock by testing the locking criterion (the "lock hint") before
> acquiring the lock. Locking occurs only if the locking criterion check indicates that locking is required.

**Programmatic Example**

We have an Item class and Inventory representing a storage for items. This storage has limited capacity indicated
by `inventorySize` property.

```java
public class Item {
}

public class Inventory {
  private final int inventorySize;
  private final List<Item> items;
  private final Lock lock;

  public Inventory(int inventorySize) {
    this.inventorySize = inventorySize;
    this.items = new ArrayList<>(inventorySize);
    this.lock = new ReentrantLock();
  }
  ...
}
```

We can add an Item to Inventory only when the number of already added items is lower than defined `inventorySize`. We
check this condition twice: first before lock is acquired, to avoid acquiring when there is no chance to add new item,
and the second time after the lock is acquired, because in the meantime other thread may have added item and condition
result may have changed.

```java
public class Inventory {
  ...
  public boolean addItem(Item item) {
    if (items.size() < inventorySize) {
      lock.lock();
      try {
        if (items.size() < inventorySize) {
          items.add(item);
          var thread = Thread.currentThread();
          LOGGER.info("{}: items.size()={}, inventorySize={}", thread, items.size(), inventorySize);
          return true;
        }
      } finally {
        lock.unlock();
      }
    }
    return false;
  }
}
```
