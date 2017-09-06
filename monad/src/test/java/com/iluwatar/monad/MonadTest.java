/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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


import junit.framework.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Objects;

/**
 * Test for Monad Pattern
 */
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
