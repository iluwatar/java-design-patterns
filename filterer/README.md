---
layout: pattern
title: Filterer
folder: filterer
permalink: /patterns/filterer/
description: Design pattern that helps container-like objects to return filtered version of themselves.# short meta description that shows in Google search results
language: en
categories:
 - Functional
tags:
 - Extensibility
---

## Name / classification

Filterer

## Intent

The intent of this design pattern is to introduce a functional interface that will add a 
functionality for container-like objects to easily return filtered versions of themselves.

## Explanation

Real world example

> We are designing a threat (malware) detection software which can analyze target systems for 
> threats that are present in it. In the design we have to take into consideration that new 
> Threat types can be added later. Additionally, there is a requirement that the threat detection 
> system can filter the detected threats based on different criteria (the target system acts as 
> container-like object for threats).

In plain words

> Filterer pattern is a design pattern that helps container-like objects return filtered versions 
> of themselves. 

**Programmatic Example**

To model the threat detection example presented above we introduce `Threat` and `ThreatAwareSystem` 
interfaces.

```java
public interface Threat {
  String name();
  int id();
  ThreatType type();
}

public interface ThreatAwareSystem {
  String systemId();
  List<? extends Threat> threats();
  Filterer<? extends ThreatAwareSystem, ? extends Threat> filtered();

}
```

Notice the `filtered` method that returns instance of `Filterer` interface which is defined as:

```java
@FunctionalInterface
public interface Filterer<G, E> {
  G by(Predicate<? super E> predicate);
}
```

It is used to fulfill the requirement for system to be able to filter itself based on threat 
properties. The container-like object (`ThreatAwareSystem` in our case) needs to have a method that 
returns an instance of `Filterer`. This helper interface gives ability to covariantly specify a 
lower bound of contravariant `Predicate` in the subinterfaces of interfaces representing the 
container-like objects.

In our example we will be able to pass a predicate that takes `? extends Threat` object and 
return `?  extends ThreatAwareSystem` from `Filtered::by` method. A simple implementation 
of `ThreatAwareSystem`:

```java
public class SimpleThreatAwareSystem implements ThreatAwareSystem {

  private final String systemId;
  private final ImmutableList<Threat> issues;

  public SimpleThreatAwareSystem(final String systemId, final List<Threat> issues) {
    this.systemId = systemId;
    this.issues = ImmutableList.copyOf(issues);
  }
  
  @Override
  public String systemId() {
    return systemId;
  }
  
  @Override
  public List<? extends Threat> threats() {
    return new ArrayList<>(issues);
  }

  @Override
  public Filterer<? extends ThreatAwareSystem, ? extends Threat> filtered() {
    return this::filteredGroup;
  }

  private ThreatAwareSystem filteredGroup(Predicate<? super Threat> predicate) {
    return new SimpleThreatAwareSystem(this.systemId, filteredItems(predicate));
  }

  private List<Threat> filteredItems(Predicate<? super Threat> predicate) {
    return this.issues.stream()
            .filter(predicate)
            .collect(Collectors.toList());
  }
}
```

The `filtered` method is overridden to filter the threats list by given predicate.

Now if we introduce a new subtype of `Threat` interface that adds probability with which given 
threat can appear:

```java
public interface ProbableThreat extends Threat {
  double probability();
}
```

We can also introduce a new interface that represents a system that is aware of threats with their 
probabilities:

````java
public interface ProbabilisticThreatAwareSystem extends ThreatAwareSystem {
  @Override
  List<? extends ProbableThreat> threats();

  @Override
  Filterer<? extends ProbabilisticThreatAwareSystem, ? extends ProbableThreat> filtered();
}
````

Notice how we override the `filtered` method in `ProbabilisticThreatAwareSystem` and specify 
different return covariant type by specifying different generic types. Our interfaces are clean and 
not cluttered by default implementations. We we will be able to filter 
`ProbabilisticThreatAwareSystem` by `ProbableThreat` properties:

```java
public class SimpleProbabilisticThreatAwareSystem implements ProbabilisticThreatAwareSystem {

  private final String systemId;
  private final ImmutableList<ProbableThreat> threats;

  public SimpleProbabilisticThreatAwareSystem(final String systemId, final List<ProbableThreat> threats) {
    this.systemId = systemId;
    this.threats = ImmutableList.copyOf(threats);
  }

  @Override
  public String systemId() {
    return systemId;
  }

  @Override
  public List<? extends ProbableThreat> threats() {
    return threats;
  }

  @Override
  public Filterer<? extends ProbabilisticThreatAwareSystem, ? extends ProbableThreat> filtered() {
    return this::filteredGroup;
  }

  private ProbabilisticThreatAwareSystem filteredGroup(final Predicate<? super ProbableThreat> predicate) {
    return new SimpleProbabilisticThreatAwareSystem(this.systemId, filteredItems(predicate));
  }

  private List<ProbableThreat> filteredItems(final Predicate<? super ProbableThreat> predicate) {
    return this.threats.stream()
            .filter(predicate)
            .collect(Collectors.toList());
  }
}
```

Now if we want filter `ThreatAwareSystem` by threat type we can do:

```java
Threat rootkit = new SimpleThreat(ThreatType.ROOTKIT, 1, "Simple-Rootkit");
Threat trojan = new SimpleThreat(ThreatType.TROJAN, 2, "Simple-Trojan");
List<Threat> threats = List.of(rootkit, trojan);

ThreatAwareSystem threatAwareSystem = new SimpleThreatAwareSystem("System-1", threats);

ThreatAwareSystem rootkitThreatAwareSystem = threatAwareSystem.filtered()
           .by(threat -> threat.type() == ThreatType.ROOTKIT);
```

Or if we want to filter `ProbabilisticThreatAwareSystem`:

```java
ProbableThreat malwareTroyan = new SimpleProbableThreat("Troyan-ArcBomb", 1, ThreatType.TROJAN, 0.99);
ProbableThreat rootkit = new SimpleProbableThreat("Rootkit-System", 2, ThreatType.ROOTKIT, 0.8);
List<ProbableThreat> probableThreats = List.of(malwareTroyan, rootkit);

ProbabilisticThreatAwareSystem simpleProbabilisticThreatAwareSystem =new SimpleProbabilisticThreatAwareSystem("System-1", probableThreats);

ProbabilisticThreatAwareSystem filtered = simpleProbabilisticThreatAwareSystem.filtered()
           .by(probableThreat -> Double.compare(probableThreat.probability(), 0.99) == 0);
```

## Class diagram

![Filterer](./etc/filterer.png "Filterer")

## Applicability

Pattern can be used when working with container-like objects that use subtyping, instead of 
parametrizing (generics) for extensible class structure. It enables you to easily extend filtering 
ability of container-like objects as business requirements change.

## Tutorials

* [Article about Filterer pattern posted on it's author's blog](https://blog.tlinkowski.pl/2018/filterer-pattern/)
* [Application of Filterer pattern in domain of text analysis](https://www.javacodegeeks.com/2019/02/filterer-pattern-10-steps.html)

## Known uses

One of the uses is present on the blog presented in 
[this](https://www.javacodegeeks.com/2019/02/filterer-pattern-10-steps.html) link. It presents how 
to use `Filterer` pattern to create text issue analyzer with support for test cases used for unit 
testing.

## Consequences

Pros:
 * You can easily introduce new subtypes for container-like objects and subtypes for objects that are contained within them and still be able to filter easily be new properties of those new subtypes.

Cons:
 * Covariant return types mixed with generics can be sometimes tricky

## Credits

* Author of the pattern : [Tomasz Linkowski](https://tlinkowski.pl/)
