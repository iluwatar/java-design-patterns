---
title: Update Method
category: Behavioral
language: en
tag:
    - Abstraction
    - Data processing
    - Decoupling
    - Event-driven
    - Game programming
    - Polymorphism
---

## Also known as

* Update Mechanism

## Intent

Update method pattern simulates a collection of independent objects by telling each to process one frame of behavior at a time.

## Explanation

Real-world example

> A real-world example of the Update Method design pattern is a weather monitoring system. In this system, multiple display devices (such as a mobile app, a website widget, and a wall-mounted digital display) need to show the current weather conditions. These displays subscribe to updates from a central weather station, which collects data from various sensors (temperature, humidity, wind speed, etc.). When the weather station detects new data, it triggers an update method that pushes the new information to all subscribed display devices, ensuring they all show the latest weather conditions simultaneously. This ensures that all displays are synchronized and updated without the need for each device to independently check for updates.

In plain words

> The Update Method design pattern processes system object behavior one frame at a time

gameprogrammingpatterns.com says

> The game world maintains a collection of objects. Each object implements an update method that simulates one frame of the object’s behavior. Each frame, the game updates every object in the collection.

**Programmatic Example**

The Update Method design pattern is a behavioral pattern that simulates a collection of independent objects by telling each to process one frame of behavior at a time. This pattern is commonly used in game development, where each object in the game world needs to be updated once per frame.

The `World` class represents the game world. It maintains a list of entities (`List<Entity> entities`) and a boolean flag (`isRunning`) to indicate whether the game is running.

```java
public class World {

  protected List<Entity> entities;
  protected volatile boolean isRunning;

  public World() {
    entities = new ArrayList<>();
    isRunning = false;
  }
  // ...
}
```

The `gameLoop` method is the main game loop. It continuously processes user input, updates the game state, and renders the next frame as long as the game is running.

```java
private void gameLoop() {
  while (isRunning) {
    processInput();
    update();
    render();
  }
}
```

The `processInput` method simulates handling user input. In this case, it simply introduces a random time lag to simulate real-life game situations.

```java
private void processInput() {
  try {
    int lag = new SecureRandom().nextInt(200) + 50;
    Thread.sleep(lag);
  } catch (InterruptedException e) {
    LOGGER.error(e.getMessage());
    Thread.currentThread().interrupt();
  }
}
```

The `update` method is where the Update Method pattern is implemented. It iterates over all entities in the game world and calls their `update` method, allowing each entity to process one frame of behavior.

```java
private void update() {
  for (var entity : entities) {
    entity.update();
  }
}
```

The `render` method is responsible for rendering the next frame. In this example, it does nothing as it's not related to the pattern.

```java
private void render() {
  // Does Nothing
}
```

The `run` and `stop` methods are used to start and stop the game loop.

```java
public void run() {
  LOGGER.info("Start game.");
  isRunning = true;
  var thread = new Thread(this::gameLoop);
  thread.start();
}

public void stop() {
  LOGGER.info("Stop game.");
  isRunning = false;
}
```

The `addEntity` method is used to add new entities to the game world.

```java
public void addEntity(Entity entity) {
  entities.add(entity);
}
```

In the `App` class, we can see how the `World` class and its methods are used to create a game world, add entities to it, and start the game loop.

```java
var world = new World();
var skeleton1 = new Skeleton(1, 10);
var skeleton2 = new Skeleton(2, 70);
var statue = new Statue(3, 20);
world.addEntity(skeleton1);
world.addEntity(skeleton2);
world.addEntity(statue);
world.run();
Thread.sleep(GAME_RUNNING_TIME);
world.stop();
```

This is a basic implementation of the Update Method pattern. In a real-world application, the `Entity` class would likely have additional methods and properties, and the `update` method would contain more complex logic to simulate the entity's behavior.

## Class diagram

![Update Method](./etc/update-method.urm.png "Update Method pattern class diagram")

## Applicability

Update Method works well when:

* The application has a number of objects or systems that need to run simultaneously.
* Each object’s behavior is mostly independent of the others.
* The objects need to be simulated over time.

## Known Uses

* Real-time games and data processing applications where world objects need to be updated once per frame.

## Consequences

Benefits:

* Each entity encapsulates its own behavior
* Makes it easy to add and remove entities
* Keeps the main loop uncluttered

Trade-offs:

* Increases complexity due to yielding control every frame
* The state needs to be stored to enable resuming updates after each frame
* Entities are simulated each frame, but they are not truly concurrent

## Related Patterns

* [Component](https://java-design-patterns.com/patterns/component/): Often used in game development to allow entities to be composed of various components, each potentially having its own update method.
* [Game Loop](https://java-design-patterns.com/patterns/game-loop/): Continuously updates game state and renders the game, which may include the Update Method for various game objects.

## Credits

* [Game Programming Patterns](https://amzn.to/3wLTbvr)
* [Game Programming Patterns - Update Method](http://gameprogrammingpatterns.com/update-method.html)
