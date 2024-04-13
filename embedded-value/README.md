---
title: Embedded Value
category: Behavioral
language: en
tag:
    - Data access
    - Enterprise patterns
    - Optimization
    - Performance
---

## Also known as

* Aggregate Mapping
* Composer
* Inline Value
* Integrated Value

## Intent

The Embedded Value design pattern aims to enhance performance and reduce memory overhead by storing frequently accessed immutable data directly within the object that uses it, rather than separately.

## Explanation

Real-world example

> In a library, the reference desk embeds commonly used resources like dictionaries and encyclopedias directly at the desk for quick and easy access, similar to how the Embedded Value design pattern integrates frequently used data directly within an object for efficiency.

In plain words

> Embedded value pattern let's you map an object into several fields of another object’s table.

**Programmatic Example**

Consider an online ordering example where we have details of item ordered and shipping address. We have Shipping address embedded in Order object. But in database we map shipping address values in Order record instead of creating a separate table for Shipping address and using foreign key to reference the order object.

First, we have POJOs `Order` and `ShippingAddress`

```java
public class Order {

  private int id;
  private String item;
  private String orderedBy;
  private ShippingAddress ShippingAddress;

  public Order(String item, String orderedBy, ShippingAddress ShippingAddress) {
    this.item = item;
    this.orderedBy = orderedBy;
    this.ShippingAddress = ShippingAddress;
  }
}
```

```java
public class ShippingAddress {

    private String city;
    private String state;
    private String pincode;

    public ShippingAddress(String city, String state, String pincode) {
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }
}
```

Now, we have to create only one table for Order along with fields for shipping address attributes.

```Sql
CREATE TABLE Orders
(
    Id        INT AUTO_INCREMENT,
    item      VARCHAR(50) NOT NULL,
    orderedBy VARCHAR(50) city VARCHAR (50),
    state     VARCHAR(50),
    pincode   CHAR(6)     NOT NULL,
    PRIMARY KEY (Id)
)
```

While performing the database queries and inserts, we box and unbox shipping address details.

```java
final String INSERT_ORDER="INSERT INTO Orders (item, orderedBy, city, state, pincode) VALUES (?, ?, ?, ?, ?)";

public boolean insertOrder(Order order)throws Exception{
  var insertOrder=new PreparedStatement(INSERT_ORDER);
  var address=order.getShippingAddress();
  conn.setAutoCommit(false);
  insertIntoOrders.setString(1,order.getItem());
  insertIntoOrders.setString(2,order.getOrderedBy());
  insertIntoOrders.setString(3,address.getCity());
  insertIntoOrders.setString(4,address.getState());
  insertIntoOrders.setString(5,address.getPincode());

  var affectedRows=insertIntoOrders.executeUpdate();
  if(affectedRows==1) {
    Logger.info("Inserted successfully");
  } else {
    Logger.info("Couldn't insert "+order);
  }
}
```

## Class diagram

![Embedded Value](./etc/embedded-value.urm.png "Embedded Value class diagram")

## Applicability

Use the Embedded value pattern when:

* An application requires high performance and the data involved is immutable.
* Memory footprint reduction is critical, especially in environments with limited resources.
* Objects frequently access a particular piece of immutable data.

## Tutorials

* [Dzone](https://dzone.com/articles/practical-php-patterns/practical-php-patterns-3)
* [Ram N Java](https://ramj2ee.blogspot.com/2013/08/embedded-value-design-pattern.html)
* [Five's Weblog](https://powerdream5.wordpress.com/2007/10/09/embedded-value/)

## Consequences

Benefits:

* Reduces the memory overhead by avoiding separate allocations for immutable data.
* Improves performance by minimizing memory accesses and reducing cache misses.

Trade-offs:

* Increases complexity in object design and can lead to tightly coupled systems.
* Modifying the embedded value necessitates changes across all objects that embed this value, which can complicate maintenance.

## Related Patterns

[Flyweight](https://java-design-patterns.com/patterns/flyweight/): Shares objects to support large quantities using a minimal amount of memory, somewhat similar in intent but different in implementation.
[Singleton](https://java-design-patterns.com/patterns/singleton/): Ensures a class has only one instance and provides a global point of access to it, can be used to manage a shared embedded value.

## Credits

* [Patterns of Enterprise Application Architecture](https://amzn.to/4452Idd)
* [Ram N Java](https://ramj2ee.blogspot.com/2013/08/embedded-value-design-pattern.html)
