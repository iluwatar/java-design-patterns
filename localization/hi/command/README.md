---
title: Command
category: Behavioral
language: hi
tag:
 - Gang of Four
---

## दूसरा नाम

क्रिया, लेन-देन

## हेतु

एक अनुरोध को एक ऑब्जेक्ट के रूप में इनकैप्सुलेट करें, जिससे आपको क्लाइंट को अलग-अलग पैरामीटराइज़ करने की सुविधा मिलती है
अनुरोध, कतार या लॉग अनुरोध, और पूर्ववत संचालन का समर्थन करते हैं।

## व्याख्या
वास्तविक दुनिया का उदाहरण

> एक जादूगर भूत पर जादू कर रहा है। मंत्रों को एक-एक करके भूत पर क्रियान्वित किया जाता है।
> पहला जादू भूत को सिकोड़ देता है और दूसरा उसे अदृश्य बना देता है। फिर विज़ार्ड उलट जाता है
> मंत्र एक-एक करके। यहां प्रत्येक मंत्र एक कमांड ऑब्जेक्ट है जिसे पूर्ववत किया जा सकता है।

साफ़ शब्दों में

> अनुरोधों को कमांड ऑब्जेक्ट के रूप में संग्रहीत करने से कोई कार्रवाई करने या बाद में उसे पूर्ववत करने की अनुमति मिलती है।

विकिपीडिया कहता है

> ऑब्जेक्ट-ओरिएंटेड प्रोग्रामिंग में, कमांड पैटर्न एक व्यवहारिक डिज़ाइन पैटर्न है जिसमें a
> ऑब्जेक्ट का उपयोग किसी क्रिया को करने या किसी ईवेंट को ट्रिगर करने के लिए आवश्यक सभी जानकारी को समाहित करने के लिए किया जाता है
> बाद में।

**प्रोग्रामेटिक उदाहरण**

यहां विज़ार्ड और गॉब्लिन के साथ नमूना कोड दिया गया है। आइए `Wizard` वर्ग से शुरू करें।

```java
@Slf4j
public class Wizard {

  private final Deque<Runnable> undoStack = new LinkedList<>();
  private final Deque<Runnable> redoStack = new LinkedList<>();

  public Wizard() {}

  public void castSpell(Runnable runnable) {
    runnable.run();
    undoStack.offerLast(runnable);
  }

  public void undoLastSpell() {
    if (!undoStack.isEmpty()) {
      var previousSpell = undoStack.pollLast();
      redoStack.offerLast(previousSpell);
      previousSpell.run();
    }
  }

  public void redoLastSpell() {
    if (!redoStack.isEmpty()) {
      var previousSpell = redoStack.pollLast();
      undoStack.offerLast(previousSpell);
      previousSpell.run();
    }
  }

  @Override
  public String toString() {
    return "Wizard";
  }
}
```

इसके बाद, हमारे पास वह भूत है जो मंत्रों का लक्ष्य है।

```java
@Slf4j
public abstract class Target {

  private Size size;

  private Visibility visibility;

  public Size getSize() {
    return size;
  }

  public void setSize(Size size) {
    this.size = size;
  }

  public Visibility getVisibility() {
    return visibility;
  }

  public void setVisibility(Visibility visibility) {
    this.visibility = visibility;
  }

  @Override
  public abstract String toString();

  public void printStatus() {
    LOGGER.info("{}, [size={}] [visibility={}]", this, getSize(), getVisibility());
  }
}

public class Goblin extends Target {

  public Goblin() {
    setSize(Size.NORMAL);
    setVisibility(Visibility.VISIBLE);
  }

  @Override
  public String toString() {
    return "Goblin";
  }

  public void changeSize() {
    var oldSize = getSize() == Size.NORMAL ? Size.SMALL : Size.NORMAL;
    setSize(oldSize);
  }

  public void changeVisibility() {
    var visible = getVisibility() == Visibility.INVISIBLE
          ? Visibility.VISIBLE : Visibility.INVISIBLE;
    setVisibility(visible);
  }
}
```

अंत में, हमारे पास मुख्य फ़ंक्शन कास्टिंग मंत्र में विज़ार्ड है।

```java
public static void main(String[] args) {
  var wizard = new Wizard();
  var goblin = new Goblin();

  // casts shrink/unshrink spell
  wizard.castSpell(goblin::changeSize);

  // casts visible/invisible spell
  wizard.castSpell(goblin::changeVisibility);

  // undo and redo casts
   wizard.undoLastSpell();
   wizard.redoLastSpell();
```

यहाँ कार्रवाई में पूरा उदाहरण है।

```java
var wizard = new Wizard();
var goblin = new Goblin();

goblin.printStatus();
wizard.castSpell(goblin::changeSize);
goblin.printStatus();

wizard.castSpell(goblin::changeVisibility);
goblin.printStatus();

wizard.undoLastSpell();
goblin.printStatus();

wizard.undoLastSpell();
goblin.printStatus();

wizard.redoLastSpell();
goblin.printStatus();

wizard.redoLastSpell();
goblin.printStatus();
```

यहाँ प्रोग्राम आउटपुट है:

```java
Goblin, [size=normal] [visibility=visible]
Goblin, [size=small] [visibility=visible]
Goblin, [size=small] [visibility=invisible]
Goblin, [size=small] [visibility=visible]
Goblin, [size=normal] [visibility=visible]
Goblin, [size=small] [visibility=visible]
Goblin, [size=small] [visibility=invisible]
```

## क्लास डायग्राम

![alt text](../../../command/etc/command.png "Command")

## प्रयोज्यता

जब आप चाहें तो कमांड पैटर्न का उपयोग करें:

* निष्पादित की जाने वाली क्रिया द्वारा वस्तुओं का मापन करें। आप ऐसे मानकीकरण को a में व्यक्त कर सकते हैं
  कॉलबैक फ़ंक्शन के साथ प्रक्रियात्मक भाषा, यानी, एक फ़ंक्शन जो कहीं पंजीकृत है
  बाद में बुलाया गया। कमांड कॉलबैक के लिए ऑब्जेक्ट-ओरिएंटेड प्रतिस्थापन हैं।
* अलग-अलग समय पर अनुरोध निर्दिष्ट करें, कतारबद्ध करें और निष्पादित करें। एक कमांड ऑब्जेक्ट में जीवन हो सकता है
  मूल अनुरोध से स्वतंत्र. यदि किसी अनुरोध के प्राप्तकर्ता को किसी पते में दर्शाया जा सकता है
  अंतरिक्ष-स्वतंत्र तरीके से, फिर आप अनुरोध के लिए एक कमांड ऑब्जेक्ट को एक अलग प्रक्रिया में स्थानांतरित कर सकते हैं
  और वहां अनुरोध पूरा करें।
* समर्थन पूर्ववत करें। कमांड का निष्पादन ऑपरेशन इसके प्रभावों को उलटने के लिए स्थिति को संग्रहीत कर सकता है
  स्वयं आदेश दें. कमांड इंटरफ़ेस में एक अतिरिक्त अन-एक्ज़िक्यूट ऑपरेशन होना चाहिए जो उलट देता है
  निष्पादित करने के लिए पिछली कॉल के प्रभाव। निष्पादित आदेश इतिहास सूची में संग्रहीत होते हैं।
  इस सूची को आगे और पीछे घुमाकर असीमित स्तर की पूर्ववत और पुनः करें कार्यक्षमता प्राप्त की जाती है
  क्रमशः अन-एक्ज़ीक्यूट और एक्ज़िक्यूट को कॉल करना।
* लॉगिंग परिवर्तनों का समर्थन करें ताकि सिस्टम क्रैश होने की स्थिति में उन्हें फिर से लागू किया जा सके। को बढ़ाकर
  लोड और स्टोर संचालन के साथ कमांड इंटरफ़ेस, आप परिवर्तनों का लगातार लॉग रख सकते हैं।
  क्रैश से उबरने में डिस्क से लॉग किए गए कमांड को फिर से लोड करना और उन्हें फिर से निष्पादित करना शामिल है
  निष्पादित ऑपरेशन.
* उच्च-स्तरीय संचालन के आसपास एक प्रणाली की संरचना करें जो आदिम संचालन पर आधारित हो। ऐसी संरचना है
  लेनदेन का समर्थन करने वाली सूचना प्रणालियों में आम है। एक लेन-देन डेटा के एक सेट को समाहित करता है
  परिवर्तन। कमांड पैटर्न लेनदेन को मॉडल करने का एक तरीका प्रदान करता है। कमांड का एक सामान्य इंटरफ़ेस होता है,
  आपको सभी लेन-देन एक ही तरीके से शुरू करने देता है। यह पैटर्न विस्तार करना भी आसान बनाता है
  नए लेनदेन के साथ प्रणाली.
* अनुरोधों का इतिहास रखें।
* कॉलबैक कार्यक्षमता लागू करें।
* पूर्ववत कार्यक्षमता लागू करें।

## ज्ञात उपयोग

* [java.lang.Runnable](http://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html)
* [org.junit.runners.model.Statement](https://github.com/junit-team/junit4/blob/master/src/main/java/org/junit/runners/model/Statement.java)
* [Netflix Hystrix](https://github.com/Netflix/Hystrix/wiki)
* [javax.swing.Action](http://docs.oracle.com/javase/8/docs/api/javax/swing/Action.html)

## श्रेय

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
