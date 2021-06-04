---
layout: pattern
title: Observer
folder: observer
permalink: /patterns/observer/zh
categories: Behavioral
language: zh
tags:
 - Gang Of Four
 - Reactive
---

## Also known as
## 又被称为

家属，发布订阅模式

## 目的

定义一种一对多的对象依赖关系这样当一个对象改变状态时，所有依赖它的对象都将自动通知或更新。

## 解释

真实世界例子

> 在遥远的土地上生活着霍比特人和兽人的种族。他们都是户外生活的人所以他们密切关注天气的变化。可以说他们不断地关注着天气。

通俗的说

> 注册成为一个观察者以接收对象状态的改变。

维基百科说

> 观察者模式是这样的一种软件设计模式：它有一个被称为主题的对象，维护着一个所有依赖于它的依赖者清单，也就是观察者清单，当主题的状态发生改变时，主题通常会调用观察者的方法来自动通知观察者们。

**编程示例**

让我们先来介绍天气观察者的接口以及我们的种族，兽人和霍比特人。

```java
public interface WeatherObserver {

  void update(WeatherType currentWeather);
}

@Slf4j
public class Orcs implements WeatherObserver {

  @Override
  public void update(WeatherType currentWeather) {
    LOGGER.info("The orcs are facing " + currentWeather.getDescription() + " weather now");
  }
}

@Slf4j
public class Hobbits implements WeatherObserver {

  @Override
  public void update(WeatherType currentWeather) {
    switch (currentWeather) {
      LOGGER.info("The hobbits are facing " + currentWeather.getDescription() + " weather now");
  }
}
```

然后这里是不断变化的天气。

```java
@Slf4j
public class Weather {

  private WeatherType currentWeather;
  private final List<WeatherObserver> observers;

  public Weather() {
    observers = new ArrayList<>();
    currentWeather = WeatherType.SUNNY;
  }

  public void addObserver(WeatherObserver obs) {
    observers.add(obs);
  }

  public void removeObserver(WeatherObserver obs) {
    observers.remove(obs);
  }

  /**
   * Makes time pass for weather.
   */
  public void timePasses() {
    var enumValues = WeatherType.values();
    currentWeather = enumValues[(currentWeather.ordinal() + 1) % enumValues.length];
    LOGGER.info("The weather changed to {}.", currentWeather);
    notifyObservers();
  }

  private void notifyObservers() {
    for (var obs : observers) {
      obs.update(currentWeather);
    }
  }
}
```

这是完整的示例。

```java
    var weather = new Weather();
    weather.addObserver(new Orcs());
    weather.addObserver(new Hobbits());

    weather.timePasses();
    // The weather changed to rainy.
    // The orcs are facing rainy weather now
    // The hobbits are facing rainy weather now
    weather.timePasses();
    // The weather changed to windy.
    // The orcs are facing windy weather now
    // The hobbits are facing windy weather now
    weather.timePasses();
    // The weather changed to cold.
    // The orcs are facing cold weather now
    // The hobbits are facing cold weather now
    weather.timePasses();
    // The weather changed to sunny.
    // The orcs are facing sunny weather now
    // The hobbits are facing sunny weather now
```

## Class diagram
![alt text](../../observer/etc/observer.png "Observer")

## 应用
在下面任何一种情况下都可以使用观察者模式

* 当抽象具有两个方面时，一个方面依赖于另一个方面。将这些方面封装在单独的对象中，可以使你分别进行更改和重用
* 当一个对象的改变的同时需要改变其他对象，同时你又不知道有多少对象需要改变时
* 当一个对象可以通知其他对象而无需假设这些对象是谁时。换句话说，你不想让这些对象紧耦合。

## 典型用例

* 一个对象的改变导致其他对象的改变

## Java中的例子

* [java.util.Observer](http://docs.oracle.com/javase/8/docs/api/java/util/Observer.html)
* [java.util.EventListener](http://docs.oracle.com/javase/8/docs/api/java/util/EventListener.html)
* [javax.servlet.http.HttpSessionBindingListener](http://docs.oracle.com/javaee/7/api/javax/servlet/http/HttpSessionBindingListener.html)
* [RxJava](https://github.com/ReactiveX/RxJava)

## 鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Java Generics and Collections](https://www.amazon.com/gp/product/0596527756/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596527756&linkCode=as2&tag=javadesignpat-20&linkId=246e5e2c26fe1c3ada6a70b15afcb195)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
