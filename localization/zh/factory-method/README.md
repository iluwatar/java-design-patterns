---
layout: pattern
title: Factory Method
folder: factory-method
permalink: /patterns/factory-method/zh
categories: Creational
language: zh
tags:
 - Extensibility
 - Gang Of Four
---

## Also known as
# 或称

虚拟构造器

## 目的
为创建一个对象定义一个接口，但是让子类决定实例化哪个类。工厂方法允许类将实例化延迟到子类。

## 解释
真实世界例子

> 铁匠生产武器。精灵需要精灵武器，而兽人需要兽人武器。根据客户来召唤正确类型的铁匠。

通俗的说

> 它为类提供了一种把实例化的逻辑委托给子类的方式。

维基百科上说

> 在基于类的编程中，工厂方法模式是一种创建型设计模式用来解决创建对象的问题，而不需要指定将要创建对象的确切类。这是通过调用工厂方法创建对象来完成的，而不是通过调用构造器。该工厂方法在接口中指定并由子类实现，或者在基类实现并可以选择由子类重写。

 **程序示例**

以上面的铁匠为例，首先我们有铁匠的接口和一些它的实现。

```java
public interface Blacksmith {
  Weapon manufactureWeapon(WeaponType weaponType);
}

public class ElfBlacksmith implements Blacksmith {
  public Weapon manufactureWeapon(WeaponType weaponType) {
    return ELFARSENAL.get(weaponType);
  }
}

public class OrcBlacksmith implements Blacksmith {
  public Weapon manufactureWeapon(WeaponType weaponType) {
    return ORCARSENAL.get(weaponType);
  }
}
```

现在随着客户的到来，会召唤出正确类型的铁匠并制造出要求的武器。

```java
var blacksmith = new ElfBlacksmith();
blacksmith.manufactureWeapon(WeaponType.SPEAR);
blacksmith.manufactureWeapon(WeaponType.AXE);
// Elvish weapons are created
```

## 类图
![alt text](../../factory-method/etc/factory-method.urm.png "Factory Method pattern class diagram")

## 适用性
使用工厂方法模式当

* 一个类无法预料它所要必须创建的对象的类
* 一个类想要它的子类来指定它要创建的对象
* 类将责任委派给几个帮助子类中的一个，而你想定位了解是具体之中的哪一个

## Java中的例子

* [java.util.Calendar](http://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html#getInstance--)
* [java.util.ResourceBundle](http://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html#getBundle-java.lang.String-)
* [java.text.NumberFormat](http://docs.oracle.com/javase/8/docs/api/java/text/NumberFormat.html#getInstance--)
* [java.nio.charset.Charset](http://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html#forName-java.lang.String-)
* [java.net.URLStreamHandlerFactory](http://docs.oracle.com/javase/8/docs/api/java/net/URLStreamHandlerFactory.html#createURLStreamHandler-java.lang.String-)
* [java.util.EnumSet](https://docs.oracle.com/javase/8/docs/api/java/util/EnumSet.html#of-E-)
* [javax.xml.bind.JAXBContext](https://docs.oracle.com/javase/8/docs/api/javax/xml/bind/JAXBContext.html#createMarshaller--)

## 鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
