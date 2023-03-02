---
title: Reader Writer Lock
category: Concurrency
language: en
tag: 
 - Performance
---

## Intent  

Regular lock does not distinguish the ‘read lock’ and ‘write lock’, as when access the data structure patterns 
consists of many threads reading the data, each thread will have to lock it which produces unnecessary serialization.
The existence of reader-writer lock resolves this issue as it is well known as 
“multiple concurrent readers, single writer locks”, used to consist of multiple threads reading the data concurrently 
and allow only one thread to write or modify the data. All others (readers or writers) will be blocked while the writer 
is modifying or writing the data and unblocked until the writer finishes writing. 

## Explanation

Real world example 

> Consider if we obtain a database for bank accounts. If Alice wants to transfer from account1 to account2, at the 
> same time Bob transfer money from account2 to account3. Alice will first read the totals of account1 and account2. Then, Bob's
> transaction executed completely. Alice is now working with outdated values, therefore, the total amount in account2
> would be incorrect. With transactions, Bob would have to wait until Alice finishes her process of the accounts. 




In plain words 

> Reader-writer lock enables either multiple readers or single writer to hold the lock at any given time.


Wikipedia says 
> In computer science, a readers–writer (single-writer lock, a multi-reader lock, a push lock, or an MRSW lock) 
> is a synchronization primitive that solves one of the readers–writers problems.


**Programmatic Example**

In our programmatic example, we demonstrate the implementation of the access to either reader or writer. 
We first create a `Reader` class which read when it acquired the read lock. It creates the reader and simulate the read operation.
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

    public Reader(String name, Lock readLock) {
        this(name, readLock, 250L);
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


In the  `Writer` class, we operate write when it acquired the writer lock. It follows the similar process as the  `Reader` class
which creates the writer and simulate the write operation.
```java

public class Writer implements Runnable {
    
    private final Lock writeLock;
    private final String name;
    private final long writingTime;

    public Writer(String name, Lock writeLock) {
        this(name, writeLock, 250L);
    }

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


Now, in the `ReadWriteLock` class which would take the responsibilities to control the access for either the reader or the writer. 
In the `ReadLock` class, it restricts that if there's no writer that gets the lock, then multiple readers could be access concurrently.
In the `WriteLock` class, it restricts that only one writer could be accessed.

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

        @Override
        public void lock() {
            synchronized (readerMutex) {
                currentReaderCount++;
                if (currentReaderCount == 1) {
                    acquireForReaders();
                }
            }
        }
        
        @Override
        public void unlock() {
            synchronized (readerMutex) {
                currentReaderCount--;

                if (currentReaderCount == 0) {
                    synchronized (globalMutex) {
                        globalMutex.remove(this);
                        globalMutex.notifyAll();
                    }
                }
            }

        }
    }


    private class WriteLock implements Lock {

        @Override
        public void lock() {
            synchronized (globalMutex) {
                while (!isLockFree()) {
                    try {
                        globalMutex.wait();
                    } catch (InterruptedException e) {
                        LOGGER.info("InterruptedException while waiting for globalMutex to begin writing", e);
                        Thread.currentThread().interrupt();
                    }
                }
                globalMutex.add(this);
            }
        }

        @Override
        public void unlock() {
            synchronized (globalMutex) {
                globalMutex.remove(this);
                globalMutex.notifyAll();
            }
        }

    }
}





```


## Class diagram
![alt text](./etc/reader-writer-lock.png "Reader writer lock")

## Applicability  

Use the Reader-writer lock when:
* You need to increase the performance of resource synchronize for multiple thread, in particularly there are mixed read/write operations.
* In the bank transaction system, you want to ensure when two users are transacting the same account, one people will wait until the other finishes.





## Credits

* [Readers–writer lock](https://en.wikipedia.org/wiki/Readers%E2%80%93writer_lock)
* [Readers–writers_problem](https://en.wikipedia.org/wiki/Readers%E2%80%93writers_problem)

