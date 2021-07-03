---
layout: pattern
title: Step Builder
folder: step-builder
permalink: /patterns/step-builder/zh
categories: Creational
language: zh
tags:
 - Instantiation
---

## 目的
Builder 模式的扩展，可完全引导用户创建对象，而不会出现混淆。
由于他只会看到可用的下一步方法，在构建对象的正确时间之前没有构建方法，因此用户体验将得到更大改善。
## 类图
![alt text](./etc/step-builder.png "Step Builder")

## 适用性
当创建复杂对象的算法应该独立于组成对象的部分以及它们如何组装时，使用 Step Builder 模式
构建过程必须允许在构建顺序的过程中对构建的对象进行不同的表示很重要。
## 鸣谢

* [Marco Castigliego - Step Builder](http://rdafbn.blogspot.co.uk/2012/07/step-builder-pattern_28.html)
