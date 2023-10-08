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
package com.iluwatar.event.sourcing.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iluwatar.event.sourcing.state.AccountAggregate;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.Getter;

/**
 * This is the class that implements money deposit event. Holds the necessary info for a money
 * deposit event. Implements the process function that finds the event-related domain objects and
 * calls the related domain object's handle event functions
 *
 * <p>Created by Serdar Hamzaogullari on 06.08.2017.
 */
@Getter
public class MoneyDepositEvent extends DomainEvent {

  private final BigDecimal money;
  private final int accountNo;

  /**
   * Instantiates a new Money deposit event.
   *
   * @param sequenceId  the sequence id
   * @param createdTime the created time
   * @param accountNo   the account no
   * @param money       the money
   */
  @JsonCreator
  public MoneyDepositEvent(@JsonProperty("sequenceId") long sequenceId,
      @JsonProperty("createdTime") long createdTime,
      @JsonProperty("accountNo") int accountNo, @JsonProperty("money") BigDecimal money) {
    super(sequenceId, createdTime, "MoneyDepositEvent");
    this.money = money;
    this.accountNo = accountNo;
  }

  @Override
  public void process() {
    var account = Optional.ofNullable(AccountAggregate.getAccount(accountNo))
        .orElseThrow(() -> new RuntimeException("Account not found"));
    account.handleEvent(this);
  }
}
