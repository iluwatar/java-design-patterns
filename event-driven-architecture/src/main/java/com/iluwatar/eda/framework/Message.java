package com.iluwatar.eda.framework;

/**
 * A {@link Message} is an object with a specific type that is associated to a
 * specific {@link Channel}
 */
public interface Message {
    Class<? extends Message> getType();
}
