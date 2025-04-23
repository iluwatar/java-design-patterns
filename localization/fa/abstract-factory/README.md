---
title: "الگوی طراحی Abstract Factory در جاوا: مهارت در ایجاد شیء با ظرافت"
shortTitle: Abstract Factory
description: "با مثال‌های دنیای واقعی، دیاگرام‌های کلاس و آموزش‌ها، الگوی Abstract Factory را در جاوا بیاموزید. منظور، کاربرد، مزایا و نمونه‌های واقعی آن را درک کنید و دانش طراحی الگوهایتان را افزایش دهید."
category: Creational
language: fa
tag:
  - Abstraction
  - Decoupling
  - Gang of Four
  - Instantiation
  - Polymorphism
---

## همچنین به عنوان

* کیت

## هدف از الگوی طراحی Abstract Factory

الگوی Abstract Factory در جاوا یک واسط برای ایجاد خانواده‌هایی از اشیای مرتبط یا وابسته فراهم می‌کند بدون آنکه کلاس‌های مشخص آن‌ها را تعیین کند، و این کار موجب افزایش مدولاریتی و انعطاف‌پذیری در طراحی نرم‌افزار می‌شود.

## توضیح دقیق الگوی Abstract Factory با مثال‌های دنیای واقعی

مثال دنیای واقعی

> تصور کنید یک شرکت مبلمان وجود دارد که از الگوی Abstract Factory در جاوا برای تولید سبک‌های مختلف مبلمان استفاده می‌کند: مدرن، ویکتوریایی و روستایی. هر سبک شامل محصولاتی مانند صندلی‌ها، میزها و کاناپه‌ها است. برای اطمینان از یکنواختی در هر سبک، شرکت از یک الگوی Abstract Factory استفاده می‌کند.
> 
> در این سناریو، Abstract Factory یک واسط برای ایجاد خانواده‌هایی از اشیای مبلمان مرتبط (صندلی‌ها، میزها، کاناپه‌ها) است. هر Factory مشخص (کارخانه‌ی مبلمان مدرن، کارخانه‌ی مبلمان ویکتوریایی، کارخانه‌ی مبلمان روستایی) این واسط را پیاده‌سازی می‌کند و مجموعه‌ای از محصولات مطابق با سبک خاص ایجاد می‌کند. به این ترتیب، مشتریان می‌توانند یک مجموعه کامل از مبلمان مدرن یا ویکتوریایی ایجاد کنند بدون اینکه نگران جزئیات ساخت آن‌ها باشند. این باعث حفظ یکنواختی سبک می‌شود و امکان تغییر آسان سبک مبلمان را فراهم می‌کند.

به زبان ساده

> کارخانه‌ای از کارخانه‌ها؛ یک Factory یا کارخانه که مجموعه‌ای از کارخانه‌های مرتبط یا وابسته را بدون مشخص کردن کلاس‌های concrete آن‌ها گروه‌بندی می‌کند.

ویکی‌پدیا می‌گوید

> الگوی Abstract Factory راهی برای کپسوله کردن مجموعه‌ای از کارخانه‌های منحصر به فرد با یک تم مشترک بدون تعیین کلاس‌های concrete آن‌ها فراهم می‌کند.

دیاگرام کلاس

![Abstract Factory class diagram](./etc/abstract-factory.urm.png "Abstract Factory class diagram")

## مثال برنامه‌نویسی از Abstract Factory در جاوا

برای ایجاد یک پادشاهی با استفاده از الگوی Abstract Factory در جاوا، ما به اشیایی با یک تم مشترک نیاز داریم. یک پادشاهی اِلف (Elf) به یک پادشاه اِلف، یک قلعه‌ی اِلف، و یک ارتش اِلف نیاز دارد، در حالی که یک پادشاهی اورک (Orc) به یک پادشاه اورک، یک قلعه‌ی اورک، و یک ارتش اورک نیاز دارد. بین اشیای موجود در پادشاهی وابستگی وجود دارد.

ترجمه‌ی مثال پادشاهی بالا. ابتدا ما برخی واسط‌ها و پیاده‌سازی‌هایی برای اشیای موجود در پادشاهی داریم:

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

سپس واسط و پیاده‌سازی‌های کارخانه‌ی پادشاهی را داریم:

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

// Orcish implementations similarly -> ...
```

اکنون می‌توانیم یک کارخانه برای کارخانه‌های مختلف پادشاهی طراحی کنیم. در این مثال، ما `FactoryMaker` را ایجاد کردیم که مسئول برگرداندن یک نمونه از `ElfKingdomFactory` یا `OrcKingdomFactory` است. مشتری می تواند از `FactoryMaker` برای ایجاد کارخانه concrete مورد نظر استفاده کند که به نوبه خود اشیاء concrete مختلف (مشتق شده از ارتش، پادشاه، قلعه) را تولید می‌کند. در این مثال، ما همچنین از یک enum برای پارامتری کردن نوع کارخانه پادشاهی که مشتری درخواست خواهد کرد استفاده کردیم.

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

نمونه‌ای از تابع اصلی برنامه:

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

خروجی برنامه:

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

## چه زمانی باید از الگوی Abstract Factory در جاوا استفاده کرد؟

* زمانی که سیستم باید مستقل از نحوه‌ی ایجاد، ترکیب و نمایش محصولاتش باشد.
* زمانی که نیاز به پیکربندی سیستم با یکی از چند خانواده محصول دارید.
* زمانی که باید خانواده‌ای از اشیای مرتبط با هم استفاده شوند، برای اطمینان از یکنواختی.
* زمانی که می‌خواهید کتابخانه‌ای از محصولات را فراهم کنید و فقط واسط‌های آن‌ها را نمایان کنید، نه پیاده‌سازی‌ها را.
* زمانی که طول عمر وابستگی‌ها کوتاه‌تر از مصرف‌کننده باشد.
* زمانی که نیاز به ساخت وابستگی‌ها با مقادیر یا پارامترهای زمان اجرا باشد.
* زمانی که باید در زمان اجرا انتخاب کنید که کدام خانواده از محصول را استفاده کنید.
* زمانی که افزودن محصولات یا خانواده های جدید نباید نیاز به تغییر در کد موجود داشته باشد.

## آموزش‌های الگوی Abstract Factory در جاوا

* [Abstract Factory Design Pattern in Java (DigitalOcean)](https://www.digitalocean.com/community/tutorials/abstract-factory-design-pattern-in-java)
* [Abstract Factory (Refactoring Guru)](https://refactoring.guru/design-patterns/abstract-factory)

## مزایا و معایب الگوی Abstract Factory

مزایا:

* انعطاف‌پذیری: به راحتی می‌توان خانواده‌های محصول را تعویض کرد بدون تغییر کد.

* جداسازی (Decoupling): کد مشتری فقط با واسط‌های انتزاعی کار می‌کند که باعث قابلیت حمل و نگهداری می‌شود.

* قابلیت استفاده مجدد: کارخانه‌های انتزاعی و محصولات امکان استفاده مجدد از مؤلفه‌ها را فراهم می‌کنند.

* قابلیت نگهداری: تغییرات در خانواده‌های محصول محلی شده و به‌روزرسانی را ساده‌تر می‌کند.

معایب:

* پیچیدگی: تعریف واسط‌های انتزاعی و کارخانه‌های مشخص سربار اولیه ایجاد می‌کند.

* غیرمستقیم بودن: کد مشتری از طریق کارخانه‌ها با محصولات کار می‌کند که ممکن است شفافیت را کاهش دهد.

## نمونه‌های واقعی استفاده از الگوی Abstract Factory در جاوا

* کلاس‌های `LookAndFeel` در Java Swing برای ارائه گزینه های مختلف look-and-feel
* پیاده‌سازی‌های مختلف در Java AWT برای ایجاد اجزای مختلف GUI
* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
* [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
* [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)

## الگوهای طراحی مرتبط با جاوا

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): الگوی کارخانه‌ی انتزاعی از روش‌های کارخانه‌ای برای ایجاد محصولات استفاده می‌کند.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): کلاس‌های کارخانه‌ی انتزاعی اغلب به صورت Singleton پیاده‌سازی می‌شوند.
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/): مشابه کارخانه‌ی انتزاعی اما بر پیکربندی و مدیریت مجموعه‌ای از اشیای مرتبط تمرکز دارد.

## منابع و ارجاعات

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Design Patterns in Java](https://amzn.to/3Syw0vC)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3HWNf4U)
