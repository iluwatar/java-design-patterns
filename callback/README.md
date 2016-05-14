---
layout: pattern
title: Callback
folder: callback
permalink: /patterns/callback/
categories: Other
tags:
 - Java
 - Difficulty-Beginner
 - Functional
 - Idiom
---

## Intent
Callback is a piece of executable code that is passed as an
argument to other code, which is expected to call back (execute) the argument
at some convenient time.

![alt text](./etc/callback.png "Callback")

## Applicability
Use the Callback pattern when

* when some arbitrary synchronous or asynchronous action must be performed after execution of some defined activity.

## Real world examples

* [CyclicBarrier](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CyclicBarrier.html#CyclicBarrier%28int,%20java.lang.Runnable%29) constructor can accept callback that will be triggered every time when barrier is tripped.
