---
title: "Pipeline Pattern in Java: Streamlining Data Processing with Modular Components"
shortTitle: Pipeline
description: "Master the Pipeline design pattern in Java with our comprehensive guide. Learn how to implement data processing in discrete stages for improved code scalability and flexibility. Ideal for developers looking to advance their software engineering skills."
category: Behavioral
language: en
tag:
  - API design
  - Data processing
  - Decoupling
  - Extensibility
  - Functional decomposition
  - Scalability
---

## Also known as

* Chain of Operations
* Processing Pipeline

## Intent of Pipeline Design Pattern

The Pipeline design pattern in Java is engineered to facilitate data processing across discrete stages, enhancing modular development and operational efficiency.

## Detailed Explanation of Pipeline Pattern with Real-World Examples

Real-world example

> A practical example of the Java Pipeline design pattern can be seen in assembly lines, such as those in car manufacturing, illustrating its efficiency and scalability.
>
> In this analogy, the car manufacturing process is divided into several discrete stages, each stage handling a specific part of the car assembly. For example:
>
> 1. **Chassis Assembly:** The base frame of the car is assembled.
> 2. **Engine Installation:** The engine is installed onto the chassis.
> 3. **Painting:** The car is painted.
> 4. **Interior Assembly:** The interior, including seats and dashboard, is installed.
> 5. **Quality Control:** The finished car is inspected for defects.
>
> In the Java Pipeline pattern, each stage functions independently and sequentially, ensuring smooth data flow and easy modifications. The output of one stage (e.g., a partially assembled car) becomes the input for the next stage. This modular approach allows for easy maintenance, scalability (e.g., adding more workers to a stage), and flexibility (e.g., replacing a stage with a more advanced version). Just like in a software pipeline, changes in one stage do not affect the others, facilitating continuous improvements and efficient production.

In plain words

> Pipeline pattern is an assembly line where partial results are passed from one stage to another.

Wikipedia says

> In software engineering, a pipeline consists of a chain of processing elements (processes, threads, coroutines, functions, etc.), arranged so that the output of each element is the input of the next; the name is by analogy to a physical pipeline.

## Programmatic Example of Pipeline Pattern in Java

Let's create a string processing pipeline example. The stages of our pipeline are called `Handler`s.

```java
interface Handler<I, O> {
    O process(I input);
}
```

In our string processing example we have 3 different concrete `Handler`s.

```java
class RemoveAlphabetsHandler implements Handler<String, String> {
  // ...
}

class RemoveDigitsHandler implements Handler<String, String> {
  // ...
}

class ConvertToCharArrayHandler implements Handler<String, char[]> {
  // ...
}
```

Here is the `Pipeline` that will gather and execute the handlers one by one.

```java
class Pipeline<I, O> {

    private final Handler<I, O> currentHandler;

    Pipeline(Handler<I, O> currentHandler) {
        this.currentHandler = currentHandler;
    }

    <K> Pipeline<I, K> addHandler(Handler<O, K> newHandler) {
        return new Pipeline<>(input -> newHandler.process(currentHandler.process(input)));
    }

    O execute(I input) {
        return currentHandler.process(input);
    }
}
```

And here's the `Pipeline` in action processing the string.

```java
public static void main(String[] args) {
    LOGGER.info("Creating pipeline");
    var filters = new Pipeline<>(new RemoveAlphabetsHandler())
            .addHandler(new RemoveDigitsHandler())
            .addHandler(new ConvertToCharArrayHandler());
    var input = "GoYankees123!";
    LOGGER.info("Executing pipeline with input: {}", input);
    var output = filters.execute(input);
    LOGGER.info("Pipeline output: {}", output);
}
```

Console output:

```
07:34:27.069 [main] INFO com.iluwatar.pipeline.App -- Creating pipeline
07:34:27.072 [main] INFO com.iluwatar.pipeline.App -- Executing pipeline with input: GoYankees123!
07:34:27.074 [main] INFO com.iluwatar.pipeline.RemoveAlphabetsHandler -- Current handler: class com.iluwatar.pipeline.RemoveAlphabetsHandler, input is GoYankees123! of type class java.lang.String, output is 123!, of type class java.lang.String
07:34:27.075 [main] INFO com.iluwatar.pipeline.RemoveDigitsHandler -- Current handler: class com.iluwatar.pipeline.RemoveDigitsHandler, input is 123! of type class java.lang.String, output is !, of type class java.lang.String
07:34:27.075 [main] INFO com.iluwatar.pipeline.ConvertToCharArrayHandler -- Current handler: class com.iluwatar.pipeline.ConvertToCharArrayHandler, input is ! of type class java.lang.String, output is [!], of type class [Ljava.lang.Character;
07:34:27.075 [main] INFO com.iluwatar.pipeline.App -- Pipeline output: [!]
```

## When to Use the Pipeline Pattern in Java

Use the Pipeline pattern when you want to

* When you need to process data in a sequence of stages.
* When each stage of processing is independent and can be easily replaced or reordered.
* When you want to improve the scalability and maintainability of data processing code.

## Pipeline Pattern Java Tutorials

* [The Pipeline design pattern (in Java) (Medium)](https://medium.com/@deepakbapat/the-pipeline-design-pattern-in-java-831d9ce2fe21)
* [The Pipeline Pattern — for fun and profit (Aaron Weatherall)](https://medium.com/@aaronweatherall/the-pipeline-pattern-for-fun-and-profit-9b5f43a98130)
* [Pipelines (Microsoft)](https://docs.microsoft.com/en-us/previous-versions/msp-n-p/ff963548(v=pandp.10))

## Real-World Applications of Pipeline Pattern in Java

* Data transformation and ETL (Extract, Transform, Load) processes.
* Compilers for processing source code through various stages such as lexical analysis, syntax analysis, semantic analysis, and code generation.
* Image processing applications where multiple filters are applied sequentially.
* Logging frameworks where messages pass through multiple handlers for formatting, filtering, and output.

## Benefits and Trade-offs of Pipeline Pattern

Benefits:

* Decoupling: Each stage of the pipeline is a separate component, making the system more modular and easier to maintain.
* Reusability: Individual stages can be reused in different pipelines.
* Extensibility: New stages can be added without modifying existing ones.
* Scalability: Pipelines can be parallelized by running different stages on different processors or threads.

Trade-offs:

* Complexity: Managing the flow of data through multiple stages can introduce complexity.
* Performance Overhead: Each stage introduces some performance overhead due to context switching and data transfer between stages.
* Debugging Difficulty: Debugging pipelines can be more challenging since the data flows through multiple components.

## Related Java Design Patterns

* [Chain of Responsibility](https://java-design-patterns.com/patterns/chain-of-responsibility/): Both patterns involve passing data through a series of handlers, but in Chain of Responsibility, handlers can decide not to pass the data further.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): Both patterns involve adding behavior dynamically, but Decorator wraps additional behavior around objects, whereas Pipeline processes data in discrete steps.
* [Composite](https://java-design-patterns.com/patterns/composite/): Like Pipeline, Composite also involves hierarchical processing, but Composite is more about part-whole hierarchies.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
