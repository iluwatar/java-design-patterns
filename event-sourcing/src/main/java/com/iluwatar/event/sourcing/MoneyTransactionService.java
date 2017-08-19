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
package com.iluwatar.event.sourcing;

import com.iluwatar.event.sourcing.api.EventProcessor;
import com.iluwatar.event.sourcing.event.MoneyDepositEvent;
import com.iluwatar.event.sourcing.event.MoneyTransferEvent;
import com.iluwatar.event.sourcing.event.MoneyWithdrawalEvent;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Serdar Hamzaogullari on 06.08.2017.
 */
public class MoneyTransactionService {

  private EventProcessor eventProcessor;

  /**
   * Instantiates a new Money transaction service.
   *
   * @param eventProcessor the event processor
   */
  public MoneyTransactionService(EventProcessor eventProcessor) {
    this.eventProcessor = eventProcessor;
  }

  /**
   * Deposit money.
   *
   * @param accountNo the account no
   * @param money the money
   */
  public void depositMoney(int accountNo, BigDecimal money) {
    MoneyDepositEvent moneyDepositEvent = new MoneyDepositEvent(
        SequenceIdGenerator.nextSequenceId(), new Date().getTime(), accountNo, money);
    eventProcessor.process(moneyDepositEvent);
  }

  /**
   * Withdrawal money.
   *
   * @param accountNo the account no
   * @param money the money
   */
  public void withdrawalMoney(int accountNo, BigDecimal money) {
    MoneyWithdrawalEvent moneyWithdrawalEvent = new MoneyWithdrawalEvent(
        SequenceIdGenerator.nextSequenceId(), new Date().getTime(), accountNo, money);
    eventProcessor.process(moneyWithdrawalEvent);
  }

  /**
   * Transfer money.
   *
   * @param accountNoFrom the account no from
   * @param accountNoTo the account no to
   * @param money the money
   */
  public void transferMoney(int accountNoFrom, int accountNoTo, BigDecimal money) {
    MoneyTransferEvent moneyTransferEvent = new MoneyTransferEvent(
        SequenceIdGenerator.nextSequenceId(), new Date().getTime(), money, accountNoFrom,
        accountNoTo);
    eventProcessor.process(moneyTransferEvent);
  }
}
