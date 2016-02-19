package com.iluwatar.monad;

import java.util.Objects;

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
