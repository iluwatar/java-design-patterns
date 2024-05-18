package com.iluwatar.soa.services.registry;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {
  public static final Map<String, Object> registry = new HashMap<>();

  public static void registerService(String serviceName, Object serviceInstance) {
    registry.put(serviceName, serviceInstance);
  }

  public static Object getService(String serviceName) {
    return registry.get(serviceName);
  }
}
