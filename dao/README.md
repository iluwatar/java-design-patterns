---
title: Data Access Object
category: Structural
language: en
tag:
    - Data access
    - Layered architecture
    - Persistence
---

## Also known as

* Data Access Layer
* DAO

## Intent

The Data Access Object (DAO) design pattern aims to separate the application's business logic from the persistence layer, typically a database or any other storage mechanism. By using DAOs, the application can access and manipulate data without being dependent on the specific database implementation details.

## Explanation

Real world example

> There's a set of customers that need to be persisted to database. Additionally, we need the whole set of CRUD (create/read/update/delete) operations, so we can operate on customers easily.

In plain words

> DAO is an interface we provide over the base persistence mechanism.

Wikipedia says

> In computer software, a data access object (DAO) is a pattern that provides an abstract interface to some type of database or other persistence mechanism.

**Programmatic Example**

Walking through our customers example, here's the basic `Customer` entity.

```java
public class Customer {

    private int id;
    private String firstName;
    private String lastName;

    public Customer(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
```

Here's the `CustomerDao` interface and two different implementations for it. `InMemoryCustomerDao` keeps a simple map of customers in memory while `DBCustomerDao` is the real RDBMS implementation.

```java
public interface CustomerDao {

    Stream<Customer> getAll() throws Exception;

    Optional<Customer> getById(int id) throws Exception;

    boolean add(Customer customer) throws Exception;

    boolean update(Customer customer) throws Exception;

    boolean delete(Customer customer) throws Exception;
}

public class InMemoryCustomerDao implements CustomerDao {

    private final Map<Integer, Customer> idToCustomer = new HashMap<>();

    // implement the interface using the map
}

@Slf4j
public class DbCustomerDao implements CustomerDao {

  private final DataSource
      dataSource;

  public DbCustomerDao(
      DataSource dataSource) {
    this.dataSource =
        dataSource;
  }

  // implement the interface using the data source
}
```

Finally, here's how we use our DAO to manage customers.

```java
    final var dataSource=createDataSource();
        createSchema(dataSource);
final var customerDao=new DbCustomerDao(dataSource);

        addCustomers(customerDao);
        log.info(ALL_CUSTOMERS);
        try(var customerStream=customerDao.getAll()){
        customerStream.forEach((customer)->log.info(customer.toString()));
        }
        log.info("customerDao.getCustomerById(2): "+customerDao.getById(2));
final var customer=new Customer(4,"Dan","Danson");
        customerDao.add(customer);
        log.info(ALL_CUSTOMERS+customerDao.getAll());
        customer.setFirstName("Daniel");
        customer.setLastName("Danielson");
        customerDao.update(customer);
        log.info(ALL_CUSTOMERS);
        try(var customerStream=customerDao.getAll()){
        customerStream.forEach((cust)->log.info(cust.toString()));
        }
        customerDao.delete(customer);
        log.info(ALL_CUSTOMERS+customerDao.getAll());

        deleteSchema(dataSource);
```

The program output:

```java
customerDao.getAllCustomers():
        Customer{id=1,firstName='Adam',lastName='Adamson'}
        Customer{id=2,firstName='Bob',lastName='Bobson'}
        Customer{id=3,firstName='Carl',lastName='Carlson'}
        customerDao.getCustomerById(2):Optional[Customer{id=2,firstName='Bob',lastName='Bobson'}]
        customerDao.getAllCustomers():java.util.stream.ReferencePipeline$Head@7cef4e59
        customerDao.getAllCustomers():
        Customer{id=1,firstName='Adam',lastName='Adamson'}
        Customer{id=2,firstName='Bob',lastName='Bobson'}
        Customer{id=3,firstName='Carl',lastName='Carlson'}
        Customer{id=4,firstName='Daniel',lastName='Danielson'}
        customerDao.getAllCustomers():java.util.stream.ReferencePipeline$Head@2db0f6b2
        customerDao.getAllCustomers():
        Customer{id=1,firstName='Adam',lastName='Adamson'}
        Customer{id=2,firstName='Bob',lastName='Bobson'}
        Customer{id=3,firstName='Carl',lastName='Carlson'}
        customerDao.getCustomerById(2):Optional[Customer{id=2,firstName='Bob',lastName='Bobson'}]
        customerDao.getAllCustomers():java.util.stream.ReferencePipeline$Head@12c8a2c0
        customerDao.getAllCustomers():
        Customer{id=1,firstName='Adam',lastName='Adamson'}
        Customer{id=2,firstName='Bob',lastName='Bobson'}
        Customer{id=3,firstName='Carl',lastName='Carlson'}
        Customer{id=4,firstName='Daniel',lastName='Danielson'}
        customerDao.getAllCustomers():java.util.stream.ReferencePipeline$Head@6ec8211c
```

## Class diagram

![alt text](./etc/dao.png "Data Access Object")

## Applicability

Use the Data Access Object in any of the following situations:

* There is a need to abstract and encapsulate all access to the data source.
* The application needs to support multiple types of databases or storage mechanisms without significant code changes.
* You want to keep the database access clean and simple, and separate from business logic.

## Tutorials

* [The DAO Pattern in Java](https://www.baeldung.com/java-dao-pattern)
* [Data Access Object Pattern](https://www.tutorialspoint.com/design_pattern/data_access_object_pattern.htm)

## Known Uses

* Enterprise applications that require database interaction.
* Applications requiring data access to be adaptable to multiple storage types (relational databases, XML files, flat files, etc.).
* Frameworks providing generic data access functionalities.

## Consequences

Benefits:

* Decoupling: Separates the data access logic from the business logic, enhancing modularity and clarity.
* Reusability: DAOs can be reused across different parts of the application or even in different projects.
* Testability: Simplifies testing by allowing business logic to be tested separately from the data access logic.
* Flexibility: Makes it easier to switch underlying storage mechanisms with minimal impact on the application code.

Trade-offs:

* Layer Complexity: Introduces additional layers to the application, which can increase complexity and development time.
* Overhead: For simple applications, the DAO pattern might introduce more overhead than necessary.
* Learning Curve: Developers might need time to understand and implement the pattern effectively, especially in complex projects.

## Related Patterns

* [Service Layer](https://java-design-patterns.com/patterns/service-layer/): Often used in conjunction with the DAO pattern to define application's boundaries and its set of available operations.
* [Factory](https://java-design-patterns.com/patterns/factory/): Can be used to instantiate DAOs dynamically, providing flexibility in the choice of implementation.
* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Helps in abstracting the creation of DAOs, especially when supporting multiple databases or storage mechanisms.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Might be employed to change the data access strategy at runtime, depending on the context.

## Credits

* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
* [Core J2EE Patterns: Best Practices and Design Strategies](https://amzn.to/49u3r91)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3U5cxEI)
* [Expert One-on-One J2EE Design and Development](https://amzn.to/3vK3pfq)
* [Professional Java Development with the Spring Framework](https://amzn.to/49tANF0)
