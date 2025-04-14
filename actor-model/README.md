# Actor Model

## Intent

The Actor Model is a concurrency pattern that treats "actors" as the fundamental units of computation. Each actor has its own state and behavior and interacts with other actors exclusively through message passing.

## Explanation

### Real-world Example

Imagine a team of people (actors) working in an office. They don’t share the same brain (memory), but instead communicate by passing notes (messages). Each person reads one note at a time and responds accordingly.

### Problem

Managing concurrent behavior in a safe and scalable way is difficult, especially with shared memory and race conditions.

### Solution

Encapsulate state and behavior within individual actors that communicate through asynchronous messages.

## Class Diagram

![UML Diagram](etc/actor-model.png)

## Participants

- **Actor**: Base class that defines a mailbox and handles incoming messages sequentially.
- **Message**: Represents communication between actors.
- **ActorSystem**: Creates and manages actors.
- **PrinterActor**: Sample actor that prints incoming messages.

## Applicability

Use the Actor Model pattern when:

- You need a simple and safe way to handle concurrency.
- You want to avoid thread synchronization issues like race conditions and deadlocks.
- You want each object to process messages independently.

## Example

```java
ActorSystem system = new ActorSystem();
Actor printer = system.actorOf(new PrinterActor());

printer.send(new Message("Hello Actor!", null));
printer.send(new Message("Another message", null));

Thread.sleep(1000);

((PrinterActor) printer).stop();
system.shutdown();
