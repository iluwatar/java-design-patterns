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

package com.iluwatar.microkernel.internals;

import com.iluwatar.microkernel.models.Account;
import com.iluwatar.microkernel.models.FlowingType;
import com.iluwatar.microkernel.models.Transaction;
import com.iluwatar.microkernel.utils.AccountUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccounterServerTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private Transaction debitTransaction;
  private Transaction creditTransaction;
  private final LocalDate usedDate = LocalDate.now();
  private AccounterServer internal;

  @BeforeEach
  private void setUp() {
    System.setOut(new PrintStream(outContent));
    this.creditTransaction = new Transaction("transaction 1", usedDate, 2000, FlowingType.CREDIT);
    this.debitTransaction = new Transaction("transaction 2", usedDate, 1000, FlowingType.DEBIT);
    this.internal = new AccounterServer();
  }

  @AfterEach
  private void restoreStreams() {
    System.setOut(System.out);
  }

  @Test
  void receiveRequest_should_increases_outgoings() {
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(debitTransaction);
    Account account = new Account(transactions, AccountUtil.OUTGOING, AccountUtil.OUTGOING_NAME,
            2019, true, true, 1000);
    Transaction newTransaction = new Transaction("increase",usedDate,500,FlowingType.DEBIT);

    Account result = this.internal.receiveRequest(account,newTransaction);

    assertEquals(result.getCurrentValue(), 1500);
    assertEquals(result.getTransactions().size(), 2);
  }

  @Test
  void receiveRequest_should_decreases_outgoings() {
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(debitTransaction);
    Account account = new Account(transactions, AccountUtil.OUTGOING, AccountUtil.OUTGOING_NAME,
            2019, true, true, 1000);
    Transaction newTransaction = new Transaction("increase",usedDate,500,FlowingType.CREDIT);

    Account result = this.internal.receiveRequest(account,newTransaction);

    assertEquals(result.getCurrentValue(), 500);
    assertEquals(result.getTransactions().size(), 2);
  }

  @Test
  void receiveRequest_should_increases_incomes() {
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(creditTransaction);
    Account account = new Account(transactions, AccountUtil.INCOME, AccountUtil.INCOME_NAME,
            2019, true, false, 2000);
    Transaction newTransaction = new Transaction("increase",usedDate,1000,FlowingType.CREDIT);

    Account result = this.internal.receiveRequest(account,newTransaction);

    assertEquals(result.getCurrentValue(), 3000);
    assertEquals(result.getTransactions().size(), 2);
  }

  @Test
  void receiveRequest_should_decreases_incomes() {
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(creditTransaction);
    Account account = new Account(transactions, AccountUtil.INCOME, AccountUtil.INCOME_NAME,
            2019, true, false, 2000);
    Transaction newTransaction = new Transaction("increase",usedDate,1000,FlowingType.DEBIT);

    Account result = this.internal.receiveRequest(account,newTransaction);

    assertEquals(result.getCurrentValue(),1000);
    assertEquals(result.getTransactions().size(), 2);
  }
}
