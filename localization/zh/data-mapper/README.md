---
title: Data Mapper
category: Architectural
language: zh
tag:
 - Decoupling
---

## 含义
一个用于在持久化对象和数据库之间传输数据的映射器，同时保持它们之间和映射器本身的独立性。

## 类图
![alt text](./etc/data-mapper.png "Data Mapper")

## 适用场景
数据映射器适用于以下场景：

* 当你想把数据对象从数据库访问层解耦时时
* 当你想编写多个数据查询/持久化实现时

## 引用

* [Data Mapper](http://richard.jp.leguen.ca/tutoring/soen343-f2010/tutorials/implementing-data-mapper/)