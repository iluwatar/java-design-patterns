---
title: Adapter
shortTitle: Adapter
category: Structural
language: ar
tag:
 - Gang of Four
---

## أيضًا معروف بـ
الغطاء (Wrapper)

## الهدف
تحويل واجهة فئة إلى واجهة أخرى يتوقعها العميل. يتيح نمط المحول (Adapter) للفئات العمل مع فئات أخرى التي لا يمكنها العمل معها في الظروف العادية بسبب مشاكل التوافق.

## التوضيح

مثال من العالم الحقيقي

> تخيل أنك تمتلك بعض الصور في بطاقة ذاكرة وتريد نقلها إلى جهاز الكمبيوتر الخاص بك. لنقل الصور، تحتاج إلى نوع من المحول الذي يتوافق مع منافذ جهاز الكمبيوتر الخاص بك ويسمح لك بإدخال البطاقة. في هذه الحالة، قارئ البطاقات هو محول (Adapter).
> مثال آخر هو محول التيار الكهربائي؛ إذا كان هناك قابس بثلاثة دبابيس ولا يمكن توصيله بمنفذ كهربائي به ثقبين، فإنه يحتاج إلى محول لجعله متوافقًا مع المنفذ.
> مثال آخر هو مترجم يترجم كلمات من شخص لآخر.

بصيغة أخرى

> يتيح نمط المحول (Adapter) تغليف كائن داخل محول لجعله متوافقًا مع فئة سيكون غير متوافق معها بطريقة أخرى.

حسب ويكيبيديا

> في هندسة البرمجيات، نمط المحول هو نمط تصميم برمجي يسمح باستخدام واجهة فئة موجودة كواجهة مختلفة. وغالبًا ما يُستخدم لجعل الفئات الموجودة تعمل مع فئات أخرى دون الحاجة إلى تعديل كود المصدر الخاص بها.

**مثال برمجي**

خذ على سبيل المثال قبطان يمكنه فقط استخدام القوارب التي تعمل بالمجاديف ولا يمكنه الإبحار على الإطلاق.

أولاً، لدينا الواجهات `RowingBoat` (قارب المجاديف) و `FishingBoat` (قارب الصيد).

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

ويتوقع القبطان تنفيذ واجهة `RowingBoat` (قارب المجاديف) ليتمكن من التحرك.


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

الآن لنفترض أن مجموعة من القراصنة قد جاءت ويجب على قائدنا الهروب، ولكن هناك فقط قارب صيد. نحتاج إلى إنشاء محول يسمح للقائد باستخدام قارب الصيد مع مهاراته لاستخدام القوارب التي تعمل بالمجاديف.


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

والآن يمكن لـ `Captain` (القائد) استخدام `FishingBoat` (قارب الصيد) للهروب من القراصنة.


```java
var captain = new Captain(new FishingBoatAdapter());
captain.row();
```

## مخطط الفئات
![alt text](./etc/adapter.urm.png "Adapter class diagram")

## التطبيق
استخدم نمط المحول (Adapter) عندما:

* تريد استخدام فئة موجودة وواجهتها لا تتناسب مع ما تحتاجه.
* تريد إنشاء فئة قابلة لإعادة الاستخدام تتعاون مع فئات غير مرتبطة أو لم يكن من المخطط تعاونها معًا، أي فئات ليس لديها بالضرورة واجهات متوافقة.
* تحتاج إلى استخدام عدة فئات فرعية موجودة، ولكن من غير العملي تعديل واجهتها عن طريق إنشاء فئات فرعية لكل واحدة. يمكن للمحول تعديل واجهة الفئة الأصلية.
* العديد من التطبيقات التي تستخدم مكتبات طرف ثالث تستخدم المحولات كطبقات وسيطة بين التطبيق والمكتبة لفصل التطبيق عن المكتبة. إذا كان من الضروري استخدام مكتبة أخرى، يكفي إنشاء محول للمكتبة الجديدة دون الحاجة إلى تعديل كود التطبيق.

## الدروس


* [Dzone](https://dzone.com/articles/adapter-design-pattern-in-java)
* [Refactoring Guru](https://refactoring.guru/design-patterns/adapter/java/example)
* [Baeldung](https://www.baeldung.com/java-adapter-pattern)

## العواقب
المحولات بين الفئات والكائنات لها خصائص مختلفة. محول الفئات

* يقوم بإجراء التكيف ويرتبط بفئة محددة. كنتيجة لذلك، لن يعمل محول الفئات عندما نريد تكيف فئة وفئاتها الفرعية.
* يسمح للمحول بتعديل سلوك الفئة المتكيفة لأن المحول هو فئة فرعية من الفئة المتكيفة.
* يستخدم كائنًا واحدًا ولا يحتاج إلى استخدام مؤشرات إضافية للإشارة إلى الفئة المتكيفة.

محوّل الكائنات

* يسمح للمحول الواحد بالعمل مع العديد من الفئات، أي مع الفئة المتكيفة وكل الفئات الفرعية لها (إذا كانت موجودة). يمكن للمحول أيضًا إضافة وظائف إلى جميع الفئات المتكيفة في نفس الوقت.
* يجعل من الصعب تعديل سلوك الفئة المتكيفة. سيكون من الضروري إنشاء فئة فرعية للفئة المتكيفة وجعل المحول يشير إلى الفئة الفرعية بدلاً من الفئة الأصلية.

## أمثلة من العالم الواقعي


* [java.util.Arrays#asList()](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList%28T...%29)
* [java.util.Collections#list()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#list-java.util.Enumeration-)
* [java.util.Collections#enumeration()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-)
* [javax.xml.bind.annotation.adapters.XMLAdapter](http://docs.oracle.com/javase/8/docs/api/javax/xml/bind/annotation/adapters/XmlAdapter.html#marshal-BoundType-)


## الشكر

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
