package com.iluwatar.optimisticconcurrency;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lombok.extern.slf4j.Slf4j;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Unit tests for Optimistic Concurrency pattern.
 */
@Slf4j
public class OptimisticConcurrencyTest {
    /**
     * emf
     */
    private static EntityManagerFactory emf;
    /**
     * productDao
     */
    private static ProductDao productDao;
    /**
     * productService
     */
    private static ProductService productService;
    /**
     * productIdList
     */
    private static List<Long> productIdList;

    /**
     * init
     */
    @BeforeClass
    public static void init() {
        emf = Persistence.createEntityManagerFactory("AdvancedMapping");
        productDao = new ProductDao(emf);
        productService = new ProductService(emf);
        productIdList = new ArrayList<>();
        //clear table
        productDao.deleteAll();
        // insert some products in database
        final Product apple = new Product("apple", "The apple is very delicious!", 1.5, 10);
        final Product banana = new Product("banana", "The banana is fresh!", 2.1, 20);
        final Product laptop = new Product("laptop", "This laptop is very powerful.", 899.99, 5);
        final Product phone = new Product("phone", "This phone is waterproof.", 729.99, 8);
        final Product mouse = new Product("mouse", "This is a bluetooth mouse.", 35.99, 20);
        final Product keyboard = new Product("keyboard", "This is a mechanical keyboard.", 77.99, 30);
        final Product coffee = new Product("coffee", "This coffee is local sourced.", 22.99, 12);
        final Product speaker = new Product("speaker", "loud and clear!", 57.22, 11);
        productDao.save(apple);
        productDao.save(banana);
        productDao.save(laptop);
        productDao.save(phone);
        productDao.save(mouse);
        productDao.save(keyboard);
        productDao.save(coffee);
        productDao.save(speaker);
        // save product ids
        productIdList.add(apple.getId());
        productIdList.add(banana.getId());
        productIdList.add(laptop.getId());
        productIdList.add(phone.getId());
        productIdList.add(mouse.getId());
        productIdList.add(keyboard.getId());
        productIdList.add(coffee.getId());
        productIdList.add(speaker.getId());

    }

    /**
     * close
     */
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
        final Thread thread1 = new Thread(() -> {
            try {
                productService.buy(productIdList.get(0), 2, 1000, true);
            } catch (NotEnoughException | NegativeAmountException | NotExistException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e.getMessage());
                }
            }
        });

        /**
         * Thread 2 sleeps for 2s after fetching data.
         * This allows thread 1 to update first.
         * Thus, thread 2 will have outdated version.
         * This first fails and then retries.
         */
        final Thread thread2 = new Thread(() -> {
            try {
                productService.buy(productIdList.get(0), 2, 2000, true);
            } catch (NotEnoughException | NegativeAmountException | NotExistException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e.getMessage());
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            // wait for both threads to end
            thread1.join();
            thread2.join();
            // assert
            final Optional result = productDao.get(productIdList.get(0));
            if (result.isPresent()) {
                final Product product = (Product) result.get();
                Assert.assertEquals("testBuySameProductConcurrently passed! (1/1)",
                    6, product.getAmountInStock());
            } else {
                Assert.fail("Product doesn't exist!");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Test by having two threads buying different products at the same time
     */
    @Test
    public void testBuyDiffProductsConcurrently() {
        final Thread thread1 = new Thread(() -> {
            try {
                productService.buy(productIdList.get(1), 2, 0, true);
            } catch (NotEnoughException | NegativeAmountException | NotExistException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e.getMessage());
                }
            }
        });

        final Thread thread2 = new Thread(() -> {
            try {
                productService.buy(productIdList.get(2), 3, 0, true);
            } catch (NotEnoughException | NegativeAmountException | NotExistException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e.getMessage());
                }
            }
        });
        /**
         * Since Thread 1 and 2 are buying different products,
         * there shouldn't be conflict
         * both buy operations should succeed.
         */

        thread1.start();
        thread2.start();

        try {
            // wait for both threads to end
            thread1.join();
            thread2.join();
            // assert
            final Optional result1 = productDao.get(productIdList.get(1));
            final Optional result2 = productDao.get(productIdList.get(2));
            if (result1.isPresent() && result2.isPresent()) {
                final Product banana = (Product) result1.get();
                final Product laptop = (Product) result2.get();
                Assert.assertEquals("testBuyDiffProductsConcurrently passed (1/2)",
                    18, banana.getAmountInStock());
                Assert.assertEquals("testBuyDiffProductsConcurrently passed! (2/2)",
                    2, laptop.getAmountInStock());
            } else {
                Assert.fail("Product doesn't exist!");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Test by buying a product two times sequentially
     */
    @Test
    public void testBuySameProductSequentially() throws NegativeAmountException, NotEnoughException, NotExistException {
        /**
         * Since buy operations take place in sequence,
         * there shouldn't be any conflict
         */
        productService.buy(productIdList.get(3), 7, 0, true);
        productService.buy(productIdList.get(4), 10, 0, true);
        // assert
        final Optional result1 = productDao.get(productIdList.get(3));
        final Optional result2 = productDao.get(productIdList.get(4));
        if (result1.isPresent() && result2.isPresent()) {
            final Product phone = (Product) result1.get();
            final Product mouse = (Product) result2.get();
            Assert.assertEquals("testBuySameProductSequentially passed! (1/2)",
                1, phone.getAmountInStock());
            Assert.assertEquals("testBuySameProductSequentially passed! (2/2)",
                10, mouse.getAmountInStock());
        } else {
            Assert.fail("Product doesn't exist!");
        }
    }

    /**
     * Test by passing negative amount to buy
     */
    @Test
    public void testNegativeAmountException() throws NotEnoughException, NotExistException {
        try {
            productService.buy(productIdList.get(5), -2, 0, true);
        } catch (NegativeAmountException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage());
            }
            Assert.assertTrue("testNegativeAmountException passed!",
                true);
        }
    }

    /**
     * Test by buying more than there is in stock
     */
    @Test
    public void testNotEnoughException() throws NegativeAmountException, NotExistException {
        try {
            productService.buy(productIdList.get(6), 13, 0, true);
        } catch (NotEnoughException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage());
            }
            Assert.assertTrue("testNotEnoughException passed!",
                true);
        }
    }

    /**
     * Test by removing product
     */
    @Test
    public void testNotExistException() throws NotEnoughException, NegativeAmountException {
        final Optional result = productDao.get(productIdList.get(7));
        if (result.isPresent()) {
            final Product speaker = (Product) result.get();
            productDao.delete(speaker);

            try {
                productService.buy(speaker.getId(), 3, 2000, true);
                Assert.fail("Should throw NotExistException!");
            } catch (NotExistException e) {
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error(e.getMessage());
                }
                Assert.assertTrue("testNotExistException passed!",
                        true);
            }
        } else {
            Assert.fail("Product doesn't exist!");
        }
    }
}
