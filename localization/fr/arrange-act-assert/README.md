---
title: Arrange/Act/Assert
category: Idiom
language: fr
tag:
 - Testing
---

## Aussi connu sous le nom de

Given/When/Then

## Intention

Arrange/Act/Assert (AAA) est un pattron d'organisation des tests unitaires.
Il décompose les tests en trois étapes claires et distinctes :

1. Arrange : éffectuer la configuration et l'initialisation requises pour le test.
2. Act : éffectuer la ou les actions requises pour le test.
3. Assert: Vérifier le(s) résultat(s) du test.

## Explication

Ce pattron présente plusieurs avantages importants. Il crée une séparation claire entre la mise en place d'un test d'un test,
les opérations et les résultats. Cette structure rend le code plus facile à lire et à comprendre. 
Si vous placez les étapes dans l'ordre et que vous formatez votre code source pour les séparer, vous pouvez parcourir un test et comprendre rapidement ce qu'il fait.

Il impose également un certain degré de discipline lors de l'écriture des tests. Vous devez penser clairement les trois étapes de votre test.
Il est plus naturel d'écrire les tests en même temps, puisque vous avez déjà un plan.

Exemple

> Pour écrire des tests unitaire clairs et compréhensibles.

En clair

> Arrange/Act/Assert est un pattron de conception de tests qui organise les tests en trois étapes pour une maintenance facile.

WikiWikiWeb dit

> Arrange/Act/Assert est un pattron pour organiser et formater un code dans les methodes de test unitaire.

**Exemple de programme**

Commençons par présenter notre classe  `Cash` qui subira un test unitaire.

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

Ensuite, nous rédigeons nos tests unitaires suivant le pattron Arrange/Act/Assert.
Notez les étapes clairement séparées pour chaque test unitaire.

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

## Application

Le pattron Arrange/Act/Assert peut être utilisé lorsque :

* Vous avez besoin d'organiser vos tests unitaire pour qu'il soit facile à lire, maintenir et améliorer.

## Crédits

* [Arrange, Act, Assert: What is AAA Testing?](https://blog.ncrunch.net/post/arrange-act-assert-aaa-testing.aspx)
* [Bill Wake: 3A – Arrange, Act, Assert](https://xp123.com/articles/3a-arrange-act-assert/)
* [Martin Fowler: GivenWhenThen](https://martinfowler.com/bliki/GivenWhenThen.html)
* [xUnit Test Patterns: Refactoring Test Code](https://www.amazon.com/gp/product/0131495054/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0131495054&linkId=99701e8f4af2f7e8dd50d720c9b63dbf)
* [Unit Testing Principles, Practices, and Patterns](https://www.amazon.com/gp/product/1617296279/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617296279&linkId=74c75cf22a63c3e4758ae08aa0a0cc35)
* [Test Driven Development: By Example](https://www.amazon.com/gp/product/0321146530/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321146530&linkId=5c63a93d8c1175b84ca5087472ef0e05)
