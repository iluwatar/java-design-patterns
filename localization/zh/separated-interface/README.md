---
layout: pattern
title: Separated Interface
folder: separated-interface
permalink: /patterns/separated-interface/zh
categories: Structural
language: zh
tags:
 - Decoupling
---


## 目的

在不同的包中分离接口定义和实现。这允许客户端
完全不知道实现。

## 解释
真实案例


创建发票生成器时，可以使用不同的税务计算器
根据购买类型，地区等，在发票中添加>。

坦率地说

>分离接口模式鼓励保持接口的实现与
>是客户端及其定义，所以客户端不依赖于实现。

客户端代码可以将一些特定的功能抽象到接口，并定义
作为SPI的接口([服务编程接口](https://en.wikipedia.org/wiki/Service_provider_interface)
是一个打算由第三方实现或扩展的开放API)。可能另一个包
用一个具体的逻辑实现这个接口定义，它将被注入到客户端中
运行时(使用第三个类，将实现注入到客户机中)或编译时的代码
(使用Plugin模式和一些可配置文件)。

编程示例

* *客户* *

' InvoiceGenerator '类接受产品的成本并计算总成本应付金额(含税)。

```java
public class InvoiceGenerator {

  private final TaxCalculator taxCalculator;

  private final double amount;

  public InvoiceGenerator(double amount, TaxCalculator taxCalculator) {
    this.amount = amount;
    this.taxCalculator = taxCalculator;
  }

  public double getAmountWithTax() {
    return amount + taxCalculator.calculate(amount);
  }

}
```

税务计算逻辑被委托给“TaxCalculator”接口。

```java
public interface TaxCalculator {

  double calculate(double amount);

}
```

**实现包**

在另一个包中(客户端完全不知道)存在多个实现
' TaxCalculator '界面。“ForeignTaxCalculator”就是其中之一，征收60%的税
国际产品。
```java
public class ForeignTaxCalculator implements TaxCalculator {

  public static final double TAX_PERCENTAGE = 60;

  @Override
  public double calculate(double amount) {
    return amount * TAX_PERCENTAGE / 100.0;
  }

}
```

另一个是“国税计算器”，对国际产品征收20%的税。

```java
public class DomesticTaxCalculator implements TaxCalculator {

  public static final double TAX_PERCENTAGE = 20;

  @Override
  public double calculate(double amount) {
    return amount * TAX_PERCENTAGE / 100.0;
  }

}
```


这两个实现都通过App.java实例化并注入到客户端类中类。
```java
    var internationalProductInvoice = new InvoiceGenerator(PRODUCT_COST, new ForeignTaxCalculator());

    LOGGER.info("Foreign Tax applied: {}", "" + internationalProductInvoice.getAmountWithTax());

    var domesticProductInvoice = new InvoiceGenerator(PRODUCT_COST, new DomesticTaxCalculator());

    LOGGER.info("Domestic Tax applied: {}", "" + domesticProductInvoice.getAmountWithTax());
```

## 类图

![alt text](./etc/class_diagram.png "Separated Interface")

## 适用性

使用分离接口模式

* 你正在开发一个框架包，你的框架需要通过接口调用一些应用程序代码。
* 你有独立的实现功能的包，这些功能可以在运行时或编译时插入到你的客户端代码中。
* 你的代码位于一个不允许调用接口实现层的层中。例如，域层需要调用数据映射器。

## 教程

* [Separated Interface Tutorial](https://www.youtube.com/watch?v=d3k-hOA7k2Y)

## 鸣谢

* [Martin Fowler](https://www.martinfowler.com/eaaCatalog/separatedInterface.html)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321127420&linkId=e08dfb7f2cf6153542ef1b5a00b10abc)
