---
layout : pattern
titre : active-object
dossier : active-object
permalien : /patterns/active-object/
catégories : Concurrence
langue : fr
tags :
- Performance
---


## Objectif
Le modèle de conception d'objets actifs dissocie l'exécution des méthodes de l'invocation des méthodes pour les objets qui résident chacun dans leur thread de contrôle. L'objectif est d'introduire la concurrence, en utilisant l'invocation de méthodes asynchrones et un planificateur pour le traitement des demandes.

## Explication

La classe qui implémente le modèle d'objet actif contient un mécanisme d'auto-synchronisation sans utiliser de méthodes "synchronisées".

Exemple concret

>Les Orques sont connus pour leur sauvagerie et leur âme indomptable. Il semble qu'ils aient leur propre fil de contrôle basé sur leur comportement antérieur.

Pour implémenter une créature qui possède son propre mécanisme de fil de contrôle et exposer uniquement son API et non l'exécution elle-même, nous pouvons utiliser le modèle Active Object.


**Exemple programmatique**

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
          logger.info("{} has started to roam and the wastelands.",name());
        }
      }
    );
  }
  
  public String name() {
    return this.name;
  }
}
```

Nous pouvons voir que toute classe qui étend la classe ActiveCreature aura son propre fil de contrôle pour exécuter et invoquer les méthodes.

Par exemple, la classe Orc :

```java
public class Orc extends ActiveCreature {

  public Orc(String name) {
    super(name);
  }

}
```

Maintenant, nous pouvons créer de multiples créatures telles que les orques, leur dire de manger et d'errer et elles l'exécuteront sur leur propre fil de contrôle :

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

## Diagramme de classes

![alt text](../../../active-object/etc/active-object.urm.PNG "Active Object class diagram")
