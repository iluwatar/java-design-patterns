---
shortTitle: Adapter
category: Structural
language: de
tag:
  - Compatibility
  - Decoupling
  - Gang of Four
  - Interface
  - Object composition
  - Wrapping
---

## Alternativbezeichnung

* Wrapper

## Zweck

Das Adapter-Pattern konvertiert die Schnittstelle einer Klasse in eine andere Schnittstelle, die zu den Bedürfnissen der Anwender passt, und sorgt so für Kompatibilität.

## Detaillierte Erklärung
Vergleich mit der analogen Welt

> Stellen Sie sich vor, Sie haben einige Bilder auf Ihrer Speicherkarte und wollen diese auf
> Ihren Computer übertragen. Dafür brauchen Sie einen Kartenleser, der mit den Anschlüssen des Rechners
> kompatibel ist, sodass dieser auf die Daten auf der Karten zugreifen kann. Dieser Kartenleser
> ist ein Beispiel für einen Adapter.
> Ein anderes Beispiel sind die Adapter, mit denen man deutsche Netzstecker in ausländischen
> Steckdosen verwenden kann, in die sie sonst nicht passen würden. Auch ein Übersetzer, der die
> Worte eines Sprechers in die Sprache des ausländischen Publikums übersetzt, erfüllt die Funktion eines Adapters.

In einfachen Worten

> Das Adapter-Pattern verpackt ein normalerweise inkompatibles Objekt so, dass es mit einer
> anderen Klasse kompatibel wird.

Wikipedia sagt

> Das Adapter-Pattern in der Softwareentwicklung ist ein Entwurfsmuster,
> das die Schnittstelle einer bestehenden Klasse im Rahmen einer neuen Schnittstelle nutzbar macht.
> Es wird oft verwendet, damit Klassen zusammenarbeiten können, ohne ihren Quellcode zu verändern.

Ablaufdiagramm

![Adapter sequence diagram](/adapter/etc/adapter-sequence-diagram.png "Adapter sequence diagram")

## Programmbeispiel

Betrachten wir einen Möchtegern-Kapität, der zwar rudern kann, aber noch nie gesegelt ist.

Wir beginnen mit dem Interface `RowingBoat` und der Klasse `FishingBoat`

```java
public interface RowingBoat {
    void row();
}

public class FishingBoat {
    public void sail() {
        LOGGER.info("The fishing boat is sailing");
    }
}
```

Der Kapität erwartet, dass sich ein Schiff mit der `row`-Methode von `RowingBoat` steuern lässt.

```java
public class Captain {

    private final RowingBoat rowingBoat;

    public Captain(RowingBoat rowingBoat) {
        this.rowingBoat = rowingBoat;
    }

    public void row() {
        rowingBoat.row();
    }
}
```

Nun kommen Piraten und unser Kapitän muss fliehen, hat aber nur ein Fischerboot zur Verfügung.
Wir brauchen einen Adapter, mit dem er auch dieses Boot durch Rudern steuern kann.

```java
public class FishingBoatAdapter implements RowingBoat {

    private final FishingBoat boat;

    public FishingBoatAdapter() {
        boat = new FishingBoat();
    }

    @Override
    public void row() {
        boat.sail();
    }
}
```

Nun segelt das `FishingBoat`, indem der Kapitän rudert, und er kann fliehen.

```java
  public static void main(final String[] args) {
    // The captain can only operate rowing boats but with adapter he is able to
    // use fishing boats as well
    var captain = new Captain(new FishingBoatAdapter());
    captain.row();
}
```

Programmausgabe:

```
10:25:08.074 [main] INFO com.iluwatar.adapter.FishingBoat -- The fishing boat is sailing
```

## Verwendung
Das Adapter-Pattern ist geeignet für diese Fälle
* Sie wollen eine bestehende Klasse benutzen, deren Schnittstelle anders als benötigt ist.
* Sie wollen eine wiederverwendbare Klasse schreiben, die auch mit fremden Klassen zusammen
  arbeiten kann, die nicht unbedingt kompatible Schnittstellen haben.
* Sie müssen verschiedene Tochterklassen verwenden, bei denen es zu aufwendig wäre, zu jeder einzelnen eine Subklasse mit angepasster Schnittstelle zu erstellen.
  Ein Adapter kann die Schnittstelle der Elternklasse bedarfsgerecht anpassen.
* Die meisten Anwendungen, die Fremdbibliotheken verwenden, nutzen einen Adapter für die Fremdklassen,
  um die eigenen Anwendung vom fremden zu entkoppeln. 

## Tutorials

* [Using the Adapter Design Pattern in Java (Dzone)](https://dzone.com/articles/adapter-design-pattern-in-java)
* [Adapter in Java (Refactoring Guru)](https://refactoring.guru/design-patterns/adapter/java/example)
* [The Adapter Pattern in Java (Baeldung)](https://www.baeldung.com/java-adapter-pattern)
* [Adapter Design Pattern (GeeksForGeeks)](https://www.geeksforgeeks.org/adapter-pattern/)

## Vor- und Nachteile

Die Vor- und Nachteile hängen davon ab, ob ein Klassen- oder ein Objektadapter implementiert wird.

Ein Klassenadapter adaptiert durch Bindung an eine spezifische zu adaptierende Klasse. 
Er kann das Verhalten der zu adaptierenden Klasse überschreiben, weil er
als deren Tochterklasse implementiert wird. 
Das hat allerdings zur Folge, dass mit einer Klasse nicht auch all ihre Subklassen adaptiert werden können.
Bei diesem Adaptertyp wird lediglich ein neues Objekt eingeführt, ohne
dass mit einem extra Zeiger der Zugriff darauf ermöglicht werden muss.

Ein Objektadapter hingegen kann mit verschiedenen zu adaptierenden Klassen arbeiten, auch mit
allen Subklassen. Er kann  allen adaptierten Klassen zugleich Funktionalität hinzufügen.
Allerdings wird es damit schwerer, das Verhalten zu überschreiben, weil man dafür eine 
Subklasse der adaptierten Klasse benötigt und der Adapter sich auf diese Subklasse statt
auf die ursprünglich adaptierte Klasse beziehen muss.

## Reale Java-Anwendungen

* `java.io.InputStreamReader` and `java.io.OutputStreamWriter` in der Java-IO-Bibliothek.
* GUI-Komponentenbibliotheken, die per Plugin oder Adapter zwischen verschiedenen Kompontentenschnittstellen konvertieren können.
* [java.util.Arrays#asList()](http://docs.oracle.com/javase/8/docs/api/java/util/Arrays.html#asList%28T...%29)
* [java.util.Collections#list()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#list-java.util.Enumeration-)
* [java.util.Collections#enumeration()](https://docs.oracle.com/javase/8/docs/api/java/util/Collections.html#enumeration-java.util.Collection-)
* [javax.xml.bind.annotation.adapters.XMLAdapter](http://docs.oracle.com/javase/8/docs/api/javax/xml/bind/annotation/adapters/XmlAdapter.html#marshal-BoundType-)

## Quellen

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
