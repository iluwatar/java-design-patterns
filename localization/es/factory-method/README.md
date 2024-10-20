---
title: Factory Method
shortTitle: Factory Method
category: Creational
language: es
tag:
  - Extensibility
  - Gang of Four
---

## También conocido como

Virtual Constructor

## Propósito

Definir una interfaz para crear un objeto, pero dejar que las subclases decidan qué clase instanciar. El método de
fábrica permite que una clase difiera la instanciación a las subclases.

## Explicación

Ejemplo del mundo real

> El herrero fabrica armas. Los elfos requieren armas élficas y los orcos requieren armas orcas. Dependiendo del cliente
> en cuestión, se convoca al tipo correcto de herrero.

En palabras sencillas

> Proporciona una forma de delegar la lógica de instanciación a las clases hijas.

Wikipedia dice

> En la programación basada en clases, el patrón de método de fábrica es un patrón de creación que utiliza métodos de
> fábrica para lidiar con el problema de crear objetos sin tener que especificar la clase exacta del objeto que se
> creará.
> Esto se hace creando objetos llamando a un método de fábrica — ya sea especificado en una interfaz e implementado por
> clases hijas, o implementado en una clase base y opcionalmente sobrescrito por clases derivadas — en lugar de llamar a
> un constructor.

**Ejemplo programático**

Tomando nuestro ejemplo del herrero. En primer lugar, tenemos una interfaz `Blacksmith` y algunas implementaciones para
ello:

```java
public interface Blacksmith {
  Weapon manufactureWeapon(WeaponType weaponType);
}

public class ElfBlacksmith implements Blacksmith {
  public Weapon manufactureWeapon(WeaponType weaponType) {
    return ELFARSENAL.get(weaponType);
  }
}

public class OrcBlacksmith implements Blacksmith {
  public Weapon manufactureWeapon(WeaponType weaponType) {
    return ORCARSENAL.get(weaponType);
  }
}
```

Cuando llegan los clientes, se llama al herrero adecuado y se fabrican las armas solicitadas:

```java
Blacksmith blacksmith = new OrcBlacksmith();
Weapon weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
LOGGER.info("{} manufactured {}", blacksmith, weapon);
weapon = blacksmith.manufactureWeapon(WeaponType.AXE);
LOGGER.info("{} manufactured {}", blacksmith, weapon);

blacksmith = new ElfBlacksmith();
weapon = blacksmith.manufactureWeapon(WeaponType.SPEAR);
LOGGER.info("{} manufactured {}", blacksmith, weapon);
weapon = blacksmith.manufactureWeapon(WeaponType.AXE);
LOGGER.info("{} manufactured {}", blacksmith, weapon);
```

Salida del programa:

```
The orc blacksmith manufactured an orcish spear
The orc blacksmith manufactured an orcish axe
The elf blacksmith manufactured an elven spear
The elf blacksmith manufactured an elven axe
```

## Diagrama de clases

![alt text](./etc/factory-method.urm.png "Factory Method pattern diagrama de clases")

## Aplicabilidad

Utilice el patrón Método de Fábrica cuando:

* La clase no puede anticipar la clase de objetos que debe crear.
* La clase quiere que sus subclases especifiquen los objetos que crea.
* Las clases delegan la responsabilidad a una de varias subclases ayudantes, y desea localizar el conocimiento de qué
  subclase ayudante es el delegado. conocimiento de qué subclase ayudante es el delegado.

## Usos conocidos

* [java.util.Calendar](http://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html#getInstance--)
* [java.util.ResourceBundle](http://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html#getBundle-java.lang.String-)
* [java.text.NumberFormat](http://docs.oracle.com/javase/8/docs/api/java/text/NumberFormat.html#getInstance--)
* [java.nio.charset.Charset](http://docs.oracle.com/javase/8/docs/api/java/nio/charset/Charset.html#forName-java.lang.String-)
* [java.net.URLStreamHandlerFactory](http://docs.oracle.com/javase/8/docs/api/java/net/URLStreamHandlerFactory.html#createURLStreamHandler-java.lang.String-)
* [java.util.EnumSet](https://docs.oracle.com/javase/8/docs/api/java/util/EnumSet.html#of-E-)
* [javax.xml.bind.JAXBContext](https://docs.oracle.com/javase/8/docs/api/javax/xml/bind/JAXBContext.html#createMarshaller--)

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
* [Refactoring to Patterns](https://www.amazon.com/gp/product/0321213351/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0321213351&linkCode=as2&tag=javadesignpat-20&linkId=2a76fcb387234bc71b1c61150b3cc3a7)
