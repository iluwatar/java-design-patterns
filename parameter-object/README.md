---
title: Parameter Object
category: Structural
language: en
tag:
    - Abstraction
    - Code simplification
    - Decoupling
    - Encapsulation
    - Object composition
---

## Also known as

* Argument Object

## Intent

Simplify method signatures by encapsulating parameters into a single object, promoting cleaner code and better maintainability.

## Explanation

Real-world example

> Imagine booking a travel package that includes a flight, hotel, and car rental. Instead of asking the customer to provide separate details for each component (flight details, hotel details, and car rental details) every time, a travel agent asks the customer to fill out a single comprehensive form that encapsulates all the necessary information:
>
> - Flight details: Departure city, destination city, departure date, return date.
> - Hotel details: Hotel name, check-in date, check-out date, room type.
> - Car rental details: Pickup location, drop-off location, rental dates, car type.
> 
> In this analogy, the comprehensive form is the parameter object. It groups together all related details (parameters) into a single entity, making the booking process more streamlined and manageable. The travel agent (method) only needs to handle one form (parameter object) instead of juggling multiple pieces of information.

In plain words

> The Parameter Object pattern encapsulates multiple related parameters into a single object to simplify method signatures and enhance code maintainability.

wiki.c2.com says

> Replace the LongParameterList with a ParameterObject; an object or structure with data members representing the arguments to be passed in.

**Programmatic example**

The Parameter Object design pattern is a way to group multiple parameters into a single object. This simplifies method signatures and enhances code maintainability.

First, let's look at the `ParameterObject` class. This class encapsulates the parameters needed for the search operation. It uses [Builder pattern](https://java-design-patterns.com/patterns/builder/) to allow for easy creation of objects, even when there are many parameters.

```java
public class ParameterObject {

    private final String type;
    private final String sortBy;
    private final SortOrder sortOrder;

    private ParameterObject(Builder builder) {
        this.type = builder.type;
        this.sortBy = builder.sortBy;
        this.sortOrder = builder.sortOrder;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    // getters and Builder class omitted for brevity
}
```

The `Builder` class inside `ParameterObject` provides a way to construct a `ParameterObject` instance. It has methods for setting each of the parameters, and a `build()` method to create the `ParameterObject`.

```java
public static class Builder {

    private String type = "all";
    private String sortBy = "price";
    private SortOrder sortOrder = SortOrder.ASCENDING;

    public Builder withType(String type) {
        this.type = type;
        return this;
    }

    public Builder sortBy(String sortBy) {
        this.sortBy = sortBy;
        return this;
    }

    public Builder sortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
        return this;
    }

    public ParameterObject build() {
        return new ParameterObject(this);
    }
}
```

The `SearchService` class has a `search()` method that takes a `ParameterObject` as a parameter. This method uses the parameters encapsulated in the `ParameterObject` to perform a search operation.

```java
public class SearchService {

    public String search(ParameterObject parameterObject) {
        return getQuerySummary(parameterObject.getType(), parameterObject.getSortBy(),
                parameterObject.getSortOrder());
    }

    // getQuerySummary method omitted for brevity
}
```

Finally, in the `App` class, we create a `ParameterObject` using its builder, and then pass it to the `search()` method of `SearchService`.

```java
public class App {

    public static void main(String[] args) {
        ParameterObject params = ParameterObject.newBuilder()
                .withType("sneakers")
                .sortBy("brand")
                .build();
        LOGGER.info(params.toString());
        LOGGER.info(new SearchService().search(params));
    }
}
```

This example demonstrates how the Parameter Object pattern can simplify method signatures and make the code more maintainable. It also shows how the pattern can be combined with the Builder pattern to make object creation more flexible and readable.

## Applicability

* Methods require multiple parameters that logically belong together.
* There is a need to reduce the complexity of method signatures.
* The parameters may need to evolve over time, adding more properties without breaking existing method signatures.
* It’s beneficial to pass data through a method chain.

## Tutorials

* [Does Java have default parameters? (Daniel Olszewski)](http://dolszewski.com/java/java-default-parameters)


## Known Uses

* Java Libraries: Many Java frameworks and libraries use this pattern. For example, Java’s java.util.Calendar class has various methods where parameter objects are used to represent date and time components.
* Enterprise Applications: In large enterprise systems, parameter objects are used to encapsulate configuration data passed to services or API endpoints.

## Consequences

Benefits:

* Encapsulation: Groups related parameters into a single object, promoting encapsulation.
* Maintainability: Reduces method signature changes when parameters need to be added or modified.
* Readability: Simplifies method signatures, making the code easier to read and understand.
* Reusability: Parameter objects can be reused across different methods, reducing redundancy.

Trade-offs:

* Overhead: Introducing parameter objects can add some overhead, especially for simple methods that do not benefit significantly from this abstraction.
* Complexity: The initial creation of parameter objects might add complexity, especially for beginners.

## Related Patterns

* [Builder](https://java-design-patterns.com/patterns/builder/): Helps in creating complex objects step-by-step, often used in conjunction with parameter objects to manage the construction of these objects.
* [Composite](https://java-design-patterns.com/patterns/composite/): Sometimes used with parameter objects to handle hierarchical parameter data.
* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): Can be used to create instances of parameter objects, particularly when different parameter combinations are needed.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Refactoring: Improving the Design of Existing Code](https://amzn.to/3TVEgaB)
