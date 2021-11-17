package com.iluwatar.notification;

public class Error {
  private String message;

  /**
  * Creates a. Error.
  *
  * @param message the message of this Error.
  */
  public Error(String message) {
    this.message = message;
  }

  /**
  * Get error message.
  *
  * @return the message of this Error.
  */
  public String getErrorMessage() {
    return this.message;
  }
}
