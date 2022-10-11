package com.iluwater.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 */
@Getter
@AllArgsConstructor
public class NotificationError {
  private int errorID;
  private String errorMessage;

  @Override
  public String toString() {
    return "Error " + errorID + ": " + errorMessage;
  }
}
