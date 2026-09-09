---
shortTitle: Builder
language: de
tag:
  - Gang of Four
  - Instantiation
  - Object composition
---

## Zweck

Das Builder-Pattern ist ein fundamentales Erzeugungsmuster, mit dem komplexe Objekte schrittweise konstruiert werden.
Es trennt die Konstruktion eines komplexen Objekts von seiner Darstellung, sodass im gleichen
Konstruktionsprozess verschiedene Ausprägungen erzeugt werden können.

## Detaillierte Erklärung

Beispiel aus der realen Welt

> Das Builder-Pattern ist besonders nützlich, wenn zur Erzeugung eines Objekts viele Parameter benötigt werden.
>
> Stellen Sie sich vor, Sie bestellen ein individuell zusammengestelltes Sandwich.
> Das Builder-Pattern stellt dafür einen SandwichBuilder bereit, mit dem Sie jede einzelne
> Komponente auswählen können (Brotart, Fleisch, Käse, Gemüse, Würzung). Sie müssen nicht
> wissen, wie man so ein Sandwich tatsächlich zusammenbaut. Es genügt, dass Sie Schritt für
> Schritt alle gewünschten Komponenten angeben, um genau das gewünschte Sandwich zu erhalten.
> Diese Trennung der Konstruktion vom fertigen Produkt stellt sicher, dass viele verschiedene
> Sandwichtypen aus den Komponenten konstruiert werden können.

In einfachen Worten

> Man kann verschiedene Formen eines Objekts erzeugen, ohne eine Vielzahl von Konstruktoren
> oder einen Konstruktor mit vielen Parametern zu benötigen. Nützlich, wenn ein Objekt in
> mehreren Geschmacksrichtungen auftauchen kann, oder wenn die Erzeugung des Objekts aus
> vielen Schritten besteht.

Wikipedia sagt

> Das Builder-Pattern ist ein Entwurfsmuster zur Objekterzeugung, das eine Lösung für
> das Telescoping-Constructor-Antipattern bieten will.

Was hat es mit diesem Antipattern auf sich? 
Irgendwann treffen wir alle auf solche Konstruktoren:

```java
public Hero(Profession profession, String name, HairType hairType, HairColor hairColor, Armor armor, Weapon weapon){
    // Wertzuweisungen
}
```
Sie sehen, die Zahl der Parameter im Konstruktor kann schnell unübersichtlich werden, so dass 
schwer zu erkennen ist, welche benötigt werden und in welcher Reihenfolge sie stehen müssen.
Dieses Problem verstärkt sich, wenn später noch weitere Optionen hinzugefügt werden.
Dies wird als Telescoping-Constructor-Antipattern bezeichnet.

Ablaufdiagramm

![Builder sequence diagram](/builder/etc/builder-sequence-diagram.png)

## Programmbeispiel

In diesem Beispiel konstruieren wir verschiedene Typen von `Hero`-Objekten mit wechselnden Attributen.

Stellen Sie sich einen Charakter-Generator in einem Rollenspiel vor. Die einfachste Option ist es,
den Charakter komplett vom Computer erstellen zu lassen. Manchmal will der Spieler aber selbst bestimmte
Eigenschaften des Charakters auswählen, etwa Beruf, Geschlecht, Haarfarbe etc. Dies ist ein schrittweiser Prozess, der erst abgeschlossen ist, wenn alle gewünschten Eigenschaften
festgelegt wurden.

Mit dem Builder-Pattern gibt es einen besseren Ansatz dafür.
Betrachten wir zunächst den `Hero`, den wir erzeugen wollen: 

```java
public final class Hero {
    private final Profession profession;
    private final String name;
    private final HairType hairType;
    private final HairColor hairColor;
    private final Armor armor;
    private final Weapon weapon;

    private Hero(Builder builder) {
        this.profession = builder.profession;
        this.name = builder.name;
        this.hairColor = builder.hairColor;
        this.hairType = builder.hairType;
        this.weapon = builder.weapon;
        this.armor = builder.armor;
    }
}
```

Dazu gibt es den `Builder`:

```java
  public static class Builder {
    private final Profession profession;
    private final String name;
    private HairType hairType;
    private HairColor hairColor;
    private Armor armor;
    private Weapon weapon;

    public Builder(Profession profession, String name) {
        if (profession == null || name == null) {
            throw new IllegalArgumentException("profession and name can not be null");
        }
        this.profession = profession;
        this.name = name;
    }

    public Builder withHairType(HairType hairType) {
        this.hairType = hairType;
        return this;
    }

    public Builder withHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
        return this;
    }

    public Builder withArmor(Armor armor) {
        this.armor = armor;
        return this;
    }

    public Builder withWeapon(Weapon weapon) {
        this.weapon = weapon;
        return this;
    }

    public Hero build() {
        return new Hero(this);
    }
}
```

Verwendet wird das Ganze dann so:

```java
  public static void main(String[] args) {

    var mage = new Hero.Builder(Profession.MAGE, "Riobard")
            .withHairColor(HairColor.BLACK)
            .withWeapon(Weapon.DAGGER)
            .build();
    LOGGER.info(mage.toString());

    var warrior = new Hero.Builder(Profession.WARRIOR, "Amberjill")
            .withHairColor(HairColor.BLOND)
            .withHairType(HairType.LONG_CURLY).withArmor(Armor.CHAIN_MAIL).withWeapon(Weapon.SWORD)
            .build();
    LOGGER.info(warrior.toString());

    var thief = new Hero.Builder(Profession.THIEF, "Desmond")
            .withHairType(HairType.BALD)
            .withWeapon(Weapon.BOW)
            .build();
    LOGGER.info(thief.toString());
}
```

Programmausgabe:

```
16:28:06.058 [main] INFO com.iluwatar.builder.App -- This is a mage named Riobard with black hair and wielding a dagger.
16:28:06.060 [main] INFO com.iluwatar.builder.App -- This is a warrior named Amberjill with blond long curly hair wearing chain mail and wielding a sword.
16:28:06.060 [main] INFO com.iluwatar.builder.App -- This is a thief named Desmond with bald head and wielding a bow.
```

## Verwendung

* Das Builder-Pattern ist ideal für Anwendungen, die komplexe Konstruktoren benötigen.
* Der Algorithmus zur Erzeugung eines komplexen Objekts sollte unabhängig davon sein, aus
   welchen Elementen es besteht und wie diese zusammengesetzt werden.
* Der Konstruktionsprozess muss verschiedene Ausprägungen des konstruierten Objekts erlauben.
* Besonders nützlich, wenn viele Schritte zur Erzeugung benötigt werden, die in einer bestimmten
  Reihenfolge ausgeführt werden müssen.

## Tutorials

* [Builder Design Pattern in Java (DigitalOcean)](https://www.journaldev.com/1425/builder-design-pattern-in-java)
* [Builder (Refactoring Guru)](https://refactoring.guru/design-patterns/builder)
* [Exploring Joshua Bloch’s Builder design pattern in Java (Java Magazine)](https://blogs.oracle.com/javamagazine/post/exploring-joshua-blochs-builder-design-pattern-in-java)

## Reale Anwendungen in Java

* StringBuilder und StringBuffer konstruieren veränderbare String-Objekte
* Java.nio.ByteBuffer und ähnliche Klassen wie FloatBuffer, IntBuffer usw.
* javax.swing.GroupLayout.Group#addComponent()
* Verschiedene GUI-Builder in IDEs, die GUI-Komponenten bauen.
* Alle Implementationen von [java.lang.Appendable](http://docs.oracle.com/javase/8/docs/api/java/lang/Appendable.html)
* [Apache Camel Builder](https://github.com/apache/camel/tree/0e195428ee04531be27a0b659005e3aa8d159d23/camel-core/src/main/java/org/apache/camel/builder)
* [Apache Commons Option.Builder](https://commons.apache.org/proper/commons-cli/apidocs/org/apache/commons/cli/Option.Builder.html)

## Vor- und Nachteile

Vorteile:

* Bessere Kontrolle des Konstruktionsprozesses als bei anderen Erzeugungsmustern.
* Ermöglicht schrittweise Konstruktion, Verschiebung von Schritten und rekursiven Ablauf der Schritte.
* Kann Objekte konstruieren, die eine komplexe Zusammenstellung von Teilobjekten erfordern.
  Dabei wird das Gesamtobjekt von den Teilen und dem Prozess des Zusammenfügens getrennt.
* Prinzip der eindeutigen Verantwortlichkeit: Komplexer Konstruktionscode kann von der Geschäftslogik getrennt werden. 

Nachteile:

* Der Code kann insgesamt komplexer werden, weil zusätzliche Klassen benötigt werden.
* Durch die Builder-Objekte kann sich der Speicherverbrauch erhöhen.

## Verwandte Patterns

* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Kann gemeinsam mit Builder verwendet werden, um Teile eines komplexen Objekts zu generieren.
* [Prototype](https://java-design-patterns.com/patterns/prototype/): Builder bauen oft Prototypen nach.
* [Step Builder](https://java-design-patterns.com/patterns/step-builder/): Eine Variante des Builder-Patterns mit Schritt-für-Schritt-Ansatz.
  Gute Wahl für Objekte mit einer Vielzahl optionaler Parameter.

## Quellen

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
