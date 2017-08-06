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

    public static void main(String[] args) {
        System.out.println("Running the system first time............");

        DomainEventProcessor domainEventProcessor = new DomainEventProcessor();
        JsonFileJournal jsonFileJournal = new JsonFileJournal();
        jsonFileJournal.reset();
        domainEventProcessor.setPrecessorJournal(jsonFileJournal);

        System.out.println("Creating th accounts............");

        AccountService accountService = new AccountService(domainEventProcessor);
        MoneyTransactionService moneyTransactionService = new MoneyTransactionService(domainEventProcessor);
        accountService.createAccount(1,"Daenerys Targaryen");
        accountService.createAccount(2,"Jon Snow");

        System.out.println("Do some money operations............");

        moneyTransactionService.depositMoney(1,new BigDecimal("100000"));
        moneyTransactionService.depositMoney(2,new BigDecimal("10"));

        moneyTransactionService.transferMoney(1,2,new BigDecimal("10000"));
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
