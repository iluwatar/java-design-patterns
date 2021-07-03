---  
layout: pattern  
title: Update Method
folder: update-method  
permalink: /patterns/update-method/zh
categories: Behavioral
language: zh
tags:  
 - Game programming
---  

## 目的
更新方法模式通过告诉每个对象一次处理一帧行为来模拟一组独立对象。

## 解释
游戏世界维护着一组对象。每个对象都实现了一个更新方法，该方法模拟对象行为的一帧。每一帧，游戏都会更新集合中的每个对象。

要了解有关游戏循环如何运行以及何时调用更新方法的更多信息，请参阅游戏循环模式。

## 类图
![alt text](./etc/update-method.urm.png "Update Method pattern class diagram")

## 适用性  
如果游戏循环模式是切片面包以来最好的东西，那么更新方法模式就是它的黄油。大量以玩家与之交互的实时实体为特色的游戏以某种形式或其他形式使用这种模式。如果游戏中有星际战士、龙、火星人、鬼魂或运动员，它很有可能使用这种模式。

然而，如果游戏更抽象并且移动的棋子不像活着的演员而更像棋盘上的棋子，这种模式通常是不合适的。在象棋这样的游戏中，您不需要同时模拟所有棋子，并且您可能不需要告诉 pawn 每一帧更新自己。

更新方法在以下情况下运行良好：

- 您的游戏有许多需要同时运行的对象或系统。
- 每个对象的行为大多独立于其他对象。
- 对象需要随着时间的推移进行模拟。

## 鸣谢  
  
* [Game Programming Patterns - Update Method](http://gameprogrammingpatterns.com/update-method.html)
