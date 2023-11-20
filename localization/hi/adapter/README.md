---
title: Adapter
category: Structural
language: hi
tag:
 - Gang of Four
---

## दूसरा नाम
आवरण

## हेतु
किसी क्लास के इंटरफ़ेस को क्लाइंट द्वारा अपेक्षित किसी अन्य इंटरफ़ेस में कनवर्ट करें। एडाप्टर कक्षाओं को एक साथ काम करने देता है
असंगत इंटरफ़ेस के कारण अन्यथा नहीं हो सका।

## व्याख्या

वास्तविक दुनिया का उदाहरण

> विचार करें कि आपके मेमोरी कार्ड पर कुछ चित्र हैं और आपको उन्हें अपने कंप्यूटर पर स्थानांतरित करने की आवश्यकता है। उन्हें स्थानांतरित करने के लिए, आपको किसी प्रकार के एडाप्टर की आवश्यकता होती है जो आपके कंप्यूटर पोर्ट के साथ संगत हो ताकि आप अपने कंप्यूटर में मेमोरी कार्ड संलग्न कर सकें। इस मामले में कार्ड रीडर एक एडाप्टर है।
> एक अन्य उदाहरण प्रसिद्ध पावर एडॉप्टर होगा; तीन-पैर वाले प्लग को दो-आयामी आउटलेट से नहीं जोड़ा जा सकता है, इसके लिए एक पावर एडाप्टर का उपयोग करने की आवश्यकता होती है जो इसे दो-आयामी आउटलेट के साथ संगत बनाता है।
> एक अन्य उदाहरण एक अनुवादक का होगा जो एक व्यक्ति द्वारा दूसरे व्यक्ति द्वारा बोले गए शब्दों का अनुवाद करेगा।

सरल शब्दो मे

> एडेप्टर पैटर्न आपको किसी अन्यथा असंगत ऑब्जेक्ट को किसी अन्य वर्ग के साथ संगत बनाने के लिए एडॉप्टर में लपेटने की सुविधा देता है।

विकिपीडिया कहता है

> सॉफ्टवेयर इंजीनियरिंग में, एडॉप्टर पैटर्न एक सॉफ्टवेयर डिज़ाइन पैटर्न है जो मौजूदा क्लास के इंटरफ़ेस को दूसरे इंटरफ़ेस के रूप में उपयोग करने की अनुमति देता है। इसका उपयोग अक्सर मौजूदा कक्षाओं को उनके स्रोत कोड को संशोधित किए बिना दूसरों के साथ काम करने के लिए किया जाता है।

**प्रोग्रामेटिक उदाहरण**

एक ऐसे कप्तान पर विचार करें जो केवल नाव चला सकता है और बिल्कुल भी नाव नहीं चला सकता।

सबसे पहले, हमारे पास इंटरफ़ेस `RowingBoat` और `FishingBoat` हैं

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

और कैप्टन को उम्मीद है कि `RowingBoat` इंटरफ़ेस का कार्यान्वयन आगे बढ़ने में सक्षम होगा

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

अब मान लीजिए कि समुद्री डाकू आ रहे हैं और हमारे कप्तान को भागने की जरूरत है लेकिन केवल मछली पकड़ने वाली नाव उपलब्ध है। हमें एक एडॉप्टर बनाने की आवश्यकता है जो कप्तान को नाव चलाने के अपने कौशल के साथ मछली पकड़ने वाली नाव को संचालित करने की अनुमति दे।

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

और अब `Captain` समुद्री डाकुओं से बचने के लिए `FishingBoat` का उपयोग कर सकता है

```java
var captain = new Captain(new FishingBoatAdapter());
captain.row();
```

## क्लास डायग्राम
![alt text](../../../adapter/etc/adapter.urm.png "एडाप्टर क्लास डायग्राम")

## प्रयोज्यता
जब एडॉप्टर पैटर्न का उपयोग करें

* आप किसी मौजूदा कक्षा का उपयोग करना चाहते हैं, और इसका इंटरफ़ेस आपकी ज़रूरत से मेल नहीं खाता है।
* आप एक पुन: प्रयोज्य वर्ग बनाना चाहते हैं जो असंबद्ध या अप्रत्याशित वर्गों के साथ सहयोग करता है, अर्थात, ऐसे वर्ग जिनमें आवश्यक रूप से संगत इंटरफ़ेस नहीं है
* आपको कई मौजूदा उपवर्गों का उपयोग करने की आवश्यकता है, लेकिन सभी को उपवर्गित करके उनके इंटरफ़ेस को अनुकूलित करना अव्यावहारिक है। एक ऑब्जेक्ट एडाप्टर अपने मूल वर्ग के इंटरफ़ेस को अनुकूलित कर सकता है।
* तृतीय-पक्ष लाइब्रेरी का उपयोग करने वाले अधिकांश एप्लिकेशन लाइब्रेरी से एप्लिकेशन को अलग करने के लिए एप्लिकेशन और तृतीय पक्ष लाइब्रेरी के बीच मध्य परत के रूप में एडेप्टर का उपयोग करते हैं। यदि किसी अन्य लाइब्रेरी का उपयोग करना है तो एप्लिकेशन कोड को बदले बिना नई लाइब्रेरी के लिए केवल एक एडाप्टर की आवश्यकता होती है।

## ट्यूटोरियल

* [Dzone](https://dzone.com/articles/adapter-design-pattern-in-java)
* [Refactoring Guru](https://refactoring.guru/design-patterns/adapter/java/example)
* [Baeldung](https://www.baeldung.com/java-adapter-pattern)

## नतीजे
क्लास और ऑब्जेक्ट एडेप्टर के अलग-अलग ट्रेड-ऑफ़ होते हैं। एक क्लास एडॉप्टर

*	एक ठोस एडाप्टी वर्ग के लिए प्रतिबद्ध होकर एडाप्टी को लक्ष्य के अनुसार अनुकूलित करता है। परिणामस्वरूप, जब हम किसी क्लास और उसके सभी उपवर्गों को अनुकूलित करना चाहते हैं तो क्लास एडॉप्टर काम नहीं करेगा।
*	एडॉप्टर को एडैप्टी के कुछ व्यवहारों को ओवरराइड करने दें क्योंकि एडॉप्टर एडैप्टी का एक उपवर्ग है।
*	केवल एक ऑब्जेक्ट का परिचय देता है, और एडाप्टी तक पहुंचने के लिए किसी अतिरिक्त सूचक संकेत की आवश्यकता नहीं होती है।

एक ऑब्जेक्ट एडाप्टर

*	एक ही एडॉप्टर को कई एडाप्टीज़ के साथ काम करने देता है, यानी स्वयं एडाप्टी और उसके सभी उपवर्गों (यदि कोई हो) के साथ। एडॉप्टर एक साथ सभी एडेप्टीज़ में कार्यक्षमता भी जोड़ सकता है।
*	एडाप्टी व्यवहार को ओवरराइड करना कठिन बना देता है। इसके लिए एडाप्टी को उपवर्गित करने और एडॉप्टर को एडाप्टी के बजाय उपवर्ग को संदर्भित करने की आवश्यकता होगी।


## वास्तविक दुनिया के उदाहरण

* [java.util.Arrays#asList()](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList%28T...%29)
* [java.util.Collections#list()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#list-java.util.Enumeration-)
* [java.util.Collections#enumeration()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-)
* [javax.xml.bind.annotation.adapters.XMLAdapter](http://docs.oracle.com/javase/8/docs/api/javax/xml/bind/annotation/adapters/XmlAdapter.html#marshal-BoundType-)


## श्रेय

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)