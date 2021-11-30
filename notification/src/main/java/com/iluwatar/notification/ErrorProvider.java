package com.iluwatar.notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorProvider {
  public ErrorProvider() {}

  /**
  * set an Error message.
  *
  * @param notificationError the error to display.
  *
  */
  public String setError(NotificationError notificationError) {
    LOGGER.error(notificationError.getMessage());
    return notificationError.getMessage();
  }
}
