---
layout: pattern
title: Factory
folder: factory
permalink: /patterns/factory/hi
categories: Creational
language: hi
tags:
- Gang of Four
---

## इस रूप में भी जाना जाता है

* Simple Factory
* Static Factory Method

## इरादा

कार्यान्वयन को छिपाने के लिए फैक्ट्री नामक एक वर्ग में समझाया गया एक स्थिर तरीका प्रदान करना
तर्क दें और क्लाइंट कोड को नई वस्तुओं को आरंभ करने के बजाय उपयोग पर केंद्रित करें।

## व्याख्या

वास्तविक दुनिया का उदाहरण

> एक कीमियागर की कल्पना कीजिए जो सिक्कों का निर्माण करने वाला है। कीमियागर दोनों को बनाने में सक्षम होना चाहिए।  
> सोने और तांबे के सिक्के और उनके बीच स्विच करना मौजूदा को संशोधित किए बिना संभव होना चाहिए
> स्रोत कोड। फ़ैक्टरी पैटर्न एक स्थिर निर्माण विधि प्रदान करके इसे संभव बनाता है।  
> प्रासंगिक मापदंडों के साथ बुलाया जा सकता है।

विकिपीडिया कहता है

> फैक्ट्री अन्य वस्तुओं को बनाने के लिए एक वस्तु है - औपचारिक रूप से एक कारखाना एक कार्य या विधि है जो।  
> एक अलग प्रोटोटाइप या वर्ग की वस्तुओं को लौटाता है।

**प्रोग्रामेटिक उदाहरण**

हमारे पास एक इंटरफ़ेस `Coin` और दो कार्यान्वयन `GoldCoin` और `CopperCoin` हैं।

```java
public interface Coin {
  String getDescription();
}

public class GoldCoin implements Coin {

  static final String DESCRIPTION = "This is a gold coin.";

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}

public class CopperCoin implements Coin {
   
  static final String DESCRIPTION = "This is a copper coin.";

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
```

ऊपर दी गई गणना उन प्रकार के सिक्कों का प्रतिनिधित्व करती है जिनका हम समर्थन करते हैं (`GoldCoin` और `CopperCoin`)।

```java
@RequiredArgsConstructor
@Getter
public enum CoinType {

  COPPER(CopperCoin::new),
  GOLD(GoldCoin::new);

  private final Supplier<Coin> constructor;
}
```

फिर हमारे पास फैक्ट्री क्लास में इनकैप्सुलेटेड Coin ऑब्जेक्ट्स बनाने के लिए स्टैटिक मेथड `getCoin` जो `CoinFactory` में है।

```java
public class CoinFactory {

  public static Coin getCoin(CoinType type) {
    return type.getConstructor().get();
  }
}
```

अब क्लाइंट कोड पर हम फ़ैक्टरी क्लास का उपयोग करके विभिन्न प्रकार के सिक्के बना सकते हैं।

```java
LOGGER.info("The alchemist begins his work.");
var coin1 = CoinFactory.getCoin(CoinType.COPPER);
var coin2 = CoinFactory.getCoin(CoinType.GOLD);
LOGGER.info(coin1.getDescription());
LOGGER.info(coin2.getDescription());
```

प्रोग्राम आउटपुट:

```java
The alchemist begins his work.
This is a copper coin.
This is a gold coin.
```

## कक्षा आरेख

![alt text](../../../factory/etc/factory.urm.png "Factory pattern class diagram")

## प्रयोज्यता

फ़ैक्टरी पैटर्न का उपयोग तब करें जब आप केवल किसी वस्तु के निर्माण की परवाह करते हैं, न कि कैसे बनाना है
और इसे प्रबंधित करें।

मुनाफे

* सभी वस्तुओं के निर्माण को एक ही स्थान पर रखने की अनुमति देता है और कोडबेस में 'नया' कीवर्ड फैलाने से बचता है।
* शिथिल युग्मित कोड लिखने की अनुमति देता है। इसके कुछ मुख्य लाभों में बेहतर परीक्षण क्षमता, समझने में आसान कोड, स्वैपेबल घटक, मापनीयता और पृथक विशेषताएं शामिल हैं।

हानि

* कोड जितना होना चाहिए उससे कहीं अधिक जटिल हो जाता है।

## ज्ञात उपयोग

* [java.util.Calendar#getInstance()](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html#getInstance--)
* [java.util.ResourceBundle#getBundle()](https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html#getBundle-java.lang.String-)
* [java.text.NumberFormat#getInstance()](https://docs.oracle.com/javase/8/docs/api/java/text/NumberFormat.html#getInstance--)
* [java.nio.charset.Charset#forName()](https://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html#forName-java.lang.String-)
* [java.net.URLStreamHandlerFactory#createURLStreamHandler(String)](https://docs.oracle.com/javase/8/docs/api/java/net/URLStreamHandlerFactory.html) (
  प्रोटोकॉल के आधार पर अलग-अलग सिंगलटन ऑब्जेक्ट लौटाता है)
* [java.util.EnumSet#of()](https://docs.oracle.com/javase/8/docs/api/java/util/EnumSet.html#of(E))
* [javax.xml.bind.JAXBContext#createMarshaller()](https://docs.oracle.com/javase/8/docs/api/javax/xml/bind/JAXBContext.html#createMarshaller--) और अन्य समान तरीके.

## संबंधित पैटर्न

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/)
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)
* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/)
