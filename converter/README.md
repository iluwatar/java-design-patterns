---
title: Converter
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

## Intent

The purpose of the Converter pattern is to provide a generic, common way of bidirectional conversion between corresponding types, allowing a clean implementation in which the types do not need to be aware of each other. Moreover, the Converter pattern introduces bidirectional collection mapping, reducing a boilerplate code to minimum.

## Explanation

Real world example

> In a real-world scenario, consider a scenario where a library system needs to interact with a third-party book database. The library system uses its own internal book format, while the third-party database provides book information in a different format. To facilitate communication between the two systems, a Converter design pattern can be employed. This pattern will define a converter class that transforms the third-party book data format into the library's internal book format and vice versa. This ensures that the library system can seamlessly integrate with the third-party database without altering its own internal structure or the third-party system's format.

In plain words

> Converter pattern makes it easy to map instances of one class into instances of another class.

**Programmatic Example**

In real world applications it is often the case that database layer consists of entities that need to be mapped into DTOs for use on the business logic layer. Similar mapping is done for potentially huge amount of classes, and we need a generic way to achieve this.

We need a generic solution for the mapping problem. To achieve this, let's introduce a generic converter.

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

The specialized converters inherit from this base class as follows.

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

Now mapping between `User` and `UserDto` becomes trivial.

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

## Applicability

Use the Converter Pattern in the following situations:

* When there are types that logically correspond with each other, and there is a need to convert between them.
* In applications that interact with external systems or services that require data in a specific format.
* For legacy systems integration where data models differ significantly from newer systems.
* When aiming to encapsulate conversion logic to promote single responsibility and cleaner code.

## Tutorials

* [Converter Pattern in Java 8 (Boldare)](http://www.xsolve.pl/blog/converter-pattern-in-java-8/)

## Known Uses

* Data Transfer Objects (DTOs) conversions in multi-layered applications.
* Adapting third-party data structures or API responses to internal models.
* ORM (Object-Relational Mapping) frameworks for mapping between database records and domain objects.
* Microservices architecture for data exchange between different services.

## Consequences

Benefits:

* Separation of Concerns: Encapsulates conversion logic in a single component, keeping the rest of the application unaware of the conversion details.
* Reusability: Converter components can be reused across the application or even in different applications.
* Flexibility: Makes it easy to add new conversions without impacting existing code, adhering to the [Open/Closed Principle](https://java-design-patterns.com/principles/#open-closed-principle).
* Interoperability: Facilitates communication between different systems or application layers by translating data formats.

Trade-offs:

* Overhead: Introducing converters can add complexity and potential performance overhead, especially in systems with numerous data formats.
* Duplication: There's a risk of duplicating model definitions if not carefully managed, leading to increased maintenance.

## Related Patterns

* [Adapter](https://java-design-patterns.com/patterns/adapter/): Similar in intent to adapting interfaces, but Converter focuses on data models.
* [Facade](https://java-design-patterns.com/patterns/facade/): Provides a simplified interface to a complex system, which might involve data conversion.
* [Strategy](https://java-design-patterns.com/patterns/strategy/): Converters can use different strategies for conversion, especially when multiple formats are involved.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
