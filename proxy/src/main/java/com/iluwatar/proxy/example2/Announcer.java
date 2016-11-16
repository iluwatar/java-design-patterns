package com.iluwatar.proxy.example2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Announcer<T extends EventListener> {

  private T proxy;
  private List<T> listeners = new CopyOnWriteArrayList<>();

  public Announcer(Class<T> listenerType) {
    proxy = createProxy(listenerType);
  }

  private T createProxy(Class<T> listenerType) {
    return listenerType.cast(Proxy.newProxyInstance(listenerType.getClassLoader(), 
        new Class<?>[] {listenerType}, (proxy, method, args) -> {
          invokeAll(method, args);
          return null;
        }));
  }

  private void invokeAll(Method method, Object[] args) {
    for (T listener : listeners) {
      try {
        method.invoke(listener, args);
      } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }

  public void registerListener(T listener) {
    listeners.add(listener);
  }

  public T announce() {
    return proxy;
  }

  public void removeListener(T listener) {
    listeners.remove(listener);
  }

  public static <T extends EventListener> Announcer<T> to(Class<T> listenerType) {
    return new Announcer<>(listenerType);
  }
}
