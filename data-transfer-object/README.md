---
title: "Data Transfer Object Pattern in Java: Simplifying Data Exchange Between Subsystems"
shortTitle: Data Transfer Object (DTO)
description: "Learn about the Data Transfer Object (DTO) pattern, its implementation, and practical uses in Java applications. Optimize data transfer between layers with this structural design pattern."
category: Structural
language: en
tag:
  - Client-server
  - Data transfer
  - Decoupling
  - Layered architecture
  - Optimization
---

## Also known as

* Transfer Object
* Value Object

## Intent of Data Transfer Object Design Pattern

The Data Transfer Object (DTO) pattern is used to transfer data between software application subsystems or layers, particularly in the context of network calls or database retrieval in Java applications. It reduces the number of method calls by aggregating the data in a single transfer.

## Detailed Explanation of Data Transfer Object Pattern with Real-World Examples

Real-world example

> Imagine a large company with several departments (e.g., Sales, HR, and IT) needing to share employee information efficiently. Instead of each department querying and retrieving data like name, address, and role individually, they use a courier service to bundle this data into a single package. This package, representing a Data Transfer Object (DTO), allows the departments to easily receive and process comprehensive employee data without making multiple requests. This simplifies data handling, reduces communication overhead, and ensures a standardized format across the company.

In plain words

> Using DTO, relevant information can be fetched with a single backend query.

Wikipedia says

> In the field of programming a data transfer object (DTO) is an object that carries data between processes. The motivation for its use is that communication between processes is usually done resorting to remote interfaces (e.g. web services), where each call is an expensive operation. Because the majority of the cost of each call is related to the round-trip time between the client and the server, one way of reducing the number of calls is to use an object (the DTO) that aggregates the data that would have been transferred by the several calls, but that is served by one call only.

## Programmatic Example of DTO Pattern in Java

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

Now fetching customer information is easy since we have the DTOs. The 2nd example uses `ProductDTO` similarly.

```java
public class App {

  public static void main(String[] args) {

    // Example 1: Customer DTO
    var customerOne = new CustomerDto("1", "Kelly", "Brown");
    var customerTwo = new CustomerDto("2", "Alfonso", "Bass");
    var customers = new ArrayList<>(List.of(customerOne, customerTwo));

    var customerResource = new CustomerResource(customers);

    LOGGER.info("All customers:");
    var allCustomers = customerResource.customers();
    printCustomerDetails(allCustomers);

    LOGGER.info("----------------------------------------------------------");

    LOGGER.info("Deleting customer with id {1}");
    customerResource.delete(customerOne.id());
    allCustomers = customerResource.customers();
    printCustomerDetails(allCustomers);

    LOGGER.info("----------------------------------------------------------");

    LOGGER.info("Adding customer three}");
    var customerThree = new CustomerDto("3", "Lynda", "Blair");
    customerResource.save(customerThree);
    allCustomers = customerResource.customers();
    printCustomerDetails(allCustomers);

    // Example 2: Product DTO

    Product tv = Product.builder().id(1L).name("TV").supplier("Sony").price(1000D).cost(1090D).build();
    Product microwave =
        Product.builder()
            .id(2L)
            .name("microwave")
            .supplier("Delonghi")
            .price(1000D)
            .cost(1090D).build();
    Product refrigerator =
        Product.builder()
            .id(3L)
            .name("refrigerator")
            .supplier("Botsch")
            .price(1000D)
            .cost(1090D).build();
    Product airConditioner =
        Product.builder()
            .id(4L)
            .name("airConditioner")
            .supplier("LG")
            .price(1000D)
            .cost(1090D).build();
    List<Product> products =
        new ArrayList<>(Arrays.asList(tv, microwave, refrigerator, airConditioner));
    ProductResource productResource = new ProductResource(products);

    LOGGER.info(
        "####### List of products including sensitive data just for admins: \n {}",
        Arrays.toString(productResource.getAllProductsForAdmin().toArray()));
    LOGGER.info(
        "####### List of products for customers: \n {}",
        Arrays.toString(productResource.getAllProductsForCustomer().toArray()));

    LOGGER.info("####### Going to save Sony PS5 ...");
    ProductDto.Request.Create createProductRequestDto =
        new ProductDto.Request.Create()
            .setName("PS5")
            .setCost(1000D)
            .setPrice(1220D)
            .setSupplier("Sony");
    productResource.save(createProductRequestDto);
    LOGGER.info(
        "####### List of products after adding PS5: {}",
        Arrays.toString(productResource.products().toArray()));
  }

  private static void printCustomerDetails(List<CustomerDto> allCustomers) {
    allCustomers.forEach(customer -> LOGGER.info(customer.firstName()));
  }
}
```

The console output:

```
11:10:51.838 [main] INFO com.iluwatar.datatransfer.App -- All customers:
11:10:51.840 [main] INFO com.iluwatar.datatransfer.App -- Kelly
11:10:51.840 [main] INFO com.iluwatar.datatransfer.App -- Alfonso
11:10:51.840 [main] INFO com.iluwatar.datatransfer.App -- ----------------------------------------------------------
11:10:51.840 [main] INFO com.iluwatar.datatransfer.App -- Deleting customer with id {1}
11:10:51.840 [main] INFO com.iluwatar.datatransfer.App -- Alfonso
11:10:51.840 [main] INFO com.iluwatar.datatransfer.App -- ----------------------------------------------------------
11:10:51.840 [main] INFO com.iluwatar.datatransfer.App -- Adding customer three}
11:10:51.840 [main] INFO com.iluwatar.datatransfer.App -- Alfonso
11:10:51.840 [main] INFO com.iluwatar.datatransfer.App -- Lynda
11:10:51.848 [main] INFO com.iluwatar.datatransfer.App -- ####### List of products including sensitive data just for admins: 
 [Private{id=1, name='TV', price=1000.0, cost=1090.0}, Private{id=2, name='microwave', price=1000.0, cost=1090.0}, Private{id=3, name='refrigerator', price=1000.0, cost=1090.0}, Private{id=4, name='airConditioner', price=1000.0, cost=1090.0}]
11:10:51.852 [main] INFO com.iluwatar.datatransfer.App -- ####### List of products for customers: 
 [Public{id=1, name='TV', price=1000.0}, Public{id=2, name='microwave', price=1000.0}, Public{id=3, name='refrigerator', price=1000.0}, Public{id=4, name='airConditioner', price=1000.0}]
11:10:51.852 [main] INFO com.iluwatar.datatransfer.App -- ####### Going to save Sony PS5 ...
11:10:51.856 [main] INFO com.iluwatar.datatransfer.App -- ####### List of products after adding PS5: [Product{id=1, name='TV', price=1000.0, cost=1090.0, supplier='Sony'}, Product{id=2, name='microwave', price=1000.0, cost=1090.0, supplier='Delonghi'}, Product{id=3, name='refrigerator', price=1000.0, cost=1090.0, supplier='Botsch'}, Product{id=4, name='airConditioner', price=1000.0, cost=1090.0, supplier='LG'}, Product{id=5, name='PS5', price=1220.0, cost=1000.0, supplier='Sony'}]
```

## When to Use the Data Transfer Object Pattern in Java

Use the Data Transfer Object pattern when:

* When you need to optimize network traffic by reducing the number of calls, especially in a client-server architecture.
* In scenarios where batch processing of data is preferred over individual processing.
* When working with remote interfaces, to encapsulate the data transfer in a serializable object that can be easily transmitted.

## Data Transfer Object Pattern Java Tutorials

* [Data Transfer Object Pattern in Java - Implementation and Mapping (StackAbuse)](https://stackabuse.com/data-transfer-object-pattern-in-java-implementation-and-mapping/)
* [Design Pattern - Transfer Object Pattern (TutorialsPoint)](https://www.tutorialspoint.com/design_pattern/transfer_object_pattern.htm)
* [The DTO Pattern (Baeldung)](https://www.baeldung.com/java-dto-pattern)

## Real-World Applications of DTO Pattern in Java

* Remote Method Invocation (RMI) in Java, where DTOs are used to pass data across network.
* Enterprise JavaBeans (EJB), particularly when data needs to be transferred from EJBs to clients.
* Various web service frameworks where DTOs encapsulate request and response data.

## Benefits and Trade-offs of Data Transfer Object Pattern

Benefits:

* Reduces network calls, thereby improving application performance.
* Decouples the client from the server, leading to more modular and maintainable code.
* Simplifies data transmission over the network by aggregating data into single objects.

Trade-offs:

* Introduces additional classes into the application, which may increase complexity.
* Can lead to redundant data structures that mirror domain models, potentially causing synchronization issues.
* May encourage design that leads to an anemic domain model, where business logic is separated from data.

## Related Patterns

* [Composite Entity](https://java-design-patterns.com/patterns/composite-entity/): DTOs may be used to represent composite entities, particularly in persistence mechanisms.
* [Facade](https://java-design-patterns.com/patterns/facade/): Similar to DTO, a Facade may aggregate multiple calls into one, improving efficiency.
* [Service Layer](https://java-design-patterns.com/patterns/service-layer/): Often involves using DTOs to transfer data across the boundary between the service layer and its clients.

## References and Credits

* [Core J2EE Patterns: Best Practices and Design Strategies](https://amzn.to/4cKndQp)
* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Data Transfer Object (Microsoft)](https://msdn.microsoft.com/en-us/library/ff649585.aspx)
