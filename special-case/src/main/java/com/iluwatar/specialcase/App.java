/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.specialcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>The Special Case Pattern is a software design pattern that encapsulates particular cases
 * into subclasses that provide special behaviors.</p>
 *
 * <p>In this example ({@link ReceiptViewModel}) encapsulates all particular cases.</p>
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    // DB seeding
    LOGGER.info("Db seeding: " + "1 user: {\"ignite1771\", amount = 1000.0}, "
        + "2 products: {\"computer\": price = 800.0, \"car\": price = 20000.0}");
    Db.getInstance().seedUser("ignite1771", 1000.0);
    Db.getInstance().seedItem("computer", 800.0);
    Db.getInstance().seedItem("car", 20000.0);

    final var applicationServices = new ApplicationServicesImpl();
    ReceiptViewModel receipt;

    LOGGER.info("[REQUEST] User: " + "abc123" + " buy product: " + "tv");
    receipt = applicationServices.loggedInUserPurchase("abc123", "tv");
    receipt.show();
    MaintenanceLock.getInstance().setLock(false);
    LOGGER.info("[REQUEST] User: " + "abc123" + " buy product: " + "tv");
    receipt = applicationServices.loggedInUserPurchase("abc123", "tv");
    receipt.show();
    LOGGER.info("[REQUEST] User: " + "ignite1771" + " buy product: " + "tv");
    receipt = applicationServices.loggedInUserPurchase("ignite1771", "tv");
    receipt.show();
    LOGGER.info("[REQUEST] User: " + "ignite1771" + " buy product: " + "car");
    receipt = applicationServices.loggedInUserPurchase("ignite1771", "car");
    receipt.show();
    LOGGER.info("[REQUEST] User: " + "ignite1771" + " buy product: " + "computer");
    receipt = applicationServices.loggedInUserPurchase("ignite1771", "computer");
    receipt.show();
  }
}
