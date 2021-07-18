package com.iluwatar.proactor;

import java.io.NotActiveException;

/**
 * Defines an interface for processing results of asynchronous operations.
 */
public interface CompletionHandler {
  Handle handle = new Handle();

  String handleEvent(Handle handle) throws NotActiveException;
}
