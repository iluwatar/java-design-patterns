package com.iluwatar.eda.model;

import com.iluwatar.eda.event.UserCreatedEvent;
import com.iluwatar.eda.event.UserUpdatedEvent;

/**
 * This {@link User} class is a basic pojo used to demonstrate user data sent along with
 * the {@link UserCreatedEvent} and {@link UserUpdatedEvent} events.
 */
public class User {

  private String username;

  public User(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
