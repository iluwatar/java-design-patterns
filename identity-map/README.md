---
title: "Identity Map Pattern in Java: Managing Object Identity for Efficiency"
shortTitle: Identity Map
description: "Learn about the Identity Map pattern in Java. This design pattern prevents duplicate objects in memory, improving performance and consistency in your applications. Explore implementation examples, benefits, and best practices."
category: Behavioral
language: en
tag:
  - Data access
  - Decoupling
  - Enterprise patterns
  - Object mapping
  - Persistence
  - Performance
---

## Intent of Identity Map Design Pattern

The Identity Map design pattern in Java aims to ensure that each object gets loaded only once by keeping every loaded object in a map, enhancing database performance and memory management.

## Detailed Explanation of Identity Map Pattern with Real-World Examples

Real-world example

> Imagine you are organizing a conference and have a registration desk where every attendee must check in. This scenario illustrates the Identity Map pattern in Java which prevents duplicate objects. To avoid unnecessary delays and confusion, each attendee's details are entered into a computer system the first time they check in. If the same attendee comes to the desk again, the system quickly retrieves their details without requiring them to re-submit the same information. This ensures each attendee's information is handled efficiently and consistently, similar to how the Identity Map pattern ensures that an object is loaded only once and reused throughout the application.

In plain words

> The Identity Map design pattern ensures that each unique object is loaded only once and reused from a central registry, preventing duplicate objects in an application's memory.

Wikipedia says

> In the design of DBMS, the identity map pattern is a database access design pattern used to improve performance by providing a context-specific, in-memory cache to prevent duplicate retrieval of the same object data from the database.

## Programmatic Example of Identity Map Pattern in Java

For the purpose of this demonstration in Java programming, assume we have already created a database instance, showcasing the Identity Map pattern to avoid duplicate objects in memory.

Let's first look at the implementation of `Person` entity, and it's fields:

```java
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
public final class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private int personNationalId;
    private String name;
    private long phoneNum;

    @Override
    public String toString() {
        return "Person ID is : " + personNationalId + " ; Person Name is : " + name + " ; Phone Number is :" + phoneNum;
    }
}

```

The following is the implementation of the `PersonFinder` which is the entity that the user will utilize in order to search for a record in our database. It has the relevant DB attached to it. It also maintains an `IdentityMap` to store the recently read records.

```java
@Slf4j
@Getter
@Setter
public class PersonFinder {
    private static final long serialVersionUID = 1L;
    //  Access to the Identity Map
    private IdentityMap identityMap = new IdentityMap();
    private PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();

    public Person getPerson(int key) {
        // Try to find person in the identity map
        Person person = this.identityMap.getPerson(key);
        if (person != null) {
            LOGGER.info("Person found in the Map");
            return person;
        } else {
            // Try to find person in the database
            person = this.db.find(key);
            if (person != null) {
                this.identityMap.addPerson(person);
                LOGGER.info("Person found in DB.");
                return person;
            }
            LOGGER.info("Person with this ID does not exist.");
            return null;
        }
    }
}

```

The identity map field in the above class is simply an abstraction of a hashMap with **personNationalId** as the keys and the corresponding person object as the value. Here is its implementation:

```java
@Slf4j
@Getter
public class IdentityMap {
    private Map<Integer, Person> personMap = new HashMap<>();

    public void addPerson(Person person) {
        if (!personMap.containsKey(person.getPersonNationalId())) {
            personMap.put(person.getPersonNationalId(), person);
        } else { // Ensure that addPerson does not update a record. This situation will never arise in our implementation. Added only for testing purposes.
            LOGGER.info("Key already in Map");
        }
    }

    public Person getPerson(int id) {
        Person person = personMap.get(id);
        if (person == null) {
            LOGGER.info("ID not in Map.");
        }
        return person;
    }

    public int size() {
        if (personMap == null) {
            return 0;
        }
        return personMap.size();
    }
}
```

Now, let's see how the identity map works in our `App`'s `main` function.

```java
public static void main(String[] args) {

    // Dummy Persons
    Person person1 = new Person(1, "John", 27304159);
    Person person2 = new Person(2, "Thomas", 42273631);
    Person person3 = new Person(3, "Arthur", 27489171);
    Person person4 = new Person(4, "Finn", 20499078);
    Person person5 = new Person(5, "Michael", 40599078);

    // Init database
    PersonDbSimulatorImplementation db = new PersonDbSimulatorImplementation();
    db.insert(person1);
    db.insert(person2);
    db.insert(person3);
    db.insert(person4);
    db.insert(person5);

    // Init a personFinder
    PersonFinder finder = new PersonFinder();
    finder.setDb(db);

    // Find persons in DataBase not the map.
    LOGGER.info(finder.getPerson(2).toString());
    LOGGER.info(finder.getPerson(4).toString());
    LOGGER.info(finder.getPerson(5).toString());
    // Find the person in the map.
    LOGGER.info(finder.getPerson(2).toString());
}
```

Running the example produces the following console output:

```
11:19:43.775 [main] INFO com.iluwatar.identitymap.IdentityMap -- ID not in Map.
11:19:43.780 [main] INFO com.iluwatar.identitymap.PersonDbSimulatorImplementation -- Person ID is : 2 ; Person Name is : Thomas ; Phone Number is :42273631
11:19:43.780 [main] INFO com.iluwatar.identitymap.PersonFinder -- Person found in DB.
11:19:43.780 [main] INFO com.iluwatar.identitymap.App -- Person ID is : 2 ; Person Name is : Thomas ; Phone Number is :42273631
11:19:43.780 [main] INFO com.iluwatar.identitymap.IdentityMap -- ID not in Map.
11:19:43.780 [main] INFO com.iluwatar.identitymap.PersonDbSimulatorImplementation -- Person ID is : 4 ; Person Name is : Finn ; Phone Number is :20499078
11:19:43.780 [main] INFO com.iluwatar.identitymap.PersonFinder -- Person found in DB.
11:19:43.780 [main] INFO com.iluwatar.identitymap.App -- Person ID is : 4 ; Person Name is : Finn ; Phone Number is :20499078
11:19:43.780 [main] INFO com.iluwatar.identitymap.IdentityMap -- ID not in Map.
11:19:43.780 [main] INFO com.iluwatar.identitymap.PersonDbSimulatorImplementation -- Person ID is : 5 ; Person Name is : Michael ; Phone Number is :40599078
11:19:43.780 [main] INFO com.iluwatar.identitymap.PersonFinder -- Person found in DB.
11:19:43.780 [main] INFO com.iluwatar.identitymap.App -- Person ID is : 5 ; Person Name is : Michael ; Phone Number is :40599078
11:19:43.780 [main] INFO com.iluwatar.identitymap.IdentityMap -- Person ID is : 2 ; Person Name is : Thomas ; Phone Number is :42273631
11:19:43.780 [main] INFO com.iluwatar.identitymap.PersonFinder -- Person found in the Map
11:19:43.780 [main] INFO com.iluwatar.identitymap.App -- Person ID is : 2 ; Person Name is : Thomas ; Phone Number is :42273631
```

## When to Use the Identity Map Pattern in Java

The Identity Map design pattern is used in Java applications where multiple accesses to the same data occur within a single session or transaction, ensuring efficient object mapping and consistency.

## Identity Map Pattern Java Tutorials

* [Identity Map Pattern (Source Code Examples)](https://www.sourcecodeexamples.net/2018/04/identity-map-pattern.html)

## Real-World Applications of Identity Map Pattern in Java

* ORM (Object-Relational Mapping) frameworks in Java often implement Identity Maps to handle database interactions more efficiently, demonstrating the pattern’s importance in Java design patterns.
* Enterprise applications to maintain consistent data states across different business processes.

## Benefits and Trade-offs of Identity Map Pattern

Benefits:

* Reduces memory usage by ensuring that only one copy of each object resides in memory.
* Prevents inconsistencies during updates, as all parts of the application refer to the same instance.
* Improves performance by avoiding repeated database reads for the same data.

Trade-offs:

* Increases complexity in object management and persistence logic.
* Can lead to stale data if not managed correctly, especially in concurrent environments.

## Related Java Design Patterns

* [Data Mapper](https://java-design-patterns.com/patterns/data-mapper/): Separates persistence logic from domain logic. Identity Map can be used by a Data Mapper to ensure that each object is loaded only once, enhancing performance and data consistency.
* [Unit of Work](https://java-design-patterns.com/patterns/unit-of-work/): Coordinates the actions of multiple objects by keeping track of changes and handling transactional consistency. Identity Map is used within the Unit of Work to track the objects being affected by a transaction.

## References and Credits

* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Java Persistence with Hibernate](https://amzn.to/4aUfyhd)
* [Pro Java EE Spring Patterns: Best Practices and Design Strategies Implementing Java EE Patterns with the Spring Framework](https://amzn.to/49YQN24)
