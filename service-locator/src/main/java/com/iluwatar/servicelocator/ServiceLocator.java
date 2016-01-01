package com.iluwatar.servicelocator;

/**
 * The service locator module. Will fetch service from cache, otherwise creates a fresh service and
 * update cache
 *
 * @author saifasif
 */
public class ServiceLocator {

  private static ServiceCache serviceCache = new ServiceCache();

  private ServiceLocator() {
  }

  /**
   * Fetch the service with the name param from the cache first, if no service is found, lookup the
   * service from the {@link InitContext} and then add the newly created service into the cache map
   * for future requests.
   *
   * @param serviceJndiName a string
   * @return {@link Service}
   */
  public static Service getService(String serviceJndiName) {
    Service serviceObj = serviceCache.getService(serviceJndiName);
    if (serviceObj != null) {
      return serviceObj;
    } else {
      /*
       * If we are unable to retrive anything from cache, then lookup the service and add it in the
       * cache map
       */
      InitContext ctx = new InitContext();
      serviceObj = (Service) ctx.lookup(serviceJndiName);
      if (serviceObj != null) { // Only cache a service if it actually exists
        serviceCache.addService(serviceObj);
      }
      return serviceObj;
    }
  }
}
