package com.iluwatar.proactor;


/**
 * Proactor is a software design pattern for event handling in which
 * long running activities are running in an asynchronous part.
 *
 * <p>This pattern simplifies asynchronous application development
 * by integrating the demultiplexing of completion events and the
 * dispatching of their corresponding event handlers.
 */
public class App {
  /**
   * Execute the pattern.
   *
   * @param args system input
   */
  public static void main(String[] args) throws Exception {
    var operationProcessor = new AsynchronousOperationProcessor();
    var client = new Client();
    client.run(operationProcessor);
  }
}
