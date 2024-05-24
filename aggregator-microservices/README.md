---
title: Aggregator Microservices
category: Architectural
language: en
tag:
    - API design
    - Client-server
    - Data processing
    - Decoupling
    - Integration
    - Microservices
    - Scalability
---

## Intent

To aggregate responses from multiple microservices and return a consolidated response to the client.

## Explanation

Real world example

> Imagine an online travel booking platform. When a user searches for a vacation package, the platform needs to gather information from several different services: flights, hotels, car rentals, and local attractions. Instead of the user making separate requests to each service, the platform employs an Aggregator Microservice. This microservice calls each of these services, collects their responses, and then consolidates the information into a single, unified response that is sent back to the user. This simplifies the user experience by providing all necessary travel details in one place and reduces the number of direct interactions the user needs to have with the underlying services.

In plain words

> Aggregator Microservice collects pieces of data from various microservices and returns an aggregate for processing.

Stack Overflow says

> Aggregator Microservice invokes multiple services to achieve the functionality required by the application.

**Programmatic Example**

Our web marketplace needs information about products and their current inventory. It makes a call to an aggregator service, which, in turn, calls the product information and product inventory microservices, returning the combined information.

Let's start from the data model. Here's our `Product`.

```java
public class Product {
    private String title;
    private int productInventories;
    // Other properties and methods...
}
```

Next we can introduce our `Aggregator` microservice. It contains clients `ProductInformationClient` and `ProductInventoryClient` for calling respective microservices.

```java

@RestController
public class Aggregator {

    @Resource
    private ProductInformationClient informationClient;

    @Resource
    private ProductInventoryClient inventoryClient;

    @RequestMapping(path = "/product", method = RequestMethod.GET)
    public Product getProduct() {

        var product = new Product();
        var productTitle = informationClient.getProductTitle();
        var productInventory = inventoryClient.getProductInventories();

        //Fallback to error message
        product.setTitle(requireNonNullElse(productTitle, "Error: Fetching Product Title Failed"));

        //Fallback to default error inventory
        product.setProductInventories(requireNonNullElse(productInventory, -1));

        return product;
    }
}
```

Here's the essence of information microservice implementation. Inventory microservice is similar, it just returns inventory counts.

```java

@RestController
public class InformationController {
    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public String getProductTitle() {
        return "The Product Title.";
    }
}
```

Now calling our `Aggregator` REST API returns the product information.

```bash
# Example bash call
curl http://localhost:50004/product

# Example output
{"title":"The Product Title.","productInventories":5}
```

## Class diagram

![Class diagram of the Aggregator Microservices Pattern](./aggregator-service/etc/aggregator-service.png "Aggregator Microservice")

## Applicability

The Aggregator Microservices Design Pattern is particularly useful in scenarios where a client requires a composite response that is assembled from data provided by multiple microservices. Common use cases include e-commerce applications where product details, inventory, and reviews might be provided by separate services, or in dashboard applications where aggregated data from various services is displayed in a unified view.

## Consequences

Benefits:

* Simplified Client: Clients interact with just one service rather than managing calls to multiple microservices, which simplifies client-side logic.
* Reduced Latency: By aggregating responses, the number of network calls is reduced, which can improve the application's overall latency.
* Decoupling: Clients are decoupled from the individual microservices, allowing for more flexibility in changing the microservices landscape without impacting clients.
* Centralized Logic: Aggregation allows for centralized transformation and logic application on the data collected from various services, which can be more efficient than handling it in the client or spreading it across multiple services.

Trade-offs:

* Single Point of Failure: The aggregator service can become a bottleneck or a single point of failure if not designed with high availability and scalability in mind.
* Complexity: Implementing an aggregator can introduce complexity, especially in terms of data aggregation logic and error handling when dealing with multiple services.

## Related Patterns

* [API Gateway](https://java-design-patterns.com/patterns/api-gateway/): The Aggregator Microservices pattern is often used in conjunction with an API Gateway, which provides a single entry point for clients to access multiple microservices.
* [Composite](https://java-design-patterns.com/patterns/composite/): The Aggregator Microservices pattern can be seen as a form of the Composite pattern, where the composite is the aggregated response from multiple microservices.
* [Facade](https://java-design-patterns.com/patterns/facade/): The Aggregator Microservices pattern can be seen as a form of the Facade pattern, where the facade is the aggregator service that provides a simplified interface to the client.

## Credits

* [Building Microservices: Designing Fine-Grained Systems](https://amzn.to/43aGpSR)
* [Designing Data-Intensive Applications: The Big Ideas Behind Reliable, Scalable, and Maintainable Systems](https://amzn.to/3y6yv1z)
* [Designing Distributed Systems: Patterns and Paradigms for Scalable, Reliable Services](https://amzn.to/3T9g9Uj)
* [Microservice Architecture: Aligning Principles, Practices, and Culture](https://amzn.to/3T9jZNi)
* [Microservices Patterns: With examples in Java](https://amzn.to/4a5LHkP)
* [Production-Ready Microservices: Building Standardized Systems Across an Engineering Organization](https://amzn.to/4a0Vk4c)
* [Microservice Design Patterns (Arun Gupta)](http://web.archive.org/web/20190705163602/http://blog.arungupta.me/microservice-design-patterns/)
