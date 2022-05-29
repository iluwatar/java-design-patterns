package com.iluwatar.pessimistic.concurrency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class PessimisticConcurrencyTest {
  static EntityManagerFactory emf;
  static CustomerService customerService;
  static CustomerDao customerDao;
  static ArrayList<Customer> customers;

  @BeforeClass
  public static void init() {
    Map<String, Object> properties = new HashMap();
    properties.put("javax.persistence.query.timeout", 4000);
    emf = Persistence.createEntityManagerFactory("AdvancedMapping", properties);
    customerService = new CustomerService(emf);
    customerDao = new CustomerDao(emf);
    customerService.clearTable();
    customers = new ArrayList<>(3);

    Customer obj1 = new Customer("John");
    Customer obj2 = new Customer("Abby");
    Customer obj3 = new Customer("Bob");
    customers.add(obj1);
    customers.add(obj2);
    customers.add(obj3);
    customerService.save(obj1);
    customerService.save(obj2);
    customerService.save(obj3);
  }

  @AfterClass
  public static void close() {
    emf.close();
  }

  @Test
  public void testUpdateWhileLockedByAnotherUser() {
    Thread t1 =
        new Thread(
            () -> {
              try {
                Optional<Customer> customer1 =
                    customerService.startEditingCustomerInfo("user1", customers.get(0));
                Thread.sleep(100);
                if (customer1.isPresent()) {
                  try {
                    customerService.updateCustomerInfo("user1", customers.get(0), "Ben");
                  } catch (LockingException e) {
                    LOGGER.info(e.getMessage());
                  }
                }

              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            });
    Thread t2 =
        new Thread(
            () -> {
              try {
                Thread.sleep(100);
                Optional<Customer> customer1 =
                    customerService.startEditingCustomerInfo("user2", customers.get(0));
                if (customer1.isPresent()) {
                  customerService.updateCustomerInfo("user1", customers.get(0), "Eren");
                }
              } catch (LockingException e) {
                LOGGER.info(e.getMessage());

              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
            });
    t1.start();
    t2.start();
    try {
      t1.join();
      t2.join();
      long id = customers.get(0).getId();
      Optional<Customer> result = customerDao.get(id);
      result.ifPresent(customer -> assertEquals("Ben", customer.getName()));

    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  @Test
  public void testSequentialLockRequests() {
    Thread t1 =
        new Thread(
            () -> {
              try {
                Optional<Customer> customer1 =
                    customerService.startEditingCustomerInfo("user1", customers.get(0));
                if (customer1.isPresent()) {
                  try {
                    customerService.updateCustomerInfo("user1", customers.get(0), "Ben");
                  } catch (LockingException e) {
                    LOGGER.info(e.getMessage());
                  }
                }

              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            });
    Thread t2 =
        new Thread(
            () -> {
              try {
                Thread.sleep(100);
                Optional<Customer> customer1 =
                    customerService.startEditingCustomerInfo("user2", customers.get(0));
                if (customer1.isPresent()) {
                  try {
                    customerService.updateCustomerInfo("user2", customers.get(0), "Eren");
                  } catch (LockingException e) {
                    LOGGER.info(e.getMessage());
                  }
                }

              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            });
    t1.start();
    t2.start();
    try {
      t1.join();
      t2.join();
      long id = customers.get(0).getId();
      Optional<Customer> result = customerDao.get(id);
      result.ifPresent(customer -> assertEquals("Eren", customer.getName()));

    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }

  @Test
  public void testThreadSpinLocking() {
    Thread t1 =
        new Thread(
            () -> {
              try {
                Optional<Customer> customer1 =
                    customerService.startEditingCustomerInfo("user1", customers.get(0));
                if (customer1.isPresent()) {
                  try {
                    customerService.updateCustomerInfo("user1", customers.get(0), "Ben");
                  } catch (LockingException e) {
                    LOGGER.info(e.getMessage());
                  }
                }

              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            });
    Thread t2 =
        new Thread(
            () -> {
              try {
                while (true) {
                  Thread.sleep(10);
                  Optional<Customer> customer1 =
                      customerService.startEditingCustomerInfo("user2", customers.get(0));
                  if (customer1.isPresent()) {
                    try {
                      customerService.updateCustomerInfo("user2", customers.get(0), "Eren");
                      break;
                    } catch (LockingException e) {
                      LOGGER.info(e.getMessage());
                    }
                  }
                }

              } catch (Exception e) {
                throw new RuntimeException(e);
              }
            });
    t1.start();
    t2.start();
    try {
      t1.join();
      t2.join();
      long id = customers.get(0).getId();
      Optional<Customer> result = customerDao.get(id);
      result.ifPresent(customer -> assertEquals("Eren", customer.getName()));

    } catch (InterruptedException e) {
      LOGGER.error(e.getMessage());
      Thread.currentThread().interrupt();
    }
  }
}
