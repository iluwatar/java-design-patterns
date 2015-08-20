---
layout: pattern
title: Chain of responsibility
folder: chain-of-responsibility
permalink: /patterns/chain-of-responsibility/
categories: Behavioral
tags: Java
---

**Intent:** Avoid coupling the sender of a request to its receiver by giving
more than one object a chance to handle the request. Chain the receiving
objects and pass the request along the chain until an object handles it.

![alt text](./chain/etc/chain_1.png "Chain of Responsibility")

**Applicability:** Use Chain of Responsibility when

* more than one object may handle a request, and the handler isn't known a priori. The handler should be ascertained automatically
* you want to issue a request to one of several objects without specifying the receiver explicitly
* the set of objects that can handle a request should be specified dynamically

**Real world examples:**

* [java.util.logging.Logger#log()](http://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html#log%28java.util.logging.Level,%20java.lang.String%29)
* [Apache Commons Chain](https://commons.apache.org/proper/commons-chain/index.html)
