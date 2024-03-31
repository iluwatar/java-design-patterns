---
title: Collection Pipeline
category: Functional
language: en
tag:
    - Reactive
    - Data processing
---

## Intent

The Collection Pipeline design pattern is intended to process collections of data by chaining together operations in a sequence where the output of one operation is the input for the next. It promotes a declarative approach to handling collections, focusing on what should be done rather than how.

## Explanation

Real-world example

> Imagine you're in a large library filled with books, and you're tasked with finding all the science fiction books published after 2000, then arranging them by author name in alphabetical order, and finally picking out the top 5 based on their popularity or ratings.

In plain words

> The Collection Pipeline pattern involves processing data by passing it through a series of operations, each transforming the data in sequence, much like an assembly line in a factory.

Wikipedia says

> In software engineering, a pipeline consists of a chain of processing elements (processes, threads, coroutines, functions, etc.), arranged so that the output of each element is the input of the next; the name is by analogy to a physical pipeline. Usually some amount of buffering is provided between consecutive elements. The information that flows in these pipelines is often a stream of records, bytes, or bits, and the elements of a pipeline may be called filters; this is also called the pipe(s) and filters design pattern. Connecting elements into a pipeline is analogous to function composition.

**Programmatic Example**

The Collection Pipeline pattern is implemented in this code example by using Java's Stream API to perform a series of transformations on a collection of Car objects. The transformations are chained together to form a pipeline. Here's a breakdown of how it's done:

1. Creation of Cars: A list of Car objects is created using the `CarFactory.createCars()` method.

`var cars = CarFactory.createCars();`

2. Filtering and Transforming: The `FunctionalProgramming.getModelsAfter2000(cars)` method filters the cars to only include those made after the year 2000, and then transforms the filtered cars into a list of their model names.

`var modelsFunctional = FunctionalProgramming.getModelsAfter2000(cars);`

In the `getModelsAfter2000` method, the pipeline is created as follows:

```java
public static List<String> getModelsAfter2000(List<Car> cars){
        return cars.stream().filter(car->car.getYear()>2000)
        .sorted(comparing(Car::getYear))
        .map(Car::getModel)
        .collect(toList());
        }
```

3. Grouping: The `FunctionalProgramming.getGroupingOfCarsByCategory(cars)` method groups the cars by their category.

`var groupingByCategoryFunctional = FunctionalProgramming.getGroupingOfCarsByCategory(cars);`

In the getGroupingOfCarsByCategory method, the pipeline is created as follows:

```java
public static Map<Category, List<Car>>getGroupingOfCarsByCategory(List<Car> cars){
        return cars.stream().collect(groupingBy(Car::getCategory));
        }
```

4. Filtering, Sorting and Transforming: The `FunctionalProgramming.getSedanCarsOwnedSortedByDate(List.of(john))` method filters the cars owned by a person to only include sedans, sorts them by date, and then transforms the sorted cars into a list of Car objects.

`var sedansOwnedFunctional = FunctionalProgramming.getSedanCarsOwnedSortedByDate(List.of(john));`

In the `getSedanCarsOwnedSortedByDate` method, the pipeline is created as follows:

```java
public static List<Car> getSedanCarsOwnedSortedByDate(List<Person> persons){
        return persons.stream().flatMap(person->person.getCars().stream())
        .filter(car->Category.SEDAN.equals(car.getCategory()))
        .sorted(comparing(Car::getDate))
        .collect(toList());
        }
```

In each of these methods, the Collection Pipeline pattern is used to perform a series of operations on the collection of cars in a declarative manner, which improves readability and maintainability.

## Class diagram

![alt text](./etc/collection-pipeline.png "Collection Pipeline")

## Applicability

This pattern is applicable in scenarios involving bulk data operations such as filtering, mapping, sorting, or reducing collections. It's particularly useful in data analysis, transformation tasks, and where a sequence of operations needs to be applied to each element of a collection.

## Known Uses

* LINQ in .NET
* Stream API in Java 8+
* Collections in modern functional languages (e.g., Haskell, Scala)
* Database query builders and ORM frameworks

## Consequences

Benefits:

* Readability: The code is more readable and declarative, making it easier to understand the sequence of operations.
* Maintainability: Easier to modify or extend the pipeline with additional operations.
* Reusability: Common operations can be abstracted into reusable functions.
* Lazy Evaluation: Some implementations allow for operations to be lazily evaluated, improving performance.

Trade-offs:

* Performance Overhead: Chaining multiple operations can introduce overhead compared to traditional loops, especially for short pipelines or very large collections.
* Debugging Difficulty: Debugging a chain of operations might be more challenging due to the lack of intermediate variables.
* Limited to Collections: Primarily focused on collections, and its utility might be limited outside of collection processing.

## Related Patterns

* [Builder](https://java-design-patterns.com/patterns/builder/): Similar fluent interface style but used for object construction.
* [Chain of Responsibility](https://java-design-patterns.com/patterns/chain-of-responsibility/): Conceptually similar in chaining handlers, but applied to object requests rather than data collection processing.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Can be used within a pipeline stage to encapsulate different algorithms that can be selected at runtime.

## Credits

* [Function composition and the Collection Pipeline pattern](https://www.ibm.com/developerworks/library/j-java8idioms2/index.html)
* [Collection Pipeline described by Martin Fowler](https://martinfowler.com/articles/collection-pipeline/)
* [Java8 Streams](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html)
* [Refactoring: Improving the Design of Existing Code](https://amzn.to/3VDMWDO)
* [Functional Programming in Scala](https://amzn.to/4cEo6K2)
* [Java 8 in Action: Lambdas, Streams, and functional-style programming](https://amzn.to/3THp4wy)
