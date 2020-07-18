---
layout: pattern
title: Data Access Object
folder: dao
permalink: /patterns/dao/
categories: Architectural
tags:
 - Data access
---

## Intent
Object provides an abstract interface to some type of database or other persistence mechanism.

## Explanation

Real world example

> There's a set of customers that need to be persisted to database. Additionally we need the whole set of CRUD (create/read/update/delete) operations so we can operate on customers easily. 

In plain words

> DAO is an interface we provide over the base persistence mechanism. 

Wikipedia says

> In computer software, a data access object (DAO) is a pattern that provides an abstract interface to some type of database or other persistence mechanism.

**Programmatic Example**

Walking through our customers example, here's the basic Customer entity.

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

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return "Customer{" + "id=" + getId() + ", firstName='" + getFirstName() + '\'' + ", lastName='"
        + getLastName() + '\'' + '}';
  }

  @Override
  public boolean equals(final Object that) {
    var isEqual = false;
    if (this == that) {
      isEqual = true;
    } else if (that != null && getClass() == that.getClass()) {
      final var customer = (Customer) that;
      if (getId() == customer.getId()) {
        isEqual = true;
      }
    }
    return isEqual;
  }

  @Override
  public int hashCode() {
    return getId();
  }
}
```

Here's the DAO interface and two different implementations for it. InMemoryCustomerDao keeps a simple map of customers 
in memory while DBCustomerDao is the real RDBMS implementation.

```java
public interface CustomerDao {

  Stream<Customer> getAll() throws Exception;

  Optional<Customer> getById(int id) throws Exception;

  boolean add(Customer customer) throws Exception;

  boolean update(Customer customer) throws Exception;

  boolean delete(Customer customer) throws Exception;
}

public class InMemoryCustomerDao implements CustomerDao {

  private Map<Integer, Customer> idToCustomer = new HashMap<>();

  @Override
  public Stream<Customer> getAll() {
    return idToCustomer.values().stream();
  }

  @Override
  public Optional<Customer> getById(final int id) {
    return Optional.ofNullable(idToCustomer.get(id));
  }

  @Override
  public boolean add(final Customer customer) {
    if (getById(customer.getId()).isPresent()) {
      return false;
    }

    idToCustomer.put(customer.getId(), customer);
    return true;
  }

  @Override
  public boolean update(final Customer customer) {
    return idToCustomer.replace(customer.getId(), customer) != null;
  }

  @Override
  public boolean delete(final Customer customer) {
    return idToCustomer.remove(customer.getId()) != null;
  }
}

public class DbCustomerDao implements CustomerDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(DbCustomerDao.class);

  private final DataSource dataSource;

  public DbCustomerDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public Stream<Customer> getAll() throws Exception {
    try {
      var connection = getConnection();
      var statement = connection.prepareStatement("SELECT * FROM CUSTOMERS");
      var resultSet = statement.executeQuery();
      return StreamSupport.stream(new Spliterators.AbstractSpliterator<Customer>(Long.MAX_VALUE,
          Spliterator.ORDERED) {

        @Override
        public boolean tryAdvance(Consumer<? super Customer> action) {
          try {
            if (!resultSet.next()) {
              return false;
            }
            action.accept(createCustomer(resultSet));
            return true;
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        }
      }, false).onClose(() -> mutedClose(connection, statement, resultSet));
    } catch (SQLException e) {
      throw new CustomException(e.getMessage(), e);
    }
  }

  private Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }

  private void mutedClose(Connection connection, PreparedStatement statement, ResultSet resultSet) {
    try {
      resultSet.close();
      statement.close();
      connection.close();
    } catch (SQLException e) {
      LOGGER.info("Exception thrown " + e.getMessage());
    }
  }

  private Customer createCustomer(ResultSet resultSet) throws SQLException {
    return new Customer(resultSet.getInt("ID"),
        resultSet.getString("FNAME"),
        resultSet.getString("LNAME"));
  }

  @Override
  public Optional<Customer> getById(int id) throws Exception {

    ResultSet resultSet = null;

    try (var connection = getConnection();
         var statement = connection.prepareStatement("SELECT * FROM CUSTOMERS WHERE ID = ?")) {

      statement.setInt(1, id);
      resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return Optional.of(createCustomer(resultSet));
      } else {
        return Optional.empty();
      }
    } catch (SQLException ex) {
      throw new CustomException(ex.getMessage(), ex);
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
    }
  }

  @Override
  public boolean add(Customer customer) throws Exception {
    if (getById(customer.getId()).isPresent()) {
      return false;
    }

    try (var connection = getConnection();
         var statement = connection.prepareStatement("INSERT INTO CUSTOMERS VALUES (?,?,?)")) {
      statement.setInt(1, customer.getId());
      statement.setString(2, customer.getFirstName());
      statement.setString(3, customer.getLastName());
      statement.execute();
      return true;
    } catch (SQLException ex) {
      throw new CustomException(ex.getMessage(), ex);
    }
  }

  @Override
  public boolean update(Customer customer) throws Exception {
    try (var connection = getConnection();
         var statement =
             connection
                 .prepareStatement("UPDATE CUSTOMERS SET FNAME = ?, LNAME = ? WHERE ID = ?")) {
      statement.setString(1, customer.getFirstName());
      statement.setString(2, customer.getLastName());
      statement.setInt(3, customer.getId());
      return statement.executeUpdate() > 0;
    } catch (SQLException ex) {
      throw new CustomException(ex.getMessage(), ex);
    }
  }

  @Override
  public boolean delete(Customer customer) throws Exception {
    try (var connection = getConnection();
         var statement = connection.prepareStatement("DELETE FROM CUSTOMERS WHERE ID = ?")) {
      statement.setInt(1, customer.getId());
      return statement.executeUpdate() > 0;
    } catch (SQLException ex) {
      throw new CustomException(ex.getMessage(), ex);
    }
  }
}
```

Finally here's how we use our DAO to manage customers.

```java
    final var dataSource = createDataSource();
    createSchema(dataSource);
    final var customerDao = new DbCustomerDao(dataSource);
    
    addCustomers(customerDao);
    log.info(ALL_CUSTOMERS);
    try (var customerStream = customerDao.getAll()) {
      customerStream.forEach((customer) -> log.info(customer.toString()));
    }
    log.info("customerDao.getCustomerById(2): " + customerDao.getById(2));
    final var customer = new Customer(4, "Dan", "Danson");
    customerDao.add(customer);
    log.info(ALL_CUSTOMERS + customerDao.getAll());
    customer.setFirstName("Daniel");
    customer.setLastName("Danielson");
    customerDao.update(customer);
    log.info(ALL_CUSTOMERS);
    try (var customerStream = customerDao.getAll()) {
      customerStream.forEach((cust) -> log.info(cust.toString()));
    }
    customerDao.delete(customer);
    log.info(ALL_CUSTOMERS + customerDao.getAll());
    
    deleteSchema(dataSource);
```


## Class diagram
![alt text](./etc/dao.png "Data Access Object")

## Applicability
Use the Data Access Object in any of the following situations

* when you want to consolidate how the data layer is accessed
* when you want to avoid writing multiple data retrieval/persistence layers

## Credits

* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=48d37c67fb3d845b802fa9b619ad8f31)
