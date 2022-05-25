package com.iluwatar.optimisticconcurrency;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


import org.junit.*;

import java.util.ArrayList;
import java.util.Optional;


/**
 * Unit tests for Optimistic Concurrency pattern.
 */
public class OptimisticConcurrencyTest {
    static EntityManagerFactory emf;
    static ProductDao productDao;
    static ProductService productService;
    static ArrayList<Long> productIdList;

    @BeforeClass
    public static void init() {
        emf = Persistence.createEntityManagerFactory("AdvancedMapping");
        productDao = new ProductDao(emf);
        productService = new ProductService(emf);
        productIdList = new ArrayList<>();
        //clear table
        productDao.deleteAll();
        // insert some products in database
        Product apple = new Product("apple", "The apple is very delicious!", 1.5, 10);
        Product banana = new Product("banana", "The banana is fresh!", 2.1, 20);
        Product laptop = new Product("laptop", "This laptop is very powerful.", 899.99, 5);
        Product phone = new Product("phone", "This phone is waterproof.", 729.99, 8);
        Product mouse = new Product("mouse", "This is a bluetooth mouse.", 35.99, 20);
        Product keyboard = new Product("keyboard", "This is a mechanical keyboard", 77.99, 30);
        productDao.save(apple);
        productDao.save(banana);
        productDao.save(laptop);
        productDao.save(phone);
        productDao.save(mouse);
        productDao.save(keyboard);
        // save product ids
        productIdList.add(apple.getId());
        productIdList.add(banana.getId());
        productIdList.add(laptop.getId());
        productIdList.add(phone.getId());
        productIdList.add(mouse.getId());
        productIdList.add(keyboard.getId());
    }

    @AfterClass
    public static void close() {
        emf.close();
    }

    /**
     * Test by having two threads buy the same product at the same time.
     */
    @Test
    public void testBuySameProductConcurrently() {
        /**
         * Thread 1 sleeps for 1s after fetching data.
         * This ensures that Thread 1 will update before Thread 2.
         * Thread 1 should sleep for some time,
         * to ensure that Thread 2 doesn't fetch the data updated by Thread 1
         * This succeeds.
         */
        Thread t1 = new Thread(() -> {
            productService.buy(productIdList.get(0), 2, 1000, true);
        });

        /**
         * Thread 2 sleeps for 2s after fetching data.
         * This allows thread 1 to update first.
         * Thus, thread 2 will have outdated version.
         * This fails.
         */
        Thread t2 = new Thread(() -> {
            productService.buy(productIdList.get(0), 2, 2000, true);
        });

        /**
         * Since only one buy operation succeeds,
         * there will be 8 apples left.
         */

        t1.start();
        t2.start();

        try {
            // wait for both threads to end
            t1.join();
            t2.join();
            // assert
            Optional result = productDao.get(productIdList.get(0));
            if (result.isPresent()) {
                Product product = (Product) result.get();
                Assert.assertEquals(6, product.getAmountInStock());
            } else {
                Assert.fail("Product doesn't exist!");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test by having two threads buying different products at the same time
     */
    @Test
    public void testBuyDiffProductsConcurrently() {
        Thread t1 = new Thread(() -> {
            productService.buy(productIdList.get(1), 2, 0, true);
        });

        Thread t2 = new Thread(() -> {
            productService.buy(productIdList.get(2), 3, 0, true);
        });
        /**
         * Since Thread 1 and 2 are buying different products,
         * there shouldn't be conflict
         * both buy operations should succeed.
         */

        t1.start();
        t2.start();

        try {
            // wait for both threads to end
            t1.join();
            t2.join();
            // assert
            Optional result1 = productDao.get(productIdList.get(1));
            Optional result2 = productDao.get(productIdList.get(2));
            if (result1.isPresent() && result2.isPresent()) {
                Product banana = (Product) result1.get();
                Product laptop = (Product) result2.get();
                Assert.assertEquals(18, banana.getAmountInStock());
                Assert.assertEquals(2, laptop.getAmountInStock());
            } else {
                Assert.fail("Product doesn't exist!");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test by buying a product two times sequentially
     */
    @Test
    public void testBuySameProductSequentially() {
        /**
         * Since buy operations take place in sequence,
         * there shouldn't be any conflict
         */
        productService.buy(productIdList.get(3), 7, 0, true);
        productService.buy(productIdList.get(4), 10, 0, true);
        // assert
        Optional result1 = productDao.get(productIdList.get(3));
        Optional result2 = productDao.get(productIdList.get(4));
        if (result1.isPresent() && result2.isPresent()) {
            Product phone = (Product) result1.get();
            Product mouse = (Product) result2.get();
            Assert.assertEquals(1, phone.getAmountInStock());
            Assert.assertEquals(10, mouse.getAmountInStock());
        } else {
            Assert.fail("Product doesn't exist!");
        }
    }


}
