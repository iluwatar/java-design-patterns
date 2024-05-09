---
title: Multiton
category: Creational
language: en
tag:
    - Decoupling
    - Instantiation
    - Object composition
---

## Also known as

* Registry of Singletons

## Intent

The Multiton pattern is a variation of the Singleton design pattern that manages a map of named instances as key-value pairs.

## Explanation

Real-world example

> The Nazgûl, also called ringwraiths or the Nine Riders, are Sauron's most terrible servants. By definition, there's always nine of them.           

In plain words

> Multiton pattern ensures there are a predefined amount of instances available globally.

Wikipedia says

> In software engineering, the multiton pattern is a design pattern which generalizes the singleton pattern. Whereas the singleton allows only one instance of a class to be created, the multiton pattern allows for the controlled creation of multiple instances, which it manages through the use of a map.

**Programmatic Example**

`Nazgul` is the multiton class.

```java
public enum NazgulName {

    KHAMUL, MURAZOR, DWAR, JI_INDUR, AKHORAHIL, HOARMURATH, ADUNAPHEL, REN, UVATHA
}

public final class Nazgul {

    private static final Map<NazgulName, Nazgul> nazguls;

    @Getter
    private final NazgulName name;

    static {
        nazguls = new ConcurrentHashMap<>();
        nazguls.put(NazgulName.KHAMUL, new Nazgul(NazgulName.KHAMUL));
        nazguls.put(NazgulName.MURAZOR, new Nazgul(NazgulName.MURAZOR));
        nazguls.put(NazgulName.DWAR, new Nazgul(NazgulName.DWAR));
        nazguls.put(NazgulName.JI_INDUR, new Nazgul(NazgulName.JI_INDUR));
        nazguls.put(NazgulName.AKHORAHIL, new Nazgul(NazgulName.AKHORAHIL));
        nazguls.put(NazgulName.HOARMURATH, new Nazgul(NazgulName.HOARMURATH));
        nazguls.put(NazgulName.ADUNAPHEL, new Nazgul(NazgulName.ADUNAPHEL));
        nazguls.put(NazgulName.REN, new Nazgul(NazgulName.REN));
        nazguls.put(NazgulName.UVATHA, new Nazgul(NazgulName.UVATHA));
    }

    private Nazgul(NazgulName name) {
        this.name = name;
    }

    public static Nazgul getInstance(NazgulName name) {
        return nazguls.get(name);
    }
}
```

And here's how we access the `Nazgul` instances.

```java
LOGGER.info("Printing out eagerly initialized multiton contents");
LOGGER.info("KHAMUL={}", Nazgul.getInstance(NazgulName.KHAMUL));
LOGGER.info("MURAZOR={}", Nazgul.getInstance(NazgulName.MURAZOR));
LOGGER.info("DWAR={}", Nazgul.getInstance(NazgulName.DWAR));
LOGGER.info("JI_INDUR={}", Nazgul.getInstance(NazgulName.JI_INDUR));
LOGGER.info("AKHORAHIL={}", Nazgul.getInstance(NazgulName.AKHORAHIL));
LOGGER.info("HOARMURATH={}", Nazgul.getInstance(NazgulName.HOARMURATH));
LOGGER.info("ADUNAPHEL={}", Nazgul.getInstance(NazgulName.ADUNAPHEL));
LOGGER.info("REN={}", Nazgul.getInstance(NazgulName.REN));
LOGGER.info("UVATHA={}", Nazgul.getInstance(NazgulName.UVATHA));
```

Program output:

```
20:35:07.413 [main] INFO com.iluwatar.multiton.App - Printing out eagerly initialized multiton contents
20:35:07.417 [main] INFO com.iluwatar.multiton.App - KHAMUL=com.iluwatar.multiton.Nazgul@48cf768c
20:35:07.419 [main] INFO com.iluwatar.multiton.App - MURAZOR=com.iluwatar.multiton.Nazgul@7960847b
20:35:07.419 [main] INFO com.iluwatar.multiton.App - DWAR=com.iluwatar.multiton.Nazgul@6a6824be
20:35:07.419 [main] INFO com.iluwatar.multiton.App - JI_INDUR=com.iluwatar.multiton.Nazgul@5c8da962
20:35:07.419 [main] INFO com.iluwatar.multiton.App - AKHORAHIL=com.iluwatar.multiton.Nazgul@512ddf17
20:35:07.419 [main] INFO com.iluwatar.multiton.App - HOARMURATH=com.iluwatar.multiton.Nazgul@2c13da15
20:35:07.419 [main] INFO com.iluwatar.multiton.App - ADUNAPHEL=com.iluwatar.multiton.Nazgul@77556fd
20:35:07.419 [main] INFO com.iluwatar.multiton.App - REN=com.iluwatar.multiton.Nazgul@368239c8
20:35:07.420 [main] INFO com.iluwatar.multiton.App - UVATHA=com.iluwatar.multiton.Nazgul@9e89d68
```

## Class diagram

![Multiton](./etc/multiton.png "Multiton")

## Applicability

Use the Multiton pattern when

* A class must have named instances, but only one instance for each unique key.
* Global access to these instances is necessary without requiring global variables.
* You want to manage shared resources categorized by key.

## Known Uses

* Managing database connections in different contexts.
* Configuration settings for different environments in an application.

## Consequences

Benefits:

* Ensures controlled access to instances based on key.
* Reduces global state usage by encapsulating instance management within the pattern.

Trade-offs:

* Increased memory usage if not managed properly due to multiple instances.
* Potential issues with concurrency if not implemented with thread safety in mind.

## Related Patterns

[Singleton](https://java-design-patterns.com/patterns/singleton/): Multiton can be seen as an extension of the Singleton pattern where Singleton allows only one instance of a class, Multiton allows one instance per key.
[Factory Method](https://java-design-patterns.com/patterns/factory-method/): Multiton uses a method to create or retrieve instances, similar to how a Factory Method controls object creation.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
