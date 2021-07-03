---
layout: pattern
title: Reactor
folder: reactor
permalink: /patterns/reactor/zh
pumlformat: svg
categories: Concurrency
language: zh
tags:
 - Performance
 - Reactive
---

## 意图
Reactor 设计模式处理由一个或多个客户端并发传送到应用程序的服务请求。应用程序可以注册用于处理的特定处理程序，这些处理程序由反应器在特定事件上调用。事件处理程序的分派由管理注册的事件处理程序的启动分派器执行。服务请求的多路分解由同步事件多路分解器执行。

## 类图
![Reactor](./etc/reactor.png "Reactor")

## 适用性
使用 Reactor 模式时

* 服务器应用程序需要处理来自多个客户端的并发服务请求。
* 即使在处理旧客户端请求时，服务器应用程序也需要可用于接收来自新客户端的请求。
* 服务器必须最大化吞吐量、最小化延迟并有效地使用 CPU 而不会阻塞。

##真实世界的例子
* [Spring Reactor](http://projectreactor.io/)

## 鸣谢

* [Douglas C. Schmidt - Reactor](https://www.dre.vanderbilt.edu/~schmidt/PDF/Reactor.pdf)
* [Pattern Oriented Software Architecture Volume 2: Patterns for Concurrent and Networked Objects](https://www.amazon.com/gp/product/0471606952/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0471606952&linkCode=as2&tag=javadesignpat-20&linkId=889e4af72dca8261129bf14935e0f8dc)
* [Doug Lea - Scalable IO in Java](http://gee.cs.oswego.edu/dl/cpjslides/nio.pdf)
* [Netty](http://netty.io/)
