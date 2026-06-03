---
shortTitle: Singleton
category: Creational
language: de
tag:
  - Gang of Four
  - Instantiation
  - Lazy initialization
  - Resource management
---

## Alternativbezeichnung

* Single Instance

## Zweck

Sicherstellen, dass es nur eine Instanz einer Klasse gibt, und einen globalen Zugriffspunkt auf diese Instanz bereitstellen.

## Detaillierte Erklärung

Analogie aus der Realität

> Das Singleton-Pattern entspricht der Ausgabe von Pässen durch die Regierung.
> Jeder Bürger darf zu jeder Zeit nur einen Pass besitzen. Die Meldebehörde stellt sicher,
> dass niemandem ein zweiter Pass ausgestellt wird.
> Wenn ein Bürger ins Ausland reist, benötigt er seinen Pass, der als einzigartiger weltweit anerkannter Nachweis
> seiner Identität dient.

In einfachen Worten

> Es darf nur ein einziges Objekt dieser Klasse erzeugt werden.
> 
Wikipedia sagt

> In der Softwareentwicklung ist das Singleton ein Entwurfsmuster, das die Instanziierung einer
> Klasse auf ein einziges Objekt beschränkt.
> Dies ist sinnvoll, wenn genau ein Objekt benötigt wird, das Aktionen über das gesamte System
> hinweg koordiniert.

Ablaufdiagramm

![Singleton Pattern sequence diagram](/singleton/etc/singleton-sequence-diagram.png)

## Programmbeispiel

vgl. Joshua Bloch, Effective Java 2nd Edition, Seite 18

> Die beste Art der Implementation eines Singletons ist ein Enum mit nur einem Element.

```java
public enum EnumIvoryTower {
  INSTANCE
}
```

So wird es verwendet:

```java
    var enumIvoryTower1 = EnumIvoryTower.INSTANCE;
    var enumIvoryTower2 = EnumIvoryTower.INSTANCE;
    LOGGER.info("enumIvoryTower1={}", enumIvoryTower1);
    LOGGER.info("enumIvoryTower2={}", enumIvoryTower2);
```

Ausgabe in der Konsole:

```
enumIvoryTower1=com.iluwatar.singleton.EnumIvoryTower@1221555852
enumIvoryTower2=com.iluwatar.singleton.EnumIvoryTower@1221555852
```

## Verwendung

Ein Singleton sollte verwendet werden, wenn
* genau eine Instanz der Klasse benötigt wird, die für Nutzer über einen wohldefinierten Zugriffspunkt erreichbar ist.
* es möglich sein soll, diese Klasse durch Vererbung zu erweitern, ohne dass bei Verwendung der erweiterten Instanz Codeänderungen nötig sind.

## Reale Anwendungen in Java

* Logging-Klassen
* Konfigurationsklassen in vielen Anwendungen
* Verbindungspools
* Dateimanager
* [java.lang.Runtime#getRuntime()](http://docs.oracle.com/javase/8/docs/api/java/lang/Runtime.html#getRuntime%28%29)
* [java.awt.Desktop#getDesktop()](http://docs.oracle.com/javase/8/docs/api/java/awt/Desktop.html#getDesktop--)
* [java.lang.System#getSecurityManager()](http://docs.oracle.com/javase/8/docs/api/java/lang/System.html#getSecurityManager--)

## Vor- und Nachteile
Vorteile:
* Kontrollierter Zugriff auf die einzige Instanz.
* Namensraum wird nicht unnötig belastet.
* Operationen und Darstellungen können durch Vererbung verfeinert werden.
* Bei Bedarf auch mehrere Instanzen möglich.
* Flexibler als Klassenoperationen

Nachteile:
* Schwierig zu testen wegen globalem Status.
* Möglicherweise komplexeres Lebenszyklusmanagement.
* Bei Parallelität sind ohne sorgfältige Synchronisierung Engpässe möglich.

## Verwandte Patterns

* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Oft verwendet, um sicherzustellen, dass nur eine Instanz existiert.
* [Factory Methoden](https://java-design-patterns.com/patterns/factory-method/): Das Singleton-Pattern kann implementiert werden, indem über eine Factory-Methode die Instanzerzeugung gekapselt wird.
* [Prototyp](https://java-design-patterns.com/patterns/prototype/): Hier müssen keine Instanzen erzeugt werden. Das Pattern kann zusammen mit dem Singleton verwendet werden, um einzige Instanzen zu verwalten.

## Quellen

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
