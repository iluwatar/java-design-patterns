package com.iluwatar.tls;

import org.junit.Test;

import java.security.InvalidParameterException;

/**
 * TenantTest to test the creation of Tenant with valid parameters.
 */
public class TenantTest {

  @Test(expected = InvalidParameterException.class)
  public void constructorTest() {
    Tenant tenant = new Tenant("FailTenant", -1);
  }
}
