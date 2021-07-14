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
import com.iluwatar.microkernel.internals.AccounterServer;
import com.iluwatar.microkernel.models.Account;
import com.iluwatar.microkernel.models.Transaction;
import com.iluwatar.microkernel.utils.AccountUtil;
import com.iluwatar.microkernel.utils.RequestUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class is the microkernel of the pattern.
 * This class provides core mechanisms,
 * offers communication facilities,
 * encapsulates system dependencies, manages and
 * controls resources - in this way: accounts
 */
public class BudgetMicrokernel {

  private Map<Integer, Account> accounts;
  private int accountCounter;
  private AccounterServer internalServer;

  /**
   * Use this constructor to create an BudgetMicrokernel with all details.
   * @param accounts as all registered accounts
   */
  public BudgetMicrokernel(Map<Integer, Account> accounts) {
    this.accounts = Objects.requireNonNullElseGet(accounts, HashMap::new);
    accountCounter = this.accounts.size();
    this.internalServer = new AccounterServer();
  }

  /**
   * This method is used when the external server sends
   * a service request to the microkernel.
   * @param transaction  as transaction needs to be accounted
   */
  public void executeMechanism(Transaction transaction) {
    callInternalServer(transaction);
  }

  /**
   * This method is used by adapter.
   * The adapter constructs a request and asks the microkernel
   * for acommunication link with the external server.
   * @param request  as request to be executed
   * @return
   */
  public ResultProcessorServer initCommunication(String request) {
    return this.findReceiver(request);
  }

  /**
   * This method decides the right external server for the request,
   * returns the common interface.
   * @param request  as request to be executed
   * @return interface which the external servers implement
   */
  private ResultProcessorServer findReceiver(String request) {
    if (request.equals(RequestUtil.RESULT)) {
      return new ResultReportServer(this);
    } else if (request.equals(RequestUtil.FORECAST)) {
      return new ForecastServer(this);
    } else {
      createHandle();
      return null;
    }
  }

  private void createHandle() {
    sendMessage();
  }

  private void sendMessage() {
    System.out.println("This service is currently unavailable or does not exist!");
  }

  /**
   * This method calls the internal server to save the
   * transaction on the right account.
   * If the account does not exists, it will create a new one.
   * @param transaction  as transaction needs to be accounted
   */
  private void callInternalServer(Transaction transaction) {
    List<Account> relatedAccount = this.accounts.values().stream()
            .filter(account -> account.getAccountNumber() == transaction.getRelatedAccountNumber()
                    && !account.isClosed())
            .collect(Collectors.toList());
    if (relatedAccount.isEmpty()) {
      if (transaction.getRelatedAccountNumber() == AccountUtil.TAX) {
        Account taxAccount = new Account(new ArrayList<>(), AccountUtil.TAX, AccountUtil.TAX_NAME,
                transaction.getDate().getYear(), false, false, 0);
        Account account = this.internalServer.receiveRequest(taxAccount, transaction);
        this.accounts.put(this.accountCounter++, account);
      }
    } else {
      Account account = this.internalServer.receiveRequest(relatedAccount.get(0), transaction);
      this.accounts.put(getAccountKey(account), account);

    }
  }

  private Integer getAccountKey(Account account) {
    for (Map.Entry<Integer, Account> entry : this.accounts.entrySet()) {
      Account currentAccount = entry.getValue();
      if (currentAccount.getYear() == account.getYear()
              && currentAccount.getAccountNumber() == account.getAccountNumber()) {
        return entry.getKey();
      }
    }
    return 0;
  }

  /**
   * Save the given transaction on the right account.
   * @param account   as related account
   * @param transaction as transaction needs to be accounted
   */
  public void makeTransaction(Account account, Transaction transaction) {
    this.internalServer.receiveRequest(account, transaction);
  }

  /**
   * Returns the closed accounts.
   * @return list of closed accounts
   */
  public List<Account> getRecentYearsAccounts() {
    return this.accounts.values().stream()
            .filter(Account::isClosed)
            .collect(Collectors.toList());

  }

  /**
   * Returns unclosed accounts.
   * @return  list of accounts which have not been closed yet
   */
  public List<Account> getCurrentAccounts() {
    return this.accounts.values().stream()
            .filter(account -> !account.isClosed())
            .collect(Collectors.toList());
  }

}
