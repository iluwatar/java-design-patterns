--- 
layout: pattern
title: Snapshot
folder: snapshot
permalink: /patterns/snapshot/
categories:
- creational 
  language: 
  tags:
- Data access
- Gang of Four
---

## Name / classification

Snapshot.

## Intent

Create a view of an object at a point in time.

## Explanation

Real world example

> Many people have phone numbers with details attached. A phone book, where registered phone 
> numbers with details are relieved are snapshots of the history of phone numbers, and their 
> associated details.

In plain words

> A snapshot is a copy of an object as it is at a point in time.

Wikipedia says

> The memento pattern is a software design pattern that exposes the private internal state of an 
> object. One example of how this can be used is to restore an object to its previous state 
> (undo via rollback), another is versioning, another is custom serialization.  

**Programmatic Example**

In this example implementation, `Customer` is an object with Temporality on its current address, 
and `CustomerSnapshot` is the snapshot of `Customer`.

Here are the relevant parts of `Customer`

```java
public class Customer {
  private TemporalCollection<String> addresses = new TemporalCollection<>();

  public String getAddress(SimpleDate date) {
    return addresses.get(date);
  }

  public String getAddress() {
    return getAddress(SimpleDate.getToday());
  }

  public void putAddress(SimpleDate date, String value) {
    addresses.put(date, value);
  }
}
```

Here are the relavant portions of `CustomerSnapshot`,
```java
public class CustomerSnapshot {
  private Customer base;
  private SimpleDate validDate;

  public CustomerSnapshot(Customer base, SimpleDate validDate) {
    this.base = base;
    this.validDate = validDate;
  }

  public String getAddress() {
    return base.getAddress(validDate);
  }
}
```

Then, we introduce an example application that makes use of this
```java
  public static void main(String[] args) {
    Customer billy = new Customer();
    HashMap<SimpleDate, CustomerSnapshot> billySnapshots = new HashMap<>();

    // billy changes addresses a few times, and has snapshots taken occasionally.

    billy.putAddress(new SimpleDate(2004, 2, 4), "88 Worcester St");

    billySnapshots.put(new SimpleDate(2004, 3, 1),
    new CustomerSnapshot(billy, new SimpleDate(2004, 3, 1)));
    billySnapshots.put(new SimpleDate(2005, 5, 3),
    new CustomerSnapshot(billy, new SimpleDate(2005, 5, 3)));

    billy.putAddress(new SimpleDate(2005, 9, 2), "87 Franklin St");

    billySnapshots.put(new SimpleDate(2006, 2, 1),
    new CustomerSnapshot(billy, new SimpleDate(2006, 2, 1)));

    billy.putAddress(new SimpleDate(2007, 1, 2), "18 Circuit St");
    billySnapshots.put(new SimpleDate(2007, 1, 5),
    new CustomerSnapshot(billy, new SimpleDate(2006, 2, 1)));

    System.out.println("Snapshots of billy's address:");
    for (Map.Entry<SimpleDate, CustomerSnapshot> entry : billySnapshots.entrySet()) {
      System.out.println("On " + entry.getKey() + " billy was at " + entry.getValue().getAddress());
    }
  }
```

## Class diagram

![alt text](./etc/snapshot.urm.png "Snapshot")

## Applicability

Use the Snapshot pattern when
* You have an object with temporality and don't want to take that into 
account.

## Tutorials
* [Design Patterns - Memento Pattern](https://www.tutorialspoint.com/design_pattern/memento_pattern.htm)
 is a Snapshot tutorial as a Snapshot is a Memento without a caretaker.
  
## Known uses

* [java.util.Date](http://docs.oracle.com/javase/8/docs/api/java/util/Date.html)
 as Memento is a Snapshot with a caretaker.
## Consequences

Snapshot allows for removing the temporal aspects of an object with temporality.

## Related patterns
* [Memento](https://java-design-patterns.com/patterns/memento/)

## Credits
* [Martin Fowler](https://martinfowler.com/eaaDev/Snapshot.html)
