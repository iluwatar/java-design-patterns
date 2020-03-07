---
layout: pattern
title: Async Method Invocation
folder: async-method-invocation
permalink: /patterns/async-method-invocation/
categories: Concurrency
tags:
 - Reactive
---

## Intent
Asynchronous method invocation is pattern where the calling thread
is not blocked while waiting results of tasks. The pattern provides parallel
processing of multiple independent tasks and retrieving the results via
callbacks or waiting until everything is done. 

# Class diagram
![alt text](./etc/async-method-invocation.png "Async Method Invocation")

## Applicability
Use async method invocation pattern when

* you have multiple independent tasks that can run in parallel
* you need to improve the performance of a group of sequential tasks
* you have limited amount of processing capacity or long running tasks and the
  caller should not wait the tasks to be ready

## Real world examples

* [FutureTask](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/FutureTask.html), [CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html) and [ExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html) (Java)
* [Task-based Asynchronous Pattern](https://msdn.microsoft.com/en-us/library/hh873175.aspx) (.NET)
