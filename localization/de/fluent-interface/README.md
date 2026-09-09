---
shortTitle: Fluent Interface
category: Behavioral
language: de
tag: 
  - API design
  - Code simplification
  - Decoupling
  - Object composition
  - Reactive
---

## Alternativbezeichnungen

* Fluent API
* Method Chaining

## Zweck

Primäres Ziel des Fluent-Interface-Pattern ist es, eine gut lesbare sprechende API zur
Verfügung zu stellen, indem Methodenaufrufe einfach verkettet werden können (Method Chaining).
Dieser Ansatz ist ideal, um komplexe Objekte schrittweise zu bauen und generell ein angenehmeres
Programmiererlebnis zu erreichen.

## Detaillierte Erklärung

Vergleichsbeispiel

> Die Bestellung eines frei konfigurierbaren Kaffees in einer Kaffeebar funktioniert ähnlich
> wie das Fluent-Interface-Pattern.
> Dabei teilen Sie dem Barista nicht all ihre Wünsche auf einmal mit, sondern nennen schrittweise
> eine Auswahl nach der anderen, in einem natürlichen Fluss. Sie sagen beispielsweise "Ich möchte
> einen großen großen Kaffee, mit zwei Espresso-Shots, ohne Zucker, plus Mandelmilch".
> Diese Aneinanderhängung von Wünschen entspricht dem Verketten von Methodenaufrufen
> beim Fluent-Interface-Pattern, wodurch der Code zu Objektkonfiguration intuitiv lesbar wird.
> So wie eine Kaffeekomponente nach der anderen ausgewählt wird, wird im Code eine Methode nach der anderen
> ausgeführt.

In einfachen Worten:

> Fluent Interface bietet eine sprechende, leicht lesbare Schnittstelle zum Code.

Wikipedia sagt:

> In der Softwareentwicklung ist Fluent Interface eine objektorientierte API, deren Design auf
> ausgiebiger Verkettung von Methoden beruht. Ziel ist verbesserte Lesbarkeit des Codes
> durch eine domänenspezifische Sprache (DSL, domain specific language).

Ablaufdiagramm

![Fluent Interface sequence diagram](/fluent-interface/etc/fluent-interface-sequence-diagram.png)

## Programmbeispiel

Wir wollen Zahlen aus einer Liste nach verschiedenen Kriterien auswählen. Dabei können
wir die Lesbarkeit des Codes gut mit dem Fluent-Interface-Pattern verbessern.

Der Beispielcode enthält zwei Implementationen eines `FluentIterable`-Interfaces.

```java
public interface FluentIterable<E> extends Iterable<E> {

  FluentIterable<E> filter(Predicate<? super E> predicate);

  Optional<E> first();

  FluentIterable<E> first(int count);

  Optional<E> last();

  FluentIterable<E> last(int count);

  <T> FluentIterable<T> map(Function<? super E, T> function);

  List<E> asList();

  static <E> List<E> copyToList(Iterable<E> iterable) {
    var copy = new ArrayList<E>();
    iterable.forEach(copy::add);
    return copy;
  }
}
```

`SimpleFluentIterable` betreibt eifrige Auswertung und wäre für eine reale Anwendung zu rechenintensiv.

```java
public class SimpleFluentIterable<E> implements FluentIterable<E> {
  // ...
}
```
`LazyFluentIterable` wertet erst bei Terminierung der Kette aus.

```java
public class LazyFluentIterable<E> implements FluentIterable<E> {
  // ...
}
```

Ihre Verwendung zeigen wir mit einer simplen Zahlenliste, die gefiltert, transformiert
und zu einer neuen Liste zusammengestellt wird. Das Ergebnis wird anschließend ausgegeben.

```java
public static void main(String[] args) {

    var integerList = List.of(1, -61, 14, -22, 18, -87, 6, 64, -82, 26, -98, 97, 45, 23, 2, -68);

    prettyPrint("The initial list contains: ", integerList);

    var firstThreeNegatives = SimpleFluentIterable
            .fromCopyOf(integerList)
            .filter(negatives())
            .first(3)
            .asList();
    prettyPrint("The first three negative values are: ", firstThreeNegatives);


    var lastTwoPositives = SimpleFluentIterable
            .fromCopyOf(integerList)
            .filter(positives())
            .last(2)
            .asList();
    prettyPrint("The last two positive values are: ", lastTwoPositives);

    SimpleFluentIterable
            .fromCopyOf(integerList)
            .filter(number -> number % 2 == 0)
            .first()
            .ifPresent(evenNumber -> LOGGER.info("The first even number is: {}", evenNumber));


    var transformedList = SimpleFluentIterable
            .fromCopyOf(integerList)
            .filter(negatives())
            .map(transformToString())
            .asList();
    prettyPrint("A string-mapped list of negative numbers contains: ", transformedList);


    var lastTwoOfFirstFourStringMapped = LazyFluentIterable
            .from(integerList)
            .filter(positives())
            .first(4)
            .last(2)
            .map(number -> "String[" + number + "]")
            .asList();
    prettyPrint("The lazy list contains the last two of the first four positive numbers "
            + "mapped to Strings: ", lastTwoOfFirstFourStringMapped);

    LazyFluentIterable
            .from(integerList)
            .filter(negatives())
            .first(2)
            .last()
            .ifPresent(number -> LOGGER.info("Last amongst first two negatives: {}", number));
}
```

Programmausgabe:

```
08:50:08.260 [main] INFO com.iluwatar.fluentinterface.app.App -- The initial list contains: 1, -61, 14, -22, 18, -87, 6, 64, -82, 26, -98, 97, 45, 23, 2, -68.
08:50:08.265 [main] INFO com.iluwatar.fluentinterface.app.App -- The first three negative values are: -61, -22, -87.
08:50:08.265 [main] INFO com.iluwatar.fluentinterface.app.App -- The last two positive values are: 23, 2.
08:50:08.266 [main] INFO com.iluwatar.fluentinterface.app.App -- The first even number is: 14
08:50:08.267 [main] INFO com.iluwatar.fluentinterface.app.App -- A string-mapped list of negative numbers contains: String[-61], String[-22], String[-87], String[-82], String[-98], String[-68].
08:50:08.270 [main] INFO com.iluwatar.fluentinterface.app.App -- The lazy list contains the last two of the first four positive numbers mapped to Strings: String[18], String[6].
08:50:08.270 [main] INFO com.iluwatar.fluentinterface.app.App -- Last amongst first two negatives: -22
```

## Verwendung

* Zum Entwurf vielgenutzter APIs, bei denen die Lesbarkeit des sie verwendenden Codes besonders wichtig ist.
* Zur schrittweisen Konstruktion komplexer Objekte mit intuitivem und weniger fehleranfälligem Code.
* Zur Verbesserung der Übersichtlichkeit des Codes und Reduktion von Boilerplate-Code,
  speziell bei Konfigurationen und Objektkonstruktion.

## Tutorials

* [An Approach to Internal Domain-Specific Languages in Java (InfoQ)](http://www.infoq.com/articles/internal-dsls-java)

## Reale Anwendungen in Java

* [Java 8 Stream API](http://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html)
* [Google Guava FluentIterable](https://github.com/google/guava/wiki/FunctionalExplained)
* [JOOQ](http://www.jooq.org/doc/3.0/manual/getting-started/use-cases/jooq-as-a-standalone-sql-builder/)
* [Mockito](http://mockito.org/)
* [Java Hamcrest](http://code.google.com/p/hamcrest/wiki/Tutorial)
* Builder in Bibliotheken wie Apache Camel für den Integrations-Workflow.

## Vor- und Nachteile

Vorteile:

* Signifikant erhöhte Lesbarkeit und Wartbarkeit des Codes
* Unterstützt die Konstruktion unveränderlicher Objekte, weil die Methoden typischerweise neue Instanzen zurückgeben.
* Weniger Variablen nötig, da der Kontext durch die Aufrufkette klar wird.

Nachteile:

* Für Neulinge ist der Code weniger intuitiv.
* Verkettung von Methoden erschwert das Debuggen.
* Übermäßige Verwendung kann zu komplexem schwer wartbarem Code führen.

## Verwandte Patterns

* [Builder](https://java-design-patterns.com/patterns/builder/): Verwendet oft Fluent Interface zur schrittweisen Konstruktion. Bei Builder geht es um die Konstruktion komplexer Objekte, bei Fluent Interface um die Methodenverkettung.
* [Chain of Responsibility](https://java-design-patterns.com/patterns/chain-of-responsibility/): Fluent Interfaces können als spezielle Verwendung von Chain of Responsibility betrachtet werden, wo jede Methode in der Kette eine Teilaufgabe behandelt und ihr Ergebnis an die nächste Methode weiterreicht.

## Quellen

* [Domain-Driven Design: Tackling Complexity in the Heart of Software](https://amzn.to/3UrXkh2)
* [Domain Specific Languages](https://amzn.to/3R1UYDA)
* [Effective Java](https://amzn.to/4d4azvL)
* [Java Design Pattern Essentials](https://amzn.to/44bs6hG)
* [Fluent Interface (Martin Fowler)](http://www.martinfowler.com/bliki/FluentInterface.html)
