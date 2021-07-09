---
layout: pattern
title: Service Locator
folder: service-locator
permalink: /patterns/service-locator/zh
categories: Architectural
language: zh
tags:
 - Game programming
 - Performance
---

## 目的
类封装获取服务所涉及的流程强大的抽象层。

## 类图
![alt text](./etc/service-locator.png "Service Locator")

## 适用性
服务定位器模式适用于任何需要的时候
使用JNDI来定位/获取各种服务，通常JNDI是冗余的
和昂贵的查找。服务定位器模式解决了这个昂贵的问题
利用缓存技术进行查找。第一次
特定的服务被请求，服务定位器在JNDI中查找，获取
相关的服务，然后最后缓存这个服务对象。现在,进一步
通过服务定位器查找相同的服务是在其缓存中完成的
极大地提高了应用程序的性能。

## 经典案例

* 网络点击率昂贵且耗时
* 服务的查找非常频繁
* 大量服务正在被使用

## 结论

* 通过向模式使用者提供访问，违反了接口隔离原则(ISP)他们可能不需要的一些服务。
* 创建隐藏的依赖，可以在运行时破坏客户端。

## 鸣谢

* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
