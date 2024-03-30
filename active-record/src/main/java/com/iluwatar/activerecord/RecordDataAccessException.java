package com.iluwatar.activerecord;

public class RecordDataAccessException extends RuntimeException {

  public RecordDataAccessException() {
  }

  public RecordDataAccessException(String message) {
    super(message);
  }

  public RecordDataAccessException(String message, Throwable cause) {
    super(message, cause);
  }

  public RecordDataAccessException(Throwable cause) {
    super(cause);
  }
}
