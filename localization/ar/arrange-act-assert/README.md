---
title: Arrange/Act/Assert
shortTitle: Arrange/Act/Assert
category: Idiom
language: ar
tag:
 - Testing
---

## أيضا معروف بـ

Dado/Cuando/Entonces

## الغرض

Arrange/Act/Assert (AAA) هو نمط لتنظيم اختبارات الوحدة.
يقسم اختبارات الوحدة إلى ثلاث خطوات واضحة ومميزة:

1. Arrange(تنظيم): قم بإعداد التهيئة والتهيئة اللازمة للاختبار.
2. Act(العمل): اتخذ التدابير اللازمة للاختبار.
3. Assert(التأكيد): تحقق من نتائج الاختبار.

## الشرح

لهذا النمط العديد من الفوائد الكبيرة. فهو يخلق فصلًا واضحًا بين إعدادات الاختبار، العمليات والنتائج. هذا الهيكل يجعل الكود أسهل في القراءة والفهم. إذا
وضعت الخطوات بالترتيب وصيغت الكود بطريقة تفصلها، يمكنك مسح الاختبار وفهمه بسرعة.

كما أنه يفرض درجة معينة من الانضباط عند كتابة اختبارات الوحدة. يجب أن تكون قادرًا على تصور
بوضوح الخطوات الثلاث التي سيقوم بها اختبارك. هذا يجعل الاختبارات أكثر بديهية للكتابة مع الحفاظ على هيكل واضح.

مثال يومي

> نحتاج إلى كتابة مجموعة من اختبارات الوحدة كاملة وواضحة لفئة.

بعبارة أخرى

> Arrange/Act/Assert هو نمط اختبار ينظم الاختبارات إلى ثلاث خطوات واضحة لتسهيل
> الصيانة.

WikiWikiWeb يقول

> Arrange/Act/Assert هو نمط لتنظيم وتنسيق الكود في طرق اختبار الوحدة.

**كود المثال**

لنلقِ نظرة أولاً على فئتنا `Cash` التي سيتم اختبارها.


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

ثم نكتب اختبارات الوحدة الخاصة بنا بناءً على نمط Arrange/Act/Assert. لاحظ بوضوح الفصل بين الخطوات لكل اختبار وحدة.


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

## القابلية للتطبيق

استخدم نمط Arrange/Act/Assert عندما

* تحتاج إلى تنظيم اختبارات الوحدة الخاصة بك لتكون أسهل في القراءة والصيانة والتحسين.

## الشكر

* [Arrange, Act, Assert: ¿Qué son las pruebas AAA?](https://blog.ncrunch.net/post/arrange-act-assert-aaa-testing.aspx)
* [Bill Wake: 3A – Arrange, Act, Assert](https://xp123.com/articles/3a-arrange-act-assert/)
* [Martin Fowler: DadoCuandoEntonces](https://martinfowler.com/bliki/GivenWhenThen.html)
* [Patrones de prueba xUnit: Refactorizando Código de prueba](https://www.amazon.com/gp/product/0131495054/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0131495054&linkId=99701e8f4af2f63d0bcf50)
* [Principios, prácticas y patrones UnitTesting](https://www.amazon.com/gp/product/1617296279/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617296279&linkId=74c75cfae3a5aaccae3a5a)
* [Desarrollo basado en pruebas: Ejemplo](https://www.amazon.com/gp/product/0321146530/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0321146530&linkId=5c63a93d8c1175b47caef50875)
