package com.iluwatar.tls;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.AccessDeniedException;

/**
 * B2BServiceTest class to test the B2BService
 */
public class B2BServiceTest {

  @Test
  public void counterResetTest() throws AccessDeniedException {
    Tenant tenant = new Tenant("testTenant", 100);
    B2BService service = new B2BService(tenant);

    for (int i = 0; i < 20; i++) {
      service.dummyCustomerApi();
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    int counter = service.getCurrentCallsCount();
    Assert.assertTrue("", counter < 11);
  }
}
