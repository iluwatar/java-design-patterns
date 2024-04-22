---
title: Fluent Interface
category: Behavioral
language: en
tag: 
    - API design
    - Code simplification
    - Decoupling
    - Object composition
    - Reactive
---

## Also known as

* Fluent API
* Method Chaining

## Intent

A fluent interface provides an easy-readable, flowing interface, that often mimics a domain specific language. Using this pattern results in code that can be read nearly as human language.

## Explanation

The Fluent Interface pattern is useful when you want to provide an easy readable, flowing API. Those 
interfaces tend to mimic domain specific languages, so they can nearly be read as human languages.
 
A fluent interface can be implemented using any of
 
 * Method chaining - calling a method returns some object on which further methods can be called.
 * Static factory methods and imports.
 * Named parameters - can be simulated in Java using static factory methods.

Real world example

> We need to select numbers based on different criteria from the list. It's a great chance to  utilize fluent interface pattern to provide readable easy-to-use developer experience. 

In plain words

> Fluent Interface pattern provides easily readable flowing interface to code.

Wikipedia says

> In software engineering, a fluent interface is an object-oriented API whose design relies extensively on method chaining. Its goal is to increase code legibility by creating a  domain-specific language (DSL).

**Programmatic Example**

In this example two implementations of a `FluentIterable` interface are given.

```java
public interface FluentIterable<E> extends Iterable<E> {

  FluentIterable<E> filter(Predicate<? super E> predicate);

  Optional<E> first();

  FluentIterable<E> first(int count);

  Optional<E> last();

  FluentIterable<E> last(int count);

  <T> FluentIterable<T> map(Function<? super E, T> function);

  List<E> asList();

  static <E> List<E> copyToList(Iterable<E> iterable) {
    var copy = new ArrayList<E>();
    iterable.forEach(copy::add);
    return copy;
  }
}
```

The `SimpleFluentIterable` evaluates eagerly and would be too costly for real world applications.

```java
public class SimpleFluentIterable<E> implements FluentIterable<E> {
  // ...
}
```

The `LazyFluentIterable` is evaluated on termination.

```java
public class LazyFluentIterable<E> implements FluentIterable<E> {
  // ...
}
```

Their usage is demonstrated with a simple number list that is filtered, transformed and collected. The 
result is printed afterward.

```java
var integerList = List.of(1, -61, 14, -22, 18, -87, 6, 64, -82, 26, -98, 97, 45, 23, 2, -68);

prettyPrint("The initial list contains: ", integerList);

var firstFiveNegatives = SimpleFluentIterable
    .fromCopyOf(integerList)
    .filter(negatives())
    .first(3)
    .asList();
prettyPrint("The first three negative values are: ", firstFiveNegatives);


var lastTwoPositives = SimpleFluentIterable
    .fromCopyOf(integerList)
    .filter(positives())
    .last(2)
    .asList();
prettyPrint("The last two positive values are: ", lastTwoPositives);

SimpleFluentIterable
    .fromCopyOf(integerList)
    .filter(number -> number % 2 == 0)
    .first()
    .ifPresent(evenNumber -> LOGGER.info("The first even number is: {}", evenNumber));


var transformedList = SimpleFluentIterable
    .fromCopyOf(integerList)
    .filter(negatives())
    .map(transformToString())
    .asList();
prettyPrint("A string-mapped list of negative numbers contains: ", transformedList);


var lastTwoOfFirstFourStringMapped = LazyFluentIterable
    .from(integerList)
    .filter(positives())
    .first(4)
    .last(2)
    .map(number -> "String[" + valueOf(number) + "]")
    .asList();
prettyPrint("The lazy list contains the last two of the first four positive numbers "
    + "mapped to Strings: ", lastTwoOfFirstFourStringMapped);

LazyFluentIterable
    .from(integerList)
    .filter(negatives())
    .first(2)
    .last()
    .ifPresent(number -> LOGGER.info("Last amongst first two negatives: {}", number));
```

Program output:

```java
The initial list contains: 1, -61, 14, -22, 18, -87, 6, 64, -82, 26, -98, 97, 45, 23, 2, -68.
The first three negative values are: -61, -22, -87.
The last two positive values are: 23, 2.
The first even number is: 14
A string-mapped list of negative numbers contains: String[-61], String[-22], String[-87], String[-82], String[-98], String[-68].
The lazy list contains the last two of the first four positive numbers mapped to Strings: String[18], String[6].
Last amongst first two negatives: -22    
```

## Class diagram

![Fluent Interface](./etc/fluentinterface.png "Fluent Interface")

## Applicability

Use the Fluent Interface pattern when

* Designing APIs that are heavily used and where readability of client code is of high importance.
* Building complex objects step-by-step, and there is a need to make the code more intuitive and less error-prone.
* Enhancing code clarity and reducing the boilerplate code, especially in configurations and object-building scenarios.

## Known uses

* [Java 8 Stream API](http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html)
* [Google Guava FluentIterable](https://github.com/google/guava/wiki/FunctionalExplained)
* [JOOQ](http://www.jooq.org/doc/3.0/manual/getting-started/use-cases/jooq-as-a-standalone-sql-builder/)
* [Mockito](http://mockito.org/)
* [Java Hamcrest](http://code.google.com/p/hamcrest/wiki/Tutorial)
* Builders in libraries like Apache Camel for integration workflows.

## Consequences

Benefits:

* Improved code readability and maintainability.
* Encourages building immutable objects since methods typically return new instances.
* Reduces the need for variables as the context is maintained in the chain.

Trade-offs:

* Can lead to less intuitive code for those unfamiliar with the pattern.
* Debugging can be challenging due to the chaining of method calls.
* Overuse can lead to complex and hard-to-maintain code structures.

## Related Patterns

* [Builder](https://java-design-patterns.com/patterns/builder/): Often implemented using a Fluent Interface to construct objects step-by-step. The Builder Pattern focuses on constructing complex objects, while Fluent Interface emphasizes the method chaining mechanism.
* [Chain of Responsibility](https://java-design-patterns.com/patterns/chain-of-responsibility/): Fluent Interfaces can be seen as a specific utilization of the Chain of Responsibility, where each method in the chain handles a part of the task and then delegates to the next method.

## Credits

* [Fluent Interface - Martin Fowler](http://www.martinfowler.com/bliki/FluentInterface.html)
* [Evolutionary architecture and emergent design: Fluent interfaces - Neal Ford](http://www.ibm.com/developerworks/library/j-eaed14/)
* [Internal DSL](http://www.infoq.com/articles/internal-dsls-java)
* [Domain Specific Languages](https://www.amazon.com/gp/product/0321712943/ref=as_li_tl?ie=UTF8&tag=javadesignpat-20&camp=1789&creative=9325&linkCode=as2&creativeASIN=0321712943&linkId=ad8351d6f5be7d8b7ecdb650731f85df)
* [Effective Java](https://amzn.to/4d4azvL)
* [Java Design Pattern Essentials](https://amzn.to/44bs6hG)
* [Domain-Driven Design: Tackling Complexity in the Heart of Software](https://amzn.to/3UrXkh2)
