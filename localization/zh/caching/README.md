---
title: Caching
category: Behavioral
language: zh
tag:
  - Performance
  - Cloud distributed
---

## 目的
为了避免昂贵的资源重新获取，方法是在资源使用后不立即释放资源。资源保留其身份，保留在某些快速访问的存储中，并被重新使用，以避免再次获取它们。

## 类图
![alt text](./etc/caching.png "Caching")

## 适用性
在以下情况下使用缓存模式

* 重复获取，初始化和释放同一资源会导致不必要的性能开销。

## 鸣谢

* [Write-through, write-around, write-back: Cache explained](http://www.computerweekly.com/feature/Write-through-write-around-write-back-Cache-explained)
* [Read-Through, Write-Through, Write-Behind, and Refresh-Ahead Caching](https://docs.oracle.com/cd/E15357_01/coh.360/e15723/cache_rtwtwbra.htm#COHDG5177)
* [Cache-Aside pattern](https://docs.microsoft.com/en-us/azure/architecture/patterns/cache-aside)
