---
layout: pattern
title: Mute Idiom
folder: mute-idiom
permalink: /patterns/mute-idiom/zh
categories: Idiom
language: zh
tags: 
 - Decoupling
---

## 目的
提供一个模板来抑制任何已声明但不能发生或只应记录的异常；
在执行一些业务逻辑时。该模板无需编写重复的“try-catch”块。

## 类图
![alt text](./etc/mute-idiom.png "Mute Idiom")

## 适用性
使用这个习语时

* API 声明了一些异常，但永远不会抛出该异常，例如。 ByteArrayOutputStream 批量写入方法。
* 您需要仅通过记录来抑制某些异常，例如关闭资源。

## 鸣谢
* [JOOQ: Mute Design Pattern](http://blog.jooq.org/2016/02/18/the-mute-design-pattern/)
