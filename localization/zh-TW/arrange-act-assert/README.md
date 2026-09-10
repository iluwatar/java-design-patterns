---
title: Arrange/Act/Assert
shortTitle: Arrange/Act/Assert
category: Idiom
language: zh
tag:
 - Testing
---

## 或稱

Given/When/Then

## 意圖

安排/執行/斷言（AAA）是組織單元測試的一種模式。

它將測試分為三個清晰而獨特的步驟：

1. 安排：執行測試所需的設定和初始化。
2. 執行：採取測試所需的行動。
3. 斷言：驗證測試結果。

## 解釋

這種模式有幾個明顯的好處。 它在測試的設定，操作和結果之間建立了清晰的分隔。 這種結構使程式碼更易於閱讀和理解。 如果按順序排列步驟並格式化程式碼以將它們分開，則可以掃描測試並快速瞭解其功能。

當您編寫測試時，它還會強制執行一定程度的紀律。 您必須清楚地考慮您的測試將執行的三個步驟。 由於您已經有了大綱，因此可以使同時編寫測試變得更加自然。

真實世界例子

> 我們需要為一個類編寫全面而清晰的單元測試套件。

通俗地說

> 安排/執行/斷言是一種測試模式，將測試分為三個清晰的步驟以方便維護。

WikiWikiWeb 上說

> 安排/執行/斷言是用於在單元測試方法中排列和格式化程式碼的模式。

**程式示例**

讓我們首先介紹要進行單元測試的`Cash`類。

```java
public class Cash {

  private int amount;

  Cash(int amount) {
    this.amount = amount;
  }

  void plus(int addend) {
    amount += addend;
  }

  boolean minus(int subtrahend) {
    if (amount >= subtrahend) {
      amount -= subtrahend;
      return true;
    } else {
      return false;
    }
  }

  int count() {
    return amount;
  }
}
```

然後我們根據安排/ 執行 / 斷言模式編寫單元測試。 注意每個單元測試的步驟是分開的清晰的。

```java
class CashAAATest {

  @Test
  void testPlus() {
    //Arrange
    var cash = new Cash(3);
    //Act
    cash.plus(4);
    //Assert
    assertEquals(7, cash.count());
  }

  @Test
  void testMinus() {
    //Arrange
    var cash = new Cash(8);
    //Act
    var result = cash.minus(5);
    //Assert
    assertTrue(result);
    assertEquals(3, cash.count());
  }

  @Test
  void testInsufficientMinus() {
    //Arrange
    var cash = new Cash(1);
    //Act
    var result = cash.minus(6);
    //Assert
    assertFalse(result);
    assertEquals(1, cash.count());
  }

  @Test
  void testUpdate() {
    //Arrange
    var cash = new Cash(5);
    //Act
    cash.plus(6);
    var result = cash.minus(3);
    //Assert
    assertTrue(result);
    assertEquals(8, cash.count());
  }
}
```

## 適用性

使用 安排/執行/斷言 模式當

* 你需要結構化你的單元測試程式碼這樣它們可以更好的閱讀，維護和增強。

## 鳴謝

* [Arrange, Act, Assert: What is AAA Testing?](https://blog.ncrunch.net/post/arrange-act-assert-aaa-testing.aspx)
* [Bill Wake: 3A – Arrange, Act, Assert](https://xp123.com/articles/3a-arrange-act-assert/)
* [Martin Fowler: GivenWhenThen](https://martinfowler.com/bliki/GivenWhenThen.html)
* [xUnit Test Patterns: Refactoring Test Code](https://www.amazon.com/gp/product/0131495054/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0131495054&linkId=99701e8f4af2f7e8dd50d720c9b63dbf)
* [Unit Testing Principles, Practices, and Patterns](https://www.amazon.com/gp/product/1617296279/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617296279&linkId=74c75cf22a63c3e4758ae08aa0a0cc35)
* [Test Driven Development: By Example](https://www.amazon.com/gp/product/0321146530/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321146530&linkId=5c63a93d8c1175b84ca5087472ef0e05)
