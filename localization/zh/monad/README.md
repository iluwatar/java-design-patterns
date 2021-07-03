---
layout: pattern
title: Monad
folder: monad
permalink: /patterns/monad/zh
categories: Functional
language: zh
tags:
 - Reactive
---

## 目的
基于线性代数的 monad 的 Monad 模式表示链式操作的方式
一起一步一步。绑定函数可以描述为将一个输出传递到另一个输入
基于“相同类型”的合同。形式上，monad 由一个类型构造函数 M 和两个
操作：
bind - 将 monadic 对象和一个函数从普通对象转换为 monadic 值并返回 monadic 值
return - 采用普通类型对象并返回包装在 monadic 值中的此对象。

## 类图

![alt text](./etc/monad.png "Monad")

## 适用性
在以下任何一种情况下使用 Monad

* 当您想轻松链接操作时
* 当您想应用每个功能而不管它们中的任何一个的结果

## 鸣谢


* [Design Pattern Reloaded by Remi Forax](https://youtu.be/-k2X7guaArU)
* [Brian Beckman: Don't fear the Monad](https://channel9.msdn.com/Shows/Going+Deep/Brian-Beckman-Dont-fear-the-Monads)
* [Monad on Wikipedia](https://en.wikipedia.org/wiki/Monad_(functional_programming))
