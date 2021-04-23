package com.iluwatar.facet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class securityTest {
  @Test
  void administratorSecurityTest() {
    Sentry administratorSentry = new DefaultSentry(new CurrentContext());
    Facet administratorFacet = Facet.create(administratorSentry, new Class[]{SecurityMethods.class});
    administratorFacet.setUser(new Administrator());
    assertSame(administratorFacet.invokeSecurityMethod(SecurityMethods.class), "Administrator create something.");
  }

  @Test
  void clientSecurityTest() {
    Sentry clientSentry = new DefaultSentry(new CurrentContext());
    Facet clientFacet = Facet.create(clientSentry, new Class[]{SecurityMethods.class});
    clientFacet.setUser(new Client());
    assertNull(clientFacet.invokeSecurityMethod(SecurityMethods.class));
  }
}
