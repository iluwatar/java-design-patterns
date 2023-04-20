---
title: Singleton
category: Creational
language: en
tag:
 - Gang of Four
---

## Intent

Ensure a class only has one instance, and provide a global point of access to it.

确保一个类只有一个实例，并提供对它的全局访问点。

## Explanation

Real-world example

> There can only be one ivory tower where the wizards study their magic. The same enchanted ivory 
> tower is always used by the wizards. The ivory tower here is a singleton.
> 
> 只有一座象牙塔是巫师们学习魔法的地方。巫师们总是使用同一个魔法象牙塔。这里的象牙塔是一个单例。

In plain words

> Ensures that only one object of a particular class is ever created.
> 
> 确保只创建特定类的一个对象。

Wikipedia says

> In software engineering, the singleton pattern is a software design pattern that restricts the 
> instantiation of a class to one object. This is useful when exactly one object is needed to 
> coordinate actions across the system.
> 
> 在软件工程中，单例模式是一种将类的实例化限制为一个对象的软件设计模式。当只需要一个对象来协调整个系统的操作时，这很有用。

**Programmatic Example**

Joshua Bloch, Effective Java 2nd Edition p.18

> A single-element enum type is the best way to implement a singleton
> 
> 单元素枚举类型是实现单例的最佳方式

```java
public enum EnumIvoryTower {
  INSTANCE
}
```

Then in order to use:

```java
    var enumIvoryTower1 = EnumIvoryTower.INSTANCE;
    var enumIvoryTower2 = EnumIvoryTower.INSTANCE;
    LOGGER.info("enumIvoryTower1={}", enumIvoryTower1);
    LOGGER.info("enumIvoryTower2={}", enumIvoryTower2);
```

The console output

```
enumIvoryTower1=com.iluwatar.singleton.EnumIvoryTower@1221555852
enumIvoryTower2=com.iluwatar.singleton.EnumIvoryTower@1221555852
```

## Class diagram

![alt text](./etc/singleton.urm.png "Singleton pattern class diagram")

## Applicability
适用性

Use the Singleton pattern when

* There must be exactly one instance of a class, and it must be accessible to clients from a well-known access point
* 一个类必须只有一个实例，并且客户端必须可以从一个众所周知的访问点访问它
* When the sole instance should be extensible by subclassing, and clients should be able to use an extended instance without modifying their code
* 当这个唯一的实例需要通过子类扩展，而且要求客户不需要修改代码就可以使用这个扩展的的实例时。

Some typical use cases for the Singleton

Singleton的一些典型用例

* The logging class
* Managing a connection to a database
* File manager

## Known uses
已知使用

* [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)
* [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
* [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)


## Consequences
后果

* Violates Single Responsibility Principle (SRP) by controlling their creation and lifecycle.
* 通过控制它们的创建和生命周期违反单一责任原则(SRP)。
* Encourages using a globally shared instance which prevents an object and resources used by this object from being deallocated.     
* 鼓励使用全局共享实例，以防止该对象使用的对象和资源被释放。
* Creates tightly coupled code. The clients of the Singleton become difficult to test.
* 创建紧密耦合的代码。Singleton的客户端变得难以测试。
* Makes it almost impossible to subclass a Singleton.
* 使它几乎不可能子类化一个Singleton。

## Credits
鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
