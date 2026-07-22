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
package com.iluwatar.bff;

import com.iluwatar.bff.bff.DesktopBff;
import com.iluwatar.bff.bff.MobileBff;
import com.iluwatar.bff.model.CartItem;
import com.iluwatar.bff.model.Order;
import com.iluwatar.bff.model.Product;
import com.iluwatar.bff.model.SupplierRecord;
import com.iluwatar.bff.model.User;
import com.iluwatar.bff.service.impl.InMemoryAuthService;
import com.iluwatar.bff.service.impl.InMemoryCartService;
import com.iluwatar.bff.service.impl.InMemoryOrderService;
import com.iluwatar.bff.service.impl.InMemorySupplierService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link App} demonstrates the Backends For Frontends (BFF) pattern.
 *
 * <p>A single set of downstream microservices (customer authentication, cart, order and supplier
 * services) is shared by every client. Two different client-facing gateways -- {@link MobileBff}
 * for the mobile apps and {@link DesktopBff} for the intranet desktop/chatbot clients -- each call
 * a different subset of those services and reshape the results into a response tailored to what
 * their own client actually needs, rather than exposing one one-size-fits-all API to every client.
 */
public final class App {

  /** Logger for this class. */
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /** User id used in the demonstration. */
  private static final String USER_ID = "u-1";

  /** Product id used in the demonstration. */
  private static final String PRODUCT_ID = "p-42";

  /** Unit price of the demo product in US dollars. */
  private static final double DEMO_PRICE_USD = 79.99;

  /** Supplier stock level used in the demonstration. */
  private static final int DEMO_STOCK_LEVEL = 120;

  private App() {
    // utility class
  }

  /**
   * Program entry point.
   *
   * @param args no argument sent
   */
  public static void main(final String[] args) {
    // shared downstream microservices, as drawn in the pattern diagram
    var authService = new InMemoryAuthService(Map.of(USER_ID, new User(USER_ID, "Alice", "GOLD")));

    var product = new Product(PRODUCT_ID, "Wireless Headphones", DEMO_PRICE_USD);
    var cartService = new InMemoryCartService(Map.of(USER_ID, List.of(new CartItem(product, 2))));

    var orderService =
        new InMemoryOrderService(
            Map.of(
                USER_ID,
                List.of(
                    new Order("o-1", "Wireless Headphones", "DELIVERED"),
                    new Order("o-2", "USB-C Cable", "IN_TRANSIT"))));

    var supplierService =
        new InMemorySupplierService(
            Map.of(
                "Wireless Headphones",
                List.of(new SupplierRecord(PRODUCT_ID, "Acme Audio Co.", DEMO_STOCK_LEVEL))));

    // client-specific BFFs, each calling only the services their client needs
    var mobileBff = new MobileBff(authService, cartService, orderService);
    var desktopBff = new DesktopBff(authService, orderService, supplierService);

    var mobileResponse = mobileBff.getDashboard(USER_ID);
    LOGGER.info("Mobile BFF response: {}", mobileResponse);

    var desktopResponse = desktopBff.getDashboard(USER_ID);
    LOGGER.info("Desktop BFF response: {}", desktopResponse);
  }
}
