package com.iluwatar.proactor;

/**
 * Defines an interface for processing results of asynchronous operations.
 */
public interface CompletionHandler {
  Handle handle = new Handle();

  String handleEvent(Handle handle) throws Exception;
}
