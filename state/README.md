---
layout: pattern
title: State
folder: state
permalink: /patterns/state/
categories: Behavioral
language: en
tags:
 - Gang of Four
---

## Also known as

Objects for States

## Intent

Allow an object to alter its behavior when its internal state changes. The object will appear to 
change its class.

## Explanation

Real world example

> When observing a mammoth in its natural habitat it seems to change its behavior based on the 
> situation. It may first appear calm but over time when it detects a threat it gets angry and 
> dangerous to its surroundings.  

In plain words

> State pattern allows an object to change its behavior. 

Wikipedia says

> The state pattern is a behavioral software design pattern that allows an object to alter its 
> behavior when its internal state changes. This pattern is close to the concept of finite-state 
> machines. The state pattern can be interpreted as a strategy pattern, which is able to switch a 
> strategy through invocations of methods defined in the pattern's interface.

**Programmatic Example**

Here is the state interface and its concrete implementations.

```java
public interface State {

  void onEnterState();

  void observe();
}

@Slf4j
public class PeacefulState implements State {

  private final Mammoth mammoth;

  public PeacefulState(Mammoth mammoth) {
    this.mammoth = mammoth;
  }

  @Override
  public void observe() {
    LOGGER.info("{} is calm and peaceful.", mammoth);
  }

  @Override
  public void onEnterState() {
    LOGGER.info("{} calms down.", mammoth);
  }
}

@Slf4j
public class AngryState implements State {

  private final Mammoth mammoth;

  public AngryState(Mammoth mammoth) {
    this.mammoth = mammoth;
  }

  @Override
  public void observe() {
    LOGGER.info("{} is furious!", mammoth);
  }

  @Override
  public void onEnterState() {
    LOGGER.info("{} gets angry!", mammoth);
  }
}
```

And here is the mammoth containing the state.

```java
public class Mammoth {

  private State state;

  public Mammoth() {
    state = new PeacefulState(this);
  }

  public void timePasses() {
    if (state.getClass().equals(PeacefulState.class)) {
      changeStateTo(new AngryState(this));
    } else {
      changeStateTo(new PeacefulState(this));
    }
  }

  private void changeStateTo(State newState) {
    this.state = newState;
    this.state.onEnterState();
  }

  @Override
  public String toString() {
    return "The mammoth";
  }

  public void observe() {
    this.state.observe();
  }
}
```

And here is the full example how the mammoth behaves over time.

```java
    var mammoth = new Mammoth();
    mammoth.observe();
    mammoth.timePasses();
    mammoth.observe();
    mammoth.timePasses();
    mammoth.observe();
```

Program output:

```java
    The mammoth gets angry!
    The mammoth is furious!
    The mammoth calms down.
    The mammoth is calm and peaceful.
```

## Class diagram

![alt text](./etc/state_urm.png "State")

## Applicability

Use the State pattern in either of the following cases

* An object's behavior depends on its state, and it must change its behavior at run-time depending on that state
* Operations have large, multipart conditional statements that depend on the object's state. This state is usually represented by one or more enumerated constants. Often, several operations will contain this same conditional structure. The State pattern puts each branch of the conditional in a separate class. This lets you treat the object's state as an object in its own right that can vary independently from other objects.

## Real world examples

* [javax.faces.lifecycle.Lifecycle#execute()](http://docs.oracle.com/javaee/7/api/javax/faces/lifecycle/Lifecycle.html#execute-javax.faces.context.FacesContext-) controlled by [FacesServlet](http://docs.oracle.com/javaee/7/api/javax/faces/webapp/FacesServlet.html), the behavior is dependent on current phase of lifecycle.
* [JDiameter - Diameter State Machine](https://github.com/npathai/jdiameter/blob/master/core/jdiameter/api/src/main/java/org/jdiameter/api/app/State.java)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
