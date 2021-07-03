---
layout: pattern
title: Data Locality
folder: data-locality
permalink: /patterns/data-locality/zh
categories: Behavioral
language: zh
tags:
 - Game programming
 - Performance
---

## 目的
通过排列数据以利用 CPU 缓存来加速内存访问。

现代 CPU 具有缓存来加速内存访问。这些可以更快地访问与最近访问的内存相邻的内存。
利用这一点，通过增加数据局部性将数据按处理顺序保存在连续内存中来提高性能。

## 类图
![alt text](../../../data-locality/etc/data-locality.urm.png "Data Locality pattern class diagram")

## 适用性

* 与大多数优化一样，使用数据局部性模式的第一个准则是当您遇到性能问题时。
* 特别是使用这种模式，您还需要确保您的性能问题是由缓存未命中引起的。

## 真实案例

* [Artemis](http://gamadu.com/artemis/) 游戏引擎是第一个也是最著名的框架之一，它为游戏实体使用简单的 ID。

## 鸣谢

* [Game Programming Patterns Optimization Patterns: Data Locality](http://gameprogrammingpatterns.com/data-locality.html)