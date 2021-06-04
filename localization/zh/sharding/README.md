---
layout: pattern  
title: Sharding 
folder: sharding  
permalink: /patterns/sharding/  
categories: Behavioral
tags:  
 - Performance
 - Cloud distributed
---

## 含义 
分片模式是指将数据存储划分为水平分区或分片。每个分片都有相同的模式，但持有自己独特的数据子集。

一个分片本身就是一个数据存储（它可以包含许多不同类型的实体的数据），运行在作为存储节点的服务器上。

## 类图
![alt text](../../sharding/etc/sharding.urm.png "Sharding pattern class diagram")

## 适用场景 
这种设计模式提供了一下的好处：

- 你可以通过增加在额外的存储节点上，运行的更多分片来实现系统扩容。
- 系统可以使用现成的廉价硬件，而不是为每个存储节点使用专门（或者昂贵）的服务器硬件。
- 你可以通过平衡各分片之间的工作负载来减少竞争，以提高性能。
- 在云环境中，分片可以在物理上靠近访问该节点数据的用户。

## 引用

* [Sharding pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/sharding)