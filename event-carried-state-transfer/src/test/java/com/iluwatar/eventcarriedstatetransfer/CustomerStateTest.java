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
package com.iluwatar.eventcarriedstatetransfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CustomerStateTest {

  private final CustomerState initial =
      new CustomerState("C-1", "Alice", "Lisbon", new BigDecimal("500.00"), 1);

  @Test
  void changingTheAddressBumpsTheVersion() {
    var moved = initial.withShippingAddress("Porto");

    assertEquals("Porto", moved.shippingAddress());
    assertEquals(2, moved.version());
    assertEquals(initial.creditLimit(), moved.creditLimit());
  }

  @Test
  void changingTheCreditLimitBumpsTheVersion() {
    var richer = initial.withCreditLimit(new BigDecimal("900.00"));

    assertEquals(new BigDecimal("900.00"), richer.creditLimit());
    assertEquals(2, richer.version());
    assertEquals(initial.shippingAddress(), richer.shippingAddress());
  }

  @Test
  void rejectsInvalidState() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new CustomerState("C-1", "Alice", "Lisbon", BigDecimal.ONE, 0));
    assertThrows(
        NullPointerException.class,
        () -> new CustomerState(null, "Alice", "Lisbon", BigDecimal.ONE, 1));
    assertThrows(
        NullPointerException.class, () -> new CustomerState("C-1", "Alice", "Lisbon", null, 1));
  }
}
