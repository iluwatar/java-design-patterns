---
title: Abstract Document
category: Structural
language: hi
tag: 
 - Extensibility
---

## हेतु

गतिशील गुणों का उपयोग करें और टाइप-सेफ्टी रखते हुए अनटाइप्ड लैंग्वेज का लचीलापन प्राप्त करें।

## व्याख्या

सार दस्तावेज़ पैटर्न अतिरिक्त, गैर-स्थैतिक गुणों को संभालने में सक्षम बनाता है। यह पैटर्न
प्रकार की सुरक्षा और विभिन्न वर्गों के अलग-अलग गुणों को सक्षम करने के लिए लक्षणों की अवधारणा का उपयोग करता है
इंटरफेस का सेट।
वास्तविक उदाहरण

>  एक कार पर विचार करें जिसमें कई हिस्से होते हैं। हालाँकि हम नहीं जानते कि क्या विशिष्ट कार में वास्तव में सभी पुर्जे हैं, या उनमें से कुछ ही हैं। हमारी कारें गतिशील और बेहद लचीली हैं।

सरल शब्दो मे

> सार दस्तावेज़ पैटर्न वस्तुओं को इसके बारे में जानने के बिना गुणों को संलग्न करने की अनुमति देता है।

विकिपीडिया कहता है

> ढीले टाइप की-वैल्यू स्टोर्स और एक्सपोज़िंग में ऑब्जेक्ट्स को व्यवस्थित करने के लिए एक ऑब्जेक्ट-ओरिएंटेड स्ट्रक्चरल डिज़ाइन पैटर्न
टाइप किए गए दृश्यों का उपयोग कर डेटा। पैटर्न का उद्देश्य घटकों के बीच उच्च स्तर का लचीलापन प्राप्त करना है
एक दृढ़ता से टाइप की गई भाषा में जहां ऑब्जेक्ट-ट्री में नए गुण जोड़े जा सकते हैं, बिना खोए
प्रकार-सुरक्षा का समर्थन। पैटर्न एक वर्ग के विभिन्न गुणों को अलग-अलग करने के लिए लक्षणों का उपयोग करता है
इंटरफेस।

**प्रोग्रामेटिक उदाहरण**

आइए पहले आधार वर्ग `Document` और `AbstractDocument` को परिभाषित करें। वे मूल रूप से वस्तु को एक संपत्ति बनाते हैं
नक्शा और बाल वस्तुओं की कोई भी राशि।

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
इसके बाद हम एक एनुम `Property` और प्रकार, मूल्य, मॉडल और भागों के लिए इंटरफेस का एक सेट परिभाषित करते हैं। यह हमें बनाने की अनुमति देता है
हमारे `Car` वर्ग के लिए स्थिर दिखने वाला इंटरफ़ेस।

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

अब हम पेश करने के लिए तैयार हैं `Car`.

```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

  public Car(Map<String, Object> properties) {
    super(properties);
  }
}
```

और अंत में यहां बताया गया है कि कैसे हम `Car` का निर्माण और उपयोग एक पूर्ण उदाहरण में करते हैं।

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

## क्लास डायग्राम

![alt text](../../../abstract-document/etc/abstract-document.png "Abstract Document Traits and Domain")

## उपयोग

कब सार दस्तावेज़ पैटर्न का प्रयोग करें

* तत्काल नई संपत्तियों को जोड़ने की जरूरत है
* आप डोमेन को वृक्ष जैसी संरचना में व्यवस्थित करने के लिए एक लचीला तरीका चाहते हैं
* आप अधिक शिथिल युग्मित प्रणाली चाहते हैं

## श्रेय

* [Wikipedia: Abstract Document Pattern](https://en.wikipedia.org/wiki/Abstract_Document_Pattern)
* [Martin Fowler: Dealing with properties](http://martinfowler.com/apsupp/properties.pdf)
* [Pattern-Oriented Software Architecture Volume 4: A Pattern Language for Distributed Computing (v. 4)](https://www.amazon.com/gp/product/0470059028/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0470059028&linkId=e3aacaea7017258acf184f9f3283b492)
