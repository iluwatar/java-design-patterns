package com.iluwatar.singleton;

/**
 * Enum based singleton implementation.
 * Effective Java 2nd Edition (Joshua Bloch) p. 18
 */
public enum EnumIvoryTower {

  INSTANCE;

  @Override
  public String toString() {
    return getDeclaringClass().getCanonicalName() + "@" + hashCode();
  }
}
