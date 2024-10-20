---
title: Prototype
shortTitle: Prototype
category: Creational
language: es
tag:
  - Gang Of Four
  - Instantiation
---

## Propósito

Especificar los tipos de objetos a crear utilizando una instancia prototípica, y crear nuevos objetos copiando este
prototipo

## Explicación

Primero, debe notarse que el patrón Prototype no se utiliza para obtener beneficios de rendimiento. Solo se utiliza para
crear nuevos objetos a partir de instancias prototipo.

Ejemplo del mundo real

> ¿Recuerdas a Dolly? ¡La oveja que fue clonada! No entremos en detalles, pero el punto clave aquí es que todo se trata
> de clonación.

En palabras simples

> Crea un objeto basado en un objeto existente a través de la clonación.

Wikipedia dice

> El patrón de prototipo es un patrón de diseño de creación en el desarrollo de software. Se utiliza cuando el tipo de
> objetos a crear está determinado por una instancia prototípica, que se clona para producir nuevos objetos.

En resumen, te permite crear una copia de un objeto existente y modificarla según tus necesidades, en lugar de pasar por
el problema de crear un objeto desde cero y configurarlo.

**Ejemplo Programático**

En Java, se recomienda implementar el patrón prototipo de la siguiente manera. En primer lugar, cree una interfaz con un
método para clonar objetos. En este ejemplo, la interfaz `Prototype` logra esto con su método `copy`.

```java
public abstract class Prototype<T> implements Cloneable {
    @SneakyThrows
    public T copy() {
        return (T) super.clone();
    }
}
```

Nuestro ejemplo contiene una jerarquía de diferentes criaturas. Por ejemplo, veamos las clases `Beast` y `OrcBeast`.

```java
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public abstract class Beast extends Prototype<Beast> {

  public Beast(Beast source) {
  }

}

@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class OrcBeast extends Beast {

  private final String weapon;

  public OrcBeast(OrcBeast orcBeast) {
    super(orcBeast);
    this.weapon = orcBeast.weapon;
  }

  @Override
  public String toString() {
    return "Orcish wolf attacks with " + weapon;
  }

}
```

No queremos entrar en demasiados detalles, pero el ejemplo completo contiene también las clases base `Mage` y `Warlord`
y hay implementaciones especializadas para los elfos además de para los orcos.

Para aprovechar al máximo el patrón prototipo, creamos las clases `HeroFactory` y `HeroFactoryImpl` para producir
diferentes tipos de criaturas a partir de prototipos.

```java
public interface HeroFactory {
  
  Mage createMage();
  Warlord createWarlord();
  Beast createBeast();
}

@RequiredArgsConstructor
public class HeroFactoryImpl implements HeroFactory {

  private final Mage mage;
  private final Warlord warlord;
  private final Beast beast;

  public Mage createMage() {
    return mage.copy();
  }

  public Warlord createWarlord() {
    return warlord.copy();
  }

  public Beast createBeast() {
    return beast.copy();
  }
}
```

Ahora, somos capaces de mostrar el patrón prototipo completo en acción produciendo nuevas criaturas clonando instancias
existentes.

```java
    var factory = new HeroFactoryImpl(
        new ElfMage("cooking"),
        new ElfWarlord("cleaning"),
        new ElfBeast("protecting")
    );
    var mage = factory.createMage();
    var warlord = factory.createWarlord();
    var beast = factory.createBeast();
    LOGGER.info(mage.toString());
    LOGGER.info(warlord.toString());
    LOGGER.info(beast.toString());

    factory = new HeroFactoryImpl(
        new OrcMage("axe"),
        new OrcWarlord("sword"),
        new OrcBeast("laser")
    );
    mage = factory.createMage();
    warlord = factory.createWarlord();
    beast = factory.createBeast();
    LOGGER.info(mage.toString());
    LOGGER.info(warlord.toString());
    LOGGER.info(beast.toString());
```

Esta es la salida de la consola al ejecutar el ejemplo.

```
Elven mage helps in cooking
Elven warlord helps in cleaning
Elven eagle helps in protecting
Orcish mage attacks with axe
Orcish warlord attacks with sword
Orcish wolf attacks with laser
```

## Diagrama de Clases

![alt text](./etc/prototype.urm.png "Prototype pattern class diagram")

## Aplicabilidad

Utilice el patrón Prototipo cuando un sistema deba ser independiente de cómo se crean, componen, representan sus
productos y

* Cuando las clases a instanciar se especifican en tiempo de ejecución, por ejemplo, mediante carga dinámica.
* Para evitar construir una jerarquía de clases de fábricas paralela a la jerarquía de clases de productos.
* Cuando las instancias de una clase solo pueden tener una de unas pocas combinaciones diferentes de estado. Puede ser
  más conveniente instalar un número correspondiente de prototipos y clonarlos en lugar de instanciar la clase
  manualmente, cada vez con el estado apropiado.
* Cuando la creación de objetos es costosa en comparación con la clonación.

## Usos conocidos

* [java.lang.Object#clone()](http://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#clone%28%29)

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
