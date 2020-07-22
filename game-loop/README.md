---  
layout: pattern  
title: Game Loop 
folder:  game-loop  
permalink: /patterns/game-loop/  
categories: Behavioral
tags:  
 - Game programming
---  
  
## Intent  
A game loop runs continuously during gameplay. Each turn of the loop, it processes user input without blocking, updates 
the game state, and renders the game. It tracks the passage of time to control the rate of gameplay.

This pattern decouples progression of game time from user input and processor speed.

## Applicability  
This pattern is used in every game engine. 

## Explanation
Real world example

> Game loop is the main process of all the game rendering threads. It's present in all modern games. It drives input process, internal status update, rendering, AI and all the other processes.

In plain words

> Game Loop pattern ensures that game time progresses in equal speed in all different hardware setups. 

Wikipedia says

> The central component of any game, from a programming standpoint, is the game loop. The game loop allows the game to run smoothly regardless of a user's input or lack thereof.

**Programmatic Example**

Let's start with something simple. Here's a bullet that will move in our game. For demonstration it's enough that it has 1-dimensional position.

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

GameController is responsible for moving objects in the game. Including the aforementioned bullet.

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

Now we introduce the game loop. Or actually in this demo we have 3 different game loops.

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
    gameThread = new Thread(() -> processGameLoop());
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

public class FixedStepGameLoop extends GameLoop {

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

Finally we can show all these game loops in action.

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

## Class diagram
![alt text](./etc/game-loop.urm.png "Game Loop pattern class diagram")

## Credits  
* [Game Programming Patterns - Game Loop](http://gameprogrammingpatterns.com/game-loop.html)
* [Game Programming Patterns](https://www.amazon.com/gp/product/0990582906/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0990582906&linkId=1289749a703b3fe0e24cd8d604d7c40b)
* [Game Engine Architecture, Third Edition](https://www.amazon.com/gp/product/1138035459/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1138035459&linkId=94502746617211bc40e0ef49d29333ac)
