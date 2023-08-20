---
title: Bridge
category: Structural
language: hi
tag:
 - Gang of Four
---

## दूसरा नाम

हैंडल/बॉडी

## हेतु

इसके कार्यान्वयन से एक अमूर्त को अलग करें ताकि दोनों स्वतंत्र रूप से भिन्न हो सकें।

## व्याख्या

वास्तविक दुनिया का उदाहरण

> विचार करें कि आपके पास विभिन्न जादूओं वाला एक हथियार है, और आपको मिश्रण की अनुमति देनी चाहिए
> अलग-अलग जादू वाले अलग-अलग हथियार। आप क्या करेंगे? प्रत्येक की एकाधिक प्रतिलिपियाँ बनाएँ
> प्रत्येक जादू के लिए हथियारों का या आप बस अलग-अलग जादू और सेट बनाएंगे
> क्या यह आवश्यकतानुसार हथियार के लिए है? ब्रिज पैटर्न आपको दूसरा करने की अनुमति देता है।

सादे शब्दों में

> ब्रिज पैटर्न वंशानुक्रम पर संरचना को प्राथमिकता देने के बारे में है। कार्यान्वयन विवरण आगे बढ़ाए गए हैं
> एक पदानुक्रम से दूसरे ऑब्जेक्ट पर एक अलग पदानुक्रम के साथ।

विकिपीडिया कहता है

> ब्रिज पैटर्न सॉफ्टवेयर इंजीनियरिंग में उपयोग किया जाने वाला एक डिज़ाइन पैटर्न है जिसका अर्थ है "इसके कार्यान्वयन से एक अमूर्तता को अलग करना ताकि दोनों स्वतंत्र रूप से भिन्न हो सकें"

**प्रोग्रामेटिक उदाहरण**

ऊपर से हमारे हथियार उदाहरण का अनुवाद। यहां हमारे पास `Weapon` पदानुक्रम है:

```java
public interface Weapon {
  void wield();
  void swing();
  void unwield();
  Enchantment getEnchantment();
}

public class Sword implements Weapon {

  private final Enchantment enchantment;

  public Sword(Enchantment enchantment) {
    this.enchantment = enchantment;
  }

  @Override
  public void wield() {
    LOGGER.info("The sword is wielded.");
    enchantment.onActivate();
  }

  @Override
  public void swing() {
    LOGGER.info("The sword is swinged.");
    enchantment.apply();
  }

  @Override
  public void unwield() {
    LOGGER.info("The sword is unwielded.");
    enchantment.onDeactivate();
  }

  @Override
  public Enchantment getEnchantment() {
    return enchantment;
  }
}

public class Hammer implements Weapon {

  private final Enchantment enchantment;

  public Hammer(Enchantment enchantment) {
    this.enchantment = enchantment;
  }

  @Override
  public void wield() {
    LOGGER.info("The hammer is wielded.");
    enchantment.onActivate();
  }

  @Override
  public void swing() {
    LOGGER.info("The hammer is swinged.");
    enchantment.apply();
  }

  @Override
  public void unwield() {
    LOGGER.info("The hammer is unwielded.");
    enchantment.onDeactivate();
  }

  @Override
  public Enchantment getEnchantment() {
    return enchantment;
  }
}
```

यहां अलग मंत्रमुग्धता पदानुक्रम है:

```java
public interface Enchantment {
  void onActivate();
  void apply();
  void onDeactivate();
}

public class FlyingEnchantment implements Enchantment {

  @Override
  public void onActivate() {
    LOGGER.info("The item begins to glow faintly.");
  }

  @Override
  public void apply() {
    LOGGER.info("The item flies and strikes the enemies finally returning to owner's hand.");
  }

  @Override
  public void onDeactivate() {
    LOGGER.info("The item's glow fades.");
  }
}

public class SoulEatingEnchantment implements Enchantment {

  @Override
  public void onActivate() {
    LOGGER.info("The item spreads bloodlust.");
  }

  @Override
  public void apply() {
    LOGGER.info("The item eats the soul of enemies.");
  }

  @Override
  public void onDeactivate() {
    LOGGER.info("Bloodlust slowly disappears.");
  }
}
```

यहां दोनों पदानुक्रम क्रियान्वित हैं:

```java
LOGGER.info("The knight receives an enchanted sword.");
var enchantedSword = new Sword(new SoulEatingEnchantment());
enchantedSword.wield();
enchantedSword.swing();
enchantedSword.unwield();

LOGGER.info("The valkyrie receives an enchanted hammer.");
var hammer = new Hammer(new FlyingEnchantment());
hammer.wield();
hammer.swing();
hammer.unwield();
```

यहाँ कंसोल आउटपुट है.

```
The knight receives an enchanted sword.
The sword is wielded.
The item spreads bloodlust.
The sword is swung.
The item eats the soul of enemies.
The sword is unwielded.
Bloodlust slowly disappears.
The valkyrie receives an enchanted hammer.
The hammer is wielded.
The item begins to glow faintly.
The hammer is swung.
The item flies and strikes the enemies finally returning to owner's hand.
The hammer is unwielded.
The item's glow fades.
```

## क्लास डायग्राम

![alt text](../../../bridge/etc/bridge.urm.png "Bridge class diagram")

## प्रयोज्यता

जब ब्रिज पैटर्न का प्रयोग करें

* आप किसी अमूर्तता और उसके कार्यान्वयन के बीच स्थायी बंधन से बचना चाहते हैं। यह मामला हो सकता है, उदाहरण के लिए, जब कार्यान्वयन को रन-टाइम पर चुना या स्विच किया जाना चाहिए।
* अमूर्तीकरण और उनके कार्यान्वयन दोनों को उपवर्गीकरण द्वारा विस्तार योग्य होना चाहिए। इस मामले में, ब्रिज पैटर्न आपको विभिन्न अमूर्तताओं और कार्यान्वयनों को संयोजित करने और उन्हें स्वतंत्र रूप से विस्तारित करने की सुविधा देता है।
* किसी अमूर्त के कार्यान्वयन में परिवर्तन का ग्राहकों पर कोई प्रभाव नहीं पड़ना चाहिए; अर्थात्, उनके कोड को पुनः संकलित नहीं करना पड़ेगा।
* आपके पास वर्गों का प्रसार है। ऐसा वर्ग पदानुक्रम किसी वस्तु को दो भागों में विभाजित करने की आवश्यकता को इंगित करता है। रुंबॉघ ऐसे वर्ग पदानुक्रमों को संदर्भित करने के लिए "नेस्टेड सामान्यीकरण" शब्द का उपयोग करता है।
* आप एक कार्यान्वयन को कई ऑब्जेक्ट्स के बीच साझा करना चाहते हैं (शायद संदर्भ गणना का उपयोग करके), और यह तथ्य क्लाइंट से छिपा होना चाहिए। एक सरल उदाहरण कोप्लियन का स्ट्रिंग क्लास है, जिसमें कई ऑब्जेक्ट एक ही स्ट्रिंग प्रतिनिधित्व साझा कर सकते हैं।

## ट्यूटोरियल

* [Bridge Pattern Tutorial](https://www.journaldev.com/1491/bridge-design-pattern-java)

## श्रेय

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
