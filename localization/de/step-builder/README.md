---
shortTitle: Step Builder
category: Creational
language: de
tag:
  - Code simplification
  - Domain
  - Encapsulation
  - Extensibility
  - Instantiation
  - Interface
---

## Alternativbezeichnung

* Fluent Builder

## Zweck

Das Step-Builder-Pattern ist eine erweiterte Technik, um komplexe Objekte übersichtlich und flexibel
zu erzeugen. Es ist ideal für Szenarien, in denen die Objekterzeugung schrittweise und äußerst genau zu erfolgen hat.

## Detaillierte Erklärung

Vergleichsbeispiel:

> Betrachten wir den Zusammenbau eines individuell konfigurierten Computers.
Dafür sind mehrere Schritte nötig: Die CPU muss ausgewählt werden, ein Motherboard, dazu kommen
Arbeitsspeicher, Grafikkarte und Festplatte, die nach und nach ins Gehäuse eingebaut werden müssen.
Jeder Schritt folgt auf den anderen, bis schließlich ein funktionsfähiger Rechner entsteht.
Dieser schrittweise Konstruktionsprozess entspricht dem Step-Builder-Pattern und sorgt dafür,
dass alle nötigen Komponenten richtig zusammengebaut werden und anforderungsgemäß funktionieren.

In einfachen Worten

> Das Step-Builder-Pattern konstruiert komplexe Objekte nach und nach durch eine Reihe 
definierter Schritte. Das macht den Vorgang übersichtlich und flexibel.

Wikipedia sagt

> Step Builder ist eine Variante des Builder-Patterns mit dem Ziel, eine flexible Lösung
zur schrittweisen Konstruktion komplexer Objekte anzubieten. Sie ist besonders hilfreich,
wenn ein Objekt viele Initialisierungsschritte benötigt, die der Klarheit und Flexibilität wegen
einzeln abgearbeitet werden können.

Ablaufdiagramm

![Step Builder Ablaufdiagramm](/step-builder/etc/step-builder-sequence-diagram.png)

## Programmbeispiel

Als Erweiterung des Builder-Patterns führt Step Builder den Nutzer Schritt für Schritt durch
den Erzeugungsprozess eines Objekts. Es werden immer nur die nächsten verfügbaren Schritte
angezeigt, und die build-Methode ist erst dann verfügbar, wenn das Objekt tatsächlich bereit
zum Bau ist.

Betrachten wir eine Klasse `Character` mit vielen Attributen wie `name`, `fighterClass`, 
`wizardClass`, `weapon`, `spell`, und `abilities`.

```java
public class Character {

  private String name;
  private String fighterClass;
  private String wizardClass;
  private String weapon;
  private String spell;
  private List<String> abilities;

  public Character(String name) {
    this.name = name;
  }

}
```

Die Konstruktion ist wegen der vielen Attribute komplex. Darum verwenden wir Step Builder.

Wir schreiben eine Klasse `CharacterStepBuilder`, um den Benutzer durch den Konstruktionsprozess zu führen. 

```java
public class CharacterStepBuilder {

  // neuer Builder startet mit dem Schritt Namensvergabe
  public static NameStep newBuilder() {
    return new CharacterSteps();
  }

}
```

Die Klasse `CharacterStepBuilder` enthält eine Reihe von Interfaces,
die jeweils einen Schritt des Konstruktionsprozesses repräsentieren. Indem jedes Interface
eine Methode für den folgenden Schritt deklariert, wird der Benutzer durch den Prozess geführt.

```java
// nach der Namensvergabe kommt die Auswahl der wizardClass oder fighterClass
public interface NameStep {
  ClassStep name(String name);
}
```

```java
// nach der Klassenwahl wird entweder eine Waffe oder ein Zauberspruch zugewiesen
public interface ClassStep {
  WeaponStep fighterClass(String fighterClass);
  SpellStep wizardClass(String wizardClass);
}

// Weitere Schritte weggelassen
```

Die Klasse `Steps` implementiert all diese Interfaces und baut schließlich das `Character`-Object.

```java
private static class Steps implements NameStep, ClassStep, WeaponStep, SpellStep, BuildStep {

  private String name;
  private String fighterClass;
  private String wizardClass;
  private String weapon;
  private String spell;
  private List<String> abilities;

  // Die Implementationen der Methoden für die einzelnen Schritte sind hier weggelassen

  @Override
  public Character build() {
    return new Character(name, fighterClass, wizardClass, weapon, spell, abilities);
  }
}
```

Jetzt ist die Erzeugung eines `Character`-Objekts ein geführter Prozess.

```java
public static void main(String[] args) {

    var warrior = CharacterStepBuilder
            .newBuilder()
            .name("Amberjill")
            .fighterClass("Paladin")
            .withWeapon("Sword")
            .noAbilities()
            .build();

    LOGGER.info(warrior.toString());

    var mage = CharacterStepBuilder
            .newBuilder()
            .name("Riobard")
            .wizardClass("Sorcerer")
            .withSpell("Fireball")
            .withAbility("Fire Aura")
            .withAbility("Teleport")
            .noMoreAbilities()
            .build();

    LOGGER.info(mage.toString());

    var thief = CharacterStepBuilder
            .newBuilder()
            .name("Desmond")
            .fighterClass("Rogue")
            .noWeapon()
            .build();

    LOGGER.info(thief.toString());
}
```

Konsolenausgabe:

```
12:58:13.887 [main] INFO com.iluwatar.stepbuilder.App -- This is a Paladin named Amberjill armed with a Sword.
12:58:13.889 [main] INFO com.iluwatar.stepbuilder.App -- This is a Sorcerer named Riobard armed with a Fireball and wielding [Fire Aura, Teleport] abilities.
12:58:13.889 [main] INFO com.iluwatar.stepbuilder.App -- This is a Rogue named Desmond armed with a with nothing.
```

## Verwendung

* Wenn die Konstruktion eines Objekts viele Initialisierungsschritte benötigt.
* Wenn die Objektkonstruktion komplex ist und viele Parameter enthält.
* Um einen übersichtlichen, lesbaren und wartbaren Objekterzeugungsprozess bereitzustellen.

## Tutorials

* [Step Builder (Marco Castigliego)](http://rdafbn.blogspot.co.uk/2012/07/step-builder-pattern_28.html)

## Reale Anwendungen in Java
* Komplexe Konfigurationseinstellungen in Java-Anwendungen.
* Konstruktion von Objekten für Datenbankeinträge mit vielen Feldern.
* Konstruktion von GUI-Elementen, wo jeder Schritt einen anderen Teil der Schnittstelle festlegt.

## Vor- und Nachteile

Vorteile

* Code wird lesbarer und wartbarer durch übersichtliche und prägnante Objektkonstruktion.
* Erhöhte Flexibilität bei der Objekterzeugung durch Varianten im Konstruktionsprozess.
* Unterstützt unveränderbare Objekte durch Abtrennung ihrer Erzeugung.ntation.

Nachteile

* Code kann durch zusätzliche Klassen und Interfaces komplexer werden.
* Langatmiger Code bei vielen Konstruktionsschritten.

## Verwandte Patterns

* [Builder](https://java-design-patterns.com/patterns/builder/): Beide Patterns dienen der Konstruktion komplexer Objekte. Step Builder ist eine Variante mit Betonung auf schrittweisem Vorgehen.
* [Fluent Interface](https://java-design-patterns.com/patterns/fluentinterface/): Wird oft zusammen mit Step Builder verwendet, um eine sprechende API mit Methodenverkettung zur Konstruktion bereitzustellen.
* [Factory Method](https://java-design-patterns.com/patterns/factory-method/): Wird manchmal im Step-Builder-Pattern genutzt, um die Konstruktion des Builders selbst zu kapseln.

## Quellen

* [Clean Code: A Handbook of Agile Software Craftsmanship](https://amzn.to/3wRnjp5)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
