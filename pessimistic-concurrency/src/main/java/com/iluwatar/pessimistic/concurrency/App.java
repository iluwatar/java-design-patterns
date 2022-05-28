package com.iluwatar.pessimistic.concurrency;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class App {
    public static void main(String[] args) throws RuntimeException {
        Map<String,Object> properties = new HashMap();
        properties.put("javax.persistence.query.timeout", 4000);
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("AdvancedMapping", properties);

        CustomerService customerService = new CustomerService(emf);
        customerService.clearTable();
        Customer obj1 = new Customer("John");
        Customer obj2 = new Customer("Abby");
        Customer obj3 = new Customer("Bob");
        customerService.save(obj1);
        customerService.save(obj2);
        customerService.save(obj3);
        // User 1, request lock on obj1 to edit it.
        // User 2, request lock on obj1 but is unsuccessful.
        // User 2, request lock on obj2 to edit it.
        Thread t1 = new Thread(() -> {
            try {
                Optional<Customer> customer1 = customerService.startEditingCustomerInfo("user1", obj1);
                if (customer1.isPresent()) {
                    try {
                        customerService.updateCustomerInfo("user1", obj1, "Ben");
                    } catch (
                            LockingException e) {
                        LOGGER.info(e.getMessage());
                    }
                }
                Optional<Customer> customer3 = customerService.startEditingCustomerInfo("user1", obj3);
                if (customer3.isPresent()) {
                    try {
                        customerService.updateCustomerInfo("user1", obj3, "Eric");
                    } catch (
                            LockingException e) {
                        LOGGER.info(e.getMessage());
                    }
                }

            } catch (
                    Exception e) {
                throw new RuntimeException(e);
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                Optional<Customer> customer1 = customerService.startEditingCustomerInfo("user1", obj1);
                System.out.println(customer1);
                if (customer1.isPresent()) {

                        customerService.updateCustomerInfo("user1", obj1, "Eren");
                    }
                }catch (
                    LockingException e) {
                System.out.println("???");
                LOGGER.info(e.getMessage());
            }
            try {
                Optional<Customer> customer2 = customerService.startEditingCustomerInfo("user1", obj2);
                if (customer2.isPresent()) {
                    try {
                        customerService.updateCustomerInfo("user1", obj2, "Mikasa");
                    } catch (
                            LockingException e) {
                        LOGGER.info(e.getMessage());
                    }
                }
            }   catch (
                    LockingException e) {
                System.out.println("???");
                LOGGER.info(e.getMessage());
            }

        });
        try {
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        System.out.println("User 1, Obj1: " + lockManager.requestLock("user1", obj1));
//        System.out.println("User 2, Obj1: " + lockManager.requestLock("user2", obj1));
//        System.out.println("User 2, Obj2: " + lockManager.requestLock("user2", obj2));
//        System.out.println("User 1, Obj3: " + lockManager.requestLock("user1", obj3));
//        System.out.println("Release Obj1 " + lockManager.releaseLock(obj1));
//        System.out.println("User 2, Obj1: " + lockManager.requestLock("user2", obj1));


    }

}
