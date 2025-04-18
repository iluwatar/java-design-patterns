---
title: "الگوی Abstract Document در جاوا: ساده‌سازی مدیریت داده با انعطاف‌پذیری"
shortTitle: Abstract Document
description: "الگوی طراحی Abstract Document در جاوا را بررسی کنید. با هدف، توضیح، کاربرد، مزایا و نمونه‌های دنیای واقعی برای پیاده‌سازی ساختارهای داده‌ای پویا و انعطاف‌پذیر آشنا شوید."
category: Structural
language: fa
tag:
  - Abstraction
  - Decoupling
  - Dynamic typing
  - Encapsulation
  - Extensibility
  - Polymorphism
---

## هدف الگوی طراحی Abstract Document

الگوی طراحی Abstract Document در جاوا یک الگوی طراحی ساختاری مهم است که راهی یکپارچه برای مدیریت ساختارهای داده‌ای سلسله‌مراتبی و درخت‌ی فراهم می‌کند، با تعریف یک واسط مشترک برای انواع مختلف اسناد. این الگو ساختار اصلی سند را از فرمت‌های خاص داده جدا می‌کند، که باعث به‌روزرسانی پویا و نگهداری ساده‌تر می‌شود.

## توضیح دقیق الگوی Abstract Document با نمونه‌های دنیای واقعی

الگوی طراحی Abstract Document در جاوا امکان مدیریت پویا ویژگی‌های پویا(غیر استاتیک) را فراهم می‌کند. این الگو از مفهوم traits استفاده می‌کند تا ایمنی نوع‌داده (type safety) را فراهم کرده و ویژگی‌های کلاس‌های مختلف را به مجموعه‌ای از واسط‌ها تفکیک کند.

مثال دنیای واقعی

> فرض کنید یک سیستم کتابخانه از الگوی Abstract Document در جاوا استفاده می‌کند، جایی که کتاب‌ها می‌توانند فرمت‌ها و ویژگی‌های متنوعی داشته باشند: کتاب‌های فیزیکی، کتاب‌های الکترونیکی، و کتاب‌های صوتی. هر فرمت ویژگی‌های خاص خود را دارد، مانند تعداد صفحات برای کتاب‌های فیزیکی، حجم فایل برای کتاب‌های الکترونیکی، و مدت‌زمان برای کتاب‌های صوتی. الگوی Abstract Document به سیستم کتابخانه اجازه می‌دهد تا این فرمت‌های متنوع را به‌صورت انعطاف‌پذیر مدیریت کند. با استفاده از این الگو، سیستم می‌تواند ویژگی‌ها را به‌صورت پویا ذخیره و بازیابی کند، بدون نیاز به ساختار سفت و سخت برای هر نوع کتاب، و این کار افزودن فرمت‌ها یا ویژگی‌های جدید را در آینده بدون تغییرات عمده در کد آسان می‌سازد.

به زبان ساده

> الگوی Abstract Document اجازه می‌دهد ویژگی‌هایی به اشیاء متصل شوند بدون اینکه خود آن اشیاء از آن اطلاع داشته باشند.

ویکی‌پدیا می‌گوید

> یک الگوی طراحی ساختاری شی‌ء‌گرا برای سازماندهی اشیاء در کلید-مقدارهایی با تایپ آزاد و ارائه داده‌ها از طریق نمای تایپ است. هدف این الگو دستیابی به انعطاف‌پذیری بالا بین اجزا در یک زبان strongly typed است که در آن بتوان ویژگی‌های جدیدی را به‌صورت پویا به ساختار درختی اشیاء اضافه کرد، بدون از دست دادن پشتیبانی از type safety. این الگو از traits برای جداسازی ویژگی‌های مختلف یک کلاس در اینترفیس‌های متفاوت استفاده می‌کند.

نمودار کلاس

![Abstract Document class diagram](./etc/abstract-document.png "Abstract Document class diagram")

## مثال برنامه‌نویسی از الگوی Abstract Document در جاوا

فرض کنید یک خودرو داریم که از قطعات مختلفی تشکیل شده است. اما نمی‌دانیم آیا این خودرو خاص واقعاً همه قطعات را دارد یا فقط برخی از آن‌ها. خودروهای ما پویا و بسیار انعطاف‌پذیر هستند.

بیایید ابتدا کلاس‌های پایه `Document` و `AbstractDocument` را تعریف کنیم. این کلاس‌ها اساساً یک شیء را قادر می‌سازند تا یک نقشه از ویژگی‌ها و هر تعداد شیء فرزند را نگه دارد.

```java
public interface Document {

    Void put(String key, Object value);

    Object get(String key);

    <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}

public abstract class AbstractDocument implements Document {

    private final Map<String, Object> properties;

    protected AbstractDocument(Map<String, Object> properties) {
        Objects.requireNonNull(properties, "properties map is required");
        this.properties = properties;
    }

    @Override
    public Void put(String key, Object value) {
        properties.put(key, value);
        return null;
    }

    @Override
    public Object get(String key) {
        return properties.get(key);
    }

    @Override
    public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
        return Stream.ofNullable(get(key))
                .filter(Objects::nonNull)
                .map(el -> (List<Map<String, Object>>) el)
                .findAny()
                .stream()
                .flatMap(Collection::stream)
                .map(constructor);
    }
    
    // Other properties and methods...
}
```
در ادامه، یک enum به نام Property و مجموعه‌ای از واسط‌ها برای type، price، model و parts تعریف می‌کنیم. این کار به ما اجازه می‌دهد یک واسط با ظاهر استاتیک برای کلاس Car ایجاد کنیم.
```java
public enum Property {

    PARTS, TYPE, PRICE, MODEL
}

public interface HasType extends Document {

    default Optional<String> getType() {
        return Optional.ofNullable((String) get(Property.TYPE.toString()));
    }
}

public interface HasPrice extends Document {

    default Optional<Number> getPrice() {
        return Optional.ofNullable((Number) get(Property.PRICE.toString()));
    }
}

public interface HasModel extends Document {

    default Optional<String> getModel() {
        return Optional.ofNullable((String) get(Property.MODEL.toString()));
    }
}

public interface HasParts extends Document {

    default Stream<Part> getParts() {
        return children(Property.PARTS.toString(), Part::new);
    }
}

public class Part extends AbstractDocument implements HasType, HasModel, HasPrice {

    public Part(Map<String, Object> properties) {
        super(properties);
    }
}
```
اکنون آماده‌ایم تا کلاس Car را معرفی کنیم.
```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

    public Car(Map<String, Object> properties) {
        super(properties);
    }
}
```
و در نهایت، نحوه ساخت و استفاده از Car را در یک مثال کامل می‌بینید.
```java
  public static void main(String[] args) {
    LOGGER.info("Constructing parts and car");

    var wheelProperties = Map.of(
            Property.TYPE.toString(), "wheel",
            Property.MODEL.toString(), "15C",
            Property.PRICE.toString(), 100L);

    var doorProperties = Map.of(
            Property.TYPE.toString(), "door",
            Property.MODEL.toString(), "Lambo",
            Property.PRICE.toString(), 300L);

    var carProperties = Map.of(
            Property.MODEL.toString(), "300SL",
            Property.PRICE.toString(), 10000L,
            Property.PARTS.toString(), List.of(wheelProperties, doorProperties));

    var car = new Car(carProperties);

    LOGGER.info("Here is our car:");
    LOGGER.info("-> model: {}", car.getModel().orElseThrow());
    LOGGER.info("-> price: {}", car.getPrice().orElseThrow());
    LOGGER.info("-> parts: ");
    car.getParts().forEach(p -> LOGGER.info("\t{}/{}/{}",
            p.getType().orElse(null),
            p.getModel().orElse(null),
            p.getPrice().orElse(null))
    );
}
```
خروجی برنامه:
```
07:21:57.391 [main] INFO com.iluwatar.abstractdocument.App -- Constructing parts and car
07:21:57.393 [main] INFO com.iluwatar.abstractdocument.App -- Here is our car:
07:21:57.393 [main] INFO com.iluwatar.abstractdocument.App -- -> model: 300SL
07:21:57.394 [main] INFO com.iluwatar.abstractdocument.App -- -> price: 10000
07:21:57.394 [main] INFO com.iluwatar.abstractdocument.App -- -> parts: 
07:21:57.395 [main] INFO com.iluwatar.abstractdocument.App -- 	wheel/15C/100
07:21:57.395 [main] INFO com.iluwatar.abstractdocument.App -- 	door/Lambo/300
```

 ### چه زمانی از الگوی Abstract Document در جاوا استفاده کنیم؟

الگوی طراحی Abstract Document به‌ویژه در سناریوهایی مفید است که نیاز به مدیریت انواع مختلفی از اسناد در جاوا وجود دارد که برخی ویژگی‌ها یا رفتارهای مشترک دارند، ولی ویژگی‌ها یا رفتارهای خاص خود را نیز دارند. در ادامه چند سناریوی مناسب برای این الگو آورده شده است:

* سیستم‌های مدیریت محتوا (CMS): ممکن است انواع مختلفی از محتوا مانند مقاله، تصویر، ویدئو و... وجود داشته باشد. هر نوع محتوا ویژگی‌های مشترکی مثل تاریخ ایجاد، نویسنده و تگ‌ها دارد، ولی همچنین ویژگی‌های خاصی مثل ابعاد تصویر یا مدت‌زمان ویدئو.

*    سیستم‌های فایل: اگر یک سیستم فایل طراحی می‌کنید که باید انواع مختلف فایل مانند اسناد، تصاویر، فایل‌های صوتی و دایرکتوری‌ها را مدیریت کند، این الگو می‌تواند راهی یکپارچه برای دسترسی به ویژگی‌هایی مانند اندازه فایل یا تاریخ ایجاد، فراهم کند و در عین حال ویژگی‌های خاص هر نوع فایل را هم مدیریت کند.

*    سیستم‌های تجارت الکترونیک: یک پلتفرم فروش آنلاین ممکن است محصولات مختلفی داشته باشد مثل محصولات فیزیکی، فایل‌های دیجیتال، و اشتراک‌ها. این محصولات ویژگی‌هایی مثل نام، قیمت و توضیح را به اشتراک می‌گذارند، ولی ویژگی‌های خاصی هم دارند مانند وزن حمل برای محصولات فیزیکی یا لینک دانلود برای دیجیتال‌ها.

*    سیستم‌های سوابق پزشکی: در مراقبت سلامت، پرونده بیماران ممکن است داده‌های مختلفی مثل مشخصات فردی، سوابق پزشکی، نتایج آزمایش‌ها و نسخه‌ها را شامل شود. این الگو می‌تواند ویژگی‌های مشترک مثل شماره بیمار یا تاریخ تولد را مدیریت کند و هم‌زمان ویژگی‌های خاصی مثل نتایج آزمایش یا داروهای تجویزی را هم پوشش دهد.

*    مدیریت پیکربندی: هنگام کار با تنظیمات پیکربندی نرم‌افزار، ممکن است انواع مختلفی از عناصر پیکربندی وجود داشته باشد، هر یک با ویژگی‌های خاص خود. این الگو می‌تواند برای مدیریت این عناصر مفید باشد.

*    پلتفرم‌های آموزشی: سیستم‌های آموزشی ممکن است انواع مختلفی از منابع یادگیری داشته باشند مثل محتوای متنی، ویدیوها، آزمون‌ها و تمرین‌ها. ویژگی‌های مشترکی مثل عنوان، نویسنده و تاریخ انتشار وجود دارد، ولی ویژگی‌های خاصی مانند مدت ویدیو یا مهلت تحویل تمرین نیز ممکن است وجود داشته باشد.

*    ابزارهای مدیریت پروژه: در برنامه‌های مدیریت پروژه، ممکن است انواع مختلفی از وظایف مانند آیتم‌های to-do، milestoneها و issueها داشته باشید. این الگو می‌تواند برای مدیریت ویژگی‌های عمومی مانند نام وظیفه و مسئول آن استفاده شود و در عین حال ویژگی‌های خاص مانند تاریخ milestone یا اولویت issue را نیز پوشش دهد.

*    اسناد ساختار ویژگی‌های متنوع و در حال تحول دارند.

*    افزودن ویژگی‌های جدید به‌صورت پویا یک نیاز رایج است.

*    جداسازی دسترسی به داده از فرمت‌های خاص حیاتی است.

*    نگهداری‌پذیری و انعطاف‌پذیری برای کد اهمیت دارد.

ایده اصلی پشت الگوی Abstract Document فراهم کردن روشی انعطاف‌پذیر و قابل توسعه برای مدیریت انواع مختلف اسناد یا موجودیت‌ها با ویژگی‌های مشترک و خاص است. با تعریف یک واسط مشترک و پیاده‌سازی آن در انواع مختلف اسناد، می‌توان به شیوه‌ای منظم و یکپارچه برای مدیریت ساختارهای پیچیده داده دست یافت.
### مزایا و معایب الگوی Abstract Document
<div dir="rtl">
مزایا:

*    انعطاف‌پذیری: پشتیبانی از ساختارهای متنوع اسناد و ویژگی‌ها.

*    قابلیت توسعه: افزودن ویژگی‌های جدید بدون شکستن کد موجود.

*    نگهداری‌پذیری: ارتقاء کد تمیز و قابل تطبیق به‌واسطه جداسازی وظایف.

*    قابلیت استفاده مجدد: نمای دید تایپ‌شده باعث استفاده مجدد از کد برای دسترسی به نوع خاصی از ویژگی می‌شود.

معایب:

*    پیچیدگی: نیاز به تعریف واسط‌ها و نماها، که باعث اضافه شدن سربار پیاده‌سازی می‌شود.

*    کارایی: ممکن است سربار کمی نسبت به دسترسی مستقیم به داده داشته باشد.
</div>

منابع و اعتبارها

*    [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)

*    [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)

*    [Pattern-Oriented Software Architecture Volume 4: A Pattern Language for Distributed Computing (v. 4)] (https://amzn.to/49zRP4R)

*    [Patterns of Enterprise Application Architecture] (https://amzn.to/3WfKBPR)

*    [Abstract Document Pattern (Wikipedia)] (https://en.wikipedia.org/wiki/Abstract_Document_Pattern)

*    [Dealing with Properties (Martin Fowler)] (http://martinfowler.com/apsupp/properties.pdf)
