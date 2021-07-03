---
layout: pattern
title: Extension objects
folder: extension-objects
permalink: /patterns/extension-objects/zh
categories: Behavioral
language: zh
tags:
- Extensibility
---

## 目的
预计将来需要扩展对象的接口。额外的
接口由扩展对象定义。
## 类图
![Extension_objects](./etc/extension_obj.png "Extension objects")

## 适用性
* 您需要支持向现有类添加新的或不可预见的接口，并且您不想影响不需要这个新接口的客户端。扩展对象允许您通过在单独的类中定义相关操作来将它们保持在一起
* 代表关键抽象的类对不同的客户端扮演不同的角色。班级可以扮演的角色数量应该是开放式的。需要保留关键抽象本身。例如，即使不同的子系统对它的看法不同，客户对象仍然是客户对象。
* 一个类应该可以用新的行为进行扩展，而不需要从它继承子类。

## 真实案例

* [OpenDoc](https://en.wikipedia.org/wiki/OpenDoc)
* [Object Linking and Embedding](https://en.wikipedia.org/wiki/Object_Linking_and_Embedding)
