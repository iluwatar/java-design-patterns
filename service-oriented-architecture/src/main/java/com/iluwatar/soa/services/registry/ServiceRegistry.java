package com.iluwatar.soa.services.registry;

import java.util.HashMap;
import java.util.Map;

public class ServiceRegistry {
  public static final Map<String, Object> registry = new HashMap<>();

  public static <T> void registerService(String serviceName, T serviceInstance) {
    registry.put(serviceName, serviceInstance);
  }

  @SuppressWarnings("unchecked")
  public static <T> T getService(String serviceName) {
    return (T) registry.get(serviceName);
  }
}
