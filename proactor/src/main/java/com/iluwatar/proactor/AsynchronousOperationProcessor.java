package com.iluwatar.proactor;

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
  public String register(String operation, ConcreteCompletionHandler handler) throws Exception {
    final Proactor proactor = new Proactor();
    final AsynchronousOperation op = new AsynchronousOperation();
    final Handle get = op.execute(operation);
    return proactor.handleEvent(get, handler);
  }

}
