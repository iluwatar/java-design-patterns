
package com.iluwatar.twin;

/**
 * This class is a UI thread for drawing the {@link BallItem}, and provide the method for suspend
 * and resume. It hold the reference of {@link BallItem} to delegate the draw task.
 * 
 */

public class BallThread extends Thread {

  private BallItem twin;

  private volatile boolean isSuspended;

  private volatile boolean isRunning = true;

  public void setTwin(BallItem twin) {
    this.twin = twin;
  }

  /**
   * Run the thread
   */
  public void run() {

    while (isRunning) {
      if (!isSuspended) {
        twin.draw();
        twin.move();
      }
      try {
        Thread.sleep(250);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public void suspendMe() {
    isSuspended = true;
    System.out.println("Begin to suspend BallThread");
  }

  public void resumeMe() {
    isSuspended = false;
    System.out.println("Begin to resume BallThread");
  }

  public void stopMe() {
    this.isRunning = false;
    this.isSuspended = true;
  }
}

