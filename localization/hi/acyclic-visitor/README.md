---
title: Acyclic Visitor
category: Behavioral
language: hi
tag:
 - Extensibility
---

## हेतु

उन पदानुक्रमों को प्रभावित किए बिना और बनाए बिना मौजूदा वर्ग पदानुक्रमों में नए कार्यों को जोड़ने की अनुमति दें
तकलीफ़देह निर्भरता चक्र जो GoF विज़िटर पैटर्न में अंतर्निहित हैं।

## व्याख्या

वास्तविक दुनिया का उदाहरण


> हमारे पास मॉडेम कक्षाओं का एक पदानुक्रम है। इस पदानुक्रम में मोडेम को बाहरी एल्गोरिथम आधारित द्वारा देखा जाना चाहिए
> फ़िल्टरिंग मानदंड पर (क्या यह यूनिक्स या डॉस संगत मॉडेम है)। 

सरल शब्दो मे

> चक्रीय आगंतुक पदानुक्रम को संशोधित किए बिना कार्यों को मौजूदा वर्ग पदानुक्रम में जोड़ने की अनुमति देता है।

[WikiWikiWeb](https://wiki.c2.com/?AcyclicVisitor) कहता है


> एसाइक्लिक विज़िटर पैटर्न मौजूदा वर्ग पदानुक्रमों को प्रभावित किए बिना नए कार्यों को जोड़ने की अनुमति देता है
> पदानुक्रम, और निर्भरता चक्र बनाए बिना जो GangOfFour विज़िटरपैटर्न में निहित हैं।

**Programmatic Example**

यहाँ `मॉडेम` पदानुक्रम है।

```java
public abstract class Modem {
  public abstract void accept(ModemVisitor modemVisitor);
}

public class Zoom extends Modem {
  ...
  @Override
  public void accept(ModemVisitor modemVisitor) {
    if (modemVisitor instanceof ZoomVisitor) {
      ((ZoomVisitor) modemVisitor).visit(this);
    } else {
      LOGGER.info("Only ZoomVisitor is allowed to visit Zoom modem");
    }
  }
}

public class Hayes extends Modem {
  ...
  @Override
  public void accept(ModemVisitor modemVisitor) {
    if (modemVisitor instanceof HayesVisitor) {
      ((HayesVisitor) modemVisitor).visit(this);
    } else {
      LOGGER.info("Only HayesVisitor is allowed to visit Hayes modem");
    }
  }
}
```


आगे हम `मॉडेमविज़िटर` पदानुक्रम का परिचय देते हैं।

```java
public interface ModemVisitor {
}

public interface HayesVisitor extends ModemVisitor {
  void visit(Hayes hayes);
}

public interface ZoomVisitor extends ModemVisitor {
  void visit(Zoom zoom);
}

public interface AllModemVisitor extends ZoomVisitor, HayesVisitor {
}

public class ConfigureForDosVisitor implements AllModemVisitor {
  ...
  @Override
  public void visit(Hayes hayes) {
    LOGGER.info(hayes + " used with Dos configurator.");
  }
  @Override
  public void visit(Zoom zoom) {
    LOGGER.info(zoom + " used with Dos configurator.");
  }
}

public class ConfigureForUnixVisitor implements ZoomVisitor {
  ...
  @Override
  public void visit(Zoom zoom) {
    LOGGER.info(zoom + " used with Unix configurator.");
  }
}
```

अंत में, यहां आगंतुक कार्रवाई में हैं।

```java
    var conUnix = new ConfigureForUnixVisitor();
    var conDos = new ConfigureForDosVisitor();
    var zoom = new Zoom();
    var hayes = new Hayes();
    hayes.accept(conDos);
    zoom.accept(conDos);
    hayes.accept(conUnix);
    zoom.accept(conUnix);   
```

Program output:

```
    // Hayes modem used with Dos configurator.
    // Zoom modem used with Dos configurator.
    // Only HayesVisitor is allowed to visit Hayes modem
    // Zoom modem used with Unix configurator.
```

## क्लास डायग्राम

![alt text](../../../acyclic-visitor/etc/acyclic-visitor.png "Acyclic Visitor")

## प्रयोज्यता

पैटर्न का उपयोग कब करें

* जब आपको उस पदानुक्रम को बदलने या प्रभावित करने की आवश्यकता के बिना किसी मौजूदा पदानुक्रम में एक नया फ़ंक्शन जोड़ने की आवश्यकता होती है।
* जब ऐसे कार्य होते हैं जो एक पदानुक्रम पर काम करते हैं, लेकिन जो स्वयं पदानुक्रम में शामिल नहीं होते हैं। उदा. ConfigureForDOS / ConfigureForUnix / ConfigureForX समस्या।
* जब आपको किसी वस्तु के प्रकार के आधार पर बहुत भिन्न संचालन करने की आवश्यकता होती है।
* जब विज़िट किए गए वर्ग पदानुक्रम को तत्व वर्ग के नए डेरिवेटिव के साथ अक्सर विस्तारित किया जाएगा।
* जब एलिमेंट के डेरिवेटिव का पुनर्संकलन, रीलिंकिंग, रीटेस्टिंग या पुनर्वितरण बहुत महंगा हो।

## ट्यूटोरियल

* [Acyclic Visitor Pattern Example](https://codecrafter.blogspot.com/2012/12/the-acyclic-visitor-pattern.html)

## नतीजे


अच्छा:

* वर्ग पदानुक्रमों के बीच कोई निर्भरता चक्र नहीं।
* यदि कोई नया जोड़ा जाता है तो सभी आगंतुकों को पुनः संकलित करने की आवश्यकता नहीं है।
* यदि वर्ग पदानुक्रम में एक नया सदस्य है तो मौजूदा आगंतुकों में संकलन विफलता का कारण नहीं बनता है।


खराब:

* यह दिखाते हुए [लिस्कोव के प्रतिस्थापन सिद्धांत](https://java-design-patterns.com/principles/#liskov-substitution-principle) का उल्लंघन करता है कि यह सभी आगंतुकों को स्वीकार कर सकता है लेकिन वास्तव में केवल विशेष आगंतुकों में रुचि रखता है।
* विज़िट करने योग्य वर्ग पदानुक्रम में सभी सदस्यों के लिए आगंतुकों का समानांतर पदानुक्रम बनाया जाना है।

## संबंधित पैटर्न

* [Visitor Pattern](https://java-design-patterns.com/patterns/visitor/)

## श्रेय

* [Acyclic Visitor by Robert C. Martin](http://condor.depaul.edu/dmumaugh/OOT/Design-Principles/acv.pdf)
* [Acyclic Visitor in WikiWikiWeb](https://wiki.c2.com/?AcyclicVisitor)
