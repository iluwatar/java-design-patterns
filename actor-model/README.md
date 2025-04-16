---
title: "Actor Model Pattern in Java: Building Concurrent Systems with Elegance"
shortTitle: Actor Model
description: "Explore the Actor Model pattern in Java with real-world examples and practical implementation. Learn how to build scalable, message-driven systems using actors, messages, and asynchronous communication."
category: Concurrency
language: en
tag:
    - Concurrency
    - Messaging
    - Isolation
    - Asynchronous
    - Distributed Systems
    - Actor Model
---

## Also Known As

- Message-passing concurrency
- Actor-based concurrency

---

## Intent of Actor Model Pattern

The Actor Model pattern enables the construction of highly concurrent, distributed, and fault-tolerant systems by using isolated components (actors) that interact exclusively through asynchronous message passing.

---

## Detailed Explanation of Actor Model Pattern with Real-World Examples

### 📦 Real-world Example

Imagine a customer service system:
- Each **customer support agent** is an **actor**.
- Customers **send questions (messages)** to agents.
- Each agent handles one request at a time and can **respond asynchronously** without interfering with other agents.

---

### 🧠 In Plain Words

> "Actors are like independent workers that never share memory and only communicate through messages."

---

### 📖 Wikipedia Says

> [Actor model](https://en.wikipedia.org/wiki/Actor_model) is a mathematical model of concurrent computation that treats "actors" as the universal primitives of concurrent computation.

---

### 🧹 Architecture Diagram

![UML Class Diagram](./etc/Actor_Model_UML_Class_Diagram.png)

---

## Programmatic Example of Actor Model Pattern in Java

### Actor.java

```java
public abstract class Actor implements Runnable {

    @Setter @Getter private String actorId;
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

## When to Use the Actor Model Pattern in Java

- When building **concurrent or distributed systems**
- When you want **no shared mutable state**
- When you need **asynchronous, message-driven communication**
- When components should be **isolated and loosely coupled**

---

## Actor Model Pattern Java Tutorials

- [Baeldung – Akka with Java](https://www.baeldung.com/java-akka)
- [Vaughn Vernon – Reactive Messaging Patterns](https://vaughnvernon.co/?p=1143)

---

## Real-World Applications of Actor Model Pattern in Java

- [Akka Framework](https://akka.io/)
- [Erlang and Elixir concurrency](https://www.erlang.org/)
- [Microsoft Orleans](https://learn.microsoft.com/en-us/dotnet/orleans/)
- JVM-based game engines and simulators

---

## Benefits and Trade-offs of Actor Model Pattern

### ✅ Benefits
- High concurrency support
- Easy scaling across threads or machines
- Fault isolation and recovery
- Message ordering within actors

### ⚠️ Trade-offs
- Harder to debug due to asynchronous behavior
- Slight performance overhead due to message queues
- More complex to design than simple method calls

---

## Related Java Design Patterns

- [Command Pattern](../command)
- [Mediator Pattern](../mediator)
- [Event-Driven Architecture](../event-driven-architecture)
- [Observer Pattern](../observer)

---

## References and Credits

- *Programming Erlang*, Joe Armstrong
- *Reactive Design Patterns*, Roland Kuhn
- *The Actor Model in 10 Minutes*, [InfoQ Article](https://www.infoq.com/articles/actor-model/)
- [Akka Documentation](https://doc.akka.io/docs/akka/current/index.html)

