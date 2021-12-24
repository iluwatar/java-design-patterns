---
layout: pattern
title: Metadata Mapping
folder: metadata-mapping
permalink: /patterns/metadata-mapping/
categories: Architectural
language: en
tags:
 - Data access
---

## Intent

Holds details of object-relational mapping in the metadata.

## Explanation

Real world example

> Hibernate ORM Tool uses Metadata Mapping Pattern to specify the mapping between classes and tables either using XML or annotations in code. 

In plain words

> Metadata Mapping specifies the mapping between classes and tables so that we could treat a table of any database like a Java class.

Wikipedia says

> Create a "virtual [object database](https://en.wikipedia.org/wiki/Object_database)" that can be used from within the programming language.

**Programmatic Example**

We give an example about visiting the information of `USER` table in `h2` database. Firstly, we create `USER` table with `h2`:

```java
@Slf4j
public class DatabaseUtil {
  private static final String DB_URL = "jdbc:h2:mem:metamapping";
  private static final String CREATE_SCHEMA_SQL = "DROP TABLE IF EXISTS `user`;" +
                          "CREATE TABLE `user` (\n" +
                          "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                          "  `username` varchar(255) NOT NULL,\n" +
                          "  `password` varchar(255) NOT NULL,\n" +
                          "  PRIMARY KEY (`id`)\n" +
                          ");";
  private static DataSource dataSource = null;

  public static void createDataSource(){
    LOGGER.info("create h2 database");
    try {
      var _dataSource = new JdbcDataSource();
      _dataSource.setURL(DB_URL);
      Connection connection = null;
      connection = _dataSource.getConnection();
      var statement = connection.createStatement();
      statement.execute(CREATE_SCHEMA_SQL);
      dataSource = _dataSource;
      connection.close();
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
  }
}
```

Correspondingly, here's the basic `User` entity.

```java
public class User implements Serializable {
  private Integer id;
  private String username;
  private String password;

  private static final long serialVersionUID = 1L;

  public User() {}

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
  // getters and setters ->
  ...
```

Then we write a `xml` file to show the mapping between the table and the object:

```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-mapping PUBLIC
    "-//Hibernate/Hibernate Mapping DTD//EN"
    "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">

<hibernate-mapping>
  <class name="com.iluwatar.metamapping.model.User" table="user">
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
    "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
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
public class UserService{
  private static final SessionFactory factory = HibernateUtil.getSessionFactory();
  
  public List<User> listUser(){
    LOGGER.info("list all users.");
    Session session = factory.openSession();
    Transaction tx = null;
    List<User> users = new ArrayList<>();
    try{
      tx = session.beginTransaction();
      List userIter = session.createQuery("FROM User").list();
      for (Iterator iterator = userIter.iterator(); iterator.hasNext();){
        users.add((User) iterator.next());
      }
      tx.commit();
    }catch (HibernateException e) {
      if (tx!=null) tx.rollback();
      e.printStackTrace();
    }finally {
      session.close();
    }
    return users;
  }
  
  // other CRUDs ->
  ...
    
  public void close() {
    HibernateUtil.shutdown();
  }
}
```

## Class diagram

![metamapping](etc/metamapping.png)

## Applicability

Use the Metadata Mapping when:

- you want reduce the amount of work needed to handle database mapping.

## Known uses

[Hibernate](https://hibernate.org/), [EclipseLink](https://www.eclipse.org/eclipselink/), [MyBatis](https://blog.mybatis.org/)......

## Credits

- [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)

