---
title: Lazy Loading
category: Performance optimization
language: en
tag:
    - Instantiation
    - Memory management
    - Optimization
    - Performance
    - Persistence
    - Resource management
---

## Also known as

* Lazy Initialization

## Intent

The Lazy Loading pattern defers object initialization until the object is actually needed, minimizing memory usage and reducing startup times.

## Explanation

Real-world example

> A real-world analogy for Lazy Loading is using lights in a smart home. Instead of switching all lights on at once when someone enters the house, motion sensors detect and turn on lights only in rooms being used. This approach conserves electricity by activating lights only when and where they're needed, similar to how Lazy Loading delays the initialization of objects until they're actually required.

In plain words

> The Lazy Loading pattern defers the creation of an object or resource until it's actually needed, optimizing memory usage and improving performance.

Wikipedia says

> Lazy loading (also known as asynchronous loading) is a technique used in computer programming, especially web design and web development, to defer initialization of an object until it is needed. It can contribute to efficiency in the program's operation if properly and appropriately used. This makes it ideal in use cases where network content is accessed and initialization times are to be kept at a minimum, such as in the case of web pages. For example, deferring loading of images on a web page until they are needed for viewing can make the initial display of the web page faster. The opposite of lazy loading is eager loading.

**Programmatic Example**

The Lazy Loading design pattern is a performance optimization technique that delays the initialization of an object or a costly computation until it's absolutely necessary. This pattern can significantly improve the performance of your application by avoiding unnecessary computation and reducing memory usage.

In the provided code, we can see an example of the Lazy Loading pattern in the `App`, `HolderNaive`, `HolderThreadSafe`, and `Java8Holder` classes.

The `App` class is the entry point of the application. It creates instances of `HolderNaive`, `HolderThreadSafe`, and `Java8Holder`, and retrieves the `Heavy` object from them.

```java
@Slf4j
public class App {

  public static void main(String[] args) {

    var holderNaive = new HolderNaive();
    var heavy = holderNaive.getHeavy();
    LOGGER.info("heavy={}", heavy);

    var holderThreadSafe = new HolderThreadSafe();
    var another = holderThreadSafe.getHeavy();
    LOGGER.info("another={}", another);

    var java8Holder = new Java8Holder();
    var next = java8Holder.getHeavy();
    LOGGER.info("next={}", next);
  }
}
```

The `HolderNaive`, `HolderThreadSafe`, and `Java8Holder` classes are implementations of the Lazy Loading pattern with increasing sophistication.

The `HolderNaive` class is a simple, non-thread-safe implementation of the pattern.

```java
public class HolderNaive {
  private Heavy heavy;

  public HolderNaive() {
      LOGGER.info("HolderNaive created");
  }

  public Heavy getHeavy() {
    if (heavy == null) {
      heavy = new Heavy();
    }
    return heavy;
  }
}
```

The `HolderThreadSafe` class is a thread-safe implementation of the pattern, but with heavy synchronization on each access.

```java
public class HolderThreadSafe {
  private Heavy heavy;

  public synchronized Heavy getHeavy() {
    if (heavy == null) {
      heavy = new Heavy();
    }
    return heavy;
  }
}
```

The `Java8Holder` class is the most efficient implementation of the pattern, utilizing Java 8 features.

```java
public class Java8Holder {

  private Supplier<Heavy> heavy = this::createAndCacheHeavy;

  public Java8Holder() {
      LOGGER.info("Java8Holder created");
  }

  public Heavy getHeavy() {
    return heavy.get();
  }

  private synchronized Heavy createAndCacheHeavy() {
    class HeavyFactory implements Supplier<Heavy> {
      private final Heavy heavyInstance = new Heavy();

      public Heavy get() {
        return heavyInstance;
      }
    }

    if (!(heavy instanceof HeavyFactory)) {
      heavy = new HeavyFactory();
    }

    return heavy.get();
  }
}
```

In this example, the `App` class retrieves a `Heavy` object from `HolderNaive`, `HolderThreadSafe`, and `Java8Holder`. These classes delay the creation of the `Heavy` object until it's actually needed, demonstrating the Lazy Loading pattern.

## Applicability

Use Lazy Loading when:

* An object is resource-intensive to create and might not always be used.
* You need to delay object creation to optimize memory usage or improve startup times.
* Loading data or resources should happen just-in-time rather than at application startup.

## Known Uses

* Hibernate (Java ORM Framework): Delays loading of related objects until they are accessed.
* JPA annotations @OneToOne, @OneToMany, @ManyToOne, @ManyToMany and fetch = FetchType.LAZY
* Spring Framework (Dependency Injection): Loads beans only when required, reducing application startup time.

## Consequences

Benefits:

* Reduces memory usage by initializing objects only when required.
* Improves application startup performance by postponing expensive object creation.

Trade-offs:

* Complexity in implementation if objects are interdependent.
* Risk of latency spikes if initialization occurs at an unexpected point.

## Related Patterns

* [Proxy](https://java-design-patterns.com/patterns/proxy/): Can act as a placeholder for lazy-loaded objects, deferring their actual loading until necessary.
* Virtual Proxy: Specific type of Proxy that handles object creation on demand.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Often combined with Lazy Loading to ensure only one instance of an object is created and loaded lazily.

## Credits

* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Java Performance: The Definitive Guide: Getting the Most Out of Your Code](https://amzn.to/3Wu5neF)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
