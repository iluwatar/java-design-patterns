---
title: "Lockable Object Pattern in Java: Implementing Robust Synchronization Mechanisms"
shortTitle: Lockable Object
description: "Learn about the Lockable Object design pattern in Java. Explore its usage, real-world examples, benefits, and how it ensures thread safety and resource management in multithreaded environments."
category: Concurrency
language: en
tag:
  - Decoupling
  - Encapsulation
  - Security
  - Synchronization
  - Thread management
---

## Also known as

* Resource Lock
* Mutual Exclusion Object

## Intent of Lockable Object Design Pattern

The Lockable Object pattern in Java aims to control access to a shared resource in a multithreaded environment, ensuring thread safety by providing a mechanism for resource locking, ensuring that only one thread can access the resource at a time.

## Detailed Explanation of Lockable Object Pattern with Real-World Examples

Real-world example

> Imagine a shared printer in a busy office as an analogous real-world example of the Lockable Object design pattern in Java. This pattern ensures that only one thread can access the resource at a time, thus maintaining concurrency control and synchronization. Multiple employees need to print documents throughout the day, but the printer can only handle one print job at a time. To manage this, there's a locking system in place—much like a lockable object in programming—that ensures when one person is printing, others must wait their turn. This prevents print jobs from overlapping or interfering with each other, ensuring that each document is printed correctly and in the order it was sent, mirroring the concept of thread synchronization and resource locking in software development.

In plain words

> The Lockable Object design pattern ensures safe access to a shared resource in a multithreaded environment by allowing only one thread to access the resource at a time through locking mechanisms.

Wikipedia says

> In computer science, a lock or mutex (from mutual exclusion) is a synchronization primitive that prevents state from being modified or accessed by multiple threads of execution at once. Locks enforce mutual exclusion concurrency control policies, and with a variety of possible methods there exist multiple unique implementations for different applications.

## Programmatic Example of Lockable Object Pattern in Java

The Lockable Object pattern is a concurrency control design pattern in Java that allows only one thread to access a shared resource at a time, ensuring mutual exclusion and preventing data corruption. Instead of using the `synchronized` keyword on the methods to be synchronized, the object which implements the Lockable interface handles the request.

In this example, we have a `SwordOfAragorn` object that implements the `Lockable` interface. Multiple `Creature` objects, represented by `Elf`, `Orc`, and `Human` classes, are trying to acquire the sword. Each `Creature` is wrapped in a `Feind` object that implements `Runnable`, allowing each creature to attempt to acquire the sword in a separate thread.

Here's the `Lockable` interface:

```java
public interface Lockable {
  boolean isLocked();
  Creature getLocker();
  boolean acquire(Creature creature);
  void release(Creature creature);
}
```

The `SwordOfAragorn` class implements this interface:

```java
public class SwordOfAragorn implements Lockable {
  // Implementation details...
}
```

The `Creature` class and its subclasses (`Elf`, `Orc`, `Human`) represent different creatures that can try to acquire the sword:

```java
public abstract class Creature {
  // Implementation details...
}

public class Elf extends Creature {
  // Implementation details...
}

public class Orc extends Creature {
  // Implementation details...
}

public class Human extends Creature {
  // Implementation details...
}
```

The `Feind` class wraps a `Creature` and a `Lockable` object, and implements `Runnable`:

```java
public class Feind implements Runnable {
  private final Creature creature;
  private final Lockable target;

  public Feind(@NonNull Creature feind, @NonNull Lockable target) {
    this.creature = feind;
    this.target = target;
  }

  @Override
  public void run() {
    if (!creature.acquire(target)) {
      fightForTheSword(creature, target.getLocker(), target);
    } else {
      LOGGER.info("{} has acquired the sword!", target.getLocker().getName());
    }
  }

  // Additional methods...
}
```

In the `App` class, multiple `Feind` objects are created and submitted to an `ExecutorService`, each in a separate thread:

```java
public class App implements Runnable {
  @Override
  public void run() {
    var sword = new SwordOfAragorn();
    List<Creature> creatures = new ArrayList<>();
    // Creation of creatures...
    ExecutorService service = Executors.newFixedThreadPool(totalFiends);
    for (var i = 0; i < totalFiends; i = i + MULTIPLICATION_FACTOR) {
      service.submit(new Feind(creatures.get(i), sword));
      service.submit(new Feind(creatures.get(i + 1), sword));
      service.submit(new Feind(creatures.get(i + 2), sword));
    }
    // Additional code...
  }
}
```

This example demonstrates the Lockable Object pattern by showing how multiple threads can attempt to acquire a lock on a shared resource, with only one thread being able to acquire the lock at a time.

## Detailed Explanation of Lockable Object Pattern with Real-World Examples

![Lockable Object](./etc/lockable-object.urm.png "Lockable Object class diagram")

## When to Use the Lockable Object Pattern in Java

* Use the Lockable Object pattern in Java when you need to prevent data corruption by multiple threads accessing a shared resource concurrently, ensuring thread safety and robust shared resource management.
* Suitable for systems where thread safety is critical and data integrity must be maintained across various operations.

## Real-World Applications of Lockable Object Pattern in Java

* Java’s synchronized keyword and the Lock interfaces in the java.util.concurrent.locks package implement lockable objects to manage synchronization.

## Benefits and Trade-offs of Lockable Object Pattern

Benefits:

* Ensures data consistency and prevents race conditions.
* Provides clear structure for managing access to shared resources.

Trade-offs:

* Can lead to decreased performance due to overhead of acquiring and releasing locks.
* Potential for deadlocks if not implemented and managed carefully.

## Related Java Design Patterns

* [Monitor Object](https://java-design-patterns.com/patterns/monitor/): Both patterns manage access to shared resources; Monitor Object combines synchronization and encapsulation of the condition variable.
* [Read/Write Lock](https://java-design-patterns.com/patterns/reader-writer-lock/): Specialization of Lockable Object for scenarios where read operations outnumber write operations.

## References and Credits

* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
* [Pattern-Oriented Software Architecture Volume 2: Patterns for Concurrent and Networked Objects](https://amzn.to/3UgC24V)
