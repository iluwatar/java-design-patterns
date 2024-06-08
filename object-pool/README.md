---
title: "Object Pool Pattern in Java: Enhancing Performance with Reusable Object Management"
shortTitle: Object Pool
description: "Learn how the Object Pool design pattern improves performance by reusing expensive objects efficiently. Explore examples, benefits, and best practices in Java."
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

## Intent of Object Pool Design Pattern

The Object Pool design pattern in Java manages a pool of reusable objects, optimizing memory management and application performance by recycling objects rather than creating and destroying them repeatedly.

## Detailed Explanation of Object Pool Pattern with Real-World Examples

Real-world example

> Imagine a library with a limited number of study rooms that are frequently in demand. Instead of each student building their own study room whenever they need one, the library manages a pool of available study rooms. When a student needs a study room, they check one out from the pool. After they are done, they return the room back to the pool for others to use. This ensures that the study rooms are efficiently utilized without the need to build new rooms each time, thus saving time and resources, similar to how the Object Pool pattern manages the reuse of expensive objects in software.   

In plain words

> Object Pool manages a set of instances instead of creating and destroying them on demand. 

Wikipedia says

> The object pool pattern is a software creational design pattern that uses a set of initialized objects kept ready to use – a "pool" – rather than allocating and destroying them on demand.

## Programmatic Example of Object Pool Pattern in Java

In our war game we need to use oliphaunts, massive and mythic beasts, but the problem is that they are extremely expensive to create. The solution is to create a pool of them, track which ones are in-use, and instead of disposing them re-use the instances.

Here's the basic `Oliphaunt` class. These giants are very expensive to create.

```java
public class Oliphaunt {

    private static final AtomicInteger counter = new AtomicInteger(0);

    @Getter
    private final int id;

    public Oliphaunt() {
        id = counter.incrementAndGet();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.error("Error occurred: ", e);
        }
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
public static void main(String[] args) {
    var pool = new OliphauntPool();
    LOGGER.info(pool.toString());
    var oliphaunt1 = pool.checkOut();
    String checkedOut = "Checked out {}";

    LOGGER.info(checkedOut, oliphaunt1);
    LOGGER.info(pool.toString());
    var oliphaunt2 = pool.checkOut();
    LOGGER.info(checkedOut, oliphaunt2);
    var oliphaunt3 = pool.checkOut();
    LOGGER.info(checkedOut, oliphaunt3);
    LOGGER.info(pool.toString());
    LOGGER.info("Checking in {}", oliphaunt1);
    pool.checkIn(oliphaunt1);
    LOGGER.info("Checking in {}", oliphaunt2);
    pool.checkIn(oliphaunt2);
    LOGGER.info(pool.toString());
    var oliphaunt4 = pool.checkOut();
    LOGGER.info(checkedOut, oliphaunt4);
    var oliphaunt5 = pool.checkOut();
    LOGGER.info(checkedOut, oliphaunt5);
    LOGGER.info(pool.toString());
}
```

Program output:

```
21:21:55.126 [main] INFO com.iluwatar.object.pool.App -- Pool available=0 inUse=0
21:21:56.130 [main] INFO com.iluwatar.object.pool.App -- Checked out Oliphaunt id=1
21:21:56.132 [main] INFO com.iluwatar.object.pool.App -- Pool available=0 inUse=1
21:21:57.137 [main] INFO com.iluwatar.object.pool.App -- Checked out Oliphaunt id=2
21:21:58.143 [main] INFO com.iluwatar.object.pool.App -- Checked out Oliphaunt id=3
21:21:58.145 [main] INFO com.iluwatar.object.pool.App -- Pool available=0 inUse=3
21:21:58.145 [main] INFO com.iluwatar.object.pool.App -- Checking in Oliphaunt id=1
21:21:58.145 [main] INFO com.iluwatar.object.pool.App -- Checking in Oliphaunt id=2
21:21:58.146 [main] INFO com.iluwatar.object.pool.App -- Pool available=2 inUse=1
21:21:58.146 [main] INFO com.iluwatar.object.pool.App -- Checked out Oliphaunt id=2
21:21:58.146 [main] INFO com.iluwatar.object.pool.App -- Checked out Oliphaunt id=1
21:21:58.147 [main] INFO com.iluwatar.object.pool.App -- Pool available=0 inUse=3
```

## When to Use the Object Pool Pattern in Java

Use the Object Pool pattern when

* You need to frequently create and destroy objects, leading to high resource allocation and deallocation costs.
* The objects are expensive to create and maintain (e.g., database connections, thread pools).
* A fixed number of objects need to be controlled, like in connection pooling.
* Object reuse can significantly improve system performance and resource management.

## Real-World Applications of Object Mother Pattern in Java

* Database connection pooling in Java applications.
* Thread pooling in Java concurrent programming.
* Pooling of socket connections in network applications.
* Object pools in game development for frequently created and destroyed game objects.

## Benefits and Trade-offs of Object Pool Pattern

Benefits:

* Improved Performance: Reduces the overhead of object creation and garbage collection.
* Resource Management: Controls the number of instances, reducing resource contention and limiting resource usage.
* Scalability: Allows the application to handle more requests by reusing a fixed number of objects.

Trade-offs:

* Complexity: Adds complexity to the codebase, requiring careful management of the pool.
* Thread Safety: Requires careful handling of concurrent access to the pool, introducing potential synchronization issues.
* Initialization Cost: Initial creation of the pool can be resource-intensive.

## Related Java Design Patterns

* [Singleton](https://java-design-patterns.com/patterns/singleton/): Ensures a single instance of the pool is used, providing a global point of access.
* [Flyweight](https://java-design-patterns.com/patterns/flyweight/): Shares fine-grained objects to reduce memory usage, complementing object pooling by managing object state efficiently.
* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): Often used to create objects within the pool, abstracting the instantiation process.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
