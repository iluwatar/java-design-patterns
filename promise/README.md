---
layout: pattern
title: Promise
folder: promise
permalink: /patterns/promise/
categories: Concurrency
tags:
 - Reactive
---

## Also known as
CompletableFuture

## Intent
A Promise represents a proxy for a value not necessarily known when the promise is created. It
allows you to associate dependent promises to an asynchronous action's eventual success value or 
failure reason. Promises are a way to write async code that still appears as though it is executing 
in a synchronous way.

## Class diagram
![alt text](./etc/promise.png "Promise")

## Applicability
Promise pattern is applicable in concurrent programming when some work needs to be done asynchronously
and:

* Code maintainability and readability suffers due to callback hell.
* You need to compose promises and need better error handling for asynchronous tasks.
* You want to use functional style of programming.


## Real world examples

* [java.util.concurrent.CompletableFuture](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)
* [Guava ListenableFuture](https://github.com/google/guava/wiki/ListenableFutureExplained)

## Related Patterns
 * Async Method Invocation
 * Callback

## Credits

* [You are missing the point to Promises](https://gist.github.com/domenic/3889970)
* [Functional style callbacks using CompletableFuture](https://www.infoq.com/articles/Functional-Style-Callbacks-Using-CompletableFuture)
