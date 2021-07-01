---
layout: pattern
title: Arrange/Act/Assert
folder: arrange-act-assert
permalink: /patterns/arrange-act-assert/zh
categories: Idiom
language: zh
tags:
 - Testing
---

## 或称

Given/When/Then

## 意图

安排/执行/断言（AAA）是组织单元测试的一种模式。

它将测试分为三个清晰而独特的步骤：

1. 安排：执行测试所需的设置和初始化。
2. 执行：采取测试所需的行动。
3. 断言：验证测试结果。

## 解释

这种模式有几个明显的好处。 它在测试的设置，操作和结果之间建立了清晰的分隔。 这种结构使代码更易于阅读和理解。 如果按顺序排列步骤并格式化代码以将它们分开，则可以扫描测试并快速了解其功能。

当您编写测试时，它还会强制执行一定程度的纪律。 您必须清楚地考虑您的测试将执行的三个步骤。 由于您已经有了大纲，因此可以使同时编写测试变得更加自然。

真实世界例子

> 我们需要为一个类编写全面而清晰的单元测试套件。

通俗地说

> 安排/执行/断言是一种测试模式，将测试分为三个清晰的步骤以方便维护。

WikiWikiWeb 上说

> 安排/执行/断言是用于在单元测试方法中排列和格式化代码的模式。

**程序示例**

让我们首先介绍要进行单元测试的`Cash`类。

```java
public class Cash {

  private int amount;

  Cash(int amount) {
    this.amount = amount;
  }

  void plus(int addend) {
    amount += addend;
  }

  boolean minus(int subtrahend) {
    if (amount >= subtrahend) {
      amount -= subtrahend;
      return true;
    } else {
      return false;
    }
  }

  int count() {
    return amount;
  }
}
```

Then we write our unit tests according to Arrange/Act/Assert pattern. Notice the clearly
separated steps for each unit test.

然后我们根据Arrange / Act / Assert模式编写单元测试。 注意每个单元测试的步骤是分开的清晰的。

```java
class CashAAATest {

  @Test
  void testPlus() {
    //Arrange
    var cash = new Cash(3);
    //Act
    cash.plus(4);
    //Assert
    assertEquals(7, cash.count());
  }

  @Test
  void testMinus() {
    //Arrange
    var cash = new Cash(8);
    //Act
    var result = cash.minus(5);
    //Assert
    assertTrue(result);
    assertEquals(3, cash.count());
  }

  @Test
  void testInsufficientMinus() {
    //Arrange
    var cash = new Cash(1);
    //Act
    var result = cash.minus(6);
    //Assert
    assertFalse(result);
    assertEquals(1, cash.count());
  }

  @Test
  void testUpdate() {
    //Arrange
    var cash = new Cash(5);
    //Act
    cash.plus(6);
    var result = cash.minus(3);
    //Assert
    assertTrue(result);
    assertEquals(8, cash.count());
  }
}
```

## 适用性

使用 Arrange/Act/Assert 模式当

* You need to structure your unit tests so that they're easier to read, maintain, and enhance. 
* 你需要结构化你的单元测试代码这样它们可以更好的阅读，维护和增强。

## 鸣谢

* [Arrange, Act, Assert: What is AAA Testing?](https://blog.ncrunch.net/post/arrange-act-assert-aaa-testing.aspx)
* [Bill Wake: 3A – Arrange, Act, Assert](https://xp123.com/articles/3a-arrange-act-assert/)
* [Martin Fowler: GivenWhenThen](https://martinfowler.com/bliki/GivenWhenThen.html)
* [xUnit Test Patterns: Refactoring Test Code](https://www.amazon.com/gp/product/0131495054/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0131495054&linkId=99701e8f4af2f7e8dd50d720c9b63dbf)
* [Unit Testing Principles, Practices, and Patterns](https://www.amazon.com/gp/product/1617296279/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617296279&linkId=74c75cf22a63c3e4758ae08aa0a0cc35)
* [Test Driven Development: By Example](https://www.amazon.com/gp/product/0321146530/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321146530&linkId=5c63a93d8c1175b84ca5087472ef0e05)
