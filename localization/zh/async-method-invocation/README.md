---
layout: pattern
title: Async Method Invocation
folder: async-method-invocation
permalink: /patterns/async-method-invocation/
categories: Concurrency
tags:
 - Reactive
---

## 含义

异步方法是一种调用线程在等待任务结果时候不会被阻塞的模式。该模式提供了对多个任务的并行处理，并通过回调或等待，在所有任务完成后在提供结果读取。

## 解释

真实世界案例

> 发射太空火箭是一项令人兴奋的事业。在任务指挥部下达发射命令后， 经过一些未确定的时间，火箭要么成功发射，要么重演挑战者悲剧。

简而言之

> 异步方法调用开始任务处理并，在任务结果准备好之前立即返回。任务处理的结果会在稍后再返回给调用者。

维基百科的解释

> 在多线程计算机编程中，异步方法调用（AMI），也被称为异步方法调用或异步模式。这是一种设计模式，在这种模式下，调用点在等待被调用代码完成时不会被阻塞。相反，当返回点到达时，调用线程会得到通知。轮询结果是一种不受欢迎的选择。

**编程示例**

在这个例子中，我们正在发射太空火箭和部署月球车。

该应用演示了异步方法调用模式。该模式的关键部分是 `AsyncResult`，它是一个异步计算值的中间容器，`AsyncCallback` 可以在任务完成时提供执行行动作，`AsyncExecutor` 负责管理异步任务的执行。

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

`ThreadAsyncExecutor` 是 `AsyncExecutor` 的一个实现。接下来将着重说明它的一些关键部分。

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

然后我们准备发射一些火箭，看看所有东西是如何一起运作的。

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

以下是控制台输出。

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

## 类图

![alt text](../../async-method-invocation/etc/async-method-invocation.png "Async Method Invocation")

## 适用场景

在以下场景可以使用异步调用模式

* 你有多有可以并行执行的独立任务
* 你需要提高一组串行任务的性能
* 你的处理能力有限、或者有长期运行的任务，调用者不应该等待任务所有任务运行结束

## 现实示例

* [FutureTask](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/FutureTask.html)
* [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)
* [ExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html)
* [Task-based Asynchronous Pattern](https://msdn.microsoft.com/en-us/library/hh873175.aspx)

