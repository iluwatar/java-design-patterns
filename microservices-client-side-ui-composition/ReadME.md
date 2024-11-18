---
title: "Client-Side UI Composition Pattern: Assembling Modular UIs in Microservices Architecture"
shortTitle: Client-Side UI Composition Pattern
description: "Learn how the Client-Side UI Composition pattern allows the assembly of modular UIs on the client side, enabling independent teams to develop, deploy, and scale UI components in a microservices architecture. Discover the benefits, implementation examples, and best practices."
category: User Interface
language: en
tag:
  - Micro Frontends
  - API Gateway
  - Asynchronous Data Fetching
  - UI Integration
  - Microservices Architecture
  - Scalability
---

## **Intent of Client-Side UI Composition Design Pattern**

The Client-Side UI Composition Pattern allows the assembly of UIs on the client side by composing independent UI components (Micro Frontends). Each component is developed, tested, and deployed independently by separate teams, ensuring flexibility and scalability in microservices architecture.

---

## **Also Known As**

- Micro Frontends
- Modular UI Assembly
- Client-Side Integration

---

## **Detailed Explanation of Client-Side UI Composition Pattern with Real-World Examples**

### **Real-world Example**
> In a SaaS dashboard, a client-side composition pattern enables various independent modules like “Billing,” “Reports,” and “Account Settings” to be developed and deployed by separate teams. These modules are composed into a unified interface for the user, with each module independently fetching data from its respective microservice.

### **In Plain Words**
> The Client-Side UI Composition pattern breaks down the user interface into smaller, independent parts that can be developed, maintained, and scaled separately by different teams.

### **Wikipedia says**
>UI composition refers to the practice of building a user interface from modular components, each responsible for fetching its own data and rendering its own content. This approach enables faster development cycles, easier maintenance, and better scalability in large systems.
---

## **Programmatic Example of Client-Side UI Composition in JavaScript**

In this example, an e-commerce platform composes its frontend by integrating three independent modules: **CartService**, **ProductService**, and **OrderService**. Each module is served by a microservice and fetched on the client side through an API Gateway.

`ApiGateway` Implementation

```java
public class ApiGateway {

    private final Map<String, FrontendComponent> routes = new HashMap<>();

    public void registerRoute(String path, FrontendComponent component) {
        routes.put(path, component);
    }

    public String handleRequest(String path, Map<String, String> params) {
        if (routes.containsKey(path)) {
            return routes.get(path).fetchData(params);
        } else {
            return "404 Not Found";
        }
    }
}

```

`FrontendComponent` Implementation
```java
public interface FrontendComponent {
    String fetchData(Map<String, String> params);
}
```
## Example components
```java
public class ProductComponent implements FrontendComponent {
    @Override
    public String fetchData(Map<String, String> params) {
        return "Displaying Products: " + params.getOrDefault("category", "all");
    }
}

public class CartComponent implements FrontendComponent {
    @Override
    public String fetchData(Map<String, String> params) {
        return "Displaying Cart for User: " + params.getOrDefault("userId", "unknown");
    }
}
```
This approach dynamically assembles different UI components based on the route provided in the client-side request. Each component fetches its data asynchronously and renders it within the main interface.

---

## **When to Use the Client-Side UI Composition Pattern**

- When you have a microservices architecture where multiple teams develop different parts of the frontend.
- When you need to scale and deploy different UI modules independently.
- When you want to integrate multiple data sources or services into a cohesive frontend.

---

## **Client-Side UI Composition Pattern JavaScript Tutorials**

- [Micro Frontends in Action (O'Reilly)](https://www.oreilly.com/library/view/micro-frontends-in/9781617296873/)
- [Micro Frontends with React (ThoughtWorks)](https://www.thoughtworks.com/insights/articles/building-micro-frontends-using-react)
- [API Gateway in Microservices (Spring Cloud)](https://spring.io/guides/gs/gateway/)

---

## **Benefits and Trade-offs of Client-Side UI Composition Pattern**

### **Benefits**:
- **Modularity**: Each UI component is independent and can be developed by separate teams.
- **Scalability**: Micro Frontends allow for independent deployment and scaling of each component.
- **Flexibility**: Teams can choose different technologies to build components, offering flexibility in development.
- **Asynchronous Data Fetching**: Components can load data individually, improving performance.

### **Trade-offs**:
- **Increased Complexity**: Managing multiple micro frontends can increase overall system complexity.
- **Client-Side Performance**: Depending on the number of micro frontends, it may introduce a performance overhead due to multiple asynchronous requests.
- **Integration Overhead**: Client-side integration logic can become complex as the number of components grows.

---

## **Related Design Patterns**

- [Microservices API Gateway Pattern](https://java-design-patterns.com/patterns/microservices-api-gateway/) – API Gateway serves as a routing mechanism for client-side UI requests.
- [Backend for Frontend (BFF)](https://microservices.io/patterns/apigateway.html) – BFF pattern helps build custom backends for different UI experiences.

---

## **References and Credits**

- [Micro Frontends Architecture (Microfrontends.org)](https://micro-frontends.org/)
- [Building Microservices with Micro Frontends](https://martinfowler.com/articles/micro-frontends.html)
- [Client-Side UI Composition (Microservices.io)](https://microservices.io/patterns/client-side-ui-composition.html)
