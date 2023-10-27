---
title: Step Builder
category: Creational
language: zn
tag:
 - Instantiation
---

## 又被称为
分步构建

## 目的
这是构建者模式的一个扩展，完全指导用户创建对象，没有混淆的机会。
用户体验会大大提升，因为他只能看到下一个步骤的方法，直到适当的时机才会出现构建对象的“build”方法。

## 类图
![alt text](./etc/step-builder.png "Step Builder")

## 应用
使用分布构建模式当创建复杂对象的算法需要独立于组成对象的部分以及它们的组装方式，且构造过程必须允许对象有不同的表示形式，并且在此过程中顺序很重要时。

## 鸣谢

* [Marco Castigliego - Step Builder](http://rdafbn.blogspot.co.uk/2012/07/step-builder-pattern_28.html)
