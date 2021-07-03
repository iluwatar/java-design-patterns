---
layout: pattern
title: Double Dispatch
folder: double-dispatch
permalink: /patterns/double-dispatch/zh
categories: Idiom
language: zh
tags:
- Extensibility
---

## 目的
双调度模式是一种创建可维护动态的方法，基于接收器和参数类型的行为

## 类图
![alt text](../../../double-dispatch/etc/double-dispatch.png "Double Dispatch")

## 适用性
使用此模式，当

* 动态行为不仅基于接收对象的类型，还基于接收方法的参数类型。

## 真实案例

* [ObjectOutputStream](https://docs.oracle.com/javase/8/docs/api/java/io/ObjectOutputStream.html)
