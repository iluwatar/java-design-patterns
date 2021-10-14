---
layout: pattern
title: Reactor
folder: reactor
permalink: /patterns/reactor/
pumlformat: svg
categories: Concurrency
language: en
tags:
 - Performance
 - Reactive
---

## Intent
The Reactor design pattern handles service requests that are delivered concurrently to an application by one or more clients. The application can register specific handlers for processing which are called by reactor on specific events. Dispatching of event handlers is performed by an initiation dispatcher, which manages the registered event handlers. Demultiplexing of service requests is performed by a synchronous event demultiplexer.

## Class diagram
![Reactor](./etc/reactor.png "Reactor")

## Applicability
Use Reactor pattern when

* A server application needs to handle concurrent service requests from multiple clients.
* A server application needs to be available for receiving requests from new clients even when handling older client requests.
* A server must maximize throughput, minimize latency and use CPU efficiently without blocking.

## Real world examples

* [Spring Reactor](http://projectreactor.io/)

## Credits

* [Douglas C. Schmidt - Reactor](https://www.dre.vanderbilt.edu/~schmidt/PDF/Reactor.pdf)
* [Pattern Oriented Software Architecture Volume 2: Patterns for Concurrent and Networked Objects](https://www.amazon.com/gp/product/0471606952/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0471606952&linkCode=as2&tag=javadesignpat-20&linkId=889e4af72dca8261129bf14935e0f8dc)
* [Doug Lea - Scalable IO in Java](http://gee.cs.oswego.edu/dl/cpjslides/nio.pdf)
* [Netty](http://netty.io/)
