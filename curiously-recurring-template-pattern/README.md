---
title: "Curiously Recurring Template Pattern in Java: Leveraging Polymorphism Uniquely"
shortTitle: Curiously Recurring Template Pattern (CRTP)
description: "Discover the Curiously Recurring Template Pattern (CRTP) in Java. Learn how to achieve static polymorphism for efficient method overriding and compile-time polymorphic behavior. Perfect for performance-critical applications."
language: en
category: Structural
tag:
  - Code simplification
  - Extensibility
  - Generic
  - Idiom
  - Instantiation
  - Polymorphism
  - Recursion
---

## Also known as

* CRTP
* Mixin Inheritance
* Recursive Type Bound
* Recursive Generic
* Static Polymorphism

## Intent of Curiously Recurring Template Pattern

The Curiously Recurring Template Pattern (CRTP) is a powerful design pattern in Java used to achieve static polymorphism. By having a class template derive from a template instantiation of its own class, CRTP enables method overriding and compile-time polymorphic behavior, enhancing efficiency and performance in your Java applications.

## Detailed Explanation of Curiously Recurring Template Pattern with Real-World Examples

Real-world example

> Consider a scenario where a library system manages various types of media: books, DVDs, and magazines. Each media type has specific attributes and behaviors, but they all share common functionality like borrowing and returning. By applying the Curiously Recurring Template Pattern (CRTP) in Java, you can create a base template class `MediaItem` encompassing these common methods. Each specific media type (e.g., `Book`, `DVD`, `Magazine`) would inherit from `MediaItem` using itself as a template parameter. This approach allows each media type to customize shared functionality efficiently, avoiding the overhead of virtual methods.

In plain words

> The CRTP in Java ensures that certain methods within a type can accept arguments specific to its subtypes, enabling more efficient and type-safe polymorphic behavior at compile time.

Wikipedia says

> The curiously recurring template pattern (CRTP) is an idiom, originally in C++, in which a class X derives from a class template instantiation using X itself as a template argument.

## Programmatic example of CRTP in Java

For a mixed martial arts promotion that is planning an event, ensuring that the fights are organized between athletes of the same weight class is crucial. This prevents mismatches between fighters of significantly different sizes, such as a heavyweight facing off against a bantamweight.

Let's define the generic interface `Fighter`.

```java
public interface Fighter<T> {

    void fight(T t);

}
```

The `MMAFighter` class is used to instantiate fighters distinguished by their weight class.

```java
@Slf4j
@Data
public class MmaFighter<T extends MmaFighter<T>> implements Fighter<T> {

    private final String name;
    private final String surname;
    private final String nickName;
    private final String speciality;

    @Override
    public void fight(T opponent) {
        LOGGER.info("{} is going to fight against {}", this, opponent);
    }
}
```

The followings are some subtypes of `MmaFighter`.

```java
class MmaBantamweightFighter extends MmaFighter<MmaBantamweightFighter> {

    public MmaBantamweightFighter(String name, String surname, String nickName, String speciality) {
        super(name, surname, nickName, speciality);
    }
}

public class MmaHeavyweightFighter extends MmaFighter<MmaHeavyweightFighter> {

    public MmaHeavyweightFighter(String name, String surname, String nickName, String speciality) {
        super(name, surname, nickName, speciality);
    }
}
```

A fighter is allowed to fight an opponent of the same weight classes. If the opponent is of a different weight class, an error is raised.

```java
public static void main(String[] args) {

    MmaBantamweightFighter fighter1 = new MmaBantamweightFighter("Joe", "Johnson", "The Geek", "Muay Thai");
    MmaBantamweightFighter fighter2 = new MmaBantamweightFighter("Ed", "Edwards", "The Problem Solver", "Judo");
    fighter1.fight(fighter2);

    MmaHeavyweightFighter fighter3 = new MmaHeavyweightFighter("Dave", "Davidson", "The Bug Smasher", "Kickboxing");
    MmaHeavyweightFighter fighter4 = new MmaHeavyweightFighter("Jack", "Jackson", "The Pragmatic", "Brazilian Jiu-Jitsu");
    fighter3.fight(fighter4);
}
```

Program output:

```
08:42:34.048 [main] INFO crtp.MmaFighter -- MmaFighter(name=Joe, surname=Johnson, nickName=The Geek, speciality=Muay Thai) is going to fight against MmaFighter(name=Ed, surname=Edwards, nickName=The Problem Solver, speciality=Judo)
08:42:34.054 [main] INFO crtp.MmaFighter -- MmaFighter(name=Dave, surname=Davidson, nickName=The Bug Smasher, speciality=Kickboxing) is going to fight against MmaFighter(name=Jack, surname=Jackson, nickName=The Pragmatic, speciality=Brazilian Jiu-Jitsu)
```

## When to Use the Curiously Recurring Template Pattern in Java

* When you need to extend the functionality of a class through inheritance but prefer compile-time polymorphism to runtime polymorphism for efficiency reasons.
* When you want to avoid the overhead of virtual functions but still achieve polymorphic behavior.
* In template metaprogramming to provide implementations of functions or policies that can be selected at compile time.
* You have type conflicts when chaining methods in an object hierarchy.
* You want to use a parameterized class method that can accept subclasses of the class as arguments, allowing it to be applied to objects that inherit from the class.
* You want certain methods to work only with instances of the same type, such as for achieving mutual comparability.

## Curiously Recurring Template Pattern Java Tutorials

* [Curiously Recurring Template Pattern in Java (The NuaH Blog)](https://nuah.livejournal.com/328187.html)

## Real-World Applications of Curiously Recurring Template Pattern in Java

* Implementing compile-time polymorphic interfaces in template libraries.
* Enhancing code reuse in libraries where performance is critical, like in mathematical computations, embedded systems, and real-time processing applications.
* Implementation of the `Cloneable` interface in various Java libraries.

## Benefits and Trade-offs of Curiously Recurring Template Pattern

Benefits:

* Elimination of virtual function call overhead, enhancing performance.
* Safe reuse of the base class code without the risks associated with multiple inheritances.
* Greater flexibility and extensibility in compile-time polymorphism scenarios.

Trade-offs:

* Increased complexity in understanding and debugging due to the interplay of templates and inheritance.
* Can lead to code bloat because each instantiation of a template results in a new class.
* Less flexibility compared to runtime polymorphism as the behavior must be determined entirely at compile time.

## Related Java Design Patterns

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): Can be used in conjunction with CRTP to instantiate derived classes without knowing their specific types.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): CRTP can implement compile-time strategy selection.
* [Template Method](https://java-design-patterns.com/patterns/template-method/): Similar in structure but differs in that CRTP achieves behavior variation through compile-time polymorphism.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
