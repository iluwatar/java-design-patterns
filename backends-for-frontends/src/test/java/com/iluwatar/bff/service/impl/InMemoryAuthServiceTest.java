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
 * copies of the Software is furnished to do so, subject to the following conditions:
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
package com.iluwatar.bff.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.iluwatar.bff.model.User;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** Tests for {@link InMemoryAuthService}. */
class InMemoryAuthServiceTest {

  private static final String USER_ID = "u-1";

  @Test
  void shouldReturnUserForKnownId() {
    var expected = new User(USER_ID, "Alice", "GOLD");
    var service = new InMemoryAuthService(Map.of(USER_ID, expected));

    var actual = service.getUser(USER_ID);

    assertEquals(expected, actual);
  }

  @Test
  void shouldThrowForUnknownId() {
    var service = new InMemoryAuthService(Map.of(USER_ID, new User(USER_ID, "Alice", "GOLD")));

    assertThrows(IllegalArgumentException.class, () -> service.getUser("unknown-id"));
  }
}
