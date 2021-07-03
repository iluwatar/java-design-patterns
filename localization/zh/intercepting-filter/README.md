---
layout: pattern
title: Intercepting Filter
folder: intercepting-filter
permalink: /patterns/intercepting-filter/zh
categories: Behavioral
language: zh
tags:
 - Decoupling
---

## 目的
提供可插拔过滤器以进行必要的预处理和
对从客户端到目标的请求进行后处理

## 类图 
![alt text](./etc/intercepting-filter.png "Intercepting Filter")

## 适用性
在以下情况下使用拦截过滤器模式

* 系统使用预处理或后处理请求
* 系统应该对请求进行身份验证/授权/记录或跟踪，然后将请求传递给相应的处理程序
* 您需要一种模块化方法来配置预处理和后处理方案

## 教程

* [Introduction to Intercepting Filter Pattern in Java](https://www.baeldung.com/intercepting-filter-pattern-in-java)

## 真实案例

* [javax.servlet.FilterChain](https://tomcat.apache.org/tomcat-8.0-doc/servletapi/javax/servlet/FilterChain.html) and [javax.servlet.Filter](https://tomcat.apache.org/tomcat-8.0-doc/servletapi/javax/servlet/Filter.html)
* [Struts 2 - Interceptors](https://struts.apache.org/core-developers/interceptors.html)

## 鸣谢

* [TutorialsPoint - Intercepting Filter](http://www.tutorialspoint.com/design_pattern/intercepting_filter_pattern.htm)
