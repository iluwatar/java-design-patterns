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
  
  private final Logger logger = LoggerFactory.getLogger(ActiveCounter.class.getName());

  private Integer val;

  private BlockingQueue<Runnable> requests;
  
  private Thread thread;

  /**
   * Constructor and initialization.
   */
  public ActiveCounter() {
    this.requests = new LinkedBlockingQueue<Runnable>();
    this.val = 0;
    thread = new Thread(new Runnable() {
        @Override
        public void run() {
          while (true) {
            try {
              requests.take().run();
            } catch (InterruptedException e) { 
              logger.error(e.getMessage());
            }
          }
        }
      }
    );
    thread.start();
  }

  /**
   * Zerorizes val property.
   * @throws InterruptedException due to firing a new Runnable.
   */
  public void zerorize() throws InterruptedException {
    requests.put(new Runnable() {
        @Override
        public void run() { 
          val = 0; 
          logger.info("val has been set to 0.");
        }
      }
    );
  }

  /**
   * Incremented val property by one.
   * @throws InterruptedException due to firing a new Runnable.
   */
  public void incremenet() throws InterruptedException {
    requests.put(new Runnable() {
        @Override
        public void run() { 
          val++; 
          logger.info("val has been incremented.");
        }
      }
    );
  }

  /**
   * Logging the current value of val property.
   * @throws InterruptedException due to firing a new Runnable.
   */
  public void printVal() throws InterruptedException {
    requests.put(new Runnable() {
        @Override
        public void run() { 
          logger.info(val.toString());
        }
      }
    );
  }   
}
