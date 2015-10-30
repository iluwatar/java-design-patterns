package com.iluwatar.leaderfollower;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class HandleSet {
  
  private BlockingQueue<Work> queue = new ArrayBlockingQueue<>(100);
  
  public void fireEvent(Work input) throws InterruptedException {
    queue.put(input);
  }
  
  public Work getPayLoad() throws InterruptedException {
    //return queue.poll(3, TimeUnit.SECONDS);
    return queue.take();
  }
  

}
