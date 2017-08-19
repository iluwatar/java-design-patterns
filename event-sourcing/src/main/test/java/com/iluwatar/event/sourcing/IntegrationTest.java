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

import static com.iluwatar.event.sourcing.app.App.ACCOUNT_OF_DAENERYS;
import static com.iluwatar.event.sourcing.app.App.ACCOUNT_OF_JON;

import com.iluwatar.event.sourcing.domain.Account;
import com.iluwatar.event.sourcing.journal.JsonFileJournal;
import com.iluwatar.event.sourcing.processor.DomainEventProcessor;
import com.iluwatar.event.sourcing.state.AccountAggregate;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Intergartion Test for Event Sourcing state recovery
 *
 * Created by Serdar Hamzaogullari on 19.08.2017.
 */
public class IntegrationTest {

  /**
   * The Domain event processor.
   */
  DomainEventProcessor domainEventProcessor;
  /**
   * The Json file journal.
   */
  JsonFileJournal jsonFileJournal;
  /**
   * The Account service.
   */
  AccountService accountService;
  /**
   * The Money transaction service.
   */
  MoneyTransactionService moneyTransactionService;

  /**
   * Initialize.
   */
  @Before
  public void initialize() {
    domainEventProcessor = new DomainEventProcessor();
    jsonFileJournal = new JsonFileJournal();
    domainEventProcessor.setPrecessorJournal(jsonFileJournal);
    accountService = new AccountService(domainEventProcessor);
    moneyTransactionService = new MoneyTransactionService(
        domainEventProcessor);
  }

  /**
   * Test state recovery.
   */
  @Test
  public void testStateRecovery() {
    jsonFileJournal.reset();

    accountService.createAccount(ACCOUNT_OF_DAENERYS, "Daenerys Targaryen");
    accountService.createAccount(ACCOUNT_OF_JON, "Jon Snow");

    moneyTransactionService.depositMoney(ACCOUNT_OF_DAENERYS, new BigDecimal("100000"));
    moneyTransactionService.depositMoney(ACCOUNT_OF_JON, new BigDecimal("100"));

    moneyTransactionService
        .transferMoney(ACCOUNT_OF_DAENERYS, ACCOUNT_OF_JON, new BigDecimal("10000"));
    moneyTransactionService.withdrawalMoney(ACCOUNT_OF_JON, new BigDecimal("1000"));

    Account accountOfDaenerysBeforeShotDown = AccountAggregate.getAccount(ACCOUNT_OF_DAENERYS);
    Account accountOfJonBeforeShotDown = AccountAggregate.getAccount(ACCOUNT_OF_JON);

    AccountAggregate.resetState();

    domainEventProcessor = new DomainEventProcessor();
    jsonFileJournal = new JsonFileJournal();
    domainEventProcessor.setPrecessorJournal(jsonFileJournal);
    domainEventProcessor.recover();

    Account accountOfDaenerysAfterShotDown = AccountAggregate.getAccount(ACCOUNT_OF_DAENERYS);
    Account accountOfJonAfterShotDown = AccountAggregate.getAccount(ACCOUNT_OF_JON);

    Assert.assertEquals(accountOfDaenerysBeforeShotDown.getMoney(),
        accountOfDaenerysAfterShotDown.getMoney());
    Assert
        .assertEquals(accountOfJonBeforeShotDown.getMoney(), accountOfJonAfterShotDown.getMoney());
    Assert.assertEquals(accountOfDaenerysBeforeShotDown.getTransactions().size(),
        accountOfDaenerysAfterShotDown.getTransactions().size());
    Assert.assertEquals(accountOfJonBeforeShotDown.getTransactions().size(),
        accountOfJonAfterShotDown.getTransactions().size());

  }

}