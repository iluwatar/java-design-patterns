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
package com.iluwatar.event.sourcing.event;

import com.iluwatar.event.sourcing.api.DomainEvent;
import com.iluwatar.event.sourcing.domain.Account;
import com.iluwatar.event.sourcing.state.AccountAggregate;
import java.math.BigDecimal;

/**
 * Created by serdarh on 06.08.2017.
 */
public class MoneyDepositEvent extends DomainEvent {

  private BigDecimal money;
  private int accountNo;

  /**
   * Instantiates a new Money deposit event.
   *
   * @param sequenceId the sequence id
   * @param createdTime the created time
   * @param accountNo the account no
   * @param money the money
   */
  public MoneyDepositEvent(long sequenceId, long createdTime, int accountNo, BigDecimal money) {
    super(sequenceId, createdTime, "MoneyDepositEvent");
    this.money = money;
    this.accountNo = accountNo;
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
   * Gets account no.
   *
   * @return the account no
   */
  public int getAccountNo() {
    return accountNo;
  }

  @Override
  public void process() {
    Account account = AccountAggregate.getAccount(accountNo);
    if (account == null) {
      throw new RuntimeException("Account not found");
    }
    account.handleEvent(this);
  }
}
