---
layout: pattern
title: Fluent Interface
folder: fluentinterface
permalink: /patterns/fluentinterface/
categories: Functional
language: en
tags: 
 - Reactive
---

## Intent

A fluent interface provides an easy-readable, flowing interface, that often mimics a domain specific 
language. Using this pattern results in code that can be read nearly as human language.

## Explanation

The Fluent Interface pattern is useful when you want to provide an easy readable, flowing API. Those 
interfaces tend to mimic domain specific languages, so they can nearly be read as human languages.
 
A fluent interface can be implemented using any of
 
 * Method chaining - calling a method returns some object on which further methods can be called.
 * Static factory methods and imports.
 * Named parameters - can be simulated in Java using static factory methods.

Real world example

> We need to select numbers based on different criteria from the list. It's a great chance to 
> utilize fluent interface pattern to provide readable easy-to-use developer experience. 

In plain words

> Fluent Interface pattern provides easily readable flowing interface to code.

Wikipedia says

> In software engineering, a fluent interface is an object-oriented API whose design relies 
> extensively on method chaining. Its goal is to increase code legibility by creating a 
> domain-specific language (DSL).

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
  ...
}
```

The `LazyFluentIterable` is evaluated on termination.

```java
public class LazyFluentIterable<E> implements FluentIterable<E> {
  ...
}
```

Their usage is demonstrated with a simple number list that is filtered, transformed and collected. The 
result is printed afterwards.

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

* You provide an API that would benefit from a DSL-like usage.
* You have objects that are difficult to configure or use.

## Known uses

* [Java 8 Stream API](http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html)
* [Google Guava FluentIterable](https://github.com/google/guava/wiki/FunctionalExplained)
* [JOOQ](http://www.jooq.org/doc/3.0/manual/getting-started/use-cases/jooq-as-a-standalone-sql-builder/)
* [Mockito](http://mockito.org/)
* [Java Hamcrest](http://code.google.com/p/hamcrest/wiki/Tutorial)

## Credits

* [Fluent Interface - Martin Fowler](http://www.martinfowler.com/bliki/FluentInterface.html)
* [Evolutionary architecture and emergent design: Fluent interfaces - Neal Ford](http://www.ibm.com/developerworks/library/j-eaed14/)
* [Internal DSL](http://www.infoq.com/articles/internal-dsls-java)
* [Domain Specific Languages](https://www.amazon.com/gp/product/0321712943/ref=as_li_tl?ie=UTF8&tag=javadesignpat-20&camp=1789&creative=9325&linkCode=as2&creativeASIN=0321712943&linkId=ad8351d6f5be7d8b7ecdb650731f85df)
