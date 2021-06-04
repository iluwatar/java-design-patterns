---
layout: pattern
title: Collection Pipeline
folder: collection-pipeline
permalink: /patterns/collection-pipeline/
categories: Functional
tags:
 - Reactive
---

## 释义
**集合管道（Collection Pipeline）**包含**函数组合（Function Composition）**和**集合管道（Collection Pipeline）**两组合概念，这是两种函数式编程模式，你可以在代码中结合这两种模式来进行集合迭代。
在函数式编程中，可以通过一系列较小的模块化函数或操作来编排复杂的操作。这一系列函数被称为函数组合。当一个数据集合流经一个函数组合时，它就成为一个集合管道。函数组合和集合管道是函数式编程中经常使用的两种设计模式。

## 类图
![alt text](../../collection-pipeline/etc/collection-pipeline.png "Collection Pipeline")

## 适用场景
在以下场景适用集合管道模式：

* 当你想执行一组连续的算子操作，其中一个算子收集的输出需要被输入到下一个算子中
* 当你在代码中需要使用大量的中间状态语句时
* 当你在代码中使用大量的循环语句时

## 引用

* [Function composition and the Collection Pipeline pattern](https://www.ibm.com/developerworks/library/j-java8idioms2/index.html)
* [Martin Fowler](https://martinfowler.com/articles/collection-pipeline/)
* [Java8 Streams](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)