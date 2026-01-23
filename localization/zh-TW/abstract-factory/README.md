---
title: Abstract Factory
shortTitle: Abstract Factory
category: Structural
language: zh-TW
tag: 
 - Extensibility
---

## 別稱

* Kit (套件)

## 抽象工廠設計模式的意圖

Java 中的抽象工廠模式 (Abstract Factory pattern) 提供一個介面，用於創建一系列相關或相依的物件家族，而無需指定其具體類別，從而增強了軟體設計的模組化與靈活性。

## 抽象工廠模式的詳細解釋與真實世界範例

真實世界範例

> 想像一家傢俱公司，它使用 Java 中的抽象工廠模式來生產多種風格的傢俱：現代風、維多利亞風和鄉村風。每種風格都包含椅子、桌子和沙發等產品。為了確保每種風格內部的一致性，該公司使用了抽象工廠模式。
>
> 在這個情境中，抽象工廠是一個用於創建相關傢俱物件家族（椅子、桌子、沙發）的介面。每個具體工廠（ModernFurnitureFactory、VictorianFurnitureFactory、RusticFurnitureFactory）都實作了這個抽象工廠介面，並創建一組符合特定風格的產品。如此一來，客戶端就可以創建一整套現代或維多利亞風格的傢俱，而無需擔心其實例化的細節。這不僅保持了風格的一致性，也讓更換不同風格的傢俱變得輕而易舉。

簡單來說

> 工廠中的工廠；一個將個別但相關/相依的工廠群組在一起，而無需指定其具體類別的工廠。

維基百科說

> 抽象工廠模式提供了一種方法，可以封裝一組具有共通主題的獨立工廠，而無需指定它們的具體類別。

類別圖

![Abstract Factory class diagram](./etc/abstract-factory.urm.png "Abstract Factory class diagram")

## Java 中的抽象工廠程式碼範例

為了使用抽象工廠模式來創建一個王國，我們需要具有共通主題的物件。精靈王國需要精靈國王、精靈城堡和精靈軍隊；而獸人王國則需要獸人國王、獸人城堡和獸人軍隊。王國中的這些物件之間存在著相依性。

將上述的王國範例轉換為程式碼。首先，我們為王國中的物件定義一些介面和實作。

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
    static final String DESCRIPTION = "This is the elven castle!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}

public class ElfKing implements King {
    static final String DESCRIPTION = "This is the elven king!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}

public class ElfArmy implements Army {
    static final String DESCRIPTION = "This is the elven Army!";

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}

// Orcish implementations similarly -> ...
```

接著，我們為王國工廠定義抽象和實作。

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

// 獸人 (Orcish) 的實作也類似 -> ...
```
現在，我們可以為我們不同的王國工廠設計一個工廠。在這個範例中，我們創建了 `FactoryMaker`，它負責返回 `ElfKingdomFactory` 或 O`OrcKingdomFactory` 的實例。客戶端可以使用 `FactoryMaker` 來創建所需的具體工廠，而該工廠又會生產出不同的具體物件（繼承自 `Army`, `King`, `Castle`）。在這個範例中，我們還使用了一個 enum 來參數化客戶端將請求哪種類型的王國工廠。

```java
public static class FactoryMaker {

    public enum KingdomType {
        ELF, ORC
    }

    public static KingdomFactory makeFactory(KingdomType type) {
        return switch (type) {
            case ELF -> new ElfKingdomFactory();
            case ORC -> new OrcKingdomFactory();
        };
    }
}
```

這是我們範例應用程式的主函式：

```java
LOGGER.info("elf kingdom");
createKingdom(Kingdom.FactoryMaker.KingdomType.ELF);
LOGGER.info(kingdom.getArmy().getDescription());
LOGGER.info(kingdom.getCastle().getDescription());
LOGGER.info(kingdom.getKing().getDescription());

LOGGER.info("orc kingdom");
createKingdom(Kingdom.FactoryMaker.KingdomType.ORC);
LOGGER.info(kingdom.getArmy().getDescription());
LOGGER.info(kingdom.getCastle().getDescription());
LOGGER.info(kingdom.getKing().getDescription());
```

程式輸出:

```
07:35:46.340 [main] INFO com.iluwatar.abstractfactory.App -- elf kingdom
07:35:46.343 [main] INFO com.iluwatar.abstractfactory.App -- This is the elven army!
07:35:46.343 [main] INFO com.iluwatar.abstractfactory.App -- This is the elven castle!
07:35:46.343 [main] INFO com.iluwatar.abstractfactory.App -- This is the elven king!
07:35:46.343 [main] INFO com.iluwatar.abstractfactory.App -- orc kingdom
07:35:46.343 [main] INFO com.iluwatar.abstractfactory.App -- This is the orc army!
07:35:46.343 [main] INFO com.iluwatar.abstractfactory.App -- This is the orc castle!
07:35:46.343 [main] INFO com.iluwatar.abstractfactory.App -- This is the orc king!
```

## 在 Java 中何時使用抽象工廠模式
在 Java 中，當您遇到以下情況時，請使用抽象工廠模式：:

* 系統應與其產品的創建、組合和表示方式無關。

* 您需要用多個產品家族中的其中一個來配置系統。

* 必須同時使用一系列相關的產品物件，以強制保持一致性。

* 您希望提供一個產品的類別庫，只暴露它們的介面，而不是它們的實作。

* 相依物件的生命週期比消費者的生命週期短。

* 相依物件需要使用執行時期的值或參數來建構。

* 您需要在執行時期從一個家族中選擇要使用的產品。

* 新增新產品或家族時，不應要求修改現有程式碼。

## Java 抽象工廠模式教學

* [Abstract Factory Design Pattern in Java (DigitalOcean)](https://www.digitalocean.com/community/tutorials/abstract-factory-design-pattern-in-java)
* [Abstract Factory(Refactoring Guru)](https://refactoring.guru/design-patterns/abstract-factory)

## 抽象工廠模式的優點與權衡

優點：

* 靈活性：無需修改程式碼即可輕鬆切換產品家族。
 
* 解耦：客戶端程式碼只與抽象介面互動，提升了可移植性和可維護性。

* 可重用性：抽象工廠和產品有助於跨專案的元件重用。

* 可維護性：對個別產品家族的變更是局部的，簡化了更新過程。

權衡：

* 複雜性：定義抽象介面和具體工廠會增加初期的開發開銷。

* 間接性：客戶端程式碼透過工廠間接與產品互動，可能會降低透明度。

## Java 中抽象工廠模式的實際應用

* Java Swing 的 `LookAndFeel` 類別，用於提供不同的外觀與感覺選項。

* Java Abstract Window Toolkit (AWT) 中的各種實作，用於創建不同的 GUI 元件。

* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
* [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
* [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)

## 相關的 Java 設計模式

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): 抽象工廠使用工廠方法來創建產品。
* [Singleton](https://java-design-patterns.com/patterns/singleton/): 抽象工廠類別通常被實作為單例
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/): 與抽象工廠類似，但更專注於以靈活的方式配置和管理一組相關物件。

## 參考資料與致謝

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Design Patterns in Java](https://amzn.to/3Syw0vC)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3HWNf4U)
