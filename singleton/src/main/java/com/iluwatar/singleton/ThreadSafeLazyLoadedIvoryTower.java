package com.iluwatar.singleton;

/**
 * Thread-safe Singleton class. The instance is lazily initialized and thus needs synchronization
 * mechanism.
 *
 * Note: if created by reflection then a singleton will not be created but multiple options in the
 * same classloader
 */
public class ThreadSafeLazyLoadedIvoryTower {

  private static ThreadSafeLazyLoadedIvoryTower instance = null;

  private ThreadSafeLazyLoadedIvoryTower() {}

  /**
   * The instance gets created only when it is called for first time. Lazy-loading
   */
  public  static synchronized ThreadSafeLazyLoadedIvoryTower getInstance() {

    if (instance == null) {
      instance = new ThreadSafeLazyLoadedIvoryTower();
    }

    return instance;
  }
}
