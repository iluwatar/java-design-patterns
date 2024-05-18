---
title: Repository
category: Data access
language: en
tag:
    - Abstraction
    - Data access
    - Decoupling
    - Persistence
---

## Intent

To provide a central location for data access logic, abstracting the details of data storage and retrieval from the rest of the application.

## Explanation

Real-world example

> Imagine a library system where a librarian acts as the repository. Instead of each library patron searching through the entire library for a book (the data), they go to the librarian (the repository) who knows exactly where each book is located, regardless of whether it's on a shelf, in the storeroom, or borrowed by someone else. The librarian abstracts the complexities of book storage, allowing patrons to request books without needing to understand the storage system. This setup simplifies the process for patrons (clients) and centralizes the management of books (data access logic).

In plain words

> The Repository pattern provides a central place for handling all data access logic, abstracting the complexities of data storage and retrieval from the rest of the application.

[Microsoft documentation](https://docs.microsoft.com/en-us/dotnet/architecture/microservices/microservice-ddd-cqrs-patterns/infrastructure-persistence-layer-design) says

> Repositories are classes or components that encapsulate the logic required to access data sources. They centralize common data access functionality, providing better maintainability and decoupling the infrastructure or technology used to access databases from the domain model layer.

**Programmatic Example**

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

    /**
     * Constructor.
     */
    public Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

}
```

We are using Spring Data to create the `PersonRepository` so it becomes really simple.

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
var peter = new Person("Peter", "Sagan", 17);
var nasta = new Person("Nasta", "Kuzminova", 25);
var john = new Person("John", "lawrence", 35);
var terry = new Person("Terry", "Law", 36);

repository.save(peter);
repository.save(nasta);
repository.save(john);
repository.save(terry);

LOGGER.info("Count Person records: {}",repository.count());

var persons = (List<Person>) repository.findAll();
persons.stream().map(Person::toString).forEach(LOGGER::info);

nasta.setName("Barbora");
nasta.setSurname("Spotakova");
repository.save(nasta);
repository.findById(2L).ifPresent(p ->LOGGER.info("Find by id 2: {}",p));

repository.deleteById(2L);
LOGGER.info("Count Person records: {}",repository.count());

repository.findOne(new PersonSpecifications.NameEqualSpec("John")).ifPresent(p ->LOGGER.info("Find by John is {}",p));

persons = repository.findAll(new PersonSpecifications.AgeBetweenSpec(20, 40));
LOGGER.info("Find Person with age between 20,40: ");

persons.stream().map(Person::toString).forEach(LOGGER::info);

repository.deleteAll();
```

Program output:

```
Count Person records: 4
Person [id=1, name=Peter, surname=Sagan, age=17]
Person [id=2, name=Nasta, surname=Kuzminova, age=25]
Person [id=3, name=John, surname=lawrence, age=35]
Person [id=4, name=Terry, surname=Law, age=36]
Find by id 2: Person [id=2, name=Barbora, surname=Spotakova, age=25]
Count Person records: 3
Find by John is Person [id=3, name=John, surname=lawrence, age=35]
Find Person with age between 20,40: 
Person [id=3, name=John, surname=lawrence, age=35]
Person [id=4, name=Terry, surname=Law, age=36]
```

## Class diagram

![Repository](./etc/repository.png "Repository")

## Applicability

* Use when you want to decouple the business logic and data access layers of your application.
* Suitable for scenarios where multiple data sources might be used and the business logic should remain unaware of the data source specifics.
* Ideal for testing purposes as it allows the use of mock repositories.

## Known Uses

* Spring Data JPA: Provides a repository abstraction over JPA implementations.
* Hibernate: Often used with DAOs that act as repositories for accessing and managing data entities.
* Java EE applications frequently utilize repository patterns to separate business logic from data access code.

## Consequences

Benefits:

* Improves code maintainability and readability by centralizing data access logic.
* Enhances testability by allowing mock implementations of repositories.
* Promotes loose coupling between business logic and data access layers.

Trade-offs:

* Introduces additional layers of abstraction which might add complexity.
* Potential performance overhead due to the abstraction layer.

## Related Patterns

* [Data Mapper](https://java-design-patterns.com/patterns/data-mapper/): While Repository handles data access, Data Mapper is responsible for transferring data between objects and a database, maintaining the data integrity.
* [Unit of Work](https://java-design-patterns.com/patterns/unit-of-work/): Often used alongside Repository to manage transactions and track changes to the data.

## Credits

* [Domain-Driven Design: Tackling Complexity in the Heart of Software](https://amzn.to/3wlDrze)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=d9f7d37b032ca6e96253562d075fcc4a)
* [Don’t use DAO, use Repository](http://thinkinginobjects.com/2012/08/26/dont-use-dao-use-repository/)
* [Advanced Spring Data JPA - Specifications and Querydsl](https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/)
* [Repository Pattern Benefits and Spring Implementation](https://stackoverflow.com/questions/40068965/repository-pattern-benefits-and-spring-implementation)
* [Design patterns that I often avoid: Repository pattern](https://www.infoworld.com/article/3117713/design-patterns-that-i-often-avoid-repository-pattern.html)
