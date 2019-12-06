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

package com.iluwatar.event.sourcing.state;

import com.iluwatar.event.sourcing.domain.Account;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This is the static accounts map holder class. This class holds the state of the accounts.
 *
 * <p>Created by Serdar Hamzaogullari on 06.08.2017.
 */
public class AccountAggregate {

  private static Map<Integer, Account> accounts = new HashMap<>();

  private AccountAggregate() {
  }

  /**
   * Put account.
   *
   * @param account the account
   */
  public static void putAccount(Account account) {
    accounts.put(account.getAccountNo(), account);
  }

  /**
   * Gets account.
   *
   * @param accountNo the account no
   * @return the copy of the account or null if not found
   */
  public static Account getAccount(int accountNo) {
    return Optional.of(accountNo)
        .map(accounts::get)
        .map(Account::copy)
        .orElse(null);
  }

  /**
   * Reset state.
   */
  public static void resetState() {
    accounts = new HashMap<>();
  }
}
