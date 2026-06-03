---
shortTitle: Chain of Responsibility
category: Behavioral
language: de
tag:
  - Decoupling
  - Event-driven
  - Gang of Four
  - Messaging
---

## Alternativbezeichnungen

* Chain of Command
* Chain of Objects
* Responsibility Chain
* deutsch: Zuständigkeitskette

## Zweck

Chain of Responsibility ist ein Verhaltensmuster,
das den Sender einer Anfrage von deren Empfängern entkoppelt, indem mehrere Objekte die 
Gelegenheit zum Bearbeiten der Anfrage erhalten. Die empfangenden Objekte sind miteinander
verkettet und die Anfrage wird so lange entlang der Kette weitergereicht, bis ein Objekt
sie bearbeitet.

## Detaillierte Erklärung

Vergleichsbeispiel

> Die Chain of Responsibility gleicht der Arbeit in einem Call-Center für technischen
Kundendienst. Jedes Support-Level entspricht einem Handler in der Kette.
Wenn ein Kunde wegen eines Problems anruft, landet der Anruf bei einem Mitarbeiter des ersten
Levels. Einfache Probleme kann dieser direkt lösen. Wenn der Fall schwieriger ist, leitet
er den Anruf an einen Second-Level-Kollegen weiter. Dieser Prozess läuft über verschiedene
Stufen so lange weiter, bis ein Fachmann gefunden wurde, der das Problem löst.
Indem der Anruf entlang der Kette bis zur passenden Stelle weitergereicht wird,
erreicht man eine Entkopplung zwischen der Anfrage (Anruf) und ihrem Empfänger (jeweiliger
Mitarbeiter).

In einfachen Worten

> Das Pattern hilft beim Bau einer Kette von Objekten. Eine Anfrage kommt auf einer Seite herein
und wird von einem Objekt zum nächsten weitergereicht, bis der passende Handler für sie gefunden ist.

Wikipedia sagt

> Das Design-Pattern Chain of Responsibility besteht aus einer Quelle für Befehlsobjekt
und einer Reihe von Bearbeiterobjekten. Jedes Bearbeiterobjekt enthält eine Entscheidungslogik, welche
Art von Befehlsobjekten es selbst bearbeiten kann. Die übrigen leitet es an das nächste
Objekt in der Kette weiter.

Ablaufdiagramm

![Chain of Responsibility flowchart](/chain-of-responsibility/etc/chain-of-responsibility-flowchart.png)

## Programmbeispiel

In diesem Java-Beispiel gibt der König der Orcs lautstarke Befehle an seine Armee, die von 
einer Kette Untergebener ausgeführt werden. Am nächsten zum König steht sein General,
dann ein Offizier und schließlich ein einfacher Soldat.

Zuerst definieren wir für die Befehle die Klasse `Request`.

```java
public class Request {

    private final RequestType requestType;
    private final String requestDescription;
    private boolean handled;

    public Request(final RequestType requestType, final String requestDescription) {
        this.requestType = Objects.requireNonNull(requestType);
        this.requestDescription = Objects.requireNonNull(requestDescription);
    }

    public void markHandled() {
        this.handled = true;
    }

    @Override
    public String toString() {
        return getRequestDescription();
    }
}

public enum RequestType {
    DEFEND_CASTLE, TORTURE_PRISONER, COLLECT_TAX
}
```

Hier sehen wir die Hierarchie der `RequestHandler`.

```java
public interface RequestHandler {

    boolean canHandleRequest(Request req);

    int getPriority();

    void handle(Request req);

    String name();
}

public class OrcCommander implements RequestHandler {
    @Override
    public boolean canHandleRequest(Request req) {
        return req.getRequestType() == RequestType.DEFEND_CASTLE;
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public void handle(Request req) {
        req.markHandled();
        LOGGER.info("{} handling request \"{}\"", name(), req);
    }

    @Override
    public String name() {
        return "Orc commander";
    }
}

// OrcOfficer und OrcSoldier sind ähnlich definiert

```

Der `OrcKing` gibt den Befehl und baut die Kette (geordnet nach Priorität) auf:

```java
public class OrcKing {

    private List<RequestHandler> handlers;

    public OrcKing() {
        buildChain();
    }

    private void buildChain() {
        handlers = Arrays.asList(new OrcCommander(), new OrcOfficer(), new OrcSoldier());
    }

    public void makeRequest(Request req) {
        handlers
                .stream()
                .sorted(Comparator.comparing(RequestHandler::getPriority))
                .filter(handler -> handler.canHandleRequest(req))
                .findFirst()
                .ifPresent(handler -> handler.handle(req));
    }
}
```

Hier erhält die Kette verschiedene Befehle:

```java
  public static void main(String[] args) {

    var king = new OrcKing();
    king.makeRequest(new Request(RequestType.DEFEND_CASTLE, "defend castle"));
    king.makeRequest(new Request(RequestType.TORTURE_PRISONER, "torture prisoner"));
    king.makeRequest(new Request(RequestType.COLLECT_TAX, "collect tax"));
}
```

Ausgabe:

```
Orc commander handling request "defend castle"
Orc officer handling request "torture prisoner"
Orc soldier handling request "collect tax"
```

## Verwendung

* Wenn mehrere Objekte eine Anfrage bearbeiten können und der zuständige Verarbeiter nicht
vorab bekannt ist. Der Verarbeiter sollte automatisch ermittelt werden.
* Wenn eine Anfrage an eines von mehreren Objekten gesendet wird, ohne den Empfänger explizit festzulegen.
* Bei dynamischer Zusammenstellung der Menge möglicher Bearbeiter.

## Reale Anwendungen in Java

* Event-Bubbling in GUI-Frameworks, wo ein Event in verschiedenen Stufen der Komponentenhierachie bearbeitet werden kann.
* Middleware-Frameworks, bei denen Anfragen eine Kette möglicher Verarbeiter durchlaufen.
* Logging-Frameworks, bei denen Nachrichten eine Reihe von Loggern durchlaufen, von denen sie verschieden behandelt werden können.
* [java.util.logging.Logger#log()](http://docs.oracle.com/javase/8/docs/api/java/util/logging/Logger.html#log%28java.util.logging.Level,%20java.lang.String%29)
* [Apache Commons Chain](https://commons.apache.org/proper/commons-chain/index.html)
* [javax.servlet.Filter#doFilter()](http://docs.oracle.com/javaee/7/api/javax/servlet/Filter.html#doFilter-javax.servlet.ServletRequest-javax.servlet.ServletResponse-javax.servlet.FilterChain-)

## Vor- und Nachteile

Vorteile:

* Weniger Kopplung. Der Sender einer Anfrage muss nicht wissen, von wem diese bearbeitet wird.
* Flexibilität bei der Zuweisung von Verantwortlichkeiten durch Umbildung der Kette.
* Für den Fall, dass kein Kettenglied die Anfrage bearbeiten kann, kann ein Default-Verarbeiter festgelegt werden.

Nachteile:

* Bei langen und komplexen Ketten kann der Ablauf schwer zu durchschauen und zu debuggen sein.
* Eine Anfrage kann unbearbeitet bleiben, wenn ein passender Bearbeiter fehlt.
* Der (möglicherweise auch vergebliche) Durchlauf durch mehrere Verarbeiter kann die Peformance beeinträchtigen.

## Verwandte Patterns

* [Command](https://java-design-patterns.com/patterns/command/): Kann verwendet werden zur Kapselung der Anfrage in ein Objekt, das durch die Kette läuft.
* [Composite](https://java-design-patterns.com/patterns/composite/): Wird oft gemeinsam mit Chain of Responsibility verwendet.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): Decorators können ähnlich wie Verantwortlichkeiten verkettet werden.

## Quellen

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [Pattern-Oriented Software Architecture, Volume 1: A System of Patterns](https://amzn.to/3PAJUg5)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
* [Pattern languages of program design 3](https://amzn.to/4a4NxTH)
