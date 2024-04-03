---
title: Proxy
category: Structural
language: es
tag:
 - Gang Of Four
 - Decoupling
---

## También conocido como

Surrogate

## Propósito

Proporcionar un sustituto o marcador de posición de otro objeto para controlar el acceso al mismo.

## Explicación

Ejemplo real

> Imagina una torre donde los magos locales van a estudiar sus hechizos. A la torre de marfil sólo se puede acceder a través de un proxy que asegura que sólo los tres primeros magos pueden entrar. Aquí el proxy representa la funcionalidad de la torre y le añade control de acceso.

En palabras llanas

> Usando el patrón proxy, una clase representa la funcionalidad de otra clase.

Wikipedia dice

> Un proxy, en su forma más general, es una clase que funciona como interfaz de otra cosa. Un proxy es una envoltura o un objeto agente que está siendo llamado por el cliente para acceder al objeto real de servicio detrás de las escenas. El uso del proxy puede ser simplemente el reenvío al objeto real, o puede proporcionar lógica adicional. En el proxy se puede proporcionar funcionalidad adicional, por ejemplo, almacenamiento en caché cuando las operaciones en el objeto real consumen muchos recursos, o comprobación de condiciones previas antes de invocar operaciones en el objeto real.

**Ejemplo programático**

Tomando el ejemplo anterior de nuestra torre de asistentes. En primer lugar tenemos la interfaz `WizardTower` y la clase `IvoryTower`.

```java
public interface WizardTower {

  void enter(Wizard wizard);
}

@Slf4j
public class IvoryTower implements WizardTower {

  public void enter(Wizard wizard) {
    LOGGER.info("{} enters the tower.", wizard);
  }

}
```

A continuación, una simple clase `Wizard`.

```java
public class Wizard {

  private final String name;

  public Wizard(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
```

Luego tenemos el `WizardTowerProxy` para añadir control de acceso a `WizardTower`.

```java
@Slf4j
public class WizardTowerProxy implements WizardTower {

  private static final int NUM_WIZARDS_ALLOWED = 3;

  private int numWizards;

  private final WizardTower tower;

  public WizardTowerProxy(WizardTower tower) {
    this.tower = tower;
  }

  @Override
  public void enter(Wizard wizard) {
    if (numWizards < NUM_WIZARDS_ALLOWED) {
      tower.enter(wizard);
      numWizards++;
    } else {
      LOGGER.info("{} is not allowed to enter!", wizard);
    }
  }
}
```

Y aquí está el escenario de entrada a la torre.

```java
var proxy = new WizardTowerProxy(new IvoryTower());
proxy.enter(new Wizard("Red wizard"));
proxy.enter(new Wizard("White wizard"));
proxy.enter(new Wizard("Black wizard"));
proxy.enter(new Wizard("Green wizard"));
proxy.enter(new Wizard("Brown wizard"));
```

Salida del programa:

```
Red wizard enters the tower.
White wizard enters the tower.
Black wizard enters the tower.
Green wizard is not allowed to enter!
Brown wizard is not allowed to enter!
```

## Diagrama de clases

![alt text](./etc/proxy.urm.png "Proxy pattern class diagram")

## Aplicabilidad

El proxy es aplicable siempre que se necesite una referencia a un objeto más versátil o sofisticada que un simple puntero.
que un simple puntero. He aquí varias situaciones comunes en las que el patrón Proxy es
aplicable.

* Un proxy remoto proporciona un representante local para un objeto en un espacio de direcciones diferente.
* El proxy virtual crea objetos caros bajo demanda.
* Un proxy de protección controla el acceso al objeto original. Los proxies de protección son útiles cuando
  objetos deben tener diferentes derechos de acceso.

Típicamente, el patrón proxy se utiliza para

* Controlar el acceso a otro objeto
* Inicialización perezosa
* Implementar el registro
* Facilitar la conexión de red
* Contar referencias a un objeto

## Tutoriales

* [Controlling Access With Proxy Pattern](http://java-design-patterns.com/blog/controlling-access-with-proxy-pattern/)

## Usos conocidos

* [java.lang.reflect.Proxy](http://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Proxy.html)
* [Apache Commons Proxy](https://commons.apache.org/proper/commons-proxy/)
* Mocking frameworks [Mockito](https://site.mockito.org/), 
[Powermock](https://powermock.github.io/), [EasyMock](https://easymock.org/)
* [UIAppearance](https://developer.apple.com/documentation/uikit/uiappearance)

## Patrones relacionados

* [Ambassador](https://java-design-patterns.com/patterns/ambassador/)

## Créditos

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/gp/product/0201633612/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0201633612&linkCode=as2&tag=javadesignpat-20&linkId=675d49790ce11db99d90bde47f1aeb59)
* [Head First Design Patterns: A Brain-Friendly Guide](https://www.amazon.com/gp/product/0596007124/ref=as_li_tl?ie=UTF8&camp=1789&creative=9325&creativeASIN=0596007124&linkCode=as2&tag=javadesignpat-20&linkId=6b8b6eea86021af6c8e3cd3fc382cb5b)
