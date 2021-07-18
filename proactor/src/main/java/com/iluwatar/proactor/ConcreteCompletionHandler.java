package com.iluwatar.proactor;

import java.io.NotActiveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Processes results of asynchronous operations in a specific manner.
 */
public class ConcreteCompletionHandler implements CompletionHandler {

  /*Logger*/
  private static final Logger LOGGER = LoggerFactory.getLogger(ConcreteCompletionHandler.class);

  ConcreteCompletionHandler(String s) {
    handle.handleName = s;
  }

  /**
   * Verify whether the handle is the same and process the rest.
   *
   * @param handle result of AsynchronousOperation
   * @return result to client
   */

  @Override
  public String handleEvent(Handle handle) throws NotActiveException {
    if (ConcreteCompletionHandler.handle.handleName.equals(handle.handleName)) {
      LOGGER.info(handle.handleName);
      return handle.handleName + " done";
    }
    throw new NotActiveException("error");
  }
}
