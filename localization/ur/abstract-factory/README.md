---
title: Abstract Factory
category: Creational
language: ur
tag:
 - Gang of Four
---

## اس نام سے بہی جانا جاتاہے

کٹ

## ارادہ
متعلقہ یا منحصر خاندانوں کو بنانے کے لیے ایک انٹرفیس فراہم کریں۔
اشیاء کو ان کی ٹھوس کلاسوں کی وضاحت کیے بغیر۔

## وضاحت

حقیقی مثال

> ایک سلطنت بنانے کے لیے ہمیں ایک مشترکہ تھیم والی اشیاء کی ضرورت ہے۔ ایلوین بادشاہی کو ایلون بادشاہ، ایلون قلعہ، اور ایلوین فوج کی ضرورت ہوتی ہے جبکہ آرکیش بادشاہی کو ایک اورش بادشاہ، اورکش قلعہ، اور آرکیش فوج کی ضرورت ہوتی ہے۔ بادشاہی میں اشیاء کے درمیان انحصار ہے۔

صاف لفظوں میں

> کارخانوں کا ایک کارخانہ؛ ایک فیکٹری جو انفرادی لیکن متعلقہ/انحصار کارخانوں کو ان کی کنکریٹ کلاسز کی وضاحت کیے بغیر ایک ساتھ گروپ کرتی ہے۔

ویکیپیڈیا کہتا ہے۔

> تجریدی فیکٹری پیٹرن انفرادی فیکٹریوں کے ایک گروپ کو سمیٹنے کا ایک طریقہ فراہم کرتا ہے جن کی کنکریٹ کلاسز کی وضاحت کیے بغیر ایک مشترکہ تھیم ہے۔

**پروگرامی مثال**

اوپر کی بادشاہی کی مثال کا ترجمہ کرنا۔ سب سے پہلے، ہمارے پاس بادشاہی میں موجود اشیاء کے لیے کچھ انٹرفیس اور نفاذ ہیں۔

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

پھر ہمارے پاس کنگڈم فیکٹری کے لیے تجرید اور نفاذات ہیں۔

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

اب ہمارے پاس ایک تجریدی کارخانہ ہے جو ہمیں متعلقہ اشیاء کا ایک خاندان بنانے دیتا ہے یعنی ایلون کنگڈم فیکٹری ایلوین قلعہ، بادشاہ اور فوج وغیرہ بناتی ہے۔

```java
var factory = new ElfKingdomFactory();
var castle = factory.createCastle();
var king = factory.createKing();
var army = factory.createArmy();

castle.getDescription();
king.getDescription();
army.getDescription();
```

پروگرام آؤٹ پٹ:

```java
This is the elven castle!
This is the elven king!
This is the elven Army!
```
اب، ہم اپنی مختلف بادشاہی فیکٹریوں کے لیے ایک فیکٹری ڈیزائن کر سکتے ہیں۔ اس مثال میں، ہم نے `FactoryMaker` بنایا، جو کہ `ElfKingdomFactory` یا `OrcKingdomFactory` کی مثال واپس کرنے کے لئے ذمہ دار ہے۔
کلائنٹ مطلوبہ کنکریٹ فیکٹری بنانے کے لیے 'فیکٹری میکر' کا استعمال کر سکتا ہے جو بدلے میں مختلف کنکریٹ اشیاء ('آرمی'، 'کنگ'، 'کیسل' سے ماخوذ) تیار کرے گا۔
اس مثال میں، ہم نے پیرامیٹرائز کرنے کے لیے ایک اینوم بھی استعمال کیا کہ کلائنٹ کس قسم کی بادشاہی فیکٹری طلب کرے گا۔

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

## کلاس ڈایاگرام

![alt text](./etc/abstract-factory.urm.png "Abstract Factory class diagram")

## استعمال

خلاصہ فیکٹری پیٹرن کا استعمال کریں جب.

* نظام کو اس بات سے آزاد ہونا چاہئے کہ اس کی مصنوعات کی تخلیق، تشکیل اور نمائندگی کیسے کی جاتی ہے۔
* سسٹم کو مصنوعات کے متعدد خاندانوں میں سے ایک کے ساتھ ترتیب دیا جانا چاہئے۔
* متعلقہ مصنوعات کی اشیاء کے خاندان کو ایک ساتھ استعمال کرنے کے لیے ڈیزائن کیا گیا ہے، اور آپ کو اس پابندی کو نافذ کرنے کی ضرورت ہے۔
* آپ مصنوعات کی کلاس لائبریری فراہم کرنا چاہتے ہیں، اور آپ صرف ان کے انٹرفیس کو ظاہر کرنا چاہتے ہیں، ان کے نفاذ کو نہیں۔
* انحصار کی زندگی تصوراتی طور پر صارف کی زندگی سے کم ہوتی ہے۔
* کسی خاص انحصار کی تعمیر کے لیے آپ کو رن ٹائم ویلیو کی ضرورت ہے۔
* آپ فیصلہ کرنا چاہتے ہیں کہ رن ٹائم کے وقت فیملی سے کس پروڈکٹ کو کال کرنا ہے۔
* انحصار کو حل کرنے سے پہلے آپ کو ایک یا زیادہ پیرامیٹرز فراہم کرنے کی ضرورت ہے جو صرف رن ٹائم پر معلوم ہوتے ہیں۔
* جب آپ کو مصنوعات کے درمیان مستقل مزاجی کی ضرورت ہو۔
* پروگرام میں نئی مصنوعات یا مصنوعات کے خاندانوں کو شامل کرتے وقت آپ موجودہ کوڈ کو تبدیل نہیں کرنا چاہتے۔

 مثال کے طور پر استعمال کے معاملات

 * رن ٹائم پر FileSystemAcmeService یا DatabaseAcmeService یا NetworkAcmeService کے مناسب نفاذ کے لیے کال کرنے کا انتخاب کرنا۔
 * یونٹ ٹیسٹ کیس لکھنا بہت آسان ہو جاتا ہے۔
 * مختلف OS کے لیے UI ٹولز
 ## نتائج

 * جاوا میں انحصار کا انجیکشن سروس کلاس کے انحصار کو چھپاتا ہے جو رن ٹائم کی غلطیوں کا باعث بن سکتا ہے جو مرتب وقت پر پکڑا جاتا۔
* اگرچہ پہلے سے طے شدہ اشیاء بناتے وقت پیٹرن بہت اچھا ہوتا ہے، نئی چیزوں کو شامل کرنا مشکل ہوسکتا ہے۔
* کوڈ اس سے کہیں زیادہ پیچیدہ ہو جاتا ہے کیونکہ پیٹرن کے ساتھ بہت سارے نئے انٹرفیس اور کلاسز متعارف کرائے جاتے ہیں۔

## سبق

* [Abstract Factory Pattern Tutorial](https://www.journaldev.com/1418/abstract-factory-design-pattern-in-java) 

## معروف استعمال

* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
* [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
* [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)

## متعلقہ پیٹرن

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/)
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)

## کریڈٹس

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)