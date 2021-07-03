---
layout: pattern
title: Guarded Suspension
folder: guarded-suspension
permalink: /patterns/guarded-suspension/zh
categories: Concurrency
language: zh
tags:
 - Decoupling
---

## 目的
当您想对状态不正确的对象执行方法时，使用受保护的挂起模式来处理这种情况。
## 类图
![Guarded Suspension diagram](./etc/guarded-suspension.png)

## 适用性
当开发人员知道方法执行将在有限的时间段内被阻塞时，使用 Guarded Suspension 模式
## 有关的模式

* Balking 
