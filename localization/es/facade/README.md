---
title: Facade
shortTitle: Facade
category: Structural
language: es
tag:
  - Gang Of Four
  - Decoupling
---

## Propósito

Proporcionar una interfaz unificada a un conjunto de interfaces de un subsistema. La fachada define una interfaz que
facilita el uso del subsistema.

## Explicación

Un ejemplo real

> ¿Cómo funciona una mina de oro? "¡Bueno, los mineros bajan y sacan oro!" dices. Eso es lo que crees porque estás
> usando una interfaz simple que la mina de oro proporciona en el exterior, internamente tiene que hacer un montón de
> cosas para que suceda. Esta interfaz simple al subsistema complejo es una fachada.

En pocas palabras

> El patrón Facade proporciona una interfaz simplificada a un subsistema complejo.

Wikipedia dice

> Una fachada es un objeto que proporciona una interfaz simplificada a un cuerpo de código mayor, como una biblioteca de
> clases.

**Ejemplo programático**

Tomemos el ejemplo de la mina de oro. Aquí tenemos la jerarquía de los trabajadores enanos de la mina. Primero hay una
clase base `DwarvenMineWorker`:

```java

@Slf4j
public abstract class DwarvenMineWorker {

    public void goToSleep() {
        LOGGER.info("{} goes to sleep.", name());
    }

    public void wakeUp() {
        LOGGER.info("{} wakes up.", name());
    }

    public void goHome() {
        LOGGER.info("{} goes home.", name());
    }

    public void goToMine() {
        LOGGER.info("{} goes to the mine.", name());
    }

    private void action(Action action) {
        switch (action) {
            case GO_TO_SLEEP -> goToSleep();
            case WAKE_UP -> wakeUp();
            case GO_HOME -> goHome();
            case GO_TO_MINE -> goToMine();
            case WORK -> work();
            default -> LOGGER.info("Undefined action");
        }
    }

    public void action(Action... actions) {
        Arrays.stream(actions).forEach(this::action);
    }

    public abstract void work();

    public abstract String name();

    enum Action {
        GO_TO_SLEEP, WAKE_UP, GO_HOME, GO_TO_MINE, WORK
    }
}
```

Luego tenemos las clases concretas enanas `DwarvenTunnelDigger`, `DwarvenGoldDigger` y `DwarvenCartOperator`:

```java
@Slf4j
public class DwarvenTunnelDigger extends DwarvenMineWorker {

  @Override
  public void work() {
    LOGGER.info("{} creates another promising tunnel.", name());
  }

  @Override
  public String name() {
    return "Dwarven tunnel digger";
  }
}

@Slf4j
public class DwarvenGoldDigger extends DwarvenMineWorker {

  @Override
  public void work() {
    LOGGER.info("{} digs for gold.", name());
  }

  @Override
  public String name() {
    return "Dwarf gold digger";
  }
}

@Slf4j
public class DwarvenCartOperator extends DwarvenMineWorker {

  @Override
  public void work() {
    LOGGER.info("{} moves gold chunks out of the mine.", name());
  }

  @Override
  public String name() {
    return "Dwarf cart operator";
  }
}

```

Para manejar a todos estos trabajadores de la mina de oro tenemos la `FachadaDwarvenGoldmine`:

```java
public class DwarvenGoldmineFacade {

  private final List<DwarvenMineWorker> workers;

  public DwarvenGoldmineFacade() {
      workers = List.of(
            new DwarvenGoldDigger(),
            new DwarvenCartOperator(),
            new DwarvenTunnelDigger());
  }

  public void startNewDay() {
    makeActions(workers, DwarvenMineWorker.Action.WAKE_UP, DwarvenMineWorker.Action.GO_TO_MINE);
  }

  public void digOutGold() {
    makeActions(workers, DwarvenMineWorker.Action.WORK);
  }

  public void endDay() {
    makeActions(workers, DwarvenMineWorker.Action.GO_HOME, DwarvenMineWorker.Action.GO_TO_SLEEP);
  }

  private static void makeActions(Collection<DwarvenMineWorker> workers,
      DwarvenMineWorker.Action... actions) {
    workers.forEach(worker -> worker.action(actions));
  }
}
```

Ahora vamos a utilizar la fachada:

```java
var facade = new DwarvenGoldmineFacade();
facade.startNewDay();
facade.digOutGold();
facade.endDay();
```

Salida del programa:

```java
// Dwarf gold digger wakes up.
// Dwarf gold digger goes to the mine.
// Dwarf cart operator wakes up.
// Dwarf cart operator goes to the mine.
// Dwarven tunnel digger wakes up.
// Dwarven tunnel digger goes to the mine.
// Dwarf gold digger digs for gold.
// Dwarf cart operator moves gold chunks out of the mine.
// Dwarven tunnel digger creates another promising tunnel.
// Dwarf gold digger goes home.
// Dwarf gold digger goes to sleep.
// Dwarf cart operator goes home.
// Dwarf cart operator goes to sleep.
// Dwarven tunnel digger goes home.
// Dwarven tunnel digger goes to sleep.
```

## Diagrama de clases

![alt text](./etc/facade.urm.png "Facade pattern class diagram")

## Aplicabilidad

Utilice el patrón Fachada cuando

* Quieres proporcionar una interfaz sencilla a un subsistema complejo. Los subsistemas a menudo se vuelven más complejos
  a medida que evolucionan. La mayoría de los patrones, cuando se aplican, dan como resultado más clases y más pequeñas.
  Esto hace que el subsistema sea más reutilizable y más fácil de personalizar, pero también se vuelve más difícil de
  usar para los clientes que no necesitan personalizarlo. Una fachada puede proporcionar una simple vista por defecto
  del subsistema que es lo suficiente para la mayoría de los clientes. Sólo los clientes que necesiten más
  personalización tendrán que mirar más allá de la fachada.
* Hay muchas dependencias entre los clientes y las clases de implementación de una abstracción.
  Introducir una fachada para desacoplar el subsistema de los clientes y otros subsistemas, promoviendo así
  independencia y portabilidad del subsistema.
* Quieres estratificar tus subsistemas. Utiliza una fachada para definir un punto de entrada a cada nivel de subsistema.
  Si los subsistemas son dependientes, puedes simplificar las dependencias entre ellos haciéndolos que se comuniquen
  entre sí únicamente a través de sus fachadas.

## Tutoriales

*[DigitalOcean](https://www.digitalocean.com/community/tutorials/facade-design-pattern-in-java)

* [Refactoring Guru](https://refactoring.guru/design-patterns/facade)
* [GeekforGeeks](https://www.geeksforgeeks.org/facade-design-pattern-introduction/)
* [Tutorialspoint](https://www.tutorialspoint.com/design_pattern/facade_pattern.htm)

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
