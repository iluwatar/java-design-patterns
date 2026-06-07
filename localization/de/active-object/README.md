---
shortTitle: Active Object
category: Concurrency
language: de
tag:
  - Asynchronous
  - Decoupling
  - Messaging
  - Synchronization
  - Thread management
---

## Zweck

Active Object bietet eine zuverlässige Methode zur Behandlung asynchroner Prozesse, mit der reaktionsfähige Anwendungen 
und effizientes Thread-Management gesichert werden.
Dies wird dadurch erreicht, dass die einzelnen Aufgaben in Objekte gekapselt werden, die in eigenen Threads (Steuerungsflüssen)
mit eigener Nachrichtenwarteschlange aktiv sind. Durch diese Trennung bleibt der Hauptthread
reaktionsfähig und Probleme wie direkte Threadmanipulation oder gemeinsamer Zugriff auf Zustände werden vermieden.

## Detaillierte Erklärung

Reales Beispiel

> Stellen Sie sich ein gut besuchtes Restaurant vor, in dem die Gäste Bestellungen bei den
> Kellner aufgeben. Die Kellner gehen nicht selbst in die Küche, um die Essen selbst zuzubereiten,
> sondern sie schreiben die Bestellungen auf Zettel und geben diese dem Küchenmanager.
> Der Manager organisiert eine Gruppe von Köchen, die die verschiedenen Mahlzeiten parallel zubereiten.
> Wenn ein Koch frei ist, nimmt er eine Bestellung aus der Warteschlange, bereitet das Essen zu
> und benachrichtigt den Kellner, sobald es fertig zum Servieren ist.
>
> In dieser Analogie stehen die Kellner für die Client-Threads,
> der Küchenmanager für den Thread-Scheduler, und die Köche für die Methodenausführung
> in verschiedenen Threads.
> Die Organisation ermöglicht es, dass die Kellner immer weiter Bestellungen annehmen können,
> ohne durch die Essenszubereitung aufgehalten zu werden; so wie das Active-Object-Pattern
> den Methodenaufruf von der Ausführung trennt, um die Effizienz zu verbessern.

In einfachen Worten

> Das Active-Object-Pattern trennt Methodenausführung und Methodenaufruf,
> um Parallelitätsgrad und Reaktionsfähigkeit in Multithread-Anwendungen zu verbessern.

Wikipedia sagt

> Das Design-Pattern Active Object entkoppelt die Methodenausführung vom Methodenaufruf
> für Objekte, die in ihrem jeweils eigenen Thread arbeiten.
> Ziel ist, Parallelität dadurch zu ermöglichen, dass Methoden asynchron aufgerufen werden und ein
> Scheduler die Anfragen organisiert.
>
> Das Pattern besteht aus sechs Elementen.
>
> * Ein Proxy stellt für Clients ein Interface mit öffentlich zugänglichen Methoden zur Verfügung.
> * Ein weiteres Interface definiert die Anfragen an ein aktives Objekt.
> * Eine Liste offener Client-Anfragen.
> * Ein Scheduler entscheidet, welche Anfrage als nächstes ausgeführt wird.
> * Die Implementation der Methoden.
> * Eine Callback-Variable zur Rückmeldung des Ergebnisses.

Ablaufdiagramm

![Active Object sequence diagram](/active-object/etc/active-object-sequence-diagram.png)


## Programmbeispiel in Java

Die Orcs sind wilde und nicht zu bändigende Kreaturen. Anscheinend haben sie ihre eigene Steuerung, die nur von ihrem vorherigen Verhalten bestimmt wird.
Um eine derartige Kreatur zu implementieren, können wir das Active-Objekt-Pattern benutzen.

```java
public abstract class ActiveCreature {
    private final Logger logger = LoggerFactory.getLogger(ActiveCreature.class.getName());

    private BlockingQueue<Runnable> requests;

    private String name;

    private Thread thread;

    public ActiveCreature(String name) {
        this.name = name;
        this.requests = new LinkedBlockingQueue<Runnable>();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        requests.take().run();
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage());
                    }
                }
            }
        }
        );
        thread.start();
    }

    public void eat() throws InterruptedException {
        requests.put(new Runnable() {
                         @Override
                         public void run() {
                             logger.info("{} is eating!", name());
                             logger.info("{} has finished eating!", name());
                         }
                     }
        );
    }

    public void roam() throws InterruptedException {
        requests.put(new Runnable() {
                         @Override
                         public void run() {
                             logger.info("{} has started to roam the wastelands.", name());
                         }
                     }
        );
    }

    public String name() {
        return this.name;
    }
}
```

Man sieht, dass jede Klasse, die `ActiveCreature` erweitert, ihren eigenen Kontrollfluss für den Aufruf und die Ausführung der Methoden zum Herumstreifen und Essen hat.

Beispielsweise die Klasse `Orc`:

```java
public class Orc extends ActiveCreature {

    public Orc(String name) {
        super(name);
    }
}
```
Nun können wir etliche Kreaturen dieser Art schaffen, sie zum Essen und Herumstreifen auffordern, aber jede von ihnen wird das in Eigenregie (eigener Thread) ausführen.

```java
public class App implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(App.class.getName());

    private static final int NUM_CREATURES = 3;

    public static void main(String[] args) {
        var app = new App();
        app.run();
    }

    @Override
    public void run() {
        List<ActiveCreature> creatures = new ArrayList<>();
        try {
            for (int i = 0; i < NUM_CREATURES; i++) {
                creatures.add(new Orc(Orc.class.getSimpleName() + i));
                creatures.get(i).eat();
                creatures.get(i).roam();
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            for (int i = 0; i < NUM_CREATURES; i++) {
                creatures.get(i).kill(0);
            }
        }
    }
}
```

Programmausgabe:

```
09:00:02.501 [Thread-0] INFO com.iluwatar.activeobject.ActiveCreature -- Orc0 is eating!
09:00:02.501 [Thread-2] INFO com.iluwatar.activeobject.ActiveCreature -- Orc2 is eating!
09:00:02.501 [Thread-1] INFO com.iluwatar.activeobject.ActiveCreature -- Orc1 is eating!
09:00:02.504 [Thread-0] INFO com.iluwatar.activeobject.ActiveCreature -- Orc0 has finished eating!
09:00:02.504 [Thread-1] INFO com.iluwatar.activeobject.ActiveCreature -- Orc1 has finished eating!
09:00:02.504 [Thread-0] INFO com.iluwatar.activeobject.ActiveCreature -- Orc0 has started to roam in the wastelands.
09:00:02.504 [Thread-2] INFO com.iluwatar.activeobject.ActiveCreature -- Orc2 has finished eating!
09:00:02.504 [Thread-1] INFO com.iluwatar.activeobject.ActiveCreature -- Orc1 has started to roam in the wastelands.
09:00:02.504 [Thread-2] INFO com.iluwatar.activeobject.ActiveCreature -- Orc2 has started to roam in the wastelands.
```

## Verwendung

* Wenn asynchrone Aufgaben behandelt werden sollen, ohne dass der Hauptthread blockiert wird, um bessere Performance und Reaktionsfähigkeit zu gewährleisten.
* Bei asynchronen Interaktionen mit externen Ressourcen.
* Zur Verbesserung der Reaktionsfähigkeit.
* Zum Management parallel ablaufender Aufgaben in modularer und wartbarer Art und Weise.
* 
## Tutorials

* [Android and Java Concurrency: The Active Object Pattern(Douglas Schmidt)](https://www.youtube.com/watch?v=Cd8t2u5Qmvc)

## Reale Anwendungen in Java

* Echtzeit-Handelssysteme mit asynchroner Verarbeitung von Transaktionen.
* GUIs, bei denen langwierige Arbeiten im Hintergrund ablaufen, ohne dass die Benutzeroberfläche einfriert.
* Spiele, bei denen Aktualisierungen des Spielstatus oder KI-Berechnungen parallel abgearbeitet werden.

## Vor- und Nachteile

Vorteile

* Reaktionsfähigkeit des Hauptthreads wird verbessert
* Parallelitätsprobleme sind in den Objekten gekapselt
* Ermöglicht bessere Codeorganisation und -wartbarkeit.
* Sorgt für Threadsicherheit und vermeidet Probleme beim gemeinsamen Zugriff auf Zustände.
  
Nachteile

* Zusatzaufwand für die Übermittlung von Benachrichtigungen und das Threadmanagement. 
* Nicht für alle Arten von Nebenläufigkeitsproblemen geeignet.

## Verwandte Patterns

* [Command](https://java-design-patterns.com/patterns/command/): Kapselt Anfragen als Objekte, ähnlich wie Active Object es mit Methodenaufrufen macht.
* [Promise](https://java-design-patterns.com/patterns/promise/): Bietet einen Weg zur Abfrage von Ergebnissen eines asynchronen Methodenaufrufs, oft mit Active Object kombiniert.
* [Proxy](https://java-design-patterns.com/patterns/proxy/): Active Object kann einen Proxy verwenden, um asynchrone Methodenaufrufe zu behandeln. 

## Quellen

* [Design Patterns: Elements of Reusable Object Software](https://amzn.to/3HYqrBE)
* [Concurrent Programming in Java: Design Principles and Patterns](https://amzn.to/498SRVq)
* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
* [Learning Concurrent Programming in Scala](https://amzn.to/3UE07nV)
* [Pattern Languages of Program Design 3](https://amzn.to/3OI1j61)
* [Pattern-Oriented Software Architecture Volume 2: Patterns for Concurrent and Networked Objects](https://amzn.to/3UgC24V)
