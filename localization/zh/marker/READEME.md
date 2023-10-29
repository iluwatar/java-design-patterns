---
title: Marker Interface
category: Structural
language: ch
tag:
 - Decoupling
---

## 目的
使用空接口作为标记来区分特殊处理的对象。

## 类图
![alt text](./etc/MarkerDiagram.png "Marker Interface")

## 适用场景
在以下场景使用Marker Interface pattern

* 您希望将特殊对象与普通对象区分开来（以不同方式处理它们）
* 您希望标记某些对象可用于某些类型的操作

## 应用案例

* [javase.8.docs.api.java.io.Serializable](https://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html)
* [javase.8.docs.api.java.lang.Cloneable](https://docs.oracle.com/javase/8/docs/api/java/lang/Cloneable.html)

## 鸣谢

* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
