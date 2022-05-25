package com.iluwatar.optimisticconcurrency;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Optimistic Concurrency pattern.
 * Increment version during every update.
 * Attempts to update object using old version fail
 */
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
        final String appleDes = "The apple is very delicious!";
        final double banaPrice = 2.1;
        final int banaNum = 20;
        final String banaDes = "The banana is fresh!";
        Product apple = new Product("apple", appleDes, applePrice, appleNum);
        Product banana = new Product("banana", banaDes, banaPrice, banaNum);
        // insert into database
        productDao.save(apple);
        productDao.save(banana);
        // save ids
        final long id1 = apple.getId();
        final long id2 = banana.getId();
        // create threads
        final int thread1Delay = 1000;
        final int thread2Delay = 2000;
        final int thread1BuyAmount = 2;
        final int thread2BuyAmount = 4;
        Thread t1 = new Thread(() -> {
            productService.buy(id1, thread1BuyAmount, thread1Delay);
        });
        Thread t2 = new Thread(() -> {
            productService.buy(id2, thread2BuyAmount, thread2Delay);
        });
        // start threads
        t1.start();
        t2.start();
        // wait for threads to finish
        try {
            t1.join();
            t2.join();
            emf.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * private constructor so class App can't be instantiated.
     */
    private App() { }
}
