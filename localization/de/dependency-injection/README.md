---
shortTitle: Dependency Injection
category: Creational
language: de
tag:
  - Decoupling
  - Dependency management
  - Inversion of control
---

## Alternativbezeichnungen

* Inversion of Control (IoC)
* Dependency Inversion

## Zweck

Die Erzeugung der in einem Objekt benötigten Abhängigkeiten soll von der Verwendung entkoppelt werden. So wird der Code flexibler und testbarer.

## Detaillierte Erklärung

Beispiel aus dem echten Leben

> Stellen Sie sich edles Restaurant vor, in dem der Koch diverse Zutaten für das Essen braucht.
> Er geht nicht für jede Zutat einzeln zu deren Hersteller, sondern bedient sich eines zuverlässigen
> Lieferanten, der ihm jeden Tag die Produkte frisch vom jeweiligen Hersteller besorgt.
> So kann er sich aufs Kochen konzentrieren und muss sich nicht mehr um den Einkauf kümmern.
>
> Im Dependency-Injection-Pattern fungiert der Lieferant als "Injektor", der die nötigen
> Abhängigkeiten (Zutaten) dem "Objekt" Koch zur Verfügung stellt. Der Koch kann die Zutaten
> verwenden, ohne deren Ursprung kennen zu müssen. Die Beschaffung und Verwendung der Abhängigkeiten
> sind also klar getrennt. Mit diesem Ansatz werden Effizienz, Flexibilität und Wartungsfreundlichkeit in der Küche verbessert,
> ebenso wie in einem Softwaresystem.

In einfachen Worten

> Dependency Injection trennt die Beschaffung der Abhängigkeiten vom eigenen Verhalten des Verwenders.

Wikipedia sagt

> In der Softwareentwicklung ist Dependency Injection eine Technik, mit der ein Objekt andere
> Objekte zugewiesen bekommt, die es benötigt. Diese anderen Objekte nennt man Abhängigkeiten (Dependencies).

Ablaufdiagramm

![Dependency Injection sequence diagram](/dependency-injection/etc/dependency-injection-sequence-diagram.png)

## Programmbeispiel

Der alte Hexenmeister möchte hin und wieder seine Pfeife stopfen und Tabak rauchen.
Dabei will er aber nicht von einer einzigen Tabakmarke abhängig sein, sondern die Marke wechseln können.

Definieren wir also zunächst die Schnittstelle `Tobacco` und konkrete Klassen für die Marken.

```java

public abstract class Tobacco {

    public void smoke(Wizard wizard) {
        LOGGER.info("{} smoking {}", wizard.getClass().getSimpleName(),
                this.getClass().getSimpleName());
    }
}

public class SecondBreakfastTobacco extends Tobacco {
}

public class RivendellTobacco extends Tobacco {
}

public class OldTobyTobacco extends Tobacco {
}
```

Als nächstes die `Wizard`-Klassenhierarchie.

```java
public interface Wizard {

    void smoke();
}

public class AdvancedWizard implements Wizard {

    private final Tobacco tobacco;

    public AdvancedWizard(Tobacco tobacco) {
        this.tobacco = tobacco;
    }

    @Override
    public void smoke() {
        tobacco.smoke(this);
    }
}
```

Schließlich sehen wir, wie leicht es ist, dem Hexenmeister jede beliebige Tabakmarke zu geben.

```java
public static void main(String[] args) {
    var simpleWizard = new SimpleWizard();
    simpleWizard.smoke();

    var advancedWizard = new AdvancedWizard(new SecondBreakfastTobacco());
    advancedWizard.smoke();

    var advancedSorceress = new AdvancedSorceress();
    advancedSorceress.setTobacco(new SecondBreakfastTobacco());
    advancedSorceress.smoke();

    var injector = Guice.createInjector(new TobaccoModule());
    var guiceWizard = injector.getInstance(GuiceWizard.class);
    guiceWizard.smoke();
}
```

Prorammausgabe:

```
11:54:05.205 [main] INFO com.iluwatar.dependency.injection.Tobacco -- SimpleWizard smoking OldTobyTobacco
11:54:05.207 [main] INFO com.iluwatar.dependency.injection.Tobacco -- AdvancedWizard smoking SecondBreakfastTobacco
11:54:05.207 [main] INFO com.iluwatar.dependency.injection.Tobacco -- AdvancedSorceress smoking SecondBreakfastTobacco
11:54:05.308 [main] INFO com.iluwatar.dependency.injection.Tobacco -- GuiceWizard smoking RivendellTobacco
```

## Verwendung

* Wenn die Kopplung zwischen den Klassen reduziert und die Modularität der Anwendung erhöht werden soll
* In Szenarien, wo die Objekterzeugung komplex ist oder sonst von der Verwendung der Klasse getrennt werden soll.
* In Anwendungen, die einfacheres Texten durch Mocks oder Stubs benötigen.
* In Frameworks oder Bibliotheken, die den Lebenszyklus von Objekten managen, wie Spring oder Jakarta EE (früher Java EE).

## Reale Anwendungen in Java

* Frameworks wie Spring, Jakarta EE und Google Guice verwenden Dependency Injection (DI)
  ausgiebig, um Lebenszyklen und Abhängigkeiten zu verwalten.
* Desktop- und Webanwendungen, die eine flexible Architektur mit leicht austauschbaren Komponenten benötigen.

## Vor- und Nachteile

Vorteile:

* Verbessert die Modularität und Trennung von Zuständigkeiten.
* Vereinfacht Unit-Tests durch leichtes Mocken von Abhängigkeiten.
* Verbessert Flexibilität und Wartbarkeit durch Förderung von loser Kopplung.

Nachteile:

* Kann Konfiguration verkomplizieren, vor allem in großen Projekten.
* Entwickler, die mit dem Konzept nicht vertraut sind, müssen es erst erlernen.
* Erfordert sorgfältiges Management von Lebenzyklen und Gültigkeitsbereich der Objekte.

## Verwandte Patterns

* [Factory-Methoden](https://java-design-patterns.com/patterns/factory-method/) und [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Werden zur Erzeugung von Instanzen genutzt, die per DI injiziert werden.
* [Service Locator](https://java-design-patterns.com/patterns/service-locator/): Eine Alternative zu DI, um Dienste oder Komponenten zu finden, entkoppelt den Prozess aber nicht so effektiv.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Häufig Ergänzung zu DI, um eine einzige Instanz zu liefern. eines Service für die gesamte Anwendung zu liefern.

## Quellen

* [Clean Code: A Handbook of Agile Software Craftsmanship](https://amzn.to/3wRnjp5)
* [Dependency Injection: Design patterns using Spring and Guice](https://amzn.to/4aMyHkI)
* [Dependency Injection Principles, Practices, and Patterns](https://amzn.to/4aupmxe)
* [Google Guice: Agile Lightweight Dependency Injection Framework](https://amzn.to/4bTDbX0)
* [Java 9 Dependency Injection: Write loosely coupled code with Spring 5 and Guice](https://amzn.to/4ayCtxp)
* [Java Design Pattern Essentials](https://amzn.to/3xtPPxa)
* [Pro Java EE Spring Patterns: Best Practices and Design Strategies Implementing Java EE Patterns with the Spring Framework](https://amzn.to/3J6Teoh)
* [Spring in Action](https://amzn.to/4asnpSG)
