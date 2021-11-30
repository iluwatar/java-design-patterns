package com.iluwatar.notification;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class Notification {

  @Getter
  private List<NotificationError> notificationErrors = new ArrayList();

  protected Notification() {}


  /**
  * Set errors in this Notification.
  *
  * @param notificationError the error to be added.
  */
  public void setNotificationErrors(NotificationError notificationError) {

    this.notificationErrors.add(notificationError);
  }

  /**
  * Check errors.
  *
  * @return true if this Notification has error, false if errors do not exist.
  */
  public boolean hasErrors() {

    return this.notificationErrors.size() != 0;
  }

}
