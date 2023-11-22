---
title: Factory Method
category: Creational
language: hi
tag:
  - Extensibility 
  - Gang Of Four
---

## वर्चुअल कन्स्ट्रक्टर के रूप में भी जाना जाता 

## हेतु

एक इंटरफ़ेस को परिभाषित करें जो एक ऑब्जेक्ट बनाने की अनुमति देता है, लेकिन उपवर्गों को यह तय करने देता है कि किस वर्ग को तत्काल बनाया जाए।
फ़ैक्टरी मेथड एक वर्ग को उपवर्गों में तत्कालिकता स्थगित करने देता है।

## स्पष्टीकरण

वास्तविक दुनिया का उदाहरण

लोहार हथियार बनाता है। कल्पित बौनों को एल्विश हथियारों की आवश्यकता होती है और orcs को Orcish हथियारों की आवश्यकता होती है।
ग्राहक के हाथ पर निर्भर करता है कि सही प्रकार के लोहार को बुलाया जाता है।

साधारण शब्दों में

यह चाइल्ड क्लासेस को इंस्टेंटिएशन लॉजिक को डेलिगेट करने का एक तरीका प्रदान करता है।

विकिपीडिया का कहना है कि

क्लास-आधारित प्रोग्रामिंग में, फैक्ट्री मेथड पैटर्न एक क्रिएशनल पैटर्न है जो फैक्ट्री मेथड का उपयोग उस ऑब्जेक्ट को बनाने की समस्या से निपटने के लिए करता है जो उस ऑब्जेक्ट के सटीक वर्ग को निर्दिष्ट किए बिना बनाया जाएगा। यह एक फैक्ट्री मेथड को कॉल करके ऑब्जेक्ट बनाकर किया जाता है - या तो एक इंटरफ़ेस में निर्दिष्ट और चाइल्ड कक्षाओं द्वारा कार्यान्वित किया जाता है, या एक बेस क्लास में कार्यान्वित किया जाता है और वैकल्पिक रूप से व्युत्पन्न कक्षाओं द्वारा ओवरराइड किया जाता है - बजाय कंस्ट्रक्टर को कॉल करने के।

प्रोग्रामेटिक उदाहरण

हमारे ब्लैकस्मिथ उदाहरण को ऊपर लेते हुए, पहले हमारे पास एक 'ब्लैकस्मिथ' इंटरफेस होता है और इसके लिए कुछ अंगीकरण होते हैं:

```java
public interface Blacksmith {
  Weapon manufactureWeapon(WeaponType weaponType);
}

public class ElfBlacksmith implements Blacksmith {
  public Weapon manufactureWeapon(WeaponType weaponType) {
    return ELFARSENAL.get(weaponType);
  }
}

public class OrcBlacksmith implements Blacksmith {
  public Weapon manufactureWeapon(WeaponType weaponType) {
    return ORCARSENAL.get(weaponType);
  }
}
```
जब ग्राहक आते हैं, तो सही प्रकार के ब्लैकस्मिथ को बुलाया जाता है और अनुरोधित हथियार निर्मित किए जाते हैं  

```java
Blacksmith blacksmith = new OrcBlacksmith();
Weapon weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
LOGGER.info("{} manufactured {}", blacksmith, weapon);
weapon = blacksmith.manufactureWeapon(WeaponType.AXE);
LOGGER.info("{} manufactured {}", blacksmith, weapon);

blacksmith = new ElfBlacksmith();
weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
LOGGER.info("{} manufactured {}", blacksmith, weapon);
weapon = blacksmith.manufactureWeapon(WeaponType.AXE);
LOGGER.info("{} manufactured {}", blacksmith, weapon);

``` 
प्रोग्राम का आउटपुट :
```text
The orc blacksmith manufactured an orcish spear
The orc blacksmith manufactured an orcish axe
The elf blacksmith manufactured an elven spear
The elf blacksmith manufactured an elven axe

```
## क्लास डायग्राम

![alt text](./etc/factory-method.urm.png "Factory Method pattern class diagram")

## प्रयोज्यता

निम्नलिखित स्थितियों में फैक्टरी मेथड पैटर्न का उपयोग करें:

* क्लास उन ऑब्जेक्ट्स के वर्ग का अनुमान नहीं लगा सकता है जिन्हें उसे बनाना चाहिए।
* क्लास चाहता है कि उसके उपवर्ग उन ऑब्जेक्ट्स को निर्दिष्ट करें जो वह बनाता है।
* कक्षाएं कई सहायक उपवर्गों में से एक को जिम्मेदारी सौंपती हैं, और आप यह जानना चाहते हैं कि कौन सा सहायक उपवर्ग प्रतिनिधि है।

## ज्ञात उपयोग

* [java.util.Calendar](http://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html#getInstance--)
* [java.util.ResourceBundle](http://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html#getBundle-java.lang.String-)
* [java.text.NumberFormat](http://docs.oracle.com/javase/8/docs/api/java/text/NumberFormat.html#getInstance--)
* [java.nio.charset.Charset](http://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html#forName-java.lang.String-)
* [java.net.URLStreamHandlerFactory](http://docs.oracle.com/javase/8/docs/api/java/net/URLStreamHandlerFactory.html#createURLStreamHandler-java.lang.String-)
* [java.util.EnumSet](https://docs.oracle.com/javase/8/docs/api/java/util/EnumSet.html#of-E-)
* [javax.xml.bind.JAXBContext](https://docs.oracle.com/javase/8/docs/api/javax/xml/bind/JAXBContext.html#createMarshaller--)

## श्रेय

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
