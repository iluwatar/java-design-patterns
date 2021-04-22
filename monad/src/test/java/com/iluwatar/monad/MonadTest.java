/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.monad;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Objects;
import org.junit.jupiter.api.Test;

/**
 * Test for Monad Pattern
 */
public class MonadTest {

  @Test
  void testForInvalidName() {
    var tom = new User(null, 21, Sex.MALE, "tom@foo.bar");
    assertThrows(
        IllegalStateException.class,
        () -> Validator.of(tom)
            .validate(User::getName, Objects::nonNull, "name cannot be null")
            .get()
    );
  }

  @Test
  void testForInvalidAge() {
    var john = new User("John", 17, Sex.MALE, "john@qwe.bar");
    assertThrows(
        IllegalStateException.class,
        () -> Validator.of(john)
            .validate(User::getName, Objects::nonNull, "name cannot be null")
            .validate(User::getAge, age -> age > 21, "user is underage")
            .get()
    );
  }

  @Test
  void testForValid() {
    var sarah = new User("Sarah", 42, Sex.FEMALE, "sarah@det.org");
    var validated = Validator.of(sarah)
        .validate(User::getName, Objects::nonNull, "name cannot be null")
        .validate(User::getAge, age -> age > 21, "user is underage")
        .validate(User::getSex, sex -> sex == Sex.FEMALE, "user is not female")
        .validate(User::getEmail, email -> email.contains("@"), "email does not contain @ sign")
        .get();
    assertSame(validated, sarah);
  }
}
