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
package com.iluwatar.specialcase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Special Case Pattern is a software design pattern that encapsulates particular cases into
 * subclasses that provide special behaviors.
 *
 * <p>In this example ({@link ReceiptViewModel}) encapsulates all particular cases.
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  private static final String LOGGER_STRING = "[REQUEST] User: {} buy product: {}";
  private static final String TEST_USER_1 = "ignite1771";
  private static final String TEST_USER_2 = "abc123";
  private static final String ITEM_TV = "tv";
  private static final String ITEM_CAR = "car";
  private static final String ITEM_COMPUTER = "computer";

  /** Program entry point. */
  public static void main(String[] args) {
    // DB seeding
    LOGGER.info(
        "Db seeding: "
            + "1 user: {\"ignite1771\", amount = 1000.0}, "
            + "2 products: {\"computer\": price = 800.0, \"car\": price = 20000.0}");
    Db.getInstance().seedUser(TEST_USER_1, 1000.0);
    Db.getInstance().seedItem(ITEM_COMPUTER, 800.0);
    Db.getInstance().seedItem(ITEM_CAR, 20000.0);

    final var applicationServices = new ApplicationServicesImpl();
    ReceiptViewModel receipt;

    LOGGER.info(LOGGER_STRING, TEST_USER_2, ITEM_TV);
    receipt = applicationServices.loggedInUserPurchase(TEST_USER_2, ITEM_TV);
    receipt.show();
    MaintenanceLock.getInstance().setLock(false);
    LOGGER.info(LOGGER_STRING, TEST_USER_2, ITEM_TV);
    receipt = applicationServices.loggedInUserPurchase(TEST_USER_2, ITEM_TV);
    receipt.show();
    LOGGER.info(LOGGER_STRING, TEST_USER_1, ITEM_TV);
    receipt = applicationServices.loggedInUserPurchase(TEST_USER_1, ITEM_TV);
    receipt.show();
    LOGGER.info(LOGGER_STRING, TEST_USER_1, ITEM_CAR);
    receipt = applicationServices.loggedInUserPurchase(TEST_USER_1, ITEM_CAR);
    receipt.show();
    LOGGER.info(LOGGER_STRING, TEST_USER_1, ITEM_COMPUTER);
    receipt = applicationServices.loggedInUserPurchase(TEST_USER_1, ITEM_COMPUTER);
    receipt.show();
  }
}
