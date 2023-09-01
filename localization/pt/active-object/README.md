---
title: Active Object
category: Concurrency
language: pt
tag:
 - Performance
---


## Intenção
O padrão de design de objeto ativo separa a execução do método da invocação do método para objetos que residem em seu encadeamento de controle. O objetivo é introduzir simultaneidade, usando invocação de método assíncrono e um agendador para lidar com solicitações.

## Explicação

A classe que implementa o padrão de objeto ativo conterá um mecanismo de auto-sincronização sem usar métodos 'sincronizados'.

Exemplo do mundo real


>Os Orcs são conhecidos por sua selvageria e alma indomável. Parece que eles têm seu próprio fio de controle com base no comportamento anterior.

Para implementar uma criatura que possui seu próprio mecanismo de thread de controle e expor apenas sua API e não a execução em si, podemos usar o padrão Active Object.


**Exemplo programático**

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

Podemos ver que qualquer classe que estenderá a classe ActiveCreature terá seu próprio thread de controle para invocar e executar métodos.

Por exemplo, a classe Orc:
```java
public class Orc extends ActiveCreature {

  public Orc(String name) {
    super(name);
  }

}
```

Agora, podemos criar várias criaturas, como Orcs, dizer-lhes para comer e vagar, e eles o executarão em seu próprio fio de controle:

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

## Diagrama de classes

![alt text](./etc/active-object.urm.png "Active Object class diagram")

## Tutoriais

* [Android and Java Concurrency: The Active Object Pattern](https://www.youtube.com/watch?v=Cd8t2u5Qmvc)