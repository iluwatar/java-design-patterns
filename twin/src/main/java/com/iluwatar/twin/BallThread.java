/*
 * This project is licensed under the MIT license. For more information see the LICENSE.md file.
 */
package com.iluwatar.twin;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is a UI thread for drawing the {@link BallItem}, and provides methods for suspension
 * and resumption. It holds a reference to a {@link BallItem} to delegate the drawing task.
 * This implementation uses wait/notify to avoid busy-waiting.
 */
@Slf4j
public class BallThread extends Thread {

  @Setter
  private BallItem twin;

  private final Object lock = new Object();
  private volatile boolean isSuspended;
  private volatile boolean isRunning = true;

  /**
   * The main execution method for the thread. It continuously draws and moves the twin object
   * unless suspended or stopped.
   */
  @Override
  public void run() {
    while (isRunning) {
      try {
        synchronized (lock) {
          // Wait while the thread is suspended.
          while (isSuspended) {
            lock.wait();
          }
        }

        // Check if the thread should terminate after being woken up.
        if (!isRunning) {
          break;
        }

        // Perform the work.
        twin.draw();
        twin.move();

        // Control the animation speed.
        Thread.sleep(250);
      } catch (InterruptedException e) {
        // Restore the interrupted status and exit gracefully.
        Thread.currentThread().interrupt();
        LOGGER.error("BallThread was interrupted", e);
        isRunning = false;
      }
    }
  }

  /**
   * Suspends the thread's execution. The thread will pause its work and wait until resumed.
   */
  public void suspendMe() {
    isSuspended = true;
    LOGGER.info("Suspending BallThread.");
  }

  /**
   * Resumes the thread's execution. It notifies the waiting thread to continue its work.
   */
  public void resumeMe() {
    synchronized (lock) {
      isSuspended = false;
      // Wake up the waiting thread.
      lock.notifyAll();
    }
    LOGGER.info("Resuming BallThread.");
  }

  /**
   * Stops the thread's execution permanently. It ensures that if the thread is suspended,
   * it will be woken up to terminate gracefully.
   */
  public void stopMe() {
    isRunning = false;
    // Wake up the thread if it is suspended so it can terminate.
    resumeMe();
  }
}