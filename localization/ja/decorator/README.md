---
title: Decorator
category: Structural
language: ja
tag:
 - Gang of Four
 - Extensibility
---

## 別名

Wrapper

## 目的

オブジェクトに責任を動的に追加する。
Decorator パターンは、サブクラス化よりも柔軟な機能拡張方法を提供する。

## 説明

実世界の例

> 近くの丘に住む怒ったトロールがいる。大抵は素手だが、たまに武器を持っている。トロールに武器を持たせるためには、新しいトロールを作る必要はなく、適切な武器を動的に装飾すればいい。

分かりやすい説明

> Decorator パターンを用いると、decorator クラスのオブジェクトにラップすることで、オブジェクトの振る舞いを実行時間で動的に変更することができる。

Wikipedia の説明

> オブジェクト指向プログラミングにおいて、Decorator パターンは、同じクラスの別のオブジェクトの振る舞いに影響を与えることなく、静的もしくは動的に、個別のオブジェクトに振る舞いを加えることができるデザインパターンである。Decorator パターンは、単一責任の原則を遵守するためにしばしば便利である。なぜなら、クラスの機能性を変更せずに拡張することで、開放/閉鎖原則などの関心事の特異な分野を元に、クラスの間で機能性を分割することができるためである。

**サンプルコード**

トロールを例として使用する。まず、Troll のインターフェイスを実装する SimpleTroll クラスを作る。

```java
public interface Troll {
  void attack();
  int getAttackPower();
  void fleeBattle();
}

@Slf4j
public class SimpleTroll implements Troll {

  @Override
  public void attack() {
    LOGGER.info("The troll tries to grab you!");
  }

  @Override
  public int getAttackPower() {
    return 10;
  }

  @Override
  public void fleeBattle() {
    LOGGER.info("The troll shrieks in horror and runs away!");
  }
}
```

次に、トロールに対してこん棒を追加する。decorator を使い、動的に追加することが可能である。

```java
@Slf4j
public class ClubbedTroll implements Troll {

  private final Troll decorated;

  public ClubbedTroll(Troll decorated) {
    this.decorated = decorated;
  }

  @Override
  public void attack() {
    decorated.attack();
    LOGGER.info("The troll swings at you with a club!");
  }

  @Override
  public int getAttackPower() {
    return decorated.getAttackPower() + 10;
  }

  @Override
  public void fleeBattle() {
    decorated.fleeBattle();
  }
}
```

以下がトロールの動き。

```java
// simple troll
LOGGER.info("A simple looking troll approaches.");
var troll = new SimpleTroll();
troll.attack();
troll.fleeBattle();
LOGGER.info("Simple troll power: {}.\n", troll.getAttackPower());

// change the behavior of the simple troll by adding a decorator
LOGGER.info("A troll with huge club surprises you.");
var clubbedTroll = new ClubbedTroll(troll);
clubbedTroll.attack();
clubbedTroll.fleeBattle();
LOGGER.info("Clubbed troll power: {}.\n", clubbedTroll.getAttackPower());
```

アウトプット

```java
A simple looking troll approaches.
The troll tries to grab you!
The troll shrieks in horror and runs away!
Simple troll power: 10.

A troll with huge club surprises you.
The troll tries to grab you!
The troll swings at you with a club!
The troll shrieks in horror and runs away!
Clubbed troll power: 20.
```

## クラス図

![alt text](./etc/decorator.urm.png "Decorator pattern class diagram")

## 適用可能性

次のような場合に Decorator パターンを利用する。

* 個々のオブジェクトに責任を動的、かつ透明に(すなわち、他のオブジェクトに影響を与えないように)追加する場合。
* 責任を取りはずすことができるようにする場合。
* サブクラス化による拡張が非現実的な場合。非常に多くの独立した拡張が起こり得ることがある。このような場合、サブクラス化により全ての組み合わせの拡張に対応しようとすると、莫大な数のサブクラスが必要になるだろう。また、クラス定義が隠蔽されている場合や入手できない場合にも、このパターンを利用できる。

## チュートリアル

* [Decorator Pattern Tutorial](https://www.journaldev.com/1540/decorator-design-pattern-in-java-example)

## 使用例

 * [java.io.InputStream](http://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html), [java.io.OutputStream](http://docs.oracle.com/javase/8/docs/api/java/io/OutputStream.html),
 [java.io.Reader](http://docs.oracle.com/javase/8/docs/api/java/io/Reader.html) and [java.io.Writer](http://docs.oracle.com/javase/8/docs/api/java/io/Writer.html)
 * [java.util.Collections#synchronizedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#synchronizedCollection-java.util.Collection-)
 * [java.util.Collections#unmodifiableXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#unmodifiableCollection-java.util.Collection-)
 * [java.util.Collections#checkedXXX()](http://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#checkedCollection-java.util.Collection-java.lang.Class-)


## クレジット

* [オブジェクト指向における再利用のためのデザインパターン](https://www.amazon.co.jp/%E3%82%AA%E3%83%96%E3%82%B8%E3%82%A7%E3%82%AF%E3%83%88%E6%8C%87%E5%90%91%E3%81%AB%E3%81%8A%E3%81%91%E3%82%8B%E5%86%8D%E5%88%A9%E7%94%A8%E3%81%AE%E3%81%9F%E3%82%81%E3%81%AE%E3%83%87%E3%82%B6%E3%82%A4%E3%83%B3%E3%83%91%E3%82%BF%E3%83%BC%E3%83%B3-%E3%82%A8%E3%83%AA%E3%83%83%E3%82%AF-%E3%82%AC%E3%83%B3%E3%83%9E/dp/4797311126)
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](https://www.amazon.com/gp/product/1937785467/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1937785467&linkCode=as2&tag=javadesignpat-20&linkId=7e4e2fb7a141631491534255252fd08b)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
