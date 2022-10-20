---
layout: pattern
title: Singleton
folder: singleton
permalink: /patterns/singleton/hi
categories: Creational
language: hi
tags:
- Gang of Four
---

## इरादा

सुनिश्चित करें कि किसी वर्ग में केवल एक उदाहरण है, और उस तक पहुंच का वैश्विक बिंदु प्रदान करें।

## व्याख्या

वास्तविक दुनिया का उदाहरण

> केवल एक हाथीदांत टॉवर हो सकता है जहां जादूगर अपने जादू का अध्ययन करते हैं। वही मुग्ध हाथीदांत
> टावर हमेशा जादूगरों द्वारा प्रयोग किया जाता है। यहां हाथीदांत टावर एक सिंगलटन है।

सीधे शब्दों में

> यह सुनिश्चित करता है कि किसी विशेष वर्ग का केवल एक ही ऑब्जेक्ट बनाया जाए।

विकिपीडिया कहता है

> सॉफ्टवेयर इंजीनियरिंग में, सिंगलटन पैटर्न एक सॉफ्टवेयर डिजाइन पैटर्न है जो प्रतिबंधित करता है
> एक वस्तु के लिए एक वर्ग की तात्कालिकता। यह तब उपयोगी होता है जब ठीक एक वस्तु की आवश्यकता होती है
> पूरे सिस्टम में कार्यों का समन्वय करें।

**प्रोग्रामेटिक उदाहरण**

Joshua Bloch, Effective Java 2nd Edition p.18

> सिंगल-एलिमेंट Enum टाइप सिंगलटन को लागू करने का सबसे अच्छा तरीका है

```java
public enum EnumIvoryTower {
  INSTANCE
}
```

फिर उपयोग करने के लिए:

```java
    var enumIvoryTower1 = EnumIvoryTower.INSTANCE;
    var enumIvoryTower2 = EnumIvoryTower.INSTANCE;
    LOGGER.info("enumIvoryTower1={}", enumIvoryTower1);
    LOGGER.info("enumIvoryTower2={}", enumIvoryTower2);
```

कंसोल आउटपुट:

```
enumIvoryTower1=com.iluwatar.singleton.EnumIvoryTower@1221555852
enumIvoryTower2=com.iluwatar.singleton.EnumIvoryTower@1221555852
```

## कक्षा आरेख

![alt text](../../../singleton/etc/singleton.urm.png "Singleton pattern class diagram")

## प्रयोज्यता

सिंगलटन पैटर्न का प्रयोग करें जब

* एक वर्ग का ठीक एक उदाहरण होना चाहिए, और यह एक प्रसिद्ध पहुंच बिंदु से ग्राहकों के लिए सुलभ होना चाहिए
* जब एकमात्र उदाहरण उपवर्ग द्वारा एक्स्टेंसिबल होना चाहिए, और क्लाइंट अपने कोड को संशोधित किए बिना एक विस्तारित उदाहरण का उपयोग करने में सक्षम होना चाहिए

सिंगलटन के लिए कुछ विशिष्ट उपयोग के मामले

*लॉगिंग क्लास
* डेटाबेस से कनेक्शन प्रबंधित करना
* फ़ाइल मैनेजर

## ज्ञात उपयोग

* [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)
* [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
* [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)


## परिणाम

* एकल उत्तरदायित्व सिद्धांत (एस.आर.पी) का उनके निर्माण और जीवनचक्र को नियंत्रित करके उल्लंघन करता है।
* वैश्विक रूप से साझा किए गए उदाहरण का उपयोग करने के लिए प्रोत्साहित करता है जो इस वस्तु द्वारा उपयोग की जाने वाली वस्तु और संसाधनों को हटाए जाने से रोकता है।
* कसकर युग्मित कोड बनाता है। सिंगलटन के ग्राहकों का परीक्षण करना मुश्किल हो जाता है।
* सिंगलटन को उपवर्ग करना लगभग असंभव बना देता है।

## क्रेडिट

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
