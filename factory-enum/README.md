---
title: "Factory Enum Pattern in Java: Simplifying Object Access"
shortTitle: Factory Enum
description: "Learn the Factory Enum Pattern in Java using practical examples. Understand how to use enums to manage object instantiation in a clean, scalable, and singleton-safe way."
category: Creational
language: en
tag:
    - Abstraction
    - Enum Singleton
    - Gang of Four
    - Factory Pattern
    - Object Instantiation
---

## Intent of Factory Enum Pattern

The **Factory Enum Pattern** is a creational pattern that leverages Java's `enum` type to manage object instantiation. It provides a centralized, type-safe, and singleton-friendly way to access various implementations of a common interface.

This pattern is ideal when the set of possible objects is known and fixed (e.g., file processors, coin types, document types).

## Real-World Analogy

> Imagine a toolbox where each tool has a fixed slot. You don’t create a new tool each time — you just access the correct one from the right compartment. Similarly, with Factory Enum, each `enum` constant maps to a specific object instance that can be reused or constructed once.

## Programmatic Example Using FileProcessor

In this example, we want to process different file types: Excel and PDF. Each processor implements the `FileProcessor` interface.

### FileProcessor Interface

```java
public interface FileProcessor {
  String getDescription();
}
```
Implementations
```java
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExcelProcessor implements FileProcessor {

  static final String DESCRIPTION = "This is an Excel processor.";

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }

  public static class InstanceHolder {
    public static final ExcelProcessor INSTANCE = new ExcelProcessor();
  }
}
```
```java
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PDFProcessor implements FileProcessor {

  static final String DESCRIPTION = "This is a PDF processor.";

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }

  public static class InstanceHolder {
    public static final PDFProcessor INSTANCE = new PDFProcessor();
  }
}
```
Factory Enum: FileProcessorType
```java
@RequiredArgsConstructor
@Getter
public enum FileProcessorType {
  EXCEL(ExcelProcessor.InstanceHolder.INSTANCE),
  PDF(PDFProcessor.InstanceHolder.INSTANCE);

  private final FileProcessor instance;
}
```
Client Code
```java
public static void main(String[] args) {
  LOGGER.info("Start processing files.");
  var excel = FileProcessorType.EXCEL.getInstance();
  var pdf = FileProcessorType.PDF.getInstance();
  LOGGER.info(excel.getDescription());
  LOGGER.info(pdf.getDescription());
}
```
Output
```
INFO: Start processing files.
INFO: This is an Excel processor.
INFO: This is a PDF processor.
```
## When to Use the Factory Enum Pattern

* When object types are known in advance and won't change frequently.

* When you want singleton instances per type.

* To avoid traditional factory classes and keep the logic self-contained within an enum.

* When readability and simplicity are preferred over extensibility.

## Benefits
* Type-safe and readable – enum provides compiler-checked safety.
* Singleton-friendly – each instance is created once and reused.
* No need for external factory class – the enum holds both the type and instance.
* Thread-safe by nature of enum and static holders.

## Trade-offs
* Not suitable for dynamic or extensible object types.
* Violates the Open/Closed Principle if new types are added frequently.
* Slightly unconventional compared to traditional Factory pattern.

## Related Patterns
* [Traditional Factory Method](https://java-design-patterns.com/patterns/factory/): Separates creation logic into a factory class.
* [Enum Singleton](https://www.baeldung.com/java-singleton#enum-singleton): Ensures one instance per type via enum.
* [Abstract Factory](https://java-design-patterns.com/patterns/abstract-factory/): Can be considered a kind of Factory that works with groups of products.
* [Service Locator](https://java-design-patterns.com/patterns/service-locator/): If you combine enum with lookup behavior.

## References and Credits
* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0Rk5y)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/3UpTLrG)