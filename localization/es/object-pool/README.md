---
title: Object Pool
category: Creational
language: es
tag:
  - Game programming
  - Performance
---

## También conocido como

Resource Pool

## Propósito

Cuando los objetos son costosos de crear y solo se necesitan durante cortos períodos de tiempo, es ventajoso utilizar el
patrón de Object Pool. El Object Pool proporciona una caché para objetos instanciados, rastreando cuáles están en uso y
cuáles están disponibles.

## Explicación

Ejemplo del mundo real

> En nuestro juego de guerra necesitamos usar olifantes, bestias masivas y míticas, pero el problema es que son
> extremadamente costosos de crear. La solución es crear un grupo de ellos, rastrear cuáles están en uso y, en lugar de
> desecharlos, reutilizar las instancias.

En palabras simples

> Object Pool gestiona un conjunto de instancias en lugar de crearlas y destruirlas bajo demanda.

Wikipedia dice

> El patrón de object pool es un patrón de diseño de creación de software que utiliza un conjunto de objetos
> inicializados listos para usar, un "pool", en lugar de asignar y destruirlos a demanda.

**Ejemplo Programático**

Aquí está la clase básica olifante `Oliphaunt`. Estos gigantes son muy caros de crear.

```java
public class Oliphaunt {

  private static final AtomicInteger counter = new AtomicInteger(0);

  private final int id;

  public Oliphaunt() {
    id = counter.incrementAndGet();
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public int getId() {
    return id;
  }

  @Override
  public String toString() {
    return String.format("Oliphaunt id=%d", id);
  }
}
```

A continuación se muestra el `ObjectPool` y más concretamente el `OliphauntPool`.

```java
public abstract class ObjectPool<T> {

  private final Set<T> available = new HashSet<>();
  private final Set<T> inUse = new HashSet<>();

  protected abstract T create();

  public synchronized T checkOut() {
    if (available.isEmpty()) {
      available.add(create());
    }
    var instance = available.iterator().next();
    available.remove(instance);
    inUse.add(instance);
    return instance;
  }

  public synchronized void checkIn(T instance) {
    inUse.remove(instance);
    available.add(instance);
  }

  @Override
  public synchronized String toString() {
    return String.format("Pool available=%d inUse=%d", available.size(), inUse.size());
  }
}

public class OliphauntPool extends ObjectPool<Oliphaunt> {

  @Override
  protected Oliphaunt create() {
    return new Oliphaunt();
  }
}
```

Por último, así es como utilizamos la piscina.

```java
    var pool = new OliphauntPool();
    var oliphaunt1 = pool.checkOut();
    var oliphaunt2 = pool.checkOut();
    var oliphaunt3 = pool.checkOut();
    pool.checkIn(oliphaunt1);
    pool.checkIn(oliphaunt2);
    var oliphaunt4 = pool.checkOut();
    var oliphaunt5 = pool.checkOut();
```

Salida del programa:

```
Pool available=0 inUse=0
Checked out Oliphaunt id=1
Pool available=0 inUse=1
Checked out Oliphaunt id=2
Checked out Oliphaunt id=3
Pool available=0 inUse=3
Checking in Oliphaunt id=1
Checking in Oliphaunt id=2
Pool available=2 inUse=1
Checked out Oliphaunt id=2
Checked out Oliphaunt id=1
Pool available=0 inUse=3
```

## Diagrama de Clases

![alt text](./etc/object-pool.png "Object Pool")

## Aplicabilidad

Utilice el patrón Object Pool cuando

* Los objetos son caros de crear (coste de asignación).
* Necesitas un gran número de objetos de vida corta (fragmentación de memoria).
