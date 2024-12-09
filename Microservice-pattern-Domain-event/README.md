# Microservice Pattern: Domain Event

## Overview
The Domain Event design pattern enables reliable communication between microservices in a decoupled manner. This pattern allows services to react to state changes in other services without tightly coupling them, enhancing scalability and maintainability.

## Intent of the Domain Event Pattern
The Domain Event pattern decouples services by allowing one service to notify others of significant state changes. When an important change occurs, a domain event is published, and other services can listen and react to it.

## Detailed Explanation

### Real-World Example
In an e-commerce system, when a customer places an order, the `OrderService` creates an `OrderCreatedEvent`. This event is published, and other services like Inventory and Payment can listen for it and act accordingly (e.g., updating inventory, processing payment).

### In Plain Words
When an important event happens in one service (e.g., order creation), it publishes a Domain Event. Other services can subscribe to these events and react without directly communicating with each other.

---

## Example of the Domain Event Pattern in Action

Consider a system with the following services:
- **OrderService**: Creates orders.
- **InventoryService**: Updates stock based on orders.
- **PaymentService**: Processes payments after order creation.

When an order is created in the `OrderService`, an `OrderCreatedEvent` is published. Other services listen for this event and act accordingly.

---

## Implementation in Java

### 1. Domain Event Class
This class serves as the base for all domain events and includes the timestamp.

```java
public abstract class DomainEvent {
    private final LocalDateTime timestamp;

    public DomainEvent() {
        this.timestamp = LocalDateTime.now();
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
```
### 2. Event Publisher
The `EventPublisher` component is responsible for publishing domain events.

```java
@Component
public class EventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public EventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(DomainEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}

```
### 3. Event Listener
The `OrderCreatedListener` listens for the `OrderCreatedEvent` and processes it when it occurs.

```java
@Component
public class OrderCreatedListener {
    @EventListener
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        System.out.println("Handling Order Created Event: " + event.getOrderId());
        // Example logic: Notify another service or update the database
    }
}
```

### 4. Order Service
The `OrderService` publishes the `OrderCreatedEvent` when an order is created.

```java
@Service
public class OrderService {
    private final EventPublisher eventPublisher;

    public OrderService(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void createOrder(String orderId) {
        System.out.println("Order created with ID: " + orderId);
        OrderCreatedEvent event = new OrderCreatedEvent(orderId);
        eventPublisher.publish(event);
    }
}
```

### 5. Order Created Event
The `OrderCreatedEvent` extends `DomainEvent` and represents the creation of an order.

```java
public class OrderCreatedEvent extends DomainEvent {
    private final String orderId;

    public OrderCreatedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}

```
### 6. When to Use the Domain Event Pattern
This pattern is ideal for scenarios where:
- Services need to react to state changes in other services without tight coupling.
- Asynchronous communication between services is preferred.
- Scalability is crucial, allowing services to independently scale and evolve.

---

### 7. Benefits and Trade-offs of the Domain Event Pattern

#### Benefits:
1. **Decoupling**: Services are decoupled, making maintenance and independent scaling easier.
2. **Scalability**: Services scale independently, based on individual requirements.
3. **Asynchronous Processing**: Event-driven communication supports asynchronous workflows, enhancing system responsiveness.

#### Trade-offs:
1. **Eventual Consistency**: As events are processed asynchronously, eventual consistency must be handled carefully.
2. **Complexity**: Adding events and listeners increases system complexity, particularly for failure handling and retries.
3. **Debugging**: Asynchronous and decoupled communication can make tracing data flow across services more challenging.

---

### 8. Example Flow

1. **Order Created**: The `OrderService` creates a new order.
2. **Publish Event**: The `OrderCreatedEvent` is published by the `EventPublisher`.
3. **Event Listener**: The `OrderCreatedListener` listens for the event and executes relevant logic (e.g., notifying other services).
4. **Outcome**: Other services (e.g., Inventory, Payment) react to the event accordingly.

---

### 9. References and Credits
- *Domain-Driven Design* by Eric Evans
- *Building Microservices* by Sam Newman
- *Microservices Patterns* by Chris Richardson

For more information on Domain Event patterns and best practices, refer to the above books.
