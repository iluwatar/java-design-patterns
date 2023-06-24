---
title: Balking
category: Concurrency
language: es
tag:
 - Decoupling
---

## Propósito

El patrón Balking es usado cuando es necesario prevenir que un objecto ejecute cierto código mientras se encuentra en un estado incompleto o inapropiado para la ejecución de dicho código.

## Explicación

Ejemplo del mundo real

> Las lavadoras semiautomáticas tienen un botón dedicado a iniciar el lavado. Mientras el dispositivo
> se encuentra inactivo, el botón de iniciar funciona de la manera esperada, pero si el dispositivo 
> se encuentra activo ejecutando la función de lavado, el botón simplemente deja de funcionar. 

Dicho con otras palabras

> Cuando se usa el patrón Balking, cierta seccion de código es ejecutada solo si el objeto se 
> encuentra en un estado específico. 

Según Wikipedia

> Balking es un patrón de diseño de software donde solamente se ejecuta una acción sobre un objeto 
> cuando dicho objecto se encuentra en un estado particular. Por ejemplo, si un objeto lee ficheros ZIP y 
> otros objecto hace una llamada a los métodos de dicho objeto mientras el fichero ZIP se encuentras cerrado,
> la llamada es "obstaculizada" (balk).

**Ejemplos Programáticos**

En la siguiente implementación, `WashingMachine` es un objeto que posee dos estados: ENABLED (habilitado) y WASHING (lavando). Si la máquina se encuentra ENABLED, el estado cambia a WASHING mediante la invocación del método usando un hilo seguro (thread-safe). De lo contrario, si la máquina se encuentra "lavando" y otro hilo (thread) ejecuta el método `wash()`, no sucederá nada, el método simplemente retornará sin ejecutar ningún código relevante.

A continuación se describen las partes más importantes de la clase `WashingMachine`.

```java
@Slf4j
public class WashingMachine {
    
    private final DelayProvider delayProvider;
    private WashingMachineState washingMachineState;

    public WashingMachine(DelayProvider delayProvider) {
        this.delayProvider = delayProvider;
        this.washingMachineState = washingMachineState.ENABLED;
    }

    public WashingMachineState gerWashingMachineState() {
        return washingMachineState;
    }

    public void wash() {
        synchronized (this) {
            var machineState = getWashingMachineState();
            LOGGER.info("{}: Estado actual de la máquina: {}", Thread.currentThread().getName(), machineState);
            if (this.washingMachineState == WashingMachineState.WASHING) {
                LOGGER.error("¡No puede lavar si la máquina se encuentra actualmente activa!");
                return;
            }
            this.washingMachineState = WashingMachineState.WASHING;
        }
        LOGGER.info("{}: Haciendo el lavado", Thread.currentThread().getName());
        this.delayProvider.executeAfterDelay(50, TimeUnit.MILLISECONDS, this::endOfWashing); 
    }

    public synchronized void endOfWashing() {
        washingMachineState = WashingMachineState.ENABLED;
        LOGGER.info("{}: Lavado finalizado.", Thread.currentThread().getId();)
    }
}
```

A continuación una simple implementación de `DelayProvider` usada por `WashingMachine`.

```java
public interface DelayProvider {
  void executeAfterDelay(long interval, TimeUnit timeUnit, Runnable task);
}
```

Ahora presentamos la aplicación usando `WashingMachine`

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

A continuación la salida de consola del programa. 

```
14:02:52.268 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-2: Estado actual de la máquina: ENABLED
14:02:52.272 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-2: Haciendo el lavado
14:02:52.272 [pool-1-thread-3] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-3: Estado actual de la máquina: WASHING
14:02:52.273 [pool-1-thread-3] ERROR com.iluwatar.balking.WashingMachine - ¡No puede lavar si la máquina se encuentra actualmente activa!
14:02:52.273 [pool-1-thread-1] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-1: Estado actual de la máquina: WASHING
14:02:52.273 [pool-1-thread-1] ERROR com.iluwatar.balking.WashingMachine - ¡No puede lavar si la máquina se encuentra actualmente activa!
14:02:52.324 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - 14: Lavado finalizado.
```

## Diagrama de clases

![alt text](../../../balking/etc/balking.png "Diagrama de clases de Balking")

## Aplicabilidad

Usar el patrón Balking en las siguentes circunstancias

* Quieres invocar una acción sobre un objecto solamente cuando dicho objeto se encuentra en un estado particular.
* Los objetos se encuentran generalmente en un estado donde son propensos a ser obstaculizados temporalmente pero por una cantidad de tiempo indefinido.

## Patrones relacionados

* [Guarded Suspension Pattern](https://java-design-patterns.com/patterns/guarded-suspension/)
* [Double Checked Locking Pattern](https://java-design-patterns.com/patterns/double-checked-locking/)

## Créditos

* [Patterns in Java: A Catalog of Reusable Design Patterns Illustrated with UML, 2nd Edition, Volume 1](https://www.amazon.com/gp/product/0471227293/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0471227293&linkId=0e39a59ffaab93fb476036fecb637b99)