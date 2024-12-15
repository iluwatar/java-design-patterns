---
title: Builder
shortTitle: Builder
category: Creational
language: ar
tag:
  - Gang of Four
---

## الهدف

فصل بناء كائن معقد عن تمثيله بحيث يمكن لنفس عملية البناء إنشاء تمثيلات مختلفة.

## الشرح

مثال من الحياة الواقعية

> تخيل مولد شخصيات للعبة تقمص أدوار. الخيار الأسهل هو السماح للكمبيوتر بإنشاء الشخصية نيابة عنك. إذا أردت تحديد تفاصيل الشخصية يدويًا مثل المهنة، الجنس، لون الشعر، إلخ، فإن إنشاء الشخصية يصبح عملية خطوة بخطوة تكتمل عندما تكون جميع الاختيارات جاهزة.

ببساطة

> يسمح بإنشاء نكهات مختلفة من كائن دون تلويث الباني. مفيد عندما يمكن أن يكون هناك عدة نكهات لكائن ما، أو عندما تكون هناك العديد من الخطوات المعنية في إنشاء الكائن.

تقول ويكيبيديا

> نمط البناء هو نمط تصميم برمجي لإنشاء الكائنات بهدف إيجاد حل لمضاد النمط الخاص بالباني المنطقي.

مع ذلك، دعني أضيف بعض المعلومات حول ما هو مضاد النمط للباني المنطقي. في وقت ما أو آخر، رأينا جميعًا بانيًا مثل التالي:


```java
public Hero(Profession profession, String name, HairType hairType, HairColor hairColor, Armor armor, Weapon weapon) {
}
```

كما ترى، قد يخرج عدد معلمات الباني عن السيطرة بسرعة، وقد يصبح من الصعب فهم ترتيب المعلمات. بالإضافة إلى ذلك، قد تستمر هذه القائمة في النمو إذا أردت إضافة المزيد من الخيارات في المستقبل. يسمى هذا مضاد النمط للباني المنطقي.

**مثال برمجي**

البديل الحكيم هو استخدام نمط Builder. أولاً، لدينا بطلنا `Hero` الذي نريد إنشائه:


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

ثم لدينا الباني:


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

إذن يمكن استخدامه كما يلي:


```java
var mage = new Hero.Builder(Profession.MAGE, "Riobard").withHairColor(HairColor.BLACK).withWeapon(Weapon.DAGGER).build();
```

## مخطط الفئات

![alt text](./etc/builder.urm.png "مخطط فئات Builder")

## القابلية للتطبيق

استخدم نمط Builder عندما

* يجب أن يكون الخوارزمية لإنشاء كائن معقدة مستقلة عن الأجزاء التي تتكون منها الكائن وكيفية تجميعها.
* يجب أن تسمح عملية البناء بتمثيلات مختلفة للكائن الذي يتم بناؤه.

## الدروس التعليمية

* [Refactoring Guru](https://refactoring.guru/design-patterns/builder)
* [مدونة Oracle](https://blogs.oracle.com/javamagazine/post/exploring-joshua-blochs-builder-design-pattern-in-java)
* [Journal Dev](https://www.journaldev.com/1425/builder-design-pattern-in-java)

## الاستخدامات في العالم الواقعي

* [java.lang.StringBuilder](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html)
* [java.nio.ByteBuffer](http://docs.oracle.com/javase/8/docs/api/java/nio/ByteBuffer.html#put-byte-) بالإضافة إلى غيرها من المخازن المؤقتة مثل FloatBuffer و IntBuffer، إلخ.
* [java.lang.StringBuffer](http://docs.oracle.com/javase/8/docs/api/java/lang/StringBuffer.html#append-boolean-)
* جميع التطبيقات من [java.lang.Appendable](http://docs.oracle.com/javase/8/docs/api/java/lang/Appendable.html)
* [بناة Apache Camel](https://github.com/apache/camel/tree/0e195428ee04531be27a0b659005e3aa8d159d23/camel-core/src/main/java/org/apache/camel/builder)
* [Apache Commons Option.Builder](https://commons.apache.org/proper/commons-cli/apidocs/org/apache/commons/cli/Option.Builder.html)

## الاعتمادات

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
