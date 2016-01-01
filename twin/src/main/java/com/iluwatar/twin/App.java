package com.iluwatar.twin;

/**
 * Twin pattern is a design pattern which provides a standard solution to simulate multiple
 * inheritance in java.
 * <p>
 * In this example, the essence of the Twin pattern is the {@link BallItem} class and
 * {@link BallThread} class represent the twin objects to coordinate with each other(via the twin
 * reference) like a single class inheriting from {@link GameItem} and {@link Thread}.
 */

public class App {

  /**
   * Program entry point
   * 
   * @param args command line args
   */
  public static void main(String[] args) throws Exception {

    BallItem ballItem = new BallItem();
    BallThread ballThread = new BallThread();

    ballItem.setTwin(ballThread);
    ballThread.setTwin(ballItem);

    ballThread.start();

    waiting();

    ballItem.click();

    waiting();

    ballItem.click();

    waiting();

    // exit
    ballThread.stopMe();
  }

  private static void waiting() throws Exception {
    Thread.sleep(750);
  }
}
