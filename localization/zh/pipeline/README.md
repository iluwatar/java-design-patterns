---
layout: pattern
title: Pipeline
folder: pipeline
permalink: /patterns/pipeline/zh
categories: Behavioral
language: zh
tags:
 - Decoupling
---

## 目的
通过提供初始输入并传递数据，允许在一系列阶段中处理数据
处理后的输出供下一阶段使用。

## 解释

流水线模式使用有序阶段来处理输入值序列。每个实施
任务由管道的一个阶段表示。您可以将管道视为类似于装配
工厂中的流水线，其中装配线上的每个项目都是分阶段构建的。部分
组装好的物品从一个组装阶段传递到另一个组装阶段。流水线的输出发生
与输入的顺序相同。

真实世界的例子

> 假设我们想将一个字符串传递到一系列过滤阶段并将其转换为
> 最后阶段的字符数组。

简单来说

> 流水线模式是一条装配线，其中部分结果从一个阶段传递到另一个阶段。

维基百科说

> 在软件工程中，管道由一系列处理元素（流程、
> 线程、协程、函数等），排列使得每个元素的输出都是输入
> 下一个；该名称类似于物理管道。

**程序示例**

我们管道的阶段称为“处理程序”。

```java
interface Handler<I, O> {
  O process(I input);
}
```

In our string processing example we have 3 different concrete `Handler`s.

```java
class RemoveAlphabetsHandler implements Handler<String, String> {
  ...
}

class RemoveDigitsHandler implements Handler<String, String> {
  ...
}

class ConvertToCharArrayHandler implements Handler<String, char[]> {
  ...
}
```

这是将一个接一个地收集和执行处理程序的“管道”。

```java
class Pipeline<I, O> {

  private final Handler<I, O> currentHandler;

  Pipeline(Handler<I, O> currentHandler) {
    this.currentHandler = currentHandler;
  }

  <K> Pipeline<I, K> addHandler(Handler<O, K> newHandler) {
    return new Pipeline<>(input -> newHandler.process(currentHandler.process(input)));
  }

  O execute(I input) {
    return currentHandler.process(input);
  }
}
```


这是正在处理字符串的“管道”。
```java
    var filters = new Pipeline<>(new RemoveAlphabetsHandler())
        .addHandler(new RemoveDigitsHandler())
        .addHandler(new ConvertToCharArrayHandler());
    filters.execute("GoYankees123!");
```

## 类图

![alt text](./etc/pipeline.urm.png "Pipeline pattern class diagram")

## 适用性
需要时使用管道模式

* 执行产生最终值的各个阶段。
* 通过提供流畅的构建器作为界面，为复杂的操作序列增加可读性。
* 提高代码的可测试性，因为阶段很可能只做一件事情，遵守
[Single Responsibility Principle (SRP)](https://java-design-patterns.com/principles/#single-responsibility-principle)

## 已知用途

* [java.util.Stream](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)
* [Maven Build Lifecycle](http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html)
* [Functional Java](https://github.com/functionaljava/functionaljava)

## 相关模式

* [Chain of Responsibility](https://java-design-patterns.com/patterns/chain/)

## 鸣谢

* [The Pipeline Pattern — for fun and profit](https://medium.com/@aaronweatherall/the-pipeline-pattern-for-fun-and-profit-9b5f43a98130)
* [The Pipeline design pattern (in Java)](https://medium.com/@deepakbapat/the-pipeline-design-pattern-in-java-831d9ce2fe21)
* [Pipelines | Microsoft Docs](https://docs.microsoft.com/en-us/previous-versions/msp-n-p/ff963548(v=pandp.10))
