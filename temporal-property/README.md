--- 
layout: pattern 
title: Temporal Property
folder: temporal-property
permalink: /patterns/temporal-property/
categories:
- creational 
language: en 
tags:
- Data Access
---

## Name / classification

Temporal Property.

## Also known as
Historic Mapping, History on Association.

## Intent

Have a small proportion of attributes in an object that change over time.

## Explanation

Real-world example
> A person's address changes over time, so there is a history of the person changing their 
> address over time.

In plain words
> A small proportions of attributes are temporal, and are stored with a date-based history.  

Martin Fowler says
> The key to this pattern is providing a regular and predictable interface for dealing with 
> those properties of an object that change over time. The most important part of this lies in 
> the accessor functions. You will always see an accessor function that takes a Time Point as an 
> argument: this allows you to ask "what was Mr Fowler's address on 2 Feb 1998?". In addition 
> you'll usually see an accessor that takes no argument, this is a question about an address 
> according to some default, usually today.

**Programmatic Example**

In this example, each `Customer` is an object with their address being a `TemporalProperty`. The 
addresses are stored within an `AddressHistory`.

Here are the relevant parts of `Customer`
```java
public class Customer {
  private final int id;
  private String name;

  AddressHistory addressHistory;
  
  public Customer(int id, String name) {
    this.id = id;
    this.name = name;
    addressHistory = new AddressHistory();
  }
  
  public void putAddress(String address, SimpleDate date) {
    addressHistory.put(date, address);
  }
  
  public String getAddress(SimpleDate date) throws IllegalStateException {
    return addressHistory.get(date);
  }
}
```

And here are the relevant parts of `AddressHistory`, which gets the 'best' address for a given date.

```java
public class AddressHistory {
  private HashMap<SimpleDate, String> addressMap;
  
  AddressHistory() {
    addressMap = new HashMap<>();
  }
  
  public String get(SimpleDate date) throws IllegalStateException {
    // The most recent date recorded before the given date
    SimpleDate mostRecent = null;

    for (Map.Entry<SimpleDate, String> entry : addressMap.entrySet()) {
      SimpleDate checkDate = entry.getKey();
      // 0 if date = check, 1 if check > date, -1 if check < date
      int afterDate = checkDate.compareTo(date);

      // given date is in map, return the address.
      if (afterDate == 0) {
        return entry.getValue();
      }
      // date being checked is after the requested.
      if (afterDate > 0) {
        continue;
      }

      // if first date found before the given date
      if (mostRecent == null) {
        mostRecent = checkDate;
      }

      // new date is after the current most recent and is before the given
      if (checkDate.compareTo(mostRecent) > 0) {
        mostRecent = checkDate;
      }
    }

    if (mostRecent != null) {
      return addressMap.get(mostRecent);
    }

    throw new IllegalStateException("Customer has no recorded address that early.");
  }
  
  public void put(SimpleDate date, String address) {
    addressMap.put(date, address);
  }
}

```

Then, we introduce an example application that makes use of this

```java
public static void main(String[] args) {
  Customer john = new Customer(1234, "John");

  // set today's calendar date to 1st January 2000
  SimpleDate.setToday(new SimpleDate(2000, 1, 1));

  // set john's address at different points in time

  // Set the address where john lived on the 4th of february 1996 to 124 ave
  john.putAddress("124 ave", new SimpleDate(1996, 2, 4));
  // Set the address john lives in today to 123 place
  john.putAddress("123 place");

  // set the calandar day to tomorrow, the 2nd of January 2000
  SimpleDate.setToday(new SimpleDate(2000, 1, 2));

  // get john's address today
  LOGGER.info("John lives at " + john.getAddress() + " today");

  // get the address that john lived at on the 3rd of June 1997
  LOGGER.info("John lived at " + john.getAddress(new SimpleDate(1997, 7, 3))
  + " on the 3rd of June 1997");

  }
```

With output

```
John lives at 123 place today
John lived at 124 ave on the 3rd of June 1997
```

## Class diagram

![alt text](./etc/temporal-property.urm.png "Temporal Property")

## Applicability

Use the temporal property design pattern when
* You have a class that has a few properties that display temporal behavior, and you want easy 
  access to those temporal values. 

## Consequences

Pros:
* Easy to assess
* Are temporal


If most of the properties of the class are temporal, then a Temporal Object is needed instead.

## Related patterns

* [Effectivity](https://martinfowler.com/eaaDev/Effectivity.html)
* [Audit Log](https://martinfowler.com/eaaDev/AuditLog.html)
* [Temporal Object](https://martinfowler.com/eaaDev/TemporalObject.html)

## Credits

* [Temporal Property](https://martinfowler.com/eaaDev/TemporalProperty.html)