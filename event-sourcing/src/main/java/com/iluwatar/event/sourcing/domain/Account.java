/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.event.sourcing.domain;

import com.iluwatar.event.sourcing.event.AccountCreateEvent;
import com.iluwatar.event.sourcing.event.MoneyDepositEvent;
import com.iluwatar.event.sourcing.event.MoneyTransferEvent;
import com.iluwatar.event.sourcing.event.MoneyWithdrawalEvent;
import com.iluwatar.event.sourcing.gateway.Gateways;
import com.iluwatar.event.sourcing.state.AccountAggregate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serdar Hamzaogullari on 06.08.2017.
 */
public class Account {

  private final int accountNo;
  private final String owner;
  private BigDecimal money;
  private List<Transaction> transactions;

  /**
   * Instantiates a new Account.
   *
   * @param accountNo the account no
   * @param owner the owner
   */
  public Account(int accountNo, String owner) {
    this.accountNo = accountNo;
    this.owner = owner;
    money = BigDecimal.ZERO;
    transactions = new ArrayList<>();
  }

  /**
   * Gets account no.
   *
   * @return the account no
   */
  public int getAccountNo() {
    return accountNo;
  }

  /**
   * Gets owner.
   *
   * @return the owner
   */
  public String getOwner() {
    return owner;
  }

  /**
   * Gets money.
   *
   * @return the money
   */
  public BigDecimal getMoney() {
    return money;
  }

  /**
   * Sets money.
   *
   * @param money the money
   */
  public void setMoney(BigDecimal money) {
    this.money = money;
  }

  /**
   * Gets transactions.
   *
   * @return the transactions
   */
  public List<Transaction> getTransactions() {
    return transactions;
  }

  /**
   * Sets transactions.
   *
   * @param transactions the transactions
   */
  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  /**
   * Copy account.
   *
   * @return the account
   */
  public Account copy() {
    Account account = new Account(accountNo, owner);
    account.setMoney(money);
    account.setTransactions(transactions);
    return account;
  }

  @Override
  public String toString() {
    return "Account{"
        + "accountNo=" + accountNo
        + ", owner='" + owner + '\''
        + ", money=" + money
        + ", transactions=" + transactions
        + '}';
  }

  private Transaction depositMoney(BigDecimal money) {
    this.money = this.money.add(money);
    Transaction transaction = new Transaction(accountNo, money, BigDecimal.ZERO, this.money);
    transactions.add(transaction);
    return transaction;
  }

  private Transaction withdrawMoney(BigDecimal money) {
    this.money = this.money.subtract(money);
    Transaction transaction = new Transaction(accountNo, BigDecimal.ZERO, money, this.money);
    transactions.add(transaction);
    return transaction;
  }

  private void handleDeposit(BigDecimal money, boolean realTime) {
    Transaction transaction = depositMoney(money);
    AccountAggregate.putAccount(this);
    if (realTime) {
      Gateways.getTransactionLogger().log(transaction);
    }
  }

  private void handleWithdrawal(BigDecimal money, boolean realTime) {
    if (this.money.compareTo(money) == -1) {
      throw new RuntimeException("Insufficient Account Balance");
    }

    Transaction transaction = withdrawMoney(money);
    AccountAggregate.putAccount(this);
    if (realTime) {
      Gateways.getTransactionLogger().log(transaction);
    }
  }

  /**
   * Handle event.
   *
   * @param moneyDepositEvent the money deposit event
   */
  public void handleEvent(MoneyDepositEvent moneyDepositEvent) {
    handleDeposit(moneyDepositEvent.getMoney(), moneyDepositEvent.isRealTime());
  }


  /**
   * Handle event.
   *
   * @param moneyWithdrawalEvent the money withdrawal event
   */
  public void handleEvent(MoneyWithdrawalEvent moneyWithdrawalEvent) {
    handleWithdrawal(moneyWithdrawalEvent.getMoney(), moneyWithdrawalEvent.isRealTime());
  }

  /**
   * Handle event.
   *
   * @param accountCreateEvent the account create event
   */
  public void handleEvent(AccountCreateEvent accountCreateEvent) {
    AccountAggregate.putAccount(this);
    // check if this event is replicated from journal before calling an external gateway function
    if (accountCreateEvent.isRealTime()) {
      Gateways.getAccountCreateContractSender().sendContractInfo(this);
    }
  }

  /**
   * Handle transfer from event.
   *
   * @param moneyTransferEvent the money transfer event
   */
  public void handleTransferFromEvent(MoneyTransferEvent moneyTransferEvent) {
    handleWithdrawal(moneyTransferEvent.getMoney(), moneyTransferEvent.isRealTime());
  }

  /**
   * Handle transfer to event.
   *
   * @param moneyTransferEvent the money transfer event
   */
  public void handleTransferToEvent(MoneyTransferEvent moneyTransferEvent) {
    handleDeposit(moneyTransferEvent.getMoney(), moneyTransferEvent.isRealTime());
  }


}
