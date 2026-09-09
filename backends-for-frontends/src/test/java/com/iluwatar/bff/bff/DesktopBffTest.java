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
package com.iluwatar.bff.bff;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.bff.model.Order;
import com.iluwatar.bff.model.SupplierRecord;
import com.iluwatar.bff.model.User;
import com.iluwatar.bff.service.impl.InMemoryAuthService;
import com.iluwatar.bff.service.impl.InMemoryOrderService;
import com.iluwatar.bff.service.impl.InMemorySupplierService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** Tests for {@link DesktopBff}. */
class DesktopBffTest {

  private static final String USER_ID = "u-1";

  @Test
  void shouldAggregateOrdersAndSupplierStockIntoDesktopShape() {
    var authService = new InMemoryAuthService(Map.of(USER_ID, new User(USER_ID, "Alice", "GOLD")));
    var orderService =
        new InMemoryOrderService(
            Map.of(USER_ID, List.of(new Order("o-1", "Headphones", "DELIVERED"))));
    var supplierService =
        new InMemorySupplierService(
            Map.of("Headphones", List.of(new SupplierRecord("p-1", "Acme Audio", 30))));

    var bff = new DesktopBff(authService, orderService, supplierService);
    var response = bff.getDashboard(USER_ID);

    assertEquals("Welcome back, Alice", response.greeting());
    assertEquals("GOLD", response.loyaltyTier());
    assertTrue(response.orderStatuses().get(0).contains("Headphones"));
    assertTrue(response.supplierStockSummaries().get(0).contains("Acme Audio"));
  }

  @Test
  void shouldReturnNoSupplierSummariesWhenNoOrdersExist() {
    var authService =
        new InMemoryAuthService(Map.of(USER_ID, new User(USER_ID, "Carol", "STANDARD")));
    var orderService = new InMemoryOrderService(Map.of(USER_ID, List.of()));
    var supplierService = new InMemorySupplierService(Map.of());

    var bff = new DesktopBff(authService, orderService, supplierService);
    var response = bff.getDashboard(USER_ID);

    assertEquals(0, response.orderStatuses().size());
    assertEquals(0, response.supplierStockSummaries().size());
  }
}
