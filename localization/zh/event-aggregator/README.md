---
layout: pattern
title: Event Aggregator
folder: event-aggregator
permalink: /patterns/event-aggregator/zh
categories: Structural
language: zh
tags:
- Reactive
---

## 目的
具有大量对象的系统可能会导致复杂性
客户想要订阅事件。客户必须找到并注册
每个对象单独，如果每个对象有多个事件，那么每个事件
需要单独订阅。事件聚合器充当单一来源
许多对象的事件。它为许多对象的所有事件注册
允许客户端仅向聚合器注册。

## 类图
![alt text](../../../event-aggregator/etc/classes.png "Event Aggregator")

## 适用性
使用此模式，当

* 当您有很多对象时，事件聚合器是一个不错的选择
  潜在的事件源。而不是让观察者处理注册
  有了它们，您就可以将注册逻辑集中到 Event
  聚合器。除了简化注册，事件聚合器还
  简化了使用观察者的内存管理问题。

## 鸣谢

* [Martin Fowler - Event Aggregator](http://martinfowler.com/eaaDev/EventAggregator.html)
