package com.iluwatar.clientserver;

import lombok.extern.slf4j.Slf4j;

/**
 * This is a simple client-server design model implemented by multithreaded
 * simulation of client-side and service-side communication.
 */

@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
      RunnableSocket server = new RunnableSocket("Server");
      server.start();
      RunnableSocket client = new RunnableSocket("Client");
      client.start();
  }
}
