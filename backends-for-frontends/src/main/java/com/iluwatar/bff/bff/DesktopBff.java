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

import com.iluwatar.bff.dto.DesktopDashboardResponse;
import com.iluwatar.bff.service.AuthService;
import com.iluwatar.bff.service.OrderService;
import com.iluwatar.bff.service.SupplierService;
import java.util.ArrayList;
import java.util.List;

/**
 * Backend For Frontend serving the intranet clients (desktop app, chatbot) from the diagram. It
 * aggregates the auth, order and supplier services and reshapes the results into a richer,
 * back-office style payload. Unlike {@link MobileBff}, it does not touch the cart service at all
 * and it does reach into the intranet-only supplier service, matching the fan-out drawn in the
 * pattern diagram.
 */
public final class DesktopBff implements ClientBff<DesktopDashboardResponse> {

  /** The customer authentication service. */
  private final AuthService authService;

  /** The order service. */
  private final OrderService orderService;

  /** The supplier service (intranet-only). */
  private final SupplierService supplierService;

  /**
   * Creates a desktop BFF wired to the downstream services it depends on.
   *
   * @param auth the customer authentication service
   * @param orders the order service
   * @param suppliers the supplier service
   */
  public DesktopBff(
      final AuthService auth, final OrderService orders, final SupplierService suppliers) {
    this.authService = auth;
    this.orderService = orders;
    this.supplierService = suppliers;
  }

  @Override
  public DesktopDashboardResponse getDashboard(final String userId) {
    var user = authService.getUser(userId);
    var orders = orderService.getOrders(userId);

    var orderStatuses =
        orders.stream()
            .map(order -> order.id() + ": " + order.productName() + " [" + order.status() + "]")
            .toList();

    var supplierStockSummaries = new ArrayList<String>();
    for (var order : orders) {
      for (var supplierRecord : supplierService.getSupplierRecords(order.productName())) {
        supplierStockSummaries.add(
            supplierRecord.supplierName()
                + ": "
                + supplierRecord.stockLevel()
                + " units of "
                + order.productName());
      }
    }

    return new DesktopDashboardResponse(
        "Welcome back, " + user.displayName(),
        user.loyaltyTier(),
        List.copyOf(orderStatuses),
        List.copyOf(supplierStockSummaries));
  }
}
