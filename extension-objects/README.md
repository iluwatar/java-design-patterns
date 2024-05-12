---
title: Extension Objects
category: Structural
language: en
tag:
    - Encapsulation
    - Extensibility
    - Object composition
    - Polymorphism
---

## Also known as

* Interface Extensions

## Intent

The Extension Objects pattern allows for the flexible extension of an object's behavior without modifying its structure, by attaching additional objects that can dynamically add new functionality.

## Explanation

Real-world example

> Suppose you are developing a Java-based game for a client, and in the middle of the development process, new features are suggested. The Extension Objects pattern empowers your program to adapt to unforeseen changes with minimal refactoring, especially when integrating additional functionalities into your project.

In plain words

> The Extension Objects pattern is used to dynamically add functionality to objects without modifying their core classes. It is a behavioural design pattern used for adding new functionality to existing classes and objects within a program. This pattern provides programmers with the ability to extend/modify class functionality without having to refactor existing source code.

Wikipedia says

> In object-oriented computer programming, an extension objects pattern is a design pattern added to an object after the original object was compiled. The modified object is often a class, a prototype or a type. Extension object patterns are features of some object-oriented programming languages. There is no syntactic difference between calling an extension method and calling a method declared in the type definition.

**Programmatic example**

The aim of utilising the Extension Objects pattern is to implement new features/functionality without having to refactor every class. The following examples shows utilising this pattern for an Enemy class extending Entity within a game:

Primary App class to execute our program from.

```java
public class App {
    public static void main(String[] args) {
        Entity enemy = new Enemy("Enemy");
        checkExtensionsForEntity(enemy);
    }

    private static void checkExtensionsForEntity(Entity entity) {
        Logger logger = Logger.getLogger(App.class.getName());
        String name = entity.getName();
        Function<String, Runnable> func = (e) -> () -> logger.info(name + " without " + e);

        String extension = "EnemyExtension";
        Optional.ofNullable(entity.getEntityExtension(extension))
                .map(e -> (EnemyExtension) e)
                .ifPresentOrElse(EnemyExtension::extendedAction, func.apply(extension));
    }
}
```

Enemy class with initial actions and extensions.

```java
class Enemy extends Entity {
    public Enemy(String name) {
        super(name);
    }

    @Override
    protected void performInitialAction() {
        super.performInitialAction();
        System.out.println("Enemy wants to attack you.");
    }

    @Override
    public EntityExtension getEntityExtension(String extensionName) {
        if (extensionName.equals("EnemyExtension")) {
            return Optional.ofNullable(entityExtension).orElseGet(EnemyExtension::new);
        }
        return super.getEntityExtension(extensionName);
    }
}
```

EnemyExtension class with overriding extendAction() method.

```java
class EnemyExtension implements EntityExtension {
    @Override
    public void extendedAction() {
        System.out.println("Enemy has advanced towards you!");
    }
}
```

Entity class which will be extended by Enemy.

```java
class Entity {
    private String name;
    protected EntityExtension entityExtension;

    public Entity(String name) {
        this.name = name;
        performInitialAction();
    }

    protected void performInitialAction() {
        System.out.println(name + " performs the initial action.");
    }

    public EntityExtension getEntityExtension(String extensionName) {
        return null;
    }

    public String getName() {
        return name;
    }
}
```

EntityExtension interface to be used by EnemyExtension.

```java
interface EntityExtension {
    void extendedAction();
}
```

Program output:

```markdown
Enemy performs the initial action.
Enemy wants to attack you.
Enemy has advanced towards you!
```

In this example, the Extension Objects pattern allows the enemy entity to perform unique initial actions and advanced actions when specific extensions are applied. This pattern provides flexibility and extensibility to the codebase while minimizing the need for major code changes.

## Class diagram

![Extension_objects](./etc/extension_obj.png "Extension objects")

## Applicability

This pattern is applicable in scenarios where an object's functionality needs to be extended at runtime, avoiding the complications of subclassing. It's particularly useful in systems where object capabilities need to be augmented post-deployment, or where the capabilities might vary significantly across instances.

## Known Uses

* Extending services in an application server without altering existing code.
* Plugins in IDEs like IntelliJ IDEA or Eclipse to add features to the base application.
* Enabling additional features in enterprise software based on license levels.
* [OpenDoc](https://en.wikipedia.org/wiki/OpenDoc)
* [Object Linking and Embedding](https://en.wikipedia.org/wiki/Object_Linking_and_Embedding)

## Consequences

Benefits:

* Enhances flexibility by allowing dynamic extension of an object's capabilities.
* Promotes loose coupling between the base object and its extensions.
* Supports the [Open/Closed Principle](https://java-design-patterns.com/principles/#open-closed-principle) by keeping the object open for extension but closed for modification.

Trade-offs:

* Can increase complexity due to the management of extension objects.
* May introduce performance overhead if the interaction between objects and extensions is not efficiently designed.

## Related Patterns

* [Decorator](https://java-design-patterns.com/patterns/decorator/): Similar in intent to add responsibilities dynamically, but uses a different structure.
* [Composite](https://java-design-patterns.com/patterns/composite/): Also manages a group of objects, which can be seen as a form of extension.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Offers an alternative way to change the behavior of an object dynamically.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/4aBMuuL)
* [Pattern-Oriented Software Architecture: A System of Patterns](https://amzn.to/3Q9YOtX)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3W6IZYQ)
