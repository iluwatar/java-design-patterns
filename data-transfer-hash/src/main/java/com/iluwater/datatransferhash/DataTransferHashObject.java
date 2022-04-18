package com.iluwater.datatransferhash;

import java.util.HashMap;

/**
 * Data transfer hash
 * Stores data for transport between layers as a set of values associated with well-known keys.
 */
public class DataTransferHashObject {
  private final HashMap<String, Object> hash;

  /**
   * Constructor function for data transfer hash object.
   * It initiates a HashMap for storing (key,value) pair.
   */
  DataTransferHashObject() {
    hash = new HashMap<>();
  }

  /**
   * Put (key,value) pair into Hashmap storage structure.
   *
   * @param key Hash key of data value.
   * @param value The data value that needs to be stored.
   */
  public void put(final String key, final Object value) {
    hash.put(key, value);
  }

  /**
   * Get value from Hashmap storage structure according to given hash key.
   *
   * @param key Hash key of data value.
   * @return Data value.
   */
  public Object getValue(final String key) {
    return this.hash.get(key);
  }
}