---
title: Active Object
category: Concurrency
language: it
tag:
 - Performance
---


## Intento
Il design pattern active object disaccoppia l'esecuzione del metodo dall'invocazione del metodo per gli oggetti che risiedono ciascuno nel proprio thread di controllo. L'obiettivo è introdurre la concorrenza utilizzando l'invocazione asincrona dei metodi e uno scheduler per gestire le richieste.

## Spiegazione

La classe che implementa il pattern active object conterrà un meccanismo di autosincronizzazione senza utilizzare metodi 'synchronized'.

Esempio del mondo reale

>Gli Orchi sono noti per la loro natura selvaggia e la loro anima indomabile. Sembra che abbiano il loro proprio thread di controllo basato su comportamenti precedenti.

Per implementare una creatura che ha il suo meccanismo di thread di controllo e che esponga solo la sua API e non l'esecuzione stessa, possiamo utilizzare il pattern Active Object.


**Esempio di codice**

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

Possiamo vedere che qualsiasi classe che estenderà la classe ActiveCreature avrà il proprio thread di controllo per invocare ed eseguire i metodi.

Ad esempio, la classe Orc:

```java
public class Orc extends ActiveCreature {

  public Orc(String name) {
    super(name);
  }

}
```

Ora possiamo creare diverse creature come gli Orchi, dir loro di mangiare e vagabondare, e loro eseguiranno queste azioni nel proprio thread di controllo:

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

## Diagramma delle classi

![alt text](../../../active-object/etc/active-object.urm.png "Active Object class diagram")

## Tutorial

* [Android and Java Concurrency: The Active Object Pattern](https://www.youtube.com/watch?v=Cd8t2u5Qmvc)