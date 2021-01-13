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
public class SpecialCasesTest {
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
  public void beforeEach() {
    MaintenanceLock.getInstance().setLock(false);
  }

  @Test
  public void testDownForMaintenance() {
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
  public void testInvalidUser() {
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
  public void testOutOfStock() {
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
  public void testInsufficientFunds() {
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
  public void testReceiptDto() {
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
