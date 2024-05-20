---
title: Single Table Inheritance
category: Data access
language: en
tag:
    - Data access
    - Encapsulation
    - Persistence
    - Polymorphism
---

## Also known as

* Class Table Inheritance
* STI

## Intent

Simplify the storage of an inheritance hierarchy in a single database table, where rows represent objects of different classes and columns represent the union of all attributes.

## Explanation

Real-world example

> Imagine a library system where books, magazines, and DVDs are all stored in a single inventory table. This table includes columns for attributes common to all items, such as `Title`, `Author`, `PublicationDate`, and `ItemType`, as well as columns specific to certain types, like `ISBN` for books, `IssueNumber` for magazines, and `Duration` for DVDs. Each row represents an item in the library, with some columns left null depending on the item type. This setup simplifies the database schema by keeping all inventory items in one table, akin to Single Table Inheritance in a database context.

In plain words

> Single Table Inheritance stores an entire class hierarchy in a single database table, using a type discriminator column to distinguish between different subclasses.

Wikipedia says

> Single table inheritance is a way to emulate object-oriented inheritance in a relational database. When mapping from a database table to an object in an object-oriented language, a field in the database identifies what class in the hierarchy the object belongs to. All fields of all the classes are stored in the same table, hence the name "Single Table Inheritance".

**Programmatic Example**

Single Table Inheritance is a design pattern that maps an inheritance hierarchy of classes to a single database table. Each row in the table represents an instance of a class in the hierarchy. A special discriminator column is used to identify the class to which each row belongs.

This pattern is useful when classes in an inheritance hierarchy are not significantly different in terms of fields and behavior. It simplifies the database schema and can improve performance by avoiding joins.

Let's see how this pattern is implemented in the provided code.

The base class in our hierarchy is `Vehicle`. This class has common properties that all vehicles share, such as `manufacturer` and `model`.

```java
public abstract class Vehicle {
  private String manufacturer;
  private String model;
  // other common properties...
}
```

We have two subclasses, `PassengerVehicle` and `TransportVehicle`, which extend `Vehicle` and add additional properties.

```java
public abstract class PassengerVehicle extends Vehicle {
  private int noOfPassengers;
  // other properties specific to passenger vehicles...
}

public abstract class TransportVehicle extends Vehicle {
  private int loadCapacity;
  // other properties specific to transport vehicles...
}
```

Finally, we have concrete classes like `Car` and `Truck` that extend `PassengerVehicle` and `TransportVehicle` respectively.

```java
public class Car extends PassengerVehicle {
  private int trunkCapacity;
  // other properties specific to cars...
}

public class Truck extends TransportVehicle {
  private int towingCapacity;
  // other properties specific to trucks...
}
```

In this example, we're using Hibernate as our ORM. We map our class hierarchy to a single table using the `@Entity` and `@Inheritance` annotations.

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Vehicle {
  // properties...
}
```

The `@DiscriminatorColumn` annotation is used to specify the discriminator column in the table. This column will hold the class name for each row.

```java
@DiscriminatorColumn(name = "vehicle_type")
public abstract class Vehicle {
  // properties...
}
```

Each subclass specifies its discriminator value using the `@DiscriminatorValue` annotation.

```java
@DiscriminatorValue("CAR")
public class Car extends PassengerVehicle {
  // properties...
}

@DiscriminatorValue("TRUCK")
public class Truck extends TransportVehicle {
  // properties...
}
```

The `VehicleService` class provides methods for saving and retrieving vehicles. When we save a `Car` or `Truck` object, Hibernate will automatically set the discriminator column to the appropriate value. When we retrieve a vehicle, Hibernate will use the discriminator column to instantiate the correct class.

```java
public class VehicleService {
  public Vehicle saveVehicle(Vehicle vehicle) {
    // save vehicle to database...
  }

  public Vehicle getVehicle(Long id) {
    // retrieve vehicle from database...
  }

  public List<Vehicle> getAllVehicles() {
    // retrieve all vehicles from database...
  }
}
```

The Single Table Inheritance pattern is a simple and efficient way to map an inheritance hierarchy to a relational database. However, it can lead to sparse tables if subclasses have many unique fields. In such cases, other patterns like Class Table Inheritance or Concrete Table Inheritance might be more appropriate.

## Class diagram

![Single Table Inheritance](./etc/single-table-inheritance.urm.png "Single Table Inheritance")

## Applicability

* Use when you have a class hierarchy with subclasses that share a common base class and you want to store all instances of the hierarchy in a single table.
* Ideal for small to medium-sized applications where the simplicity of a single table outweighs the performance cost of null fields for some subclasses.

### Tutorials

* [Hibernate Tutorial 18 - Implementing Inheritance - Single Table Strategy - Java Brains](https://www.youtube.com/watch?v=M5YrLtAHtOo)

## Known uses

* Hibernate and JPA implementations in Java applications often use Single Table Inheritance for ORM mapping.
* Rails ActiveRecord supports Single Table Inheritance out of the box.

## Consequences

Benefits:

* Simplifies database schema by reducing the number of tables.
* Easier to manage relationships and queries since all data is in one table.

Trade-offs:

* Can lead to sparsely populated tables with many null values.
* May cause performance issues for large hierarchies due to table size and the need to filter by type.
* Changes in the inheritance hierarchy require schema changes.

## Related patterns

* Class Table Inheritance: Uses separate tables for each class in the hierarchy, reducing null values but increasing complexity in joins.
* Concrete Table Inheritance: Each class in the hierarchy has its own table, reducing redundancy but increasing the number of tables.

## Credits

* [Domain-Driven Design: Tackling Complexity in the Heart of Software](https://amzn.to/3wlDrze)
* [Java Persistence with Hibernate](https://amzn.to/44tP1ox)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Single Table Inheritance - Martin Fowler](https://www.martinfowler.com/eaaCatalog/singleTableInheritance.html)
