package com.iluwater.datatransferhash;

/**
 * Business objects
 * Create hashes for sending data, or use values in received hashes.
 */
public class Business {

  /**
   * Create hashes for sending data.
   *
   * @param k Hash key of data
   * @param v Data Value
   * @param hash The data transfer hash object, which stores data for transport between layers
   */
  public void createHash(final String k, final Object v, final DataTransferHashObject hash) {
    hash.put(k, v);
  }

  /**
   * Receive data according to hash key.
   *
   * @param k Hash key of data
   * @param hash The data transfer hash object, which stores data for transport between layers
   * @return Received Data from another layer
   */
  public Object getData(final String k, final DataTransferHashObject hash) {
    return hash.getValue(k);
  }
}