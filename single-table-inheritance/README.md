---
title: Single Table Inheritance Pattern
category: Structural
language: en
tag:
  - Data access
---

## Single Table Inheritance(STI)

## Intent

Represents an inheritance hierarchy of classes as a single table that has columns for all the fields of the various classes.

## Explanation

Real-world example

> There can be many different types of vehicles in this world but all of them
> come under the single umbrella of Vehicle

In plain words

> It maps each instance of class in an inheritance tree into a single table.

Wikipedia says

> Single table inheritance is a way to emulate object-oriented inheritance in a relational database.
> When mapping from a database table to an object in an object-oriented language,
> a field in the database identifies what class in the hierarchy the object belongs to.
> All fields of all the classes are stored in the same table, hence the name "Single Table Inheritance".

**Programmatic Example**

Baeldung - Hibernate Inheritance

> We can define the strategy we want to use by adding the @Inheritance annotation to the superclass:

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class MyProduct {
  @Id
  private long productId;
  private String name;

  // constructor, getters, setters
}
```

The identifier of the entities is also defined in the superclass.

Then we can add the subclass entities:

```java
@Entity
public class Book extends MyProduct {
  private String author;
}
```

```java
@Entity
public class Pen extends MyProduct {
  private String color;
}
```
Discriminator Values

- Since the records for all entities will be in the same table, Hibernate needs a way to differentiate between them.

- By default, this is done through a discriminator column called DTYPE that has the name of the entity as a value.

- To customize the discriminator column, we can use the @DiscriminatorColumn annotation:

```java
@Entity(name="products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="product_type",
        discriminatorType = DiscriminatorType.INTEGER)
public class MyProduct {
  // ...
}
```
- Here we’ve chosen to differentiate MyProduct subclass entities by an integer column called product_type.

- Next, we need to tell Hibernate what value each subclass record will have for the product_type column:

```java
@Entity
@DiscriminatorValue("1")
public class Book extends MyProduct {
  // ...
}
```
```java
@Entity
@DiscriminatorValue("2")
public class Pen extends MyProduct {
  // ...
}
```

- Hibernate adds two other predefined values that the annotation can take — null and not null:

  - @DiscriminatorValue(“null”) means that any row without a discriminator value will be mapped to the entity class with this annotation; this can be applied to the root class of the hierarchy.
  - @DiscriminatorValue(“not null”) – Any row with a discriminator value not matching any of the ones associated with entity definitions will be mapped to the class with this annotation.


## Class diagram

![alt text](./etc/single-table-inheritance.urm.png "Singleton pattern class diagram")

## Applicability

Use the Singleton pattern when

* Use STI When The Subclasses Have The Same Fields/Columns But Different Behavior
  - A good indication that STI is right is when the different subclasses have the same fields/columns but different methods. In the accounts example above, we expect all the columns in the database to be used by each subclass. Otherwise, there will be a lot of null columns in the database.
    <br><br>
* Use STI When We Expect To Perform Queries Across All Subclasses
  - Another good indication STI is right is if we expect to perform queries across all classes. For example, if we want to find the top 10 accounts with the highest balances across all types, STI allows lets us use just one query, whereas MTI will require in memory manipulation.


### Tutorials

- <a href ="https://www.youtube.com/watch?v=M5YrLtAHtOo" >Java Brains - Single Table Inheritance</a>

## Consequences

* Fields are sometimes relevant and sometimes not, which can be confusing
  to people using the tables directly.
* Columns used only by some subclasses lead to wasted space in the database.
  How much this is actually a problem depends on the specific data
  characteristics and how well the database compresses empty columns.
  Oracle, for example, is very efficient in trimming wasted space, particularly
  if you keep your optional columns to the right side of the database
  table. Each database has its own tricks for this.
* The single table may end up being too large, with many indexes and frequent
  locking, which may hurt performance. You can avoid this by having
  separate index tables that either list keys of rows that have a certain property
  or that copy a subset of fields relevant to an index.
* You only have a single namespace for fields, so you have to be sure that
  you don’t use the same name for different fields. Compound names with
  the name of the class as a prefix or suffix help here.

## Related patterns

* MappedSuperclass
* Single Table
* Joined Table
* Table per Class

## Credits

* [Single Table Inheritance - martinFowler.com](https://www.martinfowler.com/eaaCatalog/singleTableInheritance.html)
* [Patterns of Enterprise Application Architecture](https://books.google.co.in/books?id=vqTfNFDzzdIC&pg=PA278&redir_esc=y#v=onepage&q&f=false)
