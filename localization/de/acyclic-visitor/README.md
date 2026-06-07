---
shortTitle: Acyclic Visitor
category: Behavioral
language: de
tag:
  - Decoupling
  - Extensibility
  - Interface
  - Object composition
---

## Zweck

Das Acyclic-Visitor-Pattern entkoppelt Operationen von der Objekthierarchie und erlaubt so ein flexibles Design für verschiedenste Anwendungen.

## Detailierte Erklärung

Reales Beispiel

> Als Vergleich aus der realen analogen Welt soll ein System von Museumsführern dienen.
> Stellen Sie sich ein Museum mit verschiedensten Ausstellungsstücken (Bilder, Skulpturen, historische Artefakte, ...) vor.
> Es gibt dort verschiedene Typen von Führern (Menschen, Audio-Guides, VR-Führer), die zu jedem Objekt Informationen geben.
> Wenn nun eine neue Art der Führung eingeführt wird, muss nicht jedes Ausstellungsstück dafür angepasst werden.
> Stattdessen implementiert jeder Führer eine Schnittstelle zu den jeweiligen Ausstellungstücken.
> Auf diese Weise ist das System leicht erweiterbar.

In einfachen Worten

> Acyclic Visitor erlaubt das Hinzufügen von Funktionen, ohne die bestehende Hierarchie anpassen zu müssen.

[WikiWikiWeb](https://wiki.c2.com/?AcyclicVisitor) sagt:

> Das Acyclic-Visitor-Pattern erlaubt es, neue Funktionen zu einer bestehenden Klassenhierarchie
> hinzuzufügen, ohne diese Hierarchie zu verändern und ohne Abhängigkeitszyklen (wie beim Visitor Pattern) zu schaffen.

Ablaufdiagramm

![Acyclic Visitor sequence diagram](/acyclic-visitor/etc/acyclic-visitor-sequence-diagram.png "Acyclic Visitor sequence diagram")


## Programmbeispiel

Wir betrachten eine Hierarchie von Modem-Klassen. Die Modems werden besucht von einem externen Algorithmus,
der auf Filterkritien (Unix- oder DOS-Kompatibilität) basiert.

Here die `Modem` Hierarchie.

```java
public abstract class Modem {
    public abstract void accept(ModemVisitor modemVisitor);
}

public class Zoom extends Modem {

    // Weitere Eigenschaften und Methoden ...

    @Override
    public void accept(ModemVisitor modemVisitor) {
        if (modemVisitor instanceof ZoomVisitor) {
            ((ZoomVisitor) modemVisitor).visit(this);
        } else {
            LOGGER.info("Only ZoomVisitor is allowed to visit Zoom modem");
        }
    }
}

public class Hayes extends Modem {

    // Weitere Eigenschaften und Methoden...

    @Override
    public void accept(ModemVisitor modemVisitor) {
        if (modemVisitor instanceof HayesVisitor) {
            ((HayesVisitor) modemVisitor).visit(this);
        } else {
            LOGGER.info("Only HayesVisitor is allowed to visit Hayes modem");
        }
    }
}
```

Danach führen wir die `ModemVisitor` Hierarchie ein.

```java
public interface ModemVisitor {
}

public interface HayesVisitor extends ModemVisitor {
    void visit(Hayes hayes);
}

public interface ZoomVisitor extends ModemVisitor {
    void visit(Zoom zoom);
}

public interface AllModemVisitor extends ZoomVisitor, HayesVisitor {
}

public class ConfigureForDosVisitor implements AllModemVisitor {

    // Weitere Eigenschaften und Methoden...

    @Override
    public void visit(Hayes hayes) {
        LOGGER.info(hayes + " used with Dos configurator.");
    }

    @Override
    public void visit(Zoom zoom) {
        LOGGER.info(zoom + " used with Dos configurator.");
    }
}

public class ConfigureForUnixVisitor implements ZoomVisitor {

    // Weitere Eigenschaften und Methoden...

    @Override
    public void visit(Zoom zoom) {
        LOGGER.info(zoom + " used with Unix configurator.");
    }
}
```

Schließlich die Visitors im Einsatz.

```java
public static void main(String[] args) {
    var conUnix = new ConfigureForUnixVisitor();
    var conDos = new ConfigureForDosVisitor();

    var zoom = new Zoom();
    var hayes = new Hayes();

    hayes.accept(conDos); // Hayes modem with Dos configurator
    zoom.accept(conDos); // Zoom modem with Dos configurator
    hayes.accept(conUnix); // Hayes modem with Unix configurator
    zoom.accept(conUnix); // Zoom modem with Unix configurator   
}
```

Programausgabe:

```
09:15:11.125 [main] INFO com.iluwatar.acyclicvisitor.ConfigureForDosVisitor -- Hayes modem used with Dos configurator.
09:15:11.127 [main] INFO com.iluwatar.acyclicvisitor.ConfigureForDosVisitor -- Zoom modem used with Dos configurator.
09:15:11.127 [main] INFO com.iluwatar.acyclicvisitor.Hayes -- Only HayesVisitor is allowed to visit Hayes modem
09:15:11.127 [main] INFO com.iluwatar.acyclicvisitor.ConfigureForUnixVisitor -- Zoom modem used with Unix configurator.
```

## Verwendung

* Wenn Sie zu einer bestehenden Hierarchie eine neue Funktion hinzufügen müssen, ohne die Hierarchie zu verändern.
* Wenn es Funktionen gibt, die zwar auf einer Hierarchie arbeiten, aber nicht selbst dazu gehören (Wie die Configure-Funktionen im obigen Beispiel).
* Wenn abhängig vom Objekttyp sehr unterschiedliche Funktionen auszuführen sind.
* Wenn die zu besuchende Klassenhierarchie häufig um neue Kindklassen erweitert wird.
* When the visited class hierarchy will be frequently extended with new derivatives of the Element class.
* Wenn es sehr aufwendig ist, die neuen Kindklassen zu kompilieren, zu verlinken, zu testen oder zu verteilen.

## Tutorials

* [The Acyclic Visitor Pattern (Code Crafter)](https://codecrafter.blogspot.com/2012/12/the-acyclic-visitor-pattern.html)

## Vor- und Nachteile

Vorteile:

* Erweiterbarkeit: Neue Funktionen können leicht hinzugefügt werden, ohne die Objektstruktur zu verändern.
* Entkopplung: Kopplung zwischen Objekten und den auf ihnen stattfindenden Operationen wird reduziert.
* Keine Abhängigkeitszyklen:  Abhängigkeiten sind azyklisch, Wartbarkeit verbessert sich, Komplexität wird reduziert.
  
Nachteile:

* Komplexität: Viele erforderliche Visitor-Interfaces können die Komplexität erhöhen.
* Wartbarkeit: Änderungen an der Objekthierarchie erfordern Updates aller Visitoren.

## Verwandte Patterns

* [Composite](https://java-design-patterns.com/patterns/composite/):
Wird oft zusammen mit Acyclic Visitor verwendet, um einzelne Objekte und Zusammensetzungen aus ihnen einheitlich zu behandeln.
* [Decorator](https://java-design-patterns.com/patterns/decorator/):
Can als Ergänzung eingesetzt werden, um Objekten Verantwortlichkeiten dynamisch zuzuweisen.
* [Visitor](https://java-design-patterns.com/patterns/visitor/): Acyclic Visitor ist eine Variante des Visitor Patterns, die zyklische Abhängigkeiten vermeidet.

## Quellen

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
* [Patterns in Java: A Catalog of Reusable Design Patterns Illustrated with UML](https://amzn.to/4bOtzwF)
* [Acyclic Visitor (Robert C. Martin)](http://condor.depaul.edu/dmumaugh/OOT/Design-Principles/acv.pdf)
* [Acyclic Visitor (WikiWikiWeb)](https://wiki.c2.com/?AcyclicVisitor)
