---
title: "Microservices Distributed Tracing Pattern: Enhancing Visibility in Service Communication"
shortTitle: Distributed Tracing in Microservices
description: "Learn how the Distributed Tracing pattern enhances visibility into service communication across microservices. Discover its benefits, implementation examples, and best practices."
category: Integration
language: en
tag:
  - Distributed tracing
  - Microservices architecture
  - Service communication
  - Performance monitoring
  - Scalability
  - Observability
---

## Intent of Microservices Distributed Tracing Design Pattern

Distributed tracing aims to monitor and track requests as they flow through different services in a microservices architecture, providing insights into performance, dependencies, and failures.

## Also known as

* Distributed Request Tracing
* End-to-End Tracing

## Detailed Explanation of Microservices Distributed Tracing Pattern with Real-World Examples

Real-world example

> In an e-commerce platform, distributed tracing is used to track a customer's request from the moment they add an item to the cart until the order is processed and shipped. This helps in identifying bottlenecks, errors, and latency issues across different services.

In plain words

> Distributed tracing allows you to follow a request's journey through all the services it interacts with, providing insights into system performance and aiding in debugging.

Wikipedia says

> Tracing in software engineering refers to the process of capturing and recording information about the execution of a software program. This information is typically used by programmers for debugging purposes, and additionally, depending on the type and detail of information contained in a trace log, by experienced system administrators or technical-support personnel and by software monitoring tools to diagnose common problems with software.

## Programmatic Example of Microservices Distributed Tracing in Java


This implementation shows how an e-commerce platform's `OrderService` interacts with both `PaymentService` and `ProductService`. When a customer places an order, the `OrderService` calls the `PaymentService` to process the payment and the `ProductService` to check the product inventory. Distributed tracing logs are generated for each of these interactions and can be viewed in the Zipkin interface to monitor the flow and performance of requests across these services.

Here's the `Order microservice` implementation.

```java
@Slf4j
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }
    
    @PostMapping("/order")
    public ResponseEntity<String> processOrder(@RequestBody(required = false) String request) {
        LOGGER.info("Received order request: {}", request);
        var result = orderService.processOrder();
        LOGGER.info("Order processed result: {}", result);
        return ResponseEntity.ok(result);
    }
}
```
```java
@Slf4j
@Service
public class OrderService {

    private final RestTemplateBuilder restTemplateBuilder;

    public OrderService(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public String processOrder() {
        if (validateProduct() && processPayment()) {
            return "Order processed successfully";
        }
        return "Order processing failed";
    }
    
    Boolean validateProduct() {
        try {
            ResponseEntity<Boolean> productValidationResult = restTemplateBuilder
                    .build()
                    .postForEntity("http://localhost:30302/product/validate", "validating product",
                            Boolean.class);
            LOGGER.info("Product validation result: {}", productValidationResult.getBody());
            return productValidationResult.getBody();
        } catch (ResourceAccessException | HttpClientErrorException e) {
            LOGGER.error("Error communicating with product service: {}", e.getMessage());
            return false;
        }
    }

    Boolean processPayment() {
        try {
            ResponseEntity<Boolean> paymentProcessResult = restTemplateBuilder
                    .build()
                    .postForEntity("http://localhost:30301/payment/process", "processing payment",
                            Boolean.class);
            LOGGER.info("Payment processing result: {}", paymentProcessResult.getBody());
            return paymentProcessResult.getBody();
        } catch (ResourceAccessException | HttpClientErrorException e) {
            LOGGER.error("Error communicating with payment service: {}", e.getMessage());
            return false;
        }
    }
}
```

Here's the `Payment microservice` implementation.

```java

@Slf4j
@RestController
public class PaymentController {

    @PostMapping("/payment/process")
    public ResponseEntity<Boolean> payment(@RequestBody(required = false) String request) {
        LOGGER.info("Received payment request: {}", request);
        boolean result = true;
        LOGGER.info("Payment result: {}", result);
        return ResponseEntity.ok(result);
    }
}
```

Here's the `Product microservice` implementation.

```java
/**
 * Controller for handling product validation requests.
 */
@Slf4j
@RestController
public class ProductController {

    /**
     * Validates the product based on the request.
     *
     * @param request the request body containing product information (can be null)
     * @return ResponseEntity containing the validation result (true)
     */
    @PostMapping("/product/validate")
    public ResponseEntity<Boolean> validateProduct(@RequestBody(required = false) String request) {
        LOGGER.info("Received product validation request: {}", request);
        boolean result = true;
        LOGGER.info("Product validation result: {}", result);
        return ResponseEntity.ok(result);
    }
}
```

## When to Use the Microservices Distributed Tracing Pattern in Java

* When you have a microservices architecture and need to monitor the flow of requests across multiple services.
* When troubleshooting performance issues or errors in a distributed system.
* When you need to gain insights into system bottlenecks and optimize overall performance.


## Microservices Distributed Tracing Pattern Java Tutorials

* [Spring Boot - Tracing (Spring)](https://docs.spring.io/spring-boot/reference/actuator/tracing.html)
* [Reactive Observability (Spring Academy)](https://spring.academy/guides/microservices-observability-reactive-spring-boot-3)
* [Spring Cloud – Tracing Services with Zipkin (Baeldung)](https://dzone.com/articles/getting-started-with-spring-cloud-gateway)

## Benefits and Trade-offs of Microservices Distributed Tracing Pattern

Benefits:

* Provides end-to-end visibility into requests.
* Helps in identifying performance bottlenecks.
* Aids in debugging and troubleshooting complex systems.

Trade-offs:

* Adds overhead to each request due to tracing data.
* Requires additional infrastructure (e.g., Zipkin, Jaeger) for collecting and visualizing traces.
* Can become complex to manage in large-scale systems.

## Real-World Applications of Microservices Distributed Tracing Pattern in Java

* Monitoring and troubleshooting e-commerce platforms.
*  Performance monitoring in financial transaction systems.
*  Observability in large-scale SaaS applications.

## Related Java Design Patterns

* [Log Aggregation Microservice](https://java-design-patterns.com/patterns/microservices-log-aggregation/) - Distributed tracing works well in conjunction with log aggregation to provide comprehensive observability and troubleshooting capabilities.
* [Circuit Breaker](https://java-design-patterns.com/patterns/circuit-breaker/) - Distributed tracing can be used alongside the Circuit Breaker pattern to monitor and handle failures gracefully, preventing cascading failures in microservices.
* [API Gateway Microservice](https://java-design-patterns.com/patterns/microservices-api-gateway/) - The API Gateway pattern can be integrated with distributed tracing to provide a single entry point for tracing requests across multiple microservices.

## References and Credits

* [Building Microservices](https://amzn.to/3UACtrU)
* [OpenTelemetry Documentation](https://opentelemetry.io/docs/)
* [Distributed tracing (microservices.io)](https://microservices.io/patterns/observability/distributed-tracing.html)
