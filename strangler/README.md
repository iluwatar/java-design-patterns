---
title: "Strangler Pattern in Java: Modernizing Legacy Systems with Incremental Updates"
shortTitle: Strangler
description: "Explore the Strangler design pattern for Java, a strategic approach to incrementally modernize legacy systems without disruption. Learn how it facilitates smooth transitions to new architectures, with real-world applications and code examples."
category: Structural
language: en
tag:
  - Migration
  - Modernization
  - Refactoring
---

## Also known as

* Strangler Fig

## Intent of Strangler Design Pattern

The Strangler Pattern incrementally replaces the legacy system by building a new system alongside the old one, eventually strangling the old system. Using the pattern offer a seamless transition from old to new systems.

## Detailed Explanation of Strangler Pattern with Real-World Examples

Real-world example

> Imagine a city planning department that decides to modernize an old bridge that's crucial for daily commutes. Instead of demolishing the old bridge and causing major disruptions, they build a new, modern bridge next to it. As sections of the new bridge are completed, traffic is gradually diverted from the old bridge to the new one. Eventually, the entire flow of traffic moves to the new bridge, and the old bridge is either decommissioned or demolished. This way, the transition is smooth, and the city's daily activities are minimally affected. This approach mirrors the Strangler Design Pattern, where a legacy system is incrementally replaced by a new system, ensuring continuous operation during the transition.

In plain words

> The Strangler Design Pattern incrementally replaces a legacy system by developing a new system alongside it and gradually migrating functionality until the old system is entirely replaced.

Wikipedia says

> The Strangler Design Pattern involves incrementally migrating a legacy system by gradually replacing it with a new system. It wraps old code with new code, redirecting or logging uses of the old code to ensure a seamless transition. This pattern is named after the strangler fig plant, which grows around a host tree and eventually replaces it entirely. It's particularly useful for modernizing monolithic applications and transitioning them to microservices architecture with minimal risk and disruption.

## Programmatic Example of Strangler Pattern in Java

The Strangler design pattern in Java is a software design pattern that incrementally migrates a legacy system by gradually replacing specific pieces of functionality with new applications and services. As features from the legacy system are replaced, the new system eventually replaces all the old system's features, strangling the old system and allowing you to decommission it.

In the provided code, we have an example of the Strangler pattern in action. The `OldArithmetic` class represents the legacy system, while the `HalfArithmetic` and `NewArithmetic` classes represent the new system at different stages of development.

Let's break down the code to understand how the Strangler pattern is implemented.

```java
public class OldArithmetic {
  private final OldSource source;

  public OldArithmetic(OldSource source) {
    this.source = source;
  }

  // The sum and mul methods represent the functionality of the legacy system.
  public int sum(int... nums) {
    return source.accumulateSum(nums);
  }

  public int mul(int... nums) {
    return source.accumulateMul(nums);
  }
}
```

The `OldArithmetic` class represents the legacy system. It has two methods, `sum` and `mul`, which depend on the `OldSource` class.

```java
public class HalfArithmetic {
  private final HalfSource newSource;
  private final OldSource oldSource;

  public HalfArithmetic(HalfSource newSource, OldSource oldSource) {
    this.newSource = newSource;
    this.oldSource = oldSource;
  }

  // The sum method has been migrated to use the new source.
  public int sum(int... nums) {
    return newSource.accumulateSum(nums);
  }

  // The mul method still uses the old source.
  public int mul(int... nums) {
    return oldSource.accumulateMul(nums);
  }

  // The ifHasZero method is a new feature added in the new system.
  public boolean ifHasZero(int... nums) {
    return !newSource.ifNonZero(nums);
  }
}
```

The `HalfArithmetic` class represents the system during the migration process. It depends on both the `OldSource` and `HalfSource` classes. The `sum` method has been migrated to use the new source, while the `mul` method still uses the old source. The `ifHasZero` method is a new feature added in the new system.

```java
public class NewArithmetic {
  private final NewSource source;

  public NewArithmetic(NewSource source) {
    this.source = source;
  }

  // All methods now use the new source.
  public int sum(int... nums) {
    return source.accumulateSum(nums);
  }

  public int mul(int... nums) {
    return source.accumulateMul(nums);
  }

  public boolean ifHasZero(int... nums) {
    return !source.ifNonZero(nums);
  }
}
```

The `NewArithmetic` class represents the system after the migration process. It only depends on the `NewSource` class. All methods now use the new source.

Here is the `main` method executing our example.

```java
public static void main(final String[] args) {
    final var nums = new int[]{1, 2, 3, 4, 5};
    //Before migration
    final var oldSystem = new OldArithmetic(new OldSource());
    oldSystem.sum(nums);
    oldSystem.mul(nums);
    //In process of migration
    final var halfSystem = new HalfArithmetic(new HalfSource(), new OldSource());
    halfSystem.sum(nums);
    halfSystem.mul(nums);
    halfSystem.ifHasZero(nums);
    //After migration
    final var newSystem = new NewArithmetic(new NewSource());
    newSystem.sum(nums);
    newSystem.mul(nums);
    newSystem.ifHasZero(nums);
  }
```

Console output:

```
13:02:25.030 [main] INFO com.iluwatar.strangler.OldArithmetic -- Arithmetic sum 1.0
13:02:25.032 [main] INFO com.iluwatar.strangler.OldSource -- Source module 1.0
13:02:25.032 [main] INFO com.iluwatar.strangler.OldArithmetic -- Arithmetic mul 1.0
13:02:25.032 [main] INFO com.iluwatar.strangler.OldSource -- Source module 1.0
13:02:25.032 [main] INFO com.iluwatar.strangler.HalfArithmetic -- Arithmetic sum 1.5
13:02:25.032 [main] INFO com.iluwatar.strangler.HalfSource -- Source module 1.5
13:02:25.033 [main] INFO com.iluwatar.strangler.HalfArithmetic -- Arithmetic mul 1.5
13:02:25.033 [main] INFO com.iluwatar.strangler.OldSource -- Source module 1.0
13:02:25.033 [main] INFO com.iluwatar.strangler.HalfArithmetic -- Arithmetic check zero 1.5
13:02:25.033 [main] INFO com.iluwatar.strangler.HalfSource -- Source module 1.5
13:02:25.034 [main] INFO com.iluwatar.strangler.NewArithmetic -- Arithmetic sum 2.0
13:02:25.034 [main] INFO com.iluwatar.strangler.NewSource -- Source module 2.0
13:02:25.034 [main] INFO com.iluwatar.strangler.NewArithmetic -- Arithmetic mul 2.0
13:02:25.034 [main] INFO com.iluwatar.strangler.NewSource -- Source module 2.0
13:02:25.034 [main] INFO com.iluwatar.strangler.NewArithmetic -- Arithmetic check zero 2.0
13:02:25.035 [main] INFO com.iluwatar.strangler.NewSource -- Source module 2.0
```

This is a typical example of the Strangler pattern. The legacy system (`OldArithmetic`) is gradually replaced by the new system (`HalfArithmetic` and `NewArithmetic`). The new system is developed incrementally, and at each stage, it strangles a part of the legacy system until the legacy system is completely replaced.

## When to Use the Strangler Pattern in Java

* Use when you need to replace a monolithic or legacy system incrementally.
* Ideal for scenarios where the system cannot be replaced in one go due to risk or complexity.
* Suitable when you need to modernize parts of an application while ensuring continuous operation.
* Perfect for applications requiring updates with zero downtime, the Strangler pattern supports incremental updates in complex Java systems.

## Strangler Pattern Java Tutorials

* [Legacy Application Strangulation: Case Studies (Paul Hammant)](https://paulhammant.com/2013/07/14/legacy-application-strangulation-case-studies/)

## Real-World Applications of Strangler Pattern in Java

* Replacing a legacy monolithic application with a microservices architecture.
* Transitioning from an on-premise system to a cloud-based system.
* Incrementally migrating from an old database schema to a new one without downtime.

## Benefits and Trade-offs of Strangler Pattern

Benefits:

* Reduces risk by allowing gradual replacement.
* Enables continuous delivery and operation during migration.
* Allows for testing and validating new components before full replacement.

Trade-offs:

* Requires managing interactions between new and old systems, which can be complex.
* May introduce temporary performance overhead due to coexistence of old and new systems.
* Potentially increases the initial development time due to the need for integration.

## Related Java Design Patterns

* [Adapter](https://java-design-patterns.com/patterns/adapter/): Used to make new systems interact with the old system during the transition period.
* [Facade](https://java-design-patterns.com/patterns/facade/): Can provide a unified interface to the old and new systems, simplifying client interactions.
* Microservices: The target architecture in many cases where the Strangler Pattern is applied.

## References and Credits

* [Building Microservices](https://amzn.to/3UACtrU)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Refactoring: Improving the Design of Existing Code](https://amzn.to/3TVEgaB)
* [Strangler pattern (Microsoft)](https://docs.microsoft.com/en-us/azure/architecture/patterns/strangler)
