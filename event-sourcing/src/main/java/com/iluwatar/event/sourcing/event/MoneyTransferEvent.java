package com.iluwatar.event.sourcing.event;

import com.iluwatar.event.sourcing.api.DomainEvent;
import com.iluwatar.event.sourcing.domain.Account;
import com.iluwatar.event.sourcing.state.AccountAggregate;

import java.math.BigDecimal;

/**
 * Created by serdarh on 06.08.2017.
 */
public class MoneyTransferEvent extends DomainEvent {
    private BigDecimal money;
    private int accountNoFrom;
    private int accountNoTo;

    public MoneyTransferEvent(long sequenceId, long createdTime, BigDecimal money, int accountNoFrom, int accountNoTo) {
        super(sequenceId, createdTime, "MoneyTransferEvent");
        this.money = money;
        this.accountNoFrom = accountNoFrom;
        this.accountNoTo = accountNoTo;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public int getAccountNoFrom() {
        return accountNoFrom;
    }

    public int getAccountNoTo() {
        return accountNoTo;
    }

    @Override
    public void process() {
        Account accountFrom = AccountAggregate.getAccount(accountNoFrom);
        if(accountFrom==null){
            throw new RuntimeException("Account not found "+accountNoFrom);
        }
        Account accountTo = AccountAggregate.getAccount(accountNoTo);
        if(accountTo==null){
            throw new RuntimeException("Account not found"+ accountTo);
        }

        accountFrom.handleTransferFromEvent(this);
        accountTo.handleTransferToEvent(this);
    }
}
