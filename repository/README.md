---
layout: pattern
title: Repository
folder: repository
permalink: /patterns/repository/
categories: Architectural
tags:
 - Data access
---

## Intent
Repository layer is added between the domain and data mapping layers to isolate domain objects from details of the 
database access code and to minimize scattering and duplication of query code. The Repository pattern is especially 
useful in systems where number of domain classes is large or heavy querying is utilized.

## Explanation
Real world example

> Let's say we need a persistent data store for persons. Adding new persons and searching for them according to different criteria must be easy. 

In plain words

> Repository architectural pattern creates a uniform layer of data repositories that can be used for CRUD operations.

[Microsoft documentation](https://docs.microsoft.com/en-us/dotnet/architecture/microservices/microservice-ddd-cqrs-patterns/infrastructure-persistence-layer-design) says

> Repositories are classes or components that encapsulate the logic required to access data sources. They centralize common data access functionality, providing better maintainability and decoupling the infrastructure or technology used to access databases from the domain model layer.

**Programmatic Example**

Let's first look at the person class that we need to persist. 

```java
@Entity
public class Person {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String surname;

  private int age;

  public Person() {
  }

  /**
   * Constructor.
   */
  public Person(String name, String surname, int age) {
    this.name = name;
    this.surname = surname;
    this.age = age;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "Person [id=" + id + ", name=" + name + ", surname=" + surname + ", age=" + age + "]";
  }

  @Override
  public int hashCode() {
    final var prime = 31;
    var result = 1;
    result = prime * result + age;
    result = prime * result + (id == null ? 0 : id.hashCode());
    result = prime * result + (name == null ? 0 : name.hashCode());
    result = prime * result + (surname == null ? 0 : surname.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    var other = (Person) obj;
    if (age != other.age) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (surname == null) {
      return other.surname == null;
    }
    return surname.equals(other.surname);
  }
}
```

We are using Spring Data to create the repository so it becomes really simple.

```java
@Repository
public interface PersonRepository
    extends CrudRepository<Person, Long>, JpaSpecificationExecutor<Person> {

  Person findByName(String name);
}
```

Additionally we define a helper class for specification queries.

```java
public class PersonSpecifications {

  public static class AgeBetweenSpec implements Specification<Person> {

    private int from;

    private int to;

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

And here's the repository in action.

```java
    var peter = new Person("Peter", "Sagan", 17);
    var nasta = new Person("Nasta", "Kuzminova", 25);
    var john = new Person("John", "lawrence", 35);
    var terry = new Person("Terry", "Law", 36);

    repository.save(peter);
    repository.save(nasta);
    repository.save(john);
    repository.save(terry);

    LOGGER.info("Count Person records: {}", repository.count());

    var persons = (List<Person>) repository.findAll();
    persons.stream().map(Person::toString).forEach(LOGGER::info);

    nasta.setName("Barbora");
    nasta.setSurname("Spotakova");
    repository.save(nasta);

    repository.findById(2L).ifPresent(p -> LOGGER.info("Find by id 2: {}", p));
    repository.deleteById(2L);

    LOGGER.info("Count Person records: {}", repository.count());

    repository
        .findOne(new PersonSpecifications.NameEqualSpec("John"))
        .ifPresent(p -> LOGGER.info("Find by John is {}", p));

    persons = repository.findAll(new PersonSpecifications.AgeBetweenSpec(20, 40));

    LOGGER.info("Find Person with age between 20,40: ");
    persons.stream().map(Person::toString).forEach(LOGGER::info);

    repository.deleteAll();
    
    // Count Person records: 4
    // Person [id=1, name=Peter, surname=Sagan, age=17]
    // Person [id=2, name=Nasta, surname=Kuzminova, age=25]
    // Person [id=3, name=John, surname=lawrence, age=35]
    // Person [id=4, name=Terry, surname=Law, age=36]
    // Find by id 2: Person [id=2, name=Barbora, surname=Spotakova, age=25]
    // Count Person records: 3
    // Find by John is Person [id=3, name=John, surname=lawrence, age=35]
    // Find Person with age between 20,40: 
    // Person [id=3, name=John, surname=lawrence, age=35]
    // Person [id=4, name=Terry, surname=Law, age=36]
```

## Class diagram
![alt text](./etc/repository.png "Repository")

## Applicability
Use the Repository pattern when

* The number of domain objects is large
* You want to avoid duplication of query code
* You want to keep the database querying code in single place
* You have multiple data sources

## Real world examples

* [Spring Data](http://projects.spring.io/spring-data/)

## Credits

* [Don’t use DAO, use Repository](http://thinkinginobjects.com/2012/08/26/dont-use-dao-use-repository/)
* [Advanced Spring Data JPA - Specifications and Querydsl](https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/)
* [Repository Pattern Benefits and Spring Implementation](https://stackoverflow.com/questions/40068965/repository-pattern-benefits-and-spring-implementation)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=d9f7d37b032ca6e96253562d075fcc4a)
* [Design patterns that I often avoid: Repository pattern](https://www.infoworld.com/article/3117713/design-patterns-that-i-often-avoid-repository-pattern.html)
