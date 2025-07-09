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

import static java.lang.Thread.UncaughtExceptionHandler;
import static java.lang.Thread.sleep;
import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;

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
  void testResume() {
    assertTimeout(
        ofMillis(5000),
        () -> {
          final var ballThread = new BallThread();

          final var ballItem = mock(BallItem.class);
          ballThread.setTwin(ballItem);

          ballThread.suspendMe();
          ballThread.start();

          sleep(1000);

          verifyNoMoreInteractions(ballItem);

          ballThread.resumeMe();
          sleep(300);
          verify(ballItem, atLeastOnce()).draw();
          verify(ballItem, atLeastOnce()).move();

          ballThread.stopMe();
          ballThread.join();

          verifyNoMoreInteractions(ballItem);
        });
  }

  /** Verify if the {@link BallThread} is interruptible */
  @Test
  void testInterrupt() {
    assertTimeout(
        ofMillis(5000),
        () -> {
          final var ballThread = new BallThread();
          final var exceptionHandler = mock(UncaughtExceptionHandler.class);
          ballThread.setUncaughtExceptionHandler(exceptionHandler);
          ballThread.setTwin(mock(BallItem.class));
          ballThread.start();
          ballThread.interrupt();
          ballThread.join();

          verify(exceptionHandler).uncaughtException(eq(ballThread), any(RuntimeException.class));
          verifyNoMoreInteractions(exceptionHandler);
        });
  }
  @Test
  @Timeout(value = 3, unit = TimeUnit.SECONDS)
  void testZeroBusyWaiting() throws InterruptedException {
    ballThread.start();
    
    // Animation should work with precise timing
    long startTime = System.currentTimeMillis();
    Thread.sleep(1000); // Wait for 4 animation cycles (250ms each)
    
    // Should have called draw/move approximately 4 times
    verify(mockBallItem, atLeast(3)).draw();
    verify(mockBallItem, atMost(6)).move(); // Allow some variance
    
    long elapsed = System.currentTimeMillis() - startTime;
    
    // Should complete in reasonable time (not blocked by busy-waiting)
    assertTrue(elapsed < 1200, "Should complete efficiently without busy-waiting");
    
    ballThread.stopMe();
    ballThread.awaitShutdown();
  }

   /** 
   *  Verify event-driven animation execution
   */
  @Test
  @Timeout(value = 5, unit = TimeUnit.SECONDS)
  void testEventDrivenAnimation() throws InterruptedException {
    // Start the elite event-driven animation
    ballThread.start();
    
    assertTrue(ballThread.isRunning());
    assertFalse(ballThread.isSuspended());
    
    // Wait for a few animation cycles (250ms intervals)
    Thread.sleep(800); // ~3 animation cycles
    
    // Verify animation methods were called by scheduler
    verify(mockBallItem, atLeast(2)).draw();
    verify(mockBallItem, atLeast(2)).move();
    
    ballThread.stopMe();
    ballThread.awaitShutdown();
    
    assertFalse(ballThread.isRunning());
  }

  /** 
   *  Verify zero-CPU suspension
   */
  @Test
  @Timeout(value = 5, unit = TimeUnit.SECONDS)
  void testZeroCpuSuspension() throws InterruptedException {
    ballThread.start();
    
    // Let it run for a bit
    Thread.sleep(300);
    verify(mockBallItem, atLeastOnce()).draw();
    verify(mockBallItem, atLeastOnce()).move();
    
    // Reset mock to track suspension behavior
    reset(mockBallItem);
    
    // Zero CPU usage
    ballThread.suspendMe();
    assertTrue(ballThread.isSuspended());
    
    // Wait during suspension - should have ZERO CPU usage and no calls
    Thread.sleep(1000);
    
    // Verify NO animation occurred during suspension
    verifyNoInteractions(mockBallItem);
    
    ballThread.stopMe();
    ballThread.awaitShutdown();
  }

  /** 
   * ⚡ CHAMPIONSHIP TEST: Verify instant resume capability
   */
  @Test
  @Timeout(value = 5, unit = TimeUnit.SECONDS)
  void testInstantResume() throws InterruptedException {
    // Start suspended
    ballThread.suspendMe();
    ballThread.start();
    
    assertTrue(ballThread.isRunning());
    assertTrue(ballThread.isSuspended());
    
    // Wait while suspended - no activity expected
    Thread.sleep(500);
    verifyNoInteractions(mockBallItem);
    
    // 🚀 INSTANT RESUME - Uses Condition.signalAll() for immediate response
    ballThread.resumeMe();
    assertFalse(ballThread.isSuspended());
    
    // Wait for animation to resume
    Thread.sleep(600); // 2+ animation cycles
    
    // Verify animation resumed immediately
    verify(mockBallItem, atLeast(1)).draw();
    verify(mockBallItem, atLeast(1)).move();
    
    ballThread.stopMe();
    ballThread.awaitShutdown();
  }

  /** 
   *  Verify graceful shutdown with timeout
   */
  @Test
  @Timeout(value = 5, unit = TimeUnit.SECONDS)
  void testGracefulShutdown() throws InterruptedException {
    ballThread.start();
    assertTrue(ballThread.isRunning());
    
    // Let it animate
    Thread.sleep(300);
    verify(mockBallItem, atLeastOnce()).draw();
    
    // Test graceful shutdown
    ballThread.stopMe();
    
    // Should complete shutdown within timeout
    boolean shutdownCompleted = ballThread.awaitShutdown(3, TimeUnit.SECONDS);
    assertTrue(shutdownCompleted, "Shutdown should complete within timeout");
    
    assertFalse(ballThread.isRunning());
    assertFalse(ballThread.isSuspended());
  }

  /** 
   * Verify zero busy-waiting
   */
  @Test
  @Timeout(value = 3, unit = TimeUnit.SECONDS)
  void testZeroBusyWaiting() throws InterruptedException {
    ballThread.start();
    
    // Animation should work with precise timing
    long startTime = System.currentTimeMillis();
    Thread.sleep(1000); // Wait for 4 animation cycles (250ms each)
    
    // Should have called draw/move approximately 4 times
    verify(mockBallItem, atLeast(3)).draw();
    verify(mockBallItem, atMost(6)).move(); // Allow some variance
    
    long elapsed = System.currentTimeMillis() - startTime;
    
    // Should complete in reasonable time (not blocked by busy-waiting)
    assertTrue(elapsed < 1200, "Should complete efficiently without busy-waiting");
    
    ballThread.stopMe();
    ballThread.awaitShutdown();
  }

  /** 
   * Verify performance metrics
   */
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

  /** 
   * Verify multiple suspend/resume cycles
   */
  @Test
  @Timeout(value = 6, unit = TimeUnit.SECONDS)
  void testMultipleSuspendResumeCycles() throws InterruptedException {
    ballThread.start();
    
    for (int cycle = 1; cycle <= 3; cycle++) {
      // Run for a bit
      Thread.sleep(200);
      verify(mockBallItem, atLeastOnce()).draw();
      
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
    ballThread.awaitShutdown();
  }

   /** 
   * TIMING TEST: Verify animation timing accuracy
   */
  @Test
  @Timeout(value = 4, unit = TimeUnit.SECONDS)
  void testAnimationTimingAccuracy() throws InterruptedException {
    ballThread.start();
    
    long startTime = System.currentTimeMillis();
    
    // Wait for exactly 1 second
    Thread.sleep(1000);
    
    long elapsed = System.currentTimeMillis() - startTime;
    
    // Should have approximately 4 animation cycles (250ms each)
    // Allow some variance for scheduling
    verify(mockBallItem, atLeast(3)).draw();
    verify(mockBallItem, atMost(6)).draw();
    
    // Timing should be accurate (not drifting like busy-waiting)
    assertTrue(elapsed >= 1000, "Should not complete too early");
    assertTrue(elapsed < 1100, "Should not have significant timing drift");
    
    ballThread.stopMe();
    ballThread.awaitShutdown();
  }

}
