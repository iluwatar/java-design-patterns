package com.iluwatar.leaderfollower;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * The {@link HandleSet} class. All work arrive here, workers receive work from here.
 * @author amit
 *
 */
public class HandleSet {
  
  private BlockingQueue<Work> queue = new ArrayBlockingQueue<>(100);
  
  public void fireEvent(Work input) throws InterruptedException {
    queue.put(input);
  }
  
  public Work getPayLoad() throws InterruptedException {
    return queue.take();
  }
  

}
