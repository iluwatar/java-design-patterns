---
title: Adapter
category: Structural
language: zh
tag:
 - Gang of Four
---

## 又被称为
包装器

## 目的
将一个接口转换成另一个客户所期望的接口。适配器让那些本来因为接口不兼容的类可以合作无间。

## 解释

现实世界例子

> 考虑有这么一种情况，在你的存储卡中有一些照片，你想将其传到你的电脑中。为了传送数据，你需要某种能够兼容你电脑接口的适配器以便你的储存卡能连上你的电脑。在这种情况下，读卡器就是一个适配器。
> 另一个例子就是注明的电源适配器；三脚插头不能插在两脚插座上，需要一个电源适配器来使其能够插在两脚插座上。
> 还有一个例子就是翻译官，他翻译一个人对另一个人说的话。

用直白的话来说

> 适配器模式让你可以把不兼容的对象包在适配器中，以让其兼容其他类。

维基百科中说

> 在软件工程中，适配器模式是一种可以让现有类的接口把其作为其他接口来使用的设计模式。它经常用来使现有的类和其他类能够工作并且不用修改其他类的源代码。

**编程样例(对象适配器)**

假如有一个船长他只会划船，但不会航行。

首先我们有接口`RowingBoat`和`FishingBoat`

```java
public interface RowingBoat {
  void row();
}

@Slf4j
public class FishingBoat {
  public void sail() {
    LOGGER.info("The fishing boat is sailing");
  }
}
```
船长希望有一个`RowingBoat`接口的实现，这样就可以移动

```java
public class Captain {

  private final RowingBoat rowingBoat;
  // default constructor and setter for rowingBoat
  public Captain(RowingBoat rowingBoat) {
    this.rowingBoat = rowingBoat;
  }

  public void row() {
    rowingBoat.row();
  }
}
```

现在海盗来了，我们的船长需要逃跑但是只有一个渔船可用。我们需要创建一个可以让船长使用其划船技能来操作渔船的适配器。

```java
@Slf4j
public class FishingBoatAdapter implements RowingBoat {

  private final FishingBoat boat;

  public FishingBoatAdapter() {
    boat = new FishingBoat();
  }

  @Override
  public void row() {
    boat.sail();
  }
}

```

现在 `船长` 可以使用`FishingBoat`接口来逃离海盗了。

```java
var captain = new Captain(new FishingBoatAdapter());
captain.row();
```

## 类图
![alt text](./etc/adapter.urm.png "Adapter class diagram")


## 应用
使用适配器模式当

* 你想使用一个已有类，但是它的接口不能和你需要的所匹配
* 你需要创建一个可重用类，该类与不相关或不可预见的类进行协作，即不一定具有兼容接口的类
* 你需要使用一些现有的子类，但是子类化他们每一个的子类来进行接口的适配是不现实的。一个对象适配器可以适配他们父类的接口。
* 大多数使用第三方类库的应用使用适配器作为一个在应用和第三方类库间的中间层来使应用和类库解耦。如果必须使用另一个库，则只需使用一个新库的适配器而无需改变应用程序的代码。

## 后果:
类和对象适配器有不同的权衡取舍。一个类适配器

*	适配被适配者到目标接口，需要保证只有一个具体的被适配者类。作为结果，当我们想适配一个类和它所有的子类时，类适配器将不会起作用。
*	可以让适配器重写一些被适配者的行为，因为适配器是被适配者的子类。
*	只引入了一个对象，并且不需要其他指针间接访问被适配者。

对象适配器	

*	一个适配器可以和许多被适配者工作，也就是被适配者自己和所有它的子类。适配器同时可以为所有被适配者添加功能。
*	覆盖被适配者的行为变得更难。需要子类化被适配者然后让适配器引用这个子类不是被适配者。


## 现实世界的案例

* [java.util.Arrays#asList()](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList%28T...%29)
* [java.util.Collections#list()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#list-java.util.Enumeration-)
* [java.util.Collections#enumeration()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-)
* [javax.xml.bind.annotation.adapters.XMLAdapter](http://docs.oracle.com/javase/8/docs/api/javax/xml/bind/annotation/adapters/XmlAdapter.html#marshal-BoundType-)


## 鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)

```

```