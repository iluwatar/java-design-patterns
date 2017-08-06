package com.iluwatar.event.sourcing.event;

import com.iluwatar.event.sourcing.api.DomainEvent;
import com.iluwatar.event.sourcing.domain.Account;
import com.iluwatar.event.sourcing.domain.Transaction;
import com.iluwatar.event.sourcing.gateway.Gateways;
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
        if(accountFrom.getMoney().compareTo(money)==-1){
            throw new RuntimeException("Insufficient Account Balance");
        }
        accountFrom.setMoney(accountFrom.getMoney().subtract(money));
        accountTo.setMoney(accountTo.getMoney().add(money));

        Transaction transactionFrom = new Transaction(accountNoFrom,BigDecimal.ZERO,money,accountFrom.getMoney());
        accountFrom.getTransactions().add(transactionFrom);

        Transaction transactionTo = new Transaction(accountNoTo,money,BigDecimal.ZERO,accountTo.getMoney());
        accountTo.getTransactions().add(transactionTo);

        AccountAggregate.putAccount(accountFrom);
        AccountAggregate.putAccount(accountTo);

        if(!isReplica()) {
            Gateways.getTransactionLogger().log(transactionFrom);
            Gateways.getTransactionLogger().log(transactionTo);
        }
    }
}
