---
title: Twin
category: Structural
language: en
tag:
 - Extensibility
---

## Intent
Twin pattern is a design pattern which provides a standard solution to simulate multiple
inheritance in java

## Explanation

Real-world example

> Consider a game with a ball that needs features of two types, Game Item, and threads to function 
> smoothly in the game. We can use two objects, with one object compatible with the first type and 
> the other compatible with the second type.  
> The pair of objects together can function as one ball in the game. 

In plain words

> It provides a way to form two closely coupled sub-classes that can act as a twin class having two ends. 

Wikipedia says

> In software engineering, the Twin pattern is a software design pattern that allows developers 
> to model multiple inheritance in programming languages that do not support multiple inheritance. 
> This pattern avoids many of the problems with multiple inheritance.

**Programmatic Example**

Take our game ball example from above. Consider we have a game in which the ball needs to be both a `GameItem` and `Thread`. 
First of all, we have the `GameItem` class given below and the `Thread` class.


```java

@Slf4j
public abstract class GameItem {

  public void draw() {
    LOGGER.info("draw");
    doDraw();
  }

  public abstract void doDraw();


  public abstract void click();
}

```

Then, we have subclasses `BallItem` and `BallThread` inheriting them, respectively.

```java

@Slf4j
public class BallItem extends GameItem {

  private boolean isSuspended;

  @Setter
  private BallThread twin;

  @Override
  public void doDraw() {

    LOGGER.info("doDraw");
  }

  public void move() {
    LOGGER.info("move");
  }

  @Override
  public void click() {

    isSuspended = !isSuspended;

    if (isSuspended) {
      twin.suspendMe();
    } else {
      twin.resumeMe();
    }
  }
}


@Slf4j
public class BallThread extends Thread {

  @Setter
  private BallItem twin;

  private volatile boolean isSuspended;

  private volatile boolean isRunning = true;

  /**
   * Run the thread.
   */
  public void run() {

    while (isRunning) {
      if (!isSuspended) {
        twin.draw();
        twin.move();
      }
      try {
        Thread.sleep(250);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void suspendMe() {
    isSuspended = true;
    LOGGER.info("Begin to suspend BallThread");
  }

  public void resumeMe() {
    isSuspended = false;
    LOGGER.info("Begin to resume BallThread");
  }

  public void stopMe() {
    this.isRunning = false;
    this.isSuspended = true;
  }
}

``` 

Now, when we need the ball, we can instantiate objects from both the `BallThread` and `BallItem` as a pair and pass them to its pair object so they can act together as appropriate.

```java

var ballItem = new BallItem();
var ballThread = new BallThread();

ballItem.setTwin(ballThread);
ballThread.setTwin(ballItem);

```


## Class diagram
![alt text](./etc/twin.png "Twin")

## Applicability
Use the Twin idiom when

* To simulate multiple inheritance in a language that does not support this feature.
* To avoid certain problems of multiple inheritance such as name clashes.

## Credits

* [Twin – A Design Pattern for Modeling Multiple Inheritance](http://www.ssw.uni-linz.ac.at/Research/Papers/Moe99/Paper.pdf)
