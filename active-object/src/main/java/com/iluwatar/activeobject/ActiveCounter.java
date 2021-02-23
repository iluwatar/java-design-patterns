package com.iluwatar.activeobject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * ActiveCounter class is the active object example.
 * @author Noam Greenshtain
 *
 */
public class ActiveCounter {
  
  private final static Logger LOGGER = LoggerFactory.getLogger(ActiveCounter.class.getName());

  private Integer val;

  private BlockingQueue<Runnable> requests;

  public ActiveCounter() {
    this.requests = new LinkedBlockingQueue<Runnable>();
    this.val = 0;
    new Thread (new Runnable() {
        @Override
        public void run() {
          while (true) {
            try {
              requests.take().run();
            } catch (InterruptedException e) {   
              
            }
          }
        }
      }
    ).start();
  }
  /**
   * Zerorizes val property.
   * @throws InterruptedException
   */
  public void zerorize() throws InterruptedException {
    requests.put(new Runnable() {
        @Override
        public void run() { 
          val = 0; 
          LOGGER.info("val has been set to 0.");
        }
      }
    );
  }
  /**
   * Incremented val property by one.
   * @throws InterruptedException
   */
  public void incremenet() throws InterruptedException {
    requests.put(new Runnable() {
        @Override
        public void run() { 
          val++; 
          LOGGER.info("val has been incremented.");
        }
      }
    );
  }
  /**
   * Logging the current value of val property.
   * @throws InterruptedException
   */
  public void printVal() throws InterruptedException {
    requests.put(new Runnable() {
        @Override
        public void run() { 
          LOGGER.info(val.toString());
        }
      }
    );
  }   
}