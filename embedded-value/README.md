---
title: Embedded Value
category: Structural 
language: en
tag: 
 - Data Access
 - Enterprise Application Pattern
---

## Also known as
Aggregate Mapping, Composer

## Intent
Many small objects make sense in an OO system that don’t make sense as
tables in a database. An Embedded Value maps the values of an object to fields in the record of the object’s owner.

## Explanation

Real-world example

> Examples include currency-aware money objects and date
ranges. Although the default thinking is to save an object as a table, no sane person would want a table of money values. 
> Another example would be the online orders which have a shipping address like street, city, state. We map these values of Shipping address object to fields in record of Order object.

In plain words

> Embedded value pattern let's you map an object into several fields of another object’s table.

**Programmatic Example**

Consider online order's example where we have details of item ordered and shipping address. We have Shipping address embedded in Order object. But in database we map shipping address values in Order record instead of creating a separate table for Shipping address and using foreign key to reference the order object.  

First, we have POJOs `Order` and `ShippingAddress`

```java
public class Order {

    private int id;
    private String item;
    private String orderedBy;
    private ShippingAddress ShippingAddress;

    public Order(String item, String orderedBy, ShippingAddress           ShippingAddress) {
        this.item = item;
        this.orderedBy = orderedBy;
        this.ShippingAddress = ShippingAddress;
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
CREATE TABLE Orders (Id INT AUTO_INCREMENT, item VARCHAR(50) NOT NULL, orderedBy VARCHAR(50) city VARCHAR(50), state VARCHAR(50), pincode CHAR(6) NOT NULL, PRIMARY KEY(Id))
```

While performing the database queries and inserts, we box and unbox shipping address details.

```java
final String INSERT_ORDER = "INSERT INTO Orders (item, orderedBy, city, state, pincode) VALUES (?, ?, ?, ?, ?)";

public boolean insertOrder(Order order) throws Exception {
  var insertOrder = new PreparedStatement(INSERT_ORDER);
  var address = order.getShippingAddress();
  conn.setAutoCommit(false);
  insertIntoOrders.setString(1, order.getItem());
  insertIntoOrders.setString(2, order.getOrderedBy());
  insertIntoOrders.setString(3, address.getCity());
  insertIntoOrders.setString(4, address.getState());
  insertIntoOrders.setString(5, address.getPincode());
  
  var affectedRows = insertIntoOrders.executeUpdate();
  if(affectedRows == 1){
    Logger.info("Inserted successfully");
  }else{
    Logger.info("Couldn't insert " + order);
  }
}
```

## Class diagram
![alt text](./etc/embedded-value.urm.png "Embedded value class diagram")

## Applicability

Use the Embedded value pattern when

* Many small objects make sense in an OO system that don’t make sense as tables in a database.
* The simplest cases for Embedded Value are the clear, simple Value Objects like money and date range.
* If you’re mapping to an existing schema, you can use this pattern when a table contains data that you want to split into more than one object in memory. This can occur when you want to factor out some behaviour in object model. 
* In most cases you’ll only use Embedded Value on a reference object when the association between them is single valued at both ends (a one-to-one association). 
* It can only be used for fairly simple dependents. A solitary dependent, or a few separated dependents, works well.

## Tutorials

* [Dzone](https://dzone.com/articles/practical-php-patterns/practical-php-patterns-3)
* [Ram N Java](https://ramj2ee.blogspot.com/2013/08/embedded-value-design-pattern.html)
* [Five's Weblog](https://powerdream5.wordpress.com/2007/10/09/embedded-value/)

## Consequences

* The great advantage of Embedded Value is that it allows SQL queries to be made against the values in the dependent object.
* The embedded value object has no persistence behaviour at all.
* While using this, you have to be careful that any change to the dependent marks the owner as dirty—which isn’t an issue with Value Objects that are replaced in the owner.
* Another issue is the loading and saving. If you only load the embedded object memory when you load the owner, that’s an argument for saving both in the same table. 
* Another question is whether you’ll want to access the embedded objects' data separately through SQL. This can be important if you’re reporting through SQL and don’t have a separate database for reporting.


## Credits

* [Fowler, Martin - Patterns of enterprise application architecture-Addison-Wesley](https://www.amazon.com/Patterns-Enterprise-Application-Architecture-Martin/dp/0321127420)
* [Ram N Java](https://ramj2ee.blogspot.com/2013/08/embedded-value-design-pattern.html)