package com.iluwatar.intercepting.filter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Date: 12/13/15 - 2:57 PM
 *
 * @author Jeroen Meulemeester
 */
public class OrderTest {

  private static final String EXPECTED_VALUE = "test";

  @Test
  public void testSetName() throws Exception {
    final Order order = new Order();
    order.setName(EXPECTED_VALUE);
    assertEquals(EXPECTED_VALUE, order.getName());
  }

  @Test
  public void testSetContactNumber() throws Exception {
    final Order order = new Order();
    order.setContactNumber(EXPECTED_VALUE);
    assertEquals(EXPECTED_VALUE, order.getContactNumber());
  }

  @Test
  public void testSetAddress() throws Exception {
    final Order order = new Order();
    order.setAddress(EXPECTED_VALUE);
    assertEquals(EXPECTED_VALUE, order.getAddress());
  }

  @Test
  public void testSetDepositNumber() throws Exception {
    final Order order = new Order();
    order.setDepositNumber(EXPECTED_VALUE);
    assertEquals(EXPECTED_VALUE, order.getDepositNumber());
  }

  @Test
  public void testSetOrder() throws Exception {
    final Order order = new Order();
    order.setOrder(EXPECTED_VALUE);
    assertEquals(EXPECTED_VALUE, order.getOrder());
  }

}
