---
layout: pattern
title: Thread Local Storage
folder: tls
permalink: /patterns/tls/zh
categories: Idiom
language: zh
tags:
 - Performance
---
## 意图
保护一个线程的全局变量不被其他线程破坏。如果您在 Callable 对象或 Runnable 对象中使用非只读的类变量或静态变量，则需要这样做。

## 类图
![alt text](./etc/tls.png "Thread Local Storage")

## 适用性
在以下任何一种情况下使用线程本地存储

* 当您在 Callable / Runnable 对象中使用非只读的类变量，并且在多个并行运行的线程中使用相同的 Callable 实例时。
* 当您在 Callable / Runnable 对象中使用非只读静态变量时，Callable / Runnable 的多个实例可能会在并行线程中运行。