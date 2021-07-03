---
layout: pattern
title: Master-Worker
folder: master-worker-pattern
permalink: /patterns/master-worker-pattern/zh
categories: Concurrency
language: zh
tags:
 - Performance
---
## 也称为

> 主从或 Map-reduce

## 意图

> 用于集中并行处理。

## 类图
![alt text](./etc/master-worker-pattern.urm.png "Master-Worker pattern class diagram")

## 适用性
当数据可以被分成多个部分时，可以使用这种模式，所有这些部分都需要经过相同的计算才能给出结果，这些结果需要聚合以获得最终结果。

＃＃ 解释
在这种模式中，并行处理是使用由一个主机和一定数量的工人组成的系统来执行的，其中一个主机在工人之间分配工作，从他们那里得到结果并吸收所有结果以给出最终结果。唯一的通信是在 master 和 worker 之间——worker 之间没有任何通信，用户只与 master 通信以完成所需的工作。 Master 必须维护一个记录，记录分割的数据是如何分布的，有多少工人完成了他们的工作并返回了结果，以及结果本身能够正确聚合数据。

## 鸣谢

* [https://docs.gigaspaces.com/sbp/master-worker-pattern.html]
* [http://www.cs.sjsu.edu/~pearce/oom/patterns/behavioral/masterslave.htm]
