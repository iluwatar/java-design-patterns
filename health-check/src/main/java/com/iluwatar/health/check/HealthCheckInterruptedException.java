package com.iluwatar.health.check;

/**
 * Exception thrown when the health check is interrupted during execution. This exception is a
 * runtime exception that wraps the original cause.
 */
public class HealthCheckInterruptedException extends RuntimeException {
  /**
   * Constructs a new HealthCheckInterruptedException with the specified cause.
   *
   * @param cause the cause of the exception
   */
  public HealthCheckInterruptedException(Throwable cause) {
    super("Health check interrupted", cause);
  }
}
