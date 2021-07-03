---
layout: pattern
title: Partial Response
folder: partial-response
permalink: /patterns/partial-response/zh
categories: Behavioral
language: zh
tags:
 - Decoupling
---
## 意图
根据需要从服务器向客户端发送部分响应。客户将指定字段
它需要服务器，而不是为资源提供所有详细信息。

## 类图
![alt text](./etc/partial-response.urm.png "partial-response")

## 适用性
在以下情况下使用部分响应模式

* 客户端只需要来自资源的数据子集。
* 避免通过网络传输过多的数据

## 鸣谢

* [Common Design Patterns](https://cloud.google.com/apis/design/design_patterns)
