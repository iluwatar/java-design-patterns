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
package com.iluwatar.roleobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CustomerCoreTest {

  @Test
  void addRole() {
    var core = new CustomerCore();
    assertTrue(core.addRole(Role.BORROWER));
  }

  @Test
  void hasRole() {
    var core = new CustomerCore();
    core.addRole(Role.BORROWER);
    assertTrue(core.hasRole(Role.BORROWER));
    assertFalse(core.hasRole(Role.INVESTOR));
  }

  @Test
  void remRole() {
    var core = new CustomerCore();
    core.addRole(Role.BORROWER);

    var bRole = core.getRole(Role.BORROWER, BorrowerRole.class);
    assertTrue(bRole.isPresent());

    assertTrue(core.remRole(Role.BORROWER));

    var empt = core.getRole(Role.BORROWER, BorrowerRole.class);
    assertFalse(empt.isPresent());
  }

  @Test
  void getRole() {
    var core = new CustomerCore();
    core.addRole(Role.BORROWER);

    var bRole = core.getRole(Role.BORROWER, BorrowerRole.class);
    assertTrue(bRole.isPresent());

    var nonRole = core.getRole(Role.BORROWER, InvestorRole.class);
    assertFalse(nonRole.isPresent());

    var invRole = core.getRole(Role.INVESTOR, InvestorRole.class);
    assertFalse(invRole.isPresent());
  }

  @Test
  void toStringTest() {
    var core = new CustomerCore();
    core.addRole(Role.BORROWER);
    assertEquals("Customer{roles=[BORROWER]}", core.toString());

    core = new CustomerCore();
    core.addRole(Role.INVESTOR);
    assertEquals("Customer{roles=[INVESTOR]}", core.toString());

    core = new CustomerCore();
    assertEquals("Customer{roles=[]}", core.toString());
  }

}