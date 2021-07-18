package com.iluwatar.proactor;

import java.io.NotActiveException;

/**
 * Dispatches Completion Handlers.
 */
public class Proactor {
  /**
   * Dispatch the handler and here using FIFO.
   *
   * @param get     result of AsynchronousOperation
   * @param handler requirement of Client
   * @return result to client
   */
  public String handleEvent(Handle get, ConcreteCompletionHandler handler) throws
      NotActiveException {
    return handler.handleEvent(get);
  }
}
