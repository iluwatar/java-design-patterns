package com.iluwater.datatransferhash;

import java.util.HashMap;

/**
 * Data transfer hash
 * Stores data for transport between layers as a set of values associated with well-known keys.
 */
public class DataTransferHashObject {
  private final HashMap<String, Object> hash;

  DataTransferHashObject() {
    hash = new HashMap<>();
  }

  public void put(final String key, final Object value) {
    hash.put(key, value);
  }

  public Object getValue(final String key) {
    return this.hash.get(key);
  }
}