package com.iluwatar.monolithic.exceptions;

import java.io.Serial;

public class NonExistentProductException extends RuntimeException{
  @Serial
  private static final long serialVersionUID = -593425162052345565L;

  public NonExistentProductException(String msg){
    super(msg);
  }
}
