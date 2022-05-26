package com.iluwatar.optimisticconcurrency;

import java.util.Optional;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lombok.extern.slf4j.Slf4j;


/**
 * Optimistic Concurrency pattern.
 * Increment version during every update.
 * Attempts to update object using old version fail
 */
@Slf4j
final class App {
  /**
   * Program main entry point.
   *
   * @param args program runtime arguments.
   */
  public static void main(final String[] args) {
    EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("AdvancedMapping");

    ProductDao productDao = new ProductDao(emf);
    ProductService productService = new ProductService(emf);

    // clear table
    productDao.deleteAll();
    // create some products in database
    final double applePrice = 1.5;
    final int appleNum = 10;
    final double banaPrice = 2.1;
    final int banaNum = 20;
    Product apple = new Product("apple", "The apple is very delicious!",
        applePrice, appleNum);
    Product banana = new Product("banana", "The banana is fresh!",
        banaPrice, banaNum);
    // insert into database
    productDao.save(apple);
    productDao.save(banana);
    // save ids
    final long id1 = apple.getId();
    final long id2 = banana.getId();
    // create threads
    final int delay1 = 1000;
    final int delay2 = 2000;
    final int buyAmount1 = 2;
    final int buyAmount2 = 4;
    // for apple
    // With lock, thread can't override each other
    final Thread t1 = new Thread(()  -> productService.buy(id1, buyAmount1,
        delay1, true));
    final Thread t2 = new Thread(() -> productService.buy(id1, buyAmount2,
        delay2, true));
    // for banana
    // Without lock, thread 4's update overrides thread 2's update
    final Thread t3 = new Thread(() -> productService.buy(id2, buyAmount1,
        delay1, false));
    final Thread t4 = new Thread(() -> productService.buy(id2, buyAmount2,
        delay2, false));

    // start threads
    t1.start();
    t2.start();
    t3.start();
    t4.start();

    // wait for threads to finish
    try {
      t1.join();
      t2.join();
      t3.join();
      t4.join();

      Optional<Product> result1 = productDao.get(id1);
      Optional<Product> result2 = productDao.get(id2);

      if (result1.isPresent() && result2.isPresent()) {
        apple = result1.get();
        banana = result2.get();

        LOGGER.info("There are {} apples left.",
            apple.getAmountInStock());
        LOGGER.info("There are {} bananas left.",
            banana.getAmountInStock());
      }

      emf.close();
    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  /**
   * private constructor so class App can't be instantiated.
   */
  private App() { }
}
