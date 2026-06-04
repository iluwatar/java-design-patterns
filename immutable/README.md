---
title: "Immutable Pattern in Java: Building Thread-Safe Objects"
shortTitle: Immutable
description: "Learn the Immutable pattern in Java with real-world examples, class diagrams, and tutorials. Understand how to create objects that cannot be modified after construction."
category: Idiom
language: en
tag:
  - Immutability
  - Thread safety
  - Concurrency
  - Object composition
---

## Also known as

Value Object (when applied strictly to small domain values)

## Intent of Immutable Design Pattern

Ensure that an object's state cannot be changed after it is constructed, making it inherently thread-safe and easier to reason about.

## Detailed Explanation of Immutable Pattern with Real-World Examples

Real-world example

> A birth certificate is a perfect real-world analogy for the Immutable pattern. Once issued, a birth certificate records a person's name, date of birth, and place of birth permanently. You cannot alter the certificate itself; if a legal correction is needed, a new certificate is issued. The original document remains unchanged, guaranteeing that every copy handed to a bank, school, or government office reflects exactly the same facts.

In plain words

> An immutable object is one whose state is fixed at construction time and can never change. Instead of modifying an existing object, you create a new one with the desired state.

Wikipedia says

> In object-oriented and functional programming, an immutable object (unchangeable object) is an object whose state cannot be modified after it is created. This is in contrast to a mutable object (changeable object), which can be modified after it is created.

Class diagram

![Immutable class diagram](./etc/immutable.urm.puml)

## Programmatic Example of Immutable Pattern in Java

The core of the pattern is `ImmutableUser`. All fields are `final`, the mutable `roles` list is defensively copied via `List.copyOf`, and "mutation" is expressed by returning a new instance.

```java
public final class ImmutableUser {

  private final String name;
  private final int age;
  private final List<String> roles;

  public ImmutableUser(String name, int age, List<String> roles) {
    this.name = name;
    this.age = age;
    this.roles = List.copyOf(roles);
  }

  public String getName() { return name; }
  public int getAge()     { return age; }
  public List<String> getRoles() { return roles; }

  public ImmutableUser withAge(int newAge) {
    return new ImmutableUser(this.name, newAge, this.roles);
  }
}
```

`App` demonstrates the pattern in action:

```java
var alice = new ImmutableUser("Alice", 30, List.of("admin", "user"));
LOGGER.info("Original user: {}", alice);

var olderAlice = alice.withAge(31);
LOGGER.info("Updated user (new object): {}", olderAlice);
LOGGER.info("Original is unchanged: {}", alice);

var mutableRoles = new ArrayList<>(List.of("viewer"));
var bob = new ImmutableUser("Bob", 25, mutableRoles);
mutableRoles.add("editor");
LOGGER.info("Bob's roles (unchanged despite external list mutation): {}", bob.getRoles());
```

Running the example produces output similar to:

```
INFO  com.iluwatar.immutable.App - Original user: ImmutableUser{name='Alice', age=30, roles=[admin, user]}
INFO  com.iluwatar.immutable.App - Updated user (new object): ImmutableUser{name='Alice', age=31, roles=[admin, user]}
INFO  com.iluwatar.immutable.App - Original is unchanged: ImmutableUser{name='Alice', age=30, roles=[admin, user]}
INFO  com.iluwatar.immutable.App - Bob's roles (unchanged despite external list mutation): [viewer]
```

## When to Use the Immutable Pattern in Java

* When objects are shared across threads and synchronization overhead is undesirable.
* When you need objects to be used safely as map keys or in sets (consistent `hashCode`).
* When you want to model value types such as money, dates, or coordinates.
* When defensive programming is critical and you must prevent accidental state corruption.

## Real-World Applications of Immutable Pattern in Java

* `java.lang.String` — the quintessential immutable class in the JDK.
* `java.time.LocalDate`, `LocalDateTime` — immutable date/time representations.
* `java.math.BigDecimal`, `BigInteger` — immutable numeric types.
* Record classes introduced in Java 16 — compiler-generated immutable data carriers.

## Benefits and Trade-offs of Immutable Pattern

Benefits:

* **Thread safety**: No synchronization needed; immutable objects can be shared freely across threads.
* **Simplicity**: Absence of state changes eliminates a whole category of bugs.
* **Safe sharing**: Can be freely passed to untrusted code without defensive copying at call sites.
* **Cache-friendly**: Immutable objects can be cached, interned, or pre-computed without risk.

Trade-offs:

* **Object creation overhead**: Every logical "update" allocates a new object, which may pressure the garbage collector in hot paths.
* **Verbose construction**: Complex objects often require a Builder to avoid unwieldy constructors.
* **Not always applicable**: Objects that model inherently stateful entities (e.g., a network connection) cannot reasonably be immutable.

## Related Java Design Patterns

* [Value Object](https://java-design-patterns.com/patterns/value-object/): Overlapping concept; value objects are typically immutable and compared by value rather than identity.
* [Builder](https://java-design-patterns.com/patterns/builder/): Commonly paired with Immutable to construct complex objects step-by-step before freezing them.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): Cloning a mutable object is an alternative to immutability when shared state must occasionally change.
* [Flyweight](https://java-design-patterns.com/patterns/flyweight/): Leverages immutability to safely share fine-grained objects across many contexts.

## References and Credits

* [Effective Java, 3rd Edition — Item 17: Minimize Mutability](https://amzn.to/3JIYJoL)
* [Java Concurrency in Practice](https://amzn.to/3vXyUEh)
* [Clean Code: A Handbook of Agile Software Craftsmanship](https://amzn.to/3JIYJoL)
* [Wikipedia — Immutable object](https://en.wikipedia.org/wiki/Immutable_object)
