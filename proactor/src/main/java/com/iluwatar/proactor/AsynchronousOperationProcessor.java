package com.iluwatar.proactor;

import java.io.NotActiveException;

/**
 * To execute AsynchronousOperation and dispatch handler to proactor.
 */
public class AsynchronousOperationProcessor {
  /**
   * Execute AsynchronousOperation.
   * Delegate to a proactor when AsynchronousOperation complete.
   *
   * @param operation task to do
   * @param handler   handle Client needed
   * @return result to client
   */
  public String register(String operation, ConcreteCompletionHandler handler) throws
      NotActiveException, InterruptedException {
    final var proactor = new Proactor();
    final var op = new AsynchronousOperation();
    final Handle get = op.execute(operation);
    return proactor.handleEvent(get, handler);
  }

}
