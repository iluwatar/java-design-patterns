---
layout: pattern
title: Leader Election
folder: leader-election
permalink: /patterns/leader-election/zh
categories: Behavioral
language: zh
tags:
 - Cloud distributed
---

## 目的
Leader选举模式常用于云系统设计。它可以帮助确保任务实例正确选择领导者实例，
并且不会相互冲突、导致对共享资源的争用或无意中干扰其他任务实例正在执行的工作。

## 类图
![alt text](./etc/leader-election.urm.png "Leader Election pattern class diagram")

## 适用性
使用此模式时

* 分布式应用程序中的任务，例如云托管解决方案，需要仔细协调，并且没有天生的领导者。

请勿在以下情况下使用此模式

* 有一个天生的领导者或专门的流程可以随时充当领导者。例如，可以实现协调任务实例的单例进程。如果此过程失败或变得不健康，系统可以将其关闭并重新启动。
* 通过使用更轻量级的机制，可以轻松实现任务之间的协调。例如，如果多个任务实例只需要对共享资源进行协调访问，则更好的解决方案可能是使用乐观或悲观锁定来控制对该资源的访问。

## 真实世界的例子

* [Raft Leader Election](https://github.com/ronenhamias/raft-leader-election)

## 鸣谢

* [Leader Election pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/leader-election)
