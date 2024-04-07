---
title: Data Transfer Object
category: Structural
language: en
tag:
    - Client-server
    - Data transfer
    - Layered architecture
    - Optimization
---

## Also known as

* Transfer Object
* Value Object

## Intent

The Data Transfer Object (DTO) pattern is used to transfer data between software application subsystems or layers, particularly in the context of network calls or database retrieval in Java applications. It reduces the number of method calls by aggregating the data in a single transfer.

## Explanation

Real world example

> Imagine you're at a grocery store with a long shopping list. Instead of calling a friend from every aisle to ask what's needed, you compile the entire list into one message and send it over. This is akin to a Data Transfer Object (DTO) in software, where instead of making multiple requests for data, a single, compiled set of data (like the complete shopping list) is transferred in one go, optimizing communication and efficiency.

In plain words

> Using DTO relevant information can be fetched with a single backend query. 

Wikipedia says

> In the field of programming a data transfer object (DTO) is an object that carries data between processes. The motivation for its use is that communication between processes is usually done resorting to remote interfaces (e.g. web services), where each call is an expensive operation. Because the majority of the cost of each call is related to the round-trip time between the client and the server, one way of reducing the number of calls is to use an object (the DTO) that aggregates the data that would have been transferred by the several calls, but that is served by one call only.

**Programmatic Example**

Let's first introduce our simple `CustomerDTO` record.

```java
public record CustomerDto(String id, String firstName, String lastName) {}
```

`CustomerResource` record acts as the server for customer information.

```java
public record CustomerResource(List<CustomerDto> customers) {

    public void save(CustomerDto customer) {
        customers.add(customer);
    }
    
    public void delete(String customerId) {
        customers.removeIf(customer -> customer.id().equals(customerId));
    }
}
```

Now fetching customer information is easy since we have the DTOs.

```java
var customerOne = new CustomerDto("1", "Kelly", "Brown");
var customerTwo = new CustomerDto("2", "Alfonso", "Bass");
var customers = new ArrayList<>(List.of(customerOne, customerTwo));
var customerResource = new CustomerResource(customers);
LOGGER.info("All customers:");
var allCustomers = customerResource.getCustomers();
printCustomerDetails(allCustomers);
```

The output will be:

```
18:31:53.868 [main] INFO com.iluwatar.datatransfer.App -- All customers:
18:31:53.870 [main] INFO com.iluwatar.datatransfer.App -- Kelly
18:31:53.870 [main] INFO com.iluwatar.datatransfer.App -- Alfonso
```

## Class diagram

![DTO class diagram](./etc/data-transfer-object.urm.png "data-transfer-object")

## Applicability

Use the Data Transfer Object pattern when:

* When you need to optimize network traffic by reducing the number of calls, especially in a client-server architecture.
* In scenarios where batch processing of data is preferred over individual processing.
* When working with remote interfaces, to encapsulate the data transfer in a serializable object that can be easily transmitted.

## Tutorials

* [Data Transfer Object Pattern in Java - Implementation and Mapping](https://stackabuse.com/data-transfer-object-pattern-in-java-implementation-and-mapping/)
* [The DTO Pattern (Data Transfer Object)](https://www.baeldung.com/java-dto-pattern)

## Known Uses

* Remote Method Invocation (RMI) in Java, where DTOs are used to pass data across network.
* Enterprise JavaBeans (EJB), particularly when data needs to be transferred from EJBs to clients.
* Various web service frameworks where DTOs encapsulate request and response data.

## Consequences

Benefits:

* Reduces network calls, thereby improving application performance.
* Decouples the client from the server, leading to more modular and maintainable code.
* Simplifies data transmission over the network by aggregating data into single objects.

Trade-offs:

* Introduces additional classes into the application, which may increase complexity.
* Can lead to redundant data structures that mirror domain models, potentially causing synchronization issues.
* May encourage design that leads to an anemic domain model, where business logic is separated from data.

## Related Patterns

* [Service Layer](https://java-design-patterns.com/patterns/service-layer/): Often involves using DTOs to transfer data across the boundary between the service layer and its clients.
* [Facade](https://java-design-patterns.com/patterns/facade/): Similar to DTO, a Facade may aggregate multiple calls into one, improving efficiency.
* [Composite Entity](https://java-design-patterns.com/patterns/composite-entity/): DTOs may be used to represent composite entities, particularly in persistence mechanisms.

## Credits

* [Design Pattern - Transfer Object Pattern](https://www.tutorialspoint.com/design_pattern/transfer_object_pattern.htm)
* [Data Transfer Object](https://msdn.microsoft.com/en-us/library/ff649585.aspx)
* [J2EE Design Patterns](https://www.amazon.com/gp/product/0596004273/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596004273&linkCode=as2&tag=javadesignpat-20&linkId=f27d2644fbe5026ea448791a8ad09c94)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321127420&linkCode=as2&tag=javadesignpat-20&linkId=014237a67c9d46f384b35e10151956bd)
* [Core J2EE Patterns: Best Practices and Design Strategies](https://amzn.to/4cKndQp)
