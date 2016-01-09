package com.iluwatar.caching;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * Application test
 *
 */
public class AppTest {
  App app;

  /**
   * Setup of application test includes: initializing DB connection and cache size/capacity.
   */
  @Before
  public void setUp() {
    AppManager.initDb(false); // VirtualDB (instead of MongoDB) was used in running the JUnit tests
                              // to avoid Maven compilation errors. Set flag to true to run the
                              // tests with MongoDB (provided that MongoDB is installed and socket
                              // connection is open).
    AppManager.initCacheCapacity(3);
    app = new App();
  }

  @Test
  public void testReadAndWriteThroughStrategy() {
    app.useReadAndWriteThroughStrategy();
  }

  @Test
  public void testReadThroughAndWriteAroundStrategy() {
    app.useReadThroughAndWriteAroundStrategy();
  }

  @Test
  public void testReadThroughAndWriteBehindStrategy() {
    app.useReadThroughAndWriteBehindStrategy();
  }
}
