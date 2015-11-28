package com.iluwatar.eda.handler;

import com.iluwatar.eda.event.Event;
import com.iluwatar.eda.event.UserCreatedEvent;
import com.iluwatar.eda.event.UserUpdatedEvent;
import com.iluwatar.eda.framework.Channel;

/**
 * Handles the {@link UserUpdatedEvent} message.
 */
public class UserUpdatedEventHandler implements Channel<UserUpdatedEvent> {

  @Override
  public void dispatch(Event message) {

    UserUpdatedEvent userUpdatedEvent = (UserUpdatedEvent) message;
    System.out.printf("User with %s has been Updated!", userUpdatedEvent.getUser().getUsername());
  }
}
