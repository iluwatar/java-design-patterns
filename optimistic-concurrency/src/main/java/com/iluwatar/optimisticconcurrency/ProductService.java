package com.iluwatar.optimisticconcurrency;

import java.util.Optional;

import javax.persistence.EntityManagerFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * Service layer for Product class.
 */
@Slf4j
public class ProductService {
  /**
   * The ProductDao for the ProductService to use.
   */
  private final ProductDao productDao;

  /**
   * message for not enough in stock.
   */
  static final String NOT_ENOUGH =
      "There are not enough products in stock!";
  /**
   * message for when buy amount is negative.
   */
  static final String NEGATIVE_AMOUNT =
      "Amount to buy should be more than zero!";
  /**
   * message for when product not found.
   */
  static final String NOT_EXIST =
      "Product doesn't exist!";

  /**
   * Construct an instance of ProductService.
   *
   * @param emf the entity manager factory for the ProductDao to use.
   */
  public ProductService(final EntityManagerFactory emf) {
    this.productDao = new ProductDao(emf);
  }

  /**
   * Buy product with the id.
   *
   * @param productId id of the product to buy.
   * @param amount    amount of the product to buy.
   * @param delay     time in millisecond for the thread to sleep
   *                  after fetching from database.<br>
   *                  This allows result to be reproducible.
   * @param useLock   whether to enable locking.<br>
   *                  This is used to show difference.
   */
  public void buy(final long productId, final int amount,
                  final int delay, final boolean useLock) {
    if (amount <= 0) {
      LOGGER.error(NEGATIVE_AMOUNT);
      return;
    }

    while (true) {
      Optional<Product> found = productDao.get(productId);
      if (found.isPresent()) {
        Product oldProduct = found.get();
        long oldId = oldProduct.getId();
        int oldVersion = oldProduct.getVersion();

        // clone
        Product newProduct = new Product(oldProduct);

        try {
          Thread.sleep(delay);
        } catch (InterruptedException e) {
          LOGGER.error(e.getMessage());
          Thread.currentThread().interrupt();
        }

        // make changes
        int remaining = oldProduct.getAmountInStock() - amount;
        if (remaining < 0) {
          LOGGER.info(NOT_ENOUGH);
          return;
        }
        newProduct.setAmountInStock(remaining);

        try {
          productDao.update(newProduct, oldId,
              oldVersion, useLock);
        } catch (OptimisticLockException e) {
          LOGGER.error(e.getMessage());
          LOGGER.error("(Thread-{}) Buy operation is not successful!",
              Thread.currentThread().getId());
          continue;
        }

        LOGGER.info("(Thread-{}) Buy operation is successful!%n",
            Thread.currentThread().getId());
        return;

      } else {
        LOGGER.error(NOT_EXIST);
        return;
      }
    }
  }
}
