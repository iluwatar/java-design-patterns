package com.iluwatar.activeobject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ActiveCreature class is the base of the active object example.
 * @author Noam Greenshtain
 *
 */
public abstract class ActiveCreature {
  
  private final Logger logger = LoggerFactory.getLogger(ActiveCreature.class.getName());

  private BlockingQueue<Runnable> requests;
  
  private String name;
  
  private Thread thread;

  /**
   * Constructor and initialization.
   */
  public ActiveCreature(String name) {
    this.name = name;
    this.requests = new LinkedBlockingQueue<Runnable>();
    thread = new Thread(new Runnable() {
        @Override
        public void run() {
          boolean infinite = true;
          while (infinite) {
            try {
              requests.take().run();
            } catch (InterruptedException e) { 
              logger.error(e.getMessage());
              infinite = false;
              Thread.currentThread().interrupt();
            }
          }
        }
      }
    );
    thread.start();
  }

  /**
   * Eats the porridge.
   * @throws InterruptedException due to firing a new Runnable.
   */
  public void eat() throws InterruptedException {
    requests.put(new Runnable() {
        @Override
        public void run() { 
          logger.info("{} is eating!",name());
          logger.info("{} has finished eating!",name());
        }
      }
    );
  }

  /**
   * Roam in the wastelands.
   * @throws InterruptedException due to firing a new Runnable.
   */
  public void roam() throws InterruptedException {
    requests.put(new Runnable() {
        @Override
        public void run() { 
          logger.info("{} has started to roam in the wastelands.",name());
        }
      }
    );
  }
  
  public String name() {
    return this.name;
  }
}
