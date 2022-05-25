package com.iluwatar.optimisticconcurrency;

import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.OptimisticLockException;

/**
 * Service layer for Product class.
 */
public class ProductService {
    /**
     * The ProductDao for the ProductService to use.
     */
    private ProductDao productDao;

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
     *              to sleep after fetching from database.
     */
    public void buy(final long productId, final int amount, final int delay) {
        if (amount <= 0) {
            System.out.println("Amount to buy should be more than zero!");
            return;
        }
        //
        Optional found = productDao.get(productId);
        if (found.isPresent()) {
            Product product = (Product) found.get();

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int remaining = product.getAmountInStock() - amount;
            if (remaining < 0) {
                System.out.println("There are not enough products in stock!");
                return;
            }
            product.setAmountInStock(remaining);

            try {
                productDao.update(product);
            } catch (OptimisticLockException e) {
                System.out.println("Buy operation is not successful!");
                return;
            }

            System.out.println("Buy operation is successful!");
        } else {
            System.out.println("Product doesn't exist!");
        }
    }
}
