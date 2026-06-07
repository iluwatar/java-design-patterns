---
shortTitle: Actor Model
category: Concurrency
language: de
tag:
    - Concurrency
    - Messaging
    - Isolation
    - Asynchronous
    - Distributed Systems
    - Actor Model
---

## Alternativbezeichnungen

- Message-passing concurrency
- Actor-based concurrency

---

## Zweck

Das Actor-Model-Pattern ermöglicht die Konstruktion von hochparallelen fehlertoleranten verteilten Systemen,
indem es isolierte Komponenten (Akteure) verwendet, die ausschließlich über asynchronen Nachrichtenaustausch interagieren.

## Detaillierte Erklärung

---

### 📦 Reales Beispiel

Stellen Sie sich ein Kundendienstsystem vor.
- Jeder **Kundendienstmitarbeiter** ist ein **Aktor**.
- Kunden **senden Anfragen (Nachrichten)** an die Mitarbeiter.
- Jeder Mitarbeiter behandelt zu einem bestimmten Zeitpunkt genau eine Anfrage und kann diese **asynchron beantworten**,
ohne dabei anderen Mitarbeitern in die Quere zu kommen.

---

### 🧠 In einfachen Worten

> "Aktoren sind wie unabhängige Arbeiter, die keine Ressourcen teilen und nur über Nachrichten kommunizieren."

---

### 📖 Wikipedia sagt

> Das [Actor Model](https://en.wikipedia.org/wiki/Actor_model) ist ein mathematisches Modell
>  für parallele Informationsverarbeitung, das "Aktoren" als universelle Ausführer von Aufgaben betrachtet.

---

### 🧹 Klassendiagramm

![UML Class Diagram](./etc/Actor_Model_UML_Class_Diagram.png)

---

## Programmbeispiel in Java

### Actor.java

```java
public abstract class Actor implements Runnable {

    @Setter
    @Getter
    private String actorId;
    private final BlockingQueue<Message> mailbox = new LinkedBlockingQueue<>();
    private volatile boolean active = true; 


    public void send(Message message) {
        mailbox.add(message); 
    }

    public void stop() {
        active = false; 
    }

    @Override
    public void run() {
        
    }

    protected abstract void onReceive(Message message);
}

```

### Message.java

```java

@AllArgsConstructor
@Getter
@Setter
public class Message {
    private final String content;
    private final String senderId;
}
```

### ActorSystem.java

```java
public class ActorSystem {
    public void startActor(Actor actor) {
        String actorId = "actor-" + idCounter.incrementAndGet(); // Generate a new and unique ID
        actor.setActorId(actorId); // assign the actor it's ID
        actorRegister.put(actorId, actor); // Register and save the actor with it's ID
        executor.submit(actor); // Run the actor in a thread
    }
    public Actor getActorById(String actorId) {
        return actorRegister.get(actorId); //  Find by Id
    }

    public void shutdown() {
        executor.shutdownNow(); // Stop all threads
    }
}
```

### App.java

```java
public class App {
  public static void main(String[] args) {
      ActorSystem system = new ActorSystem();
      Actor srijan = new ExampleActor(system);
      Actor ansh = new ExampleActor2(system);

      system.startActor(srijan);
      system.startActor(ansh);
      ansh.send(new Message("Hello ansh", srijan.getActorId()));
      srijan.send(new Message("Hello srijan!", ansh.getActorId()));

      Thread.sleep(1000); // Give time for messages to process

      srijan.stop(); // Stop the actor gracefully
      ansh.stop();
      system.shutdown(); // Stop the actor system
  }
}
```

---

## Verwendung

- Bei der Konstruktion **paralleler oder verteilter Systeme**
- Wenn **keine veränderlichen Zustände geteilt** werden sollen
- Wenn **asynchrone, nachrichtenbasierte Kommunikation** benötigt wird
- Wenn die Komponenten **isoliert und lose gekoppelt** sein sollen.

---

## Tutorials

- [Baeldung – Akka with Java](https://www.baeldung.com/java-akka)
- [Vaughn Vernon – Reactive Messaging Patterns](https://vaughnvernon.co/?p=1143)

---

## Reale Anwendungen 

- [Akka Framework](https://akka.io/)
- [Concurrency in Erlang und Elixir](https://www.erlang.org/)
- [Microsoft Orleans](https://learn.microsoft.com/en-us/dotnet/orleans/)
- JVM-basierte Spiel-Engines und Simulatoren

---

## Vor- und Nachteile

### ✅ Vorteile
- Unterstützt hohes Maß an Parallelität
- Leichte Skalierbarkeit über Zahl der Threads oder Prozessoren.
- Fehlerisolation und -behebbarkeit.
- Geordnete Nachrichten in den Aktoren

### ⚠️ Nachteile
- Schwierigeres Debugging wegen asynchronen Verhaltens
- Leichte Performance-Einbußen durch die Nachrichten-Warteschlangen
- Komplexeres Design als bei einfachem Methodenaufruf
---

## Verwandte Patterns

- [Command Pattern](../command)
- [Mediator Pattern](../mediator)
- [Event-Driven Architecture](../event-driven-architecture)
- [Observer Pattern](../observer)

---

## Quellen

- *Programming Erlang*, Joe Armstrong
- *Reactive Design Patterns*, Roland Kuhn
- *The Actor Model in 10 Minutes*, [InfoQ Article](https://www.infoq.com/articles/actor-model/)
- [Akka Documentation](https://doc.akka.io/docs/akka/current/index.html)

