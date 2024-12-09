---
title: "Table Inheritance Pattern in Java: Modeling Hierarchical Data in Relational Databases"
shortTitle: Table Inheritance
description: "Explore the Table Inheritance pattern in Java with real-world examples, database schema, and tutorials. Learn how to model class hierarchies elegantly in relational databases."
category: Data Access Pattern, Structural Pattern
language: en
tag:
- Decoupling
- Inheritance
- Polymorphism
- Object Mapping
- Persistence
- Data Transformation
---

## Also Known As
- Class Table Inheritance
---

## Intent of Table Inheritance Pattern
The Table Inheritance pattern models a class hierarchy in a relational database by creating 
separate tables for each class in the hierarchy. These tables share a common primary key, which in
subclass tables also serves as a foreign key referencing the primary key of the base class table. 
This linkage maintains relationships and effectively represents the inheritance structure. This pattern 
enables the organization of complex data models, particularly when subclasses have unique properties 
that must be stored in distinct tables.

---

## Detailed Explanation of Table Inheritance Pattern with Real-World Examples

### Real-World Example
Consider a **Vehicle Management System** with a `Vehicle` superclass and subclasses like `Car` and `Truck`.

- The **Vehicle Table** stores attributes common to all vehicles, such as `make`, `model`, and `year`. Its primary key (`id`) uniquely identifies each vehicle.
- The **Car Table** and **Truck Table** store attributes specific to their respective types, such as `numberOfDoors` for cars and `payloadCapacity` for trucks.
- The `id` column in the **Car Table** and **Truck Table** serves as both the primary key for those tables and a foreign key referencing the `id` in the **Vehicle Table**.

This setup ensures each subclass entry corresponds to a base class entry, maintaining the inheritance relationship while keeping subclass-specific data in their own tables.

### In Plain Words
In table inheritance, each class in the hierarchy is represented by a separate table, which 
allows for a clear distinction between shared attributes (stored in the base class table) and 
specific attributes (stored in subclass tables).

### Martin Fowler Says

Relational databases don't support inheritance, which creates a mismatch when mapping objects. 
To fix this, Table Inheritance uses a separate table for each class in the hierarchy while maintaining 
relationships through foreign keys, making it easier to link the classes together in the database.

For more detailed information, refer to Martin Fowler's article on [Class Table Inheritance](https://martinfowler.com/eaaCatalog/classTableInheritance.html).


## Programmatic Example of Table Inheritance Pattern in Java


The `Vehicle` class will be the superclass, and we will have `Car` and `Truck` as subclasses that extend 
`Vehicle`. The `Vehicle` class will store common attributes, while `Car` and `Truck` will store 
attributes specific to those subclasses.

### Key Aspects of the Pattern:

1. **Superclass (`Vehicle`)**:  
   The `Vehicle` class stores attributes shared by all vehicle types, such as:
    - `make`: The manufacturer of the vehicle.
    - `model`: The model of the vehicle.
    - `year`: The year the vehicle was manufactured.
    - `id`: A unique identifier for the vehicle.

   These attributes are stored in the **`Vehicle` table** in the database.

2. **Subclass (`Car` and `Truck`)**:  
   Each subclass (`Car` and `Truck`) stores attributes specific to that vehicle type:
    - `Car`: Has an additional attribute `numberOfDoors` representing the number of doors the car has.
    - `Truck`: Has an additional attribute `payloadCapacity` representing the payload capacity of the truck.

   These subclass-specific attributes are stored in the **`Car` and `Truck` tables**.

3. **Foreign Key Relationship**:  
   Each subclass (`Car` and `Truck`) contains the `id` field which acts as a **foreign key** that 
references the primary key (`id`) of the superclass (`Vehicle`). This foreign key ensures the 
relationship between the common attributes in the `Vehicle` table and the specific attributes in the
subclass tables (`Car` and `Truck`).


```java
/**
 * Superclass
 * Represents a generic vehicle with basic attributes like make, model, year, and ID.
 */
public class Vehicle {
    private String make;
    private String model;
    private int year;
    private int id;

    // Constructor, getters, and setters...
}

/**
 * Represents a car, which is a subclass of Vehicle.
 */
public class Car extends Vehicle {
    private int numberOfDoors;

    // Constructor, getters, and setters...
}

/**
 * Represents a truck, which is a subclass of Vehicle.
 */
public class Truck extends Vehicle {
    private int payloadCapacity;

    // Constructor, getters, and setters...
}
```



## Table Inheritance Pattern Class Diagram


<img src="etc/class-diagram.png" width="400" height="500" />






## Table Inheritance Pattern Database Schema

### Vehicle Table
| Column | Description                         |
|--------|-------------------------------------|
| id     | Primary key                        |
| make   | The make of the vehicle            |
| model  | The model of the vehicle           |
| year   | The manufacturing year of the vehicle |

### Car Table
| Column          | Description                         |
|------------------|-------------------------------------|
| id              | Foreign key referencing `Vehicle(id)` |
| numberOfDoors   | Number of doors in the car          |

### Truck Table
| Column           | Description                         |
|-------------------|-------------------------------------|
| id               | Foreign key referencing `Vehicle(id)` |
| payloadCapacity  | Payload capacity of the truck       |

---

## When to Use the Table Inheritance Pattern in Java

- When your application requires a clear mapping of an object-oriented class hierarchy to relational tables.
- When subclasses have unique attributes that do not fit into a single base table.
- When scalability and normalization of data are important considerations.
- When you need to separate concerns and organize data in a way that each subclass has its own 
table but maintains relationships with the superclass.

## Table Inheritance Pattern Java Tutorials

- [Software Patterns Lexicon: Class Table Inheritance](https://softwarepatternslexicon.com/patterns-sql/4/4/2/)
- [Martin Fowler: Class Table Inheritance](http://thierryroussel.free.fr/java/books/martinfowler/www.martinfowler.com/isa/classTableInheritance.html)

---

## Real-World Applications of Table Inheritance Pattern in Java

- **Vehicle Management System**: Used to store different types of vehicles like Car and Truck in separate tables but maintain a relationship through a common superclass `Vehicle`.
- **E-Commerce Platforms**: Where different product types, such as Clothing, Electronics, and Furniture, are stored in separate tables with shared attributes in a superclass `Product`.

## Benefits and Trade-offs of Table Inheritance Pattern

### Benefits

- **Clear Structure**: Each class has its own table, making the data model easier to maintain and understand.
- **Scalability**: Each subclass can be extended independently without affecting the other tables, making the system more scalable.
- **Data Normalization**: Helps avoid data redundancy and keeps the schema normalized.

### Trade-offs

- **Multiple Joins**: Retrieving data that spans multiple subclasses may require joining multiple tables, which could lead to performance issues.
- **Increased Complexity**: Managing relationships between tables and maintaining integrity can become more complex.
- **Potential for Sparse Tables**: Subclasses with fewer attributes may end up with tables that have many null fields.

## Related Java Design Patterns

- **Single Table Inheritance** – A strategy where a single table is used to store all classes in an 
inheritance hierarchy. It stores all attributes of the class and its subclasses in one table.
- **Singleton Pattern** – Used when a class needs to have only one instance.


## References and Credits

- **Martin Fowler** - [*Patterns of Enterprise Application Architecture*](https://www.amazon.com/Patterns-Enterprise-Application-Architecture-Martin/dp/0321127420)
- **Java Persistence with Hibernate** - [Link to book](https://www.amazon.com/Java-Persistence-Hibernate-Christian-Bauer/dp/193239469X)
- **Object-Relational Mapping on Wikipedia** - [Link to article](https://en.wikipedia.org/wiki/Object-relational_mapping)
