package com.iluwatar.reactor.app;

import java.io.IOException;

import org.junit.Test;

import com.iluwatar.reactor.framework.SameThreadDispatcher;
import com.iluwatar.reactor.framework.ThreadPoolDispatcher;

/**
 * 
 * This class tests the Distributed Logging service by starting a Reactor and then sending it
 * concurrent logging requests using multiple clients.
 */
public class AppTest {

  /**
   * Test the application using pooled thread dispatcher.
   * 
   * @throws IOException if any I/O error occurs.
   * @throws InterruptedException if interrupted while stopping the application.
   */
  @Test
  public void testAppUsingThreadPoolDispatcher() throws IOException, InterruptedException {
    App app = new App(new ThreadPoolDispatcher(2));
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
  
  /**
   * Test the application using same thread dispatcher.
   * 
   * @throws IOException if any I/O error occurs.
   * @throws InterruptedException if interrupted while stopping the application.
   */
  @Test
  public void testAppUsingSameThreadDispatcher() throws IOException, InterruptedException {
    App app = new App(new SameThreadDispatcher());
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
