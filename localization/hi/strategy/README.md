---
layout: pattern
title: Strategy
folder: strategy
permalink: /patterns/strategy/hi
categories: Behavioral
language: hi
tags:
- Gang of Four
---

## इस रूप में भी जाना जाता है

Policy

## इरादा

एल्गोरिदम के एक परिवार को परिभाषित करें, प्रत्येक को इनकैप्सुलेट करें, और उन्हें विनिमेय बनाएं। रणनीति देता है
एल्गोरिथ्म इसका उपयोग करने वाले ग्राहकों से स्वतंत्र रूप से भिन्न होता है।

## व्याख्या

वास्तविक दुनिया का उदाहरण

> ड्रेगन को मारना एक खतरनाक काम है। अनुभव के साथ, यह आसान हो जाता है। अनुभवी व्यक्ति
> ड्रैगनस्लेयर्स ने विभिन्न प्रकार के ड्रेगन के खिलाफ अलग-अलग लड़ने की रणनीति विकसित की है।

सीधे शब्दों में

> रणनीति पैटर्न रनटाइम पर सबसे उपयुक्त एल्गोरिदम चुनने की अनुमति देता है।

विकिपीडिया कहता है

> कंप्यूटर प्रोग्रामिंग में, रणनीति पैटर्न (नीति पैटर्न के रूप में भी जाना जाता है) एक व्यवहार है
> सॉफ्टवेयर डिजाइन पैटर्न जो रनटाइम पर एक एल्गोरिथम का चयन करने में सक्षम बनाता है।

**प्रोग्रामेटिक उदाहरण**

आइए पहले `DragonSlayingInterface` और इसके कार्यान्वयन का परिचय दें।

```java
@FunctionalInterface
public interface DragonSlayingStrategy {

  void execute();
}

@Slf4j
public class MeleeStrategy implements DragonSlayingStrategy {

  @Override
  public void execute() {
    LOGGER.info("With your Excalibur you sever the dragon's head!");
  }
}

@Slf4j
public class ProjectileStrategy implements DragonSlayingStrategy {

  @Override
  public void execute() {
    LOGGER.info("You shoot the dragon with the magical crossbow and it falls dead on the ground!");
  }
}

@Slf4j
public class SpellStrategy implements DragonSlayingStrategy {

  @Override
  public void execute() {
    LOGGER.info("You cast the spell of disintegration and the dragon vaporizes in a pile of dust!");
  }
}
```

और यहाँ शक्तिशाली `DragonSlayer` है, जो के प्रतिद्वंद्वी के आधार पर अपनी लड़ाई की रणनीति चुन सकता है।
।

```java
public class DragonSlayer {

  private DragonSlayingStrategy strategy;

  public DragonSlayer(DragonSlayingStrategy strategy) {
    this.strategy = strategy;
  }

  public void changeStrategy(DragonSlayingStrategy strategy) {
    this.strategy = strategy;
  }

  public void goToBattle() {
    strategy.execute();
  }
}
```

अंत में, ड्रैगनस्लेयर उपयोग में है:

```java
    LOGGER.info("Green dragon spotted ahead!");
    var dragonSlayer = new DragonSlayer(new MeleeStrategy());
    dragonSlayer.goToBattle();
    LOGGER.info("Red dragon emerges.");
    dragonSlayer.changeStrategy(new ProjectileStrategy());
    dragonSlayer.goToBattle();
    LOGGER.info("Black dragon lands before you.");
    dragonSlayer.changeStrategy(new SpellStrategy());
    dragonSlayer.goToBattle();
```

प्रोग्राम आउटपुट:  
```
    Green dragon spotted ahead!
    With your Excalibur you sever the dragon's head!
    Red dragon emerges.
    You shoot the dragon with the magical crossbow and it falls dead on the ground!
    Black dragon lands before you.
    You cast the spell of disintegration and the dragon vaporizes in a pile of dust!    
```

इसके अलावा, जावा 8 में लैम्ब्डा एक्सप्रेशन कार्यान्वयन के लिए एक और दृष्टिकोण प्रदान करता है:

```java
public class LambdaStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(LambdaStrategy.class);

  public enum Strategy implements DragonSlayingStrategy {
    MeleeStrategy(() -> LOGGER.info(
        "With your Excalibur you severe the dragon's head!")),
    ProjectileStrategy(() -> LOGGER.info(
        "You shoot the dragon with the magical crossbow and it falls dead on the ground!")),
    SpellStrategy(() -> LOGGER.info(
        "You cast the spell of disintegration and the dragon vaporizes in a pile of dust!"));

    private final DragonSlayingStrategy dragonSlayingStrategy;

    Strategy(DragonSlayingStrategy dragonSlayingStrategy) {
      this.dragonSlayingStrategy = dragonSlayingStrategy;
    }

    @Override
    public void execute() {
      dragonSlayingStrategy.execute();
    }
  }
}
```

ड्रैगनस्लेयर उपयोग में है:

```java
    LOGGER.info("Green dragon spotted ahead!");
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.MeleeStrategy);
    dragonSlayer.goToBattle();
    LOGGER.info("Red dragon emerges.");
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.ProjectileStrategy);
    dragonSlayer.goToBattle();
    LOGGER.info("Black dragon lands before you.");
    dragonSlayer.changeStrategy(LambdaStrategy.Strategy.SpellStrategy);
    dragonSlayer.goToBattle();
```

प्रोग्राम आउटपुट उपरोक्त के समान है।

## कक्षा आरेख

![alt text](../../../strategy/etc/strategy_urm.png "Strategy")

## प्रयोज्यता

रणनीति पैटर्न का प्रयोग करें जब

* कई संबंधित वर्ग केवल उनके व्यवहार में भिन्न होते हैं। रणनीतियाँ एक वर्ग को कई व्यवहारों में से किसी एक को कॉन्फ़िगर करने का एक तरीका प्रदान करती हैं
* आपको एल्गोरिदम के विभिन्न रूपों की आवश्यकता है। उदाहरण के लिए, आप अलग-अलग स्पेस/टाइम ट्रेड-ऑफ को दर्शाने वाले एल्गोरिदम को परिभाषित कर सकते हैं। रणनीतियों का उपयोग तब किया जा सकता है जब इन प्रकारों को एल्गोरिदम के वर्ग पदानुक्रम के रूप में लागू किया जाता है
* एक एल्गोरिथ्म डेटा का उपयोग करता है जिसके बारे में ग्राहकों को पता नहीं होना चाहिए। जटिल एल्गोरिथम-विशिष्ट डेटा संरचनाओं को उजागर करने से बचने के लिए रणनीति पैटर्न का उपयोग करें
* एक वर्ग कई व्यवहारों को परिभाषित करता है, और ये इसके संचालन में कई सशर्त बयानों के रूप में दिखाई देते हैं। कई सशर्त के बजाय, संबंधित सशर्त शाखाओं को अपने स्वयं के रणनीति वर्ग में स्थानांतरित करें

## ट्यूटोरियल

* [Strategy Pattern Tutorial](https://www.journaldev.com/1754/strategy-design-pattern-in-java-example-tutorial)

## क्रेडिट

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expressions](https://www.amazon.com/gp/product/1937785467/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=1937785467&linkCode=as2&tag=javadesignpat-20&linkId=7e4e2fb7a141631491534255252fd08b)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
