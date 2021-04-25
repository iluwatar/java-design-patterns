package com.iluwatar.clientserver;

import org.slf4j.Logger;

/**
 * Multithreading to simulate client-server communication.
 */
public class RunnableSocket implements Runnable {
  /**
   * Thread of Class.
   */
  private Thread thread;
  /**
   * Role of the Thread.
   */
  private final String role;

  /**
   *  Logger.
   */
  private static final Logger LOGG = null;

  /* default */RunnableSocket(final String role) {
    this.role = role;
  }

  /**
   * start a thread.
   */
  public void start() {
    if (thread == null) {
      thread = new Thread(this, role);
      thread.start();
    }
  }

  @Override
  public void run() {
    if ("Client".equals(this.role)) {
      Client.main(new String[]{});
    } else {
      Server.main(new String[]{});
      try {
        Thread.sleep(1000, 100);
      } catch (InterruptedException e) {
        LOGG.error("Ops!", e);
      }
    }
  }
}