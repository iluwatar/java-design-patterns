package com.iluwatar.notification;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * A notification contains a list of NotificationError
 */
public class Notification {

  /**
   * notificationErrors is a list of NotificationError
   */
  @Getter
  private List<NotificationError> errors = new ArrayList();

  /**
   * Instantiates a Notification
   */
  protected Notification() {
    // This constructor is intentionally empty. Nothing special is needed here.
  }


  /**
  * Set errors in this Notification.
  *
  * @param notificationError the error to be added.
  */
  public void setErrors(final NotificationError notificationError) {

    this.errors.add(notificationError);
  }

  /**
  * Check errors.
  *
  * @return true if this Notification has error, false if errors do not exist.
  */
  public boolean hasErrors() {

    return !this.errors.isEmpty();
  }

}
