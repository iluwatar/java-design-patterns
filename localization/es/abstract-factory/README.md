---
title: Abstract Factory
shortTitle: Abstract Factory
category: Creational
language: es
tag:
 - Gang of Four
---

## También conocido como

Kit

## Propósito

Proveer de una interfaz para crear familias de objetos relacionados dependientes sin especificar su clase concreta.

## Explicación

Ejemplo del mundo real

> Para crear un reino necesitamos objetos con una temática común. El reino élfico necesita un rey elfo, un castillo élfico y un ejército élfico mientras que el reino orco necesita un rey orco, un castillo orco y un ejército orco. Hay una dependencia entre los objetos del reino.

Dicho de otra forma

> Una factoría de factorías; una factoría que agrupa otras factorías individuales pero relacionadas/dependientes sin especificar su clase concreta.

Según Wikipedia

> El patrón abstract factory provee una forma de encapsular un grupo de factorías individuales que tienen una temática común sin especificar sus clases concretas.

**Ejemplo Programático**

Traduciendo el ejemplo anterior sobre los reinos. Primero tenemos algunas interfaces e implementaciones de los objetos del `Castle`.

```java
public interface Castle {
  String getDescription();
}

public interface King {
  String getDescription();
}

public interface Army {
  String getDescription();
}

// Elven implementations ->
public class ElfCastle implements Castle {
  static final String DESCRIPTION = "This is the elven castle!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class ElfKing implements King {
  static final String DESCRIPTION = "This is the elven king!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
public class ElfArmy implements Army {
  static final String DESCRIPTION = "This is the elven Army!";
  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}

// Orcish implementations similarly -> ...

```

Luego tenemos la abstracción e implementación de la factoría del reino `KingdomFactory`.

```java
public interface KingdomFactory {
  Castle createCastle();
  King createKing();
  Army createArmy();
}

public class ElfKingdomFactory implements KingdomFactory {

  @Override
  public Castle createCastle() {
    return new ElfCastle();
  }

  @Override
  public King createKing() {
    return new ElfKing();
  }

  @Override
  public Army createArmy() {
    return new ElfArmy();
  }
}

public class OrcKingdomFactory implements KingdomFactory {

  @Override
  public Castle createCastle() {
    return new OrcCastle();
  }

  @Override
  public King createKing() {
    return new OrcKing();
  }
  
  @Override
  public Army createArmy() {
    return new OrcArmy();
  }
}
```

Ahora tenemos la factoría abstracta que nos permite hacer familias de objetos relacionados por ejemplo la factoría del reino élfico `ElfKingdomFactory` crea el castillo `castle`, rey `king` y ejército `army` etc.


```java
var factory = new ElfKingdomFactory();
var castle = factory.createCastle();
var king = factory.createKing();
var army = factory.createArmy();

castle.getDescription();
king.getDescription();
army.getDescription();
```

Salida del programa:

```java
This is the elven castle!
This is the elven king!
This is the elven Army!
```

Ahora podemos diseñar una factoría para nuestras factorías de reinos. En este ejemplo creamos  `FactoryMaker`, responsable de devolver una instancia de `ElfKingdomFactory` o `OrcKingdomFactory`.  
El cliente puede usar `FactoryMaker` para crear una factoría concreta, que a su vez, producirá diferentes objetos concretos (derivados de `Army`, `King`, `Castle`).  
En este ejemplo también usamos un enum para parametrizar el tipo de factoría de reinos pedirá el cliente.

```java
public static class FactoryMaker {

    public enum KingdomType {
        ELF, ORC
    }

    public static KingdomFactory makeFactory(KingdomType type) {
        return switch (type) {
            case ELF -> new ElfKingdomFactory();
            case ORC -> new OrcKingdomFactory();
            default -> throw new IllegalArgumentException("KingdomType not supported.");
        };
    }
}

    public static void main(String[] args) {
        var app = new App();

        LOGGER.info("Elf Kingdom");
        app.createKingdom(FactoryMaker.makeFactory(KingdomType.ELF));
        LOGGER.info(app.getArmy().getDescription());
        LOGGER.info(app.getCastle().getDescription());
        LOGGER.info(app.getKing().getDescription());

        LOGGER.info("Orc Kingdom");
        app.createKingdom(FactoryMaker.makeFactory(KingdomType.ORC));
        --similar use of the orc factory
    }
```

## Diagrama de clases

![alt text](./etc/abstract-factory.urm.png "Diagrama de Clases de Abstract Factory")


## Aplicación

Usar el patrón Abstract Factory cuando

* El sistema debe ser agnóstico a como se crean, componen y representan sus objetos.
* El sistema debe ser configurado con una de las múltiples familias de productos.
* La familia de objetos relacionados está diseñada para ser usada en conjunto y necesitas forzar esta premisa.
* Quieres proveer de una librería de productos y solo quieres revelar sus interfaces, no su implementación.
* El tiempo de vida de la dependencia es conceptualmente más corto que el del cliente.
* Necesitas un valor en tiempo de ejecución para construir una dependencia.
* Quieres decidir que producto de una familia llamar en tiempo de ejecución.
* Necesitas proveer de uno o más parámetros solo conocidos en tiempo de ejecución antes de poder resolver la dependencia.
* Necesitas consistencia entre productos.
* No quieres cambiar el código existente al añadir nuevos productos o familias de productos al programa.

Ejemplos de casos de uso

* Elegir llamar a la implementación correcta de FileSystemAcmeService o DatabaseAcmeService o NetworkAcmeService en tiempo de ejecución.
* Escribir test unitarios se hace mucho más sencillo.
* Herramientas UI (User Interface) para diferentes SO (Sistemas Operativos).

## Consecuencias

* La inyección de dependencias en java esconde las dependencias de la clase servicio lo que puede llevar a errores de ejecución que se podrían haber evitado al compilar.
* Mientras que el patrón es muy bueno creando objetos predefinidos, añadir nuevos puede ser complicado.
* El código es más complicado de lo que debería porque se añaden muchas interfaces y clases nuevas junto con el patrón.

## Tutoriales

* [Abstract Factory Pattern Tutorial](https://www.journaldev.com/1418/abstract-factory-design-pattern-in-java) 

## Usos conocidos

* [javax.xml.parsers.DocumentBuilderFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/parsers/DocumentBuilderFactory.html)
* [javax.xml.transform.TransformerFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/transform/TransformerFactory.html#newInstance--)
* [javax.xml.xpath.XPathFactory](http://docs.oracle.com/javase/8/docs/api/javax/xml/xpath/XPathFactory.html#newInstance--)

## Patrones relacionados

* [Factory Method](https://java-design-patterns.com/patterns/factory-method/)
* [Factory Kit](https://java-design-patterns.com/patterns/factory-kit/)

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
