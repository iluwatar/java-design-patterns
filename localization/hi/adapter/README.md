---
layout: pattern
title: Adapter
folder: adapter
permalink: /patterns/adapter/hi
categories: Structural
language: hi
tags:
- Gang of Four
---

## इस रूप में भी जाना जाता है

* Wrapper

## इरादा
एक वर्ग के इंटरफ़ेस को दूसरे इंटरफ़ेस में परिवर्तित करें जिसकी ग्राहक अपेक्षा करते हैं। एडेप्टर कक्षाओं को एक साथ काम करने देता है कि
असंगत इंटरफेस के कारण अन्यथा नहीं हो सका।

## व्याख्या

वास्तविक दुनिया का उदाहरण

> विचार करें कि आपके मेमोरी कार्ड में कुछ चित्र हैं और आपको उन्हें अपने कंप्यूटर पर स्थानांतरित करने की आवश्यकता है। उन्हें स्थानांतरित करने के लिए, आपको किसी प्रकार के एडेप्टर की आवश्यकता होती है जो आपके कंप्यूटर पोर्ट के साथ संगत हो ताकि आप अपने कंप्यूटर में एक मेमोरी कार्ड संलग्न कर सकें। इस मामले में कार्ड रीडर एक एडेप्टर है।  
> एक अन्य उदाहरण प्रसिद्ध पावर एडॉप्टर होगा; तीन-पैर वाले प्लग को दो-तरफा आउटलेट से नहीं जोड़ा जा सकता है, इसे एक पावर एडाप्टर का उपयोग करने की आवश्यकता होती है जो इसे दो-आयामी आउटलेट के साथ संगत बनाता है।  
> फिर भी एक अन्य उदाहरण एक अनुवादक द्वारा एक व्यक्ति द्वारा दूसरे व्यक्ति को बोले गए शब्दों का अनुवाद करना होगा

सीधे शब्दों में

> एडेप्टर पैटर्न आपको किसी अन्य वर्ग के साथ संगत बनाने के लिए एक एडेप्टर में एक अन्यथा असंगत वस्तु को लपेटने देता है।

विकिपीडिया कहता है

> सॉफ्टवेयर इंजीनियरिंग में, एडेप्टर पैटर्न एक सॉफ्टवेयर डिजाइन पैटर्न है जो मौजूदा वर्ग के इंटरफेस को दूसरे इंटरफेस के रूप में इस्तेमाल करने की अनुमति देता है। इसका उपयोग अक्सर मौजूदा कक्षाओं को उनके स्रोत कोड को संशोधित किए बिना दूसरों के साथ काम करने के लिए किया जाता है।

**प्रोग्रामेटिक उदाहरण**

एक कप्तान पर विचार करें जो केवल रोइंग नौकाओं का उपयोग कर सकता है और बिल्कुल भी नहीं जा सकता।  

सबसे पहले, हमारे पास `RowingBoat` और `FishingBoat` इंटरफेस हैं।

```java
public interface RowingBoat {
  void row();
}

@Slf4j
public class FishingBoat {
  public void sail() {
    LOGGER.info("The fishing boat is sailing");
  }
}
```
और कप्तान को उम्मीद है कि `RowingBoat` इंटरफ़ेस के कार्यान्वयन को स्थानांतरित करने में सक्षम होगा।

```java
public class Captain {

  private final RowingBoat rowingBoat;
  // default constructor and setter for rowingBoat
  public Captain(RowingBoat rowingBoat) {
    this.rowingBoat = rowingBoat;
  }

  public void row() {
    rowingBoat.row();
  }
}
```
अब मान लीजिए कि समुद्री डाकू आ रहे हैं और हमारे कप्तान को भागने की जरूरत है लेकिन केवल एक मछली पकड़ने वाली नाव उपलब्ध है।  
हमें एक एडॉप्टर बनाने की आवश्यकता है जो कप्तान को अपने रोइंग बोट कौशल के साथ मछली पकड़ने की नाव को संचालित करने की अनुमति देता है।
```java
@Slf4j
public class FishingBoatAdapter implements RowingBoat {

  private final FishingBoat boat;

  public FishingBoatAdapter() {
    boat = new FishingBoat();
  }

  @Override
  public void row() {
    boat.sail();
  }
}
```

और अब `Captain` समुद्री लुटेरों से बचने के लिए `FishingBoat` का इस्तेमाल कर सकता है.

```java
var captain = new Captain(new FishingBoatAdapter());
captain.row();
```

## वर्ग आरेख
![ "एडेप्टर वर्ग आरेख"](../../../adapter/etc/adapter.urm.png)

## प्रयोज्यता
एडेप्टर पैटर्न का उपयोग करें जब

* आप एक मौजूदा वर्ग का उपयोग करना चाहते हैं, और इसका इंटरफ़ेस आपकी ज़रूरत से मेल नहीं खाता
* आप एक पुन: प्रयोज्य वर्ग बनाना चाहते हैं जो असंबंधित या अप्रत्याशित वर्गों के साथ सहयोग करता है, अर्थात ऐसी कक्षाएं जिनके पास संगत इंटरफेस नहीं है
* आपको कई मौजूदा उपवर्गों का उपयोग करने की आवश्यकता है, लेकिन सभी को उपवर्गित करके उनके इंटरफ़ेस को अनुकूलित करना अव्यावहारिक है। एक ऑब्जेक्ट एडॉप्टर अपने मूल वर्ग के इंटरफ़ेस को अनुकूलित कर सकता है।
* तृतीय-पक्ष लाइब्रेरी का उपयोग करने वाले अधिकांश एप्लिकेशन एप्लिकेशन और तृतीय पक्ष लाइब्रेरी के बीच एक मध्य परत के रूप में एडेप्टर का उपयोग लाइब्रेरी से एप्लिकेशन को अलग करने के लिए करते हैं। यदि किसी अन्य पुस्तकालय का उपयोग किया जाना है, तो नए पुस्तकालय के लिए केवल एक एडेप्टर की आवश्यकता है, बिना एप्लिकेशन कोड को बदले।

## ट्यूटोरियल

* [Dzone](https://dzone.com/articles/adapter-design-pattern-in-java)
* [Refactoring Guru](https://refactoring.guru/design-patterns/adapter/java/example)
* [Baeldung](https://www.baeldung.com/java-adapter-pattern)

## परिणाम
क्लास और ऑब्जेक्ट एडेप्टर में अलग-अलग ट्रेड-ऑफ होते हैं। एक वर्ग अनुकूलक

* एक ठोस एडेप्टी वर्ग के लिए प्रतिबद्ध होकर एडेप्टी को लक्ष्य के लिए अनुकूलित करता है। परिणामस्वरूप, जब हम किसी वर्ग और उसके सभी उपवर्गों को अनुकूलित करना चाहते हैं, तो एक क्लास एडॉप्टर काम नहीं करेगा।
* आइए एडॉप्टर एडेप्टी के कुछ व्यवहार को ओवरराइड करें क्योंकि एडेप्टर एडेप्टी का एक उपवर्ग है।
* केवल एक वस्तु का परिचय देता है, और एडेप्टी को प्राप्त करने के लिए किसी अतिरिक्त सूचक संकेत की आवश्यकता नहीं है।

एक वस्तु अनुकूलक

* एक एडॉप्टर को कई एडेप्टीज़ के साथ काम करने देता है - यानी, एडेप्टी स्वयं और उसके सभी उपवर्गों (यदि कोई हो)। एडॉप्टर एक ही बार में सभी एडेप्टी के लिए कार्यक्षमता भी जोड़ सकता है।
* एडेप्टी व्यवहार को ओवरराइड करना कठिन बनाता है। इसके लिए एडेप्टी को सबक्लास करने और एडॉप्टर को एडेप्टी के बजाय सबक्लास को संदर्भित करने की आवश्यकता होगी।


## वास्तविक दुनिया के उदाहरण

* [java.util.Arrays#asList()](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList%28T...%29)
* [java.util.Collections#list()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#list-java.util.Enumeration-)
* [java.util.Collections#enumeration()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-)
* [javax.xml.bind.annotation.adapters.XMLAdapter](http://docs.oracle.com/javase/8/docs/api/javax/xml/bind/annotation/adapters/XmlAdapter.html#marshal-BoundType-)


## क्रेडिट

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)


