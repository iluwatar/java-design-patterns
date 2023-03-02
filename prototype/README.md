---
title: Prototype
category: Creational
language: en
tag: 
 - Gang Of Four
 - Instantiation
---

## Intent

Specify the kinds of objects to create using a prototypical instance, and create new objects by 
copying this prototype.

## Explanation

First, it should be noted that the Prototype pattern is not used to gain performance benefits. It's only 
used for creating new objects from prototype instances.

Real-world example

> Remember Dolly? The sheep that was cloned! Let's not get into the details but the key point here is 
> that it is all about cloning.

In plain words

> Create an object based on an existing object through cloning.

Wikipedia says

> The prototype pattern is a creational design pattern in software development. It is used when the 
> type of objects to create is determined by a prototypical instance, which is cloned to produce new 
> objects.

In short, it allows you to create a copy of an existing object and modify it to your needs, instead 
of going through the trouble of creating an object from scratch and setting it up.

**Programmatic Example**

In Java, the prototype pattern is recommended to be implemented as follows. First, create an
interface with a method for cloning objects. In this example, `Prototype` interface accomplishes
this with its `copy` method.

```java
public abstract class Prototype<T> implements Cloneable {
    @SneakyThrows
    public T copy() {
        return (T) super.clone();
    }
}
```

Our example contains a hierarchy of different creatures. For example, let's look at `Beast` and
`OrcBeast` classes.

```java
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public abstract class Beast extends Prototype<Beast> {

  public Beast(Beast source) {
  }

}

@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class OrcBeast extends Beast {

  private final String weapon;

  public OrcBeast(OrcBeast orcBeast) {
    super(orcBeast);
    this.weapon = orcBeast.weapon;
  }

  @Override
  public String toString() {
    return "Orcish wolf attacks with " + weapon;
  }

}
```

We don't want to go into too many details, but the full example contains also base classes `Mage`
and `Warlord` and there are specialized implementations for those for elves in addition to orcs.

To take full advantage of the prototype pattern, we create `HeroFactory` and `HeroFactoryImpl`
classes to produce different kinds of creatures from prototypes.

```java
public interface HeroFactory {
  
  Mage createMage();
  Warlord createWarlord();
  Beast createBeast();
}

@RequiredArgsConstructor
public class HeroFactoryImpl implements HeroFactory {

  private final Mage mage;
  private final Warlord warlord;
  private final Beast beast;

  public Mage createMage() {
    return mage.copy();
  }

  public Warlord createWarlord() {
    return warlord.copy();
  }

  public Beast createBeast() {
    return beast.copy();
  }
}
```

Now, we are able to show the full prototype pattern in action producing new creatures by cloning
existing instances.

```java
    var factory = new HeroFactoryImpl(
        new ElfMage("cooking"),
        new ElfWarlord("cleaning"),
        new ElfBeast("protecting")
    );
    var mage = factory.createMage();
    var warlord = factory.createWarlord();
    var beast = factory.createBeast();
    LOGGER.info(mage.toString());
    LOGGER.info(warlord.toString());
    LOGGER.info(beast.toString());

    factory = new HeroFactoryImpl(
        new OrcMage("axe"),
        new OrcWarlord("sword"),
        new OrcBeast("laser")
    );
    mage = factory.createMage();
    warlord = factory.createWarlord();
    beast = factory.createBeast();
    LOGGER.info(mage.toString());
    LOGGER.info(warlord.toString());
    LOGGER.info(beast.toString());
```

Here's the console output from running the example.

```
Elven mage helps in cooking
Elven warlord helps in cleaning
Elven eagle helps in protecting
Orcish mage attacks with axe
Orcish warlord attacks with sword
Orcish wolf attacks with laser
```

## Class diagram

![alt text](./etc/prototype.urm.png "Prototype pattern class diagram")

## Applicability

Use the Prototype pattern when a system should be independent of how its products are created, 
composed, represented and

* When the classes to instantiate are specified at run-time, for example, by dynamic loading.
* To avoid building a class hierarchy of factories that parallels the class hierarchy of products.
* When instances of a class can have one of only a few different combinations of state. It may be 
more convenient to install a corresponding number of prototypes and clone them rather than 
instantiating the class manually, each time with the appropriate state.
* When object creation is expensive compared to cloning.

## Known uses

* [java.lang.Object#clone()](http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#clone%28%29)

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
