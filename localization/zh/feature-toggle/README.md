---
layout: pattern
title: Feature Toggle
folder: feature-toggle
permalink: /patterns/feature-toggle/zh
categories: Behavioral
language: zh
tags:
- Extensibility
---

## 也称为
Feature Flag

## 目的
用于根据属性或分组切换代码执行路径。允许发布、测试新功能
并推出。如果需要，允许快速切换回旧功能。需要注意的是，这种模式，
可以很容易地引入代码复杂性。还有一个令人担忧的问题是，切换最终会成为旧功能
逐步淘汰永远不会被删除，这会导致冗余代码异味并增加可维护性。

## 类图
![alt text](./etc/feature-toggle.png "Feature Toggle")

## 适用性
在以下情况下使用功能切换模式
* 为不同的用户提供不同的功能。
* 逐步推出新功能。
* 在开发和生产环境之间切换。

## 鸣谢

* [Martin Fowler 29 October 2010 (2010-10-29).](http://martinfowler.com/bliki/FeatureToggle.html)
