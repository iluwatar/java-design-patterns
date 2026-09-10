---
title: Producer Consumer
shortTitle: Producer Consumer
category: Concurrency
language: zh
tag:
 - Reactive
---

## 目的
生產者消費者設計模式是一種經典的併發模式，透過將工作與執行工作任務分開來減少生產者與消費者之間的耦合。

## 類圖
![alt text](./etc/producer-consumer.png "Producer Consumer")

## 適用性
在以下情況下使用生產者消費者

* 透過將工作分成生產和消費兩個工作程序來解耦系統
* 解決生產工作和消費工作需要不同時間的問題
