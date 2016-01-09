package com.iluwatar.singleton;

/**
 * Singleton class. Eagerly initialized static instance guarantees thread safety.
 */
public final class IvoryTower {

  /**
   * Static to class instance of the class.
   */
  private static final IvoryTower INSTANCE = new IvoryTower();

  /**
   * Private constructor so nobody can instantiate the class.
   */
  private IvoryTower() {}

  /**
   * To be called by user to obtain instance of the class.
   *
   * @return instance of the singleton.
   */
  public static IvoryTower getInstance() {
    return INSTANCE;
  }
}
