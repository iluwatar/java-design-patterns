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
import java.util.List;

/**
 * This class represents an internal server.
 * This class implements additional services,
 * encapsulates some system specifics -
 * helps the microkernel in accounting.
 */
public class AccounterServer {

  /**
   *  This method account the transaction on the account
   *  with the right changes.
   * @param account  as the related account
   * @param transaction as transaction needs to be accounted
   * @return account which contains the given transaction
   */
  private Account executeService(Account account, Transaction transaction) {
    List<Transaction> transactions = account.getTransactions();
    transactions.add(transaction);
    account.setTransactions(transactions);
    if (account.isActive()) {
      if (transaction.getFlowingtype().equals(FlowingType.DEBIT)) {
        account.setCurrentValue(account.getCurrentValue() + transaction.getValue());
      } else {
        account.setCurrentValue(account.getCurrentValue() - transaction.getValue());
      }
    } else {
      if (transaction.getFlowingtype().equals(FlowingType.DEBIT)) {
        account.setCurrentValue(account.getCurrentValue() - transaction.getValue());
      } else {
        account.setCurrentValue(account.getCurrentValue() + transaction.getValue());
      }
    }
    return account;
  }

  /**
   * This method receives the requested transaction
   * with the account and calls the method which accounts it.
   * @param account  as the related account
   * @param transaction as transaction needs to be accounted
   * @return account which contains the given transaction
   */
  public Account receiveRequest(Account account, Transaction transaction) {
    Account freshAccount = executeService(account, transaction);
    System.out.println("The transaction has been recorded!");
    System.out.println("The current value of the " + freshAccount.getName()
            + " is " + freshAccount.getCurrentValue());
    return freshAccount;
  }
}
