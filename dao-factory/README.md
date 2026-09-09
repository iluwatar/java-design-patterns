---
title: "DAO Factory Pattern: Flexible Data Access Layer for Seamless Data Source Switching"
shortTitle: DAO Factory
description: "Learn the Data Access Object Pattern combine with Abstract Factory Pattern in Java with real-world examples, class diagrams, and tutorials. Understand its intent, applicability, benefits, and known uses to enhance your design pattern knowledge."
category: Structural
language: en
tag:
    - Abstraction
    - Data access
    - Layer architecture
    - Persistence
---

## Also known as

* DAO Factory
* Factory for Data Access Object strategy using Abstract Factory


## Intent of Data Access Object Factory Design Pattern

The DAO Factory combines the Data Access Object and Abstract Factory patterns to seperate business logic from data access logic, while increasing flexibility when switching between different data sources.

## Detailed Explanation of Data Access Object Factory Pattern with Real-World Examples

Real-world example

> A real-world analogy for the DAO Factory pattern is a multilingual customer service center. Imagine a bank that serves customers speaking different languages—English, French, and Spanish. When a customer calls, an automated system first detects the customer's preferred language, then routes the call to the appropriate support team that speaks that language. Each team follows the same company policies (standard procedures), but handles interactions in a language-specific way.
> 
> In the same way, the DAO Factory pattern uses a factory to determine the correct set of DAO implementations based on the data source (e.g., MySQL, MongoDB). Each DAO factory returns a group of DAOs tailored to a specific data source, all conforming to the same interfaces. This allows the application to interact with any supported database in a consistent manner, without changing the business logic—just like how the customer service system handles multiple languages while following the same support protocols.

In plain words

> The DAO Factory pattern abstracts the creation of Data Access Objects (DAOs), allowing you to request a specific DAO from a central factory without worrying about its underlying implementation. This makes the code easier to maintain and flexible to change, especially when switching between databases or storage mechanisms.

Wikipedia says

> The Data Access Object (DAO) design pattern is a structural pattern that provides an abstract interface to some type of database or other persistence mechanism. By mapping application calls to the persistence layer, the DAO provides some specific data operations without exposing details of the database. The DAO Factory is an extension of this concept, responsible for generating the required DAO implementations.

Class diagram

![Data Access Object Factory class diagram](./etc/dao-factory.png "Data Access Object Factory class diagram")

## Programmatic Example of Data Access Object Factory in Java

In this example, the persistence object represents a Customer. 

We are considering a flexible storage strategy where the application should be able to work with three different types of data sources: an H2 in-memory relational database (RDBMS), a MongoDB (object-oriented database), and a JSON flat file (flat file storage).

``` java
public enum DataSourceType {
H2,
Mongo,
FlatFile
}
```

First, we define a Customer class that will be persisted in different storage systems. The ID field is generic to maintain compatibility with both relational and object-oriented databases.

``` java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer<T> implements Serializable {
private T id;
private String name;
}
```

Next, we define a CustomerDAO interface that outlines the standard CRUD operations on the Customer model. This interface will have three concrete implementations, each corresponding to a specific data source: H2 in-memory database, MongoDB, and JSON file.

``` java
public interface CustomerDAO<T> {

  void save(Customer<T> customer);
  
  void update(Customer<T> customer);
  
  void delete(T id);
  
  List<Customer<T>> findAll();
  
  Optional<Customer<T>> findById(T id);
}
```

Here is the implementations

``` java
@Slf4j
@RequiredArgsConstructor
public class H2CustomerDAO implements CustomerDAO<Long> {
private final DataSource dataSource;
private final String INSERT_CUSTOMER = "INSERT INTO customer(id, name) VALUES (?, ?)";
private final String UPDATE_CUSTOMER = "UPDATE customer SET name = ? WHERE id = ?";
private final String DELETE_CUSTOMER = "DELETE FROM customer WHERE id = ?";
private final String SELECT_CUSTOMER_BY_ID = "SELECT * FROM customer WHERE id= ?";
private final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customer";
private final String CREATE_SCHEMA =
"CREATE TABLE IF NOT EXISTS customer (id BIGINT PRIMARY KEY, name VARCHAR(255))";
private final String DROP_SCHEMA = "DROP TABLE IF EXISTS customer";

  @Override
  public void save(Customer<Long> customer) {
    // Implement operation save for H2
  }

  @Override
  public void update(Customer<Long> customer) {
    // Implement operation save for H2
  }

  @Override
  public void delete(Long id) {
    // Implement operation delete for H2
  }

  @Override
  public List<Customer<Long>> findAll() {
    // Implement operation find all for H2
  }

  @Override
  public Optional<Customer<Long>> findById(Long id) {
    // Implement operation find by id for H2
  }
}
```

``` java
@Slf4j
@RequiredArgsConstructor
public class MongoCustomerDAO implements CustomerDAO<ObjectId> {
private final MongoCollection<Document> customerCollection;

  // Implement CRUD operation with MongoDB data source
}
```

``` java
@Slf4j
@RequiredArgsConstructor
public class FlatFileCustomerDAO implements CustomerDAO<Long> {
  private final Path filePath;
  private final Gson gson;
  Type customerListType = new TypeToken<List<Customer<Long>>>() {
  }.getType();

  // Implement CRUD operation with Flat file data source
}
```

After that, we create an abstract class DAOFactory that defines two key methods: a static method getDataSource() and an abstract method createCustomerDAO().

- The getDataSource() method is a factory selector—it returns a concrete DAOFactory instance based on the type of data source requested.

- Each subclass of DAOFactory will implement the createCustomerDAO() method to provide the corresponding CustomerDAO implementation.

``` java
public abstract class DAOFactory {
  public static DAOFactory getDataSource(DataSourceType dataSourceType) {
    return switch (dataSourceType) {
      case H2 -> new H2DataSourceFactory();
      case Mongo -> new MongoDataSourceFactory();
      case FlatFile -> new FlatFileDataSourceFactory();
    };
  }

  public abstract CustomerDAO createCustomerDAO();
}
```

We then implement three specific factory classes:

H2DataSourceFactory for H2 in-memory RDBMS
``` java
public class H2DataSourceFactory extends DAOFactory {
  private final String DB_URL = "jdbc:h2:~/test";
  private final String USER = "sa";
  private final String PASS = "";

  @Override
  public CustomerDAO createCustomerDAO() {
    return new H2CustomerDAO(createDataSource());
  }

  private DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setURL(DB_URL);
    dataSource.setUser(USER);
    dataSource.setPassword(PASS);
    return dataSource;
  }
}
```

MongoDataSourceFactory for MongoDB
``` java
public class MongoDataSourceFactory extends DAOFactory {
  private final String CONN_STR = "mongodb://localhost:27017/";
  private final String DB_NAME = "dao_factory";
  private final String COLLECTION_NAME = "customer";

  @Override
  public CustomerDAO createCustomerDAO() {
    try {
      MongoClient mongoClient = MongoClients.create(CONN_STR);
      MongoDatabase database = mongoClient.getDatabase(DB_NAME);
      MongoCollection<Document> customerCollection = database.getCollection(COLLECTION_NAME);
      return new MongoCustomerDAO(customerCollection);
    } catch (RuntimeException e) {
      throw new RuntimeException("Error: " + e);
    }
  }
}
```

FlatFileDataSourceFactory for flat file storage using JSON
``` java
public class FlatFileDataSourceFactory extends DAOFactory {
  private final String FILE_PATH = System.getProperty("user.home") + "/Desktop/customer.json";
  @Override
  public CustomerDAO createCustomerDAO() {
    Path filePath = Paths.get(FILE_PATH);
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .create();
    return new FlatFileCustomerDAO(filePath, gson);
  }
}
```

Finally, in the main function of client code, we will demonstrate CRUD operations on the Customer using three data source type.
``` java
    // Perform CRUD H2 Database
    LOGGER.debug("H2 - Create customer");
    performCreateCustomer(customerDAO,
        List.of(customerInmemory1, customerInmemory2, customerInmemory3));
    LOGGER.debug("H2 - Update customer");
    performUpdateCustomer(customerDAO, customerUpdateInmemory);
    LOGGER.debug("H2 - Delete customer");
    performDeleteCustomer(customerDAO, 3L);
    deleteSchema(customerDAO);

    // Perform CRUD MongoDb
    daoFactory = DAOFactory.getDataSource(DataSourceType.Mongo);
    customerDAO = daoFactory.createCustomerDAO();
    LOGGER.debug("Mongo - Create customer");
    performCreateCustomer(customerDAO, List.of(customer4, customer5));
    LOGGER.debug("Mongo - Update customer");
    performUpdateCustomer(customerDAO, customerUpdateMongo);
    LOGGER.debug("Mongo - Delete customer");
    performDeleteCustomer(customerDAO, idCustomerMongo2);
    deleteSchema(customerDAO);

    // Perform CRUD Flat file
    daoFactory = DAOFactory.getDataSource(DataSourceType.FlatFile);
    customerDAO = daoFactory.createCustomerDAO();
    LOGGER.debug("Flat file - Create customer");
    performCreateCustomer(customerDAO,
        List.of(customerFlatFile1, customerFlatFile2, customerFlatFile3));
    LOGGER.debug("Flat file - Update customer");
    performUpdateCustomer(customerDAO, customerUpdateFlatFile);
    LOGGER.debug("Flat file - Delete customer");
    performDeleteCustomer(customerDAO, 3L);
    deleteSchema(customerDAO);
```

The program output
``` java
17:17:24.368 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - H2 - Create customer
17:17:24.514 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=1, name=Green)
17:17:24.514 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=2, name=Red)
17:17:24.514 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=3, name=Blue)
17:17:24.514 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - H2 - Update customer
17:17:24.573 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=1, name=Yellow)
17:17:24.573 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=2, name=Red)
17:17:24.573 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=3, name=Blue)
17:17:24.573 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - H2 - Delete customer
17:17:24.632 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=1, name=Yellow)
17:17:24.632 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=2, name=Red)
17:17:24.747 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Mongo - Create customer
17:17:24.834 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=68173eb4c840286dbc2bc5c1, name=Masca)
17:17:24.834 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=68173eb4c840286dbc2bc5c2, name=Elliot)
17:17:24.834 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Mongo - Update customer
17:17:24.845 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=68173eb4c840286dbc2bc5c1, name=Masca)
17:17:24.845 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=68173eb4c840286dbc2bc5c2, name=Henry)
17:17:24.845 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Mongo - Delete customer
17:17:24.850 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=68173eb4c840286dbc2bc5c1, name=Masca)
17:17:24.876 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Flat file - Create customer
17:17:24.895 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=1, name=Duc)
17:17:24.895 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=2, name=Quang)
17:17:24.895 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=3, name=Nhat)
17:17:24.895 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Flat file - Update customer
17:17:24.897 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=1, name=Thanh)
17:17:24.897 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=2, name=Quang)
17:17:24.897 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=3, name=Nhat)
17:17:24.897 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Flat file - Delete customer
17:17:24.898 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=1, name=Thanh)
17:17:24.898 [main] DEBUG c.i.d.App com.iluwatar.daofactory.App - Customer(id=2, name=Quang)
```
## When to Use the Data Access Object Factory Pattern in Java

Use the DAO Factory Pattern when:

* The application needs to support multiple types of storage (RDBMS, NoSQL, file system, etc.) with minimal changes to business logic.
* You want to abstract and isolate persistence logic from the core application logic.
* You aim to make your data access layer pluggable and easy to extend with new storage technologies.
* You want to enable easier unit testing and dependency injection by providing mock implementations of DAOs.
* Runtime configuration (e.g., via environment variables or application settings) determines which data source to use.

## Data Access Object Factory Pattern Java Tutorials

* [Core J2EE Patterns - Data Access Object (Oracle)](https://www.oracle.com/java/technologies/dataaccessobject.html)
* [DAO Factories: Java Design Patterns (Youtube)](https://www.youtube.com/watch?v=5HGe9s9qM-o)
* [Java Design Patterns and Architecture (CaveofProgramming)](https://caveofprogramming.teachable.com/courses/2084/lectures/39549)

## Real-World Applications of Data Access Object Factory Pattern in Java

* Enterprise Java Applications: Where switching between test, dev, and production databases is common (e.g., MySQL ↔ MongoDB ↔ In-Memory).
* Spring Data JPA & Repository Abstraction: Though Spring provides its own abstraction, the concept is similar to DAO factory for modular and pluggable persistence.
* Microservices with Varying Storage Backends: Different microservices might store data in SQL, NoSQL, or even flat files; using a DAO Factory per service ensures consistency.
* Data Integration Tools: Tools that support importing/exporting from various formats (CSV, JSON, databases) often use DAO factories behind the scenes.
* Framework-Level Implementations: Custom internal frameworks where persistence layers need to support multiple database types.

## Benefits and Trade-offs of Data Access Object Factory Pattern

Benefits:

* Abstraction of Data Source Logic: Client code interacts only with DAO interfaces, completely decoupled from how and where the data is stored.
* Flexibility in Persistence Strategy: Easily switch between databases (e.g., H2, MongoDB, flat files) by changing the factory configuration.
* Improved Maintainability: Storage logic for each data source is encapsulated within its own DAO implementation and factory, making it easier to update or extend.
* Code Reusability: Common data access logic (e.g., CRUD operations) can be reused across different implementations and projects.
* Testability: DAOs and factories can be mocked or stubbed easily, which supports unit testing and dependency injection.

Trade-offs:
* Increased Complexity: Introducing abstract DAOs and multiple factory classes adds structural complexity to the codebase.
* Boilerplate Code: Requires defining many interfaces and implementations, even for simple data access needs.
* Less Transparent Behavior: Since clients access DAOs indirectly via factories, understanding the concrete data source behavior may require deeper inspection.

## Related Java Design Patterns

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): DAO Factory is a concrete application of the Factory Pattern, used to create DAO objects in a flexible way.
* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): When supporting multiple data sources (e.g., MySQLDAO, OracleDAO), DAO Factory can act as an Abstract Factory.
* [Data Access Object (DAO)](https://java-design-patterns.com/patterns/data-access-object/): The core pattern managed by DAO Factory, it separates data access logic from business logic.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): DAO Factory is often implemented as a Singleton to ensure only one instance manages DAO creation.
* [Service Locator](https://java-design-patterns.com/patterns/service-locator/): Can be used alongside DAO Factory to retrieve DAO services efficiently.
* [Dependency Injection](https://java-design-patterns.com/patterns/dependency-injection/): In frameworks like Spring, DAOs are typically injected into the service layer instead of being retrieved from a factory.


## References and Credits

* [DAO Factory - J2EE Design Patterns Book](https://www.oreilly.com/library/view/j2ee-design-patterns/0596004273/re15.html)
* [DAO Factory patterns with Hibernate](http://www.giuseppeurso.eu/en/dao-factory-patterns-with-hibernate/)
* [Design Patterns - Java Means DURGA SOFT](https://www.scribd.com/document/407219980/2-DAO-Factory-Design-Pattern)
* [Generic DAO pattern - Hibernate](https://in.relation.to/2005/09/09/generic-dao-pattern-with-jdk-50/)
  