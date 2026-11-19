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
package com.iluwatar.layersupertype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

class DomainObjectTest {

  @Test
  void newObjectHasNoId() {
    var customer = new Customer("Alice", "alice@example.com");
    assertTrue(customer.isNew());
    assertNull(customer.getId());
  }

  @Test
  void idCanBeAssignedOnlyOnce() {
    var customer = new Customer("Alice", "alice@example.com");
    customer.assignId(1);
    assertFalse(customer.isNew());
    assertEquals(1L, customer.getId());
    assertThrows(IllegalStateException.class, () -> customer.assignId(2));
  }

  @Test
  void sameTypeAndSameIdAreEqual() {
    var first = new Customer("Alice", "alice@example.com");
    var second = new Customer("Alice Smith", "alice@example.org");
    first.assignId(1);
    second.assignId(1);
    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }

  @Test
  void sameTypeAndDifferentIdAreNotEqual() {
    var first = new Customer("Alice", "alice@example.com");
    var second = new Customer("Alice", "alice@example.com");
    first.assignId(1);
    second.assignId(2);
    assertNotEquals(first, second);
  }

  @Test
  void differentTypesWithSameIdAreNotEqual() {
    var customer = new Customer("Alice", "alice@example.com");
    var product = new Product("Keyboard", new BigDecimal("49.90"));
    customer.assignId(1);
    product.assignId(1);
    assertNotEquals(customer, product);
  }

  @Test
  void unsavedObjectsAreEqualOnlyToThemselves() {
    var first = new Customer("Alice", "alice@example.com");
    var second = new Customer("Alice", "alice@example.com");
    assertEquals(first, first);
    assertNotEquals(first, second);
    assertFalse(first.equals(null));
  }

  @Test
  void hashCodeDoesNotChangeWhenIdIsAssigned() {
    var customer = new Customer("Alice", "alice@example.com");
    var set = new HashSet<Customer>();
    set.add(customer);
    var before = customer.hashCode();
    customer.assignId(1);
    assertEquals(before, customer.hashCode());
    assertTrue(set.contains(customer));
  }
}
