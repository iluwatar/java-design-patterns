---
title: Abstract Factory
category: Creational
language: hi
tag:
 - Gang of Four
---

## के रूप में भी जाना जाता है

किट

## हेतु

संबंधित या आश्रित परिवारों को बनाने के लिए एक इंटरफ़ेस प्रदान करें
वस्तुओं को उनके ठोस वर्गों को निर्दिष्ट किए बिना।

## व्याख्या

वास्तविक दुनिया का उदाहरण

> एक राज्य बनाने के लिए हमें एक सामान्य विषय वाली वस्तुओं की आवश्यकता होती है। Elven किंगडम को एक Elven King, Elven Castle और Elven सेना की आवश्यकता होती है जबकि Orcish राज्य को एक Orcish King, Orcish Castle और Orcish सेना की आवश्यकता होती है। राज्य में वस्तुओं के बीच एक निर्भरता है।

सरल शब्दो मे

> कारखानों का एक कारखाना; एक कारखाना जो व्यक्तिगत लेकिन संबंधित/आश्रित कारखानों को उनके ठोस वर्गों को निर्दिष्ट किए बिना एक साथ समूहित करता है।

विकिपीडिया कहता है

> अमूर्त फ़ैक्टरी पैटर्न व्यक्तिगत फ़ैक्टरियों के एक समूह को समाहित करने का एक तरीका प्रदान करता है, जिनके ठोस वर्गों को निर्दिष्ट किए बिना एक सामान्य विषय है। 

**Programmatic Example**

ऊपर किंगडम उदाहरण का अनुवाद। सबसे पहले, हमारे पास वस्तुओं के लिए कुछ इंटरफेस और कार्यान्वयन हैं
साम्राज्य।

```java
public interface Castle {
  String getDescription();
}

public interface King {
  String getDescription();
}

public interface Army {
  String getDescription();
}

// Elven implementations ->
public class ElfCastle implements Castle {
  static final String DESCRIPTION = "This is the elven castle!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class ElfKing implements King {
  static final String DESCRIPTION = "This is the elven king!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class ElfArmy implements Army {
  static final String DESCRIPTION = "This is the elven Army!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}

// Orcish implementations similarly -> ...

```

फिर हमारे पास राज्य कारखाने के लिए अमूर्तता और कार्यान्वयन है।

```java
public interface KingdomFactory {
  Castle createCastle();
  King createKing();
  Army createArmy();
}

public class ElfKingdomFactory implements KingdomFactory {

  @Override
  public Castle createCastle() {
    return new ElfCastle();
  }

  @Override
  public King createKing() {
    return new ElfKing();
  }

  @Override
  public Army createArmy() {
    return new ElfArmy();
  }
}

public class OrcKingdomFactory implements KingdomFactory {

  @Override
  public Castle createCastle() {
    return new OrcCastle();
  }

  @Override
  public King createKing() {
    return new OrcKing();
  }
  
  @Override
  public Army createArmy() {
    return new OrcArmy();
  }
}
```

अब हमारे पास एब्स्ट्रैक्ट फैक्ट्री है जो हमें संबंधित वस्तुओं का एक परिवार बनाने की सुविधा देती है, जैसे कि एल्वेन किंगडम फैक्ट्री एल्वेन कैसल, राजा और सेना आदि बनाती है।

```java
var factory = new ElfKingdomFactory();
var castle = factory.createCastle();
var king = factory.createKing();
var army = factory.createArmy();

castle.getDescription();
king.getDescription();
army.getDescription();
```

Program output:

```java
This is the elven castle!
This is the elven king!
This is the elven Army!
```

अब, हम अपने विभिन्न राज्य कारखानों के लिए एक कारखाना डिजाइन कर सकते हैं। इस उदाहरण में, हमने `FactoryMaker` बनाया, जो `ElfKingdomFactory` या `OrcKingdomFactory` के उदाहरण को वापस करने के लिए ज़िम्मेदार है।
ग्राहक वांछित कंक्रीट फैक्ट्री बनाने के लिए `फैक्ट्रीमेकर` का उपयोग कर सकता है, जो बदले में, विभिन्न ठोस वस्तुओं का उत्पादन करेगा (`आर्मी`, `किंग`, `कैसल` से व्युत्पन्न)।
इस उदाहरण में, हमने क्लाइंट द्वारा अनुरोध किए जाने वाले राज्य कारखाने के प्रकार को पैरामीटर करने के लिए एक एनम का भी उपयोग किया।

```java
public static class FactoryMaker {

    public enum KingdomType {
        ELF, ORC
    }

    public static KingdomFactory makeFactory(KingdomType type) {
        return switch (type) {
            case ELF -> new ElfKingdomFactory();
            case ORC -> new OrcKingdomFactory();
            default -> throw new IllegalArgumentException("KingdomType not supported.");
        };
    }
}

    public static void main(String[] args) {
        var app = new App();

        LOGGER.info("Elf Kingdom");
        app.createKingdom(FactoryMaker.makeFactory(KingdomType.ELF));
        LOGGER.info(app.getArmy().getDescription());
        LOGGER.info(app.getCastle().getDescription());
        LOGGER.info(app.getKing().getDescription());

        LOGGER.info("Orc Kingdom");
        app.createKingdom(FactoryMaker.makeFactory(KingdomType.ORC));
        --similar use of the orc factory
    }
```

## क्लास डायग्राम

![alt text](../../../abstract-factory/etc/abstract-factory.urm.png "Abstract Factory class diagram")


## प्रयोज्यता

एब्सट्रैक्ट फ़ैक्टरी पैटर्न का उपयोग कब करें

* सिस्टम इस बात से स्वतंत्र होना चाहिए कि इसके उत्पाद कैसे बनाए जाते हैं, बनाए जाते हैं और प्रस्तुत किए जाते हैं
* सिस्टम को उत्पादों के कई परिवारों में से एक के साथ कॉन्फ़िगर किया जाना चाहिए
* संबंधित उत्पाद वस्तुओं के परिवार को एक साथ उपयोग करने के लिए डिज़ाइन किया गया है, और आपको इस बाधा को लागू करने की आवश्यकता है
* आप उत्पादों की एक क्लास लाइब्रेरी प्रदान करना चाहते हैं, और आप केवल उनके इंटरफेस को प्रकट करना चाहते हैं, उनके कार्यान्वयन को नहीं
* निर्भरता का जीवनकाल अवधारणात्मक रूप से उपभोक्ता के जीवनकाल से छोटा होता है।
* किसी विशेष निर्भरता के निर्माण के लिए आपको रन-टाइम मान की आवश्यकता होती है
* आप यह तय करना चाहते हैं कि रनटाइम के दौरान परिवार से किस उत्पाद को कॉल किया जाए।
* आपको निर्भरता को हल करने से पहले केवल रन-टाइम पर ज्ञात एक या अधिक पैरामीटर प्रदान करने की आवश्यकता है।
* जब आपको उत्पादों के बीच निरंतरता की आवश्यकता हो
* प्रोग्राम में नए उत्पादों या उत्पादों के समूह को जोड़ते समय आप मौजूदा कोड को बदलना नहीं चाहते हैं।

उदाहरण उपयोग के मामले	

* रनटाइम पर FileSystemAcmeService या DatabaseAcmeService या NetworkAcmeService के उपयुक्त कार्यान्वयन के लिए कॉल करने का चयन करना।
* यूनिट टेस्ट केस लिखना बहुत आसान हो जाता है
* विभिन्न ओएस के लिए यूआई उपकरण

## नतीजे

* जावा में निर्भरता इंजेक्शन सेवा वर्ग की निर्भरता को छुपाता है जो रनटाइम त्रुटियों को जन्म दे सकता है जो संकलन समय पर पकड़ा गया होता।
* जबकि पूर्वनिर्धारित वस्तुओं को बनाते समय पैटर्न बहुत अच्छा होता है, नए को जोड़ना चुनौतीपूर्ण हो सकता है।
* कोड अधिक जटिल हो जाता है जितना होना चाहिए क्योंकि पैटर्न के साथ बहुत सारे नए इंटरफेस और कक्षाएं पेश की जाती हैं।

## ट्यूटोरियल

* [Abstract Factory Pattern Tutorial](https://www.journaldev.com/1418/abstract-factory-design-pattern-in-java) 

## ज्ञात उपयोग

* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
* [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
* [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)

## संबंधित पैटर्न

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/)
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)

## श्रेय

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
