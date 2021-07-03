---
layout: pattern
title: Hexagonal Architecture
folder: hexagonal
permalink: /patterns/hexagonal/zh
pumlformat: svg
categories: Architectural
language: zh
tags:
 - Decoupling
---

## 也被称为
* 端口和适配器
* 干净的架构
* 洋葱架构

## 意图
允许应用程序同等地由用户、程序、自动化测试或批处理脚本驱动，并独立于其最终运行时设备和数据库进行开发和测试。

##类图

![Hexagonal Architecture class diagram](./etc/hexagonal.png)

## 适用性
使用六边形架构模式时

* 当应用程序需要独立于任何框架时
* 当应用程序高度可维护和完全可测试很重要时

## 教程
* [Build Maintainable Systems With Hexagonal Architecture](http://java-design-patterns.com/blog/build-maintainable-systems-with-hexagonal-architecture/)

## 真实案例

* [Apache Isis](https://isis.apache.org/) builds generic UI and REST API directly from the underlying domain objects

## 鸣谢

* [Alistair Cockburn - Hexagonal Architecture](http://alistair.cockburn.us/Hexagonal+architecture)
