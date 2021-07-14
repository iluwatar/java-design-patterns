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

class ForecastServerTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private BudgetMicrokernel microkernel;
  private List<Account> accounts;
  private final double income = 50000;
  private final double outgoing = 40000;

  @BeforeEach
  private void setUp() {
    System.setOut(new PrintStream(outContent));
    this.accounts = initMcirokernel();
    this.microkernel = mock(BudgetMicrokernel.class);
    Mockito.when(microkernel.getRecentYearsAccounts()).thenReturn(this.accounts);
  }

  private List<Account> initMcirokernel() {
    Account account1 = mock(Account.class);
    Account account2 = mock(Account.class);
    List<Account> accounts = new ArrayList<>();
    accounts.add(account1);
    accounts.add(account2);
    Mockito.when(account1.getAccountNumber()).thenReturn(AccountUtil.INCOME);
    Mockito.when(account1.getCurrentValue()).thenReturn(income);
    Mockito.when(account2.getAccountNumber()).thenReturn(AccountUtil.OUTGOING);
    Mockito.when(account2.getCurrentValue()).thenReturn(outgoing);
    return accounts;
  }

  @AfterEach
  private void restoreStreams() {
    System.setOut(System.out);
  }

  @Test
  void receiveRequest() {
    ForecastServer external = new ForecastServer(this.microkernel);
    String expectedOutput = "The next income forecast: " + income +"\r\nThe next outgoing forecast: " + outgoing +"\r\n";

    external.receiveRequest();

    Mockito.verify(microkernel, times(1)).getRecentYearsAccounts();
    assertEquals(expectedOutput, outContent.toString());
  }
}
