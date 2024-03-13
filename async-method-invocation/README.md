---
title: Async Method Invocation
category: Concurrency
language: en
tag:
 - Asynchronous
 - Reactive
 - Scalability
---

## Intent

Asynchronous method invocation is a pattern where the calling thread
is not blocked while waiting results of tasks. The pattern provides parallel
processing of multiple independent tasks and retrieving the results via
callbacks or waiting until everything is done. 

## Also known as

* Asynchronous Procedure Call

## Explanation

Real world example

> Launching space rockets is an exciting business. The mission command gives an order to launch and 
> after some undetermined time, the rocket either launches successfully or fails miserably.

In plain words

> Asynchronous method invocation starts task processing and returns immediately before the task is 
> ready. The results of the task processing are returned to the caller later.

Wikipedia says

> In multithreaded computer programming, asynchronous method invocation (AMI), also known as 
> asynchronous method calls or the asynchronous pattern is a design pattern in which the call site 
> is not blocked while waiting for the called code to finish. Instead, the calling thread is 
> notified when the reply arrives. Polling for a reply is an undesired option.

**Programmatic Example**

In this example, we are launching space rockets and deploying lunar rovers.

The application demonstrates the async method invocation pattern. The key parts of the pattern are 
`AsyncResult` which is an intermediate container for an asynchronously evaluated value, 
`AsyncCallback` which can be provided to be executed on task completion and `AsyncExecutor` that 
manages the execution of the async tasks.

```java
public interface AsyncResult<T> {
  boolean isCompleted();
  T getValue() throws ExecutionException;
  void await() throws InterruptedException;
}
```

```java
public interface AsyncCallback<T> {
  void onComplete(T value, Optional<Exception> ex);
}
```

```java
public interface AsyncExecutor {
  <T> AsyncResult<T> startProcess(Callable<T> task);
  <T> AsyncResult<T> startProcess(Callable<T> task, AsyncCallback<T> callback);
  <T> T endProcess(AsyncResult<T> asyncResult) throws ExecutionException, InterruptedException;
}
```

`ThreadAsyncExecutor` is an implementation of `AsyncExecutor`. Some of its key parts are highlighted 
next.

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
  final var asyncResult4 = executor.startProcess(lazyval(20, 400), callback("Deploying lunar rover"));
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
  log("Space rocket <" + result1 + "> launch complete");
  log("Space rocket <" + result2 + "> launch complete");
  log("Space rocket <" + result3 + "> launch complete");
}
```

Here's the program console output.

```java
21:47:08.227 [executor-2] INFO com.iluwatar.async.method.invocation.App - Space rocket <test> launched successfully
21:47:08.269 [main] INFO com.iluwatar.async.method.invocation.App - Mission command is sipping coffee
21:47:08.318 [executor-4] INFO com.iluwatar.async.method.invocation.App - Space rocket <20> launched successfully
21:47:08.335 [executor-4] INFO com.iluwatar.async.method.invocation.App - Deploying lunar rover <20>
21:47:08.414 [executor-1] INFO com.iluwatar.async.method.invocation.App - Space rocket <10> launched successfully
21:47:08.519 [executor-5] INFO com.iluwatar.async.method.invocation.App - Space rocket <callback> launched successfully
21:47:08.519 [executor-5] INFO com.iluwatar.async.method.invocation.App - Deploying lunar rover <callback>
21:47:08.616 [executor-3] INFO com.iluwatar.async.method.invocation.App - Space rocket <50> launched successfully
21:47:08.617 [main] INFO com.iluwatar.async.method.invocation.App - Space rocket <10> launch complete
21:47:08.617 [main] INFO com.iluwatar.async.method.invocation.App - Space rocket <test> launch complete
21:47:08.618 [main] INFO com.iluwatar.async.method.invocation.App - Space rocket <50> launch complete
```

# Class diagram

![alt text](./etc/async-method-invocation.png "Async Method Invocation")

## Applicability

Use the async method invocation pattern when

* When operations do not need to complete before proceeding with the next steps in a program.
* For tasks that are resource-intensive or time-consuming, such as IO operations, network requests, or complex computations, where making the operation synchronous would significantly impact performance or user experience.
* In GUI applications to prevent freezing or unresponsiveness during long-running tasks.
* In web applications for non-blocking IO operations.

## Known Uses

* Web servers handling HTTP requests asynchronously to improve throughput and reduce latency.
* Desktop and mobile applications using background threads to perform time-consuming operations without blocking the user interface.
* Microservices architectures where services perform asynchronous communications via messaging queues or event streams.
* [FutureTask](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/FutureTask.html)
* [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)
* [ExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html)
* [Task-based Asynchronous Pattern](https://msdn.microsoft.com/en-us/library/hh873175.aspx)

## Consequences

Benefits:

* Improved Responsiveness: The main thread or application flow remains unblocked, improving the user experience in GUI applications and overall responsiveness.
* Better Resource Utilization: By enabling parallel execution, system resources (like CPU and IO) are utilized more efficiently, potentially improving the application's throughput.
* Scalability: Easier to scale applications, as tasks can be distributed across available resources more effectively.

Trade-offs:

* Complexity: Introducing asynchronous operations can complicate the codebase, making it more challenging to understand, debug, and maintain.
* Resource Management: Requires careful management of threads or execution contexts, which can introduce overhead and potential resource exhaustion issues.
* Error Handling: Asynchronous operations can make error handling more complex, as exceptions may occur in different threads or at different times.

Related Patterns:

* [Command](https://java-design-patterns.com/patterns/command/): Asynchronous method invocation can be used to implement the Command pattern, where commands are executed asynchronously.
* [Observer](https://java-design-patterns.com/patterns/observer/): Asynchronous method invocation can be used to notify observers asynchronously when a subject's state changes.
* [Promise](https://java-design-patterns.com/patterns/promise/): The AsyncResult interface can be considered a form of Promise, representing a value that may not be available yet.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3Ti1N4f)
* [Java Concurrency in Practice](https://amzn.to/4ab97VU)
