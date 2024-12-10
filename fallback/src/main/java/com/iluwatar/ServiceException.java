package com.iluwatar;

import java.io.Serial;

/**
 * Custom exception class for service-related errors in the fallback pattern.
 * This exception is thrown when a service operation fails and needs to be handled
 * by the fallback mechanism.
 */
public class ServiceException extends Exception {
  
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new ServiceException with the specified detail message.
   *
   * @param message the detail message describing the error
   */
  public ServiceException(String message) {
    super(message);
  }

  /**
   * Constructs a new ServiceException with the specified detail message and cause.
   *
   * @param message the detail message describing the error
   * @param cause the cause of the exception
   */
  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }
}
