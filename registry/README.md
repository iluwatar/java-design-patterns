---
layout: pattern
title: Registry
folder: registry
permalink: /patterns/registry/
categories: Creational
language: en
tags:
 - Instantiation
---

## Intent
Stores the objects of a single class and provide a global point of access to them. 
Similar to Multiton pattern, only difference is that in a registry there is no restriction on the number of objects.

## Explanation

In Plain Words

> Registry is a well-known object that other objects can use to find common objects and services.

**Programmatic Example**
Below is a `Customer` Class

```java
public class Customer {

  private final String id;
  private final String name;

  public Customer(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

}
```

This registry of the `Customer` objects is `CustomerRegistry`
```java
public final class CustomerRegistry {

  private static final CustomerRegistry instance = new CustomerRegistry();

  public static CustomerRegistry getInstance() {
    return instance;
  }

  private final Map<String, Customer> customerMap;

  private CustomerRegistry() {
    customerMap = new ConcurrentHashMap<>();
  }

  public Customer addCustomer(Customer customer) {
    return customerMap.put(customer.getId(), customer);
  }

  public Customer getCustomer(String id) {
    return customerMap.get(id);
  }

}
```

## Class diagram
![Registry](./etc/registry.png)

## Applicability
Use Registry pattern when 

* client wants reference of some object, so client can lookup for that object in the object's registry.

## Consequences
Large number of bulky objects added to registry would result in a lot of memory consumption as objects in the registry are not garbage collected.

## Credits
* https://www.martinfowler.com/eaaCatalog/registry.html
* https://wiki.c2.com/?RegistryPattern
