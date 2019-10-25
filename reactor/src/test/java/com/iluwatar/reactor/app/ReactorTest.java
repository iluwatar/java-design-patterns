/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.reactor.app;

import com.iluwatar.reactor.framework.SameThreadDispatcher;
import com.iluwatar.reactor.framework.ThreadPoolDispatcher;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 
 * This class tests the Distributed Logging service by starting a Reactor and then sending it
 * concurrent logging requests using multiple clients.
 */
public class ReactorTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(ReactorTest.class);

  /**
   * Test the application using pooled thread dispatcher.
   * 
   * @throws IOException if any I/O error occurs.
   * @throws InterruptedException if interrupted while stopping the application.
   */
  @Test
  public void testAppUsingThreadPoolDispatcher() throws IOException, InterruptedException {
    LOGGER.info("testAppUsingThreadPoolDispatcher start");
    App app = new App(new ThreadPoolDispatcher(2));
    app.start();

    AppClient client = new AppClient();
    client.start();

    // allow clients to send requests. Artificial delay.
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      LOGGER.error("sleep interrupted", e);
    }

    client.stop();

    app.stop();
    LOGGER.info("testAppUsingThreadPoolDispatcher stop");
  }

  /**
   * Test the application using same thread dispatcher.
   * 
   * @throws IOException if any I/O error occurs.
   * @throws InterruptedException if interrupted while stopping the application.
   */
  @Test
  public void testAppUsingSameThreadDispatcher() throws IOException, InterruptedException {
    LOGGER.info("testAppUsingSameThreadDispatcher start");
    App app = new App(new SameThreadDispatcher());
    app.start();

    AppClient client = new AppClient();
    client.start();

    // allow clients to send requests. Artificial delay.
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      LOGGER.error("sleep interrupted", e);
    }

    client.stop();

    app.stop();
    LOGGER.info("testAppUsingSameThreadDispatcher stop");
  }
}
