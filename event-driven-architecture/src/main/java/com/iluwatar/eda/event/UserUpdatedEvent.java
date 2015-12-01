package com.iluwatar.eda.event;

import com.iluwatar.eda.model.User;

/**
 * The {@link UserUpdatedEvent} should should be dispatched whenever a user has been updated.
 * This class can be extended to contain details about the user has been updated. In this example,
 * the entire {@link User} object is passed on as data with the event.
 */
public class UserUpdatedEvent extends Event {

  private User user;

  public UserUpdatedEvent(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}
