package com.iluwatar.reactor.app;

import java.io.IOException;

import org.junit.Test;

/**
 * 
 * This class tests the Distributed Logging service by starting a Reactor and then sending it
 * concurrent logging requests using multiple clients.
 * 
 * @author npathai
 */
public class AppTest {

  /**
   * Test the application.
   * 
   * @throws IOException if any I/O error occurs.
   * @throws InterruptedException if interrupted while stopping the application.
   */
  @Test
  public void testApp() throws IOException, InterruptedException {
    App app = new App();
    app.start();

    AppClient client = new AppClient();
    client.start();

    // allow clients to send requests. Artificial delay.
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    client.stop();

    app.stop();
  }
}
