---
title: Composite Entity
category: Structural
language: es
tag:
  - Client-server
  - Data access
  - Enterprise patterns
---

## También conocido como

* Coarse-Grained Entity

## Propósito

El patrón de diseño Entidad Compuesta tiene como objetivo gestionar un conjunto de objetos persistentes
interrelacionados como si fueran una única entidad. Se utiliza comúnmente en el contexto de Enterprise JavaBeans (EJB) y
marcos empresariales similares para representar estructuras de datos basadas en gráficos dentro de modelos de negocio,
permitiendo a los clientes tratarlos como una sola unidad.

## Explicación

Ejemplo real

> En una consola, puede haber muchas interfaces que necesiten ser gestionadas y controladas. Usando el patrón de entidad
> compuesta, objetos dependientes como mensajes y señales pueden ser combinados y controlados usando un único objeto.

En palabras llanas

> El patrón de entidad compuesta permite representar y gestionar un conjunto de objetos relacionados mediante un objeto
> unificado.

**Ejemplo programático**

Necesitamos una solución genérica para el problema. Para ello, vamos a introducir un patrón genérico de entidad
compuesta.

```java
public abstract class DependentObject<T> {

    T data;

    public void setData(T message) {
        this.data = message;
    }

    public T getData() {
        return data;
    }
}

public abstract class CoarseGrainedObject<T> {

    DependentObject<T>[] dependentObjects;

    public void setData(T... data) {
        IntStream.range(0, data.length).forEach(i -> dependentObjects[i].setData(data[i]));
    }

    public T[] getData() {
        return (T[]) Arrays.stream(dependentObjects).map(DependentObject::getData).toArray();
    }
}

```

La entidad compuesta especializada `consola` hereda de esta clase base de la siguiente manera.

```java
public class MessageDependentObject extends DependentObject<String> {

}

public class SignalDependentObject extends DependentObject<String> {

}

public class ConsoleCoarseGrainedObject extends CoarseGrainedObject<String> {

    @Override
    public String[] getData() {
        super.getData();
        return new String[] {
                dependentObjects[0].getData(), dependentObjects[1].getData()
        };
    }

    public void init() {
        dependentObjects = new DependentObject[] {
                new MessageDependentObject(), new SignalDependentObject()};
    }
}

public class CompositeEntity {

    private final ConsoleCoarseGrainedObject console = new ConsoleCoarseGrainedObject();

    public void setData(String message, String signal) {
        console.setData(message, signal);
    }

    public String[] getData() {
        return console.getData();
    }
}
```

Gestionando ahora la asignación de objetos mensaje y señal con la entidad compuesta `consola`.

```java
var console=new CompositeEntity();
        console.init();
        console.setData("No Danger","Green Light");
        Arrays.stream(console.getData()).forEach(LOGGER::info);
        console.setData("Danger","Red Light");
        Arrays.stream(console.getData()).forEach(LOGGER::info);
```

## Diagrama de clases

![alt text](./etc/composite_entity.urm.png "Patrón de entidad compuesta")

## Aplicabilidad

* Útil en aplicaciones empresariales donde los objetos de negocio son complejos e involucran varios objetos
  interdependientes.
* Ideal para escenarios donde los clientes necesitan trabajar con una interfaz unificada para un conjunto de objetos en
  lugar de entidades individuales.
* Aplicable en sistemas que requieren una vista simplificada de un modelo de datos complejo para clientes o servicios
  externos.

## Usos conocidos

* Aplicaciones empresariales con modelos de negocio complejos, particularmente aquellas que utilizan EJB o marcos
  empresariales similares.
* Sistemas que requieren abstracción sobre esquemas de bases de datos complejos para simplificar las interacciones con
  los clientes.
* Aplicaciones que necesitan reforzar la consistencia o las transacciones a través de múltiples objetos en una entidad
  de negocio.

## Consecuencias

Ventajas:

* Simplifica las interacciones del cliente con modelos de entidad complejos proporcionando una interfaz unificada.
* Mejora la reutilización y el mantenimiento de la capa de negocio al desacoplar el código del cliente de los complejos
  componentes internos de las entidades de negocio.
* Facilita la gestión de transacciones y la aplicación de la coherencia en un conjunto de objetos relacionados.

Contrapartidas:

* Puede introducir un nivel de indirección que podría afectar al rendimiento.
* Puede dar lugar a interfaces de grano demasiado grueso que podrían no ser tan flexibles para todas las necesidades de
  los clientes.
* Requiere un diseño cuidadoso para evitar entidades compuestas hinchadas que sean difíciles de gestionar.

## Patrones relacionados

* [Decorador](https://java-design-patterns.com/patterns/decorator/): Para añadir dinámicamente comportamiento a objetos
  individuales dentro de la entidad compuesta sin afectar a la estructura.
* [Fachada](https://java-design-patterns.com/patterns/facade/): Proporciona una interfaz simplificada a un subsistema
  complejo, de forma similar a como una entidad compuesta simplifica el acceso a un conjunto de objetos.
* [Flyweight](https://java-design-patterns.com/patterns/flyweight/): Útil para gestionar objetos compartidos dentro de
  una entidad compuesta para reducir la huella de memoria.

## Créditos

* [Composite Entity Pattern in wikipedia](https://en.wikipedia.org/wiki/Composite_entity_pattern)
* [Core J2EE Patterns: Best Practices and Design Strategies](https://amzn.to/4cAbDap)
* [Enterprise Patterns and MDA: Building Better Software with Archetype Patterns and UML](https://amzn.to/49mslqS)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3xjKdpe)
