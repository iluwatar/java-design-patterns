package com.iluwatar.corruption.system;

import java.util.HashMap;
import java.util.Optional;

/**
 * The class represents a data store for the modern system.
 * @param <V> the type of the value stored in the data store
 */
public abstract class DataStore<V> {
  private final HashMap<String, V> inner;

  public DataStore() {
    inner = new HashMap<>();
  }

  public Optional<V> get(String key) {
    return Optional.ofNullable(inner.get(key));
  }

  public Optional<V> put(String key, V value) {
    return Optional.ofNullable(inner.put(key, value));

  }
}
