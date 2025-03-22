---
title: "Service Stub Pattern in Java: Simplifying Testing with Stub Implementations"
shortTitle: Service Stub
description: "Explore the Service Stub design pattern in Java using a Sentiment Analysis example. Learn how stub implementations provide dummy services to facilitate testing and development."
category: Structural
language: en
tag:
    - Testing
    - Decoupling
    - Dummy Services
    - Dependency Injection
---

## Also known as

* Dummy Service
* Fake Service

## Intent of Service Stub Pattern

The Service Stub pattern provides a lightweight, dummy implementation of an external service to allow testing or development without relying on the real service, which may be unavailable, slow, or resource-intensive.

## Detailed Explanation of Service Stub Pattern with Real-World Example

Real-world example

> In this example, we simulate a **Sentiment Analysis Service**. The real implementation delays execution and randomly decides the sentiment. The stub implementation, on the other hand, quickly returns predefined responses based on input text ("good", "bad", or neutral), making it ideal for testing.

In plain words

> Use a fake service to return predictable results without relying on external systems.

Wikipedia says

> A test stub is a dummy component used during testing to isolate behavior.

## Programmatic Example of Service Stub Pattern in Java

We define a `SentimentAnalysisService` interface and provide two implementations:

1. **RealSentimentAnalysisServer**: Simulates a slow, random sentiment analysis system.
2. **StubSentimentAnalysisServer**: Returns a deterministic result based on input keywords.

### Example Implementation
Both the real service and the stub implement the interface below.
```java
public interface SentimentAnalysisServer {
    String analyzeSentiment(String text);
}
```
The real sentiment analysis class returns a random response for a given input and simulates the runtime by sleeping 
the Thread for 5 seconds. The Supplier<Integer> allows injecting controlled sentiment values during testing, ensuring
deterministic outputs.
```java
public class RealSentimentAnalysisServer implements SentimentAnalysisServer {
    
    private final Supplier<Integer> sentimentSupplier;

    public RealSentimentAnalysisServer(Supplier<Integer> sentimentSupplier) {
        this.sentimentSupplier = sentimentSupplier;
    }

    public RealSentimentAnalysisServer() {
        this(() -> new Random().nextInt(3));
    }

    @Override
    public String analyzeSentiment(String text) {
        int sentiment = sentimentSupplier.get();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return sentiment == 0 ? "Positive" : sentiment == 1 ? "Negative" : "Neutral";
    }
}
```
The stub implementation simulates the real sentiment analysis class and provides a deterministic output
for a given input. Additionally, its runtime is almost zero.
```java
public class StubSentimentAnalysisServer implements SentimentAnalysisServer {

  @Override
  public String analyzeSentiment(String text) {
    if (text.toLowerCase().contains("good")) {
      return "Positive";
    }
    else if (text.toLowerCase().contains("bad")) {
      return "Negative";
    }
    else {
      return "Neutral";
    }
  }
}

```
Here is the main function of the App class (entry point to the program)
```java
@Slf4j
  public static void main(String[] args) {
  LOGGER.info("Setting up the real sentiment analysis server.");
  RealSentimentAnalysisServer realSentimentAnalysisServer = new RealSentimentAnalysisServer();
  String text = "This movie is soso";
  LOGGER.info("Analyzing input: {}", text);
  String sentiment = realSentimentAnalysisServer.analyzeSentiment(text);
  LOGGER.info("The sentiment is: {}", sentiment);

    LOGGER.info("Setting up the stub sentiment analysis server.");
    StubSentimentAnalysisServer stubSentimentAnalysisServer = new StubSentimentAnalysisServer();
    text = "This movie is so bad";
    LOGGER.info("Analyzing input: {}", text);
    sentiment = stubSentimentAnalysisServer.analyzeSentiment(text);
    LOGGER.info("The sentiment is: {}", sentiment);
  }
```
## When to Use the Service Stub Pattern in Java

Use the Service Stub pattern when:

* Testing components that depend on external services.
* The real service is slow, unreliable, or unavailable.
* You need predictable, predefined responses.
* Developing offline without real service access.

## Real-World Applications of Service Stub Pattern in Java

* Simulating APIs (payments, recommendation systems) during testing.
* Bypassing external AI/ML models in tests.
* Simplifying integration testing.

## Benefits and Trade-offs of Service Stub Pattern

Benefits:

* Reduces dependencies.
* Provides predictable behavior.
* Speeds up testing.

Trade-offs:

* Requires maintaining stub logic.
* May not fully represent real service behavior.

## Related Java Design Patterns

* [Proxy](https://java-design-patterns.com/patterns/proxy/)
* [Strategy](https://java-design-patterns.com/patterns/strategy/)

## References and Credits

* [Martin Fowler: Test Stubs](https://martinfowler.com/articles/mocksArentStubs.html)
