---
title: "Game Loop Pattern in Java: Mastering Smooth Game Mechanics"
shortTitle: Game Loop
description: "Learn about the Game Loop design pattern, its implementation in Java, and how it ensures smooth gameplay by continuously updating game state, processing inputs, and rendering. Ideal for real-time simulations and gaming."
category: Behavioral
language: en
tag:
  - Concurrency
  - Event-driven
  - Game programming
  - Performance
---

## Also known as

* Game Cycle
* Main Game Loop

## Intent of Game Loop Design Pattern

The Game Loop design pattern is essential for creating smooth and interactive gaming experiences by facilitating continuous game execution. Each loop cycle processes input, updates the game state, and renders the game state to the screen, ensuring consistent performance across all hardware setups.

## Detailed Explanation of Game Loop Pattern with Real-World Examples

Real-world example

> A practical analogy of the Game Loop can be seen in an amusement park ride, like a roller coaster. Similar to how the ride operates in a loop, updating its state and ensuring smooth operation, the Game Loop continuously processes inputs and updates the game state for a seamless gaming experience. The roller coaster operates in a continuous loop, where the state of the ride (the position and speed of the coaster) is continuously updated while the ride is running. The control system of the roller coaster ensures that the cars move smoothly along the track, adjusting speeds, and handling the ride's safety systems in real-time. Just like the game loop, this control system repeatedly processes inputs (such as the current speed and position), updates the state, and triggers outputs (like adjusting the brakes or accelerating the cars) to maintain the desired operation throughout the duration of the ride.

In plain words

> Game Loop pattern ensures that game time progresses in equal speed in all different hardware setups. 

Wikipedia says

> The central component of any game, from a programming standpoint, is the game loop. The game loop allows the game to run smoothly regardless of a user's input, or lack thereof.

## Programmatic Example of Game Loop Pattern in Java

In our Java example, we illustrate a simple game loop controlling a bullet's movement, updating its position, ensuring smooth rendering, and responding to user inputs. The Game Loop is the main process driving all game rendering threads, present in all modern games. It handles input processing, internal status updates, rendering, AI, and other processes. Starting with a simple `Bullet` class, we demonstrate the movement of bullets in our game, focusing on their 1-dimensional position for demonstration purposes.

```java
public class Bullet {

  private float position;

  public Bullet() {
    position = 0.0f;
  }

  public float getPosition() {
    return position;
  }

  public void setPosition(float position) {
    this.position = position;
  }
}
```

`GameController` is responsible for moving objects in the game, including the aforementioned bullet.

```java
public class GameController {

  protected final Bullet bullet;

  public GameController() {
    bullet = new Bullet();
  }

  public void moveBullet(float offset) {
    var currentPosition = bullet.getPosition();
    bullet.setPosition(currentPosition + offset);
  }

  public float getBulletPosition() {
    return bullet.getPosition();
  }
}
```

Now we introduce the game loop. Actually, in this demo we have 3 different game loops. Let's see the base class `GameLoop` first.

```java
public enum GameStatus {

  RUNNING, STOPPED
}

public abstract class GameLoop {

  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  protected volatile GameStatus status;

  protected GameController controller;

  private Thread gameThread;

  public GameLoop() {
    controller = new GameController();
    status = GameStatus.STOPPED;
  }

  public void run() {
    status = GameStatus.RUNNING;
    gameThread = new Thread(this::processGameLoop);
    gameThread.start();
  }

  public void stop() {
    status = GameStatus.STOPPED;
  }

  public boolean isGameRunning() {
    return status == GameStatus.RUNNING;
  }

  protected void processInput() {
    try {
      var lag = new Random().nextInt(200) + 50;
      Thread.sleep(lag);
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
    }
  }

  protected void render() {
    var position = controller.getBulletPosition();
    logger.info("Current bullet position: " + position);
  }

  protected abstract void processGameLoop();
}
```

Here's the first game loop implementation, `FrameBasedGameLoop`:

```java
public class FrameBasedGameLoop extends GameLoop {

  @Override
  protected void processGameLoop() {
    while (isGameRunning()) {
      processInput();
      update();
      render();
    }
  }

  protected void update() {
    controller.moveBullet(0.5f);
  }
}
```

Here's the second game loop implementation, `FixedStepGameLoop`:

```java
public class FixedStepGameLoop extends GameLoop {

  /**
   * 20 ms per frame = 50 FPS.
   */
  private static final long MS_PER_FRAME = 20;

  @Override
  protected void processGameLoop() {
    var previousTime = System.currentTimeMillis();
    var lag = 0L;
    while (isGameRunning()) {
      var currentTime = System.currentTimeMillis();
      var elapsedTime = currentTime - previousTime;
      previousTime = currentTime;
      lag += elapsedTime;

      processInput();

      while (lag >= MS_PER_FRAME) {
        update();
        lag -= MS_PER_FRAME;
      }

      render();
    }
  }

  protected void update() {
    controller.moveBullet(0.5f * MS_PER_FRAME / 1000);
  }
}
```

And the third game loop implementation, `VariableStepGameLoop`:

```java
public class VariableStepGameLoop extends GameLoop {

  @Override
  protected void processGameLoop() {
    var lastFrameTime = System.currentTimeMillis();
    while (isGameRunning()) {
      processInput();
      var currentFrameTime = System.currentTimeMillis();
      var elapsedTime = currentFrameTime - lastFrameTime;
      update(elapsedTime);
      lastFrameTime = currentFrameTime;
      render();
    }
  }

  protected void update(Long elapsedTime) {
    controller.moveBullet(0.5f * elapsedTime / 1000);
  }

}
```

Finally, we show all the game loops in action.

```java
public static void main(String[] args) {

    try {
        LOGGER.info("Start frame-based game loop:");
        var frameBasedGameLoop = new FrameBasedGameLoop();
        frameBasedGameLoop.run();
        Thread.sleep(GAME_LOOP_DURATION_TIME);
        frameBasedGameLoop.stop();
        LOGGER.info("Stop frame-based game loop.");

        LOGGER.info("Start variable-step game loop:");
        var variableStepGameLoop = new VariableStepGameLoop();
        variableStepGameLoop.run();
        Thread.sleep(GAME_LOOP_DURATION_TIME);
        variableStepGameLoop.stop();
        LOGGER.info("Stop variable-step game loop.");

        LOGGER.info("Start fixed-step game loop:");
        var fixedStepGameLoop = new FixedStepGameLoop();
        fixedStepGameLoop.run();
        Thread.sleep(GAME_LOOP_DURATION_TIME);
        fixedStepGameLoop.stop();
        LOGGER.info("Stop variable-step game loop.");

    } catch (InterruptedException e) {
        LOGGER.error(e.getMessage());
    }
}
```

Program output:

```java
Start frame-based game loop:
Current bullet position: 0.5
Current bullet position: 1.0
Current bullet position: 1.5
Current bullet position: 2.0
Current bullet position: 2.5
Current bullet position: 3.0
Current bullet position: 3.5
Current bullet position: 4.0
Current bullet position: 4.5
Current bullet position: 5.0
Current bullet position: 5.5
Current bullet position: 6.0
Stop frame-based game loop.
Start variable-step game loop:
Current bullet position: 6.5
Current bullet position: 0.038
Current bullet position: 0.084
Current bullet position: 0.145
Current bullet position: 0.1805
Current bullet position: 0.28
Current bullet position: 0.32
Current bullet position: 0.42549998
Current bullet position: 0.52849996
Current bullet position: 0.57799995
Current bullet position: 0.63199997
Current bullet position: 0.672
Current bullet position: 0.778
Current bullet position: 0.848
Current bullet position: 0.8955
Current bullet position: 0.9635
Stop variable-step game loop.
Start fixed-step game loop:
Current bullet position: 0.0
Current bullet position: 1.086
Current bullet position: 0.059999995
Current bullet position: 0.12999998
Current bullet position: 0.24000004
Current bullet position: 0.33999994
Current bullet position: 0.36999992
Current bullet position: 0.43999985
Current bullet position: 0.5399998
Current bullet position: 0.65999967
Current bullet position: 0.68999964
Current bullet position: 0.7299996
Current bullet position: 0.79999954
Current bullet position: 0.89999944
Current bullet position: 0.98999935
Stop variable-step game loop.
```

## When to Use the Game Loop Pattern in Java

The Game Loop pattern is perfect for real-time simulations and gaming where continuous state updates and smooth frame rates are critical.

## Real-World Applications of Game Loop Pattern in Java

* Video games, both 2D and 3D, across various platforms.
* Real-time simulations that require a steady frame rate for updating logic and rendering.

## Benefits and Trade-offs of Game Loop Pattern

Benefits:

* Ensures the game progresses smoothly and deterministically.
* Facilitates synchronization between the game state, user input, and screen rendering.
* Provides a clear structure for the game developers to manage game dynamics and timing.

Trade-offs:

* Can lead to performance issues if the loop is not well-managed, especially in resource-intensive updates or rendering.
* Difficulty in managing varying frame rates across different hardware.

## Related Java Design Patterns

* [State](https://java-design-patterns.com/patterns/state/): Often used within a game loop to manage different states of the game (e.g., menu, playing, paused). The relationship lies in managing the state-specific behavior and transitions smoothly within the game loop.
* [Observer](https://java-design-patterns.com/patterns/observer/): Useful in a game loop for event handling, where game entities can subscribe to and react to events (e.g., collision, scoring).

## References and Credits

* [Game Programming Patterns](https://amzn.to/3K96fOn)
* [Game Engine Architecture, Third Edition](https://amzn.to/3VgB4av)
* [Real-Time Collision Detection](https://amzn.to/3W9Jj8T)
* [Game Programming Patterns - Game Loop](http://gameprogrammingpatterns.com/game-loop.html)
