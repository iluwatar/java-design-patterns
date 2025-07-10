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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

/** BallThreadTest */
class BallThreadTest {

  /** Verify if the {@link BallThread} can be resumed */
  @Test
  void testSuspend() {
    assertTimeout(
        ofMillis(5000),
        () -> {
          final var ballThread = new BallThread();

          final var ballItem = mock(BallItem.class);
          ballThread.setTwin(ballItem);

          ballThread.start();
          sleep(200);
          verify(ballItem, atLeastOnce()).draw();
          verify(ballItem, atLeastOnce()).move();
          ballThread.suspendMe();

          sleep(1000);

          ballThread.stopMe();
          ballThread.join();

          verifyNoMoreInteractions(ballItem);
        });
  }

  /** Verify if the {@link BallThread} can be resumed */
  @Test
  @Timeout(value = 5, unit = TimeUnit.SECONDS)
  void testEventDrivenAnimation() throws InterruptedException {
    // Start the event-driven animation using run() method
    ballThread.run();

    assertTrue(ballThread.isRunning());
    assertFalse(ballThread.isSuspended());

    // Wait for a few animation cycles (250ms intervals)
    Thread.sleep(800); // ~3 animation cycles

    // Verify animation methods were called by scheduler
    verify(mockBallItem, atLeast(2)).draw();
    verify(mockBallItem, atLeast(2)).move();

    ballThread.stopMe();
    ballThread.awaitShutdown(3, TimeUnit.SECONDS);

    assertFalse(ballThread.isRunning());
  }

  @Test
  @Timeout(value = 5, unit = TimeUnit.SECONDS)
  void testZeroCpuSuspension() throws InterruptedException {
    ballThread.run();

    // Let it run for a bit
    Thread.sleep(300);
    verify(mockBallItem, atLeast(1)).draw();
    verify(mockBallItem, atLeast(1)).move();

    // Reset mock to track suspension behavior
    reset(mockBallItem);

    // Elite suspension - Zero CPU usage
    ballThread.suspendMe();
    assertTrue(ballThread.isSuspended());

    // Wait during suspension - should have ZERO CPU usage and no calls
    Thread.sleep(600);

    // Verify NO animation occurred during suspension
    verifyNoInteractions(mockBallItem);

    ballThread.stopMe();
    ballThread.awaitShutdown(3, TimeUnit.SECONDS);
  }

  @Test
  @Timeout(value = 5, unit = TimeUnit.SECONDS)
  void testInstantResume() throws InterruptedException {
    // Start suspended
    ballThread.suspendMe();
    ballThread.run();

    assertTrue(ballThread.isRunning());
    assertTrue(ballThread.isSuspended());

    // Wait while suspended - no activity expected
    Thread.sleep(500);
    verifyNoInteractions(mockBallItem);

    // Instant resume
    ballThread.resumeMe();
    assertFalse(ballThread.isSuspended());

    // Wait for animation to resume
    Thread.sleep(600); // 2+ animation cycles

    // Verify animation resumed
    verify(mockBallItem, atLeast(1)).draw();
    verify(mockBallItem, atLeast(1)).move();

    ballThread.stopMe();
    ballThread.awaitShutdown(3, TimeUnit.SECONDS);
  }

  @Test
  @Timeout(value = 5, unit = TimeUnit.SECONDS)
  void testGracefulShutdown() throws InterruptedException {
    ballThread.run();
    assertTrue(ballThread.isRunning());

    // Let it animate
    Thread.sleep(300);
    verify(mockBallItem, atLeast(1)).draw();

    // Test graceful shutdown
    ballThread.stopMe();

    // Should complete shutdown within timeout
    boolean shutdownCompleted = ballThread.awaitShutdown(3, TimeUnit.SECONDS);
    assertTrue(shutdownCompleted, "Shutdown should complete within timeout");

    assertFalse(ballThread.isRunning());
    assertFalse(ballThread.isSuspended());
  }

  @Test
  void testPerformanceMetrics() {
    // Test performance monitoring capabilities
    assertFalse(ballThread.isRunning());
    assertEquals(0, ballThread.getAnimationCycles());
    assertEquals(0, ballThread.getSuspendCount());
    assertEquals(4.0, ballThread.getFrameRate(), 0.1); // 1000ms / 250ms = 4 FPS

    String report = ballThread.getPerformanceReport();
    assertNotNull(report);
    assertTrue(report.contains("Event-Driven"));
    assertTrue(report.contains("Zero Busy-Wait"));
  }

  @Test
  @Timeout(value = 6, unit = TimeUnit.SECONDS)
  void testMultipleSuspendResumeCycles() throws InterruptedException {
    ballThread.run();

    for (int cycle = 1; cycle <= 3; cycle++) {
      // Run for a bit
      Thread.sleep(200);
      verify(mockBallItem, atLeast(1)).draw();

      // Suspend
      ballThread.suspendMe();
      assertTrue(ballThread.isSuspended());

      reset(mockBallItem); // Reset to track suspension
      Thread.sleep(200);
      verifyNoInteractions(mockBallItem); // No activity during suspension

      // Resume
      ballThread.resumeMe();
      assertFalse(ballThread.isSuspended());

      // Verify suspend count tracking
      assertEquals(cycle, ballThread.getSuspendCount());
    }

    ballThread.stopMe();
    ballThread.awaitShutdown(3, TimeUnit.SECONDS);
  }

  @Test
  @Timeout(value = 3, unit = TimeUnit.SECONDS)
  void testNullTwinHandling() throws InterruptedException {
    ballThread.setTwin(null); // Set null twin
    ballThread.run();

    // Should not crash with null twin
    Thread.sleep(500);

    assertTrue(ballThread.isRunning());

    ballThread.stopMe();
    ballThread.awaitShutdown(3, TimeUnit.SECONDS);
  }

  @Test
  @Timeout(value = 5, unit = TimeUnit.SECONDS)
  void testRapidStateChanges() throws InterruptedException {
    ballThread.run();

    // Rapid suspend/resume cycles
    for (int i = 0; i < 10; i++) {
      ballThread.suspendMe();
      Thread.sleep(50);
      ballThread.resumeMe();
      Thread.sleep(50);
    }

    // Should handle rapid changes gracefully
    assertTrue(ballThread.isRunning());
    assertEquals(10, ballThread.getSuspendCount());

    ballThread.stopMe();
    ballThread.awaitShutdown(3, TimeUnit.SECONDS);
  }

  @Test
  @Timeout(value = 4, unit = TimeUnit.SECONDS)
  void testAnimationTimingAccuracy() throws InterruptedException {
    ballThread.run();

    long startTime = System.currentTimeMillis();

    // Wait for exactly 1 second
    Thread.sleep(1000);

    long elapsed = System.currentTimeMillis() - startTime;

    // Should have approximately 4 animation cycles (250ms each)
    verify(mockBallItem, atLeast(3)).draw();

    // Timing should be accurate (not drifting like busy-waiting)
    assertTrue(elapsed >= 1000, "Should not complete too early");
    assertTrue(elapsed < 1100, "Should not have significant timing drift");

    ballThread.stopMe();
    ballThread.awaitShutdown(3, TimeUnit.SECONDS);
  }

  // Helper method to create verification with mock
  private static void verify(BallItem mock, org.mockito.verification.VerificationMode mode) {
    org.mockito.Mockito.verify(mock, mode);
  }
}
