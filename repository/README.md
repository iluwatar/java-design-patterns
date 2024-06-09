---
title: "Repository Pattern in Java: Simplifying Data Access with Abstracted Persistence"
shortTitle: Repository
description: "Learn how the Repository design pattern in Java enhances data access and abstraction, simplifying application architecture while boosting maintainability and decoupling. Ideal for Java developers seeking streamlined data management solutions."
category: Data access
language: en
tag:
  - Abstraction
  - Data access
  - Decoupling
  - Persistence
---

## Intent of Repository Design Pattern

The Repository design pattern acts as a central hub for managing all Java data access logic, abstracting the details of data storage and retrieval from the rest of the application.

## Detailed Explanation of Repository Pattern with Real-World Examples

Real-world example

> Imagine a library system where a librarian acts as the repository. Instead of each library patron searching through the entire library for a book (the data), they go to the librarian (the repository) who knows exactly where each book is located, regardless of whether it's on a shelf, in the storeroom, or borrowed by someone else. The librarian abstracts the complexities of book storage, allowing patrons to request books without needing to understand the storage system. This setup simplifies the process for patrons (clients) and centralizes the management of books (data access logic).

In plain words

> The Repository pattern provides a central place for handling all data access logic, abstracting the complexities of data storage and retrieval from the rest of the application.

[Microsoft documentation](https://docs.microsoft.com/en-us/dotnet/architecture/microservices/microservice-ddd-cqrs-patterns/infrastructure-persistence-layer-design) says

> Repositories are classes or components that encapsulate the logic required to access data sources. They centralize common data access functionality, providing better maintainability and decoupling the infrastructure or technology used to access databases from the domain model layer.

## Programmatic Example of Repository Pattern in Java

Let's first look at the person entity that we need to persist.

```java

@ToString
@EqualsAndHashCode
@Setter
@Getter
@Entity
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private int age;

    public Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }
}
```

We are using Spring Data to create the `PersonRepository`, so it becomes really simple.

```java
@Repository
public interface PersonRepository extends CrudRepository<Person, Long>, JpaSpecificationExecutor<Person> {
    Person findByName(String name);
}
```

Additionally, we define a helper class `PersonSpecifications` for specification queries.

```java
public class PersonSpecifications {

    public static class AgeBetweenSpec implements Specification<Person> {

        private final int from;
        private final int to;

        public AgeBetweenSpec(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            return cb.between(root.get("age"), from, to);
        }
    }

    public static class NameEqualSpec implements Specification<Person> {

        public String name;

        public NameEqualSpec(String name) {
            this.name = name;
        }

        public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            return cb.equal(root.get("name"), this.name);
        }
    }
}
```

And here's the repository example in action.

```java
  public static void main(String[] args) {

    var context = new ClassPathXmlApplicationContext("applicationContext.xml");
    var repository = context.getBean(PersonRepository.class);

    var peter = new Person("Peter", "Sagan", 17);
    var nasta = new Person("Nasta", "Kuzminova", 25);
    var john = new Person("John", "lawrence", 35);
    var terry = new Person("Terry", "Law", 36);

    // Add new Person records
    repository.save(peter);
    repository.save(nasta);
    repository.save(john);
    repository.save(terry);

    // Count Person records
    LOGGER.info("Count Person records: {}", repository.count());

    // Print all records
    var persons = (List<Person>) repository.findAll();
    persons.stream().map(Person::toString).forEach(LOGGER::info);

    // Update Person
    nasta.setName("Barbora");
    nasta.setSurname("Spotakova");
    repository.save(nasta);

    repository.findById(2L).ifPresent(p -> LOGGER.info("Find by id 2: {}", p));

    // Remove record from Person
    repository.deleteById(2L);

    // count records
    LOGGER.info("Count Person records: {}", repository.count());

    // find by name
    repository
            .findOne(new PersonSpecifications.NameEqualSpec("John"))
            .ifPresent(p -> LOGGER.info("Find by John is {}", p));

    // find by age
    persons = repository.findAll(new PersonSpecifications.AgeBetweenSpec(20, 40));

    LOGGER.info("Find Person with age between 20,40: ");
    persons.stream().map(Person::toString).forEach(LOGGER::info);

    repository.deleteAll();

    context.close();
}
```

Program output:

```
INFO  [2024-05-27 07:00:32,847] com.iluwatar.repository.App: Count Person records: 4
INFO  [2024-05-27 07:00:32,859] com.iluwatar.repository.App: Person(id=1, name=Peter, surname=Sagan, age=17)
INFO  [2024-05-27 07:00:32,859] com.iluwatar.repository.App: Person(id=2, name=Nasta, surname=Kuzminova, age=25)
INFO  [2024-05-27 07:00:32,859] com.iluwatar.repository.App: Person(id=3, name=John, surname=lawrence, age=35)
INFO  [2024-05-27 07:00:32,859] com.iluwatar.repository.App: Person(id=4, name=Terry, surname=Law, age=36)
INFO  [2024-05-27 07:00:32,869] com.iluwatar.repository.App: Find by id 2: Person(id=2, name=Barbora, surname=Spotakova, age=25)
INFO  [2024-05-27 07:00:32,873] com.iluwatar.repository.App: Count Person records: 3
INFO  [2024-05-27 07:00:32,878] com.iluwatar.repository.App: Find by John is Person(id=3, name=John, surname=lawrence, age=35)
INFO  [2024-05-27 07:00:32,881] com.iluwatar.repository.App: Find Person with age between 20,40: 
INFO  [2024-05-27 07:00:32,881] com.iluwatar.repository.App: Person(id=3, name=John, surname=lawrence, age=35)
INFO  [2024-05-27 07:00:32,881] com.iluwatar.repository.App: Person(id=4, name=Terry, surname=Law, age=36)
```

## When to Use the Repository Pattern in Java

* Apply the Repository pattern when aiming to decouple business logic from data access layers in Java applications, ensuring more flexible and maintainable code.
* Suitable for scenarios where multiple data sources might be used and the business logic should remain unaware of the data source specifics.
* Ideal for testing purposes as it allows the use of mock repositories.

## Repository Pattern Java Tutorials

* [Don’t use DAO, use Repository (Thinking in Objects)](http://thinkinginobjects.com/2012/08/26/dont-use-dao-use-repository/)
* [Advanced Spring Data JPA - Specifications and Querydsl (Spring)](https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/)
* [Repository Pattern Benefits and Spring Implementation (Stack Overflow)](https://stackoverflow.com/questions/40068965/repository-pattern-benefits-and-spring-implementation)
* [Design patterns that I often avoid: Repository pattern (InfoWorld)](https://www.infoworld.com/article/3117713/design-patterns-that-i-often-avoid-repository-pattern.html)

## Real-World Applications of Repository Pattern in Java

* Spring Data JPA exemplifies the Repository pattern by providing a robust repository abstraction layer over JPA implementations, tailored for Java.
* Hibernate: Often used with DAOs that act as repositories for accessing and managing data entities.
* Java EE applications frequently utilize repository patterns to separate business logic from data access code.

## Benefits and Trade-offs of Repository Pattern

Benefits:

* Improves code maintainability and readability by centralizing data access logic.
* Enhances testability by allowing mock implementations of repositories.
* Promotes loose coupling between business logic and data access layers.

Trade-offs:

* Introduces additional layers of abstraction which might add complexity.
* Potential performance overhead due to the abstraction layer.

## Related Java Design Patterns

* [Data Mapper](https://java-design-patterns.com/patterns/data-mapper/): While Repository handles data access, Data Mapper is responsible for transferring data between objects and a database, maintaining the data integrity.
* [Unit of Work](https://java-design-patterns.com/patterns/unit-of-work/): Often used alongside Repository to manage transactions and track changes to the data.

## References and Credits

* [Domain-Driven Design: Tackling Complexity in the Heart of Software](https://amzn.to/3wlDrze)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
