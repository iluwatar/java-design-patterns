package com.iluwatar.proxy.example2;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * An application representing a health monitoring system. Endpoints such as TCP connections, UDP
 * connections, DB connections keep fluctuating and may get disconnected. To monitor the health of
 * these endpoints user can attach health monitors to the endpoint and when the status of the 
 * connectivity changes the health monitors are notified of the changes.
 * 
 * The class that implements the Proxy pattern is {@link Announcer}.
 * 
 * @see Announcer
 * 
 * @author npathai
 *
 */
public class App {

  public static void main(String[] args) {
    
    EndPoint endPoint = new EndPoint("TCP Endpoint");
    ConsoleMonitor consoleMonitor = new ConsoleMonitor();
    HealthCounter counter = new HealthCounter();
    endPoint.addMonitor(consoleMonitor);
    endPoint.addMonitor(counter);
    
    endPoint.connected();
    endPoint.disconnect();
    endPoint.connected();
    endPoint.disconnect();
    endPoint.connected();
    
    counter.printCounters();
  }
  
  private static class ConsoleMonitor implements HealthMonitor {

    @Override
    public void disconnected(EndPoint endPoint) {
      System.out.println(endPoint + " is disconnected.");
    }

    @Override
    public void connected(EndPoint endPoint) {
      System.out.println(endPoint + " is connected");
    }
  }
  
  private static class HealthCounter implements HealthMonitor {
    private Map<String, Health> endPointNameToHealth = new HashMap<>();
    
    private class Health {
      private boolean connected;
      private int disconnectedCount = 0;
      private int connectedCount = 0;
    }
    
    @Override
    public void disconnected(EndPoint endPoint) {
      Health health = endPointNameToHealth.get(endPoint.getName());
      if (health == null) {
        health = new Health();
        endPointNameToHealth.put(endPoint.getName(), health);
      }
      health.connected = false;
      health.disconnectedCount++;
    }

    @Override
    public void connected(EndPoint endPoint) {
      Health health = endPointNameToHealth.get(endPoint.getName());
      if (health == null) {
        health = new Health();
        endPointNameToHealth.put(endPoint.getName(), health);
      }
      health.connected = true;
      health.connectedCount++;
    }

    public void printCounters() {
      for (Entry<String, Health> entry : endPointNameToHealth.entrySet()) {
        System.out.println(String.format("EndPoint: %s, Connected: %s, Connected Count: %d, Disconnected Count: %d",
            entry.getKey(), entry.getValue().connected, entry.getValue().connectedCount, 
            entry.getValue().disconnectedCount));
      }
    }
  }
}
