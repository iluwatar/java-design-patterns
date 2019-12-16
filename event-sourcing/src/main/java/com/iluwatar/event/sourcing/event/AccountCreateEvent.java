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

package com.iluwatar.event.sourcing.event;

import com.iluwatar.event.sourcing.domain.Account;
import com.iluwatar.event.sourcing.state.AccountAggregate;

/**
 * This is the class that implements account create event. Holds the necessary info for an account
 * create event. Implements the process function that finds the event related domain objects and
 * calls the related domain object's handle event functions
 *
 * <p>Created by Serdar Hamzaogullari on 06.08.2017.
 */
public class AccountCreateEvent extends DomainEvent {

  private final int accountNo;
  private final String owner;

  /**
   * Instantiates a new Account create event.
   *
   * @param sequenceId  the sequence id
   * @param createdTime the created time
   * @param accountNo   the account no
   * @param owner       the owner
   */
  public AccountCreateEvent(long sequenceId, long createdTime, int accountNo, String owner) {
    super(sequenceId, createdTime, "AccountCreateEvent");
    this.accountNo = accountNo;
    this.owner = owner;
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

  @Override
  public void process() {
    var account = AccountAggregate.getAccount(accountNo);
    if (account != null) {
      throw new RuntimeException("Account already exists");
    }
    account = new Account(accountNo, owner);
    account.handleEvent(this);
  }
}
