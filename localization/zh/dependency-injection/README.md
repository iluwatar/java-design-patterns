---
layout: pattern
title: Dependency Injection
folder: dependency-injection
permalink: /patterns/dependency-injection/zh
categories: Creational
language: zh
tags:
 - Decoupling
---

## 目的

依赖注入是一种软件设计模式，其中一个或多个依赖项（或服务）被注入或通过引用传递到一个依赖对象（或客户端）中，并成为客户端状态的一部分。该模式将客户的依赖关系的创建与其自身的行为分开，这使程序设计可以松散耦合，并遵循控制反转和单一职责原则。

## 解释

真实世界例子

> 老巫师喜欢不时地装满烟斗抽烟。 但是，他不想只依赖一个烟草品牌，而是希望能够互换使用它们。 

通俗的说

> 依赖注入将客户端依赖的创建与其自身行为分开。

维基百科说

> 在软件工程中，依赖注入是一种对象接收其依赖的其他对象的技术。 这些其他对象称为依赖项。

**程序示例**

先介绍一下烟草接口和具体的品牌。

```java
public abstract class Tobacco {

  private static final Logger LOGGER = LoggerFactory.getLogger(Tobacco.class);

  public void smoke(Wizard wizard) {
    LOGGER.info("{} smoking {}", wizard.getClass().getSimpleName(),
        this.getClass().getSimpleName());
  }
}

public class SecondBreakfastTobacco extends Tobacco {
}

public class RivendellTobacco extends Tobacco {
}

public class OldTobyTobacco extends Tobacco {
}
```

下面是老巫师的类的层次结构。

```java
public interface Wizard {

  void smoke();
}

public class AdvancedWizard implements Wizard {

  private final Tobacco tobacco;

  public AdvancedWizard(Tobacco tobacco) {
    this.tobacco = tobacco;
  }

  @Override
  public void smoke() {
    tobacco.smoke(this);
  }
}
```

最后我们可以看到给老巫师任意品牌的烟草是多么的简单。

```java
    var advancedWizard = new AdvancedWizard(new SecondBreakfastTobacco());
    advancedWizard.smoke();
```

## 类图

![alt text](../../dependency-injection/etc/dependency-injection.png "Dependency Injection")

## 适用性

使用依赖注入当：

- 当你需要从对象中移除掉具体的实现内容时

* 使用模拟对象或存根隔离地启用类的单元测试

## 鸣谢

* [Dependency Injection Principles, Practices, and Patterns](https://www.amazon.com/gp/product/161729473X/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=161729473X&linkId=57079257a5c7d33755493802f3b884bd)
* [Clean Code: A Handbook of Agile Software Craftsmanship](https://www.amazon.com/gp/product/0132350882/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0132350882&linkCode=as2&tag=javadesignpat-20&linkId=2c390d89cc9e61c01b9e7005c7842871)
* [Java 9 Dependency Injection: Write loosely coupled code with Spring 5 and Guice](https://www.amazon.com/gp/product/1788296257/ref=as_li_tl?ie=UTF8&tag=javadesignpat-20&camp=1789&creative=9325&linkCode=as2&creativeASIN=1788296257&linkId=4e9137a3bf722a8b5b156cce1eec0fc1)
* [Google Guice Tutorial: Open source Java based dependency injection framework](https://www.amazon.com/gp/product/B083P7DZ8M/ref=as_li_tl?ie=UTF8&tag=javadesignpat-20&camp=1789&creative=9325&linkCode=as2&creativeASIN=B083P7DZ8M&linkId=04f0f902c877921e45215b624a124bfe)
