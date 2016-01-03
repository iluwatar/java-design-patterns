package com.iluwatar.eda.event;

import com.iluwatar.eda.model.User;

/**
 * The {@link UserCreatedEvent} should should be dispatched whenever a user has been created.
 * This class can be extended to contain details about the user has been created. In this example,
 * the entire {@link User} object is passed on as data with the event.
 */
public class UserCreatedEvent extends Event {

  private User user;

  public UserCreatedEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}
