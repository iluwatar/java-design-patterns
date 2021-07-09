---
layout: pattern
title: Spatial Partition
folder: spatial-partition
permalink: /patterns/spatial-partition/zh
categories: Behavioral
language: zh
tags:
 - Performance
 - Game programming
---

## 目的

如书中所述[游戏编程模式](http://gameprogrammingpatterns.com/spatial-partition.html)
作者 Bob Nystrom，空间分区模式有助于通过将对象存储在一个
按位置组织的数据结构。

## 解释

比如说，你正在建立一个有数百甚至数千名玩家的战争游戏，他们正在发生冲突
战场上。每个玩家的位置每帧都会更新。简单的处理方法
场上发生的所有互动都是为了检查每个球员的位置
球员位置：
```java
public void handleMeLee(Unit units[], int numUnits) {
  for (var a = 0; a < numUnits - 1; a++)
  {
    for (var b = a + 1; b < numUnits; b++)
    {
      if (units[a].position() == units[b].position())
      {
        handleAttack(units[a], units[b]);
      }
    }
  }
}
```

这将包括玩家之间的许多不必要的检查，这些玩家之间距离太远而无法进行任何检查。
互相影响。嵌套循环给这个操作一个 O(n^2) 的复杂度，它必须是
由于场上的许多物体可能每帧都在移动，因此每帧都执行。想法
空间分区设计模式的背后是使用数据快速定位对象
按位置组织的结构，因此在执行上述操作时，
不需要根据所有其他对象的位置检查每个对象的位置。数据结构
可用于存储移动和静态对象，但为了跟踪移动对象，
每次移动时，他们的位置都必须重新设置。这意味着必须创建一个新的
每次对象移动时数据结构的实例，这会消耗额外的内存。这
用于此设计模式的常见数据结构是：
* Grid
* Quad tree
* K-d tree
* BSP
* Boundary volume hierarchy

在我们的实现中，我们使用四叉树数据结构，这将降低时间复杂度
在从 O(n^2) 到 O(nlogn) 的某个范围内找到对象，减少计算
在大量对象的情况下非常需要。

##类图
![alt text](./etc/spatial-partition.urm.png "Spatial Partition pattern class diagram")

## 适用性
可以使用这种模式：

* 当您需要跟踪大量对象的位置时，这些位置每帧都会更新。
* 当可以接受内存换取速度时，因为创建和更新数据结构会消耗额外的内存。

## 鸣谢

* [Game Programming Patterns/Spatial Partition](http://gameprogrammingpatterns.com/spatial-partition.html) by Bob Nystrom
* [Quadtree tutorial](https://www.youtube.com/watch?v=OJxEcs0w_kE) by Daniel Schiffman
