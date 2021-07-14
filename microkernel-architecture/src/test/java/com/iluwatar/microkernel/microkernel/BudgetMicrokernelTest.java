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

package com.iluwatar.microkernel.microkernel;

import com.iluwatar.microkernel.externals.ForecastServer;
import com.iluwatar.microkernel.externals.ResultProcessorServer;
import com.iluwatar.microkernel.externals.ResultReportServer;
import com.iluwatar.microkernel.models.Account;
import com.iluwatar.microkernel.utils.AccountUtil;
import com.iluwatar.microkernel.utils.RequestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BudgetMicrokernelTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private BudgetMicrokernel microkernel;
    private Account testAccount;

    @BeforeEach
    private void setUp(){
        System.setOut(new PrintStream(outContent));
        this.testAccount = new Account(new ArrayList<>(), AccountUtil.INCOME, AccountUtil.INCOME_NAME,
                2017, true, false, 100000);
        this.microkernel = new BudgetMicrokernel(initBudgetAccounts());
    }

    @AfterEach
    private void restoreStreams() {
        System.setOut(System.out);
    }

    private Map<Integer, Account> initBudgetAccounts() {
        Map<Integer, Account> accounts = new HashMap<>();
        accounts.put(0, testAccount);
        accounts.put(1, new Account(new ArrayList<>(), AccountUtil.OUTGOING, AccountUtil.OUTGOING_NAME,
                2018, false, true, 80000));
        accounts.put(2, new Account(new ArrayList<>(), AccountUtil.INCOME, AccountUtil.INCOME_NAME,
                2019, false, false, 150000));
        return accounts;
    }

    @Test
    void getRecentYearsAccounts() {
        List<Account> result = this.microkernel.getRecentYearsAccounts();
        Account account = result.get(0);

        assertEquals(result.size(), 1);
        assertTrue(account.isClosed());
        assertEquals(account.getCurrentValue(), 100000);
        assertEquals(account.getAccountNumber(), AccountUtil.INCOME);
    }

    @Test
    void getCurrentAccounts() {
        List<Account> result = this.microkernel.getCurrentAccounts();

        assertEquals(result.size(), 2);
        assertFalse(result.get(0).isClosed());
        assertFalse(result.get(1).isClosed());
    }

    @Test
    void getCurrentAccounts_should_returns_empty_list() {
        BudgetMicrokernel microkernel = new BudgetMicrokernel(null);
        List<Account> result = microkernel.getCurrentAccounts();

        assertEquals(result.size(), 0);
    }

    @Test
    void initCommunication_should_return_a_ForecastServer() {
        ResultProcessorServer external = this.microkernel.initCommunication(RequestUtil.FORECAST);

        assertTrue(external instanceof ForecastServer);
    }

    @Test
    void initCommunication_should_return_a_ResultReportServer() {
        ResultProcessorServer external = this.microkernel.initCommunication(RequestUtil.RESULT);

        assertTrue(external instanceof ResultReportServer);
    }

    @Test
    void initCommunication_should_return_null() {
        ResultProcessorServer external = this.microkernel.initCommunication("bad request");

        assertEquals(external, null);
    }
}
