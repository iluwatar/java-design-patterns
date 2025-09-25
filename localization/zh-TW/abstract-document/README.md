---
title: Abstract Document
shortTitle: Abstract Document
category: Structural
language: zh-TW
tag: 
 - Extensibility
---

## 目的

使用動態屬性，並在保持型別安全的同時實現非型別化語言的靈活性。

## 解釋

抽象文件模式 (Abstract Document Pattern) 使您能夠處理其他非靜態屬性。此模式使用特性 (trait) 的概念來實現型別安全，並將不同類別的屬性分離為一組介面。

真實世界範例

>  考慮由多個部分組成的汽車。但是，我們不知道特定汽車是否真的擁有所有零件，或者僅僅是零件中的一部分。我們的汽車是動態而且非常靈活的。

簡單的說

> 抽象文件模式允許在物件不知道的情況下將屬性附加到物件上。

維基百科說

> 物件導向的結構設計模式，用於組織鬆散型別的鍵值儲存中的物件，並使用型別化的視圖公開資料。該模式的目的是在強型別語言中實現元件之間的高度靈活性，在這種語言中，可以在不遺失型別安全支援的情況下，將新屬性動態地新增到物件樹中。該模式利用特性 (trait) 將類別的不同屬性分成不同的介面。

**程式碼範例**

讓我們先定義基礎類別`Document`和`AbstractDocument`。 它們基本上使物件擁有屬性對映 (map) 和任意數量的子物件。

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
接下來，我們定義一個列舉 `Property` 和一組關於型別、價格、模型和零件的介面。這使我們能夠為 `Car` 類別創建靜態外觀的介面。

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

現在我們準備介紹`Car`。

```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

  public Car(Map<String, Object> properties) {
    super(properties);
  }
}
```

最後是完整範例中 `Car`的建構和使用方式。。

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

## 類別圖

![alt text](./etc/abstract-document.png "Abstract Document Traits and Domain")

## 適用性

在以下情況使用抽象文件模式：

* 需要即時新增新屬性時

* 想要一種靈活的方式以樹狀結構組織領域 (domain) 時

* 想要一個更鬆散耦合的系統時

## 參考資料和來源

* [Wikipedia: Abstract Document Pattern](https://en.wikipedia.org/wiki/Abstract_Document_Pattern)
* [Martin Fowler: Dealing with properties](http://martinfowler.com/apsupp/properties.pdf)
* [Pattern-Oriented Software Architecture Volume 4: A Pattern Language for Distributed Computing (v. 4)](https://www.amazon.com/gp/product/0470059028/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0470059028&linkId=e3aacaea7017258acf184f9f3283b492)
