---
layout: pattern
title: Event Sourcing
folder: event-sourcing
permalink: /patterns/event-sourcing/zh
categories: Architectural
language: zh
tags:
- Performance
- Cloud distributed
---

## 目的
不是仅存储域中数据的当前状态，而是使用仅附加存储来记录对该数据采取的完整系列操作。
存储充当记录系统，可用于具体化领域对象。这可以通过避免同步数据模型和业务域的需要来简化复杂域中的任务，
同时提高性能、可扩展性和响应能力。它还可以为事务数据提供一致性，并维护完整的审计跟踪和历史记录，以支持采取补偿措施。
## 类图
![alt text](./etc/event-sourcing.png "Event Sourcing")

## 适用性
* 即使您的应用程序状态具有复杂的关系数据结构，您也需要非常高的性能来持久化您的应用程序状态
* 您需要记录应用程序状态的变化以及随时恢复状态的能力。
* 您需要通过重播过去的事件来调试生产问题。

## 真实案例

* [The Lmax Architecture](https://martinfowler.com/articles/lmax.html)

## 鸣谢

* [Martin Fowler - Event Sourcing](https://martinfowler.com/eaaDev/EventSourcing.html)
* [Event Sourcing in Microsoft's documentation](https://docs.microsoft.com/en-us/azure/architecture/patterns/event-sourcing)
* [Reference 3: Introducing Event Sourcing](https://msdn.microsoft.com/en-us/library/jj591559.aspx)
* [Event Sourcing pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/event-sourcing)
