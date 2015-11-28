package com.iluwatar.eda.handler;

import com.iluwatar.eda.event.UserUpdatedEvent;
import com.iluwatar.eda.framework.Channel;

/**
 * Handles the {@link UserUpdatedEvent} message.
 */
public class UserUpdatedEventHandler implements Channel<UserUpdatedEvent> {
  public void dispatch(UserUpdatedEvent message) {
    System.out.println("User Updated!");
  }
}
