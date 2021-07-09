---
layout: pattern
title: Event Queue
folder: event-queue
permalink: /patterns/event-queue/zh
categories: Concurrency
language: zh
tags:
- Game programming
---

## 目的
如果您的可访问性资源有限（例如：
音频或数据库），但您需要处理所有想要使用它的请求。
它将所有请求放在一个队列中并异步处理它们。
当它是队列中的下一个并且在同一时间时为事件提供资源
将其从队列中删除。
## 类图
![alt text](./etc/model.png "Event Queue")

## 适用性
*您的可访问性资源有限，并且可以接受异步过程达到该资源


## 鸣谢

* [Mihaly Kuprivecz - Event Queue] (http://gameprogrammingpatterns.com/event-queue.html)
