---
title: Active Object
category: Concurrency
language: en
tag:
 - Performance
---

## ارادہ

فعال آبجیکٹ ڈیزائن پیٹرن ان اشیاء کے لیے طریقہ کار کی درخواست سے طریقہ کار پر عمل درآمد کو الگ کرتا ہے جو ہر ایک اپنے کنٹرول کے دھاگے میں رہتی ہے۔
مقصد یہ ہے کہ ہم آہنگی کو متعارف کرایا جائے، غیر مطابقت پذیر طریقہ کی درخواست کا استعمال کرتے ہوئے، اور درخواستوں کو ہینڈل کرنے کے لیے شیڈولر۔

## وضاحت

کلاس جو فعال آبجیکٹ پیٹرن کو نافذ کرتی ہے اس میں 'مطابقت پذیر' طریقوں کا استعمال کیے بغیر خود ہم آہنگی کا طریقہ کار ہوگا۔

حقیقی مثال

> آرکس اپنی جنگلی پن اور ناقابل تسخیر روح کے لیے مشہور ہیں۔ ایسا لگتا ہے کہ پچھلے رویے کی بنیاد پر ان کے پاس کنٹرول کا اپنا دھاگہ ہے۔

ایک ایسی مخلوق کو نافذ کرنے کے لیے جس کا کنٹرول میکانزم کا اپنا دھاگہ ہے اور اس کے API کو صرف اور صرف عمل درآمد کو ظاہر نہیں کرتا ہے، ہم Active Object پیٹرن استعمال کر سکتے ہیں۔

**پروگرامی مثال**

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

ہم دیکھ سکتے ہیں کہ کوئی بھی کلاس جو ActiveCreature کلاس میں توسیع کرے گی اس کے پاس طریقوں کو استعمال کرنے اور اس پر عمل کرنے کے لیے اپنا کنٹرول کا دھاگہ ہوگا۔

مثال کے طور پر، Orc کلاس:

```java
public class Orc extends ActiveCreature {

  public Orc(String name) {
    super(name);
  }

}
```

اب، ہم Orcs جیسی متعدد مخلوقات بنا سکتے ہیں، انہیں کھانے اور گھومنے کو کہہ سکتے ہیں، اور وہ اسے اپنے کنٹرول کے اپنے دھاگے پر عمل میں لائیں گے:

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

## کلاس ڈایاگرام

![alt text](./etc/active-object.urm.png "Active Object class diagram")

## سبق

* [Android and Java Concurrency: The Active Object Pattern](https://www.youtube.com/watch?v=Cd8t2u5Qmvc)