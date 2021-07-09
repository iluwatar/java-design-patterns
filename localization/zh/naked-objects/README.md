---
layout: pattern
title: Naked Objects
folder: naked-objects
permalink: /patterns/naked-objects/zh
categories: Architectural
language: zh
tags:
 - Decoupling
---

## 目的
Naked Objects 架构模式非常适合快速
原型制作。使用该模式，您只需要编写域对象，
其他一切都是由框架自动生成的。

## 类图
![alt text](./etc/naked-objects.png "Naked Objects")

## 适用性
使用 Naked Objects 模式时

*您正在制作原型并需要快速的开发周期
* 自动生成的用户界面就足够了
* 您希望将域自动发布为 REST 服务

## 真实世界的例子

* [Apache Isis](https://isis.apache.org/)

## 鸣谢

* [Richard Pawson - Naked Objects](http://downloads.nakedobjects.net/resources/Pawson%20thesis.pdf)
