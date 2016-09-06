package com.iluwatar.proxy.example2;

public interface HealthMonitor {

  void disconnected(EndPoint endPoint);

  void connected(EndPoint endPoint);

}
