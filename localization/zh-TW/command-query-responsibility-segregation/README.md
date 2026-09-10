---
title: "Java 命令查詢職責分離：為可擴充性最佳化資料互動"
shortTitle: 命令查詢職責分離（CQRS）
description: "了解 Java 的 CQRS 模式，透過分離命令與查詢提升系統的擴充性、效能與可維護性。"
category: Architectural
language: zh-TW
tag:
  - Event-driven
  - Performance
  - Scalability
---

## 又稱為

* CQRS

## 命令查詢職責分離的目的

命令查詢職責分離（CQRS）將修改應用程式狀態的操作（命令）與讀取狀態的操作（查詢）分開，以提升複雜軟體系統的擴充性、效能與可維護性。

## CQRS 的詳細說明與真實世界範例

真實世界範例

> 想像一座現代圖書館：借書與還書（命令）在服務櫃台處理，搜尋與閱讀書籍（查詢）則在閱覽區進行。櫃台最佳化交易效率與紀錄保存，閱覽區則最佳化舒適度與取用性。這種分離就像 CQRS 一樣，提升整體效率與使用體驗。

簡單來說

> CQRS 將修改資料的動作（命令）與取得資料的動作（查詢）分開，讓讀取與寫入可以獨立最佳化。

Microsoft 文件指出：

> CQRS 將讀取與寫入分成不同模型，使用命令更新資料，使用查詢讀取資料。

架構圖

![CQRS 架構圖](./etc/cqrs-architecture-diagram.png)

## Java CQRS 模式的程式範例

實作 CQRS 的一種方式，是將讀取與寫入操作放在不同服務中。以下程式使用 `CommandServiceImpl` 執行寫入，並使用 `QueryServiceImpl` 執行讀取。

```java
public static void main(String[] args) {
    // 使用 CommandService 建立作者與書籍
    var commands = new CommandServiceImpl();
    commands.authorCreated(AppConstants.E_EVANS, "Eric Evans", "evans@email.com");
    commands.authorCreated(AppConstants.J_BLOCH, "Joshua Bloch", "jBloch@email.com");
    commands.authorCreated(AppConstants.M_FOWLER, "Martin Fowler", "mFowler@email.com");
    commands.bookAddedToAuthor("Domain-Driven Design", 60.08, AppConstants.E_EVANS);
    commands.bookAddedToAuthor("Effective Java", 40.54, AppConstants.J_BLOCH);
    commands.bookAddedToAuthor("Java Puzzlers", 39.99, AppConstants.J_BLOCH);
    commands.bookAddedToAuthor("Java Concurrency in Practice", 29.40, AppConstants.J_BLOCH);
    commands.authorNameUpdated(AppConstants.E_EVANS, "Eric J. Evans");

    // 使用 QueryService 查詢資料庫
    var queries = new QueryServiceImpl();
    var nullAuthor = queries.getAuthorByUsername("username");
    var evans = queries.getAuthorByUsername(AppConstants.E_EVANS);
    var blochBooksCount = queries.getAuthorBooksCount(AppConstants.J_BLOCH);
    var authorsCount = queries.getAuthorsCount();
    var dddBook = queries.getBook("Domain-Driven Design");
    var blochBooks = queries.getAuthorBooks(AppConstants.J_BLOCH);
    LOGGER.info("Author username : {}", nullAuthor);
    LOGGER.info("Author evans : {}", evans);
    LOGGER.info("jBloch number of books : {}", blochBooksCount);
    LOGGER.info("Number of authors : {}", authorsCount);
    LOGGER.info("DDD book : {}", dddBook);
    LOGGER.info("jBloch books : {}", blochBooks);
    HibernateUtil.getSessionFactory().close();
}
```

`CommandServiceImpl` 負責建立作者、書籍及將書籍加入作者；`QueryServiceImpl` 負責取得作者與書籍細節。這種關注點分離讓資料存取與操作方式更有彈性，是 CQRS 的核心。

程式輸出：

```
Author username : null
Author evans : Author(name=Eric J. Evans, email=evans@email.com, username=eEvans)
jBloch number of books : 3
Number of authors : 3
DDD book : Book(title=Domain-Driven Design, price=60.08)
```

## 何時在 Java 中使用 CQRS

* 需要為擴充性與維護性使用不同讀寫模型的系統，例如電子商務平台與高流量網站。
* 金融或醫療等複雜領域，更新物件與讀取資料的需求差異很大。
* 讀取效能很重要，且讀寫可以使用不同資料模型或資料庫的情境。

## CQRS 的實際應用

* 由不同服務分別管理讀寫責任的分散式系統與微服務架構。
* 將狀態變更儲存為事件序列的事件溯源系統。
* 分離讀寫資料庫以最佳化負載處理的高效能 Web 應用程式。

## CQRS 的優點與取捨

優點：

* 擴充性：讀寫模型可依各自需求獨立擴充。
* 最佳化：讀取模型可針對查詢、寫入模型可針對交易完整性最佳化。
* 可維護性：分離關注點，降低複雜度。
* 彈性：讀寫兩端可選擇不同技術。

取捨：

* 讀寫模型同步與一致性維護會增加複雜度。
* 對簡單系統而言可能是過度設計。
* 需要更深入理解與仔細設計，初期學習成本較高。

## 相關 Java 設計模式

* [事件溯源](https://java-design-patterns.com/patterns/event-sourcing/)：常與 CQRS 一起使用，將狀態變更儲存為事件序列。
* 領域驅動設計（DDD）：CQRS 能提供清楚邊界與關注點分離。
* [儲存庫](https://java-design-patterns.com/patterns/repository/)：抽象資料層，整合命令端與查詢端。

## 參考資料與致謝

* [Implementing Domain-Driven Design](https://amzn.to/3TJN2HH)
* [Microsoft .NET: Architecting Applications for the Enterprise](https://amzn.to/4aktRes)
* [Patterns, Principles, and Practices of Domain-Driven Design](https://amzn.to/3vNV4Hm)
* [CQRS（Martin Fowler）](https://martinfowler.com/bliki/CQRS.html)
* [CQRS pattern（Microsoft）](https://docs.microsoft.com/en-us/azure/architecture/patterns/cqrs)