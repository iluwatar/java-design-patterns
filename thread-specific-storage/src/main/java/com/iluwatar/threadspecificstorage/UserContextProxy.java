package com.iluwatar.threadspecificstorage;

/**
 * Thread-Specific Object Proxy
 *
 * <p>The Thread-Specific Object Proxy acts as an intermediary, enabling application thread to
 * access and manipulate thread-specific objects simply and securely.
 */
public class UserContextProxy {
  /**
   * Underlying TSObjectCollection (ThreadLocalMap) managed by JVM.This ThreadLocal acts as the Key
   * for the map.So That there is also no key factory.
   */
  private static final ThreadLocal<UserContext> userContextHolder = new ThreadLocal<>();

  /** Private constructor to prevent instantiation of this utility class. */
  private UserContextProxy() {}

  /** Set UserContext for the current thread. */
  public static void set(UserContext context) {
    userContextHolder.set(context);
  }

  /** Get UserContext for the current thread. */
  public static UserContext get() {
    return userContextHolder.get();
  }

  /** Clear UserContext to prevent potential memory leaks. */
  public static void clear() {
    userContextHolder.remove();
  }
}
