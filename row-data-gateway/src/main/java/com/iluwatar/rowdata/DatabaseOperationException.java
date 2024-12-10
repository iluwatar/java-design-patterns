package com.iluwatar.rowdata;
/**
 * Custom exception for database operation errors.
 */
public class DatabaseOperationException extends RuntimeException {
  /**
   * Constructs a new DatabaseOperationException with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause the cause
   */
  public DatabaseOperationException(String message, Throwable cause) {
    super(message, cause);
  }
}
