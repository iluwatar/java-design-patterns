---
title: Model-View-Controller
category: Architectural
language: en
tag:
 - Decoupling
---

## Intent
Separate the user interface into three interconnected components:
the model, the view and the controller. Let the model manage the data, the view
display the data and the controller mediate updating the data and redrawing the
display.

## Explanation

Real-world example

> Consider ICU room in hospital which displays the patients health information on device displays which 
> are taking input from sensors connected to patient. Here, display's job is to display the data that 
> it receives from the controller which in turn gets update from sensor model.

In plain words

> MVC separates the business logic from user interface by mediating Controller between Model & View.

Wikipedia says

> Model–view–controller (MVC) is commonly used for developing user interfaces that divide the 
> related program logic into three interconnected elements. This is done to separate internal 
> representations of information from the ways information is presented to and accepted from the user.

**Programmatic Example**

Consider following `GiantModel` model class that provides the health, fatigue & nourishment information.

```java
public class GiantModel {

  private Health health;
  private Fatigue fatigue;
  private Nourishment nourishment;

  /**
   * Instantiates a new GiantModel.
   */
  public GiantModel(Health health, Fatigue fatigue, Nourishment nourishment) {
    this.health = health;
    this.fatigue = fatigue;
    this.nourishment = nourishment;
  }

  public Health getHealth() {
    return health;
  }

  public void setHealth(Health health) {
    this.health = health;
  }

  public Fatigue getFatigue() {
    return fatigue;
  }

  public void setFatigue(Fatigue fatigue) {
    this.fatigue = fatigue;
  }

  public Nourishment getNourishment() {
    return nourishment;
  }

  public void setNourishment(Nourishment nourishment) {
    this.nourishment = nourishment;
  }

  @Override
  public String toString() {
    return String.format("The giant looks %s, %s and %s.", health, fatigue, nourishment);
  }
}
```

`GiantView` class to display received patient data.

```java
public class GiantView {

  public void displayGiant(GiantModel giant) {
    LOGGER.info(giant.toString());
  }
}
```

And `GiantController` class that takes updates from `GiantModel` & sends to `GiantView` for display.

```java
public class GiantController {

  private final GiantModel giant;
  private final GiantView view;

  public GiantController(GiantModel giant, GiantView view) {
    this.giant = giant;
    this.view = view;
  }

  @SuppressWarnings("UnusedReturnValue")
  public Health getHealth() {
    return giant.getHealth();
  }

  public void setHealth(Health health) {
    this.giant.setHealth(health);
  }

  @SuppressWarnings("UnusedReturnValue")
  public Fatigue getFatigue() {
    return giant.getFatigue();
  }

  public void setFatigue(Fatigue fatigue) {
    this.giant.setFatigue(fatigue);
  }

  @SuppressWarnings("UnusedReturnValue")
  public Nourishment getNourishment() {
    return giant.getNourishment();
  }

  public void setNourishment(Nourishment nourishment) {
    this.giant.setNourishment(nourishment);
  }

  public void updateView() {
    this.view.displayGiant(giant);
  }
}
```

## Class diagram
![alt text](./etc/model-view-controller.png "Model-View-Controller")

## Applicability
Use the Model-View-Controller pattern when

* You want to clearly separate the domain data from its user interface representation

## Tutorials

* [Spring Boot MVC](https://zetcode.com/springboot/model/)
* [Spring MVC Tutorial](https://www.baeldung.com/spring-mvc-tutorial)

## Credits

* [Trygve Reenskaug - Model-view-controller](http://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=d9f7d37b032ca6e96253562d075fcc4a)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
