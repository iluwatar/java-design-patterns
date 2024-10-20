---
title: "Async Method Invocation Pattern in Java: Elevate Performance with Asynchronous Programming"
shortTitle: Async Method Invocation
description: "Learn about the Async Method Invocation pattern in Java for asynchronous method calls, enhancing concurrency, scalability, and responsiveness in your applications. Explore real-world examples and code implementations."
category: Concurrency
language: en
tag:
  - Asynchronous
  - Decoupling
  - Reactive
  - Scalability
  - Thread management
---

## Also known as

* Asynchronous Procedure Call

## Intent of Async Method Invocation Design Pattern

The Async Method Invocation pattern is designed to enhance concurrency by allowing methods to be called asynchronously. This pattern helps in executing parallel tasks, reducing wait times, and improving system throughput.

## Detailed Explanation of Async Method Invocation Pattern with Real-World Examples

Real-world example

> Asynchronous method invocation enables non-blocking operations, allowing multiple processes to run concurrently. This pattern is particularly useful in applications requiring high scalability and performance, such as web servers and microservices.
> 
> In the context of space rockets, an analogous example of the Async Method Invocation pattern can be seen in the communication between the mission control center and the onboard systems of the rocket. When mission control sends a command to the rocket to adjust its trajectory or perform a system check, they do not wait idly for the rocket to complete the task and report back. Instead, mission control continues to monitor other aspects of the mission and manage different tasks. The rocket executes the command asynchronously and sends a status update or result back to mission control once the operation is complete. This allows mission control to efficiently manage multiple concurrent operations without being blocked by any single task, similar to how asynchronous method invocation works in software systems.

In plain words

> Asynchronous method invocation starts task processing and returns immediately before the task is ready. The results of the task processing are returned to the caller later.

Wikipedia says

> In multithreaded computer programming, asynchronous method invocation (AMI), also known as asynchronous method calls or the asynchronous pattern is a design pattern in which the call site is not blocked while waiting for the called code to finish. Instead, the calling thread is notified when the reply arrives. Polling for a reply is an undesired option.

## Programmatic Example of Async Method Invocation Pattern in Java

Consider a scenario where multiple tasks need to be executed simultaneously. Using the Async Method Invocation pattern, you can initiate these tasks without waiting for each to complete, thus optimizing resource usage and reducing latency.

In this example, we are launching space rockets and deploying lunar rovers.

The application demonstrates the async method invocation pattern. The key parts of the pattern are`AsyncResult` which is an intermediate container for an asynchronously evaluated value, `AsyncCallback` which can be provided to be executed on task completion and `AsyncExecutor` that manages the execution of the async tasks.

```java
public interface AsyncResult<T> {
    boolean isCompleted();

    T getValue() throws ExecutionException;

    void await() throws InterruptedException;
}
```

```java
public interface AsyncCallback<T> {
    void onComplete(T value);

    void onError(Exception ex);
}
```

```java
public interface AsyncExecutor {
    <T> AsyncResult<T> startProcess(Callable<T> task);

    <T> AsyncResult<T> startProcess(Callable<T> task, AsyncCallback<T> callback);

    <T> T endProcess(AsyncResult<T> asyncResult) throws ExecutionException, InterruptedException;
}
```

`ThreadAsyncExecutor` is an implementation of `AsyncExecutor`. Some of its key parts are highlighted next.

```java
public class ThreadAsyncExecutor implements AsyncExecutor {

    @Override
    public <T> AsyncResult<T> startProcess(Callable<T> task) {
        return startProcess(task, null);
    }

    @Override
    public <T> AsyncResult<T> startProcess(Callable<T> task, AsyncCallback<T> callback) {
        var result = new CompletableResult<>(callback);
        new Thread(
                () -> {
                    try {
                        result.setValue(task.call());
                    } catch (Exception ex) {
                        result.setException(ex);
                    }
                },
                "executor-" + idx.incrementAndGet())
                .start();
        return result;
    }

    @Override
    public <T> T endProcess(AsyncResult<T> asyncResult)
            throws ExecutionException, InterruptedException {
        if (!asyncResult.isCompleted()) {
            asyncResult.await();
        }
        return asyncResult.getValue();
    }
}
```

Then we are ready to launch some rockets to see how everything works together.

```java
  public static void main(String[] args) throws Exception {
    // construct a new executor that will run async tasks
    var executor = new ThreadAsyncExecutor();

    // start few async tasks with varying processing times, two last with callback handlers
    final var asyncResult1 = executor.startProcess(lazyval(10, 500));
    final var asyncResult2 = executor.startProcess(lazyval("test", 300));
    final var asyncResult3 = executor.startProcess(lazyval(50L, 700));
    final var asyncResult4 = executor.startProcess(lazyval(20, 400),
            callback("Deploying lunar rover"));
    final var asyncResult5 =
            executor.startProcess(lazyval("callback", 600), callback("Deploying lunar rover"));

    // emulate processing in the current thread while async tasks are running in their own threads
    Thread.sleep(350); // Oh boy, we are working hard here
    log("Mission command is sipping coffee");

    // wait for completion of the tasks
    final var result1 = executor.endProcess(asyncResult1);
    final var result2 = executor.endProcess(asyncResult2);
    final var result3 = executor.endProcess(asyncResult3);
    asyncResult4.await();
    asyncResult5.await();

    // log the results of the tasks, callbacks log immediately when complete
    log(String.format(ROCKET_LAUNCH_LOG_PATTERN, result1));
    log(String.format(ROCKET_LAUNCH_LOG_PATTERN, result2));
    log(String.format(ROCKET_LAUNCH_LOG_PATTERN, result3));
}
```

Here's the program console output.

```
21:47:08.227[executor-2]INFO com.iluwatar.async.method.invocation.App-Space rocket<test> launched successfully
21:47:08.269[main]INFO com.iluwatar.async.method.invocation.App-Mission command is sipping coffee
21:47:08.318[executor-4]INFO com.iluwatar.async.method.invocation.App-Space rocket<20>launched successfully
21:47:08.335[executor-4]INFO com.iluwatar.async.method.invocation.App-Deploying lunar rover<20>
21:47:08.414[executor-1]INFO com.iluwatar.async.method.invocation.App-Space rocket<10>launched successfully
21:47:08.519[executor-5]INFO com.iluwatar.async.method.invocation.App-Space rocket<callback> launched successfully
21:47:08.519[executor-5]INFO com.iluwatar.async.method.invocation.App-Deploying lunar rover<callback>
21:47:08.616[executor-3]INFO com.iluwatar.async.method.invocation.App-Space rocket<50>launched successfully
21:47:08.617[main]INFO com.iluwatar.async.method.invocation.App-Space rocket<10>launch complete
21:47:08.617[main]INFO com.iluwatar.async.method.invocation.App-Space rocket<test> launch complete
21:47:08.618[main]INFO com.iluwatar.async.method.invocation.App-Space rocket<50>launch complete
```

## When to Use the Async Method Invocation Pattern in Java

This pattern is ideal for applications needing to manage multiple parallel tasks efficiently. It is commonly used in scenarios such as handling background processes, improving user interface responsiveness, and managing asynchronous data processing.

Use the async method invocation pattern when

* When operations do not need to complete before proceeding with the next steps in a program.
* For tasks that are resource-intensive or time-consuming, such as IO operations, network requests, or complex computations, where making the operation synchronous would significantly impact performance or user experience.
* In GUI applications to prevent freezing or unresponsiveness during long-running tasks.
* In web applications for non-blocking IO operations.

## Real-World Applications of Async Method Invocation Pattern in Java

Many modern applications leverage the Async Method Invocation pattern, including web servers handling concurrent requests, microservices architectures, and systems requiring intensive background processing.

* Web servers handling HTTP requests asynchronously to improve throughput and reduce latency.
* Desktop and mobile applications using background threads to perform time-consuming operations without blocking the user interface.
* Microservices architectures where services perform asynchronous communications via messaging queues or event streams.
* [FutureTask](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/FutureTask.html)
* [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)
* [ExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html)
* [Task-based Asynchronous Pattern](https://msdn.microsoft.com/en-us/library/hh873175.aspx)

## Benefits and Trade-offs of Async Method Invocation Pattern

While this pattern offers significant performance benefits, it also introduces complexity in error handling and resource management. Proper implementation is essential to avoid potential pitfalls such as race conditions and deadlocks.

Benefits:

* Improved Responsiveness: The main thread or application flow remains unblocked, improving the user experience in GUI applications and overall responsiveness.
* Better Resource Utilization: By enabling parallel execution, system resources (like CPU and IO) are utilized more efficiently, potentially improving the application's throughput.
* Scalability: Easier to scale applications, as tasks can be distributed across available resources more effectively.

Trade-offs:

* Complexity: Introducing asynchronous operations can complicate the codebase, making it more challenging to understand, debug, and maintain.
* Resource Management: Requires careful management of threads or execution contexts, which can introduce overhead and potential resource exhaustion issues.
* Error Handling: Asynchronous operations can make error handling more complex, as exceptions may occur in different threads or at different times.

## Related Java Design Patterns

The Async Method Invocation pattern often works well with other design patterns like the Command Pattern for encapsulating requests, the Observer Pattern for event handling, and the Promise Pattern for managing asynchronous results.

* [Command](https://java-design-patterns.com/patterns/command/): Asynchronous method invocation can be used to implement the Command pattern, where commands are executed asynchronously.
* [Observer](https://java-design-patterns.com/patterns/observer/): Asynchronous method invocation can be used to notify observers asynchronously when a subject's state changes.
* [Promise](https://java-design-patterns.com/patterns/promise/): The AsyncResult interface can be considered a form of Promise, representing a value that may not be available yet.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3Ti1N4f)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Java Concurrency in Practice](https://amzn.to/4ab97VU)
