---
layout: pattern
title: Prototype
folder: prototype
permalink: /patterns/prototype/hi
categories: Creational
language: hi
tags:
- Gang Of Four
- Instantiation
---

## इरादा

एक प्रोटोटाइप उदाहरण का उपयोग करके बनाने के लिए ऑब्जेक्ट के प्रकार निर्दिष्ट करें, और द्वारा नई ऑब्जेक्ट बनाएं
इस प्रोटोटाइप की नकल।

## व्याख्या

सबसे पहले, यह ध्यान दिया जाना चाहिए कि प्रदर्शन लाभ प्राप्त करने के लिए प्रोटोटाइप पैटर्न का उपयोग नहीं किया जाता है। यह केवल है
प्रोटोटाइप उदाहरणों से नई वस्तुओं को बनाने के लिए उपयोग किया जाता है।

वास्तविक दुनिया का उदाहरण

> डॉली याद है? जिस भेड़ का क्लोन बनाया गया था! आइए विवरण में न जाएं लेकिन यहां मुख्य बिंदु है
> कि यह सब क्लोनिंग के बारे में है।

सीधे शब्दों में

> क्लोनिंग के माध्यम से मौजूदा ऑब्जेक्ट के आधार पर ऑब्जेक्ट बनाएं।

विकिपीडिया कहता है

> प्रोटोटाइप पैटर्न सॉफ्टवेयर विकास में एक रचनात्मक डिजाइन पैटर्न है। इसका उपयोग तब किया जाता है जब
> बनाने के लिए वस्तुओं का प्रकार एक प्रोटोटाइपिक उदाहरण द्वारा निर्धारित किया जाता है, जिसे नया उत्पादन करने के लिए क्लोन किया जाता है
> वस्तुओं।

संक्षेप में, यह आपको किसी मौजूदा वस्तु की एक प्रति बनाने और इसे अपनी आवश्यकताओं के अनुसार संशोधित करने की अनुमति देता है, इसके बजाय
किसी वस्तु को खरोंच से बनाने और उसे स्थापित करने की परेशानी से गुजरना।

**प्रोग्रामेटिक उदाहरण**

जावा में, प्रोटोटाइप पैटर्न को निम्नानुसार लागू करने की अनुशंसा की जाती है। सबसे पहले, एक बनाएं
वस्तुओं की क्लोनिंग के लिए एक विधि के साथ इंटरफेस। इस उदाहरण में, `Prototype` इंटरफ़ेस पूरा करता है
इसकी `copy` विधि के साथ।


```java
public abstract class Prototype<T> implements Cloneable {
    @SneakyThrows
    public T copy() {
        return (T) super.clone();
    }
}
```
हमारे उदाहरण में विभिन्न प्राणियों का पदानुक्रम है। उदाहरण के लिए, आइए `Beast` और . को देखें
`OrcBeast` कक्षाएं।

```java
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public abstract class Beast extends Prototype<Beast> {

  public Beast(Beast source) {
  }

}

@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class OrcBeast extends Beast {

  private final String weapon;

  public OrcBeast(OrcBeast orcBeast) {
    super(orcBeast);
    this.weapon = orcBeast.weapon;
  }

  @Override
  public String toString() {
    return "Orcish wolf attacks with " + weapon;
  }

}
```
हम बहुत अधिक विवरण में नहीं जाना चाहते हैं, लेकिन पूर्ण उदाहरण में आधार वर्ग `Mage` भी शामिल है
और `Warlord` और orcs के अलावा कल्पित बौने के लिए विशेष कार्यान्वयन हैं।

प्रोटोटाइप पैटर्न का पूरा लाभ उठाने के लिए, हम `HeroFactory` और `HeroFactoryImpl` बनाते हैं
प्रोटोटाइप से विभिन्न प्रकार के जीवों का उत्पादन करने के लिए कक्षाएं।

```java
public interface HeroFactory {
  
  Mage createMage();
  Warlord createWarlord();
  Beast createBeast();
}

@RequiredArgsConstructor
public class HeroFactoryImpl implements HeroFactory {

  private final Mage mage;
  private final Warlord warlord;
  private final Beast beast;

  public Mage createMage() {
    return mage.copy();
  }

  public Warlord createWarlord() {
    return warlord.copy();
  }

  public Beast createBeast() {
    return beast.copy();
  }
}
```

अब, हम क्लोनिंग द्वारा नए जीवों को बनाने वाली क्रिया में पूर्ण प्रोटोटाइप पैटर्न दिखाने में सक्षम हैं
मौजूदा उदाहरण।

```java
    var factory = new HeroFactoryImpl(
        new ElfMage("cooking"),
        new ElfWarlord("cleaning"),
        new ElfBeast("protecting")
    );
    var mage = factory.createMage();
    var warlord = factory.createWarlord();
    var beast = factory.createBeast();
    LOGGER.info(mage.toString());
    LOGGER.info(warlord.toString());
    LOGGER.info(beast.toString());

    factory = new HeroFactoryImpl(
        new OrcMage("axe"),
        new OrcWarlord("sword"),
        new OrcBeast("laser")
    );
    mage = factory.createMage();
    warlord = factory.createWarlord();
    beast = factory.createBeast();
    LOGGER.info(mage.toString());
    LOGGER.info(warlord.toString());
    LOGGER.info(beast.toString());
```

उदाहरण चलाने से कंसोल आउटपुट यहां दिया गया है।

```
Elven mage helps in cooking
Elven warlord helps in cleaning
Elven eagle helps in protecting
Orcish mage attacks with axe
Orcish warlord attacks with sword
Orcish wolf attacks with laser
```

## वर्ग आरेख

![alt text](../../../prototype/etc/prototype.urm.png "Prototype pattern class diagram")

## प्रयोज्यता

प्रोटोटाइप पैटर्न का उपयोग तब करें जब कोई सिस्टम इस बात से स्वतंत्र हो कि उसके उत्पाद कैसे बनाए जाते हैं,
रचित, प्रतिनिधित्व और

* जब क्लास को इंस्टेंट करने के लिए रन-टाइम पर निर्दिष्ट किया जाता है, उदाहरण के लिए, डायनेमिक लोडिंग द्वारा।
* कारखानों के वर्ग पदानुक्रम के निर्माण से बचने के लिए जो उत्पादों के वर्ग पदानुक्रम के समानांतर हैं।
* जब किसी वर्ग के उदाहरणों में राज्य के केवल कुछ भिन्न संयोजनों में से एक हो सकता है। हो सकता है
  प्रोटोटाइप की इसी संख्या को स्थापित करने और उन्हें क्लोन करने के बजाय अधिक सुविधाजनक है
  प्रत्येक बार उपयुक्त स्थिति के साथ कक्षा को मैन्युअल रूप से तत्काल करना।
* जब क्लोनिंग की तुलना में वस्तु निर्माण महंगा हो।

## ज्ञात उपयोग

* [java.lang.Object#clone()](http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#clone%28%29)

## क्रेडिट

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
