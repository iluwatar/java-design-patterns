---
title: Promise
category: Concurrency
language: en
tag:
    - Asynchronous
    - Decoupling
    - Messaging
    - Synchronization
    - Thread management
---

## Also known as

* Deferred
* Future

## Intent

The Promise design pattern is used to handle asynchronous operations by providing a placeholder for a result that is initially unknown but will be resolved in the future.

## Explanation

Real-world example

> In an online pizza ordering system, when a customer places an order, the system immediately acknowledges the order and provides a tracking number (the promise). The pizza preparation and delivery process happens asynchronously in the background. The customer can check the status of their order at any time using the tracking number. Once the pizza is prepared and out for delivery, the customer receives a notification (promise resolved) about the delivery status. If there are any issues, such as an unavailable ingredient or delivery delay, the customer is notified about the error (promise rejected).
> 
> This analogy illustrates how the Promise design pattern manages asynchronous tasks, decoupling the initial request from the eventual outcome, and handling both results and errors efficiently.

In plain words

> Promise is a placeholder for an asynchronous operation that is ongoing.

Wikipedia says

> In computer science, future, promise, delay, and deferred refer to constructs used for synchronizing program execution in some concurrent programming languages. They describe an object that acts as a proxy for a result that is initially unknown, usually because the computation of its value is not yet complete.

**Programmatic Example**

The Promise design pattern is a software design pattern that's often used in concurrent programming to handle asynchronous operations. It represents a proxy for a value not necessarily known when the promise is created. It allows you to associate handlers with an asynchronous action's eventual success value or failure reason.

In the provided code, the Promise design pattern is used to handle various asynchronous operations such as downloading a file, counting lines in a file, and calculating the character frequency in a file.

```java
@Slf4j
public class App {

  private static final String DEFAULT_URL =
      "https://raw.githubusercontent.com/iluwatar/java-design-patterns/master/promise/README.md";
  private final ExecutorService executor;

  private App() {
    // Create a thread pool with 2 threads
    executor = Executors.newFixedThreadPool(2);
  }

  public static void main(String[] args) {
    var app = new App();
    app.promiseUsage();
  }

  private void promiseUsage() {
    calculateLineCount();
    calculateLowestFrequencyChar();
  }

  private void calculateLowestFrequencyChar() {
    // Create a promise to calculate the lowest frequency character
    lowestFrequencyChar().thenAccept(
        charFrequency -> {
          LOGGER.info("Char with lowest frequency is: {}", charFrequency);
        }
    );
  }

  private void calculateLineCount() {
    // Create a promise to calculate the line count
    countLines().thenAccept(
        count -> {
          LOGGER.info("Line count is: {}", count);
        }
    );
  }

  private Promise<Character> lowestFrequencyChar() {
    // Create a promise to calculate the character frequency and then find the lowest frequency character
    return characterFrequency().thenApply(Utility::lowestFrequencyChar);
  }

  private Promise<Map<Character, Long>> characterFrequency() {
    // Create a promise to download a file and then calculate the character frequency
    return download(DEFAULT_URL).thenApply(Utility::characterFrequency);
  }

  private Promise<Integer> countLines() {
    // Create a promise to download a file and then count the lines
    return download(DEFAULT_URL).thenApply(Utility::countLines);
  }

  private Promise<String> download(String urlString) {
    // Create a promise to download a file
    return new Promise<String>()
        .fulfillInAsync(
            () -> Utility.downloadFile(urlString), executor)
        .onError(
            throwable -> {
              LOGGER.error("An error occurred: ", throwable);
            }
        );
  }
}
```

In this code, the `Promise` class is used to create promises for various operations. The `thenApply` method is used to chain promises, meaning that the result of one promise is used as the input for the next promise. The `thenAccept` method is used to handle the result of a promise. The `fulfillInAsync` method is used to fulfill a promise asynchronously, and the `onError` method is used to handle any errors that occur while fulfilling the promise.

Program output:

```
08:19:33.036 [pool-1-thread-2] INFO com.iluwatar.promise.Utility -- Downloading contents from url: https://raw.githubusercontent.com/iluwatar/java-design-patterns/master/promise/README.md
08:19:33.036 [pool-1-thread-1] INFO com.iluwatar.promise.Utility -- Downloading contents from url: https://raw.githubusercontent.com/iluwatar/java-design-patterns/master/promise/README.md
08:19:33.419 [pool-1-thread-2] INFO com.iluwatar.promise.Utility -- File downloaded at: /var/folders/sg/9_st37nn5hq_bfhp8hw2dcrw0000gp/T/promise_pattern12403918065536844551.tmp
08:19:33.419 [pool-1-thread-1] INFO com.iluwatar.promise.Utility -- File downloaded at: /var/folders/sg/9_st37nn5hq_bfhp8hw2dcrw0000gp/T/promise_pattern11215446820862558571.tmp
08:19:33.419 [pool-1-thread-1] INFO com.iluwatar.promise.App -- Line count is: 164
08:19:33.426 [pool-1-thread-2] INFO com.iluwatar.promise.App -- Char with lowest frequency is: ’
```

## Applicability

* When you need to perform asynchronous tasks and handle their results or errors at a later point.
* In scenarios where tasks can be executed in parallel and their outcomes need to be handled once they are completed.
* Suitable for improving the readability and maintainability of asynchronous code.

## Tutorials

* [Functional-Style Callbacks Using Java 8's CompletableFuture (InfoQ)](https://www.infoq.com/articles/Functional-Style-Callbacks-Using-CompletableFuture)
* [Guide To CompletableFuture (Baeldung)](https://www.baeldung.com/java-completablefuture)
* [You are missing the point to Promises (Domenic Denicola)](https://gist.github.com/domenic/3889970)

## Known Uses

* Java's CompletableFuture and Future classes.
* JavaScript’s Promise object for managing asynchronous operations.
* Many asynchronous frameworks and libraries such as RxJava and Vert.x.
* [Guava ListenableFuture](https://github.com/google/guava/wiki/ListenableFutureExplained)

## Consequences

Benefits:

* Improved Readability: Simplifies complex asynchronous code, making it easier to understand and maintain.
* Decoupling: Decouples the code that initiates the asynchronous operation from the code that processes the result.
* Error Handling: Provides a unified way to handle both results and errors from asynchronous operations.

Trade-offs:

* Complexity: Can add complexity to the codebase if overused or misused.
* Debugging: Asynchronous code can be harder to debug compared to synchronous code due to the non-linear flow of execution.

## Related Patterns

* [Observer](https://java-design-patterns.com/patterns/observer/): Promises can be used in conjunction with the Observer pattern to notify subscribers about the completion of asynchronous operations.
* [Callback](https://java-design-patterns.com/patterns/callback/): Promises often replace callback mechanisms by providing a more structured and readable way to handle asynchronous results.
* [Async Method Invocation](https://java-design-patterns.com/patterns/async-method-invocation/): Promises are often used to handle the results of asynchronous method invocations, allowing for non-blocking execution and result handling.

## Credits

* [Effective Java](https://amzn.to/4cGk2Jz)
* [Java 8 in Action: Lambdas, Streams, and functional-style programming](https://amzn.to/3QCmGXs)
* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
* [Modern Java in Action: Lambdas, streams, functional and reactive programming](https://amzn.to/3VhwetF)
