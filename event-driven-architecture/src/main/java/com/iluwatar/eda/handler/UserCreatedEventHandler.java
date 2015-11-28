package com.iluwatar.eda.handler;

import com.iluwatar.eda.event.Event;
import com.iluwatar.eda.event.UserCreatedEvent;
import com.iluwatar.eda.framework.Channel;
import com.iluwatar.eda.model.User;

/**
 * Handles the {@link UserCreatedEvent} message.
 */
public class UserCreatedEventHandler implements Channel<UserCreatedEvent> {

  @Override
  public void dispatch(Event message) {

    UserCreatedEvent userCreatedEvent = (UserCreatedEvent) message;
    System.out.printf("User with %s has been Created!", userCreatedEvent.getUser().getUsername());
  }
}
