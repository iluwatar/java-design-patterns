---
title: Active Object
category: Concurrency
language: es
tag:
 - Performance
---


## Propósito
El patrón de diseño de objeto activo desacopla la ejecución del método de la invocación del método para los objetos que residen en su propio hilo de control. El objetivo es introducir la concurrencia mediante el uso de la invocación de métodos asíncronos y un planificador para manejar solicitudes.

## Explicación

La clase que implementa el patrón de diseño de objeto activo contendrá un mecanismo de autosincronización sin utilizar métodos sincronizados (synchronized).

Ejemplo del mundo real

> Los orcos son conocidos por su salvajismo y filosofía de no hacer equipo. Basándonos en este comportamiento se podría decir que tienen su propio hilo de control.

Podemos usar el patrón Active Object para implementar una criatura que tiene su propio hilo de control y exponer su API pero no la ejecución como tal.


**Ejemplo Programático**

```java
public abstract class ActiveCreature{
  private final Logger logger = LoggerFactory.getLogger(ActiveCreature.class.getName());

  private BlockingQueue<Runnable> requests;
  
  private String name;
  
  private Thread thread;

  public ActiveCreature(String name) {
    this.name = name;
    this.requests = new LinkedBlockingQueue<Runnable>();
    thread = new Thread(new Runnable() {
        @Override
        public void run() {
          while (true) {
            try {
              requests.take().run();
            } catch (InterruptedException e) { 
              logger.error(e.getMessage());
            }
          }
        }
      }
    );
    thread.start();
  }
  
  public void eat() throws InterruptedException {
    requests.put(new Runnable() {
        @Override
        public void run() { 
          logger.info("{} is eating!",name());
          logger.info("{} has finished eating!",name());
        }
      }
    );
  }

  public void roam() throws InterruptedException {
    requests.put(new Runnable() {
        @Override
        public void run() { 
          logger.info("{} has started to roam the wastelands.",name());
        }
      }
    );
  }
  
  public String name() {
    return this.name;
  }
}
```

Podemos ver que cualquier clase que extienda de ActiveCreature tendrá su propio hilo de control para invocar y ejecutar métodos.

Por ejemplo, la clase Orc:

```java
public class Orc extends ActiveCreature {

  public Orc(String name) {
    super(name);
  }

}
```

Ahora podemos crear múltiples criaturas como los Orcos, diles que coman y deambulen y lo harán en su propio hilo de control:

```java
  public static void main(String[] args) {  
    var app = new App();
    app.run();
  }
  
  @Override
  public void run() {
    ActiveCreature creature;
    try {
      for (int i = 0;i < creatures;i++) {
        creature = new Orc(Orc.class.getSimpleName().toString() + i);
        creature.eat();
        creature.roam();
      }
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      logger.error(e.getMessage());
    }
    Runtime.getRuntime().exit(1);
  }
```

## Diagrama de clases

![alt text](./etc/active-object.urm.png "Active Object class diagram")

## Tutoriales

* [Android and Java Concurrency: The Active Object Pattern](https://www.youtube.com/watch?v=Cd8t2u5Qmvc)