---
layout: pattern
title: Fluent Interface
folder: fluentinterface
permalink: /patterns/fluentinterface/zh
categories: Functional
language: zh
tags:
- Reactive
---

## 目的
流畅的界面提供了一个易于阅读、流畅的界面，通常模仿特定领域的
语。使用这种模式会产生几乎可以像人类语言一样阅读的代码。


## 解释

当您想要提供易于阅读、流畅的 API 时，Fluent Interface 模式非常有用。那些
界面倾向于模仿特定领域的语言，因此它们几乎可以被视为人类语言。

流畅的界面可以使用以下任何一种来实现

* 方法链 - 调用一个方法返回一些可以调用进一步方法的对象。
* 静态工厂方法和导入。
* 命名参数 - 可以使用静态工厂方法在 Java 中模拟。

真实世界的例子

> 我们需要根据不同的标准从列表中选择数字。这是一个很好的机会
> 利用流畅的界面模式，提供易读易用的开发者体验。

简单来说

> Fluent Interface 模式为代码提供了易于阅读的流畅界面。

维基百科说

> 在软件工程中，流畅的接口是一种面向对象的 API，其设计依赖于
> 广泛讨论方法链。它的目标是通过创建一个
> 领域特定语言 (DSL)。

**Programmatic Example**

在这个例子中，给出了 FluentIterable 接口的两个实现。

```java
public interface FluentIterable<E> extends Iterable<E> {

  FluentIterable<E> filter(Predicate<? super E> predicate);

  Optional<E> first();

  FluentIterable<E> first(int count);

  Optional<E> last();

  FluentIterable<E> last(int count);

  <T> FluentIterable<T> map(Function<? super E, T> function);

  List<E> asList();

  static <E> List<E> copyToList(Iterable<E> iterable) {
    var copy = new ArrayList<E>();
    iterable.forEach(copy::add);
    return copy;
  }
}
```

`SimpleFluentIterable` 会急切地求值，而且对于现实世界的应用来说成本太高。
```java
public class SimpleFluentIterable<E> implements FluentIterable<E> {
  ...
}
```

The `LazyFluentIterable` is evaluated on termination.

```java
public class LazyFluentIterable<E> implements FluentIterable<E> {
  ...
}
```

它们的用法通过一个经过过滤、转换和收集的简单数字列表来演示。这
之后打印结果。
```java
    var integerList = List.of(1, -61, 14, -22, 18, -87, 6, 64, -82, 26, -98, 97, 45, 23, 2, -68);

    prettyPrint("The initial list contains: ", integerList);

    var firstFiveNegatives = SimpleFluentIterable
        .fromCopyOf(integerList)
        .filter(negatives())
        .first(3)
        .asList();
    prettyPrint("The first three negative values are: ", firstFiveNegatives);


    var lastTwoPositives = SimpleFluentIterable
        .fromCopyOf(integerList)
        .filter(positives())
        .last(2)
        .asList();
    prettyPrint("The last two positive values are: ", lastTwoPositives);

    SimpleFluentIterable
        .fromCopyOf(integerList)
        .filter(number -> number % 2 == 0)
        .first()
        .ifPresent(evenNumber -> LOGGER.info("The first even number is: {}", evenNumber));


    var transformedList = SimpleFluentIterable
        .fromCopyOf(integerList)
        .filter(negatives())
        .map(transformToString())
        .asList();
    prettyPrint("A string-mapped list of negative numbers contains: ", transformedList);


    var lastTwoOfFirstFourStringMapped = LazyFluentIterable
        .from(integerList)
        .filter(positives())
        .first(4)
        .last(2)
        .map(number -> "String[" + valueOf(number) + "]")
        .asList();
    prettyPrint("The lazy list contains the last two of the first four positive numbers "
        + "mapped to Strings: ", lastTwoOfFirstFourStringMapped);

    LazyFluentIterable
        .from(integerList)
        .filter(negatives())
        .first(2)
        .last()
        .ifPresent(number -> LOGGER.info("Last amongst first two negatives: {}", number));
```

程序输出:

```java
The initial list contains: 1, -61, 14, -22, 18, -87, 6, 64, -82, 26, -98, 97, 45, 23, 2, -68.
The first three negative values are: -61, -22, -87.
The last two positive values are: 23, 2.
The first even number is: 14
A string-mapped list of negative numbers contains: String[-61], String[-22], String[-87], String[-82], String[-98], String[-68].
The lazy list contains the last two of the first four positive numbers mapped to Strings: String[18], String[6].
Last amongst first two negatives: -22    
```

## 类图

![Fluent Interface](./etc/fluentinterface.png "Fluent Interface")

## 适用性

在以下情况下使用 Fluent Interface 模式

* 您提供了一个可以从类似 DSL 的用法中受益的 API。
* 您有难以配置或使用的对象。

## 已知用途

* [Java 8 Stream API](http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html)
* [Google Guava FluentIterable](https://github.com/google/guava/wiki/FunctionalExplained)
* [JOOQ](http://www.jooq.org/doc/3.0/manual/getting-started/use-cases/jooq-as-a-standalone-sql-builder/)
* [Mockito](http://mockito.org/)
* [Java Hamcrest](http://code.google.com/p/hamcrest/wiki/Tutorial)

## Credits

* [Fluent Interface - Martin Fowler](http://www.martinfowler.com/bliki/FluentInterface.html)
* [Evolutionary architecture and emergent design: Fluent interfaces - Neal Ford](http://www.ibm.com/developerworks/library/j-eaed14/)
* [Internal DSL](http://www.infoq.com/articles/internal-dsls-java)
* [Domain Specific Languages](https://www.amazon.com/gp/product/0321712943/ref=as_li_tl?ie=UTF8&tag=javadesignpat-20&camp=1789&creative=9325&linkCode=as2&creativeASIN=0321712943&linkId=ad8351d6f5be7d8b7ecdb650731f85df)
