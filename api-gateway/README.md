---
title: API Gateway
category: Architectural
language: en
tag:
  - API design
  - Cloud distributed
  - Decoupling
  - Microservices
---

## Intent

The API Gateway design pattern aims to provide a unified interface to a set of microservices. It acts as a single entry point for clients, routing requests to the appropriate microservices and aggregating results, thereby simplifying the client-side code.

## Also known as

* Backend for Frontends (BFF)

## Explanation

With the Microservices pattern, a client may need data from multiple different microservices. If the 
client called each microservice directly, that could contribute to longer load times, since the 
client would have to make a network request for each microservice called. Moreover, having the 
client call each microservice directly ties the client to that microservice - if the internal 
implementations of the microservices change (for example, if two microservices are combined sometime 
in the future) or if the location (host and port) of a microservice changes, then every client that 
makes use of those microservices must be updated.

The intent of the API Gateway pattern is to alleviate some of these issues. In the API Gateway 
pattern, an additional entity (the API Gateway) is placed between the client and the microservices. 
The job of the API Gateway is to aggregate the calls to the microservices. Rather than the client 
calling each microservice individually, the client calls the API Gateway a single time. The API 
Gateway then calls each of the microservices that the client needs.

Real world example

> We are implementing microservices and API Gateway pattern for an e-commerce site. In this system 
> the API Gateway makes calls to the Image and Price microservices.

In plain words

> For a system implemented using microservices architecture, API Gateway is the single entry point 
> that aggregates the calls to the individual microservices. 

Wikipedia says

> API Gateway is a server that acts as an API front-end, receives API requests, enforces throttling 
> and security policies, passes requests to the back-end service and then passes the response back 
> to the requester. A gateway often includes a transformation engine to orchestrate and modify the 
> requests and responses on the fly. A gateway can also provide functionality such as collecting 
> analytics data and providing caching. The gateway can provide functionality to support 
> authentication, authorization, security, audit and regulatory compliance.

**Programmatic Example**

This implementation shows what the API Gateway pattern could look like for an e-commerce site. The 
`ApiGateway` makes calls to the Image and Price microservices using the `ImageClientImpl` and 
`PriceClientImpl` respectively. Customers viewing the site on a desktop device can see both price 
information and an image of a product, so the `ApiGateway` calls both of the microservices and 
aggregates the data in the `DesktopProduct` model. However, mobile users only see price information; 
they do not see a product image. For mobile users, the `ApiGateway` only retrieves price 
information, which it uses to populate the `MobileProduct`.

Here's the Image microservice implementation.

```java
public interface ImageClient {
  String getImagePath();
}

public class ImageClientImpl implements ImageClient {
  @Override
  public String getImagePath() {
    var httpClient = HttpClient.newHttpClient();
    var httpGet = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create("http://localhost:50005/image-path"))
        .build();

    try {
      var httpResponse = httpClient.send(httpGet, BodyHandlers.ofString());
      return httpResponse.body();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    return null;
  }
}
```

Here's the Price microservice implementation.

```java
public interface PriceClient {
  String getPrice();
}

public class PriceClientImpl implements PriceClient {

  @Override
  public String getPrice() {
    var httpClient = HttpClient.newHttpClient();
    var httpGet = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create("http://localhost:50006/price"))
        .build();

    try {
      var httpResponse = httpClient.send(httpGet, BodyHandlers.ofString());
      return httpResponse.body();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }

    return null;
  }
}
```

Here we can see how API Gateway maps the requests to the microservices.

```java
public class ApiGateway {

  @Resource
  private ImageClient imageClient;

  @Resource
  private PriceClient priceClient;

  @RequestMapping(path = "/desktop", method = RequestMethod.GET)
  public DesktopProduct getProductDesktop() {
    var desktopProduct = new DesktopProduct();
    desktopProduct.setImagePath(imageClient.getImagePath());
    desktopProduct.setPrice(priceClient.getPrice());
    return desktopProduct;
  }

  @RequestMapping(path = "/mobile", method = RequestMethod.GET)
  public MobileProduct getProductMobile() {
    var mobileProduct = new MobileProduct();
    mobileProduct.setPrice(priceClient.getPrice());
    return mobileProduct;
  }
}
```

## Class diagram

![alt text](./etc/api-gateway.png "API Gateway")

## Applicability

* When building a microservices architecture, and there's a need to abstract the complexity of microservices from the client.
* When multiple microservices need to be consumed in a single request.
* For authentication, authorization, and security enforcement at a single point.
* To optimize communication between clients and services, especially in a cloud environment.

## Consequences

Benefits:

* Decouples client from microservices, allowing services to evolve independently.
* Simplifies client by aggregating requests to multiple services.
* Centralized location for cross-cutting concerns like security, logging, and rate limiting.
* Potential for performance optimizations like caching and request compression.

Trade-offs:

* Introduces a single point of failure, although this can be mitigated with high availability setups.
* Can become a bottleneck if not properly scaled.
* Adds complexity in terms of deployment and management.

## Known uses

* E-commerce platforms where multiple services (product info, pricing, inventory) are aggregated for a single view.
* Mobile applications that consume various backend services but require a simplified interface for ease of use.
* Cloud-native applications that leverage multiple microservices architectures.

## Related patterns

* [Aggregator Microservice](../aggregator-microservices/README.md) - The API Gateway pattern is often used in conjunction with the Aggregator Microservice pattern to provide a unified interface to a set of microservices.
* [Proxy](../proxy/README.md) - The API Gateway pattern is a specialized form of the Proxy pattern, where the gateway acts as a single entry point for clients, routing requests to the appropriate microservices and aggregating results.
* [Circuit Breaker](../circuit-breaker/README.md) - API Gateways can use the Circuit Breaker pattern to prevent cascading failures when calling multiple microservices.

## Tutorials

* [Exploring the New Spring Cloud Gateway](https://www.baeldung.com/spring-cloud-gateway)
* [Spring Cloud - Gateway](https://www.tutorialspoint.com/spring_cloud/spring_cloud_gateway.htm)
* [Getting Started With Spring Cloud Gateway](https://dzone.com/articles/getting-started-with-spring-cloud-gateway)

## Credits

* [microservices.io - API Gateway](http://microservices.io/patterns/apigateway.html)
* [NGINX - Building Microservices: Using an API Gateway](https://www.nginx.com/blog/building-microservices-using-an-api-gateway/)
* [Microservices Patterns: With examples in Java](https://www.amazon.com/gp/product/1617294543/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1617294543&linkId=ac7b6a57f866ac006a309d9086e8cfbd)
* [Building Microservices: Designing Fine-Grained Systems](https://www.amazon.com/gp/product/1491950358/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1491950358&linkId=4c95ca9831e05e3f0dadb08841d77bf1)
* [Designing Data-Intensive Applications](https://amzn.to/3PfRk7Y)
* [Cloud Native Patterns: Designing change-tolerant software](https://amzn.to/3uV12WN)
