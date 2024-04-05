---
title: Mediator
category: Behavioral
language: es
tag:
 - Gang Of Four
 - Decoupling
---

## Propósito

Define un objeto que encapsula cómo interactúa un conjunto de objetos. Mediator fomenta el acoplamiento flexible al evitar que los objetos se refieran entre sí de forma explícita, y te permite variar su interacción de forma independiente.

## Explicación

Un ejemplo real

> Pícaro, mago, hobbit y cazador han decidido unir sus fuerzas y viajar en la misma party. Para evitar acoplar a cada miembro entre sí, utilizan la interfaz de la party para comunicarse entre ellos.

En pocas palabras

> Mediator desacopla un conjunto de clases forzando su flujo de comunicaciones a través de un objeto mediador.

Wikipedia dice

> En ingeniería de software, el patrón mediador define un objeto que encapsula cómo interactúa un conjunto de objetos. Este patrón se considera un patrón de comportamiento debido a la forma en que puede alterar el comportamiento de ejecución del programa. En la programación orientada a objetos, los programas suelen constar de muchas clases. La lógica de negocio y la computación se distribuyen entre estas clases. Sin embargo, a medida que se añaden más clases a un programa, especialmente durante el mantenimiento y/o la refactorización, el problema de la comunicación entre estas clases puede volverse más complejo. Esto hace que el programa sea más difícil de leer y mantener. Además, puede resultar difícil modificar el programa, ya que cualquier cambio puede afectar al código de otras clases. Con el patrón mediador, la comunicación entre objetos se encapsula en un objeto mediador. Los objetos ya no se comunican directamente entre sí, sino a través del mediador. Esto reduce las dependencias entre los objetos que se comunican, reduciendo así el acoplamiento.

**Ejemplo programático**

En este ejemplo, el mediador encapsula cómo interactúan un conjunto de objetos. En lugar de referirse unos a otros directamente, utilizan la interfaz del mediador.

Los miembros de la party `Rogue`, `Wizard`, `Hobbit`, y `Hunter` heredan del `PartyMemberBase` implementando la interfaz `PartyMember`.

```java
public interface PartyMember {

  void joinedParty(Party party);

  void partyAction(Action action);

  void act(Action action);
}

@Slf4j
public abstract class PartyMemberBase implements PartyMember {

  protected Party party;

  @Override
  public void joinedParty(Party party) {
    LOGGER.info("{} joins the party", this);
    this.party = party;
  }

  @Override
  public void partyAction(Action action) {
    LOGGER.info("{} {}", this, action.getDescription());
  }

  @Override
  public void act(Action action) {
    if (party != null) {
      LOGGER.info("{} {}", this, action);
      party.act(this, action);
    }
  }

  @Override
  public abstract String toString();
}

public class Rogue extends PartyMemberBase {

  @Override
  public String toString() {
    return "Rogue";
  }
}

// Wizard, Hobbit, and Hunter are implemented similarly
```

Nuestro sistema mediador consta de la interfaz `Party` y su implementación.

```java
public interface Party {

  void addMember(PartyMember member);

  void act(PartyMember actor, Action action);
}

public class PartyImpl implements Party {

  private final List<PartyMember> members;

  public PartyImpl() {
    members = new ArrayList<>();
  }

  @Override
  public void act(PartyMember actor, Action action) {
    for (var member : members) {
      if (!member.equals(actor)) {
        member.partyAction(action);
      }
    }
  }

  @Override
  public void addMember(PartyMember member) {
    members.add(member);
    member.joinedParty(this);
  }
}
```

Aquí tienes una demo que muestra el patrón mediador (Mediator) en acción.

```java
    // create party and members
    Party party = new PartyImpl();
    var hobbit = new Hobbit();
    var wizard = new Wizard();
    var rogue = new Rogue();
    var hunter = new Hunter();

    // add party members
    party.addMember(hobbit);
    party.addMember(wizard);
    party.addMember(rogue);
    party.addMember(hunter);

    // perform actions -> the other party members
    // are notified by the party
    hobbit.act(Action.ENEMY);
    wizard.act(Action.TALE);
    rogue.act(Action.GOLD);
    hunter.act(Action.HUNT);
```

Esta es la salida de la consola al ejecutar el ejemplo.

```
Hobbit joins the party
Wizard joins the party
Rogue joins the party
Hunter joins the party
Hobbit spotted enemies
Wizard runs for cover
Rogue runs for cover
Hunter runs for cover
Wizard tells a tale
Hobbit comes to listen
Rogue comes to listen
Hunter comes to listen
Rogue found gold
Hobbit takes his share of the gold
Wizard takes his share of the gold
Hunter takes his share of the gold
Hunter hunted a rabbit
Hobbit arrives for dinner
Wizard arrives for dinner
Rogue arrives for dinner
```

## Diagrama de clases

![alt text](./etc/mediator_1.png "Mediator")

## Aplicabilidad

Utilice el patrón Mediator cuando:

* Un conjunto de objetos se comunican de formas bien definidas pero complejas. Las interdependencias resultantes no están estructuradas y son difíciles de entender
* La reutilización de un objeto es difícil porque hace referencia y se comunica con muchos otros objetos.
* Un comportamiento que se distribuye entre varias clases debe ser personalizable sin muchas subclases.

## Usos conocidos

* Todos los métodos scheduleXXX() de [java.util.Timer](http://docs.oracle.com/javase/8/docs/api/java/util/Timer.html)
* [java.util.concurrent.Executor#execute()](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Executor.html#execute-java.lang.Runnable-)
* Métodos submit() e invokeXXX() de [java.util.concurrent.ExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html)
* Método scheduleXXX() de [java.util.concurrent.ScheduledExecutorService](http://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ScheduledExecutorService.html)
* [java.lang.reflect.Method#invoke()](http://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html#invoke-java.lang.Object-java.lang.Object...-)

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
