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

package com.iluwatar.intercepting.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Date: 12/13/15 - 2:17 PM
 *
 * @author Jeroen Meulemeester
 */
public class FilterTest {

  private static final Order PERFECT_ORDER =
      new Order("name", "12345678901", "addr", "dep", "order");
  private static final Order WRONG_ORDER = new Order("name", "12345678901", "addr", "dep", "");
  private static final Order WRONG_DEPOSIT = new Order("name", "12345678901", "addr", "", "order");
  private static final Order WRONG_ADDRESS = new Order("name", "12345678901", "", "dep", "order");
  private static final Order WRONG_CONTACT = new Order("name", "", "addr", "dep", "order");
  private static final Order WRONG_NAME = new Order("", "12345678901", "addr", "dep", "order");

  static List<Object[]> getTestData() {
    return List.of(
        new Object[]{new NameFilter(), PERFECT_ORDER, ""},
        new Object[]{new NameFilter(), WRONG_NAME, "Invalid name!"},
        new Object[]{new NameFilter(), WRONG_CONTACT, ""},
        new Object[]{new NameFilter(), WRONG_ADDRESS, ""},
        new Object[]{new NameFilter(), WRONG_DEPOSIT, ""},
        new Object[]{new NameFilter(), WRONG_ORDER, ""},

        new Object[]{new ContactFilter(), PERFECT_ORDER, ""},
        new Object[]{new ContactFilter(), WRONG_NAME, ""},
        new Object[]{new ContactFilter(), WRONG_CONTACT, "Invalid contact number!"},
        new Object[]{new ContactFilter(), WRONG_ADDRESS, ""},
        new Object[]{new ContactFilter(), WRONG_DEPOSIT, ""},
        new Object[]{new ContactFilter(), WRONG_ORDER, ""},

        new Object[]{new AddressFilter(), PERFECT_ORDER, ""},
        new Object[]{new AddressFilter(), WRONG_NAME, ""},
        new Object[]{new AddressFilter(), WRONG_CONTACT, ""},
        new Object[]{new AddressFilter(), WRONG_ADDRESS, "Invalid address!"},
        new Object[]{new AddressFilter(), WRONG_DEPOSIT, ""},
        new Object[]{new AddressFilter(), WRONG_ORDER, ""},

        new Object[]{new DepositFilter(), PERFECT_ORDER, ""},
        new Object[]{new DepositFilter(), WRONG_NAME, ""},
        new Object[]{new DepositFilter(), WRONG_CONTACT, ""},
        new Object[]{new DepositFilter(), WRONG_ADDRESS, ""},
        new Object[]{new DepositFilter(), WRONG_DEPOSIT, "Invalid deposit number!"},
        new Object[]{new DepositFilter(), WRONG_ORDER, ""},

        new Object[]{new OrderFilter(), PERFECT_ORDER, ""},
        new Object[]{new OrderFilter(), WRONG_NAME, ""},
        new Object[]{new OrderFilter(), WRONG_CONTACT, ""},
        new Object[]{new OrderFilter(), WRONG_ADDRESS, ""},
        new Object[]{new OrderFilter(), WRONG_DEPOSIT, ""},
        new Object[]{new OrderFilter(), WRONG_ORDER, "Invalid order!"}
    );
  }

  @ParameterizedTest
  @MethodSource("getTestData")
  public void testExecute(Filter filter, Order order, String expectedResult) {
    final var result = filter.execute(order);
    assertNotNull(result);
    assertEquals(expectedResult, result.trim());
  }

  @ParameterizedTest
  @MethodSource("getTestData")
  public void testNext(Filter filter) {
    assertNull(filter.getNext());
    assertSame(filter, filter.getLast());
  }

}
