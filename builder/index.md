---
layout: pattern
title: Builder
folder: builder
permalink: /patterns/builder/
categories: Creational
tags: Java
---

**Intent:** Separate the construction of a complex object from its
representation so that the same construction process can create different
representations.

![alt text](./etc/builder_1.png "Builder")

**Applicability:** Use the Builder pattern when

* the algorithm for creating a complex object should be independent of the parts that make up the object and how they're assembled
* the construction process must allow different representations for the object that's constructed

**Real world examples:**

* [java.lang.StringBuilder](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html)
* [Apache Camel builders](https://github.com/apache/camel/tree/0e195428ee04531be27a0b659005e3aa8d159d23/camel-core/src/main/java/org/apache/camel/builder)
