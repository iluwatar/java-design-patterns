---  
layout: pattern  
title: Subclass Sandbox 
folder:  subclass-sandbox  
permalink: /patterns/subclass-sandbox/zh
categories: Behavioral
language: zh
tags:  
 - Game programming
---  

## 目的  
子类沙箱模式描述了一个基本思想，但没有很多详细的机制。当您有几个相似的子类时，您将需要该模式。如果您必须进行微小更改，请更改基类，而不必更改所有子类。因此基类必须能够提供派生类需要执行的所有操作。

## 类图
![alt text](./etc/subclass-sandbox.urm.png "Subclass Sandbox pattern class diagram")
  
## 适用性  
子类沙盒模式是一种非常简单、常见的模式，潜伏在许多代码库中，甚至在游戏之外。如果你有一个非虚拟的受保护方法，你可能已经在使用这样的东西。在以下情况下，子类 Sandbox 非常适合：

- 您有一个带有许多派生类的基类。
- 基类能够提供派生类可能需要执行的所有操作。
- 子类中存在行为重叠，您希望更轻松地在它们之间共享代码。
- 您希望最小化这些派生类与程序其余部分之间的耦合。
## 鸣谢  
  
* [Game Programming Patterns - Subclass Sandbox]([http://gameprogrammingpatterns.com/subclass-sandbox.html](http://gameprogrammingpatterns.com/subclass-sandbox.html))
