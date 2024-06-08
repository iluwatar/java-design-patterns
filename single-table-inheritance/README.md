---
title: "Single Table Inheritance Pattern in Java: Streamlining Object Mapping with Unified Table Structures"
shortTitle: Single Table Inheritance
description: "Discover how the Single Table Inheritance pattern simplifies database schema in Java applications. Learn its use, benefits, and implementation in our comprehensive guide."
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

## Intent of Single Table Inheritance Design Pattern

Single Table Inheritance pattern simplifies database schema in Java applications.

Streamline the storage of an inheritance hierarchy in a single database table, where rows represent objects of different classes and columns represent the union of all attributes.

## Detailed Explanation of Single Table Inheritance Pattern with Real-World Examples

Real-world example

> Imagine a library system where books, magazines, and DVDs are all stored in a single inventory table. This table includes columns for attributes common to all items, such as `Title`, `Author`, `PublicationDate`, and `ItemType`, as well as columns specific to certain types, like `ISBN` for books, `IssueNumber` for magazines, and `Duration` for DVDs. Each row represents an item in the library, with some columns left null depending on the item type. This setup simplifies the database schema by keeping all inventory items in one table, akin to Single Table Inheritance in a database context.

In plain words

> Single Table Inheritance stores an entire class hierarchy in a single database table, using a type discriminator column to distinguish between different subclasses.

Wikipedia says

> Single table inheritance is a way to emulate object-oriented inheritance in a relational database. When mapping from a database table to an object in an object-oriented language, a field in the database identifies what class in the hierarchy the object belongs to. All fields of all the classes are stored in the same table, hence the name "Single Table Inheritance".

## Programmatic Example of Single Table Inheritance Pattern in Java

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

Finally, here is the Spring Boot application that runs our example.

```java
@SpringBootApplication
@AllArgsConstructor
public class SingleTableInheritance implements CommandLineRunner {

  //Autowiring the VehicleService class to execute the business logic methods
  private final VehicleService vehicleService;

  public static void main(String[] args) {
    SpringApplication.run(SingleTableInheritance.class, args);
  }

  @Override
  public void run(String... args) {

    Logger log = LoggerFactory.getLogger(SingleTableInheritance.class);

    log.info("Saving Vehicles :- ");

    // Saving Car to DB as a Vehicle
    Vehicle vehicle1 = new Car("Tesla", "Model S", 4, 825);
    Vehicle car1 = vehicleService.saveVehicle(vehicle1);
    log.info("Vehicle 1 saved : {}", car1);

    // Saving Truck to DB as a Vehicle
    Vehicle vehicle2 = new Truck("Ford", "F-150", 3325, 14000);
    Vehicle truck1 = vehicleService.saveVehicle(vehicle2);
    log.info("Vehicle 2 saved : {}\n", truck1);


    log.info("Fetching Vehicles :- ");

    // Fetching the Car from DB
    Car savedCar1 = (Car) vehicleService.getVehicle(vehicle1.getVehicleId());
    log.info("Fetching Car1 from DB : {}", savedCar1);

    // Fetching the Truck from DB
    Truck savedTruck1 = (Truck) vehicleService.getVehicle(vehicle2.getVehicleId());
    log.info("Fetching Truck1 from DB : {}\n", savedTruck1);

    log.info("Fetching All Vehicles :- ");

    // Fetching the Vehicles present in the DB
    List<Vehicle> allVehiclesFromDb = vehicleService.getAllVehicles();
    allVehiclesFromDb.forEach(s -> log.info(s.toString()));
  }
}
```

Console output:

```
2024-05-27T12:29:49.949+03:00  INFO 56372 --- [           main] com.iluwatar.SingleTableInheritance      : Starting SingleTableInheritance using Java 17.0.4.1 with PID 56372 (/Users/ilkka.seppala/git/java-design-patterns/single-table-inheritance/target/classes started by ilkka.seppala in /Users/ilkka.seppala/git/java-design-patterns)
2024-05-27T12:29:49.951+03:00  INFO 56372 --- [           main] com.iluwatar.SingleTableInheritance      : No active profile set, falling back to 1 default profile: "default"
2024-05-27T12:29:50.154+03:00  INFO 56372 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Bootstrapping Spring Data JPA repositories in DEFAULT mode.
2024-05-27T12:29:50.176+03:00  INFO 56372 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Finished Spring Data repository scanning in 19 ms. Found 1 JPA repository interface.
2024-05-27T12:29:50.315+03:00  INFO 56372 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [name: default]
2024-05-27T12:29:50.345+03:00  INFO 56372 --- [           main] org.hibernate.Version                    : HHH000412: Hibernate ORM core version 6.4.4.Final
2024-05-27T12:29:50.360+03:00  INFO 56372 --- [           main] o.h.c.internal.RegionFactoryInitiator    : HHH000026: Second-level cache disabled
2024-05-27T12:29:50.457+03:00  INFO 56372 --- [           main] o.s.o.j.p.SpringPersistenceUnitInfo      : No LoadTimeWeaver setup: ignoring JPA class transformer
2024-05-27T12:29:50.468+03:00  INFO 56372 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2024-05-27T12:29:50.541+03:00  INFO 56372 --- [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:sti user=SA
2024-05-27T12:29:50.542+03:00  INFO 56372 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2024-05-27T12:29:50.930+03:00  INFO 56372 --- [           main] o.h.e.t.j.p.i.JtaPlatformInitiator       : HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
2024-05-27T12:29:50.953+03:00  INFO 56372 --- [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
2024-05-27T12:29:51.094+03:00  INFO 56372 --- [           main] com.iluwatar.SingleTableInheritance      : Started SingleTableInheritance in 1.435 seconds (process running for 1.678)
2024-05-27T12:29:51.095+03:00  INFO 56372 --- [           main] com.iluwatar.SingleTableInheritance      : Saving Vehicles :- 
2024-05-27T12:29:51.114+03:00  INFO 56372 --- [           main] com.iluwatar.SingleTableInheritance      : Vehicle 1 saved : Car{PassengerVehicle(noOfPassengers=4)}
2024-05-27T12:29:51.115+03:00  INFO 56372 --- [           main] com.iluwatar.SingleTableInheritance      : Vehicle 2 saved : Truck{ TransportVehicle(loadCapacity=3325), towingCapacity=14000}

2024-05-27T12:29:51.115+03:00  INFO 56372 --- [           main] com.iluwatar.SingleTableInheritance      : Fetching Vehicles :- 
2024-05-27T12:29:51.129+03:00  INFO 56372 --- [           main] com.iluwatar.SingleTableInheritance      : Fetching Car1 from DB : Car{PassengerVehicle(noOfPassengers=0)}
2024-05-27T12:29:51.130+03:00  INFO 56372 --- [           main] com.iluwatar.SingleTableInheritance      : Fetching Truck1 from DB : Truck{ TransportVehicle(loadCapacity=0), towingCapacity=14000}

2024-05-27T12:29:51.130+03:00  INFO 56372 --- [           main] com.iluwatar.SingleTableInheritance      : Fetching All Vehicles :- 
2024-05-27T12:29:51.169+03:00  INFO 56372 --- [           main] com.iluwatar.SingleTableInheritance      : Car{PassengerVehicle(noOfPassengers=0)}
2024-05-27T12:29:51.169+03:00  INFO 56372 --- [           main] com.iluwatar.SingleTableInheritance      : Truck{ TransportVehicle(loadCapacity=0), towingCapacity=14000}
2024-05-27T12:29:51.172+03:00  INFO 56372 --- [ionShutdownHook] j.LocalContainerEntityManagerFactoryBean : Closing JPA EntityManagerFactory for persistence unit 'default'
2024-05-27T12:29:51.173+03:00  INFO 56372 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown initiated...
2024-05-27T12:29:51.174+03:00  INFO 56372 --- [ionShutdownHook] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Shutdown completed.
```

The Single Table Inheritance pattern is a simple and efficient way to map an inheritance hierarchy to a relational database. However, it can lead to sparse tables if subclasses have many unique fields. In such cases, other patterns like Class Table Inheritance or Concrete Table Inheritance might be more appropriate.

## When to Use the Single Table Inheritance Pattern in Java

* Use when you have a class hierarchy with subclasses that share a common base class and you want to store all instances of the hierarchy in a single table.
* Ideal for small to medium-sized applications where the simplicity of a single table outweighs the performance cost of null fields for some subclasses.

## Single Table Inheritance Pattern Tutorials

* [Hibernate Tutorial 18 - Implementing Inheritance - Single Table Strategy (Java Brains)](https://www.youtube.com/watch?v=M5YrLtAHtOo)

## Real-World Applications of Single Table Inheritance Pattern in Java

* Hibernate and JPA implementations in Java applications often use Single Table Inheritance for ORM mapping.
* Rails ActiveRecord supports Single Table Inheritance out of the box.

## Benefits and Trade-offs of Single Table Inheritance Pattern

Benefits:

Using the Single Table Inheritance pattern in Java ORM

* Simplifies database schema by reducing the number of tables.
* Easier to manage relationships and queries since all data is in one table.

Trade-offs:

* Can lead to sparsely populated tables with many null values.
* May cause performance issues for large hierarchies due to table size and the need to filter by type.
* Changes in the inheritance hierarchy require schema changes.

## Related Java Design Patterns

* Class Table Inheritance: Uses separate tables for each class in the hierarchy, reducing null values but increasing complexity in joins.
* Concrete Table Inheritance: Each class in the hierarchy has its own table, reducing redundancy but increasing the number of tables.

## References and Credits

* [Domain-Driven Design: Tackling Complexity in the Heart of Software](https://amzn.to/3wlDrze)
* [Java Persistence with Hibernate](https://amzn.to/44tP1ox)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Single Table Inheritance (Martin Fowler)](https://www.martinfowler.com/eaaCatalog/singleTableInheritance.html)
