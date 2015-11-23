package com.iluwatar.eda.handler;

import com.iluwatar.eda.event.UserCreatedEvent;
import com.iluwatar.eda.framework.Channel;

/**
 * Handles the {@link UserCreatedEvent} message
 */
public class UserCreatedEventHandler implements Channel<UserCreatedEvent> {

    public void dispatch(UserCreatedEvent message) {
        System.out.println("User Created!");
    }
}
