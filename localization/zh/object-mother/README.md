---
layout: pattern
title: Object Mother
folder: object-mother
permalink: /patterns/object-mother/zh
categories: Creational
language: zh
tags:
 - Instantiation
---
## 对象母亲模式
使用分离的构建器和工厂接口定义不可变内容的工厂。

## 类图
![alt text](./etc/object-mother.png "Object Mother")

## 适用性
在以下情况下使用对象母亲模式

* 您希望在多次测试中保持一致的对象
* 你想减少在测试中创建对象的代码
* 每个测试都应该使用新数据运行

## 鸣谢

* [Answer by David Brown](http://stackoverflow.com/questions/923319/what-is-an-objectmother) to the stackoverflow question: [What is an ObjectMother?](http://stackoverflow.com/questions/923319/what-is-an-objectmother)
* [c2wiki - Object Mother](http://c2.com/cgi/wiki?ObjectMother)
* [Nat Pryce - Test Data Builders: an alternative to the Object Mother pattern](http://www.natpryce.com/articles/000714.html)
