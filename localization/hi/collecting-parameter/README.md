---
title: Collecting Parameter 
category: Idiom
language: hi
tag:
- Generic
---

## नाम
पैरामीटर एकत्रित करना

## हेतु
एक संग्रह के भीतर अनेक विधियों के सहयोगात्मक परिणाम को संग्रहीत करना।

## व्याख्या
### वास्तविक दुनिया का उदाहरण
एक बड़े कॉर्पोरेट भवन के भीतर, एक वैश्विक प्रिंटर कतार मौजूद है जो सभी मुद्रण कार्यों का एक संग्रह है
जो फिलहाल लंबित हैं। विभिन्न मंजिलों पर प्रिंटर के अलग-अलग मॉडल होते हैं, प्रत्येक की अलग-अलग प्रिंटिंग होती है
नीति। हमें एक ऐसे प्रोग्राम का निर्माण करना चाहिए जो किसी संग्रह में लगातार उपयुक्त मुद्रण कार्य जोड़ सके, जिसे *कलेक्टिंग पैरामीटर* कहा जाता है।

### साफ़ शब्दों में
एक विशाल विधि के बजाय जिसमें एक चर में जानकारी एकत्र करने के लिए कई नीतियां शामिल हैं, हम ऐसा कर सकते हैं
कई छोटे फ़ंक्शन बनाएं जो प्रत्येक पैरामीटर लें, और नई जानकारी जोड़ें। हम पैरामीटर को पास कर सकते हैं
ये सभी छोटे कार्य और अंत तक, हमें वही मिलेगा जो हम मूल रूप से चाहते थे। इस बार, कोड साफ़ है
और समझने में आसान है. क्योंकि बड़े फ़ंक्शन को तोड़ दिया गया है, परिवर्तन के रूप में कोड को संशोधित करना भी आसान है
छोटे कार्यों के लिए स्थानीयकृत हैं।

### विकिपीडिया कहता है
कलेक्टिंग पैरामीटर मुहावरे में एक संग्रह (सूची, मानचित्र, आदि) को एक विधि के पैरामीटर के रूप में बार-बार पारित किया जाता है जो संग्रह में आइटम जोड़ता है।

### प्रोग्रामेटिक उदाहरण
ऊपर से हमारे उदाहरण को कोड करते हुए, हम संग्रह `result` को एक संग्रहण पैरामीटर के रूप में उपयोग कर सकते हैं। निम्नलिखित प्रतिबंध
कार्यान्वित हैं:
- यदि A4 कागज रंगीन है, तो वह भी एक तरफा होना चाहिए। अन्य सभी गैर-रंगीन कागज स्वीकार किए जाते हैं
- A3 कागज़ गैर-रंगीन और एक तरफा होने चाहिए
- A2 पेपर एकल-पृष्ठ, एकल-पक्षीय और गैर-रंगीन होने चाहिए

```java
package com.iluwatar.collectingparameter;
import java.util.LinkedList;
import java.util.Queue;
public class App {
  static PrinterQueue printerQueue = PrinterQueue.getInstance();

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    /*
      Initialising the printer queue with jobs
    */
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A4, 5, false, false));
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A3, 2, false, false));
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A2, 5, false, false));

    /*
      This variable is the collecting parameter.
    */    
    var result = new LinkedList<PrinterItem>();

    /* 
     * Using numerous sub-methods to collaboratively add information to the result collecting parameter
     */
    addA4Papers(result);
    addA3Papers(result);
    addA2Papers(result);
  }
}
```
हम `result` संग्रहण पैरामीटर को पॉप्युलेट करने के लिए `addA4Paper`, `addA3Paper`, और `addA2Paper` विधियों का उपयोग करते हैं
पहले वर्णित नीति के अनुसार उपयुक्त मुद्रण कार्य। तीन नीतियां नीचे एन्कोड की गई हैं,

```java
public class App {
  static PrinterQueue printerQueue = PrinterQueue.getInstance();
  /**
   * Adds A4 document jobs to the collecting parameter according to some policy that can be whatever the client
   * (the print center) wants.
   *
   * @param printerItemsCollection the collecting parameter
   */
  public static void addA4Papers(Queue<PrinterItem> printerItemsCollection) {
    /*
      Iterate through the printer queue, and add A4 papers according to the correct policy to the collecting parameter,
      which is 'printerItemsCollection' in this case.
     */
    for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
      if (nextItem.paperSize.equals(PaperSizes.A4)) {
        var isColouredAndSingleSided = nextItem.isColour && !nextItem.isDoubleSided;
        if (isColouredAndSingleSided) {
          printerItemsCollection.add(nextItem);
        } else if (!nextItem.isColour) {
          printerItemsCollection.add(nextItem);
        }
      }
    }
  }

  /**
   * Adds A3 document jobs to the collecting parameter according to some policy that can be whatever the client
   * (the print center) wants. The code is similar to the 'addA4Papers' method. The code can be changed to accommodate
   * the wants of the client.
   *
   * @param printerItemsCollection the collecting parameter
   */
  public static void addA3Papers(Queue<PrinterItem> printerItemsCollection) {
    for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
      if (nextItem.paperSize.equals(PaperSizes.A3)) {

        // Encoding the policy into a Boolean: the A3 paper cannot be coloured and double-sided at the same time
        var isNotColouredAndSingleSided = !nextItem.isColour && !nextItem.isDoubleSided;
        if (isNotColouredAndSingleSided) {
          printerItemsCollection.add(nextItem);
        }
      }
    }
  }

  /**
   * Adds A2 document jobs to the collecting parameter according to some policy that can be whatever the client
   * (the print center) wants. The code is similar to the 'addA4Papers' method. The code can be changed to accommodate
   * the wants of the client.
   *
   * @param printerItemsCollection the collecting parameter
   */
  public static void addA2Papers(Queue<PrinterItem> printerItemsCollection) {
    for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
      if (nextItem.paperSize.equals(PaperSizes.A2)) {

        // Encoding the policy into a Boolean: the A2 paper must be single page, single-sided, and non-coloured.
        var isNotColouredSingleSidedAndOnePage = nextItem.pageCount == 1 && !nextItem.isDoubleSided
                && !nextItem.isColour;
        if (isNotColouredSingleSidedAndOnePage) {
          printerItemsCollection.add(nextItem);
        }
      }
    }
  }
}
```

प्रत्येक विधि एक संग्रह पैरामीटर को तर्क के रूप में लेती है। इसके बाद यह वैश्विक चर से लिए गए तत्वों को जोड़ता है,
यदि प्रत्येक तत्व किसी दिए गए मानदंड को पूरा करता है तो इस संग्रहण पैरामीटर पर। इन विधियों में ग्राहक की इच्छानुसार कोई भी नीति हो सकती है।

इस प्रोग्रामेटिक उदाहरण में, तीन प्रिंट कार्य कतार में जोड़े गए हैं। केवल पहले दो प्रिंट कार्य ही जोड़े जाने चाहिए
नीति के अनुसार संग्रहण पैरामीटर। निष्पादन के बाद `result` चर के तत्व हैं,

| paperSize | pageCount | isDoubleSided | isColour |
|-----------|-----------|---------------|----------|
| A4        | 5         | false         | false    |
| A3        | 2         | false         | false    |

जिसकी हमें अपेक्षा थी।

## क्लास डायग्राम
![alt text](../../../collecting-parameter/etc/collecting-parameter.urm.png "Collecting Parameter")

## प्रयोज्यता
जब कलेक्टिंग पैरामीटर डिज़ाइन पैटर्न का उपयोग करें
- आप एक संग्रह या ऑब्जेक्ट वापस करना चाहते हैं जो कई विधियों का सहयोगात्मक परिणाम है
- आप एक ऐसी विधि को सरल बनाना चाहते हैं जो डेटा जमा करती है क्योंकि मूल विधि बहुत जटिल है

## ट्यूटोरियल
इस विधि के लिए ट्यूटोरियल यहां पाए जाते हैं:
- [रिफैक्टरिंग टू पैटर्न्स](http://www.tarrani.net/RefactoringToPatterns.pdf) जोशुआ केरिवस्की द्वारा
- [स्मॉलटॉक सर्वोत्तम अभ्यास पैटर्न] (https://ptgmedia.pearsoncmg.com/images/9780134769042/samplepages/013476904X.pdf) केंट बेक द्वारा

## ज्ञात उपयोग
जोशुआ केरिव्स्की ने अपनी पुस्तक 'रिफैक्टरिंग टू पैटर्न्स' में वास्तविक दुनिया का उदाहरण दिया है। वह इसका उपयोग करने का एक उदाहरण देता है
XML ट्री के लिए `toString()` विधि बनाने के लिए पैरामीटर डिज़ाइन पैटर्न एकत्रित करना। इस डिज़ाइन पैटर्न का उपयोग किए बिना,
इसके लिए सशर्त और संयोजन के साथ एक भारी फ़ंक्शन की आवश्यकता होगी जो कोड पठनीयता को खराब कर देगा। ऐसी विधि
इसे छोटी-छोटी विधियों में विभाजित किया जा सकता है, जिनमें से प्रत्येक जानकारी के अपने स्वयं के सेट को संग्रहण पैरामीटर में जोड़ता है।

इसे [रिफैक्टरिंग टू पैटर्न](http://www.tarrani.net/RefactoringToPatterns.pdf) में देखें।

## नतीजे
पेशेवर:
- कोड को अधिक पठनीय बनाता है
- 'लिंकेज' से बचें, जहां कई विधियां एक ही वैश्विक चर का संदर्भ देती हैं
- बड़े कार्यों को विघटित करके रखरखाव बढ़ाता है

दोष:
- कोड की लंबाई बढ़ सकती है
- तरीकों की 'परतें' जोड़ता है

## संबंधित पैटर्न
- [Compose Methods](https://www.geeksforgeeks.org/composite-design-pattern/)

## श्रेय
निम्नलिखित पुस्तकों का उपयोग किया गया:
- [Refactoring To Patterns](http://www.tarrani.net/RefactoringToPatterns.pdf) जोशुआ केरिवस्की द्वारा
- [Smalltalk Best Practice Patterns](https://ptgmedia.pearsoncmg.com/images/9780134769042/samplepages/013476904X.pdf) केंट बेक द्वारा
  साइटें:
- [Wiki](https://wiki.c2.com/?CollectingParameter)
