package test.java.com.wssia.caching;

import main.java.com.wssia.caching.App;
import main.java.com.wssia.caching.AppManager;

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
    AppManager.init();
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
