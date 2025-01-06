---
title: "Idempotent Consumer Pattern in Java: Ensuring Reliable Message Processing"
shortTitle: Idempotent Consumer
description: "Learn about the Idempotent Consumer pattern in Java. Discover how it ensures reliable and consistent message processing, even in cases of duplicate messages."
category: Messaging
language: en
tag:
    - Messaging
    - Fault tolerance
    - Event-driven
    - Reliability
---

## Also known as

* Idempotency Pattern

## Intent of Idempotent Consumer Pattern

The Idempotent Consumer pattern is used to handle duplicate messages in distributed systems, ensuring that multiple processing of the same message does not cause undesired side effects. This pattern guarantees that the same message can be processed repeatedly with the same outcome, which is critical in ensuring reliable communication and data consistency in systems where message duplicates are possible.

## Detailed Explanation of Idempotent Consumer Pattern with Real-World Examples

### Real-world Example

> In a payment processing system, ensuring that payment messages are idempotent prevents duplicate transactions. For example, if a user’s payment message is accidentally processed twice, the system should recognize the second message as a duplicate and prevent it from executing a second time. By storing unique identifiers for each processed message, such as a transaction ID, the system can skip any duplicate messages. This ensures that a user is not charged twice for the same transaction, maintaining system integrity and customer satisfaction.

### In Plain Words

> The Idempotent Consumer pattern prevents duplicate messages from causing unintended side effects by ensuring that processing the same message multiple times results in the same outcome. This makes message processing safe in distributed systems where duplicates may occur.

### Wikipedia says

> In computing, idempotence is the property of certain operations in mathematics and computer science whereby they can be applied multiple times without changing the result beyond the initial application.

## When to Use the Idempotent Consumer Pattern

The Idempotent Consumer pattern is particularly useful in scenarios:

* When messages can be duplicated due to network retries or communication issues.
* In distributed systems where message ordering is not guaranteed, making deduplication necessary to avoid repeated processing.
* In financial or critical systems, where duplicate processing would have significant side effects.

## Real-World Applications of Idempotent Consumer Pattern

* Payment processing systems that avoid duplicate transactions.
* E-commerce systems to prevent multiple entries of the same order.
* Inventory management systems to prevent multiple entries when updating stock levels.

## Programmatic example of Idempotent Consumer Pattern
In this Java example, we have an idempotent service that offers functionality to create and update (start, complete, etc.) orders. The service ensures that the **create order** operation is idempotent, meaning that performing it multiple times with the same order ID will lead to the same result without creating duplicates. For state transitions (such as starting or completing an order), the service enforces valid state changes and throws exceptions if an invalid transition is attempted. The state machine governs the valid order status transitions, ensuring that statuses progress in a defined and consistent sequence.
### RequestService - Managing Idempotent Order Operations
The `RequestService` class is responsible for handling the creation and state transitions of orders. The `create` method is designed to be idempotent, ensuring that it either returns an existing order or creates a new one without any side effects if invoked multiple times with the same order ID.
```java
public class RequestService {
    // Idempotent: ensures that the same request is returned if it already exists
    public Request create(UUID uuid) {
        Optional<Request> optReq = requestRepository.findById(uuid);
        if (!optReq.isEmpty()) {
            return optReq.get();  // Return existing request
        }
        return requestRepository.save(new Request(uuid));  // Save and return new request
    }

    public Request start(UUID uuid) {
        Optional<Request> optReq = requestRepository.findById(uuid);
        if (optReq.isEmpty()) {
            throw new RequestNotFoundException(uuid);
        }
        return requestRepository.save(requestStateMachine.next(optReq.get(), Request.Status.STARTED));
    }

    public Request complete(UUID uuid) {
        Optional<Request> optReq = requestRepository.findById(uuid);
        if (optReq.isEmpty()) {
            throw new RequestNotFoundException(uuid);
        }
        return requestRepository.save(requestStateMachine.next(optReq.get(), Request.Status.COMPLETED));
    }
}
```
### RequestStateMachine - Managing Order Transitions
The `RequestStateMachine` ensures that state transitions occur in a valid order. It handles the progression of an order's status, ensuring the correct sequence (e.g., from `PENDING` to `STARTED` to `COMPLETED`).
```java
public class RequestStateMachine {

  public Request next(Request req, Request.Status nextStatus) {
    String transitionStr = String.format("Transition: %s -> %s", req.getStatus(), nextStatus);
    switch (nextStatus) {
      case PENDING -> throw new InvalidNextStateException(transitionStr);
      case STARTED -> {
        if (Request.Status.PENDING.equals(req.getStatus())) {
          return new Request(req.getUuid(), Request.Status.STARTED);  // Valid transition
        }
        throw new InvalidNextStateException(transitionStr);  // Invalid transition
      }
      case COMPLETED -> {
        if (Request.Status.STARTED.equals(req.getStatus())) {
          return new Request(req.getUuid(), Request.Status.COMPLETED);  // Valid transition
        }
        throw new InvalidNextStateException(transitionStr);  // Invalid transition
      }
      default -> throw new InvalidNextStateException(transitionStr);  // Invalid status
    }
  }
}
```
### Main Application - Running the Idempotent Consumer Example

In the main application, we demonstrate how the `RequestService` can be used to perform idempotent operations. Whether the order creation or state transition is invoked once or multiple times, the result is consistent and does not produce unexpected side effects.

```java
Request req = requestService.create(UUID.randomUUID());
// Try creating the same Request again with the same UUID (idempotent operation)
requestService.create(req.getUuid());
// Again, try creating the same Request (idempotent operation, no new Request should be created)
requestService.create(req.getUuid());
LOGGER.info("Nb of requests : {}", requestRepository.count()); // 1, processRequest is idempotent
// Attempt to start the Request (the first valid transition)
req = requestService.start(req.getUuid());
// Try to start the Request again, which should throw an exception since it's already started
try {
  req = requestService.start(req.getUuid());
} catch (InvalidNextStateException ex) {
  // Log an error message when trying to start a request twice
  LOGGER.error("Cannot start request twice!");
}
// Complete the Request (valid transition from STARTED to COMPLETED)
req = requestService.complete(req.getUuid());
// Log the final status of the Request to confirm it's been completed
LOGGER.info("Request: {}", req);
```
Program output:
```
19:01:54.382  INFO [main] com.iluwatar.idempotentconsumer.App      : Nb of requests : 1
19:01:54.395 ERROR [main] com.iluwatar.idempotentconsumer.App      : Cannot start request twice!
19:01:54.399  INFO [main] com.iluwatar.idempotentconsumer.App      : Request: Request(uuid=2d5521ef-6b6b-4003-9ade-81e381fe9a63, status=COMPLETED)
```
## Benefits and Trade-offs of the Idempotent Consumer Pattern

### Benefits

* **Reliability**: Ensures that messages can be processed without unwanted side effects from duplicates.
* **Consistency**: Maintains data integrity by ensuring that duplicate messages do not cause redundant updates or actions.
* **Fault Tolerance**: Handles message retries gracefully, preventing them from causing errors.

### Trade-offs

* **State Management**: Requires storing processed message IDs, which can add memory overhead.
* **Complexity**: Implementing deduplication mechanisms can increase the complexity of the system.
* **Scalability**: In high-throughput systems, maintaining a large set of processed messages can impact performance and resource usage.

## Related Patterns in Java

* [Retry Pattern](https://java-design-patterns.com/patterns/retry/): Works well with the Idempotent Consumer pattern to handle failed messages.
* [Circuit Breaker Pattern](https://java-design-patterns.com/patterns/circuitbreaker/): Often used alongside idempotent consumers to prevent repeated failures from causing overload.

## References and Credits

* [Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions](https://amzn.to/4dznP2Y)
* [Designing Data-Intensive Applications](https://amzn.to/3UADv7Q)
