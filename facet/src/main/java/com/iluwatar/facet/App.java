package com.iluwatar.facet;

import lombok.extern.slf4j.Slf4j;

/**
 * use client and administrator to check whether it can successfully secure.
 */
@Slf4j
public class App {
  /**
   * Program entry point.
   *
   * @param args command line args.
   */
  public static void main(String[] args) {
    Sentry clientSentry = new DefaultSentry(new CurrentContext());
    Facet clientFacet = Facet.create(clientSentry, new Class[]{SecurityMethods.class});
    clientFacet.setUser(new Client());
    Sentry administratorSentry = new DefaultSentry(new CurrentContext());
    Facet administratorFacet =
            Facet.create(administratorSentry, new Class[]{SecurityMethods.class});
    administratorFacet.setUser(new Administrator());
    LOGGER.info("client invoke result: {}",
            clientFacet.invokeSecurityMethod(SecurityMethods.class));
    LOGGER.info("administrator invoke result: {}",
            administratorFacet.invokeSecurityMethod(SecurityMethods.class));
  }
}
