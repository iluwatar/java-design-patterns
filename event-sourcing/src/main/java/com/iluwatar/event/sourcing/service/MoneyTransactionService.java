package com.iluwatar.event.sourcing.service;

import com.iluwatar.event.sourcing.api.EventProcessor;
import com.iluwatar.event.sourcing.event.MoneyDepositEvent;
import com.iluwatar.event.sourcing.event.MoneyTransferEvent;
import com.iluwatar.event.sourcing.event.MoneyWithdrawalEvent;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by serdarh on 06.08.2017.
 */
public class MoneyTransactionService {
    private EventProcessor eventProcessor;

    public MoneyTransactionService(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    public void depositMoney(int accountNo, BigDecimal money){
        MoneyDepositEvent moneyDepositEvent = new MoneyDepositEvent(SequenceIdGenerator.nextSequenceId(), new Date().getTime(), accountNo, money);
        eventProcessor.process(moneyDepositEvent);
    }

    public void withdrawalMoney(int accountNo, BigDecimal money){
        MoneyWithdrawalEvent moneyWithdrawalEvent = new MoneyWithdrawalEvent(SequenceIdGenerator.nextSequenceId(), new Date().getTime(), accountNo, money);
        eventProcessor.process(moneyWithdrawalEvent);
    }

    public void transferMoney(int accountNoFrom, int accountNoTo, BigDecimal money){
        MoneyTransferEvent moneyTransferEvent = new MoneyTransferEvent(SequenceIdGenerator.nextSequenceId(), new Date().getTime(), money, accountNoFrom, accountNoTo);
        eventProcessor.process(moneyTransferEvent);
    }
}
