---
title: Abstract Factory
category: Creational
language: ru
tag:
 - Gang of Four
---

## Альтернативные названия
Kit

## Цель
Предоставление интерфейса для создания семейств взаимосвязанных или взаимозависимых объектов без указания их конкретных классов.

## Объяснение
Пример из реального мира:
> Представьте, что вы хотите создать королевство с замком, королём и армией. Эльфийскому королевству понадобится эльфийский замок, эльфийский король и эльфийская армия, в то время как оркскому королевству понадобится оркский замок, оркский король и оркская армия. Между объектами королевства существует взаимозависимость.

Простыми словами:
> Фабрика фабрик; фабрика, которая объединяет отдельные, но взаимозависимые/взаимозаменяемые фабрики без указания их конкретных классов.

Википедия пишет:
> Порождающий шаблон проектирования, предоставляет интерфейс для создания семейств взаимосвязанных или взаимозависимых объектов, не специфицируя￼ их конкретных классов.￼

**Программный пример**\
Основываясь на примере королевства выше, во-первых, нам понадобятся интерфейсы и реализации для объектов в королевстве.

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

// Эльфийская реализация
public class ElfCastle implements Castle {
  static final String DESCRIPTION = "Это эльфийский замок!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class ElfKing implements King {
  static final String DESCRIPTION = "Это эльфийский король!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class ElfArmy implements Army {
  static final String DESCRIPTION = "Это эльфийская армия!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}

// Оркская реализация
public class OrcCastle implements Castle {
  static final String DESCRIPTION = "Это оркский замок!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class OrcKing implements King {
  static final String DESCRIPTION = "Это оркский король!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class OrcArmy implements Army {
  static final String DESCRIPTION = "Это оркская армия!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
```

Во-вторых, нам понадобятся абстракция фабрики королевства, а также реализации этой абстракции:

```java
public interface KingdomFactory {
  Castle createCastle();
  King createKing();
  Army createArmy();
}

public class ElfKingdomFactory implements KingdomFactory {

  @Override
  public Castle createCastle() {
    return new ElfCastle();
  }

  @Override
  public King createKing() {
    return new ElfKing();
  }

  @Override
  public Army createArmy() {
    return new ElfArmy();
  }
}

public class OrcKingdomFactory implements KingdomFactory {

  @Override
  public Castle createCastle() {
    return new OrcCastle();
  }

  @Override
  public King createKing() {
    return new OrcKing();
  }
  
  @Override
  public Army createArmy() {
    return new OrcArmy();
  }
}
```

На данный момент, у нас есть абстрактная фабрика, которая позволяет создавать семейство взаимосвязанных объектов, то есть фабрика эльфийского королевства создаёт эльфийский замок, эльфийского короля, эльфийскую армию и так далее:

```java
var factory = new ElfKingdomFactory();
var castle = factory.createCastle();
var king = factory.createKing();
var army = factory.createArmy();

castle.getDescription();
king.getDescription();
army.getDescription();
```

Вывод программы:

```
Это эльфийский замок!
Это эльфийский король!
Это эльфийская армия!
```

Можно спроектировать фабрику для наших различных фабрик королевств. В следующем примере, мы создали `FactoryMaker`, который ответственен за создание экземпляра `ElfKingdomFactory`, либо `OrcKingdomFactory`.

Клиент может использовать класс `FactoryMaker`, чтобы создать желаемую конкретную фабрику, которая в свою очередь будет производить разные конкретные объекты, унаследованные от `Castle`, `King`, `Army`.

В этом примере использовано перечисление, чтобы параметризовать то, какую фабрику королевства запрашивает клиент:

```java
public static class FactoryMaker {
  public enum KingdomType {
    ELF, ORC
  }

  public static KingdomFactory makeFactory(KingdomType type) {
    return switch (type) {
      case ELF -> new ElfKingdomFactory();
      case ORC -> new OrcKingdomFactory();
      default -> throe new IllegalArgumentException("Данный тип королества не поддерживается.")
    }
  }
}

  public static void main(String[] args) {
    var app = new App();
    
    LOGGER.info("Эльфийское королевство");
    app.createKingdom(FactoryMaker.makeFactory(KingdomType.ELF));
    LOGGER.indo(app.getCastle().getDescription());
    LOGGER.indo(app.getKinv().getDescription());
    LOGGER.indo(app.getArmy().getDescription());

    LOGGER.info("Оркское королевство");

    app.createKingdom(FactoryMaker.makeFactory(KingdomType.ORC));
    LOGGER.indo(app.getCastle().getDescription());
    LOGGER.indo(app.getKinv().getDescription());
    LOGGER.indo(app.getArmy().getDescription());
  }

```

## Диаграмма классов
![Диаграмма классов паттерна проектирования абстрактная фабрика](../../../abstract-factory/etc/abstract-factory.urm.png)

## Применимость
Используйте шаблон проектирования абстрактная фабрика, когда:

- Система должна быть независимой от того, как создаются, составляются и представляются её продукты;
- Система должна быть сконфигурирована одним из нескольких семейств продуктов;
- Семейство связанных продуктов спроектировано для совместного использования и вам необходимо обеспечить соблюдение данного ограничения;
- Вы хотите предоставить библиотеку классов, но готовы раскрыть только их интерфейсы, но не реализацию;
- Время жизни зависимости концептуально короче, чем время жизни потребителя;
- Вам требуется значение времени исполнения, чтобы создать требуемую зависимость;
- Вы хотите решить какие продукты из семейства вам нужны во время исполнения;
- Вам нужно предоставить один или несколько параметров, которые известны только во время исполнения прежде, чем вы сможете решить зависимость;
- В случае, когда требуется последовательность среди продуктов;
- Вы не хотите менять существующий код, когда добавляются новые продукты или семейство продуктов в программе.

Примеры сценариев использования:

- Выбор необходимой реализации FileSystemAcmeService или DataBaseAcmeSerivce или NetworkAcmeService во времени выполнения;
- Написание модульных тестов становится намного легче;
- Элементы графического пользовательского интерфейса для различных операционных систем.

## Следствия
- Внедрение зависимости в Java скрывает зависимости класса, приводящие к ошибкам времени выполнения, которые могли-бы быть обнаружены во времени компиляции;
- Данный паттерн проектирования отлично справляется с задачей создания уже определённых объектов, но добавление новых может быть трудоёмким;
- Код становится сложнее, чем ему следует из-за появления большого количества новых интерфейсов и классов, которые появляются вместе с этим паттерном проектирования.

## Руководства
* [Abstract Factory Pattern Tutorial](https://www.journaldev.com/1418/abstract-factory-design-pattern-in-java) 

## Известные применения
* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
* [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
* [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)

## Родственные паттерны проектирования
* [Factory Method](https://java-design-patterns.com/patterns/factory-method/)
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)

## Благодарности
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
