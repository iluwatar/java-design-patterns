---
title: "Java 收集參數模式：掌握高效的參數處理"
shortTitle: 收集參數
description: "了解收集參數設計模式如何將多個參數聚合為單一集合物件，簡化 Java 方法呼叫並提升可讀性。"
category: Behavioral
language: zh-TW
tag:
  - Accumulation
  - Data processing
  - Data transfer
  - Generic
---

## 又稱為

* 收集器
* 累加器

## 收集參數設計模式的目的

收集參數模式將多個參數聚合成單一集合物件，以簡化方法呼叫。方法可以把同一個集合物件傳遞給不同方法，讓每個方法將結果加入集合，而不必各自建立集合，藉此提升 Java 程式的可讀性與可維護性。

## 收集參數模式的詳細說明與真實世界範例

真實世界範例

> 在餐廳中，服務生需要記錄顧客的訂單。服務生不必分別記下開胃菜、主餐、甜點與飲料，而是使用一張訂單表單把所有品項集中在同一份文件中。這張表單讓服務生與廚房更容易溝通；在軟體中，收集參數模式也會將多個參數集中到單一物件中。

簡單來說

> 收集參數模式將多個參數封裝到單一物件，簡化方法呼叫。

維基百科指出：

> 收集參數慣用法會反覆將集合（清單、映射等）作為參數傳給方法，由方法將項目加入集合。

流程圖

![收集參數流程圖](./etc/collecting-parameter-flowchart.png)

## Java 收集參數模式的程式範例

大型企業大樓中有一個全域印表機佇列，包含目前等待處理的所有列印工作。不同樓層有不同型號的印表機與列印政策，程式需要持續將符合條件的列印工作加入收集參數。

規則如下：

* 彩色 A4 紙必須單面列印，其他非彩色紙張都接受。
* A3 紙張必須非彩色且單面列印。
* A2 紙張必須單頁、單面且非彩色。

```java
public class App {
  static PrinterQueue printerQueue = PrinterQueue.getInstance();

  public static void main(String[] args) {
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A4, 5, false, false));
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A3, 2, false, false));
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A2, 5, false, false));

    var result = new LinkedList<PrinterItem>();
    addValidA4Papers(result);
    addValidA3Papers(result);
    addValidA2Papers(result);
  }

  public static void addValidA4Papers(Queue<PrinterItem> collection) {
    for (PrinterItem item : printerQueue.getPrinterQueue()) {
      if (item.paperSize.equals(PaperSizes.A4)) {
        var valid = item.isColour && !item.isDoubleSided;
        if (valid || !item.isColour) collection.add(item);
      }
    }
  }

  public static void addValidA3Papers(Queue<PrinterItem> collection) {
    for (PrinterItem item : printerQueue.getPrinterQueue()) {
      if (item.paperSize.equals(PaperSizes.A3)
          && !item.isColour && !item.isDoubleSided) collection.add(item);
    }
  }

  public static void addValidA2Papers(Queue<PrinterItem> collection) {
    for (PrinterItem item : printerQueue.getPrinterQueue()) {
      if (item.paperSize.equals(PaperSizes.A2)
          && item.pageCount == 1 && !item.isDoubleSided && !item.isColour) {
        collection.add(item);
      }
    }
  }
}
```

`result` 就是收集參數。它從一個方法傳到下一個方法，逐步累積符合政策的列印工作，這正是收集參數模式的核心。

## 何時使用收集參數模式

* 方法需要大量參數，導致方法簽章難以閱讀。
* 多個方法需要傳遞同一組參數，以減少重複與錯誤。
* 想提升程式碼的可讀性與可維護性。

## 收集參數模式的實際應用

* 收集多個處理步驟的結果。
* 在資料處理流程中傳遞同一個累加器。
* 重構參數過多且難以維護的方法。

## 收集參數模式的優點與取捨

優點：

* 減少方法簽章中的參數數量，提高可讀性。
* 讓不同方法重複使用參數集合。
* 集中參數結構，提高可維護性。

取捨：

* 會增加額外類別，管理不當時可能提高複雜度。
* 參數物件過大時可能造成過度泛化。

## 相關 Java 設計模式

* [命令](https://java-design-patterns.com/patterns/command/)：命令可使用收集參數聚合多個操作的結果。
* [組合](https://java-design-patterns.com/patterns/composite/)：處理階層結構時，可收集整個組合結構的結果。
* [訪問者](https://java-design-patterns.com/patterns/visitor/)：訪問者負責走訪，收集參數則累積結果。

## 參考資料與致謝

* [Refactoring To Patterns](http://www.tarrani.net/RefactoringToPatterns.pdf)
* [Smalltalk Best Practice Patterns](https://ptgmedia.pearsoncmg.com/images/9780134769042/samplepages/013476904X.pdf)
* [Collecting Parameter（WikiWikiWeb）](https://wiki.c2.com/?CollectingParameter)