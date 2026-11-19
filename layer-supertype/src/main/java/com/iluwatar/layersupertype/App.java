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

import java.math.BigDecimal;
import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;

/**
 * Layer Supertype puts the behaviour that all classes of one layer share into a common base class.
 *
 * <p>This example has two layers, each with its own supertype. In the domain layer, {@link
 * Customer} and {@link Product} extend {@link DomainObject}, which owns identity and equality. In
 * the data access layer, {@link CustomerRepository} and {@link ProductRepository} extend {@link
 * Repository}, which owns id generation and the basic save, find and delete operations. The
 * concrete classes contain only what is specific to them.
 */
@Slf4j
public class App {

  /** Program entry point. */
  public static void main(String[] args) {
    var customers = new CustomerRepository();
    var products = new ProductRepository();

    customers.save(new Customer("Alice", "alice@example.com"));
    var bob = customers.save(new Customer("Bob", "bob@example.com"));
    products.save(new Product("Keyboard", new BigDecimal("49.90")));
    products.save(new Product("Monitor", new BigDecimal("199.00")));
    products.save(new Product("Mouse", new BigDecimal("19.90")));

    LOGGER.info("Customers: {}", customers.findAll());
    LOGGER.info("Products: {}", products.findAll());

    LOGGER.info(
        "Customer with email bob@example.com: {}", customers.findByEmail("BOB@example.com"));
    LOGGER.info("Products cheaper than 50: {}", products.findCheaperThan(new BigDecimal("50")));

    var carol = new Customer("Carol", "carol@example.com");
    var newsletter = new HashSet<Customer>();
    newsletter.add(carol);
    customers.save(carol);
    LOGGER.info(
        "Carol is still found in the newsletter set after saving gave her id {}: {}",
        carol.getId(),
        newsletter.contains(carol));

    var unsaved = new Customer("Bob", "bob@example.com");
    LOGGER.info("An unsaved copy of Bob equals Bob: {}", unsaved.equals(bob));

    customers.delete(bob);
    LOGGER.info("Customers after deleting Bob: {}", customers.findAll());
  }
}
