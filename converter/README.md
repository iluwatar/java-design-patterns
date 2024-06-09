---
title: "Converter Pattern in Java: Streamlining Data Conversion Across Layers"
shortTitle: Converter
description: "Discover the benefits and implementation of the Converter Pattern in Java. Learn how to achieve seamless bidirectional conversion between different data formats, promoting clean code and flexibility in your applications."
category: Structural
language: en
tag:
  - Compatibility
  - Data transformation
  - Decoupling
  - Interface
  - Object mapping
  - Wrapping
---

## Also known as

* Mapper
* Translator

## Intent of Converter Design Pattern

The purpose of the Converter Pattern is to provide a generic, systematic way of bidirectional conversion between corresponding data types. This allows for a clean, decoupled implementation where types are unaware of each other. Additionally, the Converter pattern supports bidirectional collection mapping, minimizing boilerplate code.

## Detailed Explanation of Converter Pattern with Real-World Examples

Real-world example

> In a real-world scenario, consider a library system that interacts with a third-party book database. The library uses an internal book format, while the third-party database uses a different format. By employing the Converter Pattern, a converter class can transform the third-party book data into the library's format and vice versa. This ensures seamless integration without altering the internal structures of either system.

In plain words

> The Converter Pattern simplifies mapping instances of one class to instances of another class, ensuring consistent and clean data transformation.

## Programmatic Example of Converter Pattern in Java

In applications, it's common for the database layer to have entities that need mapping to DTOs (Data Transfer Objects) for business logic. This mapping often involves many classes, necessitating a generic solution.

We introduce a generic `Converter` class:

```java
public class Converter<T, U> {

    private final Function<T, U> fromDto;
    private final Function<U, T> fromEntity;

    public Converter(final Function<T, U> fromDto, final Function<U, T> fromEntity) {
        this.fromDto = fromDto;
        this.fromEntity = fromEntity;
    }

    public final U convertFromDto(final T dto) {
        return fromDto.apply(dto);
    }

    public final T convertFromEntity(final U entity) {
        return fromEntity.apply(entity);
    }

    public final List<U> createFromDtos(final Collection<T> dtos) {
        return dtos.stream().map(this::convertFromDto).collect(Collectors.toList());
    }

    public final List<T> createFromEntities(final Collection<U> entities) {
        return entities.stream().map(this::convertFromEntity).collect(Collectors.toList());
    }
}
```

Specialized converters inherit from this base class:

```java
public class UserConverter extends Converter<UserDto, User> {

    public UserConverter() {
        super(UserConverter::convertToEntity, UserConverter::convertToDto);
    }

    private static UserDto convertToDto(User user) {
        return new UserDto(user.firstName(), user.lastName(), user.active(), user.userId());
    }

    private static User convertToEntity(UserDto dto) {
        return new User(dto.firstName(), dto.lastName(), dto.active(), dto.email());
    }
}
```

Mapping between `User` and `UserDto` becomes straightforward:

```java
  public static void main(String[] args) {
    Converter<UserDto, User> userConverter = new UserConverter();

    UserDto dtoUser = new UserDto("John", "Doe", true, "whatever[at]wherever.com");
    User user = userConverter.convertFromDto(dtoUser);
    LOGGER.info("Entity converted from DTO: {}", user);

    var users = List.of(
            new User("Camile", "Tough", false, "124sad"),
            new User("Marti", "Luther", true, "42309fd"),
            new User("Kate", "Smith", true, "if0243")
    );
    LOGGER.info("Domain entities:");
    users.stream().map(User::toString).forEach(LOGGER::info);

    LOGGER.info("DTO entities converted from domain:");
    List<UserDto> dtoEntities = userConverter.createFromEntities(users);
    dtoEntities.stream().map(UserDto::toString).forEach(LOGGER::info);
}
```

Program output:

```
08:28:27.019 [main] INFO com.iluwatar.converter.App -- Entity converted from DTO: User[firstName=John, lastName=Doe, active=true, userId=whatever[at]wherever.com]
08:28:27.035 [main] INFO com.iluwatar.converter.App -- Domain entities:
08:28:27.035 [main] INFO com.iluwatar.converter.App -- User[firstName=Camile, lastName=Tough, active=false, userId=124sad]
08:28:27.035 [main] INFO com.iluwatar.converter.App -- User[firstName=Marti, lastName=Luther, active=true, userId=42309fd]
08:28:27.035 [main] INFO com.iluwatar.converter.App -- User[firstName=Kate, lastName=Smith, active=true, userId=if0243]
08:28:27.036 [main] INFO com.iluwatar.converter.App -- DTO entities converted from domain:
08:28:27.037 [main] INFO com.iluwatar.converter.App -- UserDto[firstName=Camile, lastName=Tough, active=false, email=124sad]
08:28:27.037 [main] INFO com.iluwatar.converter.App -- UserDto[firstName=Marti, lastName=Luther, active=true, email=42309fd]
08:28:27.037 [main] INFO com.iluwatar.converter.App -- UserDto[firstName=Kate, lastName=Smith, active=true, email=if0243]
```

## When to Use the Converter Pattern in Java

Use the Converter Pattern in the following situations:

* When there are types that logically correspond with each other, and there is a need to convert between them.
* In applications that interact with external systems or services that require data in a specific format.
* For legacy systems integration where data models differ significantly from newer systems.
* When aiming to encapsulate conversion logic to promote single responsibility and cleaner code.

## Converter Pattern Java Tutorials

* [Converter Pattern in Java 8 (Boldare)](http://www.xsolve.pl/blog/converter-pattern-in-java-8/)

## Real-World Applications of Converter Pattern in Java

* Data Transfer Objects (DTOs) conversions in multi-layered applications.
* Adapting third-party data structures or API responses to internal models.
* ORM (Object-Relational Mapping) frameworks for mapping between database records and domain objects.
* Microservices architecture for data exchange between different services.

## Benefits and Trade-offs of Converter Pattern

Benefits:

* Separation of Concerns: Encapsulates conversion logic in a single component, keeping the rest of the application unaware of the conversion details.
* Reusability: Converter components can be reused across the application or even in different applications.
* Flexibility: Makes it easy to add new conversions without impacting existing code, adhering to the [Open/Closed Principle](https://java-design-patterns.com/principles/#open-closed-principle).
* Interoperability: Facilitates communication between different systems or application layers by translating data formats.

Trade-offs:

* Overhead: Introducing converters can add complexity and potential performance overhead, especially in systems with numerous data formats.
* Duplication: There's a risk of duplicating model definitions if not carefully managed, leading to increased maintenance.

## Related Java Design Patterns

* [Adapter](https://java-design-patterns.com/patterns/adapter/): Similar in intent to adapting interfaces, but Converter focuses on data models.
* [Facade](https://java-design-patterns.com/patterns/facade/): Provides a simplified interface to a complex system, which might involve data conversion.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Converters can use different strategies for conversion, especially when multiple formats are involved.

## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
