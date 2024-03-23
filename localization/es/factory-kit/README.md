---
title: Factory Kit
category: Creational
language: es
tag:
  - Extensibility
---

## También conocido como

Abstract-Factory

## Propósito

Define una fábrica de contenido inmutable con interfaces de constructor y fábrica separadas.

## Explicación

Ejemplo del mundo real

> Imagina una fábrica de armas mágicas que puede crear cualquier tipo de arma que se desee. Cuando la fábrica se
> desempaqueta, el maestro recita los tipos de armas necesarios para prepararla. Después de eso, cualquiera de esos
> tipos
> de armas pueden ser invocados en un instante.

En palabras sencillas

> El kit de fábrica (Kit Factory) es un constructor de objetos configurable, una fábrica para crear fábricas.

**Ejemplo programático**

Definamos primero la interfaz simple arma `Weapon`.

```java
public interface Weapon {
}

public enum WeaponType {
    SWORD,
    AXE,
    BOW,
    SPEAR
}

public class Sword implements Weapon {
    @Override
    public String toString() {
        return "Sword";
    }
}

// Hacha `Axe`, Arco `Bow` y Lanza `Spear` se definen de forma similar
```

A continuación, definimos una interfaz funcional que permite añadir un constructor con un nombre a la fábrica.

```java
public interface Builder {
  void add(WeaponType name, Supplier<Weapon> supplier);
}
```

El núcleo del ejemplo es la interfaz `WeaponFactory` que implementa el patrón de fábrica. El método `#factory` se
utiliza para configurar la fábrica con las clases que necesita para ser capaz de construir. El método `#create` se
utiliza para crear instancias del objeto.

```java
public interface WeaponFactory {

  static WeaponFactory factory(Consumer<Builder> consumer) {
      var map = new HashMap<WeaponType, Supplier<Weapon>>();
      consumer.accept(map::put);
      return name -> map.get(name).get();
  }
    
  Weapon create(WeaponType name);
}
```

Ahora, podemos mostrar cómo se puede utilizar `WeaponFactory`.

```java
var factory = WeaponFactory.factory(builder -> {
  builder.add(WeaponType.SWORD, Sword::new);
  builder.add(WeaponType.AXE, Axe::new);
  builder.add(WeaponType.SPEAR, Spear::new);
  builder.add(WeaponType.BOW, Bow::new);
});
var list = new ArrayList<Weapon>();
list.add(factory.create(WeaponType.AXE));
list.add(factory.create(WeaponType.SPEAR));
list.add(factory.create(WeaponType.SWORD));
list.add(factory.create(WeaponType.BOW));
list.stream().forEach(weapon -> LOGGER.info("{}", weapon.toString()));
```

Esta es la salida de la consola cuando se ejecuta el ejemplo.

```
21:15:49.709 [main] INFO com.iluwatar.factorykit.App - Axe
21:15:49.713 [main] INFO com.iluwatar.factorykit.App - Spear
21:15:49.713 [main] INFO com.iluwatar.factorykit.App - Sword
21:15:49.713 [main] INFO com.iluwatar.factorykit.App - Bow
```

## Diagrama de clases

![alt text](./etc/factory-kit.png "Factory Kit")

## Aplicabilidad

Utilice el patrón Factory Kit cuando

* La clase de fábrica no puede anticipar los tipos de objetos que debe crear.
* Se necesita una nueva instancia de un constructor personalizado en lugar de uno global.
* Los tipos de objetos que la fábrica puede construir necesitan ser definidos fuera de la clase.
* Es necesario separar las interfaces del constructor y del creador.
* Desarrollos de juegos y otras aplicaciones que tienen personalización del usuario

## Patrones relacionados

* [Builder](https://java-design-patterns.com/patterns/builder/)
* [Factory](https://java-design-patterns.com/patterns/factory/)
* [Abstract-Factory](https://java-design-patterns.com/patterns/abstract-factory/)

## Tutoriales

* [Factory kit implementation tutorial](https://diego-pacheco.medium.com/factory-kit-pattern-66d5ccb0c405)

## Créditos

* [Design Pattern Reloaded by Remi Forax](https://www.youtube.com/watch?v=-k2X7guaArU)
