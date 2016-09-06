package com.iluwatar.proxy.example2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Announcer {

  private HealthMonitor proxy;
  private List<HealthMonitor> listeners = new CopyOnWriteArrayList<>();

  public Announcer(Class<HealthMonitor> listenerType) {
    proxy = createProxy(listenerType);
  }

  private HealthMonitor createProxy(Class<HealthMonitor> listenerType) {
    return listenerType.cast(Proxy.newProxyInstance(listenerType.getClassLoader(), 
        new Class<?>[] {listenerType}, (proxy, method, args) -> {
          invokeAll(method, args);
          return null;
        }));
  }

  private void invokeAll(Method method, Object[] args) {
    for (HealthMonitor listener : listeners) {
      try {
        method.invoke(listener, args);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }

  public void registerListener(HealthMonitor listener) {
    listeners.add(listener);
  }

  public HealthMonitor announce() {
    return proxy;
  }

  public void removeListener(HealthMonitor listener) {
    listeners.remove(listener);
  }
}
