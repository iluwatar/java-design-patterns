---
title: Proxy
shortTitle: Proxy
category: Structural
language: zh
tag:
 - Gang Of Four
 - Decoupling
---

## 又被称为

替代（代孕）模式

## 目的

为另一个对象提供代理或占位符以控制对其的访问。

## 解释

真实世界例子

> 想象有一个塔，当地的巫师去那里学习他们的法术。象牙塔只能够通过代理来进入以此来保证只有首先3个巫师才能进入。这里的代理就代表的塔的功能并添加访问控制。

通俗的说

> 使用代理模式，一个类代表另一个类的功能。

维基百科说

> 在最一般的形式上，代理是一个类，它充当与其他对象的接口。代理是客户端调用的包装器或代理对象，以访问后台的实际服务对象。代理本身可以简单地转发到真实对象，也可以提供其他逻辑。在代理中，可以提供额外的功能，例如在对实对象的操作占用大量资源时进行缓存，或者在对实对象的操作被调用之前检查前提条件。

**程序示例**

使用上面的巫师塔为例。首先我们有**巫师塔**接口和**象牙塔**类 。

```java
public interface WizardTower {

  void enter(Wizard wizard);
}

public class IvoryTower implements WizardTower {

  private static final Logger LOGGER = LoggerFactory.getLogger(IvoryTower.class);

  public void enter(Wizard wizard) {
    LOGGER.info("{} enters the tower.", wizard);
  }

}
```

然后有个简单的巫师类。

```java
public class Wizard {

  private final String name;

  public Wizard(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
```

然后我们有巫师塔代理类为巫师塔添加访问控制。

```java
public class WizardTowerProxy implements WizardTower {

  private static final Logger LOGGER = LoggerFactory.getLogger(WizardTowerProxy.class);

  private static final int NUM_WIZARDS_ALLOWED = 3;

  private int numWizards;

  private final WizardTower tower;

  public WizardTowerProxy(WizardTower tower) {
    this.tower = tower;
  }

  @Override
  public void enter(Wizard wizard) {
    if (numWizards < NUM_WIZARDS_ALLOWED) {
      tower.enter(wizard);
      numWizards++;
    } else {
      LOGGER.info("{} is not allowed to enter!", wizard);
    }
  }
}
```

然后这是进入塔的场景。

```java
var proxy = new WizardTowerProxy(new IvoryTower());
proxy.enter(new Wizard("Red wizard"));
proxy.enter(new Wizard("White wizard"));
proxy.enter(new Wizard("Black wizard"));
proxy.enter(new Wizard("Green wizard"));
proxy.enter(new Wizard("Brown wizard"));
```

程序输出：

```
Red wizard enters the tower.
White wizard enters the tower.
Black wizard enters the tower.
Green wizard is not allowed to enter!
Brown wizard is not allowed to enter!
```

## 类图

![alt text](./etc/proxy.urm.png "Proxy pattern class diagram")

## 适用性

代理适用于需要比简单指针更广泛或更复杂的对象引用的情况。这是代理模式适用的几种常见情况。

* 远程代理为不同地址空间中的对象提供了本地代表。
* 虚拟代理根据需要创建昂贵的对象。
* 保护代理控制对原始对象的访问。当对象有不同的接入权限时保护代理很有用。

## 典型用例

* 对象的访问控制
* 懒加载
* 实现日志记录
* 简化网络连接
* 对象的访问计数

## 教程

* [Controlling Access With Proxy Pattern](http://java-design-patterns.com/blog/controlling-access-with-proxy-pattern/)

## 已知使用

* [java.lang.reflect.Proxy](http://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html)
* [Apache Commons Proxy](https://commons.apache.org/proper/commons-proxy/)
* Mocking frameworks [Mockito](https://site.mockito.org/), 
[Powermock](https://powermock.github.io/), [EasyMock](https://easymock.org/)

## 相关设计模式

* [Ambassador](https://java-design-patterns.com/patterns/ambassador/)

## 鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
