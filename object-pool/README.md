---
title: Object Pool
category: Creational
language: en
tag:
    - Game programming
    - Instantiation
    - Memory management
    - Performance
    - Resource management
    - Scalability
---

## Also known as

* Resource Pool

## Intent

The Object Pool design pattern manages a pool of reusable objects, optimizing resource use by recycling objects rather than creating and destroying them repeatedly.

## Explanation

Real world example

> In our war game we need to use oliphaunts, massive and mythic beasts, but the problem is that they are extremely expensive to create. The solution is to create a pool of them, track which ones are in-use, and instead of disposing them re-use the instances.   

In plain words

> Object Pool manages a set of instances instead of creating and destroying them on demand. 

Wikipedia says

> The object pool pattern is a software creational design pattern that uses a set of initialized objects kept ready to use – a "pool" – rather than allocating and destroying them on demand.

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

Next, we present the `ObjectPool` and more specifically `OliphauntPool`.

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

![Object Pool](./etc/object-pool.png "Object Pool")

## Applicability

Use the Object Pool pattern when

* You need to frequently create and destroy objects, leading to high resource allocation and deallocation costs.
* The objects are expensive to create and maintain (e.g., database connections, thread pools).
* A fixed number of objects need to be controlled, like in connection pooling.
* Object reuse can significantly improve system performance and resource management.

## Known Uses

* Database connection pooling in Java applications.
* Thread pooling in Java concurrent programming.
* Pooling of socket connections in network applications.
* Object pools in game development for frequently created and destroyed game objects.

## Consequences

Benefits:

* Improved Performance: Reduces the overhead of object creation and garbage collection.
* Resource Management: Controls the number of instances, reducing resource contention and limiting resource usage.
* Scalability: Allows the application to handle more requests by reusing a fixed number of objects.

Trade-offs:

* Complexity: Adds complexity to the codebase, requiring careful management of the pool.
* Thread Safety: Requires careful handling of concurrent access to the pool, introducing potential synchronization issues.
* Initialization Cost: Initial creation of the pool can be resource-intensive.

## Related Patterns

[Singleton](https://java-design-patterns.com/patterns/singleton/): Ensures a single instance of the pool is used, providing a global point of access.
[Flyweight](https://java-design-patterns.com/patterns/flyweight/): Shares fine-grained objects to reduce memory usage, complementing object pooling by managing object state efficiently.
[Factory Method](https://java-design-patterns.com/patterns/factory-method/): Often used to create objects within the pool, abstracting the instantiation process.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
