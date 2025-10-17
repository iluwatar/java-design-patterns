# Bulkhead Pattern

## Intent

Isolate elements of an application into pools so that if one fails, others continue to function. Prevents cascading failures in distributed systems.

## Explanation

Similar to ship bulkheads that prevent flooding, this pattern limits concurrent executions to protect system resources. When a service becomes overloaded, the bulkhead contains the failure without affecting other services.

## Usage

```java
BulkheadConfig config = new BulkheadConfig(3, 1000);
ServiceBulkhead bulkhead = BulkheadPattern.getBulkhead("PaymentService", config);

CompletableFuture<String> result = bulkhead.execute(() -> {
    // Your service call
    return processPayment();
});
