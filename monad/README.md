---
title: Monad
category: Functional
language: en
tag:
 - Reactive
---

## Intent

Monad pattern based on monad from linear algebra represents the way of chaining operations
together step by step. Binding functions can be described as passing one's output to another's input
basing on the 'same type' contract. Formally, monad consists of a type constructor M and two
operations:
bind - that takes monadic object and a function from plain object to monadic value and returns monadic value
return - that takes plain type object and returns this object wrapped in a monadic value.

## Explanation

The Monad pattern provides a way to chain operations together and manage sequencing,
side effects, and exception handling in a consistent manner.

Real-world example

> Consider a conveyor belt in a factory: items move from one station to another,
> and each station performs a specific task, with the assurance that every task will be carried out,
> even if some items are rejected at certain stations.

In plain words

> Monad pattern ensures that each operation is executed regardless of the success or failure of previous ones.

Wikipedia says

> In functional programming, a monad is a structure that combines program fragments (functions)
> and wraps their return values in a type with additional computation. In addition to defining a
> wrapping monadic type, monads define two operators: one to wrap a value in the monad type, and
> another to compose together functions that output values of the monad type (these are known as
> monadic functions). General-purpose languages use monads to reduce boilerplate code needed for
> common operations (such as dealing with undefined values or fallible functions, or encapsulating
> bookkeeping code). Functional languages use monads to turn complicated sequences of functions into
> succinct pipelines that abstract away control flow, and side-effects.

**Programmatic Example**

Here’s the Monad implementation in Java.

The `Validator` takes an object, validates it against specified predicates, and collects any
validation errors. The `validate` method allows you to add validation steps, and the `get` method
either returns the validated object or throws an `IllegalStateException` with a list of validation
exceptions if any of the validation steps fail.

```java
public class Validator<T> {
  private final T obj;
  private final List<Throwable> exceptions = new ArrayList<>();

   private Validator(T obj) {
    this.obj = obj;
  }
  public static <T> Validator<T> of(T t) {
    return new Validator<>(Objects.requireNonNull(t));
  }

  public Validator<T> validate(Predicate<? super T> validation, String message) {
    if (!validation.test(obj)) {
      exceptions.add(new IllegalStateException(message));
    }
    return this;
  }

  public <U> Validator<T> validate(
      Function<? super T, ? extends U> projection,
      Predicate<? super U> validation,
      String message
  ) {
    return validate(projection.andThen(validation::test)::apply, message);
  }

  public T get() throws IllegalStateException {
    if (exceptions.isEmpty()) {
      return obj;
    }
    var e = new IllegalStateException();
    exceptions.forEach(e::addSuppressed);
    throw e;
  }
}
```

Next we define an enum `Sex`.

```java
public enum Sex {
  MALE, FEMALE
}
```

Now we can introduce the `User`.

```java
public record User(String name, int age, Sex sex, String email) {
}
```

And finally, a `User` object is validated for its name, email, and age using the `Validator` monad.

```java
public static void main(String[] args) {
        var user = new User("user", 24, Sex.FEMALE, "foobar.com");
        LOGGER.info(Validator.of(user).validate(User::name, Objects::nonNull, "name is null")
        .validate(User::name, name -> !name.isEmpty(), "name is empty")
        .validate(User::email, email -> !email.contains("@"), "email doesn't contains '@'")
        .validate(User::age, age -> age > 20 && age < 30, "age isn't between...").get()
        .toString());
}
```

## Class diagram
![alt text](./etc/monad.png "Monad")

## Applicability

Use the Monad in any of the following situations

* When you want to chain operations easily
* When you want to apply each function regardless of the result of any of them

## Credits

* [Design Pattern Reloaded by Remi Forax](https://youtu.be/-k2X7guaArU)
* [Brian Beckman: Don't fear the Monad](https://channel9.msdn.com/Shows/Going+Deep/Brian-Beckman-Dont-fear-the-Monads)
* [Monad on Wikipedia](https://en.wikipedia.org/wiki/Monad_(functional_programming))
