---
layout: pattern
title: Reader Writer Lock
folder: reader-writer-lock
permalink: /patterns/reader-writer-lock/zh
categories: Concurrency
language: zh
tags: 
 - Performance
---

## 目的  
假设我们有一个共享内存区域，其中包含上面详述的基本约束。可以在互斥互斥体后面保护共享数据，在这种情况下，没有两个线程可以同时访问数据。但是，该解决方案不是最佳的，因为读取器 R1 可能拥有锁，然后另一个读取器 R2 请求访问。 R2 等到 R1 完成后再开始自己的读取操作是愚蠢的；相反，R2 应该立即启动。这就是 Reader Writer Lock 模式的动机。

## 类图
![alt text](./etc/reader-writer-lock.png "Reader writer lock")

## 适用性

应用程序需要提高多线程资源同步的性能，特别是有混合读/写操作。

## 真实世界的例子
* [Java Reader Writer Lock](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/ReadWriteLock.html)

## 鸣谢

* [Readers–writer lock](https://en.wikipedia.org/wiki/Readers%E2%80%93writer_lock)
* [Readers–writers_problem](https://en.wikipedia.org/wiki/Readers%E2%80%93writers_problem)
