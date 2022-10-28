/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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
import com.iluwatar.event.sourcing.state.AccountAggregate;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * This is the Account class that holds the account info, the account number, account owner name and
 * money of the account. Account class also have the business logic of events that effects this
 * account.
 *
 * <p>Created by Serdar Hamzaogullari on 06.08.2017.
 */
@Setter
@Getter
@RequiredArgsConstructor
@Slf4j
public class Account {

  private final int accountNo;
  private final String owner;
  private BigDecimal money = BigDecimal.ZERO;

  private static final String MSG =
      "Some external api for only realtime execution could be called here.";

  /**
   * Copy account.
   *
   * @return the account
   */
  public Account copy() {
    var account = new Account(accountNo, owner);
    account.setMoney(money);
    return account;
  }

  @Override
  public String toString() {
    return "Account{"
        + "accountNo=" + accountNo
        + ", owner='" + owner + '\''
        + ", money=" + money
        + '}';
  }

  private void depositMoney(BigDecimal money) {
    this.money = this.money.add(money);
  }

  private void withdrawMoney(BigDecimal money) {
    this.money = this.money.subtract(money);
  }

  private void handleDeposit(BigDecimal money, boolean realTime) {
    depositMoney(money);
    AccountAggregate.putAccount(this);
    if (realTime) {
      LOGGER.info(MSG);
    }
  }

  private void handleWithdrawal(BigDecimal money, boolean realTime) {
    if (this.money.compareTo(money) < 0) {
      throw new RuntimeException("Insufficient Account Balance");
    }

    withdrawMoney(money);
    AccountAggregate.putAccount(this);
    if (realTime) {
      LOGGER.info(MSG);
    }
  }

  /**
   * Handles the MoneyDepositEvent.
   *
   * @param moneyDepositEvent the money deposit event
   */
  public void handleEvent(MoneyDepositEvent moneyDepositEvent) {
    handleDeposit(moneyDepositEvent.getMoney(), moneyDepositEvent.isRealTime());
  }


  /**
   * Handles the AccountCreateEvent.
   *
   * @param accountCreateEvent the account create event
   */
  public void handleEvent(AccountCreateEvent accountCreateEvent) {
    AccountAggregate.putAccount(this);
    if (accountCreateEvent.isRealTime()) {
      LOGGER.info(MSG);
    }
  }

  /**
   * Handles transfer from account event.
   *
   * @param moneyTransferEvent the money transfer event
   */
  public void handleTransferFromEvent(MoneyTransferEvent moneyTransferEvent) {
    handleWithdrawal(moneyTransferEvent.getMoney(), moneyTransferEvent.isRealTime());
  }

  /**
   * Handles transfer to account event.
   *
   * @param moneyTransferEvent the money transfer event
   */
  public void handleTransferToEvent(MoneyTransferEvent moneyTransferEvent) {
    handleDeposit(moneyTransferEvent.getMoney(), moneyTransferEvent.isRealTime());
  }


}
