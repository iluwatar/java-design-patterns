---
title: Builder
category: Creational
language: hi
tag:
 - Gang of Four
---

## हेतु

किसी जटिल वस्तु के निर्माण को उसके प्रतिनिधित्व से अलग करें ताकि निर्माण समान हो
प्रक्रिया विभिन्न अभ्यावेदन बना सकती है।

## व्याख्या

वास्तविक दुनिया का उदाहरण

> रोल-प्लेइंग गेम के लिए एक चरित्र जनरेटर की कल्पना करें। सबसे आसान विकल्प कंप्यूटर को चालू करना है
> अपने लिए चरित्र बनाएं. यदि आप मैन्युअल रूप से चरित्र विवरण का चयन करना चाहते हैं जैसे
> पेशा, लिंग, बालों का रंग आदि, चरित्र निर्माण एक चरण-दर-चरण प्रक्रिया बन जाती है
> सभी चयन तैयार होने पर पूरा होता है।

साफ़ शब्दों में

> कंस्ट्रक्टर प्रदूषण से बचते हुए आपको किसी वस्तु के विभिन्न स्वाद बनाने की अनुमति देता है। उपयोगी
>जब किसी वस्तु के कई स्वाद हो सकते हैं। या जब इसमें बहुत सारे चरण शामिल हों
> किसी वस्तु का निर्माण।

विकिपीडिया कहता है

> बिल्डर पैटर्न खोजने के इरादे से एक ऑब्जेक्ट निर्माण सॉफ़्टवेयर डिज़ाइन पैटर्न है
> टेलिस्कोपिंग कंस्ट्रक्टर एंटी-पैटर्न का समाधान।

यह कहने के बाद, मैं इस बारे में थोड़ा जोड़ना चाहूंगा कि टेलीस्कोपिंग कंस्ट्रक्टर एंटी-पैटर्न क्या है। एक बिंदु पर
या अन्य, हम सभी ने नीचे जैसा एक कंस्ट्रक्टर देखा है:

```java
public Hero(Profession profession, String name, HairType hairType, HairColor hairColor, Armor armor, Weapon weapon) {
}
```

जैसा कि आप देख सकते हैं, कंस्ट्रक्टर मापदंडों की संख्या जल्दी से नियंत्रण से बाहर हो सकती है, और यह बन सकती है
मापदंडों की व्यवस्था को समझना कठिन है। साथ ही यह पैरामीटर सूची चालू रह सकती है
यदि आप भविष्य में और विकल्प जोड़ना चाहेंगे तो बढ़ रहा हूँ। इसे टेलिस्कोपिंग कंस्ट्रक्टर कहा जाता है
विरोधी पैटर्न.

**प्रोग्रामेटिक उदाहरण**

बिल्डर पैटर्न का उपयोग करना समझदारी भरा विकल्प है। सबसे पहले, हमारे पास अपना हीरो है जिसे हम चाहते हैं
बनाएं:

```java
public final class Hero {
  private final Profession profession;
  private final String name;
  private final HairType hairType;
  private final HairColor hairColor;
  private final Armor armor;
  private final Weapon weapon;

  private Hero(Builder builder) {
    this.profession = builder.profession;
    this.name = builder.name;
    this.hairColor = builder.hairColor;
    this.hairType = builder.hairType;
    this.weapon = builder.weapon;
    this.armor = builder.armor;
  }
}
```

फिर हमारे पास बिल्डर है:

```java
  public static class Builder {
    private final Profession profession;
    private final String name;
    private HairType hairType;
    private HairColor hairColor;
    private Armor armor;
    private Weapon weapon;

    public Builder(Profession profession, String name) {
      if (profession == null || name == null) {
        throw new IllegalArgumentException("profession and name can not be null");
      }
      this.profession = profession;
      this.name = name;
    }

    public Builder withHairType(HairType hairType) {
      this.hairType = hairType;
      return this;
    }

    public Builder withHairColor(HairColor hairColor) {
      this.hairColor = hairColor;
      return this;
    }

    public Builder withArmor(Armor armor) {
      this.armor = armor;
      return this;
    }

    public Builder withWeapon(Weapon weapon) {
      this.weapon = weapon;
      return this;
    }

    public Hero build() {
      return new Hero(this);
    }
  }
```

तब इसका उपयोग इस प्रकार किया जा सकता है:

```java
var mage = new Hero.Builder(Profession.MAGE, "Riobard").withHairColor(HairColor.BLACK).withWeapon(Weapon.DAGGER).build();
```

## क्लास डायग्राम

![alt text](../../../builder/etc/builder.urm.png "Builder class diagram")

## प्रयोज्यता

जब बिल्डर पैटर्न का उपयोग करें

* एक जटिल वस्तु बनाने के लिए एल्गोरिदम उन हिस्सों से स्वतंत्र होना चाहिए जो वस्तु बनाते हैं और उन्हें कैसे इकट्ठा किया जाता है
* निर्माण प्रक्रिया में निर्मित वस्तु के लिए अलग-अलग प्रतिनिधित्व की अनुमति होनी चाहिए

## ट्यूटोरियल

* [Refactoring Guru](https://refactoring.guru/design-patterns/builder)
* [Oracle Blog](https://blogs.oracle.com/javamagazine/post/exploring-joshua-blochs-builder-design-pattern-in-java)
* [Journal Dev](https://www.journaldev.com/1425/builder-design-pattern-in-java)

## ज्ञात उपयोग

* [java.lang.StringBuilder](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html)
* [java.nio.ByteBuffer](http://docs.oracle.com/javase/8/docs/api/java/nio/ByteBuffer.html#put-byte-) साथ ही समान बफ़र्स जैसे फ़्लोटबफ़र, इंटबफ़र इत्यादि।
* [java.lang.StringBuffer](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuffer.html#append-boolean-)
* के सभी कार्यान्वयन [java.lang.Appendable](http://docs.oracle.com/javase/8/docs/api/java/lang/Appendable.html)
* [Apache Camel builders](https://github.com/apache/camel/tree/0e195428ee04531be27a0b659005e3aa8d159d23/camel-core/src/main/java/org/apache/camel/builder)
* [Apache Commons Option.Builder](https://commons.apache.org/proper/commons-cli/apidocs/org/apache/commons/cli/Option.Builder.html)

## श्रेय

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
