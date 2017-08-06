package com.iluwatar.event.sourcing.service;

import com.iluwatar.event.sourcing.api.EventProcessor;
import com.iluwatar.event.sourcing.event.AccountCreateEvent;

import java.util.Date;

/**
 * Created by serdarh on 06.08.2017.
 */
public class AccountService {
    private EventProcessor eventProcessor;

    public AccountService(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    public void createAccount(int accountNo, String owner){
        AccountCreateEvent accountCreateEvent = new AccountCreateEvent(SequenceIdGenerator.nextSequenceId(), new Date().getTime(),accountNo,owner);
        eventProcessor.process(accountCreateEvent);
    }
}
