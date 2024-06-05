---
title: State
category: Behavioral
language: en
tag:
    - Decoupling
    - Gang of Four
    - State tracking
---

## Also known as

* Objects for States

## Intent

Allow an object to alter its behavior when its internal state changes. The object will appear to change its class.

## Explanation

Real-world example

> Imagine a traffic light system at an intersection. The traffic light can be in one of three states: Green, Yellow, or Red. Depending on the current state, the traffic light's behavior changes:
>
> 1. **Green State**: Cars are allowed to pass through the intersection.
> 2. **Yellow State**: Cars are warned that the light will soon turn red, so they should prepare to stop.
> 3. **Red State**: Cars must stop and wait for the light to turn green.
>
> In this scenario, the traffic light uses the State design pattern. Each state (Green, Yellow, Red) is represented by a different object that defines what happens in that particular state. The traffic light (context) delegates the behavior to the current state object. When the state changes (e.g., from Green to Yellow), the traffic light updates its state object and changes its behavior accordingly. 

In plain words

> State pattern allows an object to change its behavior. 

Wikipedia says

> The state pattern is a behavioral software design pattern that allows an object to alter its behavior when its internal state changes. This pattern is close to the concept of finite-state machines. The state pattern can be interpreted as a strategy pattern, which is able to switch a strategy through invocations of methods defined in the pattern's interface.

**Programmatic Example**

In our programmatic example there is a mammoth with alternating moods.

First, here is the state interface and its concrete implementations.

```java
public interface State {

  void onEnterState();

  void observe();
}
```

```java
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
```

```java
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

And here is the mammoth containing the state. The state changes via calls to `timePasses` method.

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

Here is the full example of how the mammoth behaves over time.

```java
public static void main(String[] args) {

    var mammoth = new Mammoth();
    mammoth.observe();
    mammoth.timePasses();
    mammoth.observe();
    mammoth.timePasses();
    mammoth.observe();
}
```

Program output:

```java
    The mammoth gets angry!
    The mammoth is furious!
    The mammoth calms down.
    The mammoth is calm and peaceful.
```

## Applicability

* An object's behavior depends on its state, and it must change its behavior at runtime depending on that state.
* Operations have large, multipart conditional statements that depend on the object's state.

## Known Uses

* `java.util.Iterator` in Java's Collections Framework uses different states for iteration.
* TCP connection classes in network programming often implement states like `Established`, `Listen`, and `Closed`.

## Consequences

Benefits:

* Localizes state-specific behavior and partitions behavior for different states.
* Makes state transitions explicit.
* State objects can be shared among different contexts.

Trade-offs:

* Can result in a large number of classes for states.
* Context class can become complicated with the state transition logic.

## Related Patterns

* [Flyweight](https://java-design-patterns.com/patterns/flyweight/): State objects may be shared between different contexts.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): State objects are often singletons.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Both patterns have similar structures, but the State pattern's implementations depend on the context’s state.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
