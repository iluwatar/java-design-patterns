package com.iluwatar.twin;

/**
 * Twin pattern is a design pattern which provides a standard solution to simulate multiple
 * inheritance in java.
 * 
 * <p>
 * In this example, there is a ball game, a ball needs to subclass {@link GameItem} which provide
 * some common method like draw and click. Moreover, it needs to subclass {@link Thread} as ball is
 * a moving item (we use {@link Thread} instead of implements {@link Runnable} for example only)
 * <p>
 * Threre is scenario, when user click the ball, the ball will stop, when user click it gain, it
 * will resume to move. We create two class, ons is {@link BallItem} which subclass {@link GameItem}
 * , another is {@link BallThread} which subclass {@link Thread}. These two object both hold a field
 * named "Twin" reference to another object. In {@link BallItem#click()}, it will invoke
 * {@link BallThread} to suspend or resume moving; in {@link BallThread#run()}, it will invoke
 * {@link BallItem} for drawing.
 * 
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

    ballThread.stopMe();
  }

  private static void waiting() throws Exception {
    Thread.sleep(2500);
  }
}
