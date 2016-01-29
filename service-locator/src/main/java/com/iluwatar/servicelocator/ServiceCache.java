package com.iluwatar.servicelocator;

import java.util.HashMap;
import java.util.Map;

/**
 * The service cache implementation which will cache services that are being created. On first hit,
 * the cache will be empty and thus any service that is being requested, will be created fresh and
 * then placed into the cache map. On next hit, if same service name will be requested, it will be
 * returned from the cache
 *
 * @author saifasif
 */
public class ServiceCache {

  private final Map<String, Service> serviceCache;

  public ServiceCache() {
    serviceCache = new HashMap<>();
  }

  /**
   * Get the service from the cache. null if no service is found matching the name
   *
   * @param serviceName a string
   * @return {@link Service}
   */
  public Service getService(String serviceName) {
    Service cachedService = null;
    for (String serviceJndiName : serviceCache.keySet()) {
      if (serviceJndiName.equals(serviceName)) {
        cachedService = serviceCache.get(serviceJndiName);
        System.out.println("(cache call) Fetched service " + cachedService.getName() + "("
            + cachedService.getId() + ") from cache... !");
      }
    }
    return cachedService;
  }

  /**
   * Adds the service into the cache map
   *
   * @param newService a {@link Service}
   */
  public void addService(Service newService) {
    serviceCache.put(newService.getName(), newService);
  }
}
