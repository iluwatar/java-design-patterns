package com.iluwatar.event.sourcing.event;

import com.iluwatar.event.sourcing.api.DomainEvent;
import com.iluwatar.event.sourcing.domain.Account;
import com.iluwatar.event.sourcing.state.AccountAggregate;

/**
 * Created by serdarh on 06.08.2017.
 */
public class AccountCreateEvent extends DomainEvent {
    private final int accountNo;
    private final String owner;

    public AccountCreateEvent(long sequenceId, long createdTime, int accountNo, String owner) {
        super(sequenceId, createdTime, "AccountCreateEvent");
        this.accountNo = accountNo;
        this.owner = owner;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public void process() {
        Account account = AccountAggregate.getAccount(accountNo);
        if(account!=null){
            throw new RuntimeException("Account already exists");
        }
        account = new Account(accountNo,owner);
        account.handleEvent(this);
    }
}
