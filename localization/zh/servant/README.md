---
layout: pattern
title: Servant
folder: servant
permalink: /patterns/servant/zh
categories: Behavioral
language: zh
tags:
 - Decoupling
---

## 目的
Servant用于为一组类提供某种行为。
而不是在每个类中定义行为，或者当我们不能分解的时候
此行为在公共父类中—它在Servant中定义一次。

## 类图
![alt text](./etc/servant-pattern.png "Servant")

## 适用性
在以下情况下使用Servant模式

* 当我们想让一些对象执行一个共同的动作，而不想把这个动作定义为每个类中的一个方法时。

## 鸣谢
* [Let's Modify the Objects-First Approach into Design-Patterns-First](http://edu.pecinovsky.cz/papers/2006_ITiCSE_Design_Patterns_First.pdf)
