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
package com.iluwatar.event.sourcing.service;

import com.iluwatar.event.sourcing.api.EventProcessor;
import com.iluwatar.event.sourcing.event.AccountCreateEvent;
import java.util.Date;

/**
 * Created by serdarh on 06.08.2017.
 */
public class AccountService {

  private EventProcessor eventProcessor;

  /**
   * Instantiates a new Account service.
   *
   * @param eventProcessor the event processor
   */
  public AccountService(EventProcessor eventProcessor) {
    this.eventProcessor = eventProcessor;
  }

  /**
   * Create account.
   *
   * @param accountNo the account no
   * @param owner the owner
   */
  public void createAccount(int accountNo, String owner) {
    AccountCreateEvent accountCreateEvent = new AccountCreateEvent(
        SequenceIdGenerator.nextSequenceId(), new Date().getTime(), accountNo, owner);
    eventProcessor.process(accountCreateEvent);
  }
}
