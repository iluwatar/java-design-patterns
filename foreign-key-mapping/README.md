---
layout: pattern
title: Foreign Key Mapping 
folder: foreign-key-mapping 
permalink: /patterns/foreign-key-mapping/
categories: data access
language: en
tags: 
- 
---

## Intent
Maps an association between objects to a foreign key reference between tables.


## Explanation
### Real world Example
**Persons**
| PersonID    | LastName | FirstName       | Age       |
| ----------- | ----------- | ----------- | ----------- |
| 1      | Hansen       | John       | 30       |
| 2   | Sven        | Tom       | 23       |
| 3   | Peter        | Michael        | 20       |

**Orders**
| OrderID       | Content | PersonID       |
| ----------- | ----------- | ----------- |
| 1      | Juice       | 3       |
| 2   | Coffee        | 3       |
| 3   | Tea Leaves       | 2       |
| 4   | Beer        | 1       |

> Suppose we have a database that contains the above ***Orders*** and ***Persons*** tables. You
> can see that the table ***Orders*** contains a field called **PersonID**. This field is also the primary
> key for the ***Persons*** table and hence is a **Foreign key** for the ***Orders*** table.
> In this way the ***Orders*** and ***Persons*** tables are linked to each other via this foreign key.


### In simple words

> A foreign key is a set of attributes that references a candidate key(a key eligible to be a primary key of another table).
> This implementation is a simple example of linking 2 tables via this foreign key.

### Wikipedia says

> In the context of relational databases, a foreign key is a set of attributes 
> subject to a certain kind of inclusion dependency constraints, 
> specifically a constraint that the tuples consisting of the foreign 
> key attributes in one relation, R, must also exist in some other (not necessarily distinct)
> relation, S, and furthermore that those attributes must also be a candidate key in S.

### Programmatic example
* For the purpose of this demonstration assume we have a database called **database** which has 
2 tables **Orders** and **Persons**. (In this implementation each table is represented by an Arraylist.)
* First lets start by looking at the person and order objects.

```java
/**
 * Person definition.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
public final class Person implements Serializable {

  private static final long serialVersionUID = 1L;

  @EqualsAndHashCode.Include
  private int personNationalId;
  private String lastName;
  private String firstName;
  private long age;

  @Override
  public String toString() {

    return "Person ID is : " + personNationalId + " ; Person Last Name is : " + lastName + " ; Person First Name is : "
        + firstName + " ; Age is :" + age;

  }

  /**
   * Get specific person's order list.
   *
   * @param orderList all order in database
   * @return a list of order belong to person
   */
  public List<Order> getAllOrder(List<Order> orderList) {
    List<Order> orders = new ArrayList<>();
    for (Order order : orderList) {
      if (this.equals(order.getOwner())) {
        orders.add(order);
      }
    }
    return orders;
  }

}
```

````java
/**
 * Order definition.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
public final class Order implements Serializable {

  private static final long serialVersionUID = 1L;

  @EqualsAndHashCode.Include
  private int orderNationalId;
  private String content;
  private Person owner;

  @Override
  public String toString() {

    return "Order ID is : " + orderNationalId + " ; Order Content is : " + content + " ; Owner is: "
        + owner.getFirstName();

  }

}
````

* Note from the above implementations that each Order and Person can be uniquely identified by orderNationalID and personNationalID fields
  respectively. Hence, these keys, are candidate keys for our 2 tables respectively.
* Now, we can see that each order contains a person object (which is analogous to it just containing a personNationalID in a relational database).
  Hence, this personNationalID ID key in the Order acts as a FOREIGN KEY for the Order table and uniquely maps each order to 1 person.
## Class diagram

![alt text](./etc/ForeignKeyMapping.png "Foreign Key Mapping Pattern")

## Applicability

* Each object contains the database key from the appropriate database table. If two objects are linked together with an association, 
this association can be replaced by a foreign key in the database.

## Credits
* [Foreign Key Mapping](https://www.sourcecodeexamples.net/2018/05/foreign-key-mapping-pattern.html)