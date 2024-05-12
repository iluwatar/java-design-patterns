---
title: Balking
category: Concurrency
language: fr
tag:
 - Decoupling
---

## Intention

Le pattron de conception Balking est utilisé pour empêcher un objet d'exécuter un certain code 
s'il est dans un état incomplet ou inapproprié.

## Explication

Exemple concret

> Sur une machine à laver, un bouton de démarrage permet de lancer le lavage du linge. Lorsque la machine à laver est inactive,
> le bouton fonctionne comme prévu, mais si la machine est déjà en train de laver, le bouton ne fait rien.

En clair

> En utilisant le pattron balking, un certain code ne s'exécute que si l'objet se trouve dans un état particulier.

Wikipedia dit

> Le pattron de conception balking est un pattron de conception de logiciel qui n'exécute une action sur un objet que
> lorsque l'objet se trouve dans un état particulier. Par exemple, si un objet lit des fichiers ZIP et qu'une méthode 
> appelante invoque une méthode get sur l'objet alors que le fichier ZIP n'est pas ouvert, l'objet rejette la demande.

**Exemple de programme**

Dans cet exemple d'implémentation, `WashingMachine` est un objet qui peut être dans deux états : ENABLED et WASHING.
Si la machine est ENABLED, l'état change en WASHING en utilisant une méthode thread-safe. D'autre part, si elle a déjà
été lavée et qu'un autre thread exécute `wash()`, elle ne le fera pas et terminera sans rien faire.

Voici les parties pertinentes de la classe `WashingMachine`

```java
@Slf4j
public class WashingMachine {

  private final DelayProvider delayProvider;
  private WashingMachineState washingMachineState;

  public WashingMachine(DelayProvider delayProvider) {
    this.delayProvider = delayProvider;
    this.washingMachineState = WashingMachineState.ENABLED;
  }

  public WashingMachineState getWashingMachineState() {
    return washingMachineState;
  }

  public void wash() {
    synchronized (this) {
      var machineState = getWashingMachineState();
      LOGGER.info("{}: Actual machine state: {}", Thread.currentThread().getName(), machineState);
      if (this.washingMachineState == WashingMachineState.WASHING) {
        LOGGER.error("Cannot wash if the machine has been already washing!");
        return;
      }
      this.washingMachineState = WashingMachineState.WASHING;
    }
    LOGGER.info("{}: Doing the washing", Thread.currentThread().getName());
    this.delayProvider.executeAfterDelay(50, TimeUnit.MILLISECONDS, this::endOfWashing);
  }

  public synchronized void endOfWashing() {
    washingMachineState = WashingMachineState.ENABLED;
    LOGGER.info("{}: Washing completed.", Thread.currentThread().getId());
  }
}
```

Voici l'interface simple `DelayProvider` utilisée par le `WashingMachine`.

```java
public interface DelayProvider {
  void executeAfterDelay(long interval, TimeUnit timeUnit, Runnable task);
}
```

Nous allons maintenant présenter l'application en utilisant le `WashingMachine`.

```java
  public static void main(String... args) {
    final var washingMachine = new WashingMachine();
    var executorService = Executors.newFixedThreadPool(3);
    for (int i = 0; i < 3; i++) {
      executorService.execute(washingMachine::wash);
    }
    executorService.shutdown();
    try {
      executorService.awaitTermination(10, TimeUnit.SECONDS);
    } catch (InterruptedException ie) {
      LOGGER.error("ERROR: Waiting on executor service shutdown!");
      Thread.currentThread().interrupt();
    }
  }
```

Voici la sortie de la console du programme.

```
14:02:52.268 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-2: Actual machine state: ENABLED
14:02:52.272 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-2: Doing the washing
14:02:52.272 [pool-1-thread-3] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-3: Actual machine state: WASHING
14:02:52.273 [pool-1-thread-3] ERROR com.iluwatar.balking.WashingMachine - Cannot wash if the machine has been already washing!
14:02:52.273 [pool-1-thread-1] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-1: Actual machine state: WASHING
14:02:52.273 [pool-1-thread-1] ERROR com.iluwatar.balking.WashingMachine - Cannot wash if the machine has been already washing!
14:02:52.324 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - 14: Washing completed.
```

## Diagramme de classe

![alt text](../../../balking/etc/balking.png "Balking")

## Application

Utilisé le pattron balking lorsque :

* Vous souhaitez invoquer une action sur un objet uniquement lorsqu'il se trouve dans un état spécifique
* Les objets ne sont généralement que dans un état susceptible d'évoluer temporairement, mais pour une durée inconnue.

## Pattron de conception apparentés

* [Guarded Suspension Pattern](https://java-design-patterns.com/patterns/guarded-suspension/)
* [Double Checked Locking Pattern](https://java-design-patterns.com/patterns/double-checked-locking/)

## Crédits

* [Patterns in Java: A Catalog of Reusable Design Patterns Illustrated with UML, 2nd Edition, Volume 1](https://www.amazon.com/gp/product/0471227293/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0471227293&linkId=0e39a59ffaab93fb476036fecb637b99)
