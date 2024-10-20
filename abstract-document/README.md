---
title: "Abstract Document Pattern in Java: Simplifying Data Handling with Flexibility"
shortTitle: Abstract Document
description: "Explore the Abstract Document design pattern in Java. Learn its intent, explanation, applicability, benefits, and see real-world examples to implement flexible and dynamic data structures."
category: Structural
language: en
tag:
  - Abstraction
  - Decoupling
  - Dynamic typing
  - Encapsulation
  - Extensibility
  - Polymorphism
---

## Intent of Abstract Document Design Pattern

The Abstract Document design pattern in Java is a crucial structural design pattern that provides a consistent way to handle hierarchical and tree-like data structures by defining a common interface for various document types. It separates the core document structure from specific data formats, enabling dynamic updates and simplified maintenance.

## Detailed Explanation of Abstract Document Pattern with Real-World Examples

The Abstract Document design pattern in Java allows dynamic handling of non-static properties. This pattern uses concept of traits to enable type safety and separate properties of different classes into set of interfaces.

Real-world example

> Consider a library system implementing the Abstract Document design pattern in Java, where books can have diverse formats and attributes: physical books, eBooks, and audiobooks. Each format has unique properties, such as page count for physical books, file size for eBooks, and duration for audiobooks. The Abstract Document design pattern allows the library system to manage these diverse formats flexibly. By using this pattern, the system can store and retrieve properties dynamically, without needing a rigid structure for each book type, making it easier to add new formats or attributes in the future without significant changes to the codebase.

In plain words

> Abstract Document pattern allows attaching properties to objects without them knowing about it.

Wikipedia says

> An object-oriented structural design pattern for organizing objects in loosely typed key-value stores and exposing the data using typed views. The purpose of the pattern is to achieve a high degree of flexibility between components in a strongly typed language where new properties can be added to the object-tree on the fly, without losing the support of type-safety. The pattern makes use of traits to separate different properties of a class into different interfaces.

## Programmatic Example of Abstract Document Pattern in Java

Consider a car that consists of multiple parts. However, we don't know if the specific car really has all the parts, or just some of them. Our cars are dynamic and extremely flexible.

Let's first define the base classes `Document` and `AbstractDocument`. They basically make the object hold a property map and any amount of child objects.

```java
public interface Document {

    Void put(String key, Object value);

    Object get(String key);

    <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}

public abstract class AbstractDocument implements Document {

    private final Map<String, Object> properties;

    protected AbstractDocument(Map<String, Object> properties) {
        Objects.requireNonNull(properties, "properties map is required");
        this.properties = properties;
    }

    @Override
    public Void put(String key, Object value) {
        properties.put(key, value);
        return null;
    }

    @Override
    public Object get(String key) {
        return properties.get(key);
    }

    @Override
    public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
        return Stream.ofNullable(get(key))
                .filter(Objects::nonNull)
                .map(el -> (List<Map<String, Object>>) el)
                .findAny()
                .stream()
                .flatMap(Collection::stream)
                .map(constructor);
    }
    
    // Other properties and methods...
}
```

Next we define an enum `Property` and a set of interfaces for type, price, model and parts. This allows us to create static looking interface to our `Car` class.

```java
public enum Property {

    PARTS, TYPE, PRICE, MODEL
}

public interface HasType extends Document {

    default Optional<String> getType() {
        return Optional.ofNullable((String) get(Property.TYPE.toString()));
    }
}

public interface HasPrice extends Document {

    default Optional<Number> getPrice() {
        return Optional.ofNullable((Number) get(Property.PRICE.toString()));
    }
}

public interface HasModel extends Document {

    default Optional<String> getModel() {
        return Optional.ofNullable((String) get(Property.MODEL.toString()));
    }
}

public interface HasParts extends Document {

    default Stream<Part> getParts() {
        return children(Property.PARTS.toString(), Part::new);
    }
}
```

Now we are ready to introduce the `Car`.

```java
public class Car extends AbstractDocument implements HasModel, HasPrice, HasParts {

    public Car(Map<String, Object> properties) {
        super(properties);
    }
}
```

And finally here's how we construct and use the `Car` in a full example.

```java
  public static void main(String[] args) {
    LOGGER.info("Constructing parts and car");

    var wheelProperties = Map.of(
            Property.TYPE.toString(), "wheel",
            Property.MODEL.toString(), "15C",
            Property.PRICE.toString(), 100L);

    var doorProperties = Map.of(
            Property.TYPE.toString(), "door",
            Property.MODEL.toString(), "Lambo",
            Property.PRICE.toString(), 300L);

    var carProperties = Map.of(
            Property.MODEL.toString(), "300SL",
            Property.PRICE.toString(), 10000L,
            Property.PARTS.toString(), List.of(wheelProperties, doorProperties));

    var car = new Car(carProperties);

    LOGGER.info("Here is our car:");
    LOGGER.info("-> model: {}", car.getModel().orElseThrow());
    LOGGER.info("-> price: {}", car.getPrice().orElseThrow());
    LOGGER.info("-> parts: ");
    car.getParts().forEach(p -> LOGGER.info("\t{}/{}/{}",
            p.getType().orElse(null),
            p.getModel().orElse(null),
            p.getPrice().orElse(null))
    );
}
```

The program output:

```
07:21:57.391 [main] INFO com.iluwatar.abstractdocument.App -- Constructing parts and car
07:21:57.393 [main] INFO com.iluwatar.abstractdocument.App -- Here is our car:
07:21:57.393 [main] INFO com.iluwatar.abstractdocument.App -- -> model: 300SL
07:21:57.394 [main] INFO com.iluwatar.abstractdocument.App -- -> price: 10000
07:21:57.394 [main] INFO com.iluwatar.abstractdocument.App -- -> parts: 
07:21:57.395 [main] INFO com.iluwatar.abstractdocument.App -- 	wheel/15C/100
07:21:57.395 [main] INFO com.iluwatar.abstractdocument.App -- 	door/Lambo/300
```

## Abstract Document Pattern Class Diagram

![Abstract Document](./etc/abstract-document.png "Abstract Document Traits and Domain")

## When to Use the Abstract Document Pattern in Java

The Abstract Document design pattern is especially beneficial in scenarios requiring management of different document types in Java that share some common attributes or behaviors, but also have unique attributes or behaviors specific to their individual types. Here are some scenarios where the Abstract Document design pattern can be applicable:

* Content Management Systems (CMS): In a CMS, you might have various types of content such as articles, images, videos, etc. Each type of content could have shared attributes like creation date, author, and tags, while also having specific attributes like image dimensions for images or video duration for videos.

* File Systems: If you're designing a file system where different types of files need to be managed, such as documents, images, audio files, and directories, the Abstract Document pattern can help provide a consistent way to access attributes like file size, creation date, etc., while allowing for specific attributes like image resolution or audio duration.

* E-commerce Systems: An e-commerce platform might have different product types such as physical products, digital downloads, and subscriptions. Each type could share common attributes like name, price, and description, while having unique attributes like shipping weight for physical products or download link for digital products.

* Medical Records Systems: In healthcare, patient records might include various types of data such as demographics, medical history, test results, and prescriptions. The Abstract Document pattern can help manage shared attributes like patient ID and date of birth, while accommodating specialized attributes like test results or prescribed medications.

* Configuration Management: When dealing with configuration settings for software applications, there can be different types of configuration elements, each with its own set of attributes. The Abstract Document pattern can be used to manage these configuration elements while ensuring a consistent way to access and manipulate their attributes.

* Educational Platforms: Educational systems might have various types of learning materials such as text-based content, videos, quizzes, and assignments. Common attributes like title, author, and publication date can be shared, while unique attributes like video duration or assignment due dates can be specific to each type.

* Project Management Tools: In project management applications, you could have different types of tasks like to-do items, milestones, and issues. The Abstract Document pattern could be used to handle general attributes like task name and assignee, while allowing for specific attributes like milestone date or issue priority.

* Documents have diverse and evolving attribute structures.

* Dynamically adding new properties is a common requirement.

* Decoupling data access from specific formats is crucial.

* Maintainability and flexibility are critical for the codebase.

The key idea behind the Abstract Document design pattern is to provide a flexible and extensible way to manage different types of documents or entities with shared and distinct attributes. By defining a common interface and implementing it across various document types, you can achieve a more organized and consistent approach to handling complex data structures.

## Benefits and Trade-offs of Abstract Document Pattern

Benefits:

* Flexibility: Accommodates varied document structures and properties.

* Extensibility: Dynamically add new attributes without breaking existing code.

* Maintainability: Promotes clean and adaptable code due to separation of concerns.

* Reusability: Typed views enable code reuse for accessing specific attribute types.

Trade-offs:

* Complexity: Requires defining interfaces and views, adding implementation overhead.

* Performance: Might introduce slight performance overhead compared to direct data access.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Java Design Patterns: A Hands-On Experience with Real-World Examples](https://amzn.to/3yhh525)
* [Pattern-Oriented Software Architecture Volume 4: A Pattern Language for Distributed Computing (v. 4)](https://amzn.to/49zRP4R)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
* [Abstract Document Pattern (Wikipedia)](https://en.wikipedia.org/wiki/Abstract_Document_Pattern)
* [Dealing with Properties (Martin Fowler)](http://martinfowler.com/apsupp/properties.pdf)
