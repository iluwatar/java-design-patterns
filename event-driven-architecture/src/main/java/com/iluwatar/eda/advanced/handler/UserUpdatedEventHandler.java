package com.iluwatar.eda.advanced.handler;

import com.iluwatar.eda.advanced.events.UserUpdatedEvent;
import com.iluwatar.eda.advanced.framework.Channel;

public class UserUpdatedEventHandler implements Channel<UserUpdatedEvent> {

    public void dispatch(UserUpdatedEvent message) {
        System.out.println("User Updated!");
    }
}
