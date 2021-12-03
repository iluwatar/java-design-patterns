package com.iluwatar.notification;

import lombok.extern.slf4j.Slf4j;

/**
 * Error Provider displays the errors
 */
@Slf4j
public class ErrorProvider {
  /**
   * Instantiates a ErrorProvider
   */
  public ErrorProvider() {
    // This constructor is intentionally empty. Nothing special is needed here.
  }

  /**
  * set an Error message.
  *
  * @param notificationError the error to display.
  *
  */
  public String displayErrorMessage(final NotificationError notificationError) {
    if (LOGGER.isErrorEnabled()) {
      LOGGER.error(notificationError.getMessage());
    }
    return notificationError.getMessage();
  }
}
