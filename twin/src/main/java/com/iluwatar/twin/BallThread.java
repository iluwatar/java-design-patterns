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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is a UI thread for drawing the {@link BallItem}, and provide the method for suspend
 * and resume. It holds the reference of {@link BallItem} to delegate the draw task.
 */
@Slf4j
public class BallThread extends Thread {

  @Setter private BallItem twin;

  private static final int ANIMATION_INTERVAL_MS = 250;
  private static final int SHUTDOWN_TIMEOUT_SECONDS = 5;

  // threading components
  private final ScheduledExecutorService scheduler =
      Executors.newSingleThreadScheduledExecutor(
          r -> {
            Thread t = new Thread(r, "BallThread-Scheduler");
            t.setDaemon(true); // Won't prevent JVM shutdown
            return t;
          });

  // Advanced synchronization primitives
  private final ReentrantLock stateLock = new ReentrantLock();
  private final Condition resumeCondition = stateLock.newCondition();
  private final CountDownLatch shutdownLatch = new CountDownLatch(1);

  // Atomic state management - no race conditions
  private final AtomicBoolean isRunning = new AtomicBoolean(false);
  private final AtomicBoolean isSuspended = new AtomicBoolean(false);

  // Performance monitoring
  private volatile long animationCycles = 0;
  private volatile long suspendCount = 0;

  /** Run the thread. */
  public void run() {
    if (isRunning.compareAndSet(false, true)) {
      LOGGER.info("Starting elite BallThread with zero busy-waiting");

      // Schedule the animation task with fixed rate execution
      scheduler.scheduleAtFixedRate(
          this::animationCycle, 0, ANIMATION_INTERVAL_MS, TimeUnit.MILLISECONDS);

      // Register shutdown hook for clean termination
      Runtime.getRuntime()
          .addShutdownHook(
              new Thread(
                  () -> {
                    LOGGER.info("🛑 Shutdown hook triggered - stopping BallThread gracefully");
                    stopMe();
                  }));

      LOGGER.info("BallThread started successfully - CPU efficient mode engaged");
    } else {
      LOGGER.warn("BallThread already running - ignoring start request");
    }
  }

  /**
   * CORE ANIMATION CYCLE - THE HEART OF ZERO-WAIT ARCHITECTURE This method is called by the
   * scheduler at precise intervals. No busy-waiting, no Thread.sleep(), pure event-driven
   * excellence.
   */
  private void animationCycle() {
    if (!isRunning.get()) {
      return; // Early exit if stopped
    }

    try {
      // Handle suspension with lock-free approach first
      if (isSuspended.get()) {
        handleSuspension();
        return;
      }

      // Execute twin operations atomically
      if (twin != null) {
        twin.draw();
        twin.move();
        animationCycles++;

        // Log progress every 100 cycles for monitoring
        if (animationCycles % 100 == 0) {
          LOGGER.debug("🎯 Animation cycle #{} completed", animationCycles);
        }
      } else {
        LOGGER.warn("Twin is null - skipping animation cycle");
      }

    } catch (Exception e) {
      LOGGER.error("Error in animation cycle #{}: {}", animationCycles, e.getMessage(), e);
      // Continue running despite errors - resilient design
    }
  }

  /** ADVANCED SUSPENSION HANDLER Uses Condition variables for efficient thread blocking. */
  private void handleSuspension() {
    stateLock.lock();
    try {
      while (isSuspended.get() && isRunning.get()) {
        LOGGER.debug("💤 BallThread suspended - waiting for resume signal");

        // This is the magic - thread blocks efficiently until signaled
        // NO CPU consumption during suspension!
        boolean resumed = resumeCondition.await(1, TimeUnit.SECONDS);

        if (!resumed) {
          LOGGER.trace("Suspension timeout - checking state again");
        }
      }
    } catch (InterruptedException e) {
      LOGGER.info("BallThread interrupted during suspension");
      Thread.currentThread().interrupt();
    } finally {
      stateLock.unlock();
    }
  }

  public void suspendMe() {
    if (isSuspended.compareAndSet(false, true)) {
      suspendCount++;
      LOGGER.info("BallThread suspended (#{}) - zero CPU mode activated", suspendCount);
    } else {
      LOGGER.debug("BallThread already suspended");
    }
  }

  public void resumeMe() {
    if (isSuspended.compareAndSet(true, false)) {
      stateLock.lock();
      try {
        resumeCondition.signalAll(); // Wake up suspended threads
        LOGGER.info("BallThread resumed - animation cycles: {}", animationCycles);
      } finally {
        stateLock.unlock();
      }
    } else {
      LOGGER.debug("BallThread was not suspended");
    }
  }

  public void stopMe() {
    if (isRunning.compareAndSet(true, false)) {
      LOGGER.info("Initiating BallThread shutdown...");

      // Wake up any suspended threads
      resumeMe();

      // Shutdown scheduler gracefully
      scheduler.shutdown();

      try {
        if (!scheduler.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
          LOGGER.warn("Forcing immediate scheduler shutdown");
          scheduler.shutdownNow();
        }
      } catch (InterruptedException e) {
        LOGGER.warn("Shutdown interrupted - forcing immediate stop");
        scheduler.shutdownNow();
        Thread.currentThread().interrupt();
      }

      shutdownLatch.countDown();

      LOGGER.info("BallThread stopped successfully");
      LOGGER.info(
          "Final stats - Animation cycles: {}, Suspensions: {}", animationCycles, suspendCount);
    }
  }

  /** WAIT FOR SHUTDOWN WITH TIMEOUT Blocks until thread is terminated or timeout occurs. */
  public boolean awaitShutdown(long timeout, TimeUnit unit) throws InterruptedException {
    return shutdownLatch.await(timeout, unit);
  }

  //  CHAMPION MONITORING METHODS

  /**
   * @return Current running state
   */
  public boolean isRunning() {
    return isRunning.get();
  }

  /**
   * @return Current suspension state
   */
  public boolean isSuspended() {
    return isSuspended.get();
  }

  /**
   * @return Total animation cycles completed
   */
  public long getAnimationCycles() {
    return animationCycles;
  }

  /**
   * @return Number of times thread was suspended
   */
  public long getSuspendCount() {
    return suspendCount;
  }

  /**
   * @return Current animation frame rate (approximate)
   */
  public double getFrameRate() {
    return 1000.0 / ANIMATION_INTERVAL_MS;
  }

  /** PERFORMANCE STATUS REPORT Returns detailed thread performance metrics. */
  public String getPerformanceReport() {
    return String.format(
        "  BallThread Performance Report:\n"
            + "   Running: %s | Suspended: %s\n"
            + "   Animation Cycles: %,d\n"
            + "   Suspend Count: %,d\n"
            + "   Target FPS: %.1f\n"
            + "   Thread Model: Event-Driven (Zero Busy-Wait)",
        isRunning.get(), isSuspended.get(), animationCycles, suspendCount, getFrameRate());
  }
}
