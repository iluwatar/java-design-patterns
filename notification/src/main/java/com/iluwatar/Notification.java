package com.iluwatar;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The notification. Used for storing errors and any other methods
 * that may be necessary for when we send information back to the
 * presentation layer.
 */
@Getter
@NoArgsConstructor
public class Notification {

  private final List<NotificationError> errors = new ArrayList<>();

  public boolean hasErrors() {
    return !this.errors.isEmpty();
  }

  public void addError(NotificationError error) {
    this.errors.add(error);
  }
}
