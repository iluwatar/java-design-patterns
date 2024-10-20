---
title: Abstract Factory
shortTitle: Abstract Factory
category: Creational
language: zh
tag:
 - Gang of Four
---

## 或称

工具包

## 目的

提供一个用于创建相关对象家族的接口，而无需指定其具体类。

## 解释

真实世界例子

> 要创建一个王国，我们需要具有共同主题的对象。精灵王国需要精灵国王、精灵城堡和精灵军队，而兽人王国需要兽人国王、兽人城堡和兽人军队。王国中的对象之间存在依赖关系。

通俗的说

> 工厂的工厂； 一个将单个但相关/从属的工厂分组在一起而没有指定其具体类别的工厂。

维基百科上说

> 抽象工厂模式提供了一种封装一组具有共同主题的单个工厂而无需指定其具体类的方法

**程序示例**

翻译上面的王国示例。 首先，我们为王国中的对象提供了一些接口和实现。

```java
public interface Castle {
  String getDescription();
}

public interface King {
  String getDescription();
}

public interface Army {
  String getDescription();
}

// Elven implementations ->
public class ElfCastle implements Castle {
  static final String DESCRIPTION = "This is the Elven castle!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class ElfKing implements King {
  static final String DESCRIPTION = "This is the Elven king!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class ElfArmy implements Army {
  static final String DESCRIPTION = "This is the Elven Army!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}

// Orcish implementations similarly -> ...

```

然后我们有了王国工厂的抽象和实现

```java
public interface KingdomFactory {
  Castle createCastle();
  King createKing();
  Army createArmy();
}

public class ElfKingdomFactory implements KingdomFactory {
  public Castle createCastle() {
    return new ElfCastle();
  }
  public King createKing() {
    return new ElfKing();
  }
  public Army createArmy() {
    return new ElfArmy();
  }
}

public class OrcKingdomFactory implements KingdomFactory {
  public Castle createCastle() {
    return new OrcCastle();
  }
  public King createKing() {
    return new OrcKing();
  }
  public Army createArmy() {
    return new OrcArmy();
  }
}
```

现在我们有了抽象工厂，使我们可以制作相关对象的系列，即精灵王国工厂创建了精灵城堡，国王和军队等。

```java
var factory = new ElfKingdomFactory();
var castle = factory.createCastle();
var king = factory.createKing();
var army = factory.createArmy();

castle.getDescription();
king.getDescription();
army.getDescription();
```

程序输出:

```java
This is the Elven castle!
This is the Elven king!
This is the Elven Army!
```

现在，我们可以为不同的王国工厂设计工厂。 在此示例中，我们创建了FactoryMaker，负责返回ElfKingdomFactory或OrcKingdomFactory的实例。 客户可以使用FactoryMaker来创建所需的具体工厂，该工厂随后将生产不同的具体对象（军队，国王，城堡）。 在此示例中，我们还使用了一个枚举来参数化客户要求的王国工厂类型。

```java
public static class FactoryMaker {

    public enum KingdomType {
        ELF, ORC
    }

    public static KingdomFactory makeFactory(KingdomType type) {
        return switch (type) {
            case ELF -> new ElfKingdomFactory();
            case ORC -> new OrcKingdomFactory();
            default -> throw new IllegalArgumentException("KingdomType not supported.");
        };
    }
}

    public static void main(String[] args) {
        var app = new App();

        LOGGER.info("Elf Kingdom");
        app.createKingdom(FactoryMaker.makeFactory(KingdomType.ELF));
        LOGGER.info(app.getArmy().getDescription());
        LOGGER.info(app.getCastle().getDescription());
        LOGGER.info(app.getKing().getDescription());

        LOGGER.info("Orc Kingdom");
        app.createKingdom(FactoryMaker.makeFactory(KingdomType.ORC));
        --similar use of the orc factory
    }
```

## 类图

![alt text](./etc/abstract-factory.urm.png "Abstract Factory class diagram")


## 适用性

在以下情况下使用抽象工厂模式

* 该系统应独立于其产品的创建，组成和表示方式
* 系统应配置有多个产品系列之一
* 相关产品对象系列旨在一起使用，你需要强制执行此约束
* 你想提供产品的类库，并且只想暴露它们的接口，而不是它们的实现。
* 从概念上讲，依赖项的生存期比使用者的生存期短。
* 你需要一个运行时值来构建特定的依赖关系
* 你想决定在运行时从系列中调用哪种产品。
* 你需要提供一个或更多仅在运行时才知道的参数，然后才能解决依赖关系。
* 当你需要产品之间的一致性时
* 在向程序添加新产品或产品系列时，您不想更改现有代码。

示例场景

* 在运行时在FileSystemAcmeService ，DatabaseAcmeService 或NetworkAcmeService中选择并调用一个
* 单元测试用例的编写变得更加容易
* 适用于不同操作系统的UI工具

## 后果:

* Java中的依赖注入会隐藏服务类的依赖关系，这些依赖关系可能导致运行时错误，而这些错误在编译时会被捕获。
* 虽然在创建预定义对象时模式很好，但是添加新对象可能会很困难。
* 由于引入了许多新的接口和类，因此代码变得比应有的复杂。

## 教程

* [Abstract Factory Pattern Tutorial](https://www.journaldev.com/1418/abstract-factory-design-pattern-in-java) 

## 已知使用

* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
* [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
* [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)

## 相关模式

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/)
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)

## 鸣谢

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
