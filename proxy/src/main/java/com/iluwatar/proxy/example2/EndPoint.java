package com.iluwatar.proxy.example2;

public class EndPoint {

  private final String name;
  private final Announcer healthMonitors = new Announcer(HealthMonitor.class);
  
  public EndPoint(String name) {
    this.name = name;
  }

  public void addMonitor(HealthMonitor monitor) {
    healthMonitors.registerListener(monitor);
  }

  public void disconnect() {
    healthMonitors.announce().disconnected(this);
  }

  public void connected() {
    healthMonitors.announce().connected(this);
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }
}