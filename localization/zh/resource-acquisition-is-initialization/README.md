---
layout: pattern
title: Resource Acquisition Is Initialization
folder: resource-acquisition-is-initialization
permalink: /patterns/resource-acquisition-is-initialization/zh
categories: Idiom
language: zh
tags:
 - Data access
---

## 意图
Resource Acquisition Is Initialization 模式可用于实现异常安全的资源管理。

## 类图
![alt text](./etc/resource-acquisition-is-initialization.png "Resource Acquisition Is Initialization")

## 适用性
在以下情况下使用资源获取是初始化模式

* 您拥有在任何情况下都必须关闭的资源