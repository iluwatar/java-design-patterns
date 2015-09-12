---
layout: pattern
title: Iterator
folder: iterator
permalink: /patterns/iterator/
categories: Behavioral
tags: Java
---

**Intent:** Provide a way to access the elements of an aggregate object
sequentially without exposing its underlying representation.

![alt text](./etc/iterator_1.png "Iterator")

**Applicability:** Use the Iterator pattern

* to access an aggregate object's contents without exposing its internal representation
* to support multiple traversals of aggregate objects
* to provide a uniform interface for traversing different aggregate structures

**Real world examples:**

* [java.util.Iterator](http://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html)
