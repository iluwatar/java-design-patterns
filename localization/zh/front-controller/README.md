---
layout: pattern
title: Front Controller
folder: front-controller
permalink: /patterns/front-controller/zh
categories: Structural
language: zh
tags:
- Decoupling
---

## 目的


## 类图
![alt text](./etc/front-controller.png "Front Controller")

## 适用性
使用前端控制器模式时
* 您想在一个地方封装常见的请求处理功能
* 您想实现动态请求处理，即在不修改代码的情况下更改路由
* 使 web 服务器配置可移植，您只需要注册处理程序 web 服务器特定的方式

## 真实世界的例子

* [Apache Struts](https://struts.apache.org/)

## 鸣谢

* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Presentation Tier Patterns](http://www.javagyan.com/tutorials/corej2eepatterns/presentation-tier-patterns)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=d9f7d37b032ca6e96253562d075fcc4a)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
