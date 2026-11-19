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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class RepositoryTest {

  @Test
  void saveAssignsSequentialIds() {
    var customers = new CustomerRepository();
    var alice = customers.save(new Customer("Alice", "alice@example.com"));
    var bob = customers.save(new Customer("Bob", "bob@example.com"));
    assertEquals(1L, alice.getId());
    assertEquals(2L, bob.getId());
  }

  @Test
  void savingAgainKeepsTheIdAndStoresTheUpdate() {
    var customers = new CustomerRepository();
    var alice = customers.save(new Customer("Alice", "alice@example.com"));
    alice.setEmail("alice@example.org");
    customers.save(alice);
    assertEquals(1L, alice.getId());
    assertEquals(1, customers.findAll().size());
    assertEquals("alice@example.org", customers.findById(1).orElseThrow().getEmail());
  }

  @Test
  void findByIdReturnsTheSavedObject() {
    var products = new ProductRepository();
    var keyboard = products.save(new Product("Keyboard", new BigDecimal("49.90")));
    assertSame(keyboard, products.findById(keyboard.getId()).orElseThrow());
    assertTrue(products.findById(99).isEmpty());
  }

  @Test
  void eachRepositoryHasItsOwnIdSequence() {
    var customers = new CustomerRepository();
    var products = new ProductRepository();
    var alice = customers.save(new Customer("Alice", "alice@example.com"));
    var keyboard = products.save(new Product("Keyboard", new BigDecimal("49.90")));
    assertEquals(1L, alice.getId());
    assertEquals(1L, keyboard.getId());
  }

  @Test
  void saveRejectsAnObjectWithAnIdFromAnotherRepository() {
    var first = new CustomerRepository();
    var second = new CustomerRepository();
    var alice = first.save(new Customer("Alice", "alice@example.com"));
    assertThrows(IllegalArgumentException.class, () -> second.save(alice));
    assertTrue(second.findAll().isEmpty());
    var bob = second.save(new Customer("Bob", "bob@example.com"));
    assertEquals(1L, bob.getId());
    assertEquals(List.of(bob), second.findAll());
  }

  @Test
  void deleteRemovesOnlyStoredObjects() {
    var customers = new CustomerRepository();
    var alice = customers.save(new Customer("Alice", "alice@example.com"));
    assertFalse(customers.delete(new Customer("Bob", "bob@example.com")));
    assertTrue(customers.delete(alice));
    assertFalse(customers.delete(alice));
    assertTrue(customers.findAll().isEmpty());
  }

  @Test
  void customerRepositoryFindsByEmailIgnoringCase() {
    var customers = new CustomerRepository();
    var bob = customers.save(new Customer("Bob", "bob@example.com"));
    assertSame(bob, customers.findByEmail("BOB@example.com").orElseThrow());
    assertTrue(customers.findByEmail("carol@example.com").isEmpty());
  }

  @Test
  void productRepositoryFindsCheaperProducts() {
    var products = new ProductRepository();
    var keyboard = products.save(new Product("Keyboard", new BigDecimal("49.90")));
    products.save(new Product("Monitor", new BigDecimal("199.00")));
    var mouse = products.save(new Product("Mouse", new BigDecimal("19.90")));
    assertEquals(List.of(keyboard, mouse), products.findCheaperThan(new BigDecimal("50")));
  }
}
