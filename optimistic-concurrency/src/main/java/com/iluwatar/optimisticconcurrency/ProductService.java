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
   * @throws NotEnoughException when not enough in stock.
   * @throws NegativeAmountException when buy in negative amount.
   * @throws NotExistException when product not found.
   */
  public void buy(final long productId, final int amount,
                  final int delay, final boolean useLock)
      throws NotEnoughException, NegativeAmountException, NotExistException {

    checkAmount(amount);

    while (true) {
      final Optional<Product> found = productDao.get(productId);
      if (found.isPresent()) {
        // sleep
        try {
          Thread.sleep(delay);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
        // declare
        final Product oldProduct = found.get();

        final int remaining = oldProduct.getAmountInStock() - amount;
        checkRemaining(remaining);
        // clone
        final Product newProduct = new Product(oldProduct);
        // make changes
        newProduct.setAmountInStock(remaining);

        try {
          productDao.update(newProduct, oldProduct.getId(),
              oldProduct.getVersion(), useLock);
        } catch (OptimisticLockException e) {
          if (LOGGER.isErrorEnabled()) {
            LOGGER.error(e.getMessage());
            LOGGER.error("(Thread-{}) Buy operation is not successful!",
                Thread.currentThread().getId());
          }
          continue;
        }

        if (LOGGER.isInfoEnabled()) {
          LOGGER.info("(Thread-{}) Buy operation is successful!%n",
              Thread.currentThread().getId());
        }
      } else {
        throw new NotExistException();
      }
      return;
    }
  }

  /**
   * check amount is not negative.
   * @param amount amount to check.
   * @throws NegativeAmountException when buy negative amount.
   */
  private void checkAmount(final int amount) throws NegativeAmountException {
    if (amount <= 0) {
      throw new NegativeAmountException();
    }
  }

  /**
   * check remaining is not negative.
   * @param remaining remaining to check.
   * @throws NotEnoughException when not enough amount in stock.
   */
  private void checkRemaining(final int remaining) throws NotEnoughException {
    if (remaining < 0) {
      throw new NotEnoughException();
    }
  }
}
