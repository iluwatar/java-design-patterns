package com.iluwatar.facet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Facet pattern is used as a security pattern in
 * CapabilityOrientedProgramming, in order to satisfy
 * the PrincipleOfLeastAuthority.
 * <p>
 * Test for Security.
 */
class SecurityTest {

  /**
   * Testing {@link Administrator} can execute the secured method.
   */
  @Test
  void administratorSecurityTest() {
    Sentry administratorSentry = new DefaultSentry(new CurrentContext());
    Facet administratorFacet = Facet.create(administratorSentry, new Class[]{SecurityMethods.class});
    administratorFacet.setUser(new Administrator());
    assertSame("Administrator create something.", administratorFacet.invokeSecurityMethod(SecurityMethods.class));
  }

  /**
   * Testing {@link Client} cannot execute the secured method.
   */
  @Test
  void clientSecurityTest() {
    Sentry clientSentry = new DefaultSentry(new CurrentContext());
    Facet clientFacet = Facet.create(clientSentry, new Class[]{SecurityMethods.class});
    clientFacet.setUser(new Client());
    assertNull(clientFacet.invokeSecurityMethod(SecurityMethods.class));
  }

  /**
   * Testing {@link Facet} query and narrow method are correct.
   */
  @Test
  void FacetNarrowTest() {
    Facet facet = Facet.create(new DefaultSentry(new CurrentContext()), new Class[0]);
    Facet.narrow(facet, new Class[]{SecurityMethods.class});
    assertEquals(SecurityMethods.class, Facet.query(facet)[0]);
  }

}
