---
layout: pattern
title: Marker Interface
folder: marker
permalink: /patterns/marker/zh
categories: Structural
language: zh
tags:
 - Decoupling
---
## 意图
使用空接口作为标记来区分特殊处理的对象。

## 类图
![alt text](./etc/MarkerDiagram.png "Marker Interface")

## 适用性
在以下情况下使用标记接口模式

* 您想从普通对象中识别特殊对象（区别对待它们）
* 您想标记某些对象可用于某些类型的操作

## 真实世界的例子

* [javase.8.docs.api.java.io.Serializable](https://docs.oracle.com/javase/8/docs/api/java/io/Serializable.html)
* [javase.8.docs.api.java.lang.Cloneable](https://docs.oracle.com/javase/8/docs/api/java/lang/Cloneable.html)

## 鸣谢

* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
