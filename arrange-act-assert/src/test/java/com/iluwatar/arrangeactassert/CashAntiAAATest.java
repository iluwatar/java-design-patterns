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
package com.iluwatar.arrangeactassert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * ({@link CashAAATest}) is an anti-example of AAA pattern. This test is functionally correct, but
 * with the addition of a new feature, it needs refactoring. There are an awful lot of steps in that
 * test method, but it verifies the class' important behavior in just eleven lines. It violates the
 * single responsibility principle. If this test method failed after a small code change, it might
 * take some digging to discover why.
 */
class CashAntiAAATest {

  @Test
  void testCash() {
    // initialize
    var cash = new Cash(3);
    // test plus
    cash.plus(4);
    assertEquals(7, cash.count());
    // test minus
    cash = new Cash(8);
    assertTrue(cash.minus(5));
    assertEquals(3, cash.count());
    assertFalse(cash.minus(6));
    assertEquals(3, cash.count());
    // test update
    cash.plus(5);
    assertTrue(cash.minus(5));
    assertEquals(3, cash.count());
  }
}
