---
layout: pattern
title: Memento
folder: memento
permalink: /patterns/memento/
categories: Behavioral
tags: Java
---

**Intent:** Without violating encapsulation, capture and externalize an
object's internal state so that the object can be restored to this state later.

![alt text](./etc/memento.png "Memento")

**Applicability:** Use the Memento pattern when

* a snapshot of an object's state must be saved so that it can be restored to that state later, and
* a direct interface to obtaining the state would expose implementation details and break the object's encapsulation

**Real world examples:**

* [java.util.Date](http://docs.oracle.com/javase/8/docs/api/java/util/Date.html)
