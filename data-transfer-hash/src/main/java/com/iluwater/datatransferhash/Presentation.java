package com.iluwater.datatransferhash;

/**
 * Presentation tier objects
 * Create hashes for sending data, or use values in received hashes.
 */

public class Presentation {

  public void createHash(final String k, final Object v, final DataTransferHashObject hash) {
    hash.put(k, v);
  }

  public Object getData(final String k, final DataTransferHashObject hash) {
    return hash.getValue(k);
  }
}