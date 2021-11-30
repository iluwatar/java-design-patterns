package com.iluwatar.notification;

import lombok.Getter;

public class NotificationError {

  @Getter
  private String message;

  /**
  * Creates a NotificationError.
  *
  * @param message the message of this NotificationError.
  */
  public NotificationError(String message) {
    this.message = message;
  }

}
