---
title: Active Object
category: Concurrency
language: fr
tag:
 - Performance
---


## Intention

Le patron de conception des "active object" actifs dissocie l'exécution et l'invocation des méthodes pour les objets qui résident chacun dans leur fil de contrôle. L'objectif est d'introduire la concurrence en utilisant l'invocation asynchrone des méthodes et un planificateur pour traiter les demandes.

## Explication

La classe qui met en œuvre le patron "active object", contiendra un mécanisme d'auto-synchronisation sans utiliser de méthodes "synchronisées".

**Exemple concret**

> Les orques sont connus pour leur caractère sauvage et leur âme indomptable. Il semble qu'ils aient leur propre système de contrôle basé sur leur comportement antérieur.

Pour mettre en œuvre une créature qui possède son propre mécanisme de contrôle et qui n'expose que son API et non l'exécution elle-même, nous pouvons utiliser le patron "active-object".


**Exemple de programme**

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

Nous pouvons constater que toute classe qui hérite de la classe ActiveCreature aura son propre fil de contrôle pour invoquer et exécuter les méthodes.

Par exemple, la classe Orc :

```java
public class Orc extends ActiveCreature {

  public Orc(String name) {
    super(name);
  }

}
```

Désormais, nous pouvons créer plusieurs créatures telles que des orques, leur dire de manger et d'errer, et elles s'exécuteront selon leur propre fil(mechanisme) de contrôle :

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

## Diagramme de classe

![alt text](../../../active-object/etc/active-object.urm.png "Active Object class diagram")

## Tutoriels

* [Android and Java Concurrency: The Active Object Pattern](https://www.youtube.com/watch?v=Cd8t2u5Qmvc)