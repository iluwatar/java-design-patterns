package com.iluwatar.pessimistic.concurrency;

import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {
  /**
   * A demo of the functionalities made.
   * @param args args to pass if needed
   */
  public static void main(String[] args) {
    EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("AdvancedMapping");

    CustomerService customerService = new CustomerService(emf);
    CustomerDao customerDao = new CustomerDao(emf);
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
    Thread t1 =
        new Thread(
            () -> {
              try {
                Optional<Customer> customer1 =
                    customerService.startEditingCustomerInfo("user1", obj1);
                Thread.sleep(100);
                if (customer1.isPresent()) {
                    customerService.updateCustomerInfo("user1", obj1, "Ben");
                }
                Optional<Customer> customer3 =
                    customerService.startEditingCustomerInfo("user1", obj3);
                if (customer3.isPresent()) {
                    customerService.updateCustomerInfo("user1", obj3, "Eric");
                }
              } catch (InterruptedException e) {
                LOGGER.info(e.getMessage());
                Thread.currentThread().interrupt();

              } catch (LockingException e) {
                LOGGER.info(e.getMessage());
              }
            });
    Thread t2 =
        new Thread(
            () -> {
              try {
                Optional<Customer> customer1 =
                    customerService.startEditingCustomerInfo("user1", obj1);
                if (customer1.isPresent()) {
                  customerService.updateCustomerInfo("user1", obj1, "Eren");
                }
                Optional<Customer> customer2 =
                    customerService.startEditingCustomerInfo("user1", obj2);
                if (customer2.isPresent()) {
                    customerService.updateCustomerInfo("user1", obj2, "Mikasa");
                }
                Thread.sleep(1000);
                Optional<Customer> customer1_retry =
                    customerService.startEditingCustomerInfo("user1", obj1);
                if (customer1_retry.isPresent()) {
                  customerService.updateCustomerInfo("user1", obj1, "Eren");
                }
              } catch (LockingException e) {
                LOGGER.info(e.getMessage());
              } catch (InterruptedException e) {
                LOGGER.info(e.getMessage());
                Thread.currentThread().interrupt();
              }
            });
    try {
      t1.start();
      t2.start();
      t1.join();
      t2.join();
      long id1 = obj1.getId();
      Optional<Customer> result1 = customerDao.get(id1);
      result1.ifPresent(customer -> LOGGER.info(customer.getName()));
      long id2 = obj1.getId();
      Optional<Customer> result2 = customerDao.get(id2);
      result2.ifPresent(customer -> LOGGER.info(customer.getName()));
      long id3 = obj1.getId();
      Optional<Customer> result3 = customerDao.get(id3);
      result3.ifPresent(customer -> LOGGER.info(customer.getName()));
      emf.close();


    } catch (InterruptedException e) {
      LOGGER.info(e.getMessage());
      Thread.currentThread().interrupt();

    }
  }
}
