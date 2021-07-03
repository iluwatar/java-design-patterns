---
layout: pattern
title: Value Object
folder: value-object
permalink: /patterns/value-object/zh
categories: Creational
language: zh
tags:
 - Instantiation
---
## 目的
提供遵循值语义而不是引用语义的对象。
这意味着值对象的相等性不是基于身份。两个值对象是
当它们具有相同的值时相等，不一定是相同的对象。

## 类图
![alt text](./etc/value-object.png "Value Object")

## 适用性
使用值对象时

* 您需要根据对象的值来衡量对象的相等性

## 真实世界的例子

* [java.util.Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)
* [java.time.LocalDate](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html)
* [joda-time, money, beans](http://www.joda.org/)

## 鸣谢

* [Patterns of Enterprise Application Architecture](http://www.martinfowler.com/books/eaa.html)
* [VALJOs - Value Java Objects : Stephen Colebourne's blog](http://blog.joda.org/2014/03/valjos-value-java-objects.html)
* [Value Object : Wikipedia](https://en.wikipedia.org/wiki/Value_object)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
