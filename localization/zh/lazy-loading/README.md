---
layout: pattern
title: Lazy Loading
folder: lazy-loading
permalink: /patterns/lazy-loading/zh
categories: Idiom
language: zh
tags:
 - Performance
---

## 目的
延迟加载是一种常用的延迟加载设计模式
对象的初始化，直到需要它为止。它可以
如果适当且有助于提高程序运行的效率
适当地使用。

## 类图
![alt text](./etc/lazy-loading.png "Lazy Loading")

## 适用性
在以下情况下使用延迟加载习语

* 急切加载代价高昂，或者可能根本不需要要加载的对象

##真实世界的例子

* JPA 注释@OneToOne、@OneToMany、@ManyToOne、@ManyToMany 和 fetch = FetchType.LAZY

## 鸣谢

* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=d9f7d37b032ca6e96253562d075fcc4a)
