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

import com.iluwatar.bff.dto.MobileDashboardResponse;
import com.iluwatar.bff.service.AuthService;
import com.iluwatar.bff.service.CartService;
import com.iluwatar.bff.service.OrderService;
import java.util.List;

/**
 * Backend For Frontend serving the mobile clients (iOS app, Android app) from the diagram. It
 * aggregates the auth, cart and order services and reshapes the results into a small payload suited
 * to a phone screen and a limited-bandwidth connection. It never calls the supplier service: a
 * mobile shopper has no use for back-office stock data, so this BFF simply does not expose it,
 * rather than sending it and letting the client ignore it.
 */
public final class MobileBff implements ClientBff<MobileDashboardResponse> {

  /** Maximum number of recent order summaries to include in the mobile response. */
  private static final int MAX_RECENT_ORDERS = 3;

  /** The customer authentication service. */
  private final AuthService authService;

  /** The cart service. */
  private final CartService cartService;

  /** The order service. */
  private final OrderService orderService;

  /**
   * Creates a mobile BFF wired to the downstream services it depends on.
   *
   * @param auth the customer authentication service
   * @param cart the cart service
   * @param orders the order service
   */
  public MobileBff(final AuthService auth, final CartService cart, final OrderService orders) {
    this.authService = auth;
    this.cartService = cart;
    this.orderService = orders;
  }

  @Override
  public MobileDashboardResponse getDashboard(final String userId) {
    var user = authService.getUser(userId);
    var cart = cartService.getCart(userId);
    var orders = orderService.getOrders(userId);

    var cartTotal = cart.stream().mapToDouble(item -> item.lineTotal()).sum();
    var recentOrderSummaries =
        orders.stream()
            .limit(MAX_RECENT_ORDERS)
            .map(order -> order.productName() + " (" + order.status() + ")")
            .toList();

    return new MobileDashboardResponse(
        "Hi " + user.displayName() + "!",
        cart.size(),
        cartTotal,
        List.copyOf(recentOrderSummaries));
  }
}
