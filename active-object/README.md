---
layout: pattern
title: Active Object
folder: active-object
permalink: /patterns/active-object/
categories: Concurrency
language: en
tags:
 - Performance
---


## Intent
The active object design pattern decouples method execution from method invocation for objects that each reside in their thread of control. The goal is to introduce concurrency, by using asynchronous method invocation and a scheduler for handling requests.

## Explanation

The class that implements the active object pattern will contain a self-synchronization mechanism without using 'synchronized' methods.

Real-world example

>The Orcs are known for their wildness and untameable soul. It seems like they have their own thread of control based on previous behavior.

To implement a creature that has its own thread of control mechanism and expose its API only and not the execution itself, we can use the Active Object pattern.


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
          logger.info("{} has started to roam and the wastelands.",name());
        }
      }
    );
  }
  
  public String name() {
    return this.name;
  }
}
```

We can see that any class that will extend the ActiveCreature class will have its own thread of control to execute and invocate methods.

For example, the Orc class:

```java
public class Orc extends ActiveCreature {

  public Orc(String name) {
    super(name);
  }

}
```

Now, we can create multiple creatures such as Orcs, tell them to eat and roam and they will execute it on their own thread of control:

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

## Class diagram

![alt text](./etc/active-object.urm.PNG "Active Object class diagram")
