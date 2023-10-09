package com.iluwatar;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Error class for storing information on the error.
 * Error ID is not necessary, but may be useful for serialisation.
 */
@Getter
@AllArgsConstructor
public class NotificationError {
  private int errorId;
  private String errorMessage;

  @Override
  public String toString() {
    return "Error " + errorId + ": " + errorMessage;
  }
}
