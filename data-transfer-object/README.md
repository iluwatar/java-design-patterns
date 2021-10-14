---
layout: pattern
title: Data Transfer Object
folder: data-transfer-object
permalink: /patterns/data-transfer-object/
categories: Architectural
language: en
tags:
 - Performance
---

## Intent

Pass data with multiple attributes in one shot from client to server, to avoid multiple calls to 
remote server. 

## Explanation

Real world example

> We need to fetch information about customers from remote database. Instead of querying the 
> attributes one at a time, we use DTOs to transfer all the relevant attributes in a single shot.     

In plain words

> Using DTO relevant information can be fetched with a single backend query. 

Wikipedia says

> In the field of programming a data transfer object (DTO) is an object that carries data between 
> processes. The motivation for its use is that communication between processes is usually done 
> resorting to remote interfaces (e.g. web services), where each call is an expensive operation. 
> Because the majority of the cost of each call is related to the round-trip time between the client 
> and the server, one way of reducing the number of calls is to use an object (the DTO) that 
> aggregates the data that would have been transferred by the several calls, but that is served by 
> one call only.

**Programmatic Example**

Let's first introduce our simple `CustomerDTO` class.

```java
public class CustomerDto {
  private final String id;
  private final String firstName;
  private final String lastName;

  public CustomerDto(String id, String firstName, String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
}
```

`CustomerResource` class acts as the server for customer information.

```java
public class CustomerResource {
  private final List<CustomerDto> customers;

  public CustomerResource(List<CustomerDto> customers) {
    this.customers = customers;
  }

  public List<CustomerDto> getAllCustomers() {
    return customers;
  }

  public void save(CustomerDto customer) {
    customers.add(customer);
  }

  public void delete(String customerId) {
    customers.removeIf(customer -> customer.getId().equals(customerId));
  }
}
```

Now fetching customer information is easy since we have the DTOs.

```java
    var allCustomers = customerResource.getAllCustomers();
    allCustomers.forEach(customer -> LOGGER.info(customer.getFirstName()));
    // Kelly
    // Alfonso
```

## Class diagram

![alt text](./etc/data-transfer-object.urm.png "data-transfer-object")

## Applicability

Use the Data Transfer Object pattern when:

* The client is asking for multiple information. And the information is related.
* When you want to boost the performance to get resources.
* You want reduced number of remote calls.

## Credits

* [Design Pattern - Transfer Object Pattern](https://www.tutorialspoint.com/design_pattern/transfer_object_pattern.htm)
* [Data Transfer Object](https://msdn.microsoft.com/en-us/library/ff649585.aspx)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=014237a67c9d46f384b35e10151956bd)
