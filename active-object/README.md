---
title: Active Object
category: Concurrency
language: en
tag:
 - Performance
---


## Intent

The Active Object design pattern provides a safe and reliable way to implement asynchronous behavior in concurrent systems. It achieves this by encapsulating tasks within objects that have their own thread and message queue. This separation keeps the main thread responsive and avoids issues like direct thread manipulation or shared state access.

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

We can see that any class that will extend the ActiveCreature class will have its own thread of control to invoke and execute methods.

For example, the Orc class:

```java
public class Orc extends ActiveCreature {

  public Orc(String name) {
    super(name);
  }

}
```

Now, we can create multiple creatures such as Orcs, tell them to eat and roam, and they will execute it on their own thread of control:

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

![alt text](./etc/active-object.urm.png "Active Object class diagram")

## Applicability

* When you need to perform long-running operations without blocking the main thread.
* When you need to interact with external resources asynchronously.
* When you want to improve the responsiveness of your application.
* When you need to manage concurrent tasks in a modular and maintainable way.

## Tutorials

* [Android and Java Concurrency: The Active Object Pattern](https://www.youtube.com/watch?v=Cd8t2u5Qmvc)

## Consequences

Benefits

* Improves responsiveness of the main thread.
* Encapsulates concurrency concerns within objects.
* Promotes better code organization and maintainability.
* Provides thread safety and avoids shared state access problems.

Trade-offs

* Introduces additional overhead due to message passing and thread management.
* May not be suitable for all types of concurrency problems.

## Related patterns

* Observer
* Reactor
* Producer-consumer
* Thread pool

## Credits

* [Design Patterns: Elements of Reusable Object Software](https://amzn.to/3HYqrBE)
* [Concurrent Programming in Java: Design Principles and Patterns](https://amzn.to/498SRVq)
* [Learning Concurrent Programming in Scala](https://amzn.to/3UE07nV)
* [Pattern Languages of Program Design 3](https://amzn.to/3OI1j61)
