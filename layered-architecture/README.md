---
title: Layered Architecture
category: Architectural
language: en
tag:
    - Abstraction
    - Decoupling
    - Enterprise patterns
    - Layered architecture
    - Scalability
---

## Also known as

* N-Tier Architecture

## Intent

The Layered Architecture pattern helps organize applications into groups of subtasks at different levels of abstraction, facilitating independent development and maintenance of each layer.

## Explanation

Real-world example

> Imagine constructing a modern high-rise building, which is analogous to using the Layered Architecture pattern in software development. Just as a building is divided into layers such as the foundation, structural floors, residential floors, and the rooftop, each with specific functions and built using different materials and techniques, a software application can be similarly structured.
> 
> In this analogy, the foundation represents the data layer, responsible for managing database operations. The structural floors are akin to the service layer, which contains business logic and rules. The residential floors parallel the presentation layer, which deals with user interfaces and interactions. Finally, the rooftop could be seen as the API layer, allowing external systems to communicate with the application. 
> 
> Just as each floor in a building is constructed to support the layers above and below, each software layer supports seamless interaction with its neighboring layers while maintaining a degree of independence. This structure allows for easy maintenance and updates, such as refurbishing the interiors (presentation layer) without affecting the underlying structures (business logic and data layers).

In plain words

> The Layered Architecture pattern organizes software into hierarchical groups of tasks, each encapsulated in distinct layers that interact with each other, facilitating ease of maintenance, scalability, and clear separation of concerns.

Wikipedia says

> In software engineering, multitier architecture (often referred to as n-tier architecture) or multilayered architecture is a client–server architecture in which presentation, application processing, and data management functions are physically separated.

**Programmatic Example**

On the data layer, we keep our cake building blocks. `Cake` consist of layers and topping.

```java
@Entity
public class Cake {
  @Id
  @GeneratedValue
  private Long id;
  @OneToOne(cascade = CascadeType.REMOVE)
  private CakeTopping topping;
  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
  private Set<CakeLayer> layers;
}
```

The service layer offers `CakeBakingService` for easy access to different aspects of cakes.

```java
public interface CakeBakingService {
  void bakeNewCake(CakeInfo cakeInfo) throws CakeBakingException;
  List<CakeInfo> getAllCakes();
  void saveNewTopping(CakeToppingInfo toppingInfo);
  List<CakeToppingInfo> getAvailableToppings();
  void saveNewLayer(CakeLayerInfo layerInfo);
  List<CakeLayerInfo> getAvailableLayers();
}
```

On the top we have our `View` responsible for rendering the cakes.

```java
public interface View {
  void render();
}
@Slf4j
public class CakeViewImpl implements View {
  private final CakeBakingService cakeBakingService;
  public CakeViewImpl(CakeBakingService cakeBakingService) {
    this.cakeBakingService = cakeBakingService;
  }
  public void render() {
    cakeBakingService.getAllCakes().forEach(cake -> LOGGER.info(cake.toString()));
  }
}
```

## Class diagram

![Layered Architecture](./etc/layers.png "Layered Architecture")

## Applicability

This pattern is suitable for structuring applications that can be divided into groups where each group has a specific role or responsibility. Common in enterprise applications, it simplifies dependencies, enhances maintainability, and supports scaling and technology stack segregation.

Use the Layers architecture when

* You want clearly divide software responsibilities into different parts of the program.
* You want to prevent a change from propagating throughout the application.
* You want to make your application more maintainable and testable.

## Known Uses

* Web applications where the presentation, business logic, and data access layers are distinctly separated.
* Enterprise systems where core functionalities are isolated from interface applications and databases.

## Consequences

Benefits

* Improved manageability with separation of concerns
* Easier to update or modify one layer without affecting others
* Promotes reuse of functionalities.

Trade-offs

* Potential performance overhead due to layer interaction
* Complexity in layer management
* Challenges in designing an effective layer distribution.

## Related Patterns

* [Model-View-Controller](https://java-design-patterns.com/patterns/model-view-controller/): Shares separation of concerns by dividing application into input, processing, and output. Layered Architecture often implements an MVC within its presentation layer.
* Service-Oriented Architecture (SOA): Both patterns emphasize modularization but SOA focuses more on distributed services that can be reused across different systems.

## Credits

* [Clean Architecture: A Craftsman's Guide to Software Structure and Design](https://amzn.to/3UoKkaR)
* [Java Design Pattern Essentials](https://amzn.to/4drLhHU)
* [Pattern-Oriented Software Architecture Volume 1: A System of Patterns](https://amzn.to/3xZ1ELU)
