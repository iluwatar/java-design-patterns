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
import com.iluwatar.event.sourcing.service.AccountService;
import com.iluwatar.event.sourcing.service.MoneyTransactionService;
import com.iluwatar.event.sourcing.state.AccountAggregate;
import java.math.BigDecimal;

/**
 * Created by serdarh on 06.08.2017.
 */
public class App {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    System.out.println("Running the system first time............");

    DomainEventProcessor domainEventProcessor = new DomainEventProcessor();
    JsonFileJournal jsonFileJournal = new JsonFileJournal();
    jsonFileJournal.reset();
    domainEventProcessor.setPrecessorJournal(jsonFileJournal);

    System.out.println("Creating th accounts............");

    AccountService accountService = new AccountService(domainEventProcessor);
    MoneyTransactionService moneyTransactionService = new MoneyTransactionService(
        domainEventProcessor);
    accountService.createAccount(1, "Daenerys Targaryen");
    accountService.createAccount(2, "Jon Snow");

    System.out.println("Do some money operations............");

    moneyTransactionService.depositMoney(1, new BigDecimal("100000"));
    moneyTransactionService.depositMoney(2, new BigDecimal("100"));

    moneyTransactionService.transferMoney(1, 2, new BigDecimal("10000"));
    moneyTransactionService.withdrawalMoney(2, new BigDecimal("1000"));

    System.out.println("...............State:............");
    System.out.println(AccountAggregate.getAccount(1));
    System.out.println(AccountAggregate.getAccount(2));

    System.out.println("At that point system goes down state in memory cleared............");

    AccountAggregate.resetState();

    System.out.println("Recover the syste by the events in journal file............");

    domainEventProcessor = new DomainEventProcessor();
    jsonFileJournal = new JsonFileJournal();
    domainEventProcessor.setPrecessorJournal(jsonFileJournal);
    domainEventProcessor.recover();

    System.out.println("...............State Recovered:............");
    System.out.println(AccountAggregate.getAccount(1));
    System.out.println(AccountAggregate.getAccount(2));
  }
}
