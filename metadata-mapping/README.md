---
title: "Metadata Mapping Pattern in Java: Bridging Objects and Data Stores Seamlessly"
shortTitle: Metadata Mapping
description: "Explore the Metadata Mapping Design Pattern for managing the mapping between database records and objects in Java applications. Learn implementation with Hibernate, use cases, benefits, and best practices."
category: Data access
language: en
tag:
  - Decoupling
  - Enterprise patterns
  - Object mapping
  - Persistence
---

## Intent of Metadata Mapping Design Pattern

Metadata Mapping Design Pattern is designed to manage the mapping between database records and Java objects in a way that keeps the database schema and object model decoupled and manageable.

## Detailed Explanation of Metadata Mapping Pattern with Real-World Examples

Real-world example

> An analogous real-world example of the Metadata Mapping design pattern can be seen in online retail systems. In such systems, products often have varying attributes depending on their category. For instance, electronics might have attributes like battery life and screen size, while clothing might have attributes like size and fabric type. Using Metadata Mapping, the system can dynamically map these varying attributes to the product objects without modifying the underlying class structure. This flexibility allows for easy updates and management of product attributes as new categories and attributes are introduced, ensuring that the system can evolve with the changing product landscape.

In plain words

> Metadata Mapping specifies the mapping between classes and tables so that we could treat a table of any database like a Java class.

Wikipedia says

> Create a "virtual [object database](https://en.wikipedia.org/wiki/Object_database)" that can be used from within the programming language.

## Programmatic Example of Metadata Mapping Pattern in Java

Hibernate ORM Tool uses Metadata Mapping Pattern to specify the mapping between classes and tables either using XML or annotations in code.

We give an example about visiting the information of `user_account` table in `h2` database. Firstly, we create `user_account` table with `h2`:

```java
@Slf4j
public class DatabaseUtil {
  private static final String DB_URL = "jdbc:h2:mem:metamapping";
  private static final String CREATE_SCHEMA_SQL = "DROP TABLE IF EXISTS `user_account`;"
      + "CREATE TABLE `user_account` (\n"
      + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
      + "  `username` varchar(255) NOT NULL,\n"
      + "  `password` varchar(255) NOT NULL,\n"
      + "  PRIMARY KEY (`id`)\n"
      + ");";
    
  static {
    LOGGER.info("create h2 database");
    var source = new JdbcDataSource();
    source.setURL(DB_URL);
    try (var statement = source.getConnection().createStatement()) {
      statement.execute(CREATE_SCHEMA_SQL);
    } catch (SQLException e) {
      LOGGER.error("unable to create h2 data source", e);
    }
  }
}
```

Correspondingly, here's the basic `User` entity.

```java
@Setter
@Getter
@ToString
public class User {
  private Integer id;
  private String username;
  private String password;
    
  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
```

Then we write a `xml` file to show the mapping between the table and the object:

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-mapping PUBLIC
    "-//Hibernate/Hibernate Mapping DTD//EN"
    "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">

<hibernate-mapping>
  <class name="com.iluwatar.metamapping.model.User" table="user_account">
    <id name="id" type="java.lang.Integer" column="id">
      <generator class="native"/>
    </id>
    <property name="username" column="username" type="java.lang.String"/>
    <property name="password" column="password" type="java.lang.String"/>
  </class>
</hibernate-mapping>
```

We use `Hibernate` to resolve the mapping and connect to our database, here's its configuration:

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
    "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
    "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
  <session-factory>
    <!-- JDBC Database connection settings -->
    <property name="connection.url">jdbc:h2:mem:metamapping</property>
    <property name="connection.driver_class">org.h2.Driver</property>
    <!-- JDBC connection pool settings ... using built-in test pool -->
    <property name="connection.pool_size">1</property>
    <!-- Select our SQL dialect -->
    <property name="dialect">org.hibernate.dialect.H2Dialect</property>
    <!-- Echo the SQL to stdout -->
    <property name="show_sql">false</property>
    <!-- Drop and re-create the database schema on startup -->
    <property name="hbm2ddl.auto">create-drop</property>
    <mapping resource="com/iluwatar/metamapping/model/User.hbm.xml" />
  </session-factory>
</hibernate-configuration>
```

Then we can get access to the table just like an object with `Hibernate`, here's some CRUDs:

```java
@Slf4j
public class UserService {
  private static final SessionFactory factory = HibernateUtil.getSessionFactory();

  public List<User> listUser() {
    LOGGER.info("list all users.");
    List<User> users = new ArrayList<>();
    try (var session = factory.openSession()) {
      var tx = session.beginTransaction();
      List<User> userIter = session.createQuery("FROM User").list();
      for (var iterator = userIter.iterator(); iterator.hasNext();) {
        users.add(iterator.next());
      }
      tx.commit();
    } catch (HibernateException e) {
      LOGGER.debug("fail to get users", e);
    }
    return users;
  }
  
  // other CRUDs ->
  // ...
    
  public void close() {
    HibernateUtil.shutdown();
  }
}
```

Here is our `App` class with `main` function for running the example.

```java
@Slf4j
public class App {

  public static void main(String[] args) {
    // get service
    var userService = new UserService();
    // use create service to add users
    for (var user : generateSampleUsers()) {
      var id = userService.createUser(user);
      LOGGER.info("Add user" + user + "at" + id + ".");
    }
    // use list service to get users
    var users = userService.listUser();
    LOGGER.info(String.valueOf(users));
    // use get service to get a user
    var user = userService.getUser(1);
    LOGGER.info(String.valueOf(user));
    // change password of user 1
    user.setPassword("new123");
    // use update service to update user 1
    userService.updateUser(1, user);
    // use delete service to delete user 2
    userService.deleteUser(2);
    // close service
    userService.close();
  }

  public static List<User> generateSampleUsers() {
    final var user1 = new User("ZhangSan", "zhs123");
    final var user2 = new User("LiSi", "ls123");
    final var user3 = new User("WangWu", "ww123");
    return List.of(user1, user2, user3);
  }
}
```

Console output:

```
14:44:17.792 [main] INFO  org.hibernate.Version - HHH000412: Hibernate ORM core version 5.6.12.Final
14:44:17.977 [main] INFO  o.h.annotations.common.Version - HCANN000001: Hibernate Commons Annotations {5.1.2.Final}
14:44:18.216 [main] WARN  o.hibernate.orm.connections.pooling - HHH10001002: Using Hibernate built-in connection pool (not for production use!)
14:44:18.217 [main] INFO  o.hibernate.orm.connections.pooling - HHH10001005: using driver [org.h2.Driver] at URL [jdbc:h2:mem:metamapping]
14:44:18.217 [main] INFO  o.hibernate.orm.connections.pooling - HHH10001001: Connection properties: {}
14:44:18.217 [main] INFO  o.hibernate.orm.connections.pooling - HHH10001003: Autocommit mode: false
14:44:18.219 [main] INFO  o.h.e.j.c.i.DriverManagerConnectionProviderImpl - HHH000115: Hibernate connection pool size: 1 (min=1)
14:44:18.276 [main] INFO  org.hibernate.dialect.Dialect - HHH000400: Using dialect: org.hibernate.dialect.H2Dialect
14:44:18.463 [main] INFO  o.hibernate.orm.connections.access - HHH10001501: Connection obtained from JdbcConnectionAccess [org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess@73a8e994] for (non-JTA) DDL execution was not in auto-commit mode; the Connection 'local transaction' will be committed and the Connection will be set into auto-commit mode.
14:44:18.465 [main] INFO  o.hibernate.orm.connections.access - HHH10001501: Connection obtained from JdbcConnectionAccess [org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess@7affc159] for (non-JTA) DDL execution was not in auto-commit mode; the Connection 'local transaction' will be committed and the Connection will be set into auto-commit mode.
14:44:18.470 [main] INFO  o.h.e.t.j.p.i.JtaPlatformInitiator - HHH000490: Using JtaPlatform implementation: [org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform]
14:44:18.473 [main] INFO  c.i.metamapping.service.UserService - create user: ZhangSan
14:44:18.508 [main] INFO  c.i.metamapping.service.UserService - create user ZhangSan at 1
14:44:18.508 [main] INFO  com.iluwatar.metamapping.App - Add userUser(id=1, username=ZhangSan, password=zhs123)at1.
14:44:18.508 [main] INFO  c.i.metamapping.service.UserService - create user: LiSi
14:44:18.509 [main] INFO  c.i.metamapping.service.UserService - create user LiSi at 2
14:44:18.509 [main] INFO  com.iluwatar.metamapping.App - Add userUser(id=2, username=LiSi, password=ls123)at2.
14:44:18.509 [main] INFO  c.i.metamapping.service.UserService - create user: WangWu
14:44:18.512 [main] INFO  c.i.metamapping.service.UserService - create user WangWu at 3
14:44:18.512 [main] INFO  com.iluwatar.metamapping.App - Add userUser(id=3, username=WangWu, password=ww123)at3.
14:44:18.512 [main] INFO  c.i.metamapping.service.UserService - list all users.
14:44:18.542 [main] INFO  com.iluwatar.metamapping.App - [User(id=1, username=ZhangSan, password=zhs123), User(id=2, username=LiSi, password=ls123), User(id=3, username=WangWu, password=ww123)]
14:44:18.542 [main] INFO  c.i.metamapping.service.UserService - get user at: 1
14:44:18.545 [main] INFO  com.iluwatar.metamapping.App - User(id=1, username=ZhangSan, password=zhs123)
14:44:18.545 [main] INFO  c.i.metamapping.service.UserService - update user at 1
14:44:18.548 [main] INFO  c.i.metamapping.service.UserService - delete user at: 2
14:44:18.550 [main] INFO  o.h.t.s.i.SchemaDropperImpl$DelayedDropActionImpl - HHH000477: Starting delayed evictData of schema as part of SessionFactory shut-down'
14:44:18.550 [main] INFO  o.hibernate.orm.connections.access - HHH10001501: Connection obtained from JdbcConnectionAccess [org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator$ConnectionProviderJdbcConnectionAccess@7b5cc918] for (non-JTA) DDL execution was not in auto-commit mode; the Connection 'local transaction' will be committed and the Connection will be set into auto-commit mode.
14:44:18.551 [main] INFO  o.hibernate.orm.connections.pooling - HHH10001008: Cleaning up connection pool [jdbc:h2:mem:metamapping]
```

## When to Use the Metadata Mapping Pattern in Java

Use the Metadata Mapping Design Pattern when you need to bridge the gap between an object-oriented domain model and a relational database in Java applications, without hard-coding database queries into the domain logic.

## Real-World Applications of Metadata Mapping Pattern in Java

* Object-Relational Mapping (ORM) frameworks like Hibernate, JPA, EclipseLink, and MyBatis frequently utilize the Metadata Mapping Design Pattern to map Java objects to database tables.
* Mapping database rows to domain objects in enterprise applications.

## Benefits and Trade-offs of Metadata Mapping Pattern

Benefits:

* Decouples object model and database schema, allowing independent evolution.
* Reduces boilerplate code associated with data access.
* Centralizes mapping logic, making changes more manageable.

Trade-offs:

* Adds complexity due to an additional layer of abstraction.
* Can impact performance if not properly optimized.

## Related Java Design Patterns

* [Data Mapper](https://java-design-patterns.com/patterns/data-mapper/): Metadata Mapping is often used within the broader Data Mapper pattern to facilitate the mapping process.
* Active Record: Differently from Active Record, Metadata Mapping separates the data access logic from the domain entities.
* [Repository](https://java-design-patterns.com/patterns/repository/): Works well with the Repository pattern by abstracting data access further, allowing more complex domain logic to be cleanly separated from data mapping.

## References and Credits

* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Java Persistence with Hibernate](https://amzn.to/44tP1ox)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Pro JPA 2: Mastering the Java Persistence API](https://amzn.to/4b7UoMC)
