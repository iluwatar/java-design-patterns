---
layout: pattern
title: Factory Kit
folder: factory-kit
permalink: /patterns/factory-kit/
categories: Creational
tags:
 - Extensibility
---

## 含义
使用分离的构建器和工厂接口来定义一个不可变内容的工厂。

## 类图
![alt text](../../factory-kit/etc/factory-kit.png "Factory Kit")

## 适用场景
工厂套件模式适用于与以下场景：

* 一个类无法预知它需要创建的对象的类别
- 你只是想要一个新的自定义构建器(builder)的实例，而非全局的构建器
- 你明确地想要定义对象的类型，而且工厂可以创建这些对象
- 你想要分离构建器(builder)和创建器(creator)接口

## 引用

* [Design Pattern Reloaded by Remi Forax: ](https://www.youtube.com/watch?v=-k2X7guaArU)