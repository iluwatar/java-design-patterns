---
layout: pattern
title: Type-Object
folder: typeobjectpattern
permalink: /patterns/typeobjectpattern/zh
categories: Behavioral
language: zh
tags:
 - Game programming
 - Extensibility
---

## 目的
正如 Robert Nystrom 所著的 Game Programming Patterns 一书中所解释的，类型对象模式有助于

> 通过创建单个类允许灵活创建新的“类”，每个类的实例代表不同类型的对象

## 解释
比如说，我们正在开发一个游戏，它有一个英雄和许多要攻击英雄的怪物。这些怪物具有某些属性，例如攻击力、点数等，并且来自不同的“品种”，例如僵尸或食人魔。显而易见的答案是拥有一个包含一些字段和方法的基础 Monster 类，这些字段和方法可能会被 Zombie 或 Ogre 类等子类覆盖。但是随着我们继续构建游戏，可能会添加越来越多的怪物品种，并且现有怪物的某些属性也可能需要更改。在这种情况下，从基类继承的 OOP 解决方案将不是一种有效的方法。
使用类型对象模式，我们没有创建许多继承自基类的类，而是有 1 个类，其字段表示对象的“类型”。这使得代码更清晰，对象实例化也变得像解析具有对象属性的 json 文件一样简单。

## 类图
![alt text](./etc/typeobjectpattern.urm.png "Type-Object pattern class diagram")

## 适用性
在以下情况下可以使用此模式：

* 我们不知道我们需要什么类型。
* 我们希望能够在无需重新编译或更改代码的情况下修改或添加新类型。
* 不同“类型”对象之间的唯一区别是数据，而不是行为。

## 鸣谢

* [Game Programming Patterns - Type Object](http://gameprogrammingpatterns.com/type-object.html)
* [Types as Objects Pattern](http://www.cs.sjsu.edu/~pearce/modules/patterns/analysis/top.htm)
