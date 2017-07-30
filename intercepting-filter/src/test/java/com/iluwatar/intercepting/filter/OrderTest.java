/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
    order.setOrderItem(EXPECTED_VALUE);
    assertEquals(EXPECTED_VALUE, order.getOrderItem());
  }

}
