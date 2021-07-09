---
layout: pattern
title: Saga
folder: saga
permalink: /patterns/saga/zh
categories: Concurrency
language: zh
tags:
 - Cloud distributed
---

## 又称为
此模式具有与两阶段提交(XA事务)类似的目标

## 意图
此模式用于分布式服务中，以原子方式执行一组操作。
这与数据库中的事务类似，但在微服务体系结构中是执行的
在分布式环境中

## 解释
saga是在特定环境下的本地事务的序列。如果一个事务由于某种原因失败，
saga执行补偿事务(回滚)来撤销前面事务的影响。
Saga有两种类型:

——Choreography-Based传奇。
在这种方法中，没有中央指挥。
参与Saga的每个服务执行它们的事务和发布事件。
其他服务对这些事件起作用并执行它们的事务。
此外，他们可以根据情况发布或不发布其他事件。

——基于编配的传奇
在这种方法中，有一个Saga协调器来管理和指导所有的事务
参与者根据事件提供服务以执行本地事务。
这个编曲者也可以被认为是Saga的管理者。

## 类图
![alt text](./etc/saga.urm.png "Saga pattern class diagram")

## 适用性
使用Saga模式，如果:

— 您需要原子地执行一组与不同微服务相关的操作
- 你需要回滚不同的地方的更改，以防其中一个操作失败
- 你需要注意不同地方的数据一致性，包括不同的数据库
- 你不能使用2PC(两阶段提交)

## 鸣谢

- [Pattern: Saga](https://microservices.io/patterns/data/saga.html)
- [Saga distributed transactions pattern](https://docs.microsoft.com/en-us/azure/architecture/reference-architectures/saga/saga)
