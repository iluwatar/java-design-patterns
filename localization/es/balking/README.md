---
title: Balking
shortTitle: Balking
category: Concurrency
language: es
tag:
 - Decoupling
---

## Propósito

El patrón _Balking_ se utiliza para prevenir que un objeto ejecute un código determinado si
este se encuentra en un estado incompleto o inapropiado.

## Explicación

Ejemplo del mundo real

> En una lavadora hay un botón de arranque para iniciar el lavado de ropa. Cuando la lavadora
> está inactiva el botón funciona como se espera, pero si ya está lavando entonces el botón no hace
> nada.

En otras palabras

> Usando el patrón _Balking_, un código determinado se ejecuta solo si el objeto está en un estado concreto.

Wikipedia dice

> El patrón _Balking_ es un patrón de diseño de software que ejecuta una acción en un objeto solamente cuando
> el objeto está en un estado concreto. Por ejemplo, si un objeto lee archivos ZIP y un método de llamada
> invoca un método _get_ en el objeto cuando el archivo ZIP no está abierto, el objeto "rechazaría" (_balk_)
> la petición.

**Ejemplo Programático**

En este ejemplo de implementación, `WashingMachine` es un objeto que tiene dos estados los cuales pueden
ser: _ENABLED_ y _WASHING_ (ACTIVADA y LAVANDO respectivamente). Si la máquina está _ENABLED_, el estado
cambia a _WASHING_ usando un método thread-safe a prueba de hilos. Por otra parte, si ya está lavando y
cualquier otro hilo ejecuta `wash()` entonces no hará ningún cambio de estado y finalizará la ejecución del
método sin hacer nada.

Aquí están las partes relevantes de la clase `WashingMachine`.

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

Aquí la sencilla interfaz `DelayProvider` utilizada por `WashingMachine`.

```java
public interface DelayProvider {
    void executeAfterDelay(long interval, TimeUnit timeUnit, Runnable task);
}
```

Ahora introducimos la aplicación utilizando `WashingMachine`.

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

Aquí está la salida en consola de la aplicación.

```
14:02:52.268 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-2: Actual machine state: ENABLED
14:02:52.272 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-2: Doing the washing
14:02:52.272 [pool-1-thread-3] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-3: Actual machine state: WASHING
14:02:52.273 [pool-1-thread-3] ERROR com.iluwatar.balking.WashingMachine - Cannot wash if the machine has been already washing!
14:02:52.273 [pool-1-thread-1] INFO com.iluwatar.balking.WashingMachine - pool-1-thread-1: Actual machine state: WASHING
14:02:52.273 [pool-1-thread-1] ERROR com.iluwatar.balking.WashingMachine - Cannot wash if the machine has been already washing!
14:02:52.324 [pool-1-thread-2] INFO com.iluwatar.balking.WashingMachine - 14: Washing completed.
```

## Diagrama de clases

![alt text](./etc/balking.png "Balking")

## Aplicabilidad

Usa el patrón _Balking_ cuando

* Un objeto debe ejecutar un código determinado sólo cuando se encuentra en un estado concreto.
* Los objetos están en un estado que es propenso a bloquearse temporalmente, pero durante una cantidad de tiempo indeterminada.

## Patrones relacionados

* [Guarded Suspension Pattern](https://java-design-patterns.com/patterns/guarded-suspension/)
* [Double Checked Locking Pattern](https://java-design-patterns.com/patterns/double-checked-locking/)

## Referencias

* [Patterns in Java: A Catalog of Reusable Design Patterns Illustrated with UML, 2nd Edition, Volume 1](https://www.amazon.com/gp/product/0471227293/ref=as_li_qf_asin_il_tl?ie=UTF8&tag=javadesignpat-20&creative=9325&linkCode=as2&creativeASIN=0471227293&linkId=0e39a59ffaab93fb476036fecb637b99)
