/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.twin;

import static java.lang.Thread.sleep;
import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

/** BallThreadTest */
class BallThreadTest {

  /** Verify if the {@link BallThread} can be suspended */
  @Test
  void testSuspend() {
    assertTimeout(
        ofMillis(5000),
        () -> {
          final var ballThread = new BallThread();
          final var ballItem = mock(BallItem.class);
          ballThread.setTwin(ballItem);

          // FIXED: Use run() instead of start() for new architecture
          ballThread.run();
          sleep(200);
          verify(ballItem, atLeastOnce()).draw();
          verify(ballItem, atLeastOnce()).move();

          ballThread.suspendMe();
          reset(ballItem); // Reset mock to track suspension behavior
          sleep(1000);

          ballThread.stopMe();
          // FIXED: Use awaitShutdown() instead of join()
          ballThread.awaitShutdown(3, TimeUnit.SECONDS);
          verifyNoMoreInteractions(ballItem);
        });
  }

  /** Verify if the {@link BallThread} can be resumed */
  @Test
  void testResume() {
    assertTimeout(
        ofMillis(5000),
        () -> {
          final var ballThread = new BallThread();
          final var ballItem = mock(BallItem.class);
          ballThread.setTwin(ballItem);

          ballThread.suspendMe(); // Suspend before starting
          // 🚀 FIXED: Use run() instead of start()
          ballThread.run();
          sleep(1000);
          verifyNoMoreInteractions(ballItem); // Should be no activity while suspended

          ballThread.resumeMe();
          sleep(300);
          verify(ballItem, atLeastOnce()).draw();
          verify(ballItem, atLeastOnce()).move();

          ballThread.stopMe();
          // FIXED: Use awaitShutdown() instead of join()
          ballThread.awaitShutdown(3, TimeUnit.SECONDS);
        });
  }

  /**
   * UPDATED: Test graceful shutdown instead of interrupt (New architecture doesn't use
   * Thread.interrupt())
   */
  @Test
  void testGracefulShutdown() {
    assertTimeout(
        ofMillis(5000),
        () -> {
          final var ballThread = new BallThread();
          final var ballItem = mock(BallItem.class);
          ballThread.setTwin(ballItem);

          // FIXED: Use run() instead of start()
          ballThread.run();
          sleep(200); // Let it run briefly

          verify(ballItem, atLeastOnce()).draw();
          verify(ballItem, atLeastOnce()).move();

          // NEW: Test graceful shutdown instead of interrupt
          ballThread.stopMe();
          boolean shutdownCompleted = ballThread.awaitShutdown(3, TimeUnit.SECONDS);

          // Verify shutdown completed successfully
          if (!shutdownCompleted) {
            throw new RuntimeException("Shutdown did not complete within timeout");
          }
        });
  }
}
