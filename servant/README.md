---
title: Servant
category: Behavioral
language: en
tag:
- Decoupling
---

## Intent
Servant is used for providing some behavior to a group of classes.
Instead of defining that behavior in each class - or when we cannot factor out
this behavior in the common parent class - it is defined once in the Servant.

## Explanation

Real-world example

> King, Queen, and other royal member of palace need servant to service them for feeding,
> organizing drinks, and so on.

In plain words

> Ensures one servant object to give some specific services for a group of serviced classes.

Wikipedia says

> In software engineering, the servant pattern defines an object used to offer some functionality
> to a group of classes without defining that functionality in each of them. A Servant is a class
> whose instance (or even just class) provides methods that take care of a desired service, while
> objects for which (or with whom) the servant does something, are taken as parameters.

**Programmatic Example**

Servant class which can give services to other royal members of palace.

```java
/**
 * Servant.
 */
public class Servant {

  public String name;

  /**
   * Constructor.
   */
  public Servant(String name) {
    this.name = name;
  }

  public void feed(Royalty r) {
    r.getFed();
  }

  public void giveWine(Royalty r) {
    r.getDrink();
  }

  public void giveCompliments(Royalty r) {
    r.receiveCompliments();
  }

  /**
   * Check if we will be hanged.
   */
  public boolean checkIfYouWillBeHanged(List<Royalty> tableGuests) {
    return tableGuests.stream().allMatch(Royalty::getMood);
  }
}
```

Royalty is an interface. It is implemented by King, and Queen classes to get services from servant.

```java
interface Royalty {

    void getFed();

    void getDrink();

    void changeMood();

    void receiveCompliments();

    boolean getMood();
}
```
King, class is implementing Royalty interface.
```java
public class King implements Royalty {

    private boolean isDrunk;
    private boolean isHungry = true;
    private boolean isHappy;
    private boolean complimentReceived;

    @Override
    public void getFed() {
        isHungry = false;
    }

    @Override
    public void getDrink() {
        isDrunk = true;
    }

    public void receiveCompliments() {
        complimentReceived = true;
    }

    @Override
    public void changeMood() {
        if (!isHungry && isDrunk) {
            isHappy = true;
        }
        if (complimentReceived) {
            isHappy = false;
        }
    }

    @Override
    public boolean getMood() {
        return isHappy;
    }
}
```
Queen, class is implementing Royalty interface.
```java
public class Queen implements Royalty {

    private boolean isDrunk = true;
    private boolean isHungry;
    private boolean isHappy;
    private boolean isFlirty = true;
    private boolean complimentReceived;

    @Override
    public void getFed() {
        isHungry = false;
    }

    @Override
    public void getDrink() {
        isDrunk = true;
    }

    public void receiveCompliments() {
        complimentReceived = true;
    }

    @Override
    public void changeMood() {
        if (complimentReceived && isFlirty && isDrunk && !isHungry) {
            isHappy = true;
        }
    }

    @Override
    public boolean getMood() {
        return isHappy;
    }

    public void setFlirtiness(boolean f) {
        this.isFlirty = f;
    }

}
```

Then in order to use:

```java
public class App {

    private static final Servant jenkins = new Servant("Jenkins");
    private static final Servant travis = new Servant("Travis");

    /**
     * Program entry point.
     */
    public static void main(String[] args) {
        scenario(jenkins, 1);
        scenario(travis, 0);
    }

    /**
     * Can add a List with enum Actions for variable scenarios.
     */
    public static void scenario(Servant servant, int compliment) {
        var k = new King();
        var q = new Queen();

        var guests = List.of(k, q);

        // feed
        servant.feed(k);
        servant.feed(q);
        // serve drinks
        servant.giveWine(k);
        servant.giveWine(q);
        // compliment
        servant.giveCompliments(guests.get(compliment));

        // outcome of the night
        guests.forEach(Royalty::changeMood);

        // check your luck
        if (servant.checkIfYouWillBeHanged(guests)) {
            LOGGER.info("{} will live another day", servant.name);
        } else {
            LOGGER.info("Poor {}. His days are numbered", servant.name);
        }
    }
}
```

The console output

```
Jenkins will live another day
Poor Travis. His days are numbered
```


## Class diagram
![alt text](./etc/servant-pattern.png "Servant")

## Applicability
Use the Servant pattern when

* When we want some objects to perform a common action and don't want to define this action as a method in every class.

## Credits

* [Let's Modify the Objects-First Approach into Design-Patterns-First](http://edu.pecinovsky.cz/papers/2006_ITiCSE_Design_Patterns_First.pdf)
