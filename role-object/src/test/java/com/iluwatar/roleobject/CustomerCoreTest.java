/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class CustomerCoreTest {

    @Test
    public void addRole() {
        CustomerCore core = new CustomerCore();
        boolean add = core.addRole(Role.Borrower);
        assertTrue(add);

    }

    @Test
    public void hasRole() {
        CustomerCore core = new CustomerCore();
        core.addRole(Role.Borrower);

        boolean has = core.hasRole(Role.Borrower);
        assertTrue(has);

        boolean notHas = core.hasRole(Role.Investor);
        assertFalse(notHas);
    }

    @Test
    public void remRole() {
        CustomerCore core = new CustomerCore();
        core.addRole(Role.Borrower);

        Optional<BorrowerRole> bRole = core.getRole(Role.Borrower, BorrowerRole.class);
        assertTrue(bRole.isPresent());

        boolean res = core.remRole(Role.Borrower);
        assertTrue(res);

        Optional<BorrowerRole> empt = core.getRole(Role.Borrower, BorrowerRole.class);
        assertFalse(empt.isPresent());

    }

    @Test
    public void getRole() {
        CustomerCore core = new CustomerCore();
        core.addRole(Role.Borrower);

        Optional<BorrowerRole> bRole = core.getRole(Role.Borrower, BorrowerRole.class);
        assertTrue(bRole.isPresent());

        Optional<InvestorRole> nonRole = core.getRole(Role.Borrower, InvestorRole.class);
        assertFalse(nonRole.isPresent());

        Optional<InvestorRole> invRole = core.getRole(Role.Investor, InvestorRole.class);
        assertFalse(invRole.isPresent());


    }


    @Test
    public void toStringTest() {
        CustomerCore core = new CustomerCore();
        core.addRole(Role.Borrower);
        assertEquals(core.toString(), "Customer{roles=[Borrower]}");

        core = new CustomerCore();
        core.addRole(Role.Investor);
        assertEquals(core.toString(), "Customer{roles=[Investor]}");

        core = new CustomerCore();
        assertEquals(core.toString(), "Customer{roles=[]}");


    }

}