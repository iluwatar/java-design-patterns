---  
title: Game Loop 
category: Behavioral
language: es
tag:  
 - Game programming
---  
  
## Propósito

Un bucle de juego se ejecuta continuamente durante la partida. En cada vuelta del bucle, procesa las entradas del usuario sin bloquearse, actualiza el estado del juego y lo renderiza. Realiza un seguimiento del paso del tiempo para controlar el ritmo de juego.

Este patrón desvincula la progresión del tiempo de juego de la entrada del usuario y de la velocidad del procesador.

## Aplicabilidad

Este patrón se utiliza en todos los motores de juego.

## Explicación

Ejemplo del mundo real

> El bucle de juego es el proceso principal de todos los hilos de renderizado del juego. Está presente en todos los juegos modernos. Controla el proceso de entrada, la actualización del estado interno, el renderizado, la IA y todos los demás procesos.

En pocas palabras

> El patrón de bucle de juego garantiza que el tiempo de juego progrese a la misma velocidad en todas las configuraciones de hardware diferentes.

Wikipedia dice

> El componente central de cualquier juego, desde el punto de vista de la programación, es el bucle de juego. El bucle de juego permite que el juego se ejecute sin problemas, independientemente de la entrada de un usuario, o la falta de ella.

**Ejemplo programático**

Empecemos con algo sencillo. Aquí está la clase `Bullet`. Las balas se moverán en nuestro juego. Para propósitos de demostración es suficiente que tenga una posición unidimensional.

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

El `GameController` es el responsable de mover los objetos del juego, incluida la mencionada bala.

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

Ahora introducimos el bucle de juego. O en realidad en esta demo tenemos 3 bucles de juego diferentes. Veamos primero la clase base `GameLoop`.

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

Aquí está la primera implementación del bucle de juego, `FrameBasedGameLoop`:

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

Por último, mostramos todos los bucles del juego en acción.

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

Salida del programa:

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

## Diagrama de clases

![alt text](./etc/game-loop.urm.png "Game Loop pattern class diagram")

## Créditos
  
* [Game Programming Patterns - Game Loop](http://gameprogrammingpatterns.com/game-loop.html)
* [Game Programming Patterns](https://www.amazon.com/gp/product/0990582906/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0990582906&linkId=1289749a703b3fe0e24cd8d604d7c40b)
* [Game Engine Architecture, Third Edition](https://www.amazon.com/gp/product/1138035459/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=1138035459&linkId=94502746617211bc40e0ef49d29333ac)
