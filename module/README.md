---
title: Module
category: Structural
language: en
tag:
    - Decoupling
    - Encapsulation
    - Layered architecture
    - Object composition
---

## Intent

The Module pattern aims to encapsulate a group of related elements, such as classes, functions, and variables, into a single unit or module, enhancing cohesion and reducing dependencies between different parts of an application.

## Explanation

Real-world example

> Consider the organization of a modern kitchen as a real-world analogy to the Module pattern. In a kitchen, different sections are dedicated to specific functions: there's a cooking module with stoves and ovens, a cleaning module with sinks and dishwashers, and a storage module with refrigerators and cabinets. Each module encapsulates all the tools and equipment needed for its specific function, ensuring they are not intermingled with those of other modules. This organization enhances efficiency, as each section has everything necessary for its tasks, and reduces confusion by keeping unrelated items separate. This modular approach mirrors the Module pattern in software, where different functionalities are grouped into distinct, self-contained units that manage their own resources and dependencies. 

In plain words

> The Module pattern encapsulates related functions and data into a single unit, allowing for organized and manageable software components.

Wikipedia says

> In software engineering, the module pattern is a design pattern used to implement the concept of software modules, defined by modular programming, in a programming language with incomplete direct support for the concept.
> 
> This pattern can be implemented in several ways depending on the host programming language, such as the singleton design pattern, object-oriented static members in a class and procedural global functions. In Python, the pattern is built into the language, and each .py file is automatically a module. The same applies to Ada, where the package can be considered a module (similar to a static class).

**Programmatic Example**

The Module design pattern is a design pattern that provides a way to wrap a set of related functionalities into a single unit, which can be an object or a function. This pattern is particularly useful in large systems where it's necessary to group related functionality and data to improve code organization and readability.

In the provided code, we have a great example of the Module pattern. The pattern is implemented using two modules: `FileLoggerModule` and `ConsoleLoggerModule`. Both of these modules are singletons, meaning that only one instance of each module can exist at a time.

Here's a simplified version of the `FileLoggerModule`:

```java
class FileLoggerModule extends Logger {
    private static final String OUTPUT_FILE = "output.log";
    private static final String ERROR_FILE = "error.log";

    private static FileLoggerModule instance;

    private FileLoggerModule() {
        this.output = OUTPUT_FILE;
        this.error = ERROR_FILE;
    }

    public static FileLoggerModule getSingleton() {
        if (instance == null) {
            instance = new FileLoggerModule();
        }
        return instance;
    }

    @Override
    public void printString(String message) {
        System.out.println("Writing to " + output + ": " + message);
    }

    @Override
    public void printErrorString(String errorMessage) {
        System.out.println("Writing to " + error + ": " + errorMessage);
    }
}
```

In this module, we have a `getSingleton` method that ensures only one instance of `FileLoggerModule` is created. The `printString` and `printErrorString` methods are used to print messages and errors to the console, simulating writing to a file.

Similarly, the `ConsoleLoggerModule` is another module that logs messages and errors to the console:

```java
class ConsoleLoggerModule extends Logger {
    private static ConsoleLoggerModule instance;

    private ConsoleLoggerModule() {}

    public static ConsoleLoggerModule getSingleton() {
        if (instance == null) {
            instance = new ConsoleLoggerModule();
        }
        return instance;
    }

    @Override
    public void printString(String message) {
        System.out.println("Console Output: " + message);
    }

    @Override
    public void printErrorString(String errorMessage) {
        System.err.println("Console Error: " + errorMessage);
    }
}
```

In the `App` class, these modules are used to log messages:

```java
public class App {
    public void prepare() {
        FileLoggerModule.getSingleton().prepare();
        ConsoleLoggerModule.getSingleton().prepare();
    }

    public void execute(String message) {
        FileLoggerModule.getSingleton().printString(message);
        ConsoleLoggerModule.getSingleton().printString(message);
    }

    public static void main(String[] args) {
        App app = new App();
        app.prepare();
        app.execute("Hello, Module Pattern!");
        app.unprepare();
    }
}
```

In the `prepare` method, the `prepare` method of each module is called. In the `execute` method, the `printString` method of each module is used to log a message. This demonstrates how the Module pattern can be used to encapsulate related functionality into a single unit, improving code organization and readability.

## Class diagram

![Module](./etc/module.png "Module")

## Applicability

* When you need to group related functionality and data to improve code organization and readability.
* To encapsulate internal details of a part of a program, exposing only what is necessary through a well-defined interface.
* Useful in large systems for dividing the codebase into manageable sections.

## Known Uses

* Java Platform Module System introduced in Java 9.
* Organizing libraries or frameworks into coherent units.
* Web applications that segment logic into various modules for maintainability.

## Consequences

Benefits:

* Enhances code clarity and maintainability by grouping related features.
* Reduces global scope pollution and namespace clashes.
* Facilitates better testing and debugging by isolating functionalities.

Trade-offs:

* Initial complexity in setting up modules and their interactions.
* Over-modularization can lead to unnecessary complexity and overhead.

## Related Patterns

* [Facade](https://java-design-patterns.com/patterns/facade/): Simplifies the module's interface for clients. Both aim to simplify usage by hiding complexity.
* [Singleton](https://java-design-patterns.com/patterns/singleton/): Often used within a module to ensure a single instance of a component is used across the system.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Java 9 Modularity: Patterns and Practices for Developing Maintainable Applications](https://amzn.to/4b4CWIK)
