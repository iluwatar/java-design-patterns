---

layout: pattern
title: Immutable Object
folder: immutable-object
permalink: /patterns/immutable-object/
categories: Creational
language: en
tags:
 - Gang Of Four
 - Instantiation
 - Thread safety
---

## Also known as

- Value Object
- Data Transfer Object (immutable variant)

## Intent

Ensure that an object's state cannot be changed after construction, providing thread safety and preventing accidental mutation.

## Explanation

Real-world example

> An immutable user profile, once created, cannot be modified. To "update" it, you create a new instance with the desired changes.

In plain words

> Immutable Object pattern ensures that an object cannot be modified after creation. Any modification results in a new object.

**Programmatic Example**

```java
// Create an immutable user profile
var preferences = Map.of("theme", "dark", "language", "en");
var profile = new UserProfile("john_doe", "john@example.com", 30, preferences);

// Attempting to modify throws exception
profile.getPreferences().put("key", "value"); // throws UnsupportedOperationException

// To "update", create a new instance
var updatedProfile = profile.withAge(31);
```

## Class diagram

![alt text](etc/immutable-object.urm.png "Immutable Object class diagram")

## Applicability

Use the Immutable Object pattern when:

- You need objects that are inherently thread-safe
- You want to prevent accidental state mutation
- Objects are used as map keys or set elements
- You need to share objects between threads without synchronization
- You want to create value objects that represent fixed data

## Consequences

**Benefits:**

- Thread-safe without synchronization
- Can be shared freely between threads
- Excellent as map keys and set elements
- No invalid intermediate states
- Easier to reason about and test

**Liabilities:**

- Creating new objects for every change can be expensive
- May lead to memory overhead with many similar objects
- Requires careful design of the object's fields

## Related Patterns

- [Builder](https://java-design-patterns.com/patterns/builder/) - Can be used to construct complex immutable objects
- [Value Object](https://java-design-patterns.com/patterns/value-object/) - Often implemented as immutable objects
- [Prototype](https://java-design-patterns.com/patterns/prototype/) - Can be used to create copies of immutable objects

## Credits

- [Effective Java](https://www.amazon.com/Effective-Java-Joshua-Bloch/dp/0134685997) - Item 17: Minimize mutability
- [Java Concurrency in Practice](https://www.amazon.com/Java-Concurrency-Practice-Brian-Goetz/dp/0321349601) - Section 3.4: Immutability
