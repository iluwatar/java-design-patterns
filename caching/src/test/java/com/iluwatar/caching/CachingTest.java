/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.caching;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Application test
 */
class CachingTest {
  private App app;

  /**
   * Setup of application test includes: initializing DB connection and cache size/capacity.
   */
  @BeforeEach
  public void setUp() {
    // VirtualDB (instead of MongoDB) was used in running the JUnit tests
    // to avoid Maven compilation errors. Set flag to true to run the
    // tests with MongoDB (provided that MongoDB is installed and socket
    // connection is open).
    AppManager.initDb(false);
    AppManager.initCacheCapacity(3);
    app = new App();
  }

  @Test
  void testReadAndWriteThroughStrategy() {
	assertNotNull(app);
    app.useReadAndWriteThroughStrategy();
  }

  @Test
  void testReadThroughAndWriteAroundStrategy() {
	assertNotNull(app);
    app.useReadThroughAndWriteAroundStrategy();
  }

  @Test
  void testReadThroughAndWriteBehindStrategy() {
	assertNotNull(app);
    app.useReadThroughAndWriteBehindStrategy();
  }

  @Test
  void testCacheAsideStrategy() {
	assertNotNull(app);
    app.useCacheAsideStategy();
  }
}
