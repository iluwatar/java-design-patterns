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
/*
*The MIT License
*Copyright © 2014-2021 Ilkka Seppälä
*
*Permission is hereby granted, free of charge, to any person obtaining a copy
*of this software and associated documentation files (the "Software"), to deal
*in the Software without restriction, including without limitation the rights
*to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
*copies of the Software, and to permit persons to whom the Software is
*furnished to do so, subject to the following conditions:
*
*The above copyright notice and this permission notice shall be included in
*all copies or substantial portions of the Software.
*
*THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
*IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
*FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
*AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
*LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
*OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
*THE SOFTWARE.
*/

package com.iluwatar.monitor;

import java.util.Arrays;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/** Bank Definition. */
@Slf4j
public class Bank {

  @Getter
  private final int[] accounts;

  /**
   * Constructor.
   *
   * @param accountNum - account number
   * @param baseAmount - base amount
   */
  public Bank(int accountNum, int baseAmount) {
    accounts = new int[accountNum];
    Arrays.fill(accounts, baseAmount);
  }

  /**
   * Transfer amounts from one account to another.
   *
   * @param accountA - source account
   * @param accountB - destination account
   * @param amount   - amount to be transferred
   */
  public synchronized void transfer(int accountA, int accountB, int amount) {
    if (accounts[accountA] >= amount && accountA != accountB) {
      accounts[accountB] += amount;
      accounts[accountA] -= amount;
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug(
            "Transferred from account: {} to account: {} , amount: {} , bank balance at: {}, source account balance: {}, destination account balance: {}",
            accountA,
            accountB,
            amount,
            getBalance(),
            getBalance(accountA),
            getBalance(accountB));
      }
    }
  }

  /**
   * Calculates the total balance.
   *
   * @return balance
   */
  public synchronized int getBalance() {
    int balance = 0;
    for (int account : accounts) {
      balance += account;
    }
    return balance;
  }

  /**
   * Get the accountNumber balance.
   *
   * @param accountNumber - accountNumber number
   * @return accounts[accountNumber]
   */
  public synchronized int getBalance(int accountNumber) {
    return accounts[accountNumber];
  }
}
