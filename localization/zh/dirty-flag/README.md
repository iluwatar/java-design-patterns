---
title: Dirty Flag
category: Behavioral
language: zh
tag:
 - Game programming
 - Performance
---

## 或称
* 是否脏 模式

## 目的
避免昂贵资源的重新获取。资源保留其身份，保留在某些快速访问的存储中，并被重新使用以避免再次获取它们。

## 类图
![alt text](./etc/dirty-flag.png "Dirty Flag")

## 适用性
在以下情况下使用脏标志模式

* 重复获取，初始化，释放相同资源所导致不必要的性能开销

## 鸣谢

* [Design Patterns: Dirty Flag](https://www.takeupcode.com/podcast/89-design-patterns-dirty-flag/)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
