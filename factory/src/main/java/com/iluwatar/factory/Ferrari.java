package com.iluwatar.factory;

/**
 * Ferrari implementation.
 */
public class Ferrari implements Car {
   
  static final String DESCRIPTION = "This is Ferrari.";

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }
}
