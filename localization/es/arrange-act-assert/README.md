---
title: Arrange/Act/Assert
category: Idiom
language: es
tag:
 - Testing
---

## También conocido como

Dado/Cuando/Entonces

## Propósito

Arrange/Act/Assert (AAA) es un patrón para organizar UnitTests.
Divide las UnitTests en tres pasos claros y diferenciados:

1. Arrange(Organizar): Realiza la configuración y la inicialización necesarias para el test.
2. Act(Actuar): Toma las medidas necesarias para el test.
3. Assert(Afirmar): Verifica los resultados del test.

## Explicación

Este patrón tiene varios beneficios significativos. Crea una clara separación entre la configuración, operaciones y resultados de un test. Esta estructura hace que el código sea más fácil de leer y comprender. Si
colocas los pasos en orden y formateas su código para separarlos, puedes escanear un test y
comprender rápidamente lo que hace.

También impone un cierto grado de disciplina cuando escribes tus UnitTests. Tienes que visualizar
claramente los tres pasos que tu test realizará. Esto hace que los tests sean más intuitivos de escribir a la vez que tienes presente un esquema.

Ejemplo cotidiano

> Necesitamos escribir un conjunto de UnitTests completo y claro para una clase.

En otras palabras

> Arrange/Act/Assert es un patrón de testeo que organiza las pruebas en tres pasos claros para facilitar su
> mantenimiento.

WikiWikiWeb dice

> Arrange/Act/Assert es un patrón para organizar y dar formato al código en los métodos UnitTest.

**Código de ejemplo**

Veamos primero nuestra clase `Cash` para que sea testeada.

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

Luego escribimos nuestras UnitTests en función del patrón Arrange/Act/Assert. Note claramente la separación de los pasos para cada UnitTest.

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

## Aplicabilidad

Utilice el patrón Arrange/Act/Assert cuando

* Necesitas estructurar tus UnitTests para que sean más fáciles de leer, mantener y mejorar.

## Créditos

* [Arrange, Act, Assert: ¿Qué son las pruebas AAA?](https://blog.ncrunch.net/post/arrange-act-assert-aaa-testing.aspx)
* [Bill Wake: 3A – Arrange, Act, Assert](https://xp123.com/articles/3a-arrange-act-assert/)
* [Martin Fowler: DadoCuandoEntonces](https://martinfowler.com/bliki/GivenWhenThen.html)
* [Patrones de prueba xUnit: Refactorizando Código de prueba](https://www.amazon.com/gp/product/0131495054/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0131495054&linkId=99701e8f4af2f63d0bcf50)
* [Principios, prácticas y patrones UnitTesting](https://www.amazon.com/gp/product/1617296279/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617296279&linkId=74c75cfae3a5aaccae3a5a)
* [Desarrollo basado en pruebas: Ejemplo](https://www.amazon.com/gp/product/0321146530/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321146530&linkId=5c63a93d8c1175b47caef50875)
