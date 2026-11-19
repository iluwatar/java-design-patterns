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
package com.iluwatar.compositionroot;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * The Composition Root pattern wires the whole object graph in one place, as close as possible to
 * the application's entry point. Here that place is {@link #main(String[])}.
 *
 * <p>Every {@code new} of a collaborator happens below, by hand, with no dependency injection
 * framework and no service locator. The classes being wired only accept their dependencies through
 * constructors, so none of them knows which implementation it gets. The root also decides
 * lifetimes: one {@link InMemoryOrderRepository} is shared by {@link OrderService} and {@link
 * SalesReport}, which is why the report sees the orders the service places.
 */
@Slf4j
public class App {

  /**
   * Program entry point and composition root.
   *
   * @param args command line arguments, not used
   */
  public static void main(String[] args) {
    // Compose the object graph: leaves first, then the classes that depend on them.
    ProductCatalog catalog =
        new InMemoryProductCatalog(Map.of("BOOK-001", 2_500L, "MUG-002", 1_200L));
    OrderRepository repository = new InMemoryOrderRepository();
    Notifier notifier = new LoggingNotifier();
    var pricing = new PricingService(catalog);
    var orderService = new OrderService(pricing, repository, notifier);
    var salesReport = new SalesReport(repository);

    // Use the composed graph.
    orderService.placeOrder("alice", "BOOK-001", 2);
    orderService.placeOrder("bob", "MUG-002", 3);
    LOGGER.info(
        "Placed {} orders, revenue {} cents",
        salesReport.orderCount(),
        salesReport.revenueInCents());
  }
}
