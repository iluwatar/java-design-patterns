package com.iluwatar.eda.handler;

import com.iluwatar.eda.event.Event;
import com.iluwatar.eda.event.UserCreatedEvent;
import com.iluwatar.eda.framework.Handler;

/**
 * Handles the {@link UserCreatedEvent} message.
 */
public class UserCreatedEventHandler implements Handler<UserCreatedEvent> {

  @Override
  public void onEvent(Event message) {

    UserCreatedEvent userCreatedEvent = (UserCreatedEvent) message;
    System.out.printf("User with %s has been Created!", userCreatedEvent.getUser().getUsername());
  }
}
