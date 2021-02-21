---
layout: pattern
title: Active Object
folder: active-object
permalink: /patterns/active-object/
categories: Concurrency
tags:
 - Gang of Four
---


## Intent
The active object design pattern decouples method execution from method invocation for objects that each reside in their own thread of control.The goal is to introduce concurrency, by using asynchronous method invocation and a scheduler for handling requests.(https://en.wikipedia.org/wiki/Active_object)

## Explanation

The class that implements the active object pattern will contain a self-synchronization mechanism without using 'synchronized' methods.

The class provides an interface ob method execution, but the logic and runtime will be implemented in a seperated thread. This thread, will be inserted
into the scheduler to handle the request properly.

In the following example, the scheduler is implemented by a BlockingQueue.