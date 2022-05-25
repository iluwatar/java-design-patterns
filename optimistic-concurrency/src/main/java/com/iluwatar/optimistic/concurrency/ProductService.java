package com.iluwatar.optimistic.concurrency;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;

import org.hibernate.StaleObjectStateException;

public class ProductService {
    ProductDAO productDAO;

    public ProductService(EntityManager em) {
        this.productDAO = new ProductDAO(em);
    }

    public void buy(long productId, int amount, boolean delay) {
        Optional found = productDAO.get(productId);
        if (found.isPresent()) {
            Product product = (Product) found.get();
            if (delay) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int remaining = product.getAmountInStock() - amount;
            String[] params = new String[]{
                    product.getName(),
                    product.getDescription(),
                    String.valueOf(product.getPrice()),
                    String.valueOf(remaining)
            };
            try {
                productDAO.update(product, params);
            } catch (RuntimeException e) {
                if (e.getCause() instanceof OptimisticLockException) {
                    System.out.println("Buy operation is not successful!");
                }
            }

            System.out.println("Buy operation is successful!");
        }
    }
}
