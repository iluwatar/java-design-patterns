---
title: "Table Data Gateway Pattern in Java: Encapsulating Table Operations"
shortTitle: Table Data Gateway
description: "Learn how the Table Data Gateway pattern centralizes SQL operations for a database table behind a single gateway class."
category: Data access
language: en
tag:
  - Data access
  - Persistence
  - Encapsulation
---

## Also known as

* Table Gateway

## Intent

The Table Data Gateway pattern provides one object that acts as the gateway to all rows in a database table. It encapsulates SQL operations so callers can work with domain objects instead of issuing SQL directly.

## Detailed Explanation

CustomerTableGateway encapsulates create, read, update, and delete operations for the CUSTOMERS table. It accepts a DataSource, uses prepared statements, and maps query results to immutable Customer values.

Unlike a Row Data Gateway, a Table Data Gateway instance handles operations across the table rather than representing one specific row.

## Programmatic Example

The example uses an in-memory H2 database:

```java
var gateway = new CustomerTableGateway(dataSource);
gateway.insert(new Customer(1, "Alice", "Smith"));

var customer = gateway.findById(1).orElseThrow();
gateway.update(new Customer(customer.id(), customer.firstName(), "Jones"));
gateway.delete(customer.id());
```

## Known Uses

* Fowler's Patterns of Enterprise Application Architecture, Table Data Gateway: https://martinfowler.com/eaaCatalog/tableDataGateway.html

## Consequences

* SQL for a table is centralized in one class.
* Domain objects remain independent of database access code.
* The gateway can grow large when a table has many operations or complex query behavior.

## Related Patterns

* Row Data Gateway represents one row per gateway instance.
* Table Module combines table-wide data and domain logic in one class.
* Data Access Object provides an abstraction over a data access mechanism.
