---
title: Data Locality
category: Performance optimization
language: en
tag:
    - Caching
    - Data access
    - Game programming
    - Memory management
    - Performance
---

## Also known as

* Cache-Friendly Design
* Data-Oriented Design

## Intent

The Data Locality design pattern aims to minimize data access times and improve cache utilization by arranging data in memory to take advantage of spatial locality. This pattern is particularly useful in high-performance computing and game development where access speed is crucial.

## Explanation

Real-world example

> Consider a supermarket where items are arranged based on purchase patterns and categories for efficiency. Just like the Data Locality pattern organizes data in memory for quick access, the supermarket places frequently bought items together and in easily accessible areas. This layout minimizes the time shoppers spend searching for items, enhancing their shopping experience by ensuring that related and popular items are close at hand, much like how data locality improves cache utilization and reduces access latency in computing.

In plain words

> The Data Locality pattern organizes data in memory to reduce access times and improve performance by ensuring that data frequently accessed together is stored close together.

**Programmatic Example**

The Data Locality pattern is a design pattern that aims to improve performance by arranging data in memory to take advantage of spatial locality. This pattern is particularly useful in high-performance computing and game development where access speed is crucial.

In the data-locality module, the pattern is demonstrated using a game loop that processes a bunch of game entities. These entities are decomposed into different domains: AI, physics, and rendering.

The GameEntity class is the main class that represents a game entity. It contains an array of AiComponent, PhysicsComponent, and RenderComponent objects. These components represent different aspects of a game entity.

```java
public class GameEntity {
    private final AiComponent[] aiComponents;
    private final PhysicsComponent[] physicsComponents;
    private final RenderComponent[] renderComponents;
    // Other properties and methods...
}
```

The GameEntity class has a start method that initializes all the components.

```java
public void start() {
  for (int i = 0; i < numEntities; i++) {
    aiComponents[i] = new AiComponent();
    physicsComponents[i] = new PhysicsComponent();
    renderComponents[i] = new RenderComponent();
  }
}
```

The GameEntity class also has an update method that updates all the components. This method demonstrates the data locality pattern. Instead of updating all aspects of a single entity at a time (AI, physics, and rendering), it updates the same aspect (e.g., AI) for all entities first, then moves on to the next aspect (e.g., physics). This approach improves cache utilization because it's more likely that the data needed for the update is already in the cache.

```java
public void update() {
  for (int i = 0; i < numEntities; i++) {
    aiComponents[i].update();
  }
  for (int i = 0; i < numEntities; i++) {
    physicsComponents[i].update();
  }
  for (int i = 0; i < numEntities; i++) {
    renderComponents[i].update();
  }
}
```

The Application class contains the main method that creates a GameEntity object and starts the game loop.

```java
public class Application {
  public static void main(String[] args) {
    var gameEntity = new GameEntity(NUM_ENTITIES);
    gameEntity.start();
    gameEntity.update();
  }
}
```

The console output:

```
10:19:52.155 [main] INFO com.iluwatar.data.locality.Application -- Start Game Application using Data-Locality pattern
10:19:52.157 [main] INFO com.iluwatar.data.locality.game.GameEntity -- Init Game with #Entity : 5
10:19:52.158 [main] INFO com.iluwatar.data.locality.game.GameEntity -- Start Game
10:19:52.158 [main] INFO com.iluwatar.data.locality.game.component.manager.AiComponentManager -- Start AI Game Component
10:19:52.158 [main] INFO com.iluwatar.data.locality.game.component.manager.PhysicsComponentManager -- Start Physics Game Component 
10:19:52.159 [main] INFO com.iluwatar.data.locality.game.component.manager.RenderComponentManager -- Start Render Game Component 
10:19:52.159 [main] INFO com.iluwatar.data.locality.game.GameEntity -- Update Game Component
10:19:52.159 [main] INFO com.iluwatar.data.locality.game.component.manager.AiComponentManager -- Update AI Game Component
10:19:52.159 [main] INFO com.iluwatar.data.locality.game.component.AiComponent -- update AI component
10:19:52.159 [main] INFO com.iluwatar.data.locality.game.component.AiComponent -- update AI component
10:19:52.159 [main] INFO com.iluwatar.data.locality.game.component.AiComponent -- update AI component
10:19:52.159 [main] INFO com.iluwatar.data.locality.game.component.AiComponent -- update AI component
10:19:52.159 [main] INFO com.iluwatar.data.locality.game.component.AiComponent -- update AI component
10:19:52.159 [main] INFO com.iluwatar.data.locality.game.component.manager.PhysicsComponentManager -- Update Physics Game Component 
10:19:52.160 [main] INFO com.iluwatar.data.locality.game.component.PhysicsComponent -- Update physics component of game
10:19:52.160 [main] INFO com.iluwatar.data.locality.game.component.PhysicsComponent -- Update physics component of game
10:19:52.160 [main] INFO com.iluwatar.data.locality.game.component.PhysicsComponent -- Update physics component of game
10:19:52.160 [main] INFO com.iluwatar.data.locality.game.component.PhysicsComponent -- Update physics component of game
10:19:52.160 [main] INFO com.iluwatar.data.locality.game.component.PhysicsComponent -- Update physics component of game
10:19:52.160 [main] INFO com.iluwatar.data.locality.game.component.manager.RenderComponentManager -- Update Render Game Component 
10:19:52.160 [main] INFO com.iluwatar.data.locality.game.component.RenderComponent -- Render Component
10:19:52.160 [main] INFO com.iluwatar.data.locality.game.component.RenderComponent -- Render Component
10:19:52.160 [main] INFO com.iluwatar.data.locality.game.component.RenderComponent -- Render Component
10:19:52.160 [main] INFO com.iluwatar.data.locality.game.component.RenderComponent -- Render Component
10:19:52.160 [main] INFO com.iluwatar.data.locality.game.component.RenderComponent -- Render Component
```

In this way, the data-locality module demonstrates the Data Locality pattern. By updating all components of the same type together, it increases the likelihood that the data needed for the update is already in the cache, thereby improving performance.

## Class diagram

![Data Locality](./etc/data-locality.urm.png "Data Locality pattern class diagram")

## Applicability

This pattern is applicable in scenarios where large datasets are processed and performance is critical. It's particularly useful in:

* Game development for efficient rendering and physics calculations.
* High-performance computing tasks that require rapid access to large data sets.
* Real-time data processing systems where latency is a critical factor.

## Known Uses

* Game engines (e.g., Unity, Unreal Engine) to optimize entity and component data access.
* High-performance matrix libraries in scientific computing to optimize matrix operations.
* Real-time streaming data processing systems for efficient data manipulation and access.

## Consequences

Benefits:

* Improved Cache Utilization: By enhancing spatial locality, data frequently accessed together is stored close together in memory, improving cache hit rates.
* Reduced Access Latency: Minimizes the time taken to fetch data from memory, leading to performance improvements.
* Enhanced Performance: Overall system performance is improved due to reduced memory access times and increased efficiency in data processing.

Trade-offs:

* Complexity in Implementation: Managing data layout can add complexity to the system design and implementation.
* Maintenance Overhead: As data access patterns evolve, the layout may need to be re-evaluated, adding to the maintenance overhead.
* Less Flexibility: The tight coupling of data layout to access patterns can reduce flexibility in how data structures are used and evolved over time.

## Related Patterns

* [Flyweight](https://java-design-patterns.com/patterns/flyweight/): Can be used in conjunction with Data Locality to share data efficiently among multiple objects.
* [Object Pool](https://java-design-patterns.com/patterns/object-pool/): Often used together to manage a group of initialized objects that can be reused, further optimizing memory usage and access.
* [Iterator](https://java-design-patterns.com/patterns/iterator/): Facilitates navigation through a collection of data laid out with data locality in mind.

## Credits

* [Effective Java](https://amzn.to/4cGk2Jz)
* [Game Programming Patterns](https://amzn.to/3vK8c0d)
* [High-Performance Java Persistence](https://amzn.to/3TMc8Wd)
* [Java Performance: The Definitive Guide](https://amzn.to/3Ua392J)
* [Game Programming Patterns Optimization Patterns: Data Locality](http://gameprogrammingpatterns.com/data-locality.html)
