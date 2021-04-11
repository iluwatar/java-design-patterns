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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Lockable Object pattern is a concurrency pattern. Instead of using the "synchronized" word
 * upon the methods to be synchronized, the object which implements the Lockable interface handles
 * the request.
 *
 * @author Noam Greenshtain
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class.getName());
  private static final int WAIT_TIME = 2000;
  private static final int WORKERS = 2;
  private static final int MULTIPLICATION_FACTOR = 3;

  /**
   * main method.
   *
   * @param args as arguments for the main method.
   * @throws InterruptedException in case of interruption for one of the threads.
   */
  public static void main(String[] args) throws InterruptedException {
    Lockable sword = new SwordOfAragorn();
    List<Creature> creatures = new ArrayList<>();
    for (int i = 0; i < WORKERS; i++) {
      creatures.add(new Elf(String.format("Elf %s", i)));
      creatures.add(new Orc(String.format("Orc %s", i)));
      creatures.add(new Human(String.format("Human %s", i)));
    }
    int totalFiends = WORKERS * MULTIPLICATION_FACTOR;
    ExecutorService service = Executors.newFixedThreadPool(totalFiends);
    for (int i = 0; i < totalFiends; i++) {
      service.submit(new Feind(creatures.get(i), sword));
      service.submit(new Feind(creatures.get(++i), sword));
      service.submit(new Feind(creatures.get(++i), sword));
    }
    Thread.sleep(WAIT_TIME);
    LOGGER.info("The master of the sword is now {}.", sword.getLocker().getName());
    service.shutdown();
  }
}
