package com.iluwatar.timeout;

/**
 * Exception thrown when an operation times out.
 */
public class TimeoutException extends Exception {
  
  public TimeoutException(String message) {
    super(message);
  }
  
  public TimeoutException(String message, Throwable cause) {
    super(message, cause);
  }
}