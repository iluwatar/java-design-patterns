---
title: Acyclic Visitor
category: Behavioral
language: ur
tag:
 - Extensibility
---

## ارادہ

ان درجہ بندیوں کو متاثر کیے بغیر، اور تخلیق کیے بغیر، موجودہ طبقاتی درجہ بندی میں نئے فنکشنز کو شامل کرنے کی اجازت دیں
پریشان کن انحصار سائیکل جو GoF وزیٹر پیٹرن میں شامل ہیں۔

## وضاحت

حقیقی مثال

> ہمارے پاس موڈیم کلاسز کا درجہ بندی ہے۔ اس درجہ بندی میں موڈیمز کو ایک بیرونی الگورتھم کی بنیاد پر دیکھنے کی ضرورت ہے۔
فلٹرنگ کے معیار پر (کیا یہ یونکس ہے یا DOS کے موافق موڈیم)۔

صاف لفظوں میں

> Acyclic Visitor درجہ بندی میں ترمیم کیے بغیر فنکشنز کو موجودہ طبقاتی درجہ بندی میں شامل کرنے کی اجازت دیتا ہے۔

[WikiWikiWeb](https://wiki.c2.com/?AcyclicVisitor) کہتے ہیں

> Acyclic پیٹرن ان کو درجہ بندی میں فرق صرف موجودہ طبقاتی میں فنکشنز کو شامل کرنے کی اجازت دیتا ہے۔

> درجہ بندی، اور انحصار سائیکل بنائے بغیر جو کہ GangOfFour VisitorPattern میں موروثی ہیں۔

**پروگرامی مثال**

یہ ہے `موڈیم` درجہ بندی۔

```java
public abstract class Modem {
  public abstract void accept(ModemVisitor modemVisitor);
}

public class Zoom extends Modem {
  ...
  @Override
  public void accept(ModemVisitor modemVisitor) {
    if (modemVisitor instanceof ZoomVisitor) {
      ((ZoomVisitor) modemVisitor).visit(this);
    } else {
      LOGGER.info("Only ZoomVisitor is allowed to visit Zoom modem");
    }
  }
}

public class Hayes extends Modem {
  ...
  @Override
  public void accept(ModemVisitor modemVisitor) {
    if (modemVisitor instanceof HayesVisitor) {
      ((HayesVisitor) modemVisitor).visit(this);
    } else {
      LOGGER.info("Only HayesVisitor is allowed to visit Hayes modem");
    }
  }
}
```

اس کے بعد ہم 'ModemVisitor' کا درجہ بندی متعارف کراتے ہیں۔

```java
public interface ModemVisitor {
}

public interface HayesVisitor extends ModemVisitor {
  void visit(Hayes hayes);
}

public interface ZoomVisitor extends ModemVisitor {
  void visit(Zoom zoom);
}

public interface AllModemVisitor extends ZoomVisitor, HayesVisitor {
}

public class ConfigureForDosVisitor implements AllModemVisitor {
  ...
  @Override
  public void visit(Hayes hayes) {
    LOGGER.info(hayes + " used with Dos configurator.");
  }
  @Override
  public void visit(Zoom zoom) {
    LOGGER.info(zoom + " used with Dos configurator.");
  }
}

public class ConfigureForUnixVisitor implements ZoomVisitor {
  ...
  @Override
  public void visit(Zoom zoom) {
    LOGGER.info(zoom + " used with Unix configurator.");
  }
}
```

آخر میں، یہاں ایکشن میں زائرین ہیں.


```java
    var conUnix = new ConfigureForUnixVisitor();
    var conDos = new ConfigureForDosVisitor();
    var zoom = new Zoom();
    var hayes = new Hayes();
    hayes.accept(conDos);
    zoom.accept(conDos);
    hayes.accept(conUnix);
    zoom.accept(conUnix);   
```

پروگرام آؤٹ پٹ:

```
    // Hayes modem used with Dos configurator.
    // Zoom modem used with Dos configurator.
    // Only HayesVisitor is allowed to visit Hayes modem
    // Zoom modem used with Unix configurator.
```

## کلاس ڈایاگرام

![alt text](./etc/acyclic-visitor.png "Acyclic Visitor")

## استعمال

یہ پیٹرن استعمال کیا جا سکتا ہے:

* جب آپ کو کسی موجودہ درجہ بندی میں ایک نیا فنکشن شامل کرنے کی ضرورت ہو بغیر اس درجہ بندی کو تبدیل کرنے یا متاثر کرنے کی ضرورت۔
* جب ایسے افعال ہوتے ہیں جو درجہ بندی پر کام کرتے ہیں، لیکن ان کا تعلق خود درجہ بندی میں نہیں ہوتا ہے۔ جیسے ConfigureForDOS / ConfigureForUnix / ConfigureForX مسئلہ۔
* جب آپ کو کسی چیز پر اس کی قسم کے لحاظ سے بہت مختلف آپریشن کرنے کی ضرورت ہوتی ہے۔
* جب ملاحظہ کردہ کلاس کے درجہ بندی کو عنصر کلاس کے نئے مشتقات کے ساتھ کثرت سے بڑھایا جائے گا۔
* جب عنصر کے مشتقات کو دوبارہ مرتب کرنا، دوبارہ لنک کرنا، دوبارہ جانچنا یا دوبارہ تقسیم کرنا بہت مہنگا ہوتا ہے۔

## ٹیوٹوریل

* [Acyclic Visitor Pattern Example](https://codecrafter.blogspot.com/2012/12/the-acyclic-visitor-pattern.html)

## نتائج

اچھائی:

* طبقاتی درجہ بندی کے درمیان کوئی انحصار سائیکل نہیں ہے۔
* اگر کوئی نیا شامل کیا جائے تو تمام زائرین کو دوبارہ مرتب کرنے کی ضرورت نہیں ہے۔
* اگر کلاس کے درجہ بندی میں کوئی نیا ممبر ہے تو موجودہ زائرین میں تالیف کی ناکامی کا سبب نہیں بنتا ہے۔

برائی:

 * خلاف ورزی کرتا ہے۔ [Liskov's Substitution Principle](https://java-design-patterns.com/principles/#liskov-substitution-principle) 
 یہ ظاہر کر کے کہ یہ تمام زائرین کو قبول کر سکتا ہے لیکن اصل میں صرف خاص مہمانوں میں دلچسپی رکھتا ہے۔
*  ملاحظہ کرنے کے قابل طبقاتی درجہ بندی میں تمام اراکین کے لیے زائرین کا متوازی درجہ بندی بنانا ضروری ہے۔

 ## متعلقہ پیٹرن

 [Visitor Pattern](https://java-design-patterns.com/patterns/visitor/)


 کریڈٹس

 * [Acyclic Visitor by Robert C. Martin](http://condor.depaul.edu/dmumaugh/OOT/Design-Principles/acv.pdf)
* [Acyclic Visitor in WikiWikiWeb](https://wiki.c2.com/?AcyclicVisitor)