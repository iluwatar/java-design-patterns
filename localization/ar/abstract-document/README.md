---
title: Abstract Document
shortTitle: Abstract Document
category: Structural
language: ar
tag:
  - Extensibility
---


## الهدف

استخدام الخصائص الديناميكية والحصول على مرونة اللغات غير المتغيرة مع الحفاظ على أمان الأنواع.

## التوضيح

يتيح استخدام نمط الوثيقة المجردة إدارة الخصائص غير الثابتة الإضافية. يستخدم هذا النمط مفهوم 
السمات لتمكين أمان الأنواع وخصائص مفصولة من فئات مختلفة في مجموعة من الواجهات.

مثال من العالم الحقيقي

> خذ على سبيل المثال سيارة مكونة من العديد من الأجزاء. ومع ذلك، لا نعرف إذا كانت السيارة تحتوي على جميع الأجزاء أو جزء منها فقط. سياراتنا ديناميكية ومرنة للغاية.

بصيغة أخرى

> يسمح نمط الوثيقة المجردة بإضافة خصائص إلى الكائنات دون أن تكون هذه الكائنات على دراية بذلك.

حسب ويكيبيديا

> نمط تصميم هيكلي موجه للكائنات لتنظيم الكائنات في حاويات من نوع مفتاح-قيمة بشكل فضفاض مع نوعية غير محددة، وكشف البيانات باستخدام طرق عرض مهيكلة. الهدف من هذا النمط هو تحقيق درجة عالية من المرونة بين المكونات في لغة قوية النوع حيث يمكن إضافة خصائص جديدة إلى شجرة الكائنات أثناء العمل دون فقدان دعم أمان الأنواع. يستخدم النمط السمات لفصل خصائص مختلفة للفئة إلى واجهات متعددة.

**مثال برمجي**

أولاً، دعونا نعرف الفئات الأساسية `Document` و `AbstractDocument`. في الأساس، تجعل الكائن يحتوي على خريطة من الخصائص وأي عدد من الكائنات الفرعية.


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

بعد ذلك، نعرف `enum` لـ `Property` ومجموعة من الواجهات للنمط، السعر، النموذج، والأجزاء. هذا يتيح لنا إنشاء واجهات تظهر بشكل ثابت لفئة `Car`.


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

Ahora estamos listos para introducir el Coche `Car`.

```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

  public Car(Map<String, Object> properties) {
    super(properties);
  }
}
```

وأخيرًا، هكذا نبني ونستخدم السيارة `Car` في مثال كامل.


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

    // Constructing parts and car
    // Here is our car:
    // model: 300SL
    // price: 10000
    // parts: 
    // wheel/15C/100
    // door/Lambo/300
```

## Diagrama de clases

![alt text](./etc/abstract-document.png "Abstract Document Traits and Domain")

## التطبيق

استخدم نمط الوثيقة المجردة عندما:

* يوجد حاجة لإضافة خصائص أثناء العمل.
* ترغب في طريقة مرنة لتنظيم النطاق في هيكل مشابه لشجرة.
* ترغب في نظام أقل ترابطًا.

## الحقوق


* [Wikipedia: Abstract Document Pattern](https://en.wikipedia.org/wiki/Abstract_Document_Pattern)
* [Martin Fowler: Dealing with properties](http://martinfowler.com/apsupp/properties.pdf)
* [Pattern-Oriented Software Architecture Volume 4: A Pattern Language for Distributed Computing (v. 4)](https://www.amazon.com/gp/product/0470059028/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0470059028&linkId=e3aacaea7017258acf184f9f3283b492)