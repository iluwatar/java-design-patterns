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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

/**
 * Special cases unit tests. (including the successful scenario {@link ReceiptDto})
 */
class SpecialCasesTest {
  private static ApplicationServices applicationServices;
  private static ReceiptViewModel receipt;

  @BeforeAll
  static void beforeAll() {
    Db.getInstance().seedUser("ignite1771", 1000.0);
    Db.getInstance().seedItem("computer", 800.0);
    Db.getInstance().seedItem("car", 20000.0);

    applicationServices = new ApplicationServicesImpl();
  }

  @BeforeEach
  void beforeEach() {
    MaintenanceLock.getInstance().setLock(false);
  }

  @Test
  void testDownForMaintenance() {
    final Logger LOGGER = (Logger) LoggerFactory.getLogger(DownForMaintenance.class);

    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    listAppender.start();
    LOGGER.addAppender(listAppender);

    MaintenanceLock.getInstance().setLock(true);
    receipt = applicationServices.loggedInUserPurchase(null, null);
    receipt.show();

    List<ILoggingEvent> loggingEventList = listAppender.list;
    assertEquals("Down for maintenance", loggingEventList.get(0).getMessage());
    assertEquals(Level.INFO, loggingEventList.get(0).getLevel());
  }

  @Test
  void testInvalidUser() {
    final Logger LOGGER = (Logger) LoggerFactory.getLogger(InvalidUser.class);

    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    listAppender.start();
    LOGGER.addAppender(listAppender);

    receipt = applicationServices.loggedInUserPurchase("a", null);
    receipt.show();

    List<ILoggingEvent> loggingEventList = listAppender.list;
    assertEquals("Invalid user: a", loggingEventList.get(0).getMessage());
    assertEquals(Level.INFO, loggingEventList.get(0).getLevel());
  }

  @Test
  void testOutOfStock() {
    final Logger LOGGER = (Logger) LoggerFactory.getLogger(OutOfStock.class);

    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    listAppender.start();
    LOGGER.addAppender(listAppender);

    receipt = applicationServices.loggedInUserPurchase("ignite1771", "tv");
    receipt.show();

    List<ILoggingEvent> loggingEventList = listAppender.list;
    assertEquals("Out of stock: tv for user = ignite1771 to buy"
        , loggingEventList.get(0).getMessage());
    assertEquals(Level.INFO, loggingEventList.get(0).getLevel());
  }

  @Test
  void testInsufficientFunds() {
    final Logger LOGGER = (Logger) LoggerFactory.getLogger(InsufficientFunds.class);

    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    listAppender.start();
    LOGGER.addAppender(listAppender);

    receipt = applicationServices.loggedInUserPurchase("ignite1771", "car");
    receipt.show();

    List<ILoggingEvent> loggingEventList = listAppender.list;
    assertEquals("Insufficient funds: 1000.0 of user: ignite1771 for buying item: car"
        , loggingEventList.get(0).getMessage());
    assertEquals(Level.INFO, loggingEventList.get(0).getLevel());
  }

  @Test
  void testReceiptDto() {
    final Logger LOGGER = (Logger) LoggerFactory.getLogger(ReceiptDto.class);

    ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
    listAppender.start();
    LOGGER.addAppender(listAppender);

    receipt = applicationServices.loggedInUserPurchase("ignite1771", "computer");
    receipt.show();

    List<ILoggingEvent> loggingEventList = listAppender.list;
    assertEquals("Receipt: 800.0 paid"
        , loggingEventList.get(0).getMessage());
    assertEquals(Level.INFO, loggingEventList.get(0).getLevel());
  }
}
