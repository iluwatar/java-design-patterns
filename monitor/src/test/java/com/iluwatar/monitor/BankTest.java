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
package com.iluwatar.monitor;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class BankTest {

  private static final int ACCOUNT_NUM = 4;
  private static final int BASE_AMOUNT = 1000;
  private static Bank bank;

  @BeforeAll
  public static void Setup() {
    bank = new Bank(ACCOUNT_NUM, BASE_AMOUNT);
  }

  @AfterAll
  public static void TearDown() {
    bank = null;
  }

  @Test
  void GetAccountHaveNotBeNull() {
    assertNotNull(bank.getAccounts());
  }

  @Test
  void LengthOfAccountsHaveToEqualsToAccountNumConstant() {
    assumeTrue(bank.getAccounts() != null);
    assertEquals(ACCOUNT_NUM, bank.getAccounts().length);
  }

  @Test
  void TransferMethodHaveToTransferAmountFromAnAccountToOtherAccount() {
    bank.transfer(0, 1, 1000);
    int[] accounts = bank.getAccounts();
    assertEquals(0, accounts[0]);
    assertEquals(2000, accounts[1]);
  }

  @Test
  void BalanceHaveToBeOK() {
    assertEquals(4000, bank.getBalance());
  }

  @Test
  void ReturnBalanceWhenGivenAccountNumber() {
    bank.transfer(0, 1, 1000);
    assertEquals(0, bank.getBalance(0));
    assertEquals(2000, bank.getBalance(1));
  }
}
