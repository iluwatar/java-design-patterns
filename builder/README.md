---
title: Builder
category: Creational
language: en
tag:
    - Gang of Four
    - Instantiation
    - Object composition
---

## Intent

Separate the construction of a complex object from its representation so that the same construction process can create different representations.

## Explanation

Real-world example

> Imagine you are building a customizable sandwich at a deli. The Builder design pattern in this context would involve a SandwichBuilder that allows you to specify each component of the sandwich, such as the type of bread, meat, cheese, vegetables, and condiments. Instead of having to know how to construct the sandwich from scratch, you use the SandwichBuilder to add each desired component step-by-step, ensuring you get exactly the sandwich you want. This separation of construction from the final product representation ensures that the same construction process can yield different types of sandwiches based on the specified components.

In plain words

> Allows you to create different flavors of an object while avoiding constructor pollution. Useful when there could be several flavors of an object. Or when there are a lot of steps involved in creation of an object.

Wikipedia says

> The builder pattern is an object creation software design pattern with the intentions of finding a solution to the telescoping constructor antipattern.

That said, let's clarify what telescoping constructor antipattern is. At one point or the other, we have all seen a constructor like below:

```java
public Hero(Profession profession,String name,HairType hairType,HairColor hairColor,Armor armor,Weapon weapon){
    // Value assignments
}
```

As you can see the number of constructor parameters can quickly get out of hand, and it may become difficult to understand the arrangement of parameters. Plus this parameter list could keep on growing if you would want to add more options in the future. This is called telescoping constructor antipattern.

**Programmatic Example**

Imagine a character generator for a role-playing game. The easiest option is to let the computer create the character for you. If you want to manually select the character details like profession, gender, hair color, etc. the character generation becomes a step-by-step process that completes when all the selections are ready.

The sane alternative is to use the Builder pattern. First of all, we have our hero that we want to create:

```java
public final class Hero {
    private final Profession profession;
    private final String name;
    private final HairType hairType;
    private final HairColor hairColor;
    private final Armor armor;
    private final Weapon weapon;

    private Hero(Builder builder) {
        this.profession = builder.profession;
        this.name = builder.name;
        this.hairColor = builder.hairColor;
        this.hairType = builder.hairType;
        this.weapon = builder.weapon;
        this.armor = builder.armor;
    }
}
```

Then we have the builder:

```java
  public static class Builder {
    private final Profession profession;
    private final String name;
    private HairType hairType;
    private HairColor hairColor;
    private Armor armor;
    private Weapon weapon;

    public Builder(Profession profession, String name) {
        if (profession == null || name == null) {
            throw new IllegalArgumentException("profession and name can not be null");
        }
        this.profession = profession;
        this.name = name;
    }

    public Builder withHairType(HairType hairType) {
        this.hairType = hairType;
        return this;
    }

    public Builder withHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
        return this;
    }

    public Builder withArmor(Armor armor) {
        this.armor = armor;
        return this;
    }

    public Builder withWeapon(Weapon weapon) {
        this.weapon = weapon;
        return this;
    }

    public Hero build() {
        return new Hero(this);
    }
}
```

Then it can be used as:

```java
  public static void main(String[] args) {

    var mage = new Hero.Builder(Profession.MAGE, "Riobard")
            .withHairColor(HairColor.BLACK)
            .withWeapon(Weapon.DAGGER)
            .build();
    LOGGER.info(mage.toString());

    var warrior = new Hero.Builder(Profession.WARRIOR, "Amberjill")
            .withHairColor(HairColor.BLOND)
            .withHairType(HairType.LONG_CURLY).withArmor(Armor.CHAIN_MAIL).withWeapon(Weapon.SWORD)
            .build();
    LOGGER.info(warrior.toString());

    var thief = new Hero.Builder(Profession.THIEF, "Desmond")
            .withHairType(HairType.BALD)
            .withWeapon(Weapon.BOW)
            .build();
    LOGGER.info(thief.toString());
}
```

Program output:

```
16:28:06.058 [main] INFO com.iluwatar.builder.App -- This is a mage named Riobard with black hair and wielding a dagger.
16:28:06.060 [main] INFO com.iluwatar.builder.App -- This is a warrior named Amberjill with blond long curly hair wearing chain mail and wielding a sword.
16:28:06.060 [main] INFO com.iluwatar.builder.App -- This is a thief named Desmond with bald head and wielding a bow.
```

## Class diagram

![Builder](./etc/builder.urm.png "Builder class diagram")

## Applicability

Use the Builder pattern when

* The algorithm for creating a complex object should be independent of the parts that make up the object and how they're assembled
* The construction process must allow different representations for the object that's constructed
* It's particularly useful when a product requires a lot of steps to be created and when these steps need to be executed in a specific sequence

## Tutorials

* [Builder Design Pattern in Java (DigitalOcean)](https://www.journaldev.com/1425/builder-design-pattern-in-java)
* [Builder (Refactoring Guru)](https://refactoring.guru/design-patterns/builder)
* [Exploring Joshua Bloch’s Builder design pattern in Java (Java Magazine)](https://blogs.oracle.com/javamagazine/post/exploring-joshua-blochs-builder-design-pattern-in-java)

## Known Uses

* StringBuilder in Java for constructing strings.
* java.lang.StringBuffer used to create mutable string objects.
* Java.nio.ByteBuffer as well as similar buffers such as FloatBuffer, IntBuffer, and others
* javax.swing.GroupLayout.Group#addComponent()
* Various GUI builders in IDEs that construct UI components.
* All implementations of [java.lang.Appendable](http://docs.oracle.com/javase/8/docs/api/java/lang/Appendable.html)
* [Apache Camel builders](https://github.com/apache/camel/tree/0e195428ee04531be27a0b659005e3aa8d159d23/camel-core/src/main/java/org/apache/camel/builder)
* [Apache Commons Option.Builder](https://commons.apache.org/proper/commons-cli/apidocs/org/apache/commons/cli/Option.Builder.html)

## Consequences

Benefits:

* More control over the construction process compared to other creational patterns
* Supports constructing objects step-by-step, defer construction steps or run steps recursively
* Can construct objects that require a complex assembly of sub-objects. The final product is detached from the parts that make it up, as well as their assembly process
* Single Responsibility Principle. You can isolate complex construction code from the business logic of the product

Trade-offs:

* The overall complexity of the code can increase since the pattern requires creating multiple new classes
* May increase memory usage due to the necessity of creating multiple builder objects

## Related patterns

* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Can be used in conjunction with Builder to build parts of a complex object.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): Builders often create objects from a prototype.
* [Step Builder](https://java-design-patterns.com/patterns/step-builder/): It is a variation of the Builder pattern that generates a complex object using a step-by-step approach. The Step Builder pattern is a good choice when you need to build an object with a large number of optional parameters, and you want to avoid the telescoping constructor antipattern.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
