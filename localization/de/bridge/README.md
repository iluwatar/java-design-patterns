---
shortTitle: Bridge
category: Structural
language: de
tag:
  - Abstraction
  - Decoupling
  - Extensibility
  - Gang of Four
  - Object composition
---

## Alternativbezeichnung

* Handle/Body

## Zweck

Das Bridge-Pattern ist ein Entwurfsmuster, das eine Abstraktion von ihrer Implementation
entkoppelt, wodurch beide unabhängig voneinander variieren können.
Es ist wesentlich, um flexible und erweiterbare Softwaresysteme zu bauen.

## Detaillierte Erklärung

Reales Beispiel

> In Java wird das Bridge-Pattern oft verwendet für GUI-Frameworks, Datenbanktreiber und Gerätetreiber.
>
> Denken Sie an eine Universalfernbedienung (Abstraktion), die verschiedene Geräte diverser Marken
> (Implementationen) schalten kann. Die Fernbedienung stellt eine einheitliche Schnittstelle bereit für
> Aktionen wie Ein- und Ausschalten, Programmwechsel und Lautstärkeregelung. Diese Aktionen sind
> bei jedem einzelnen Gerät unterschiedlich implementiert. Mit dem Bridge-Pattern werden diese
> spezifischen Implementationen von der Fernbedienungsschnittstelle entkoppelt, sodass alle Geräte bedient
> unabhängig von Marke und interner Funktionsweise bedient werden können. Diese Trennung erlaubt es,
> dass neue Geräte mit der gleichen Fernbedienung ohne Änderung an deren Schnittstelle gesteuert werden können,
> oder dass neue Fernbedienungen für den gleichen Gerätepool entwickelt werden können.

In einfachen Worten

> Beim Bridge-Pattern geht es um den Vorrang von Komposition vor Vererbung.
> Details der Implementation werden aus einer Hierarchie in ein anderes Objekt mit separater Hierarchie verschoben.

Wikipedia sagt

> Das Bridge-Pattern ist ein Entwurfsmuster der Softwareentwicklung mit dem Zweck,
> eine Abstraktion von ihrer Implementierung zu trennen, so dass beide unabhängig voneinander angepasst werden können.

Ablaufdiagramm

![Bridge sequence diagram](/bridge/etc/bridge-sequence-diagram.png)

## Programmbeispiel

Stellen Sie sich vor, eine Waffe kann diverse Verzauberungen haben und sie müssen
verschiedene Waffen mit verschiedenen Verzauberungen kombinieren. Wie realisieren Sie das?
Imagine you have a weapon that can have various enchantments, and you need to combine 
different weapons with different enchantments. How would you handle this? 
Durch viele Versionen einer Waffe mit jeweils einem anderen Zauber, oder würden Sie separate
Verzauberungen definieren und diese nach Bedarf einer Waffe zuordnen? Das Bridge-Pattern macht letzteres möglich.

Hier ist die `Weapon`-Hierarchie:

```java
public interface Weapon {
    void wield();

    void swing();

    void unwield();

    Enchantment getEnchantment();
}

public class Sword implements Weapon {

    private final Enchantment enchantment;

    public Sword(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    @Override
    public void wield() {
        LOGGER.info("The sword is wielded.");
        enchantment.onActivate();
    }

    @Override
    public void swing() {
        LOGGER.info("The sword is swung.");
        enchantment.apply();
    }

    @Override
    public void unwield() {
        LOGGER.info("The sword is unwielded.");
        enchantment.onDeactivate();
    }

    @Override
    public Enchantment getEnchantment() {
        return enchantment;
    }
}

public class Hammer implements Weapon {

    private final Enchantment enchantment;

    public Hammer(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    @Override
    public void wield() {
        LOGGER.info("The hammer is wielded.");
        enchantment.onActivate();
    }

    @Override
    public void swing() {
        LOGGER.info("The hammer is swung.");
        enchantment.apply();
    }

    @Override
    public void unwield() {
        LOGGER.info("The hammer is unwielded.");
        enchantment.onDeactivate();
    }

    @Override
    public Enchantment getEnchantment() {
        return enchantment;
    }
}
```

Hier die separate `Enchantment`-Hierarchie:

```java
public interface Enchantment {
    void onActivate();

    void apply();

    void onDeactivate();
}

public class FlyingEnchantment implements Enchantment {

    @Override
    public void onActivate() {
        LOGGER.info("The item begins to glow faintly.");
    }

    @Override
    public void apply() {
        LOGGER.info("The item flies and strikes the enemies finally returning to owner's hand.");
    }

    @Override
    public void onDeactivate() {
        LOGGER.info("The item's glow fades.");
    }
}

public class SoulEatingEnchantment implements Enchantment {

    @Override
    public void onActivate() {
        LOGGER.info("The item spreads bloodlust.");
    }

    @Override
    public void apply() {
        LOGGER.info("The item eats the soul of enemies.");
    }

    @Override
    public void onDeactivate() {
        LOGGER.info("Bloodlust slowly disappears.");
    }
}
```

Hier werden beide Hierarchien aktiv:

```java
public static void main(String[] args) {
    LOGGER.info("The knight receives an enchanted sword.");
    var enchantedSword = new Sword(new SoulEatingEnchantment());
    enchantedSword.wield();
    enchantedSword.swing();
    enchantedSword.unwield();

    LOGGER.info("The valkyrie receives an enchanted hammer.");
    var hammer = new Hammer(new FlyingEnchantment());
    hammer.wield();
    hammer.swing();
    hammer.unwield();
}
```

Dies ist die Ausgabe:

```
The knight receives an enchanted sword.
The sword is wielded.
The item spreads bloodlust.
The sword is swung.
The item eats the soul of enemies.
The sword is unwielded.
Bloodlust slowly disappears.
The valkyrie receives an enchanted hammer.
The hammer is wielded.
The item begins to glow faintly.
The hammer is swung.
The item flies and strikes the enemies finally returning to owner's hand.
The hammer is unwielded.
The item's glow fades.
```

## Verwendung

Das Bridge-Pattern kommt in diesen Fällen in Betracht:

* Eine dauerhafte Bindung zwischen Abstration und Implementierung soll vermieden werden, etwa
  wenn die Implementation zur Laufzeit ausgewählt oder ausgewechselt werden muss.
* Sowohl Abstraktion als auch Implementationen sollen durch Vererbung erweitert werden können,
 um unabhängige Erweiterungen jeder Komponente zu ermöglichen.
* Änderungen an der Implementation einer Abstraktion sollen die Verwender nicht beeinflussen, d.h. sie sollen nicht neu kompiliert werden müssen.
* Die Hierarchie enthält eine große Anzahl Klassen, was dafür spricht, Objekte in zwei Teile zu spalten.
  Dieses Konzept wird von Rumbaugh als "verschachtelte Generalisierungen" bezeichnet.
* Sie wollen eine Implementation mit mehreren Objekten teilen, eventuell unter Verwendung von
  Referenzzählern, dabei aber dieses Detail aber vor den Verwendern verstecken. Beispiel dafür
  ist die String-Klasse von Coplien, wo mehrere Objekte die gleiche Stringdarstellung haben.

## Tutorials

* [Bridge Pattern Tutorial (DigitalOcean)](https://www.digitalocean.com/community/tutorials/bridge-design-pattern-java)

## Reale Anwendungen in Java

* In GUI-Frameworks ist das Fenster die Abstraktion, die Implementation die Fensterkonstruktion des Betriebssystem.
* Bei Datenbanktreibern ist die Abstraktion eine generische Datenbankschnittstelle, die Implementationen sind datenbankspezifische Treiber.
* Bei Gerätetreibern ist der geräteunabhängige Code die Abstraktion, die Implementation betrifft das einzelne Gerät.

## Vor- und Nachteile

Vorteile:

* Entkopplung von Schnittstelle und Implementation: Durch Trennung von Operationen der höheren (Schnittstelle) und der niedrigeren Ebene (Implementation) verbessert sich die Modularität.
* Bessere Erweiterbarkeit: Abstraktions- und Implementationshierarchien können unabhängig voneinander erweitert werden.
* Versteckte Implementationsdetails: Verwender sehen nur die Schnittstelle, nicht ihre Implementation.

Nachteile:

* Höhere Komplexität: Systemarchitektur und Code können sich verkomplizieren, vor allem wenn man nicht mit dem Pattern vertraut ist.
* Laufzeitkosten: Die zusätzliche Abstraktionsschicht kann zu Performanceeinbußen führen, auch wenn das in der Praxis oft vernachlässigbar ist.


## Verwandte Patterns

* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/):
 Das Abstract-Factory-Pattern kann zusammen mit dem Bridge-Pattern verwendet werden, um Plattformen zu schaffen, die unabhängig von den konkreten Klassen zur Objekterzeugung sind.
* [Adapter](https://java-design-patterns.com/patterns/adapter/):
  Das Adapter-Pattern stellt eine neue Schnittstelle für ein Objekt zur Verfügung,
  das Bridge-Pattern trennt die Schnittstelle des Objekts von der Implementation.
* [Composite](https://java-design-patterns.com/patterns/composite/):
  Das Bridge-Pattern wird häufig mit dem Composite-Pattern verwendet, um die Implementationsdetails
  einer Komponente zu modellieren.
* [Strategy](https://java-design-patterns.com/patterns/strategy/):
 Beide Patterns verwenden Komposition: Strategy für Veränderungen am Verhalten einer Klasse, Bridge für die Trennung von Abstraktion und Implementation.

## Quellen

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
* [Pattern-Oriented Software Architecture Volume 1: A System of Patterns](https://amzn.to/3TEnhtl)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
