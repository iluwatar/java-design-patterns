/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Converter}
 */
class ConverterTest {

  private final UserConverter userConverter = new UserConverter();

  /**
   * Tests whether a converter created of opposite functions holds equality as a bijection.
   */
  @Test
  void testConversionsStartingFromDomain() {
    var u1 = new User("Tom", "Hanks", true, "tom@hanks.com");
    var u2 = userConverter.convertFromDto(userConverter.convertFromEntity(u1));
    assertEquals(u1, u2);
  }

  /**
   * Tests whether a converter created of opposite functions holds equality as a bijection.
   */
  @Test
  void testConversionsStartingFromDto() {
    var u1 = new UserDto("Tom", "Hanks", true, "tom@hanks.com");
    var u2 = userConverter.convertFromEntity(userConverter.convertFromDto(u1));
    assertEquals(u1, u2);
  }

  /**
   * Tests the custom users converter. Thanks to Java8 lambdas, converter can be easily and cleanly
   * instantiated allowing various different conversion strategies to be implemented.
   */
  @Test
  void testCustomConverter() {
    var converter = new Converter<UserDto, User>(
        userDto -> new User(
            userDto.firstName(),
            userDto.lastName(),
            userDto.active(),
            String.valueOf(new Random().nextInt())
        ),
        user -> new UserDto(
            user.firstName(),
            user.lastName(),
            user.active(),
            user.firstName().toLowerCase() + user.lastName().toLowerCase() + "@whatever.com")
    );
    var u1 = new User("John", "Doe", false, "12324");
    var userDto = converter.convertFromEntity(u1);
    assertEquals("johndoe@whatever.com", userDto.email());
  }

  /**
   * Test whether converting a collection of Users to DTO Users and then converting them back to
   * domain users returns an equal collection.
   */
  @Test
  void testCollectionConversion() {
    var users = List.of(
        new User("Camile", "Tough", false, "124sad"),
        new User("Marti", "Luther", true, "42309fd"),
        new User("Kate", "Smith", true, "if0243")
    );
    var fromDtos = userConverter.createFromDtos(userConverter.createFromEntities(users));
    assertEquals(users, fromDtos);
  }
}
