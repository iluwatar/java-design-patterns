---
layout: pattern
title: Half-Sync/Half-Async
folder: half-sync-half-async
permalink: /patterns/half-sync-half-async/zh
categories: Concurrency
language: zh
tags:
 - Performance
---

## 目的
半同步/半异步模式将同步 I/O 与
系统中的异步 I/O 以简化并发编程工作，而无需
降低执行效率。

## 类图
![Half-Sync/Half-Async class diagram](./etc/half-sync-half-async.png)

## 适用性
在以下情况下使用半同步/半异步模式

* 一个系统具有以下特点：
  * 系统必须执行任务以响应异步发生的外部事件，例如操作系统中的硬件中断
  * 将单独的控制线程专用于为每个外部事件源执行同步 I/O 是低效的
  * 如果 I/O 是同步执行的，系统中更高级别的任务可以显着简化。
* 系统中的一个或多个任务必须在单个控制线程中运行，而其他任务可能受益于多线程。

## 真实世界的例子

* [BSD Unix networking subsystem](https://www.dre.vanderbilt.edu/~schmidt/PDF/PLoP-95.pdf)
* [Real Time CORBA](http://www.omg.org/news/meetings/workshops/presentations/realtime2001/4-3_Pyarali_thread-pool.pdf)
* [Android AsyncTask framework](http://developer.android.com/reference/android/os/AsyncTask.html)

## 鸣谢

* [Douglas C. Schmidt and Charles D. Cranor - Half Sync/Half Async](https://www.dre.vanderbilt.edu/~schmidt/PDF/PLoP-95.pdf)
* [Pattern Oriented Software Architecture Volume 2: Patterns for Concurrent and Networked Objects](https://www.amazon.com/gp/product/0471606952/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0471606952&linkCode=as2&tag=javadesignpat-20&linkId=889e4af72dca8261129bf14935e0f8dc)
