# Table Inheritance

**Category:** Data Access Pattern, Structural Pattern

**Tags:** Decoupling, Inheritance, Polymorphism, Object Mapping, Persistence, Data Transformation

---

## Intent
The **Table Inheritance** pattern models a class hierarchy in a relational database. Each class in the hierarchy is mapped to its own table, and the tables are linked through foreign keys representing the inheritance structure.

This pattern is particularly useful for organizing complex data models when different subclasses have distinct properties that need to be stored in separate tables.

---

## Real-World Example
Imagine a vehicle management system with a `Vehicle` superclass and subclasses such as `Car` and `Truck`.
- The `Vehicle` table stores attributes common to all vehicles.
- The subclass tables (`Car` and `Truck`) store attributes specific to each subclass.

---

## Database Schema

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

## Benefits
- Decouples subclasses into their own tables for easier management and scalability.
- Allows for flexibility in subclass-specific attributes without altering the base table.

---
