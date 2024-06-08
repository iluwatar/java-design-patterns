---
title: "Execute Around Pattern in Java: Encapsulating Pre and Post Execution Steps"
shortTitle: Execute Around
description: "Explore the Execute Around Pattern in Java with detailed explanations, real-world examples, and best practices. Learn how to implement this design pattern to streamline resource management."
category: Behavioral
language: en
tag:
  - Closure
  - Code simplification
  - Encapsulation
  - Functional decomposition
  - Resource management
head:
  - - meta
    - name: keywords
      content:
---

## Also known as

* Around Method Pattern
* Resource Block Management

## Intent of Execute Around Design Pattern

Real-world business applications often require executing necessary operations before and after the business method invocation. The Execute Around Pattern in Java provides a way to encapsulate these operations, enhancing code readability and reusability.

## Detailed Explanation of Execute Around Pattern with Real-World Examples

Real-world example

> A real-world analogy for the Execute Around pattern can be found in the use of rental cars. When you rent a car, the rental company handles all the setup (cleaning the car, filling it with gas, ensuring it's in good condition) and cleanup (checking the car back in, inspecting it for damage, refueling it if necessary) processes for you. As a customer, you simply use the car for your intended purpose without worrying about the setup and cleanup. This pattern of abstracting away the repetitive tasks around the main operation is similar to the Execute Around pattern in software, where the setup and cleanup of resources are handled by a reusable method, allowing the main logic to be executed seamlessly.

In plain words

> Execute Around idiom handles boilerplate code before and after business method.

[Stack Overflow](https://stackoverflow.com/questions/341971/what-is-the-execute-around-idiom) says

> Basically it's the pattern where you write a method to do things which are always required, e.g. resource allocation and clean-up, and make the caller pass in "what we want to do with the resource".

## Programmatic Example of Execute Around Pattern in Java

The Execute Around Pattern is a design pattern that is widely used in Java programming to manage resource allocation and deallocation. It ensures that important setup and cleanup operations are performed reliably around a core business operation. This pattern is particularly useful for resource management, such as handling files, databases, or network connections in Java applications.

A class needs to be provided for writing text strings to files. To make it easy for the user, the service class opens and closes the file automatically. The user only has to specify what is written into which file.

`SimpleFileWriter` class implements the Execute Around idiom. It takes `FileWriterAction` as a constructor argument allowing the user to specify what gets written into the file.

```java

@FunctionalInterface
public interface FileWriterAction {
    void writeFile(FileWriter writer) throws IOException;
}

@Slf4j
public class SimpleFileWriter {
    public SimpleFileWriter(String filename, FileWriterAction action) throws IOException {
        LOGGER.info("Opening the file");
        try (var writer = new FileWriter(filename)) {
            LOGGER.info("Executing the action");
            action.writeFile(writer);
            LOGGER.info("Closing the file");
        }
    }
}
```

The following code demonstrates how `SimpleFileWriter` is used. `Scanner` is used to print the file contents after the writing finishes.

```java
  public static void main(String[] args) throws IOException {

    // create the file writer and execute the custom action
    FileWriterAction writeHello = writer -> writer.write("Gandalf was here");
    new SimpleFileWriter("testfile.txt", writeHello);

    // print the file contents
    try (var scanner = new Scanner(new File("testfile.txt"))) {
        while (scanner.hasNextLine()) {
            LOGGER.info(scanner.nextLine());
        }
    }
}
```

Here's the console output.

```
21:18:07.185 [main] INFO com.iluwatar.execute.around.SimpleFileWriter - Opening the file
21:18:07.188 [main] INFO com.iluwatar.execute.around.SimpleFileWriter - Executing the action
21:18:07.189 [main] INFO com.iluwatar.execute.around.SimpleFileWriter - Closing the file
21:18:07.199 [main] INFO com.iluwatar.execute.around.App - Gandalf was here
```

## When to Use the Execute Around Pattern in Java

When to use the Execute Around Pattern in Java:

* Useful in scenarios requiring repetitive setup and cleanup activities, particularly in resource management (e.g., files, network connections, database sessions).
* Ideal for ensuring proper resource handling and cleanup in the face of exceptions, ensuring resources do not leak.
* Suitable in any Java application where the same preparation and finalization steps are executed around varying core functionalities.

## Real-World Applications of Execute Around Pattern in Java

In real-world Java applications, the Execute Around Pattern is applied in these scenarios:

* Java's try-with-resources statement, which ensures that resources are closed after execution regardless of whether an exception was thrown.
* Frameworks like Spring for managing database transactions, where predefined cleanup or rollback operations are performed depending on the execution outcome.

## Benefits and Trade-offs of Execute Around Pattern

Implementing the Execute Around Pattern in Java offers several benefits and trade-offs.

Benefits:

* Reduces boilerplate code by abstracting routine setup and cleanup tasks.
* Increases code clarity and maintainability by separating business logic from resource management.
* Ensures robustness by automatically handling resource cleanup, even in error situations.

Trade-offs:

* Introduces additional abstraction layers, which might increase complexity and obscure control flow for some developers.
* May require more sophisticated understanding of closures and functional interfaces in Java.

## Related Java Design Patterns

* [Template Method](https://java-design-patterns.com/patterns/template-method/): Similar in concept but differs in that it uses inheritance and abstract classes, while Execute Around typically uses interfaces and lambdas.
* [Decorator](https://java-design-patterns.com/patterns/decorator/): Shares the concept of adding functionality around a core component; can be extended to wrap additional behaviors dynamically.

## References and Credits

* [Effective Java](https://amzn.to/4aDdWbs)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3vUGApm)
* [Functional Programming in Java](https://amzn.to/3JUIc5Q)
