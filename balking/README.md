---
title: Balking
category: Concurrency
language: en
tag:
    - Concurrency
    - Decoupling
    - Fault tolerance
    - Synchronization
---

## Intent

Balking Pattern is used to prevent an object from executing a certain code if it is in an incomplete or inappropriate state. If the state is not suitable for the action, the method call is ignored (or "balked").

## Explanation

Real world example

> A real-world analogy of the Balking design pattern can be seen in a laundry service. Imagine a washing machine at a laundromat that only starts washing clothes if the door is properly closed and locked. If a user tries to start the machine while the door is open, the machine balks and does nothing. This ensures that the washing process only begins when it is safe to do so, preventing water spillage and potential damage to the machine. Similarly, the Balking pattern in software design ensures that operations are only executed when the object is in an appropriate state, preventing erroneous actions and maintaining system stability.

In plain words

> Using the balking pattern, a certain code executes only if the object is in particular state.

Wikipedia says

> The balking pattern is a software design pattern that only executes an action on an object when the object is in a particular state. For example, if an object reads ZIP files and a calling method invokes a get method on the object when the ZIP file is not open, the object would "balk" at the request.

**Programmatic Example**

There's a start-button in a washing machine to initiate the laundry washing. When the washing machine is inactive the button works as expected, but if it's already washing the button does nothing.

In this example implementation, `WashingMachine` is an object that has two states in which it can be: ENABLED and WASHING. If the machine is ENABLED, the state changes to WASHING using a thread-safe method. On the other hand, if it already has been washing and any other thread executes `wash()`it won't do that and returns without doing anything.

Here are the relevant parts of the `WashingMachine` class.

```java

@Slf4j
public class WashingMachine {

    private final DelayProvider delayProvider;
    private WashingMachineState washingMachineState;

    public WashingMachine(DelayProvider delayProvider) {
        this.delayProvider = delayProvider;
        this.washingMachineState = WashingMachineState.ENABLED;
    }

    public WashingMachineState getWashingMachineState() {
        return washingMachineState;
    }

    public void wash() {
        synchronized (this) {
            var machineState = getWashingMachineState();
            LOGGER.info("{}: Actual machine state: {}", Thread.currentThread().getName(), machineState);
            if (this.washingMachineState == WashingMachineState.WASHING) {
                LOGGER.error("Cannot wash if the machine has been already washing!");
                return;
            }
            this.washingMachineState = WashingMachineState.WASHING;
        }
        LOGGER.info("{}: Doing the washing", Thread.currentThread().getName());
        this.delayProvider.executeAfterDelay(50, TimeUnit.MILLISECONDS, this::endOfWashing);
    }

    public synchronized void endOfWashing() {
        washingMachineState = WashingMachineState.ENABLED;
        LOGGER.info("{}: Washing completed.", Thread.currentThread().getId());
    }
}
```

Here's the simple `DelayProvider` interface used by the `WashingMachine`.

```java
public interface DelayProvider {
    void executeAfterDelay(long interval, TimeUnit timeUnit, Runnable task);
}
```

Now, we introduce the application using the `WashingMachine`.

```java
public static void main(String... args) {
    final var washingMachine = new WashingMachine();
    var executorService = Executors.newFixedThreadPool(3);
    for (int i = 0; i < 3; i++) {
        executorService.execute(washingMachine::wash);
    }
    executorService.shutdown();
    try {
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }
    } catch (InterruptedException ie) {
        LOGGER.error("ERROR: Waiting on executor service shutdown!");
        Thread.currentThread().interrupt();
    }
}
```

Here is the console output of the program.

```
14:02:52.268 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-2: Actual machine state: ENABLED
14:02:52.272 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-2: Doing the washing
14:02:52.272 [pool-1-thread-3] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-3: Actual machine state: WASHING
14:02:52.273 [pool-1-thread-3] ERROR com.iluwatar.balking.WashingMachine - Cannot wash if the machine has been already washing!
14:02:52.273 [pool-1-thread-1] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-1: Actual machine state: WASHING
14:02:52.273 [pool-1-thread-1] ERROR com.iluwatar.balking.WashingMachine - Cannot wash if the machine has been already washing!
14:02:52.324 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - 14: Washing completed.
```

## Class diagram

![Balking](./etc/balking.png "Balking")

## Applicability

Use the Balking pattern when

* You want to invoke an action on an object only when it is in a particular state
* Objects are generally only in a state that is prone to balking temporarily but for an unknown amount of time
* In multithreaded applications where certain actions should only proceed when specific conditions are met, and those conditions are expected to change over time due to external factors or concurrent operations.

## Known Uses:

* Resource pooling, where resources are only allocated if they are in a valid state for allocation.
* Thread management, where threads only proceed with tasks if certain conditions (like task availability or resource locks) are met.

## Consequences:

Benefits:

* Reduces unnecessary lock acquisitions in situations where actions cannot proceed, enhancing performance in concurrent applications.
* Encourages clear separation of state management and behavior, leading to cleaner code.
* Simplifies the handling of operations that should only be performed under certain conditions without cluttering the caller code with state checks.

Trade-offs:

* Can introduce complexity by obscuring the conditions under which actions are taken or ignored, potentially making the system harder to debug and understand.
* May lead to missed opportunities or actions if the state changes are not properly monitored or if the balking condition is too restrictive.

## Related patterns

* [Double-Checked Locking](https://java-design-patterns.com/patterns/double-checked-locking/): Ensures that initialization occurs only when necessary and avoids unnecessary locking, which is related to Balking in terms of conditionally executing logic based on the object's state.
* [Guarded Suspension](https://java-design-patterns.com/patterns/guarded-suspension/): Similar in ensuring actions are only performed when an object is in a certain state, but typically involves waiting until the state is valid.
* [State](https://java-design-patterns.com/patterns/state/): The State pattern can be used in conjunction with Balking to manage the states and transitions of the object.

## Credits

* [Concurrent Programming in Java : Design Principles and Patterns](https://amzn.to/4dIBqxL)
* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
* [Patterns in Java: A Catalog of Reusable Design Patterns Illustrated with UML](https://amzn.to/4bOtzwF)
