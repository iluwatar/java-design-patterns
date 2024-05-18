---
title: Reader-Writer Lock
category: Concurrency
language: en
tag: 
    - Asynchronous
    - Concurrency
    - Performance
    - Resource management
    - Synchronization
    - Thread management
---

## Also known as

* Shared-Exclusive Lock

## Intent  

Allow concurrent access to a shared resource for read operations while limiting access for write operations to ensure data consistency.

## Explanation

Real world example 

> Imagine a library where patrons frequently come to read books. The library allows multiple people to read the same book simultaneously without any issues. However, when someone wants to update or correct the content of the book, the library ensures that no one else is reading the book during the update process. This ensures that the book's content remains consistent and accurate, similar to how a Reader-Writer Lock allows multiple threads to read a shared resource concurrently while restricting write access to one thread at a time.

In plain words 

> The Reader-Writer Lock design pattern allows concurrent read access to a shared resource while ensuring exclusive write access to maintain data consistency.

Wikipedia says

> In computer science, a readers–writer (single-writer lock, a multi-reader lock, a push lock, or an MRSW lock) is a synchronization primitive that solves one of the readers–writers problems.

**Programmatic Example**

The Reader-Writer Lock design pattern allows concurrent read access to a shared resource while ensuring exclusive write access to maintain data consistency. This pattern is particularly useful in scenarios where read operations are more frequent than write operations.

First, we have the `Reader` class. This class represents a reader that can read a shared resource when it acquires the read lock. The `run` method locks the read lock, performs the read operation, and then unlocks the read lock.

```java
@Slf4j
public class Reader implements Runnable {

    private Lock readLock;
    private String name;
    private long readingTime;

    public Reader(String name, Lock readLock, long readingTime) {
        this.name = name;
        this.readLock = readLock;
        this.readingTime = readingTime;
    }

    @Override
    public void run() {
        readLock.lock();
        try {
            read();
        } catch (InterruptedException e) {
            LOGGER.info("InterruptedException when reading", e);
            Thread.currentThread().interrupt();
        } finally {
            readLock.unlock();
        }
    }

    public void read() throws InterruptedException {
        LOGGER.info("{} begin", name);
        Thread.sleep(readingTime);
        LOGGER.info("{} finish after reading {}ms", name, readingTime);
    }
}
```

Next, we have the `Writer` class. This class represents a writer that can write to a shared resource when it acquires the write lock. Similar to the `Reader` class, the `run` method locks the write lock, performs the write operation, and then unlocks the write lock.

```java
public class Writer implements Runnable {

    private final Lock writeLock;
    private final String name;
    private final long writingTime;

    public Writer(String name, Lock writeLock, long writingTime) {
        this.name = name;
        this.writeLock = writeLock;
        this.writingTime = writingTime;
    }

    @Override
    public void run() {
        writeLock.lock();
        try {
            write();
        } catch (InterruptedException e) {
            LOGGER.info("InterruptedException when writing", e);
            Thread.currentThread().interrupt();
        } finally {
            writeLock.unlock();
        }
    }

    public void write() throws InterruptedException {
        LOGGER.info("{} begin", name);
        Thread.sleep(writingTime);
        LOGGER.info("{} finished after writing {}ms", name, writingTime);
    }
}
```

Finally, we have the `ReadWriteLock` class. This class controls the access to the shared resource. It allows multiple readers to access the resource concurrently if there's no writer that has the lock. On the other hand, it ensures that only one writer can access the resource at a time.

```java
public class ReadWriteLock implements ReaderWriterLock {

    private final Object readerMutex = new Object();
    private int currentReaderCount;
    private final Set<Object> globalMutex = new HashSet<>();
    private final ReadLock readerLock = new ReadLock();
    private final WriteLock writerLock = new WriteLock();

    public Lock readLock() {
        return readerLock;
    }

    public Lock writeLock() {
        return writerLock;
    }

    private boolean doesWriterOwnThisLock() {
        return globalMutex.contains(writerLock);
    }

    private boolean isLockFree() {
        return globalMutex.isEmpty();
    }

    private class ReadLock implements Lock {
        // Implementation of the read lock
    }

    private class WriteLock implements Lock {
        // Implementation of the write lock
    }
}
```

In the `App` class, we create a fixed thread pool and submit `Reader` and `Writer` tasks to it. The `Reader` tasks acquire the read lock to read the shared resource, and the `Writer` tasks acquire the write lock to write to the shared resource.

## Class diagram

![Reader-Writer Lock](./etc/reader-writer-lock.png "Reader-Writer Lock")

## Applicability

* Use when you need to manage concurrent read and write access to a shared resource.
* Suitable for systems with more frequent read operations than write operations.
* Ideal for situations where read operations can proceed concurrently but write operations require exclusive access.

## Known Uses

* Database management systems to allow multiple transactions to read data simultaneously while ensuring exclusive access for data modifications.
* Filesystems to manage concurrent read and write operations on files.
* In-memory caches where read access is predominant over write access.
* [ReentrantReadWriteLock in Java](https://docs.oracle.com/en/java/javase/17/docs//api/java.base/java/util/concurrent/locks/ReentrantReadWriteLock.html)

## Consequences

Benefits:

* Improves performance in scenarios with a high ratio of read operations to write operations.
* Reduces contention and increases concurrency for read operations.

Trade-offs:

* More complex to implement compared to simple locks.
* Potential for starvation where write operations could be delayed indefinitely if read operations continue to occur.
* Requires careful handling of thread management and synchronization to avoid deadlocks and ensure data consistency.

## Related Patterns

* [Lockable Object](https://java-design-patterns.com/patterns/lockable-object/): Both patterns deal with managing access to shared resources, but Reader-Writer Lock allows higher concurrency for read operations. 

## Credits

* [Concurrent Programming in Java : Design Principles and Patterns](https://amzn.to/4dIBqxL)
* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Readers–writer lock - Wikipedia](https://en.wikipedia.org/wiki/Readers%E2%80%93writer_lock)
* [Readers–writers_problem - Wikipedia](https://en.wikipedia.org/wiki/Readers%E2%80%93writers_problem)
