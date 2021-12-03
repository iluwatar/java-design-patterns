package com.iluwatar.notification;

import lombok.Getter;

/**
 * A NotificationError with a error message
 */
public class NotificationError {

  /**
   * Error message of a NotificationError
   */
  @Getter
  private String message;

  /**
  * Creates a NotificationError.
  *
  * @param message the message of this NotificationError.
  */
  public NotificationError(final String message) {
    this.message = message;
  }

}
