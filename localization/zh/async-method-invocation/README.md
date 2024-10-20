---
title: Async Method Invocation
shortTitle: Async Method Invocation
category: Concurrency
language: zh
tag:
 - Reactive
---

## 意图

异步方法调用是一个调用线程在等待任务结果时不会阻塞的模式。模式为多个独立的任务提供并行的处理方式并且通过回调或等到它们全部完成来接收任务结果。

## 解释

真实世界例子

> 发射火箭是一项令人激动的事务。任务指挥官发出了发射命令，经过一段不确定的时间后，火箭要么成功发射，要么惨遭失败。

通俗地说

> 异步方法调用开始任务处理，并在任务完成之前立即返回。 任务处理的结果稍后返回给调用方。

维基百科说

> 在多线程计算机编程中，异步方法调用（AMI），也称为异步方法调用或异步模式，是一种设计模式，其中在等待被调用的代码完成时不会阻塞调用站点。 而是在执行结果到达时通知调用线程。轮询调用结果是不希望的选项。

**程序示例**

在此示例中，我们正在发射太空火箭并部署月球漫游车。该应用演示了异步方法调用模式。 模式的关键部分是`AsyncResult`（用于异步评估值的中间容器），`AsyncCallback`（可以在任务完成时被执行）和`AsyncExecutor`（用于管理异步任务的执行）。

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

`ThreadAsyncExecutor`是`AsyncExecutor`的实现。 接下来将突出显示其一些关键部分。

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

然后，我们准备发射一些火箭，看看一切是如何协同工作的。

```java
public static void main(String[] args) throws Exception {
  // 构造一个将执行异步任务的新执行程序
  var executor = new ThreadAsyncExecutor();

  // 以不同的处理时间开始一些异步任务，最后两个使用回调处理程序
  final var asyncResult1 = executor.startProcess(lazyval(10, 500));
  final var asyncResult2 = executor.startProcess(lazyval("test", 300));
  final var asyncResult3 = executor.startProcess(lazyval(50L, 700));
  final var asyncResult4 = executor.startProcess(lazyval(20, 400), callback("Deploying lunar rover"));
  final var asyncResult5 =
      executor.startProcess(lazyval("callback", 600), callback("Deploying lunar rover"));

  // 在当前线程中模拟异步任务正在它们自己的线程中执行
  Thread.sleep(350); // 哦，兄弟，我们在这很辛苦
  log("Mission command is sipping coffee");

  // 等待任务完成
  final var result1 = executor.endProcess(asyncResult1);
  final var result2 = executor.endProcess(asyncResult2);
  final var result3 = executor.endProcess(asyncResult3);
  asyncResult4.await();
  asyncResult5.await();

  // log the results of the tasks, callbacks log immediately when complete
  // 记录任务结果的日志， 回调的日志会在回调完成时立刻记录
  log("Space rocket <" + result1 + "> launch complete");
  log("Space rocket <" + result2 + "> launch complete");
  log("Space rocket <" + result3 + "> launch complete");
}
```

这是程序控制台的输出。

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

# 类图

![alt text](./etc/async-method-invocation.png "Async Method Invocation")

## 适用性

在以下情况下使用异步方法调用模式

* 您有多个可以并行运行的独立任务
* 您需要提高一组顺序任务的性能
* 您的处理能力或长时间运行的任务数量有限，并且调用方不应等待任务执行完毕

## 真实世界例子

* [FutureTask](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/FutureTask.html)
* [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)
* [ExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html)
* [Task-based Asynchronous Pattern](https://msdn.microsoft.com/en-us/library/hh873175.aspx)
