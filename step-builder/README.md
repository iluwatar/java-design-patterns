---
title: Step Builder
category: Creational
language: en
tag:
    - Code simplification
    - Domain
    - Encapsulation
    - Extensibility
    - Instantiation
    - Interface
---

## Also known as

* Fluent Builder

## Intent

Separate the construction of a complex object step-by-step, allowing an object to be built incrementally.

## Explanation

Real-world example

> Imagine a scenario where you are assembling a custom computer. The process involves several steps: selecting the CPU, choosing the motherboard, adding memory, picking a graphics card, and installing storage. Each step builds on the previous one, gradually creating a fully functional computer. This step-by-step assembly process mirrors the Step Builder design pattern, ensuring that each component is correctly chosen and installed in a structured manner, ultimately resulting in a custom-built computer that meets specific requirements and preferences.

In plain words

> The Step Builder pattern constructs complex objects incrementally through a series of defined steps, ensuring clarity and flexibility in the creation process.

Wikipedia says

> The Step Builder pattern is a variation of the Builder design pattern, designed to provide a flexible solution for constructing complex objects step-by-step. This pattern is particularly useful when an object requires multiple initialization steps, which can be done incrementally to ensure clarity and flexibility in the creation process.

**Programmatic Example**

The Step Builder pattern is an extension of the Builder pattern that guides the user through the creation of an object in a step-by-step manner. This pattern improves the user experience by only showing the next step methods available, and not showing the build method until it's the right time to build the object.

Let's consider a `Character` class that has many attributes such as `name`, `fighterClass`, `wizardClass`, `weapon`, `spell`, and `abilities`.

```java
@Getter
@Setter
public class Character {

  private String name;
  private String fighterClass;
  private String wizardClass;
  private String weapon;
  private String spell;
  private List<String> abilities;

  public Character(String name) {
    this.name = name;
  }

  // toString method omitted
}
```

Creating an instance of this class can be complex due to the number of attributes. This is where the Step Builder pattern comes in handy.

We create a `CharacterStepBuilder` class that guides the user through the creation of a `Character` object.

```java
public class CharacterStepBuilder {

  // Builder steps and methods...

  public static NameStep newBuilder() {
    return new Steps();
  }

  // Steps implementation...
}
```

The `CharacterStepBuilder` class defines a series of nested interfaces, each representing a step in the construction process. Each interface declares a method for the next step, guiding the user through the construction of a `Character` object.

```java
public interface NameStep {
  ClassStep name(String name);
}
```

```java
public interface ClassStep {
  WeaponStep fighterClass(String fighterClass);
  SpellStep wizardClass(String wizardClass);
}

// More steps...
```

The `Steps` class implements all these interfaces and finally builds the `Character` object.

```java
private static class Steps implements NameStep, ClassStep, WeaponStep, SpellStep, BuildStep {

  private String name;
  private String fighterClass;
  private String wizardClass;
  private String weapon;
  private String spell;
  private List<String> abilities;

  // Implement the methods for each step...

  @Override
  public Character build() {
    return new Character(name, fighterClass, wizardClass, weapon, spell, abilities);
  }
}
```

Now, creating a `Character` object becomes a guided process:

```java
public static void main(String[] args) {

    var warrior = CharacterStepBuilder
            .newBuilder()
            .name("Amberjill")
            .fighterClass("Paladin")
            .withWeapon("Sword")
            .noAbilities()
            .build();

    LOGGER.info(warrior.toString());

    var mage = CharacterStepBuilder
            .newBuilder()
            .name("Riobard")
            .wizardClass("Sorcerer")
            .withSpell("Fireball")
            .withAbility("Fire Aura")
            .withAbility("Teleport")
            .noMoreAbilities()
            .build();

    LOGGER.info(mage.toString());

    var thief = CharacterStepBuilder
            .newBuilder()
            .name("Desmond")
            .fighterClass("Rogue")
            .noWeapon()
            .build();

    LOGGER.info(thief.toString());
}
```

Console output:

```
12:58:13.887 [main] INFO com.iluwatar.stepbuilder.App -- This is a Paladin named Amberjill armed with a Sword.
12:58:13.889 [main] INFO com.iluwatar.stepbuilder.App -- This is a Sorcerer named Riobard armed with a Fireball and wielding [Fire Aura, Teleport] abilities.
12:58:13.889 [main] INFO com.iluwatar.stepbuilder.App -- This is a Rogue named Desmond armed with a with nothing.
```

## Class diagram

![Step Builder](./etc/step-builder.png "Step Builder")

## Applicability

* When constructing an object that requires multiple initialization steps.
* When object construction is complex and involves many parameters.
* When you want to provide a clear, readable, and maintainable way to construct an object in a step-by-step manner.

## Tutorials

* [Step Builder (Marco Castigliego)](http://rdafbn.blogspot.co.uk/2012/07/step-builder-pattern_28.html)

## Known Uses

* Complex configuration settings in Java applications.
* Constructing objects for database records with multiple fields.
* Building UI elements where each step configures a different part of the interface.

## Consequences

Benefits:

* Improves code readability and maintainability by providing a clear and concise way to construct objects.
* Enhances flexibility in creating objects as it allows variations in the construction process.
* Encourages immutability by separating the construction process from the object's representation.

Trade-offs:

* May result in more complex code due to the additional classes and interfaces required.
* Can lead to verbose code when many steps are involved.

## Related Patterns

* [Builder](https://java-design-patterns.com/patterns/builder/): Both patterns help in constructing complex objects. Step Builder is a variation that emphasizes incremental step-by-step construction.
* [Fluent Interface](https://java-design-patterns.com/patterns/fluentinterface/): Often used in conjunction with the Step Builder pattern to provide a fluent API for constructing objects.
* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): Sometimes used with the Step Builder pattern to encapsulate the creation logic of the builder itself.

## Credits

* [Clean Code: A Handbook of Agile Software Craftsmanship](https://amzn.to/3wRnjp5)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
