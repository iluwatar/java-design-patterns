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

package com.iluwatar.microkernel.externals;

import com.iluwatar.microkernel.microkernel.BudgetMicrokernel;
import com.iluwatar.microkernel.models.Account;
import com.iluwatar.microkernel.models.Transaction;
import com.iluwatar.microkernel.utils.AccountUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class ResultReportServerTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private BudgetMicrokernel microkernel;
  private List<Account> accounts;
  private final double income = 100000;
  private final double outgoing = 80000;
  private final int year = 2019;

  @BeforeEach
  private void setUp() {
    System.setOut(new PrintStream(outContent));

    this.accounts = initMcirokernel();
    this.microkernel = mock(BudgetMicrokernel.class);
    Mockito.when(microkernel.getCurrentAccounts()).thenReturn(this.accounts);
    Mockito.doNothing().when(microkernel).executeMechanism(Mockito.any(Transaction.class));
  }

  private List<Account> initMcirokernel() {
    List<Account> accounts = new ArrayList<>();
    accounts.add(0, new Account(new ArrayList<>(), AccountUtil.OUTGOING, AccountUtil.OUTGOING_NAME,
            year, true, true, outgoing));
    accounts.add(1, new Account(new ArrayList<>(), AccountUtil.INCOME, AccountUtil.INCOME_NAME,
            year, true, false, income));
    return accounts;
  }

  @AfterEach
  private void restoreStreams() {
    System.setOut(System.out);
  }

  @Test
  void receiveRequest() {
    double profitTax = 15;
    double untaxedProfit = this.income - this.outgoing;
    double tax = untaxedProfit * profitTax /100;
    ResultReportServer external = new ResultReportServer(this.microkernel);
    String expectedResult = "In " + year + " the untaxed profit is " + untaxedProfit +"\r\nThe tax is " + profitTax + "%.\r\n"
            +"Debt to the state in the form of tax: " + tax + ".\r\n";

    external.receiveRequest();

    Mockito.verify(microkernel, times(1)).getCurrentAccounts();
    Mockito.verify(microkernel, times(1)).executeMechanism(Mockito.any(Transaction.class));
    assertEquals(expectedResult, outContent.toString());


  }
}
