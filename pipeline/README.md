---
layout: pattern
title: Pipeline
folder: pipeline
permalink: /patterns/pipeline/
categories: Behavioral
tags:
 - Decoupling
---

## Intent
Allows processing of data in a series of stages by giving in an initial input and passing the processed output to be used by the next stages.

## Class diagram
![alt text](./etc/pipeline.urm.png "Pipeline pattern class diagram")

## Applicability
Use the Pipeline pattern when you want to

* Execute individual stages that yields a final value
* Add readability to complex sequence of operations by providing a fluent builder as an interface
* Improve testability of code since stages will most likely be doing a single thing, complying to the [Single Responsibility Principle (SRP)](https://java-design-patterns.com/principles/#single-responsibility-principle)

## Typical Use Case

* Implement stages and execute them in an ordered manner

## Real world examples

* [java.util.Stream](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)
* [Maven Build Lifecycle](http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
* [Functional Java](https://github.com/functionaljava/functionaljava)

## Credits

* [The Pipeline Pattern — for fun and profit](https://medium.com/@aaronweatherall/the-pipeline-pattern-for-fun-and-profit-9b5f43a98130)
* [The Pipeline design pattern (in Java)](https://medium.com/@deepakbapat/the-pipeline-design-pattern-in-java-831d9ce2fe21)
* [Pipelines | Microsoft Docs](https://docs.microsoft.com/en-us/previous-versions/msp-n-p/ff963548(v=pandp.10))
