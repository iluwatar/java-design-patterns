package com.iluwatar.optimisticconcurrency;

import java.util.Optional;

import javax.persistence.EntityManagerFactory;

/**
 * Service layer for Product class.
 */
public class ProductService {
    /**
     * The ProductDao for the ProductService to use.
     */
    private final ProductDao productDao;

    /**
     * Some placeholders for string literals.
     */
    static final String NOT_ENOUGH =
            "There are not enough products in stock!";
    /***/
    static final String NEGATIVE_AMOUNT =
            "Amount to buy should be more than zero!";
    /***/
    static final String NOT_EXIST =
            "Product doesn't exist!";

    /**
     * Construct an instance of ProductService.
     * @param emf the entity manager factory for the ProductDao to use.
     */
    public ProductService(final EntityManagerFactory emf) {
        this.productDao = new ProductDao(emf);
    }

    /**
     * Buy product with the id.
     * @param productId id of the product to buy.
     * @param amount amount of the product to buy.
     * @param delay time in millisecond for the thread
     *              to sleep after fetching from database.<br>
     *              This allows result to be reproducible.
     * @param useLock whether to enable locking.<br>
     *                This is used to show difference.
     */
    public void buy(final long productId, final int amount,
                    final int delay, final boolean useLock) {
        if (amount <= 0) {
            System.out.println(NEGATIVE_AMOUNT);
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
                    e.printStackTrace();
                }

                // make changes
                int remaining = oldProduct.getAmountInStock() - amount;
                if (remaining < 0) {
                    System.out.println(NOT_ENOUGH);
                    return;
                }
                newProduct.setAmountInStock(remaining);

                try {
                    productDao.update(newProduct, oldId,
                            oldVersion, useLock);
                } catch (OptimisticLockException e) {
                    System.out.println(e.getMessage());
                    System.out.printf(
                            "(Thread-%d) Buy operation is not successful!\n",
                            Thread.currentThread().getId());
                    continue;
                }

                System.out.printf("(Thread-%d) Buy operation is successful!\n",
                        Thread.currentThread().getId());
            } else {
                System.out.println(NOT_EXIST);
            }

            break;
        }

    }
}
