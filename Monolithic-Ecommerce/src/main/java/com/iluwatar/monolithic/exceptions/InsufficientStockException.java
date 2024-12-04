package com.iluwatar.monolithic.exceptions;

import java.io.Serial;

public class InsufficientStockException extends RuntimeException{
  @Serial
  private static final long serialVersionUID = 1005208208127745099L;

  public InsufficientStockException(String message) {
        super(message);
    }
}
