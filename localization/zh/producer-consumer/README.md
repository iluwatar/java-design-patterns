---
layout: pattern
title: Producer Consumer
folder: producer-consumer
permalink: /patterns/producer-consumer/zh
categories: Concurrency
language: zh
tags:
 - Reactive
---

## 目的
生产者消费者设计模式是一种经典的并发模式，通过将工作与执行工作任务分开来减少生产者与消费者之间的耦合。

## 类图
![alt text](../../producer-consumer/etc/producer-consumer.png "Producer Consumer")

## 适用性
在以下情况下使用生产者消费者

* 通过将工作分成生产和消费两个工作进程来解耦系统
* 解决生产工作和消费工作需要不同时间的问题
