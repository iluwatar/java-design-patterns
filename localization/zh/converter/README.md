---
layout: pattern
title: Converter
folder: converter
permalink: /patterns/converter/zh
categories: Creational
language: zh
tags:
 - Decoupling
---

## 目的

转换器模式的目的是提供相应类型之间双向转换的通用方法，允许进行干净的实现，而类型之间无需相互了解。此外，Converter模式引入了双向集合映射，从而将样板代码减少到最少。

## 解释

真实世界例子

> 在真实的应用中经常有这种情况，数据库层包含需要被转换成业务逻辑层DTO来使用的实体。对于潜在的大量类进行类似的映射，我们需要一种通用的方法来实现这一点。

通俗的说

> 转换器模式让一个类的实例映射成另一个类的实例变得简单

**程序示例**

我们需要一个通用的方案来解决映射问题。让我们来介绍一个通用的转换器。

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

专属的转换器像下面一样从基类继承。

```java
public class UserConverter extends Converter<UserDto, User> {

  public UserConverter() {
    super(UserConverter::convertToEntity, UserConverter::convertToDto);
  }

  private static UserDto convertToDto(User user) {
    return new UserDto(user.getFirstName(), user.getLastName(), user.isActive(), user.getUserId());
  }

  private static User convertToEntity(UserDto dto) {
    return new User(dto.getFirstName(), dto.getLastName(), dto.isActive(), dto.getEmail());
  }

}
```

现在，在User和UserDto之间的映射变得轻而易举。

```java
var userConverter = new UserConverter();
var dtoUser = new UserDto("John", "Doe", true, "whatever[at]wherever.com");
var user = userConverter.convertFromDto(dtoUser);
```

## 类图

![alt text](../../converter/etc/converter.png "Converter Pattern")

## 适用性

在下面这些情况下使用转换器模式：

* 如果你的类型在逻辑上相互对应，并需要在它们之间转换实体
* 当你想根据上下文提供不同的类型转换方式时
* 每当你引入DTO（数据传输对象）时你可能都需要将其转换为
  DO

## 鸣谢

* [Converter](http://www.xsolve.pl/blog/converter-pattern-in-java-8/)
