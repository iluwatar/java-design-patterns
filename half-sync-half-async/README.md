---
layout: pattern
title: Half-Sync/Half-Async
folder: half-sync-half-async
permalink: /patterns/half-sync-half-async/
categories: Concurrency
language: en
tags:
 - Performance
---

## Intent
The Half-Sync/Half-Async pattern decouples synchronous I/O from
asynchronous I/O in a system to simplify concurrent programming effort without
degrading execution efficiency.

## Class diagram
![Half-Sync/Half-Async class diagram](./etc/half-sync-half-async.png)

## Applicability
Use Half-Sync/Half-Async pattern when

* a system possesses following characteristics:
  * the system must perform tasks in response to external events that occur asynchronously, like hardware interrupts in OS
  * it is inefficient to dedicate separate thread of control to perform synchronous I/O for each external source of event
  * the higher level tasks in the system can be simplified significantly if I/O is performed synchronously.
* one or more tasks in a system must run in a single thread of control, while other tasks may benefit from multi-threading.

## Real world examples

* [BSD Unix networking subsystem](https://www.dre.vanderbilt.edu/~schmidt/PDF/PLoP-95.pdf)
* [Real Time CORBA](http://www.omg.org/news/meetings/workshops/presentations/realtime2001/4-3_Pyarali_thread-pool.pdf)
* [Android AsyncTask framework](http://developer.android.com/reference/android/os/AsyncTask.html)

## Credits

* [Douglas C. Schmidt and Charles D. Cranor - Half Sync/Half Async](https://www.dre.vanderbilt.edu/~schmidt/PDF/PLoP-95.pdf)
* [Pattern Oriented Software Architecture Volume 2: Patterns for Concurrent and Networked Objects](https://www.amazon.com/gp/product/0471606952/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0471606952&linkCode=as2&tag=javadesignpat-20&linkId=889e4af72dca8261129bf14935e0f8dc)
