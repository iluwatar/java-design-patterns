package com.iluwatar.eda.advanced.handler;

import com.iluwatar.eda.advanced.events.UserCreatedEvent;
import com.iluwatar.eda.advanced.framework.Channel;

/**
 * @author cfarrugia
 */
public class UserCreatedEventHandler implements Channel<UserCreatedEvent> {

    public void dispatch(UserCreatedEvent message) {
        System.out.println("User Created!");
    }
}
