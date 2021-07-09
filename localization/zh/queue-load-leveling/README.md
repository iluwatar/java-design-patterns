---
layout: pattern
title: Queue based load leveling
folder: queue-load-leveling
permalink: /patterns/queue-load-leveling/zh
categories: Concurrency
language: zh
tags:
 - Decoupling
 - Performance
 - Cloud distributed
---
## 意图
使用队列作为任务和它调用的服务之间的缓冲区，以便平滑
可能会导致服务失败或任务超时的间歇性重负载。
这种模式有助于最大限度地减少需求高峰对可用性和响应能力的影响
对于任务和服务。

##类图
![alt text](./etc/queue-load-leveling.gif "queue-load-leveling")

## 适用性
* 这种模式非常适合使用可能会过载的服务的任何类型的应用程序。
* 如果应用程序期望来自服务的响应具有最小延迟，则此模式可能不适合。

## 教程
* [Queue-Based Load Leveling Pattern](http://java-design-patterns.com/blog/queue-load-leveling/)
##真实世界的例子

* Microsoft Azure Web 角色使用单独的存储服务来存储数据。如果 Web 角色的大量实例同时运行，则存储服务可能会不堪重负，无法足够快地响应请求以防止这些请求超时或失败。

## 鸣谢

* [Queue-Based Load Leveling pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/queue-based-load-leveling)
