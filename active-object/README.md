---
diseño: patrón
título: Objeto activo
carpeta: objeto activo
enlace permanente: /patrones/objeto-activo/
categorías: Concurrencia
idioma: es
etiquetas:
 - Actuación
---


## Intención
El patrón de diseño de objeto activo desacopla la ejecución del método de la invocación del método para los objetos que residen en su subproceso de control. El objetivo es introducir la simultaneidad mediante el uso de la invocación de métodos asincrónicos y un programador para gestionar las solicitudes.

## Explicación

La clase que implementa el patrón de objeto activo contendrá un mecanismo de autosincronización sin usar métodos 'sincronizados'.

ejemplo del mundo real

>Los orcos son conocidos por su salvajismo y su alma indomable. Parece que tienen su propio hilo de control basado en el comportamiento anterior.

Para implementar una criatura que tiene su propio hilo de mecanismo de control y exponer solo su API y no la ejecución en sí, podemos usar el patrón Active Object.


**Ejemplo programático**

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

Podemos ver que cualquier clase que extienda la clase ActiveCreature tendrá su propio hilo de control para invocar y ejecutar métodos.

Por ejemplo, la clase Orc:

```java
public class Orc extends ActiveCreature {

  public Orc(String name) {
    super(name);
  }

}
```

Ahora, podemos crear múltiples criaturas como orcos, decirles que coman y deambulen, y lo ejecutarán en su propio hilo de control:

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

## Diagrama de clase

![texto alternativo](./etc/active-object.urm.png "Active Object class diagram")

## Tutoriales

* [Simultaneidad de Android y Java: el patrón de objeto activo](https://www.youtube.com/watch?v=Cd8t2u5Qmvc)
