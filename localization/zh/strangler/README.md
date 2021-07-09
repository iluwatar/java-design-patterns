---
layout: pattern
title: Strangler
folder: strangler
permalink: /patterns/strangler/zh
categories: Structural
language: zh
tags:
 - Extensibility
 - Cloud distributed
---

## 目的
通过逐渐替换特定功能部分来逐步迁移遗留系统
新的应用程序和服务。随着旧系统的功能被替换，新的
系统最终覆盖了旧系统的所有功能，并且可能有自己的新功能，那么
扼杀旧系统并允许您将其退役。

## 类图
![alt text](./etc/strangler.png "Strangler")

## 适用性
这种扼杀模式是一种安全的方式，可以将一件事逐步淘汰，以获得更好、更便宜或
更具扩展性。特别是当您想使用新技术更新遗留系统时
同时不断开发新功能。注意这个模式确实需要额外的努力，
所以通常在系统不是那么简单的时候使用它。
## 鸣谢

* [Strangler pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/strangler)
* [Legacy Application Strangulation : Case Studies](https://paulhammant.com/2013/07/14/legacy-application-strangulation-case-studies/)
