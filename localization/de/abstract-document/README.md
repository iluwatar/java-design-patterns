---
shortTitle: Abstract Document
category: Structural
language: de
tag:
    - Abstraction
    - Decoupling
    - Dynamic typing
    - Encapsulation
    - Extensibility
    - Polymorphism
---

## Zweck

Abstract Document ist ein wichtiges Struktur-Pattern, das ein einheitliches Handling von hierarchischen baumartige Datenstrukturen ermöglicht, indem es eine gemeinsame Schnittstelle für verschiedene Dokumenttypen definiert. Es trennt die Kernstruktur des Dokuments von spezifischen Datenformaten und ermöglicht so dynamische Aktualisierungen und einfachere Wartung.

## Detaillierte Erklärung

Abstract Document erlaubt die dynamische Behandlung nicht-statischer Eigenschaften. Dieses Pattern verwendet das Konzept der Traits, um Typsicherheit zu gewährleisten und Eigenschaften verschiedener Klassen in verschiedene Schnittstellen abzuspalten.

Reales Beispiel

> Denken Sie an ein Bibliothekssystem, wo Bücher verschiedene Formate und Attribute haben können: physische Bücher, eBooks und Hörbücher. Jedes Format hat spezielle Eigenschaften, wie die Seitenzahl bei physischen Büchern, die Dateigröße bei eBooks oder die Spielzeit bei Hörbüchern. Das Abstract Document Design Pattern ermöglicht es dem Bibliothekssystem, diese verschiedenen Formate flexibel zu verwalten. Durch die Verwendung dieses Patterns kann das System Eigenschaften dynamisch speichern und abrufen, ohne dass eine starre Struktur für jeden Buchtyp erforderlich ist. Damit wird es einfacher, in der Zukunft neue Formate oder Attribute hinzuzufügen, ohne erhebliche Änderungen am Code vornehmen zu müssen.

In einfachen Worten

> Abstract Document erlaubt das Anhängen von Eigenschaften an Objekte, ohne dass diese davon wissen.

Wikipedia sagt

> Ein objektorientiertes strukturelles Design Pattern zur Organisation von Objekten in schwach typisierten Schlüssel-Wert-Speichern mit Darstellung der Daten über typisierte Ansichten. Der Zweck des Patterns besteht darin, einen hohen Grad an Flexibilität zwischen Komponenten in einer stark typisierten Sprache zu erreichen, in der neue Eigenschaften zur Objektstruktur dynamisch hinzugefügt werden können, ohne die Unterstützung der Typsicherheit zu verlieren. Das Pattern verwendet Traits, um verschiedene Klasseneigenschaften in verschiedene Schnittstellen abzutrennen.

## Klassendiagramm

![Abstract Document](/abstract-document/etc/abstract-document.png "Abstract Document Traits und Domain")

## Beispielprogramm in Java

Betrachten Sie ein Auto, das aus mehreren Teilen besteht. Wir wissen jedoch nicht, 
ob das spezifische Auto wirklich alle Teile hat oder nur einige davon. 
Unsere Autos sind dynamisch und extrem flexibel.

Zunächst definieren wir die Basisklassen `Document` und `AbstractDocument`. 
Sie sorgen im Wesentlichen dafür, dass jedes Objekt eine Map von Eigenschaften und eine beliebige Anzahl von Kindobjekten enthält.

```java
public interface Document {

    Void put(String key, Object value);

    Object get(String key);

    <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}

public abstract class AbstractDocument implements Document {

    private final Map<String, Object> properties;

    protected AbstractDocument(Map<String, Object> properties) {
        Objects.requireNonNull(properties, "properties map is required");
        this.properties = properties;
    }

    @Override
    public Void put(String key, Object value) {
        properties.put(key, value);
        return null;
    }

    @Override
    public Object get(String key) {
        return properties.get(key);
    }

    @Override
    public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
        return Stream.ofNullable(get(key))
                .filter(Objects::nonNull)
                .map(el -> (List<Map<String, Object>>) el)
                .findAny()
                .stream()
                .flatMap(Collection::stream)
                .map(constructor);
    }
    
    // Weitere Eigenschaften und Methoden...
}
```

Als nächstes definieren wir ein Enum `Property` und je eine Schnittstelle für Typ, Preis, Modell und Teile. So können wir eine statisch aussehende Schnittstelle für unsere Klasse `Car` erstellen.

```java
public enum Property {

    PARTS, TYPE, PRICE, MODEL
}

public interface HasType extends Document {

    default Optional<String> getType() {
        return Optional.ofNullable((String) get(Property.TYPE.toString()));
    }
}

public interface HasPrice extends Document {

    default Optional<Number> getPrice() {
        return Optional.ofNullable((Number) get(Property.PRICE.toString()));
    }
}

public interface HasModel extends Document {

    default Optional<String> getModel() {
        return Optional.ofNullable((String) get(Property.MODEL.toString()));
    }
}

public interface HasParts extends Document {

    default Stream<Part> getParts() {
        return children(Property.PARTS.toString(), Part::new);
    }
}
```

Nun können wir das `Car` einführen.

```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

    public Car(Map<String, Object> properties) {
        super(properties);
    }
}
```
Und schließlich konstruieren und verwenden wir ein solches `Car`.

```java  
public static void main(String[] args) {
    LOGGER.info("Constructing parts and car");

var wheelProperties = Map.of(
        Property.TYPE.toString(), "wheel",
        Property.MODEL.toString(), "15C",
        Property.PRICE.toString(), 100L);

var doorProperties = Map.of(
        Property.TYPE.toString(), "door",
        Property.MODEL.toString(), "Lambo",
        Property.PRICE.toString(), 300L);

var carProperties = Map.of(
        Property.MODEL.toString(), "300SL",
        Property.PRICE.toString(), 10000L,
        Property.PARTS.toString(), List.of(wheelProperties, doorProperties));

var car = new Car(carProperties);

    LOGGER.info("Here is our car:");
    LOGGER.info("-> model: {}", car.getModel().orElseThrow());
        LOGGER.info("-> price: {}", car.getPrice().orElseThrow());
        LOGGER.info("-> parts: ");
    car.getParts().forEach(p -> LOGGER.info("\t{}/{}/{}",
            p.getType().orElse(null),
            p.getModel().orElse(null),
            p.getPrice().orElse(null))
    );
}
```
Programmausgabe:

```
07:21:57.391 [main] INFO com.iluwatar.abstractdocument.App -- Constructing parts and car
07:21:57.393 [main] INFO com.iluwatar.abstractdocument.App -- Here is our car:
07:21:57.393 [main] INFO com.iluwatar.abstractdocument.App -- -> model: 300SL
07:21:57.394 [main] INFO com.iluwatar.abstractdocument.App -- -> price: 10000
07:21:57.394 [main] INFO com.iluwatar.abstractdocument.App -- -> parts: 
07:21:57.395 [main] INFO com.iluwatar.abstractdocument.App -- 	wheel/15C/100
07:21:57.395 [main] INFO com.iluwatar.abstractdocument.App -- 	door/Lambo/300
```

## Verwendung

Abstract Document ist besonders vorteilhaft in Szenarien, die eine Verwaltung unterschiedlicher 
Dokumenttypen erfordern, die zwar einige gemeinsame Attribute oder Verhaltensweisen teilen, 
aber auch typspezifische Eigenschaften haben. Hier sind einige Beispiele:

* **Content-Management-Systeme (CMS)**: In einem CMS könnten verschiedene Arten von Inhalten wie Artikel, Bilder, Videos usw. vorkommen. Jede Inhaltsart könnte gemeinsame Attribute wie Erstellungsdatum, Autor und Tags haben, aber auch spezifische Attribute wie Bildabmessungen für Bilder oder Videodauer für Videos.

* **Dateisysteme**: Wenn Sie ein Dateisystem entwerfen, in dem unterschiedliche Dateitypen verwaltet werden müssen, wie Dokumente, Bilder, Audiodateien und Verzeichnisse, kann das Pattern helfen, einen einheitlichen Zugriff auf Attribute wie Dateigröße, Erstellungsdatum usw. zu bieten, aber zugleich spezifische Attribute wie Bildauflösung oder Audiodauer zu berücksichtigen.

* **E-Commerce-Systeme**: Eine E-Commerce-Plattform kann verschiedene Produkttypen haben, 
wie physische Produkte, digitale Downloads und Abonnements. 
Alle Typen haben gemeinsame Attribute wie Name, Preis und Beschreibung, 
aber auch einzigartige Attribute wie Versandgewicht für physische Produkte 
oder Download-Link für digitale Produkte.

* **Medizindatensysteme**: Im Gesundheitswesen enthalten Patientenakten verschiedene 
Datentypen wie demografische Daten, medizinische Vorgeschichte, Testergebnisse und Rezepte. 
Das Abstract Document Pattern kann helfen, gemeinsame Attribute wie Patienten-ID und Geburtsdatum zu 
verwalten und zusätzlich spezielle Attribute wie Testergebnisse oder verschriebene Medikamente zu berücksichtigt werden.

* **Konfigurationsmanagement**: Bei der Verwaltung von Konfigurationseinstellungen für 
Software gibt es verschiedene Arten von Konfigurationselementen, jedes mit einer eigenen Reihe 
von Attributen. Mit dem Abstract Document Pattern können diese Konfigurationselemente 
konsistent verwaltet werden.

* **Bildungsplattformen**: Bildungssysteme nutzen verschiedene Arten von Lernmaterialien wie textbasierte Inhalte, 
Videos, Quiz und Übungsaufgaben. Gemeinsame Attribute können Titel, Autor und Veröffentlichungsdatum sein, 
während spezifische Attribute wie Videodauer oder Aufgabenfälligkeit typabhängig sind.

* **Projektmanagement-Tools**: In Projektmanagement-Anwendungen können unterschiedliche Aufgabenarten 
wie To-Dos, Meilensteine und Probleme vorliegen. Das Abstract Document Pattern kann verwendet werden, um allgemeine 
Attribute wie Aufgabenname und Zuständigkeit zu handhaben, während spezifische Attribute wie Meilensteindaten oder
Aufgabenprioritäten zugelassen sind.

* **Dokumente haben vielfältige und sich verändernde Attributstrukturen.**

* **Häufig ist es erforderlich, neue Eigenschaften dynamisch hinzuzufügen.**

* **Entscheidend ist die Entkopplung des Datenzugriffs von spezifischen Formaten.**

* **Wartbarkeit und Flexibilität sind wesentlich für die Codebasis.**

Die Kernidee des Abstract Document Pattern ist es, eine flexible und erweiterbare Möglichkeit zur Verwaltung 
unterschiedlicher Dokumenttypen oder Entitäten mit gemeinsamen und spezifischen Attributen bereitzustellen. 
Durch die Definition einer gemeinsamen Schnittstelle und deren Implementierung über verschiedene Dokumenttypen 
hinweg können Sie komplexe Datenstrukturen besser organisiert und einheitlich verarbeiten.

## Vor- und Nachteile

**Vorteile:**

* **Flexibilität**: Ermöglicht die Handhabung unterschiedlicher Dokumentstrukturen und -eigenschaften.
* **Erweiterbarkeit**: Neue Attribute können dynamisch hinzugefügt werden, ohne bestehenden Code zu brechen.
* **Wartbarkeit**: Fördert sauberen und anpassungsfähigen Code durch Trennung der Verantwortlichkeiten.
* **Wiederverwendbarkeit**: Typspezifische Ansichten ermöglichen eine Wiederverwendung des Codes zum Zugriff auf bestimmte Attributtypen.

**Nachteile:**

* **Komplexität**: Die Definition von Schnittstellen und spezifischen Ansichten erfordert zusätzlichen Implementierungsaufwand.
* **Performance**: Leicht verringerte Performance im Vergleich zum direkten Datenzugriff möglich.

## Quellen

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
* [Pattern-Oriented Software Architecture Volume 4: A Pattern Language for Distributed Computing (v. 4)](https://amzn.to/49zRP4R)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Abstract Document Pattern (Wikipedia)](https://en.wikipedia.org/wiki/Abstract_Document_Pattern)
* [Dealing with Properties (Martin Fowler)](http://martinfowler.com/apsupp/properties.pdf)





