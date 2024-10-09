---
title: Trampoline
shortTitle: Trampoline
category: Behavioral
language: zh
tag:
- Performance
---

## 目的

蹦床模式是用于在 Java 中递归地实现算法，而不会破坏堆栈，并且可以交错地执行函数，而无需将它们编码在一起。

## 解释

递归是一种常用的技术，用于以分而治之的方式解决算法问题。例如，计算斐波那契累加和与阶乘。在这类问题中，递归比循环更简单直接。此外，递归可能需要更少的代
码并且看起来更简明。有一种说法是，每个递归问题都可以使用循环来解决，但代价是编写更难以理解的代码。然而，递归型解决方案有一个很大的警告。对于每个
递归调用，通常需要存储一个中间值，并且可用的栈内存有限。栈内存不足会导致栈溢出错误并停止程序执行。蹦床模式是一种允许在 Java 中定义递归算法而无需破坏
堆栈的技巧。

现实世界例子

> 使用蹦床模式进行递归斐波那契计算，不存在堆栈溢出问题。

通俗地说

> 蹦床模式允许递归而不会耗尽栈内存。

维基百科上说

> 在 Java 中，蹦床是指使用反射来避免使用内部类，例如在事件侦听器中。反射调用的整理操作时间换成了内部类的整理操作空间。 Java 中的蹦床通常涉及创建 GenericListener 以将事件传递到外部类。

**编程实例**

这是 Java 中的蹦床实现。

当在返回的蹦床上调用 `get` 时，只要返回的具体实例是蹦床，内部就会在返回的蹦床上迭代调用跳转，并在返回的实例完成后停止。

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

使用蹦床获取斐波那契值。

```java
public static void main(String[] args) {
    LOGGER.info("Start calculating war casualties");
    var result = loop(10, 1).result();
    LOGGER.info("The number of orcs perished in the war: {}", result);
}

public static Trampoline<Integer> loop(int times, int prod) {
    if (times == 0) {
        return Trampoline.done(prod);
    } else {
        return Trampoline.more(() -> loop(times - 1, prod * times));
    }
}
```

程序输出：

```java
19:22:24.462 [main] INFO com.iluwatar.trampoline.TrampolineApp - Start calculating war casualties
19:22:24.472 [main] INFO com.iluwatar.trampoline.TrampolineApp - The number of orcs perished in the war: 3628800
```

## 类图

![alt text](./etc/trampoline_urm.png "Trampoline pattern class diagram")

## 适用场景

使用蹦床模式时
* 用于实现尾递归函数。该模式允许切换无堆栈操作。
* 用于在同一线程上交错执行两个或多个函数。

## 现实案例

* [cyclops-react](https://github.com/aol/cyclops-react)

## 鸣谢

* [Trampolining: a practical guide for awesome Java Developers](https://medium.com/@johnmcclean/trampolining-a-practical-guide-for-awesome-java-developers-4b657d9c3076)
* [Trampoline in java ](http://mindprod.com/jgloss/trampoline.html)
* [Laziness, trampolines, monoids and other functional amenities: this is not your father's Java](https://www.slideshare.net/mariofusco/lazine)
* [Trampoline implementation](https://github.com/bodar/totallylazy/blob/master/src/com/googlecode/totallylazy/Trampoline.java)
* [What is a trampoline function?](https://stackoverflow.com/questions/189725/what-is-a-trampoline-function)
* [Modern Java in Action: Lambdas, streams, functional and reactive programming](https://www.amazon.com/gp/product/1617293563/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617293563&linkId=ad53ae6f9f7c0982e759c3527bd2595c)
* [Java 8 in Action: Lambdas, Streams, and functional-style programming](https://www.amazon.com/gp/product/1617291994/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617291994&linkId=e3e5665b0732c59c9d884896ffe54f4f)
