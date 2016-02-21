package com.iluwatar.monad;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The Monad pattern defines a monad structure, that enables chaining operations
 * in pipelines and processing data step by step.
 * Formally, monad consists of a type constructor M and two operations:
 * <br>bind - that takes monadic object and a function from plain object to the
 * monadic value and returns monadic value.
 * <br>return - that takes plain type object and returns this object wrapped in a monadic value.
 * <p>
 * In the given example, the Monad pattern is represented as a {@link Validator} that takes an instance
 * of a plain object with {@link Validator#of(Object)}
 * and validates it {@link Validator#validate(Function, Predicate, String)} against given predicates.
 * <p>As a validation result {@link Validator#get()} it either returns valid object {@link Validator#t}
 * or throws a list of exceptions {@link Validator#exceptions} collected during validation.
 */
public class App {

  /**
   * Program entry point.
   *
   * @param args @param args command line args
   */
  public static void main(String[] args) {
    User user = new User("user", 24, Sex.FEMALE, "foobar.com");
    System.out.println(Validator.of(user).validate(User::getName, Objects::nonNull, "name is null")
        .validate(User::getName, name -> !name.isEmpty(), "name is empty")
        .validate(User::getEmail, email -> !email.contains("@"), "email doesn't containt '@'")
        .validate(User::getAge, age -> age > 20 && age < 30, "age isn't between...").get().toString());
  }
}
