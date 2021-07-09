---
layout: pattern
title: Leader/Followers
folder: leader-followers
permalink: /patterns/leader-followers/zh
categories: Concurrency
language: zh
tags:
 - Performance
---

## 目的
领导者/追随者模式提供了一个并发模型，其中多个
线程可以有效地多路分解事件和调度事件处理程序
线程共享的进程 I/O 句柄。

## 类图
![Leader/Followers class diagram](./etc/leader-followers.png)

## 适用性
在以下情况下使用领导者 - 追随者模式

* 多个线程轮流共享一组事件源，以检测、解复用、分派和处理发生在事件源上的服务请求。

## 真实世界的例子

* [ACE Thread Pool Reactor framework](https://www.dre.vanderbilt.edu/~schmidt/PDF/HPL.pdf)
* [JAWS](http://www.dre.vanderbilt.edu/~schmidt/PDF/PDCP.pdf)
* [Real-time CORBA](http://www.dre.vanderbilt.edu/~schmidt/PDF/RTS.pdf)

## 鸣谢

* [Douglas C. Schmidt and Carlos O’Ryan - Leader/Followers](http://www.kircher-schwanninger.de/michael/publications/lf.pdf)
