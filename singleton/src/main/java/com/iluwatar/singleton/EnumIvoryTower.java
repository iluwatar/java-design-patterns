/**
 * Enum Singleton class.
 * Effective Java 2nd Edition (Joshua Bloch) p. 18
 */
package com.iluwatar.singleton;

/**
 * Enum based singleton implementation.
 */
public enum EnumIvoryTower {

    INSTANCE;

    @Override
    public String toString() {
        return getDeclaringClass().getCanonicalName() + "@" + hashCode();
    }
}
