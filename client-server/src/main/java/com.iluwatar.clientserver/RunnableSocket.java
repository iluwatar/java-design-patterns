package com.iluwatar.clientserver;

/**
 * Multithreading to simulate client-server communication.
 */
public class RunnableSocket implements Runnable {
  private Thread thread;
  private String role;

  RunnableSocket(String role) {
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
    if (this.role.equals("Client")) {
      Client.main(new String[]{});
    } else {
      Server.main(new String[]{});
      try {
        Thread.sleep(1000, 100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
