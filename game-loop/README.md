---
title: Game Loop 
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

## Intent

The Game Loop design pattern aims to facilitate the continuous execution of a game, where each loop cycle processes input, updates game state, and renders the game state to the screen, maintaining a smooth and interactive gaming experience.

## Explanation

Real world example

> Game loop is the main process of all the game rendering threads. It's present in all modern games. It drives input process, internal status update, rendering, AI and all the other processes.

In plain words

> Game Loop pattern ensures that game time progresses in equal speed in all different hardware setups. 

Wikipedia says

> The central component of any game, from a programming standpoint, is the game loop. The game loop allows the game to run smoothly regardless of a user's input, or lack thereof.

**Programmatic Example**

Let's start with something simple. Here's `Bullet` class. Bullets will move in our game. For demonstration purposes it's enough that it has 1-dimensional position.

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

Now we introduce the game loop. Or actually in this demo we have 3 different game loops. Let's see the base class `GameLoop` first.

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

Finally, we show all the game loops in action.

```java
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

## Class diagram

![alt text](./etc/game-loop.urm.png "Game Loop pattern class diagram")

## Applicability

The Game Loop pattern is applicable in real-time simulation and gaming where the state needs to be updated continuously and consistently in response to user inputs and other events.

## Known Uses

* Video games, both 2D and 3D, across various platforms.
* Real-time simulations that require a steady frame rate for updating logic and rendering.

## Consequences

Benefits:

* Ensures the game progresses smoothly and deterministically.
* Facilitates synchronization between the game state, user input, and screen rendering.
* Provides a clear structure for the game developers to manage game dynamics and timing.

Trade-offs:

* Can lead to performance issues if the loop is not well-managed, especially in resource-intensive updates or rendering.
* Difficulty in managing varying frame rates across different hardware.

## Related Patterns

* [State](https://java-design-patterns.com/patterns/state/): Often used within a game loop to manage different states of the game (e.g., menu, playing, paused). The relationship lies in managing the state-specific behavior and transitions smoothly within the game loop.
* [Observer](https://java-design-patterns.com/patterns/observer/): Useful in a game loop for event handling, where game entities can subscribe to and react to events (e.g., collision, scoring).

## Credits
  
* [Game Programming Patterns - Game Loop](http://gameprogrammingpatterns.com/game-loop.html)
* [Game Programming Patterns](https://www.amazon.com/gp/product/0990582906/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0990582906&linkId=1289749a703b3fe0e24cd8d604d7c40b)
* [Game Engine Architecture, Third Edition](https://www.amazon.com/gp/product/1138035459/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1138035459&linkId=94502746617211bc40e0ef49d29333ac)
* [Real-Time Collision Detection](https://amzn.to/3W9Jj8T)
