package com.iluwatar.threadspecificstorage;

/**
 * Application entry point demonstrating the Thread-Specific Storage pattern.
 *
 * <p>This example simulates concurrent request processing for multiple users. Each request carries
 * a user token, and user-specific context is managed transparently using thread-specific storage.
 */
public class App {

  /**
   * Runs the Thread-Specific Storage pattern demonstration.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    // Initialize components
    UserContextProxy proxy = new UserContextProxy();

    // Simulate concurrent requests from multiple users
    for (int i = 1; i <= 5; i++) {
      // Simulate tokens for different users
      String token = "token::" + (i % 3 + 1); // 3 distinct users

      new Thread(
              () -> {
                // Simulate request processing flow
                RequestHandler handler = new RequestHandler(proxy, token);
                handler.process();
              })
          .start();

      // Slightly stagger request times
      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
