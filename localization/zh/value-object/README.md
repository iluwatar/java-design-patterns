---
title: Value Object
category: Creational
language: zn
tag:
 - Instantiation
---

## 又被称为
值对象

## 目的

提供的对象应遵循值语义而不是引用语义。这意味着两个值对象的相等性不是基于它们的身份。只要两个值对象的值相同，即使它们不是同一个对象，它们也被认为是相等的。

## 解释

现实世界例子

> 在一个角色扮演游戏中，有一个用于英雄属性统计的类。
> 这些统计属性包括力量、智慧和运气等特征。
> 当所有的属性都相同时，不同英雄的统计数据应被认为是相等的。

用直白的话来说

> 当值对象的属性有相同的值时，它们是相等的。

维基百科中说

> 在计算机科学中，值对象是一个代表简单实体的小对象。
> 其相等性不是基于身份的：即当两个值对象有相同的值的时候。
> 它们是相等的，而不必要是同一个对象。

**编程样例**

这里是作为值对象的 `HeroStat` 类。 请注意使用了
[Lombok's `@Value`](https://projectlombok.org/features/Value) 注解。

```java
@Value(staticConstructor = "valueOf")
class HeroStat {

    int strength;
    int intelligence;
    int luck;
}
```

这个示例创建了三个不同的 `HeroStat`s 并比较了它们的相等性。

```java
var statA = HeroStat.valueOf(10, 5, 0);
var statB = HeroStat.valueOf(10, 5, 0);
var statC = HeroStat.valueOf(5, 1, 8);

LOGGER.info(statA.toString());
LOGGER.info(statB.toString());
LOGGER.info(statC.toString());

LOGGER.info("Is statA and statB equal : {}", statA.equals(statB));
LOGGER.info("Is statA and statC equal : {}", statA.equals(statC));
```

以下是控制台的输出。

```
20:11:12.199 [main] INFO com.iluwatar.value.object.App - HeroStat(strength=10, intelligence=5, luck=0)
20:11:12.202 [main] INFO com.iluwatar.value.object.App - HeroStat(strength=10, intelligence=5, luck=0)
20:11:12.202 [main] INFO com.iluwatar.value.object.App - HeroStat(strength=5, intelligence=1, luck=8)
20:11:12.202 [main] INFO com.iluwatar.value.object.App - Is statA and statB equal : true
20:11:12.203 [main] INFO com.iluwatar.value.object.App - Is statA and statC equal : false
```

## 类图

![alt text](./etc/value-object.png "Value Object")

## 应用

当满足以下情况时，使用值对象：

* 对象的相等性需要基于对象的值

## 现实世界的案例

* [java.util.Optional](https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html)
* [java.time.LocalDate](https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html)
* [joda-time, money, beans](http://www.joda.org/)

## 鸣谢

* [Patterns of Enterprise Application Architecture](http://www.martinfowler.com/books/eaa.html)
* [ValueObject](https://martinfowler.com/bliki/ValueObject.html)
* [VALJOs - Value Java Objects : Stephen Colebourne's blog](http://blog.joda.org/2014/03/valjos-value-java-objects.html)
* [Value Object : Wikipedia](https://en.wikipedia.org/wiki/Value_object)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
