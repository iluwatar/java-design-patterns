package com.iluwater;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * The notification. Used for storing errors and any other methods
 * that may be necessary for when we send information back to the
 * presentation layer.
 */
public class Notification {
  @Getter
  private List<NotificationError> errors = new ArrayList<>();

  protected Notification() {
    //constructor purposefully left blank
  }

  public boolean hasErrors() {
    return this.errors.size() != 0;
  }

  public void addError(NotificationError error) {
    this.errors.add(error);
  }
}
