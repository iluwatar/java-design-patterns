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
package com.iluwatar.event.sourcing.app;

import com.iluwatar.event.sourcing.journal.JsonFileJournal;
import com.iluwatar.event.sourcing.processor.DomainEventProcessor;
import com.iluwatar.event.sourcing.AccountService;
import com.iluwatar.event.sourcing.MoneyTransactionService;
import com.iluwatar.event.sourcing.state.AccountAggregate;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Event Sourcing : Instead of storing just the current state of the data in a domain, use an
 * append-only store to record the full series of actions taken on that data. The store acts as the
 * system of record and can be used to materialize the domain objects. This can simplify tasks in
 * complex domains, by avoiding the need to synchronize the data model and the business domain,
 * while improving performance, scalability, and responsiveness. It can also provide consistency for
 * transactional data, and maintain full audit trails and history that can enable compensating
 * actions.
 *
 * This App class is an example usage of Event Sourcing pattern. As an example, two bank account is
 * created, then some money deposit and transfer actions are taken so a new state of accounts is
 * created. At that point, state is cleared in order to represent a system shot down. After the shot
 * down, system state is recovered by re-creating the past events from event journal. Then state is
 * printed so a user can view the last state is same with the state before system shot down.
 *
 * Created by Serdar Hamzaogullari on 06.08.2017.
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
  public static final int ACCOUNT_OF_DAENERYS = 1;
  public static final int ACCOUNT_OF_JON = 2;

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {

    DomainEventProcessor domainEventProcessor = new DomainEventProcessor();
    JsonFileJournal jsonFileJournal = new JsonFileJournal();
    domainEventProcessor.setPrecessorJournal(jsonFileJournal);
    AccountService accountService = new AccountService(domainEventProcessor);
    MoneyTransactionService moneyTransactionService = new MoneyTransactionService(
        domainEventProcessor);

    LOGGER.info("Running the system first time............");
    jsonFileJournal.reset();

    LOGGER.info("Creating th accounts............");

    accountService.createAccount(ACCOUNT_OF_DAENERYS, "Daenerys Targaryen");
    accountService.createAccount(ACCOUNT_OF_JON, "Jon Snow");

    LOGGER.info("Do some money operations............");

    moneyTransactionService.depositMoney(ACCOUNT_OF_DAENERYS, new BigDecimal("100000"));
    moneyTransactionService.depositMoney(ACCOUNT_OF_JON, new BigDecimal("100"));

    moneyTransactionService.transferMoney(ACCOUNT_OF_DAENERYS, ACCOUNT_OF_JON, new BigDecimal("10000"));
    moneyTransactionService.withdrawalMoney(ACCOUNT_OF_JON, new BigDecimal("1000"));

    LOGGER.info("...............State:............");
    LOGGER.info(AccountAggregate.getAccount(ACCOUNT_OF_DAENERYS).toString());
    LOGGER.info(AccountAggregate.getAccount(ACCOUNT_OF_JON).toString());

    LOGGER.info("At that point system had a shot down, state in memory is cleared............");
    AccountAggregate.resetState();

    LOGGER.info("Recover the system by the events in journal file............");

    domainEventProcessor = new DomainEventProcessor();
    jsonFileJournal = new JsonFileJournal();
    domainEventProcessor.setPrecessorJournal(jsonFileJournal);
    domainEventProcessor.recover();

    LOGGER.info("...............Recovered State:............");
    LOGGER.info(AccountAggregate.getAccount(ACCOUNT_OF_DAENERYS).toString());
    LOGGER.info(AccountAggregate.getAccount(ACCOUNT_OF_JON).toString());
  }
}
