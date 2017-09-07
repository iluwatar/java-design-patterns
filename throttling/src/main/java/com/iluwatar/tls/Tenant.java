package com.iluwatar.tls;

import java.security.InvalidParameterException;

/**
 * A Pojo class to create a basic Tenant with the allowed calls per second.
 */
public class Tenant {

  private String name;
  private int allowedCallsPerSecond;

  /**
   *
   * @param name Name of the tenant
   * @param allowedCallsPerSecond The number of calls allowed for a particular tenant.
   * @throws InvalidParameterException If number of calls is less than 0, throws exception.
   */
  public Tenant(String name, int allowedCallsPerSecond) {
    if (allowedCallsPerSecond < 0) {
      throw new InvalidParameterException("Number of calls less than 0 not allowed");
    }
    this.name = name;
    this.allowedCallsPerSecond = allowedCallsPerSecond;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAllowedCallsPerSecond() {
    return allowedCallsPerSecond;
  }

  public void setAllowedCallsPerSecond(int allowedCallsPerSecond) {
    this.allowedCallsPerSecond = allowedCallsPerSecond;
  }
}
