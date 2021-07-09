---
layout: pattern
title: Event Driven Architecture
folder: event-driven-architecture
permalink: /patterns/event-driven-architecture/zh
categories: Architectural
language: zh
tags:
- Reactive
---

## 目的
使用事件驱动架构向其他应用程序发送和通知对象的状态更改。
## 类图
![alt text](./etc/eda.png "Event Driven Architecture")

## 适用性
当

* 你想创建一个松散耦合的系统
* 你想建立一个反应更灵敏的系统
* 你想要一个更容易扩展的系统

## 真实案例
* SendGrid，一个电子邮件 API，在处理、发送、打开电子邮件等时发送事件... (https://sendgrid.com/docs/API_Reference/Webhooks/event.html)
* Chargify，一个计费 API，通过各种事件公开支付活动 (https://docs.chargify.com/api-events)
* Amazon 的 AWS Lambda，让您可以执行代码以响应事件，例如 Amazon S3 存储桶的更改、Amazon DynamoDB 表的更新或您的应用程序或设备生成的自定义事件。 （https://aws.amazon.com/lambda）
* MySQL 根据数据库表上发生的插入和更新事件等事件运行触发器。

## 鸣谢

* [Event-driven architecture - Wikipedia](https://en.wikipedia.org/wiki/Event-driven_architecture)
* [Fundamental Components of an Event-Driven Architecture](http://giocc.com/fundamental-components-of-an-event-driven-architecture.html)
* [Real World Applications/Event Driven Applications](https://wiki.haskell.org/Real_World_Applications/Event_Driven_Applications)
* [Event-driven architecture definition](http://searchsoa.techtarget.com/definition/event-driven-architecture)
