---
title: Abstract Document
category: Structural
language: ur
tag: 
 - Extensibility
---

## ارادہ

متحرک خصوصیات کا استعمال کریں اور ٹائپ سیفٹی کو برقرار رکھتے ہوئے غیر ٹائپ شدہ زبانوں کی لچک حاصل کریں۔

## وضاحت

خلاصہ دستاویز کا پیٹرن اضافی، غیر جامد خصوصیات کو سنبھالنے کے قابل بناتا ہے۔ یہ پیٹرن قسم کی حفاظت اور مختلف کلاسوں کی الگ الگ خصوصیات کو انٹرفیس کے سیٹ میں فعال کرنے کے لیے خصلتوں کے تصور کا استعمال کرتا ہے۔

حقیقی مثال

> ایک ایسی کار پر غور کریں جو متعدد حصوں پر مشتمل ہو۔ تاہم ہم نہیں جانتے کہ آیا مخصوص کار میں واقعی تمام پرزے ہیں یا ان میں سے کچھ ۔ ہماری کاریں متحرک اور انتہائی لچکدار ہیں۔

صاف لفظوں میں

> خلاصہ دستاویز کا پیٹرن اشیاء کے بارے میں جانے بغیر خصوصیات کو منسلک کرنے کی اجازت دیتا ہے۔

ویکیپیڈیا کہتا ہے۔

> ڈھیلے ٹائپ شدہ کلیدی قدر والے اسٹورز میں اشیاء کو منظم کرنے اور ٹائپ شدہ نظاروں کا استعمال کرتے ہوئے ڈیٹا کو ظاہر کرنے کے لیے آبجیکٹ پر مبنی ساختی ڈیزائن کا نمونہ۔ پیٹرن کا مقصد اجزاء کے درمیان اعلی درجے کی لچک حاصل کرنا ہے۔
سختی سے ٹائپ کی گئی زبان میں جہاں قسم کی حفاظت کی حمایت کو کھونے کے بغیر، فلائی پر آبجیکٹ ٹری میں نئی خصوصیات شامل کی جا سکتی ہیں۔پیٹرن کلاس کی مختلف خصوصیات کو مختلف انٹرفیس میں الگ کرنے کے لیے خصلتوں کا استعمال کرتا ہے۔

**پروگرامی مثال**

آئیے پہلے بیس کلاسز `Document` اور `AbstractDocument` کی وضاحت کرتے ہیں۔ 
وہ بنیادی طور پر آبجیکٹ کو پراپرٹی کا نقشہ اور کسی بھی مقدار میں چائلڈ آبجیکٹ رکھتے ہیں۔

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
  ...
}
```
اس کے بعد ہم ایک enum `Property` اور type, price, model اور parts کے لئے انٹرفیس کا ایک سیٹ بیان کرتے ہیں۔ یہ ہمیں تخلیق کرنے کی اجازت دیتا ہے۔
ہماری `Car` کلاس میں جامد نظر آنے والا انٹرفیس۔

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
```
اب ہم 'کار' متعارف کرانے کے لیے تیار ہیں۔

```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

  public Car(Map<String, Object> properties) {
    super(properties);
  }
}
```

اور آخر کار یہ ہے کہ ہم ایک مکمل مثال میں 'کار' کو کیسے بناتے اور استعمال کرتے ہیں۔

```java
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

    
```

## کلاس ڈایاگرام

![alt text](./etc/abstract-document.png "Abstract Document Traits and Domain")

## استعمال 

خلاصہ دستاویز کا پیٹرن استعمال کریں جب -

* نئ  پراپرٹیز آن دی فلائی شامل کرنے کی ضرورت ہے۔
* آپ ڈھانچے جیسے درخت میں ڈومین کو منظم کرنے کا ایک لچکدار طریقہ چاہتے ہیں۔
* آپ کو زیادہ ڈھیلا ڈھالا نظام چاہتے ہیں۔

## کریڈٹ

* [Wikipedia: Abstract Document Pattern](https://en.wikipedia.org/wiki/Abstract_Document_Pattern)
* [Martin Fowler: Dealing with properties](http://martinfowler.com/apsupp/properties.pdf)
* [Pattern-Oriented Software Architecture Volume 4: A Pattern Language for Distributed Computing (v. 4)](https://www.amazon.com/gp/product/0470059028/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0470059028&linkId=e3aacaea7017258acf184f9f3283b492)