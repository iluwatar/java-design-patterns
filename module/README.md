---
title: Module
category: Structural
language: en
tag:
 - Decoupling
---

## Intent
Module pattern is used to implement the concept of software modules, defined by modular programming, in a programming language with incomplete direct support for the concept.

## Explanation

Real-world example

> In a bustling software city, different software components such as Database, UI, and API often need to collaborate. Instead of each component directly talking with every other, they rely on the module manager. This module manager acts like a central marketplace, where each component registers its services and requests for others. This ensures that components remain decoupled, and changes to one don't ripple throughout the system.


> Imagine a modern smartphone. It has different apps like messaging, camera, and music player. While each app functions independently, they sometimes need shared resources like access to contacts or storage. Instead of every app having its unique way to access these resources, they use the phone's built-in modules, like the Contacts module or the Storage module. This ensures a consistent experience for the user and avoids potential clashes between apps.

In plain words

> The Module pattern encapsulates related functions and data into a single unit, allowing for organized and manageable software components.

Wikipedia says

> In software engineering, the module pattern is a design pattern used to implement the concept of software modules, defined by modular programming, in a programming language with incomplete direct support for the concept.

> This pattern can be implemented in several ways depending on the host programming language, such as the singleton design pattern, object-oriented static members in a class and procedural global functions. In Python, the pattern is built into the language, and each .py file is automatically a module. The same applies to Ada, where the package can be considered a module (similar to a static class).

**Programmatic Example**

```java
//Define Logger abstract class
abstract class Logger {
    protected String output;
    protected String error;

    public abstract void prepare();
    public abstract void unprepare();
    public abstract void printString(String message);
    public abstract void printErrorString(String errorMessage);
}

//File log module
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
    public void prepare() {
        // For example, open file operation
        // add the action you want
    }

    @Override
    public void unprepare() {
        // For example, close file operation
        // add the action you want
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

//Console log module
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
    public void prepare() {
        //Initialize console operation
    }

    @Override
    public void unprepare() {
        //End console operation
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


public class App {
    public void prepare() {
        FileLoggerModule.getSingleton().prepare();
        ConsoleLoggerModule.getSingleton().prepare();
    }

    public void unprepare() {
        FileLoggerModule.getSingleton().unprepare();
        ConsoleLoggerModule.getSingleton().unprepare();
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

Programme outputs:
```
Writing to output.log: Hello, Module Pattern!
Console Output: Hello, Module Pattern!
```

## Class diagram
![alt text](./etc/module.png "Module")

## Applicability
The Module pattern can be considered a creational pattern and a structural pattern. It manages the creation and organization of other elements, and groups them as the structural pattern does.

An object that applies this pattern can provide the equivalent of a namespace, providing the initialization and finalization process of a static class or a class with static members with cleaner, more concise syntax and semantics.

## Credits

* [Module](https://en.wikipedia.org/wiki/Module_pattern)
