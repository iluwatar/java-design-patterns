package com.iluwatar;

import java.io.Serial;

/**
 * Custom exception class for service-related errors in the fallback pattern.
 */
public class ServiceException extends Exception {
  @Serial
  private static final long serialVersionUID = 1L;

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
