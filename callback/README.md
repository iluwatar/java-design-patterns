---
layout: pattern
title: Callback
folder: callback
permalink: /patterns/callback/
categories: Idiom
language: en
tags:
 - Reactive
---

## Intent

Callback is a piece of executable code that is passed as an argument to other code, which is 
expected to call back (execute) the argument at some convenient time.

## Explanation

Real world example

> We need to be notified after executing task has finished. We pass a callback method for 
> the executor and wait for it to call back on us.     

In plain words

> Callback is a method passed to the executor which will be called at defined moment. 

Wikipedia says

> In computer programming, a callback, also known as a "call-after" function, is any executable 
> code that is passed as an argument to other code; that other code is expected to call 
> back (execute) the argument at a given time.

**Programmatic Example**

Callback is a simple interface with single method.

```java
public interface Callback {

  void call();
}
```

Next we define a task that will execute the callback after the task execution has finished.

```java
public abstract class Task {

  final void executeWith(Callback callback) {
    execute();
    Optional.ofNullable(callback).ifPresent(Callback::call);
  }

  public abstract void execute();
}

@Slf4j
public final class SimpleTask extends Task {

  @Override
  public void execute() {
    LOGGER.info("Perform some important activity and after call the callback method.");
  }
}
```

Finally, here's how we execute a task and receive a callback when it's finished.

```java
    var task = new SimpleTask();
    task.executeWith(() -> LOGGER.info("I'm done now."));
```

## Class diagram

![alt text](./etc/callback.png "Callback")

## Applicability

Use the Callback pattern when

* when some arbitrary synchronous or asynchronous action must be performed after execution of some defined activity.

## Real world examples

* [CyclicBarrier](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CyclicBarrier.html#CyclicBarrier%28int,%20java.lang.Runnable%29) constructor can accept a callback that will be triggered every time a barrier is tripped.
