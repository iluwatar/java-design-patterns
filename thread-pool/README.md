---
title: Thread Pool
category: Concurrency
language: en
tag:
    - Asynchronous
    - Performance
    - Resource management
    - Scalability
    - Synchronization
    - Thread management
---

## Also known as

* Worker Pool

## Intent

Efficiently manage a pool of worker threads to execute tasks concurrently, improving resource utilization and performance.

## Explanation

Real-world example

> A real-world analogy for the Thread Pool design pattern can be found in a restaurant kitchen. Imagine a busy restaurant with a limited number of chefs (threads). Instead of hiring a new chef every time an order (task) comes in, the restaurant uses a fixed number of chefs to handle all the incoming orders. Each chef works on one order at a time and then moves on to the next one when finished. This approach ensures that the kitchen operates efficiently without the overhead of hiring and firing chefs continuously, and it prevents the kitchen from becoming overcrowded with too many chefs working at once. This setup allows the restaurant to handle multiple orders concurrently, optimize resource use, and maintain a steady workflow.

In plain words

> Thread Pool is a concurrency pattern where threads are allocated once and reused between tasks.

Wikipedia says

> In computer programming, a thread pool is a software design pattern for achieving concurrency of execution in a computer program. Often also called a replicated workers or worker-crew model, a thread pool maintains multiple threads waiting for tasks to be allocated for concurrent execution by the supervising program. By maintaining a pool of threads, the model increases performance and avoids latency in execution due to frequent creation and destruction of threads for short-lived tasks. The number of available threads is tuned to the computing resources available to the program, such as a parallel task queue after completion of execution.

**Programmatic Example**

Let's first look at our task hierarchy. We have an abstract base class `Task` and concrete tasks `CoffeeMakingTask` and `PotatoPeelingTask`.

```java
public abstract class Task {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

    @Getter
    private final int id;
    @Getter
    private final int timeMs;

    public Task(final int timeMs) {
        this.id = ID_GENERATOR.incrementAndGet();
        this.timeMs = timeMs;
    }

    @Override
    public String toString() {
        return String.format("id=%d timeMs=%d", id, timeMs);
    }
}

public class CoffeeMakingTask extends Task {

    private static final int TIME_PER_CUP = 100;

    public CoffeeMakingTask(int numCups) {
        super(numCups * TIME_PER_CUP);
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.getClass().getSimpleName(), super.toString());
    }
}

public class PotatoPeelingTask extends Task {

    private static final int TIME_PER_POTATO = 200;

    public PotatoPeelingTask(int numPotatoes) {
        super(numPotatoes * TIME_PER_POTATO);
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.getClass().getSimpleName(), super.toString());
    }
}
```

Next, we present a runnable `Worker` class that the thread pool will utilize to handle all the potato peeling and coffee making.

```java

@Slf4j
public class Worker implements Runnable {

    private final Task task;

    public Worker(final Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        LOGGER.info("{} processing {}", Thread.currentThread().getName(), task.toString());
        try {
            Thread.sleep(task.getTimeMs());
        } catch (InterruptedException e) {
            LOGGER.error("Error occurred: ", e);
        }
    }
}
```

Now, we are ready to show the full example in action.

```java
LOGGER.info("Program started");

// Create a list of tasks to be executed
var tasks = List.of(
        new PotatoPeelingTask(3),
        new PotatoPeelingTask(6),
        new CoffeeMakingTask(2),
        new CoffeeMakingTask(6),
        new PotatoPeelingTask(4),
        new CoffeeMakingTask(2),
        new PotatoPeelingTask(4),
        new CoffeeMakingTask(9),
        new PotatoPeelingTask(3),
        new CoffeeMakingTask(2),
        new PotatoPeelingTask(4),
        new CoffeeMakingTask(2),
        new CoffeeMakingTask(7),
        new PotatoPeelingTask(4),
        new PotatoPeelingTask(5));

// Creates a thread pool that reuses a fixed number of threads operating off a shared
// unbounded queue. At any point, at most nThreads threads will be active processing
// tasks. If additional tasks are submitted when all threads are active, they will wait
// in the queue until a thread is available.
var executor = Executors.newFixedThreadPool(3);

// Allocate new worker for each task
// The worker is executed when a thread becomes
// available in the thread pool
tasks.stream().map(Worker::new).forEach(executor::execute);

// All tasks were executed, now shutdown
executor.shutdown();
while (!executor.isTerminated()) {
    Thread.yield();
}
LOGGER.info("Program finished");
```

Running the example produces the following output:

```
13:47:07.244 [main] INFO com.iluwatar.threadpool.App -- Program started
13:47:07.258 [pool-1-thread-3] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-3 processing CoffeeMakingTask id=3 timeMs=200
13:47:07.258 [pool-1-thread-2] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-2 processing PotatoPeelingTask id=2 timeMs=1200
13:47:07.258 [pool-1-thread-1] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-1 processing PotatoPeelingTask id=1 timeMs=600
13:47:07.464 [pool-1-thread-3] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-3 processing CoffeeMakingTask id=4 timeMs=600
13:47:07.864 [pool-1-thread-1] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-1 processing PotatoPeelingTask id=5 timeMs=800
13:47:08.066 [pool-1-thread-3] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-3 processing CoffeeMakingTask id=6 timeMs=200
13:47:08.271 [pool-1-thread-3] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-3 processing PotatoPeelingTask id=7 timeMs=800
13:47:08.464 [pool-1-thread-2] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-2 processing CoffeeMakingTask id=8 timeMs=900
13:47:08.668 [pool-1-thread-1] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-1 processing PotatoPeelingTask id=9 timeMs=600
13:47:09.076 [pool-1-thread-3] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-3 processing CoffeeMakingTask id=10 timeMs=200
13:47:09.273 [pool-1-thread-1] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-1 processing PotatoPeelingTask id=11 timeMs=800
13:47:09.277 [pool-1-thread-3] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-3 processing CoffeeMakingTask id=12 timeMs=200
13:47:09.367 [pool-1-thread-2] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-2 processing CoffeeMakingTask id=13 timeMs=700
13:47:09.482 [pool-1-thread-3] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-3 processing PotatoPeelingTask id=14 timeMs=800
13:47:10.072 [pool-1-thread-2] INFO com.iluwatar.threadpool.Worker -- pool-1-thread-2 processing PotatoPeelingTask id=15 timeMs=1000
13:47:11.078 [main] INFO com.iluwatar.threadpool.App -- Program finished
```

## Class diagram

![Thread Pool](./etc/thread_pool_urm.png "Thread Pool")

## Applicability

* When there are multiple tasks to be executed and creating a new thread for each task would be inefficient.
* In scenarios where tasks are short-lived and the overhead of thread creation and destruction is significant.
* When you need to control the number of concurrent threads to prevent resource exhaustion.

## Known Uses

* Java's `java.util.concurrent.ExecutorService` and `ThreadPoolExecutor`.
* Web servers handling multiple client requests concurrently.
* Background task execution in GUI applications to keep the user interface responsive.

## Consequences

Benefits:

* Improved performance by reusing existing threads instead of creating new ones.
* Better resource management by limiting the number of active threads.
* Simplifies the management of concurrent task execution.

Trade-offs:

* Requires careful tuning of the thread pool size to balance resource utilization and performance.
* Potential for thread starvation if the pool size is too small.
* Complexity in handling task rejection and thread lifecycle management.

## Related Patterns

* [Promise](https://java-design-patterns.com/patterns/promise/): Used to represent the result of an asynchronous computation, often executed by a thread pool.
* [Producer-Consumer](https://java-design-patterns.com/patterns/producer-consumer/): Threads in the pool consume tasks produced by another part of the application.
* [Command](https://java-design-patterns.com/patterns/command/): Each task submitted to the thread pool can be treated as a command object.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
