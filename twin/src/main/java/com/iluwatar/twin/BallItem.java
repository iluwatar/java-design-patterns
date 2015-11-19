
package com.iluwatar.twin;

/**
 * This class represents a Ball which extends {@link GameItem} and implements the logic for ball
 * item, like move and draw. It hold a reference of {@link BallThread} to delegate the suspend and
 * resume task.
 */
public class BallItem extends GameItem {

  private boolean isSuspended = false;

  private BallThread twin;

  public void setTwin(BallThread twin) {
    this.twin = twin;
  }

  @Override
  public void doDraw() {

    System.out.println("doDraw");
  }

  public void move() {
    System.out.println("move");
  }

  @Override
  public void click() {

    isSuspended = !isSuspended;

    if (isSuspended) {
      twin.suspendMe();
    } else {
      twin.resumeMe();
    }
  }
}

