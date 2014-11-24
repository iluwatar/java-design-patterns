package com.iluwatar;

/**
 *
 * Enum Singleton class.
 *
 */
public enum EnumIvoryTower {
    INSTANCE;

    public static EnumIvoryTower getInstance() {
        return INSTANCE;
    }

    @Override
    public String toString() {
        return getDeclaringClass().getCanonicalName() + "@" + hashCode();
    }
}
