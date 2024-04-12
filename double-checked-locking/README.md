---
title: Double-Checked Locking
category: Concurrency
language: en
tag:
    - Optimization
    - Performance
---

## Intent

The Double-Checked Locking pattern aims to reduce the overhead of acquiring a lock by first testing the locking criterion (the 'lock hint') without actually acquiring the lock. Only if the locking criterion check indicates that locking is necessary does the actual locking logic proceed.

## Explanation

Real world example

> In a company with a high-value equipment room, employees first check a visible sign to see if the room is locked. If the sign shows it's unlocked, they enter directly; if locked, they use a security keycard for access. This two-step verification process efficiently manages security without unnecessary use of the electronic lock system, mirroring the Double-Checked Locking pattern used in software to minimize resource-intensive operations.

In plain words

> The Double-Checked Locking pattern in software minimizes costly locking operations by first checking the lock status in a low-cost manner before proceeding with a more resource-intensive lock, ensuring efficiency and thread safety during object initialization.

Wikipedia says

> In software engineering, double-checked locking (also known as "double-checked locking optimization") is a software design pattern used to reduce the overhead of acquiring a lock by testing the locking criterion (the "lock hint") before acquiring the lock. Locking occurs only if the locking criterion check indicates that locking is required.

**Programmatic Example**

The Double-Checked Locking pattern is used in the HolderThreadSafe class to ensure that the Heavy object is only created once, even when accessed from multiple threads.  Here's how it works:

Check if the object is initialized (first check): If it is, return it immediately.

```java
if (heavy == null) {
  // ...
}
```

Synchronize the block of code where the object is created: This ensures that only one thread can create the object.

```java
synchronized (this) {
  // ...
}
```

Check again if the object is initialized (second check): If another thread has already created the object by the time the current thread enters the synchronized block, return the created object.

```java
if (heavy == null) {
  heavy = new Heavy();
}
```

Return the created object.

```java
return heavy;
```

Here's the complete code for the HolderThreadSafe class:

```java
public class HolderThreadSafe {

  private Heavy heavy;

  public HolderThreadSafe() {
    LOGGER.info("Holder created");
  }

  public synchronized Heavy getHeavy() {
    if (heavy == null) {
      synchronized (this) {
        if (heavy == null) {
          heavy = new Heavy();
        }
      }
    }
    return heavy;
  }
}
```

In this code, the Heavy object is only created when the getHeavy() method is called for the first time. This is known as lazy initialization. The double-checked locking pattern is used to ensure that the Heavy object is only created once, even when the getHeavy() method is called from multiple threads simultaneously.

## Class diagram

![Double-Check Locking](./etc/double_checked_locking_1.png "Double-Checked Locking")

## Applicability

This pattern is used in scenarios where:

* There is a significant performance cost associated with acquiring a lock, and
* The lock is not frequently needed.

## Known Uses

* Singleton pattern implementation in multithreading environments.
* Lazy initialization of resource-intensive objects in Java applications.

## Consequences

Benefits:

* Performance gains from avoiding unnecessary locking after the object is initialized.
* Thread safety is maintained for critical initialization sections.

Trade-offs:

* Complex implementation can lead to mistakes, such as incorrect publishing of objects due to memory visibility issues.
* In Java, it can be redundant or broken in some versions unless volatile variables are used with care.

## Related Patterns

* [Singleton](https://java-design-patterns.com/patterns/singleton/): Double-Checked Locking is often used in implementing thread-safe Singletons.
* [Lazy Loading](https://java-design-patterns.com/patterns/lazy-loading/): Shares the concept of delaying object creation until necessary.

## Credits

* [Java Concurrency in Practice](https://amzn.to/4aIAPKa)
* [Effective Java](https://amzn.to/3xx7KDh)
