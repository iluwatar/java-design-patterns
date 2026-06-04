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
package com.iluwatar.immutable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class ImmutableUserTest {

  @Test
  void constructorSetsAllFields() {
    var user = new ImmutableUser("Alice", 30, List.of("admin"));
    assertEquals("Alice", user.getName());
    assertEquals(30, user.getAge());
    assertEquals(List.of("admin"), user.getRoles());
  }

  @Test
  void withAgeReturnsNewObjectWithUpdatedAge() {
    var original = new ImmutableUser("Alice", 30, List.of("admin"));
    var updated = original.withAge(31);

    assertEquals(31, updated.getAge());
    assertEquals("Alice", updated.getName());
    assertEquals(original.getRoles(), updated.getRoles());
  }

  @Test
  void withAgeDoesNotMutateOriginal() {
    var original = new ImmutableUser("Alice", 30, List.of("admin"));
    original.withAge(99);

    assertEquals(30, original.getAge());
  }

  @Test
  void withAgeReturnsDistinctInstance() {
    var original = new ImmutableUser("Alice", 30, List.of("admin"));
    var updated = original.withAge(31);

    assertNotSame(original, updated);
  }

  @Test
  void defensiveCopyPreventsExternalListMutation() {
    var mutableRoles = new ArrayList<>(List.of("viewer"));
    var user = new ImmutableUser("Bob", 25, mutableRoles);

    mutableRoles.add("editor");

    assertEquals(List.of("viewer"), user.getRoles());
  }

  @Test
  void getRolesReturnsUnmodifiableList() {
    var user = new ImmutableUser("Bob", 25, List.of("viewer"));

    assertThrows(UnsupportedOperationException.class, () -> user.getRoles().add("editor"));
  }
}
