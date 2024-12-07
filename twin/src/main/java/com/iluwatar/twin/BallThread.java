package com.iluwatar.twin;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is a UI thread for drawing the {@link BallItem}, and provides methods to pause, resume,
 * and stop the thread. It holds the reference of {@link BallItem} to delegate drawing and movement tasks.
 */
@Slf4j
public class BallThread extends Thread {

  @Setter
  private BallItem twin;

  private volatile boolean isSuspended;
  private volatile boolean isRunning = true;

  /**
   * Main execution logic for the thread.
   */
  @Override
  public void run() {
    try {
      while (isRunning) {
        synchronized (this) {
          while (isSuspended) {
            LOGGER.info("BallThread is suspended.");
            wait(); // Wait until notified
          }
        }
        // Perform drawing and movement tasks
        twin.draw();
        twin.move();

        Thread.sleep(250); // Pause briefly between actions
      }
    } catch (InterruptedException e) {
      LOGGER.warn("BallThread interrupted, shutting down.");
      Thread.currentThread().interrupt(); // Preserve interrupted status
    } finally {
      LOGGER.info("BallThread has stopped.");
    }
  }

  /**
   * Suspends the thread's execution.
   */
  public synchronized void pauseThread() {
    isSuspended = true;
    LOGGER.info("BallThread paused.");
  }

  /**
   * Resumes the thread's execution.
   */
  public synchronized void resumeThread() {
    isSuspended = false;
    notify(); // Notify the waiting thread
    LOGGER.info("BallThread resumed.");
  }

  /**
   * Stops the thread's execution.
   */
  public void stopThread() {
    isRunning = false;
    // Notify the thread in case it is waiting
    synchronized (this) {
      isSuspended = false;
      notify(); // Notify any waiting thread to allow graceful shutdown
    }
    LOGGER.info("BallThread stopping.");
  }
}