---
layout: pattern
title: Singleton
folder: singleton
permalink: /patterns/singleton/zh
categories: Creational
language: zh
tags:
 - Gang of Four
---

## 目的
确保一个类只有一个实例，并提供对它的全局访问点。

＃＃ 解释

真实世界的例子

> 只有一座象牙塔可供巫师学习魔法。相同的魔法象牙
> 塔总是被巫师使用。这里的象牙塔是单身人士。

简单来说

> 确保只创建特定类的一个对象。

维基百科说

> 在软件工程中，单例模式是一种软件设计模式，它限制了
> 将一个类实例化为一个对象。当只需要一个对象时，这很有用
> 协调整个系统的行动。

**程序示例**

Joshua Bloch, Effective Java 第二版 p.18

> 单元素枚举类型是实现单例的最佳方式

```java
public enum EnumIvoryTower {
  INSTANCE
}
```

使用

```java
    var enumIvoryTower1 = EnumIvoryTower.INSTANCE;
    var enumIvoryTower2 = EnumIvoryTower.INSTANCE;
    LOGGER.info("enumIvoryTower1={}", enumIvoryTower1);
    LOGGER.info("enumIvoryTower2={}", enumIvoryTower2);
```

控制台输出
```
enumIvoryTower1=com.iluwatar.singleton.EnumIvoryTower@1221555852
enumIvoryTower2=com.iluwatar.singleton.EnumIvoryTower@1221555852
```

## 类图

![alt text](./etc/singleton.urm.png "Singleton pattern class diagram")

## 适用性
使用单例模式时

* 必须只有一个类的实例，并且客户端必须可以从众所周知的访问点访问它
* 当唯一的实例应该可以通过子类化扩展，并且客户端应该能够使用扩展的实例而无需修改他们的代码

Singleton 的一些典型用例

* 日志类
* 管理与数据库的连接
* 文件管理器

## 已知用途

* [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)
* [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
* [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)

## 结果

* 通过控制它们的创建和生命周期，违反了单一职责原则 (SRP)。
* 鼓励使用全局共享实例，以防止释放该对象使用的对象和资源。
* 创建紧密耦合的代码。 Singleton 的客户端变得难以测试。
* 几乎不可能对单例进行子类化。

##学分

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
