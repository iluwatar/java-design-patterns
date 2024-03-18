package com.iluwatar.gateway;

import java.util.HashMap;
import java.util.Map;

/**
 * The "GatewayFactory" class is responsible for providing different external services in this Gateway design pattern
 * example. It allows clients to register and retrieve specific gateways based on unique keys.
 */
public class GatewayFactory {
  private Map<String, Gateway> gateways = new HashMap<>();

  public void registerGateway(String key, Gateway gateway) {
    gateways.put(key, gateway);
  }

  public Gateway getGateway(String key) {
    return gateways.get(key);
  }
}
