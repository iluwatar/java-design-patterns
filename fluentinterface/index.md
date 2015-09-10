---
layout: pattern
title: Fluent Interface
folder: fluentinterface
permalink: /patterns/fluentinterface/
categories: Architectural
tags: Java
---

**Intent:** A fluent interface provides an easy-readable, flowing interface, that often mimics a domain specific language. Using this pattern results in code that can be read nearly as human language.

![Fluent Interface](./etc/fluentinterface.png "Fluent Interface")

**Applicability:** Use the Fluent Interface pattern when

* you provide an API that would benefit from a DSL-like usage
* you have objects that are difficult to configure or use

**Real world examples:**

* [Java 8 Stream API](http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html)
* [Google Guava FluentInterable](https://github.com/google/guava/wiki/FunctionalExplained)
* [JOOQ](http://www.jooq.org/doc/3.0/manual/getting-started/use-cases/jooq-as-a-standalone-sql-builder/)

**Credits**

* [Fluent Interface - Martin Fowler](http://www.martinfowler.com/bliki/FluentInterface.html)
* [Evolutionary architecture and emergent design: Fluent interfaces - Neal Ford](http://www.ibm.com/developerworks/library/j-eaed14/)