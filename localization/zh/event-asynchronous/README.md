---
layout: pattern
title: Event-based Asynchronous
folder: event-asynchronous
permalink: /patterns/event-asynchronous/zh
categories: Concurrency
language: zh
tags:
- Reactive
---

## 目的
基于事件的异步模式可以利用多线程应用程序的优点，同时隐藏许多
多线程设计中固有的复杂问题。使用支持此模式的类可以让您：

1.“在后台”执行耗时的任务，例如下载和数据库操作，而不会中断您的应用程序。
2. 同时执行多个操作，当每个操作完成时接收通知。
3. 等待资源可用而不停止（“挂起”）您的应用程序。
4. 使用熟悉的事件和委托模型与挂起的异步操作进行通信。
## 类图
![alt text](./etc/event-asynchronous.png "Event-based Asynchronous")

## 适用性
使用此模式，当
* 需要耗时的任务在后台运行而不中断当前应用程序。

## 鸣谢

* [Event-based Asynchronous Pattern Overview](https://msdn.microsoft.com/en-us/library/wewwczdw%28v=vs.110%29.aspx?f=255&MSPPError=-2147217396)
