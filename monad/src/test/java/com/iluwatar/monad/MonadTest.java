package com.iluwatar.monad;


import junit.framework.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Objects;

public class MonadTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void testForInvalidName() {
    thrown.expect(IllegalStateException.class);
    User tom = new User(null, 21, Sex.MALE, "tom@foo.bar");
    Validator.of(tom).validate(User::getName, Objects::nonNull, "name cannot be null").get();
  }

  @Test
  public void testForInvalidAge() {
    thrown.expect(IllegalStateException.class);
    User john = new User("John", 17, Sex.MALE, "john@qwe.bar");
    Validator.of(john).validate(User::getName, Objects::nonNull, "name cannot be null")
        .validate(User::getAge, age -> age > 21, "user is underaged")
        .get();
  }

  @Test
  public void testForValid() {
    User sarah = new User("Sarah", 42, Sex.FEMALE, "sarah@det.org");
    User validated = Validator.of(sarah).validate(User::getName, Objects::nonNull, "name cannot be null")
        .validate(User::getAge, age -> age > 21, "user is underaged")
        .validate(User::getSex, sex -> sex == Sex.FEMALE, "user is not female")
        .validate(User::getEmail, email -> email.contains("@"), "email does not contain @ sign")
        .get();
    Assert.assertSame(validated, sarah);
  }
}
