---
title: Monitor
category: Concurrency
language: en
tag:
    - Encapsulation
    - Fault tolerance
    - Isolation
    - Synchronization
    - Thread management
---

## Also known as

* Synchronized Block

## Intent

The Monitor design pattern is used to synchronize concurrent operations by encapsulating shared resources in such a way that only one thread can access them at a time, ensuring thread safety.

## Explanation

Real-world example

> Imagine a shared office printer that several employees need to use. The printer can only handle one print job at a time to avoid mixing up pages from different documents. This scenario is analogous to the Monitor design pattern in programming.
>
> In this example, the printer represents the shared resource, and the employees are analogous to threads. A system is set up where each employee must request access to the printer before starting their print job. This system ensures that only one employee (or "thread") can use the printer at a time, preventing any overlap or interference between jobs. Once a print job is complete, the next employee in the queue can access the printer. This mechanism mirrors the Monitor pattern's way of controlling access to a shared resource, ensuring orderly and safe use by multiple "threads" (employees).

In plain words

> Monitor pattern is used to enforce single-threaded access to data. Only one thread at a time is allowed to execute code within the monitor object.

Wikipedia says

> In concurrent programming (also known as parallel programming), a monitor is a synchronization construct that allows threads to have both mutual exclusion and the ability to wait (block) for a certain condition to become false. Monitors also have a mechanism for signaling other threads that their condition has been met.

**Programmatic Examples**

The Monitor design pattern is a synchronization technique used in concurrent programming to ensure that only one thread can execute a particular section of code at a time. It is a method of wrapping and hiding the synchronization primitives (like semaphores or locks) within the methods of an object. This pattern is useful in situations where race conditions could occur.

In the provided code, the `Bank` class is an example of the Monitor pattern. The `Bank` class has several methods that are marked as `synchronized`. This means that only one thread can execute these methods at a time, ensuring that the bank's state remains consistent even when accessed from multiple threads.

Here is a simplified version of the `Bank` class with additional comments:

```java
public class Bank {

    @Getter
    private final int[] accounts;

    public Bank(int accountNum, int baseAmount) {
        accounts = new int[accountNum];
        Arrays.fill(accounts, baseAmount);
    }

    public synchronized void transfer(int accountA, int accountB, int amount) {
        // Only one thread can execute this method at a time due to the 'synchronized' keyword.
        if (accounts[accountA] >= amount && accountA != accountB) {
            accounts[accountB] += amount;
            accounts[accountA] -= amount;
        }
    }

    public synchronized int getBalance() {
        // Only one thread can execute this method at a time due to the 'synchronized' keyword.
        int balance = 0;
        for (int account : accounts) {
            balance += account;
        }
        return balance;
    }

    public synchronized int getBalance(int accountNumber) {
        // Only one thread can execute this method at a time due to the 'synchronized' keyword.
        return accounts[accountNumber];
    }
}
```

In the `Main` class, multiple threads are created to perform transactions on the bank accounts. The `Bank` class, acting as a monitor, ensures that these transactions are performed in a thread-safe manner.

```java
public class Main {

    private static final int NUMBER_OF_THREADS = 5;
    private static final int BASE_AMOUNT = 1000;
    private static final int ACCOUNT_NUM = 4;

    public static void runner(Bank bank, CountDownLatch latch) {
        try {
            SecureRandom random = new SecureRandom();
            Thread.sleep(random.nextInt(1000));
            for (int i = 0; i < 1000000; i++) {
                bank.transfer(random.nextInt(4), random.nextInt(4), random.nextInt(0, BASE_AMOUNT));
            }
            latch.countDown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var bank = new Bank(ACCOUNT_NUM, BASE_AMOUNT);
        var latch = new CountDownLatch(NUMBER_OF_THREADS);
        var executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            executorService.execute(() -> runner(bank, latch));
        }

        latch.await();
    }
}
```

In this example, the `Bank` class is the monitor, and the `transfer` method is the critical section that needs to be executed in a mutually exclusive manner. The `synchronized` keyword in Java is used to implement the Monitor pattern, ensuring that only one thread can execute the `transfer` method at a time.

## Applicability

The Monitor design pattern should be used in situations where you have shared resources that need to be accessed and manipulated by multiple threads or processes concurrently. This pattern is particularly useful in scenarios where synchronization is necessary to prevent race conditions, data corruption, and inconsistent states. Here are some situations where you should consider using the Monitor pattern:

1. **Shared Data**: When your application involves shared data structures, variables, or resources that need to be accessed and updated by multiple threads. Monitors ensure that only one thread can access the shared resource at a time, preventing conflicts and ensuring data consistency.

2. **Critical Sections**: When you have critical sections of code that need to be executed by only one thread at a time. Critical sections are portions of code where shared resources are manipulated, and concurrent access could lead to problems. Monitors help ensure that only one thread can execute the critical section at any given time.

3. **Thread Safety**: When you need to ensure thread safety without relying solely on low-level synchronization mechanisms like locks and semaphores. Monitors provide a higher-level abstraction that encapsulates synchronization and resource management.

4. **Waiting and Signaling**: When you have scenarios where threads need to wait for certain conditions to be met before proceeding. Monitors often include mechanisms for threads to wait for specific conditions and for other threads to notify them when the conditions are satisfied.

5. **Deadlock Prevention**: When you want to prevent deadlocks by providing a structured way to acquire and release locks on shared resources. Monitors help avoid common deadlock scenarios by ensuring that resource access is well-managed.

6. **Concurrent Data Structures**: When you're implementing concurrent data structures, such as queues, stacks, or hash tables, where multiple threads need to manipulate the structure while maintaining its integrity.

7. **Resource Sharing**: When multiple threads need to share limited resources, like connections to a database or access to a network socket. Monitors can help manage the allocation and release of these resources in a controlled manner.

8. **Improved Maintainability**: When you want to encapsulate synchronization logic and shared resource management within a single object, improving code organization and making it easier to reason about concurrency-related code.

However, it's important to note that the Monitor pattern might not be the best fit for all concurrency scenarios. In some cases, other synchronization mechanisms like locks, semaphores, or concurrent data structures might be more suitable. Additionally, modern programming languages and frameworks often provide higher-level concurrency constructs that abstract away the complexities of low-level synchronization.

Before applying the Monitor pattern, it's recommended to thoroughly analyze your application's concurrency requirements and choose the synchronization approach that best suits your needs, taking into consideration factors like performance, complexity, and available language features.

## Known Uses

* Java's synchronized methods and blocks.
* Implementations of concurrent data structures like Vector and Hashtable in the Java Collections Framework.

## Consequences

Benefits:

* Ensures mutual exclusion, preventing race conditions.
* Simplifies the complexity of thread management by providing a clear structure for resource access.

Trade-offs:

* Can lead to decreased performance due to locking overhead.
* Potential for deadlocks if not carefully designed.

## Related Patterns

Semaphore: Used to control access to a common resource by multiple threads; Monitor uses a binary semaphore concept at its core.
Mutex: Another mechanism for ensuring mutual exclusion; Monitor is a higher-level construct often implemented using mutexes.

## Credits

* [Concurrency: State Models & Java Programs](https://amzn.to/4dxxjUX)
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
