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
    final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("AdvancedMapping");

    final ProductDao productDao = new ProductDao(emf);
    final ProductService productService = new ProductService(emf);

    // clear table
    productDao.deleteAll();
    // create some products in database
    final double applePrice = 1.5;
    final int appleNum = 10;
    final double banaPrice = 2.1;
    final int banaNum = 20;
    final Product apple = new Product("apple", "The apple is very delicious!",
        applePrice, appleNum);
    final Product banana = new Product("banana", "The banana is fresh!",
        banaPrice, banaNum);
    // insert into database
    productDao.save(apple);
    productDao.save(banana);
    // save ids
    final long id1 = apple.getId();
    // create threads
    final int delay1 = 1000;
    final int delay2 = 2000;
    final int buyAmount1 = 2;
    final int buyAmount2 = 4;
    // for apple
    // With lock, thread can't override each other
    final Thread thread1 = new Thread(()  -> {
      try {
        productService.buy(id1, buyAmount1,
            delay1, true);
      } catch (NotEnoughException
          | NegativeAmountException
          | NotExistException e) {
        if (LOGGER.isErrorEnabled()) {
          LOGGER.error(e.getMessage());
        }
      }
    });
    final Thread thread2 = new Thread(() -> {
      try {
        productService.buy(id1, buyAmount2,
            delay2, true);
      } catch (NotEnoughException
          | NegativeAmountException
          | NotExistException e) {
        if (LOGGER.isErrorEnabled()) {
          LOGGER.error(e.getMessage());
        }
      }
    });

    // start threads
    thread1.start();
    thread2.start();

    // wait for threads to finish
    try {
      thread1.join();
      thread2.join();

      final Optional<Product> result1 = productDao.get(id1);

      if (result1.isPresent()) {
        final Product appleRes = result1.get();

        if (LOGGER.isInfoEnabled()) {
          LOGGER.info("There are {} apples left.",
              appleRes.getAmountInStock());
        }
      }

      emf.close();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * private constructor so class App can't be instantiated.
   */
  private App() { }
}
