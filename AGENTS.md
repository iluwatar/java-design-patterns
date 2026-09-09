# Java Design Patterns - Priming Context for AI Agents

## Quick Overview
- This repository is a comprehensive collection of design patterns implemented in Java.
- The project demonstrates how to solve common software design problems using standard patterns.
- The code for each pattern lives directly in this repository, alongside its explanatory README.
- These readmes are published on the java-design-patterns.com website.
- Another repository (https://github.com/iluwatar/java-design-patterns-vuepress-web) handles the deployment of the website.

## Stack
- **Java 21**: The primary programming language used for pattern implementations.
- **Maven**: Dependency management and build tool.
- **JUnit 5**: The testing framework used to verify pattern behaviors.
- **Mockito**: Used for mocking dependencies in unit tests.
- **Lombok**: Used to reduce boilerplate code (getters, setters, etc.).
- **Spotless**: Enforces consistent code formatting via Google Java Format.

## Trusted Sources
- [Java SE 21 Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/)
- [Maven Official Documentation](https://maven.apache.org/guides/index.html)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [Project Wiki](https://github.com/iluwatar/java-design-patterns/wiki)

## Structure
- `/pom.xml`: The root Maven configuration file that defines global dependencies and lists all pattern modules.
- `/[pattern-name]/`: Individual folders for each design pattern (e.g., `/abstract-factory`, `/builder`), acting as standalone Maven modules.
- `/[pattern-name]/src/main/java/`: Contains the actual Java implementation classes of the specific design pattern.
- `/[pattern-name]/src/test/java/`: Contains the JUnit tests verifying the pattern's behavior.
- `/[pattern-name]/README.md`: The documentation for the pattern, which gets published to the main website.

## Patterns
- Keep pattern implementations simple, atomic, and easy to understand.
- Write descriptive and meaningful names for classes, interfaces, and methods.
- Always include comprehensive unit tests for every new pattern or code modification.
- Follow the Google Java Format strictly (enforced by Spotless).
- Document the intent, explanation, and real-world usage clearly in each module's `README.md`.

## Anti-patterns
- Avoid overcomplicating patterns with unnecessary external dependencies or complex frameworks.
- Do not introduce business logic that distracts from the core mechanism of the design pattern itself.
- Submitting new patterns or features without corresponding unit tests is strictly discouraged.
- Avoid large monolithic packages; each pattern should reside in its own isolated module.

## Example Design Pattern
When a new design pattern is added to the repository, it generally follows these steps:
- **Create a Module**: Create a new folder for the pattern in the root directory (e.g., `/my-new-pattern`).
- **Update root pom.xml**: Add `<module>my-new-pattern</module>` to the `<modules>` section of the root `pom.xml`.
- **Add Module pom.xml**: Create a `pom.xml` inside the new folder that inherits from the parent project.
- **Write the Code**: Implement the pattern logic under `src/main/java/com/iluwatar/mynewpattern`, usually including an `App.java` class to demonstrate its usage.
- **Write the Tests**: Add comprehensive unit tests under `src/test/java/com/iluwatar/mynewpattern`.
- **Document**: Create a `README.md` at the root of the new module, structured with standard sections like Intent, Explanation, Class diagram, Applicability, and Real world examples.
