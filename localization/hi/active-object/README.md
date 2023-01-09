---
title: Active Object
category: Concurrency
language: hi
tag:
 - Performance
---


## हेतु
सक्रिय ऑब्जेक्ट डिज़ाइन पैटर्न उन वस्तुओं के लिए विधि आमंत्रण से विधि निष्पादन को अलग करता है जो प्रत्येक अपने नियंत्रण के धागे में रहते हैं। लक्ष्य एसिंक्रोनस विधि आमंत्रण और अनुरोधों को संभालने के लिए शेड्यूलर का उपयोग करके समवर्तीता पेश करना है।

## व्याख्या

सक्रिय ऑब्जेक्ट पैटर्न को लागू करने वाली कक्षा में 'सिंक्रनाइज़' विधियों का उपयोग किए बिना एक स्व-सिंक्रनाइज़ेशन तंत्र होगा।

वास्तविक दुनिया का उदाहरण

> ओर्क्स अपने जंगलीपन और अदम्य आत्मा के लिए जाने जाते हैं। ऐसा लगता है जैसे उनके पास पिछले व्यवहार के आधार पर नियंत्रण का अपना सूत्र है।

एक प्राणी को लागू करने के लिए जिसके पास नियंत्रण तंत्र का अपना धागा है और केवल अपने एपीआई का पर्दाफाश करता है न कि स्वयं निष्पादन, हम सक्रिय ऑब्जेक्ट पैटर्न का उपयोग कर सकते हैं।


**Programmatic Example**

```java
public abstract class ActiveCreature{
  private final Logger logger = LoggerFactory.getLogger(ActiveCreature.class.getName());

  private BlockingQueue<Runnable> requests;
  
  private String name;
  
  private Thread thread;

  public ActiveCreature(String name) {
    this.name = name;
    this.requests = new LinkedBlockingQueue<Runnable>();
    thread = new Thread(new Runnable() {
        @Override
        public void run() {
          while (true) {
            try {
              requests.take().run();
            } catch (InterruptedException e) { 
              logger.error(e.getMessage());
            }
          }
        }
      }
    );
    thread.start();
  }
  
  public void eat() throws InterruptedException {
    requests.put(new Runnable() {
        @Override
        public void run() { 
          logger.info("{} is eating!",name());
          logger.info("{} has finished eating!",name());
        }
      }
    );
  }

  public void roam() throws InterruptedException {
    requests.put(new Runnable() {
        @Override
        public void run() { 
          logger.info("{} has started to roam the wastelands.",name());
        }
      }
    );
  }
  
  public String name() {
    return this.name;
  }
}
```

हम देख सकते हैं कि कोई भी वर्ग जो ActiveCreature वर्ग का विस्तार करेगा, उसके पास तरीकों को लागू करने और निष्पादित करने के लिए नियंत्रण का अपना धागा होगा।


उदाहरण के लिए, Orc क्लास:

```java
public class Orc extends ActiveCreature {

  public Orc(String name) {
    super(name);
  }

}
```

अब, हम Orcs जैसे कई जीव बना सकते हैं, उन्हें खाने और घूमने के लिए कह सकते हैं, और वे इसे अपने नियंत्रण के thread पर क्रियान्वित करेंगे:

```java
  public static void main(String[] args) {  
    var app = new App();
    app.run();
  }
  
  @Override
  public void run() {
    ActiveCreature creature;
    try {
      for (int i = 0;i < creatures;i++) {
        creature = new Orc(Orc.class.getSimpleName().toString() + i);
        creature.eat();
        creature.roam();
      }
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
    }
    Runtime.getRuntime().exit(1);
  }
```

## क्लास डायग्राम

![alt text](../../../active-object/etc/active-object.urm.png "Active Object class diagram")

## ट्यूटोरियल

* [Android and Java Concurrency: The Active Object Pattern](https://www.youtube.com/watch?v=Cd8t2u5Qmvc)