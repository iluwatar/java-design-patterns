---
Title: "Coarse-Grained Lock Pattern in Java: Simplifying Thread-Safe Operations"
Short Title: Coarse-Grained Lock
Description: "Coarse-Grained Lock pattern ensures thread safety by holding a global lock for related objects instead of multiple locks."
Category: Concurrency
Tags:

    Concurrency
    Synchronization
    Thread Safety
    Locking Mechanisms
---


## Intent of Coarse-Grained Lock Design Pattern

The Coarse-Grained Lock pattern simplifies synchronization in multithreaded systems by applying a single lock to a group of operations or a shared resource. This design reduces the complexity of managing multiple fine-grained locks, albeit at the cost of some parallelism.
## Detailed Explanation of Coarse-Grained Lock Pattern with Real-World Examples

Real-world example

> Imagine a bank system where multiple threads handle customer transactions such as deposits, withdrawals, and balance checks. To ensure account integrity, a coarse-grained lock can be applied to the entire account object. While this reduces concurrency, it simplifies synchronization logic and prevents data inconsistencies.

In plain words

> A Coarse-Grained Lock is a single lock used to control access to a shared resource or related group of operations. It’s a simple way to ensure thread safety when fine-grained locking would be too complex to manage.


## Programmatic Example of Coarse-Grained Locks Pattern in Java

The Coarse-Grained Lock pattern is used to synchronize access to multiple related objects. In this example, we demonstrate how to use a single lock to coordinate updates to a customer and their associated addresses.

**Locking the Address and Customer Objects**

```java
package com.iluwatar.coarse.grained;

/**
 * Demonstrates the Coarse-Grained Lock pattern.
 */
public class App {

    /**
     * Program entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Create a lock instance to synchronize access
        Lock lock = new Lock();

        // Create a customer and their addresses
        Customer customer = new Customer(55, "John");
        Address address1 = new Address(customer.getCustomerId(), 1, "Chicago");
        Address address2 = new Address(customer.getCustomerId(), 2, "Houston");

        // Use the lock to synchronize modifications to customer and addresses
        lock.synchronizedMethod(() -> {
            customer.setName("Smith");
            address1.setCity("Dallas");
            address2.setCity("Phoenix");
        });
    }
}

```
Both Addresses and customer objects can only be changed by a single thread, otherwise if another thread attempts to access a variable, then no update will occur. This example demonstrates how the Coarse-Grained Lock ensures thread-safe modifications across related objects, simplifying concurrency management while maintaining accuracy.

## When to Use the Coarse-Grained Lock Pattern in Java

The Coarse-Grained Lock pattern is applicable:

* When ensuring thread safety in a multithreaded environment with shared resources.
* For applications where ease of implementation outweighs the need for high concurrency.
* When fine-grained locking introduces complexity or increases the risk of deadlocks.

## Real-World Applications of Coarse-Grained Pattern in Java

* Ensuring the integrity of account transactions in Banking Systems.
* Preventing concurrent threads from corrupting shared log files in Inventory Management Systems.
* Synchronizing stock operations to prevent over-selling.

## Benefits and Trade-Offs of Coarse-Grained Lock Pattern
Benefits:

*  Easier to implement and debug compared to fine-grained locking.
* Reduces the chances of deadlocks due to fewer locks being used.
*  Ensures data consistency in critical sections.

Trade-Offs:

*  Limits the ability of threads to perform parallel operations on shared resources.
* Can lead to contention when multiple threads compete for the same lock.
* Operations that don’t require synchronization are still blocked.

## Related Patterns

- Readers–writer lock: allows for concurrent access for read-only operations, while requiring an exclusive lock for write operations.
- Lock Manager pattern: Can be used to define graunality in coarse-grained locks, as well as, detection and handling of deadlocks.
- Fine-Grained Lock: Increases concurrency by locking smaller portions of code or objects.
## References and Credits

* [Java Concurrency in Practice](https://amzn.to/4cYY4kU)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3Uh7rW1)
* [Oracle java concurrency](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
