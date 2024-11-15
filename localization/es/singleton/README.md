---
title: Singleton
shortTitle: Singleton
category: Creational
language: es
tag:
 - Gang of Four
---

## Propósito

Asegurar que una clase solo tenga una instancia y proporcionar un punto de acceso global a ella.

## Explicación

Ejemplo del mundo real

> Solo puede haber una torre de marfil donde los magos estudian su magia. La misma torre de marfil encantada
> siempre es utilizada por los magos. La torre de marfil aquí es un singleton.

En otras palabras

> Asegura que solo se cree un objeto de una clase en particular.

Wikipedia dice

> En ingeniería de software, el patrón singleton es un patrón de diseño de software que limita la
> instanciación de una clase a un solo objeto. Esto es útil cuando se necesita exactamente un objeto para
> coordinar acciones en todo el sistema.


**Ejemplo programático**

Joshua Bloch, Effective Java 2nd Edition p.18

> Un enum type con un solo elemento es la mejor forma de implementar un singleton

```java
public enum EnumIvoryTower {
  INSTANCE
}
```

Luego,

```java
    var enumIvoryTower1 = EnumIvoryTower.INSTANCE;
    var enumIvoryTower2 = EnumIvoryTower.INSTANCE;
    LOGGER.info("enumIvoryTower1={}", enumIvoryTower1);
    LOGGER.info("enumIvoryTower2={}", enumIvoryTower2);
```

La salida de la consola

```
enumIvoryTower1=com.iluwatar.singleton.EnumIvoryTower@1221555852
enumIvoryTower2=com.iluwatar.singleton.EnumIvoryTower@1221555852
```

## Diagrama de clases

![alt text](./etc/singleton.urm.png "Singleton pattern class diagram")

## Aplicabilidad

Utilice el patrón Singleton cuando:

* Debe haber exactamente una instancia de una clase, y debe ser accesible para los clientes desde un punto de acceso conocido
* Cuando la única instancia debe ser extensible mediante herencia, y los clientes deben poder usar una instancia extendida sin modificar su código

Algunos casos típicos para Singleton:

* La clase logging
* Gestionar una conexión a una base de datos
* Gestor de archivos

## Usos conocidos

* [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)
* [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
* [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)


## Consecuencias

* Viola el Principio de Responsabilidad Única (Single Responsibility Principle - SRP) al controlar su creación y ciclo de vida.
* Fomenta el uso de una instancia compartida globalmente, lo que impide que un objeto y los recursos utilizados por este objeto se liberen.
* Crea un código fuertemente acoplado. Los clientes del Singleton se vuelven difíciles de probar.
* Hace casi imposible hacer subclases de un Singleton.

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Effective Java](https://www.amazon.com/gp/product/0134685997/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0134685997&linkCode=as2&tag=javadesignpat-20&linkId=4e349f4b3ff8c50123f8147c828e53eb)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
