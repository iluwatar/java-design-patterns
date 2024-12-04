package com.iluwatar.monolithic.exceptions;

import java.io.Serial;

public class NonExistentUserException extends RuntimeException {
  @Serial
  private static final long serialVersionUID = -7660909426227843633L;

  public NonExistentUserException(String msg) {
        super(msg);
    }
}
