package com.iluwatar.facet;

import lombok.extern.slf4j.Slf4j;

/**
 * use client and administrator to check whether it can successfully secure.
 * for client, it want to execute the secured method. Then the facet will ask
 * the sentry to validate the execution. But because of the identity is user,
 * it requirement is illegal. So the result is null. For admin to execute the
 * secured method. It will be executed.
 */
@Slf4j
public class App {
  /**
   * Program entry point.
   *
   * @param args command line args.
   */
  public static void main(String[] args) {
    var clientSentry = new DefaultSentry(new CurrentContext());
    var clientFacet = Facet.create(clientSentry, new Class[]{SecurityMethods.class});
    clientFacet.setUser(new Client());
    var administratorSentry = new DefaultSentry(new CurrentContext());
    var administratorFacet =
            Facet.create(administratorSentry, new Class[]{SecurityMethods.class});
    administratorFacet.setUser(new Administrator());
    LOGGER.info("client invoke result: {}",
            clientFacet.invokeSecurityMethod(SecurityMethods.class));
    LOGGER.info("administrator invoke result: {}",
            administratorFacet.invokeSecurityMethod(SecurityMethods.class));
  }
}
