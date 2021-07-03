---
layout: pattern
title: CQRS
folder: cqrs
permalink: /patterns/cqrs/zh
categories: Architectural
language: zh
tags:
  - Performance
  - Cloud distributed
---

## 目的
CQRS 命令查询职责分离 - 将查询端与命令端分开。

## 类图
![alt text](../../../cqrs/etc/cqrs.png "CQRS")

## 适用性
使用CQRS模式，当

* 您希望独立扩展查询和命令。
* 您希望对查询和命令使用不同的数据模型。在处理复杂域时很有用。
* 您想使用事件源或基于任务的 UI 等架构。

## gg'xie

* [Greg Young - CQRS, Task Based UIs, Event Sourcing agh!](http://codebetter.com/gregyoung/2010/02/16/cqrs-task-based-uis-event-sourcing-agh/)
* [Martin Fowler - CQRS](https://martinfowler.com/bliki/CQRS.html)
* [Oliver Wolf - CQRS for Great Good](https://www.youtube.com/watch?v=Ge53swja9Dw)
* [Command and Query Responsibility Segregation (CQRS) pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/cqrs)
