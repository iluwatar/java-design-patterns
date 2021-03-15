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

package com.iluwatar.roleobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CustomerCoreTest {

  @Test
  void addRole() {
    var core = new CustomerCore();
    assertTrue(core.addRole(Role.Borrower));
  }

  @Test
  void hasRole() {
    var core = new CustomerCore();
    core.addRole(Role.Borrower);
    assertTrue(core.hasRole(Role.Borrower));
    assertFalse(core.hasRole(Role.Investor));
  }

  @Test
  void remRole() {
    var core = new CustomerCore();
    core.addRole(Role.Borrower);

    var bRole = core.getRole(Role.Borrower, BorrowerRole.class);
    assertTrue(bRole.isPresent());

    assertTrue(core.remRole(Role.Borrower));

    var empt = core.getRole(Role.Borrower, BorrowerRole.class);
    assertFalse(empt.isPresent());
  }

  @Test
  void getRole() {
    var core = new CustomerCore();
    core.addRole(Role.Borrower);

    var bRole = core.getRole(Role.Borrower, BorrowerRole.class);
    assertTrue(bRole.isPresent());

    var nonRole = core.getRole(Role.Borrower, InvestorRole.class);
    assertFalse(nonRole.isPresent());

    var invRole = core.getRole(Role.Investor, InvestorRole.class);
    assertFalse(invRole.isPresent());
  }

  @Test
  void toStringTest() {
    var core = new CustomerCore();
    core.addRole(Role.Borrower);
    assertEquals("Customer{roles=[Borrower]}", core.toString());

    core = new CustomerCore();
    core.addRole(Role.Investor);
    assertEquals("Customer{roles=[Investor]}", core.toString());

    core = new CustomerCore();
    assertEquals("Customer{roles=[]}", core.toString());
  }

}