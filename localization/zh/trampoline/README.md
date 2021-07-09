---
layout: pattern
title: Trampoline
folder: trampoline
permalink: /patterns/trampoline/zh
categories: Behavioral
language: zh
tags:
 - Performance
---

## 意图

Trampoline 模式用于在 Java 中递归地实现算法而不会破坏堆栈
并在不将它们硬编码在一起的情况下交错执行函数。

## 解释

递归是一种经常采用的技术，用于解决分而治之的算法问题
风格。例如计算斐波那契累计和和阶乘。在这类问题中
递归比它们的循环更直接。此外，递归可能需要更少
代码看起来更简洁。有句话说，每个递归问题都可以用
以编写更难理解的代码为代价的循环。

然而，递归类型的解决方案有一个很大的警告。对于每个递归调用，它通常需要
存储的中间值，可用的堆栈内存量有限。快用完
堆栈内存会产生堆栈溢出错误并停止程序执行。

Trampoline 模式是一个技巧，它允许我们在 Java 中定义递归算法而不会破坏
堆。

真实世界的例子

> 使用 Trampoline 模式的没有堆栈溢出问题的递归斐波那契计算。

简单来说

> Trampoline 模式允许在不耗尽堆栈内存的情况下进行递归。

维基百科说

> 在 Java 中，trampoline 是指使用反射来避免使用内部类，例如在事件中
> 听众。反射调用的时间开销被交换为内部的空间开销
> 班级。 Java 中的 Trampolines 通常涉及创建 GenericListener 以将事件传递给
> 一个外部类。

**程序示例**

这是 Java 中的 `Trampoline` 实现。

当在返回的 Trampoline 上调用 `get` 时，它会在内部迭代调用 `jump`
只要返回的具体实例是`Trampoline`，就返回`Trampoline`，一旦
返回的实例已完成。
```java
public interface Trampoline<T> {

  T get();

  default Trampoline<T> jump() {
    return this;
  }

  default T result() {
    return get();
  }

  default boolean complete() {
    return true;
  }

  static <T> Trampoline<T> done(final T result) {
    return () -> result;
  }

  static <T> Trampoline<T> more(final Trampoline<Trampoline<T>> trampoline) {
    return new Trampoline<T>() {
      @Override
      public boolean complete() {
        return false;
      }

      @Override
      public Trampoline<T> jump() {
        return trampoline.result();
      }

      @Override
      public T get() {
        return trampoline(this);
      }

      T trampoline(final Trampoline<T> trampoline) {
        return Stream.iterate(trampoline, Trampoline::jump)
            .filter(Trampoline::complete)
            .findFirst()
            .map(Trampoline::result)
            .orElseThrow();
      }
    };
  }
}
```

使用 `Trampoline` 获取斐波那契值。
```java
  public static Trampoline<Integer> loop(int times, int prod) {
    if (times == 0) {
      return Trampoline.done(prod);
    } else {
      return Trampoline.more(() -> loop(times - 1, prod * times));
    }
  }
  
  log.info("start pattern");
  var result = loop(10, 1).result();
  log.info("result {}", result);
```

程序输出：
```
start pattern
result 3628800
```

## 类图

![alt text](./etc/trampoline.urm.png "Trampoline pattern class diagram")

## 适用性
使用蹦床模式时

* 用于实现尾递归函数。这种模式允许开启无堆栈操作。
* 用于在同一线程上交错执行两个或多个函数。

## 已知用途

* [cyclops-react](https://github.com/aol/cyclops-react)

## 鸣谢

* [Trampolining: a practical guide for awesome Java Developers](https://medium.com/@johnmcclean/trampolining-a-practical-guide-for-awesome-java-developers-4b657d9c3076)
* [Trampoline in java ](http://mindprod.com/jgloss/trampoline.html)
* [Laziness, trampolines, monoids and other functional amenities: this is not your father's Java](https://www.slideshare.net/mariofusco/lazine)
* [Trampoline implementation](https://github.com/bodar/totallylazy/blob/master/src/com/googlecode/totallylazy/Trampoline.java)
* [What is a trampoline function?](https://stackoverflow.com/questions/189725/what-is-a-trampoline-function)
* [Modern Java in Action: Lambdas, streams, functional and reactive programming](https://www.amazon.com/gp/product/1617293563/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617293563&linkId=ad53ae6f9f7c0982e759c3527bd2595c)
* [Java 8 in Action: Lambdas, Streams, and functional-style programming](https://www.amazon.com/gp/product/1617291994/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617291994&linkId=e3e5665b0732c59c9d884896ffe54f4f)
