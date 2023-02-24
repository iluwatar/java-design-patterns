---
title: Arrange/Act/Assert
category: Idiom
language: en
tag:
 - Testing
---

## Also known as
## También conocido como

Given/When/Then
Dado/Cuando/Entonces

## Intent
## Intención

Arrange/Act/Assert (AAA) is a pattern for organizing unit tests.
Arrange/Act/Assert (AAA) es un patrón para organizar UnitTests.
It breaks tests down into three clear and distinct steps:
Divide las UnitTests en tres pasos claros y diferenciados:

1. Arrange: Perform the setup and initialization required for the test.
1. Organizar: Realiza la configuración y la inicialización necesarias para el test.
2. Act: Take action(s) required for the test.
2. Actuar: Toma las medidas necesarias para el test.
3. Assert: Verify the outcome(s) of the test.
3. Afirmar: Verifica los resultados del test.

## Explanation
## Explicacación

This pattern has several significant benefits. It creates a clear separation between a test's
setup, operations, and results. This structure makes the code easier to read and understand. If
you place the steps in order and format your code to separate them, you can scan a test and
quickly comprehend what it does.
Este patrón tiene varios beneficios significativos. Crea una clara separación entre la configuración, operaciones y resultados de un test. Esta estructura hace que el código sea más fácil de leer y comprender. Si
colocas los pasos en orden y formateas su código para separarlos, puedes escanear un test y
comprender rápidamente lo que hace.

It also enforces a certain degree of discipline when you write your tests. You have to think
clearly about the three steps your test will perform. It makes tests more natural to write at
the same time since you already have an outline.
También impone un cierto grado de disciplina cuando escribes tus UnitTests. Tienes que visualizar
claramente los tres pasos que tu test realizará. Esto hace que los tests sean más intuitivos de escribir a la vez que tienes presente un esquema.

Real world example
Ejemplo cotidiano

> We need to write comprehensive and clear unit test suite for a class.
> Necesitamos escribir un conjunto de UnitTests completo y claro para una clase.

In plain words
En otras palabras

> Arrange/Act/Assert is a testing pattern that organizes tests into three clear steps for easy
> maintenance.
> Arrange/Act/Assert es un patrón de testeo que organiza las pruebas en tres pasos claros para facilitar su
> mantenimiento.

WikiWikiWeb says
WikiWikiWeb dice

> Arrange/Act/Assert is a pattern for arranging and formatting code in UnitTest methods. 
> Arrange/Act/Assert es un patrón para organizar y dar formato al código en los métodos UnitTest.

**Programmatic Example**
**Código de ejemplo**

Let's first introduce our `Cash` class to be unit tested.
Veamo primero nuestra clase `Cash` para que sea testeada.

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

Then we write our unit tests according to Arrange/Act/Assert pattern. Notice the clearly
separated steps for each unit test.
Luego escribimos nuestras UnitTests enfunción del patrón Arrange/Act/Assert. Note claramente la separación de los pasos para cada UnitTest.

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

## Applicability
## Aplicabilidad

Use Arrange/Act/Assert pattern when
Utilice el patrón Arrange/Act/Asert cuando

* You need to structure your unit tests so that they're easier to read, maintain, and enhance. 
* Necesites estructurar tus UnitTests para que sean más fáciles de leer, mantener y mejorar.



## Credits
## Créditos

* [Arrange, Act, Assert: What is AAA Testing?](https://blog.ncrunch.net/post/arrange-act-assert-aaa-testing.aspx)
* [Organizar, Actuar, Afirmar: ¿Qué son las pruebas AAA?](https://blog.ncrunch.net/post/arrange-act-assert-aaa-testing.aspx)
* [Bill Wake: 3A – Arrange, Act, Assert](https://xp123.com/articles/3a-arrange-act-assert/)
* [Bill Wake: 3A – Organizar, Actuar, Afirmar](https://xp123.com/articles/3a-arrange-act-assert/)
* [Martin Fowler: GivenWhenThen](https://martinfowler.com/bliki/GivenWhenThen.html)
* [Martin Fowler: DadoCuandoEntonces](https://martinfowler.com/bliki/GivenWhenThen.html)
* [xUnit Test Patterns: Refactoring Test Code](https://www.amazon.com/gp/product/0131495054/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0131495054&linkId=99701e8f4af2f7e8dd50d720c9b63dbf)
* [Patrones de prueba xUnit: Refactorizando Código de prueba](https://www.amazon.com/gp/product/0131495054/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0131495054&linkId=99701e8f4af2f63d0bcf50)
* [Unit Testing Principles, Practices, and Patterns](https://www.amazon.com/gp/product/1617296279/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617296279&linkId=74c75cf22a63c3e4758ae08aa0a0cc35)
* [Principios, prácticas y patrones UnitTesting](https://www.amazon.com/gp/product/1617296279/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617296279&linkId=74c75cfae3a5a)accae3a5a)
* [Test Driven Development: By Example](https://www.amazon.com/gp/product/0321146530/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321146530&linkId=5c63a93d8c1175b84ca5087472ef0e05)
* [Desarrollo basado en pruebas: Ejemplo](https://www.amazon.com/gp/product/0321146530/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321146530&linkId=5c63a93d8c1175b47caef50875)
