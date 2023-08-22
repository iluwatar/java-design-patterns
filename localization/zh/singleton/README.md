---
title: Singleton
category: Creational
language: zh
tag:
 - Gang of Four
---

## 目的

确保一个类只有一个实例，并为其提供一个全局访问点。

## 解释

情境示例

> 巫师们之在一个象牙塔中学习他们的魔法，并且始终使用同一座附魔的象牙塔。
> 
> 这里的象牙塔是一个单例对象。

通俗来说

> 对于一个特定的类，确保只会创建一个对象。

维基百科说

> 在软件工程中，单例模式是一种软件设计模式，它将类的实例化限制为一个对象。当系统中只需要一个对象来协调各种操作时，这种模式非常有用。

**程序示例**

详见 Joshua Bloch, Effective Java 2nd Edition p.18。

> 一个只有一个元素的枚举类型是实现单例模式的最佳方式。

```java
public enum EnumIvoryTower {
  INSTANCE
}
```

使用：

```java
    var enumIvoryTower1 = EnumIvoryTower.INSTANCE;
    var enumIvoryTower2 = EnumIvoryTower.INSTANCE;
    LOGGER.info("enumIvoryTower1={}", enumIvoryTower1);
    LOGGER.info("enumIvoryTower2={}", enumIvoryTower2);
```

控制台输出：

```
enumIvoryTower1=com.iluwatar.singleton.EnumIvoryTower@1221555852
enumIvoryTower2=com.iluwatar.singleton.EnumIvoryTower@1221555852
```

## 类图

![alt text](./etc/singleton.urm.png "Singleton pattern class diagram")

## 适用性

当满足以下情况时，使用单例模式：

* 确保一个类只有一个实例，并且客户端能够通过一个众所周知的访问点访问该实例。
* 唯一的实例能够被子类扩展, 同时客户端不需要修改代码就能使用扩展后的实例。

一些典型的单例模式用例包括：

* logging类
* 管理与数据库的链接
* 文件管理器（File manager）

## 已知使用

* [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)
* [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
* [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)


## 影响

* 通过控制实例的创建和生命周期，违反了单一职责原则（SRP）。
* 鼓励使用全局共享实例，组织了对象及其使用的资源被释放。     
* 代码变得耦合，给客户端的测试带来难度。
* 单例模式的设计可能会使得子类化（继承）单例变得几乎不可能

## 鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
