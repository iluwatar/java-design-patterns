---
layout: pattern
title: Flux
folder: flux
permalink: /patterns/flux/zh
categories: Structural
language: zh
tags:
- Decoupling
---

## 目的
Flux 避开 MVC，支持单向数据流。当一个
用户与视图交互，视图通过中心传播动作
调度员，到保存应用程序数据和业务的各种商店
逻辑，更新所有受影响的视图。

## 类图
![alt text](./etc/flux.png "Flux")

## 适用性
使用 Flux 模式时

* 您希望专注于为应用程序数据创建明确且易于理解的更新路径，这使得在开发过程中跟踪更改更简单，并使错误更易于跟踪和修复。

## 鸣谢

* [Flux - Application architecture for building user interfaces](http://facebook.github.io/flux/)
