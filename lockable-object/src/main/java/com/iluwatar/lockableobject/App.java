package com.iluwatar.lockableobject;

import com.iluwatar.lockableobject.domain.Creature;
import com.iluwatar.lockableobject.domain.Elf;
import com.iluwatar.lockableobject.domain.Feind;
import com.iluwatar.lockableobject.domain.Human;
import com.iluwatar.lockableobject.domain.Orc;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Lockable Object pattern is a concurrency pattern. Instead of using the "synchronized" word
 * upon the methods to be synchronized, the object which implements the Lockable interface handles
 * the request.
 *
 * @author Noam Greenshtain
 */
public class App implements Runnable {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class.getName());
  private static final int WAIT_TIME = 2;
  private static final int WORKERS = 2;
  private static final int MULTIPLICATION_FACTOR = 3;

  /**
   * main method.
   *
   * @param args as arguments for the main method.
   */
  public static void main(String[] args) {
    var app = new App();
    app.run();
  }

  @Override
  public void run() {
    Lockable sword = new SwordOfAragorn();
    List<Creature> creatures = new ArrayList<>();
    for (int i = 0; i < WORKERS; i++) {
      creatures.add(new Elf(String.format("Elf %s", i)));
      creatures.add(new Orc(String.format("Orc %s", i)));
      creatures.add(new Human(String.format("Human %s", i)));
    }
    int totalFiends = WORKERS * MULTIPLICATION_FACTOR;
    ExecutorService service = Executors.newFixedThreadPool(totalFiends);
    for (int i = 0; i < totalFiends; i = i + MULTIPLICATION_FACTOR) {
      service.submit(new Feind(creatures.get(i), sword));
      service.submit(new Feind(creatures.get(i + 1), sword));
      service.submit(new Feind(creatures.get(i + 2), sword));
    }
    try {
      if (!service.awaitTermination(WAIT_TIME, TimeUnit.SECONDS)) {
        LOGGER.info("The master of the sword is now {}.", sword.getLocker().getName());
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    } finally {
      service.shutdown();
    }
  }
}
