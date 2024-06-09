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
package com.iluwatar.tablemodule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
  @Test
  void testCanEqual() {
    assertFalse((new User(1, "janedoe", "iloveyou"))
            .canEqual("Other"));
  }

  @Test
  void testCanEqual2() {
    var user = new User(1, "janedoe", "iloveyou");
    assertTrue(user.canEqual(new User(1, "janedoe",
            "iloveyou")));
  }

  @Test
  void testEquals1() {
    var user = new User(1, "janedoe", "iloveyou");
    assertNotEquals(user, new User(123, "abcd",
        "qwerty"));
  }

  @Test
  void testEquals2() {
    var user = new User(1, "janedoe", "iloveyou");
    assertEquals(user, new User(1, "janedoe",
            "iloveyou"));
  }

  @Test
  void testEquals3() {
    var user = new User(123, "janedoe", "iloveyou");
    assertNotEquals(user, new User(1, "janedoe",
            "iloveyou"));
  }

  @Test
  void testEquals4() {
    var user = new User(1, null, "iloveyou");
    assertNotEquals(user, new User(1, "janedoe",
            "iloveyou"));
  }

  @Test
  void testEquals5() {
    var user = new User(1, "iloveyou", "iloveyou");
    assertNotEquals(user, new User(1, "janedoe",
            "iloveyou"));
  }

  @Test
  void testEquals6() {
    var user = new User(1, "janedoe", "janedoe");
    assertNotEquals(user, new User(1, "janedoe",
            "iloveyou"));
  }

  @Test
  void testEquals7() {
    var user = new User(1, "janedoe", null);
    assertNotEquals(user, new User(1, "janedoe",
            "iloveyou"));
  }

  @Test
  void testEquals8() {
    var user = new User(1, null, "iloveyou");
    assertEquals(user, new User(1, null,
            "iloveyou"));
  }

  @Test
  void testEquals9() {
    var user = new User(1, "janedoe", null);
    assertEquals(user, new User(1, "janedoe",
            null));
  }

  @Test
  void testHashCode1() {
    assertEquals(-1758941372, (new User(1, "janedoe",
            "iloveyou")).hashCode());

  }

  @Test
  void testHashCode2() {
    assertEquals(-1332207447, (new User(1, null,
            "iloveyou")).hashCode());
  }

  @Test
  void testHashCode3() {
    assertEquals(-426522485, (new User(1, "janedoe",
            null)).hashCode());
  }

  @Test
  void testSetId() {
    var user = new User(1, "janedoe", "iloveyou");
    user.setId(2);
    assertEquals(2, user.getId());
  }

  @Test
  void testSetPassword() {
    var user = new User(1, "janedoe", "tmp");
    user.setPassword("iloveyou");
    assertEquals("iloveyou", user.getPassword());
  }

  @Test
  void testSetUsername() {
    var user = new User(1, "tmp", "iloveyou");
    user.setUsername("janedoe");
    assertEquals("janedoe", user.getUsername());
  }

  @Test
  void testToString() {
    var user = new User(1, "janedoe", "iloveyou");
    assertEquals(String.format("User(id=%s, username=%s, password=%s)",
            user.getId(), user.getUsername(), user.getPassword()),
            user.toString());
  }
}

