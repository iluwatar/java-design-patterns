---
layout: pattern
title: Page Object
folder: page-object
permalink: /patterns/page-object/zh
categories: Structural
language: zh
tags:
- Decoupling
---

## 目的
Page Object 封装了 UI，隐藏了应用程序（通常是 Web 应用程序）的底层 UI 小部件，并提供了特定于应用程序的 API 以允许操作测试所需的 UI 组件。这样做，它允许测试类本身专注于测试逻辑。

## 类图
![alt text](./etc/page-object.png "Page Object")


## 适用性
在以下情况下使用页面对象模式

* 您正在为 Web 应用程序编写自动化测试，并且希望将测试所需的 UI 操作与实际测试逻辑分开。
* 使您的测试不那么脆弱，更具可读性和健壮性

## 鸣谢

* [Martin Fowler - PageObject](http://martinfowler.com/bliki/PageObject.html)
* [Selenium - Page Objects](https://github.com/SeleniumHQ/selenium/wiki/PageObjects)
