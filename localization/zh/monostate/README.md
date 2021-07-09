---
layout: pattern
title: MonoState
folder: monostate
permalink: /patterns/monostate/zh
categories: Creational
language: zh
tags:
 - Instantiation
---

## 又称为
Borg

## 目的
强制执行一种行为，例如在所有实例之间共享相同的状态。

## 类图
![alt text](./etc/monostate.png "MonoState")

## 适用性
使用 Monostate 模式时

* 必须在一个类的所有实例之间共享相同的状态。
* 通常，此模式可用于任何可能使用单例的地方。然而单例使用不是透明的，单态使用是透明的。
* 单态比单态有一大优势。子类可以根据需要装饰共享状态，因此可以提供与基类不同的动态行为。

## 典型用例

* 日志类
* 管理与数据库的连接
* 文件管理器

## 真实世界的例子
