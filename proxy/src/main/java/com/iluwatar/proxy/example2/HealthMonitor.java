package com.iluwatar.proxy.example2;

import java.util.EventListener;

public interface HealthMonitor extends EventListener {

  void disconnected(EndPoint endPoint);

  void connected(EndPoint endPoint);

}
