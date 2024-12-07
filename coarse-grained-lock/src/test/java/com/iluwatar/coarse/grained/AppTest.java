package com.iluwatar.coarse.grained;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link App} class.
 */
class AppTest {

  @Test
  void testLockingMechanism() {
    // Arrange
    Lock lock = new Lock();
    Customer customer = new Customer(55, "john");
    Address address1 = new Address(customer.getCustomerId(), 1, "chicago");
    Address address2 = new Address(customer.getCustomerId(), 2, "houston");

    // Act
    lock.synchronizedMethod(() -> {
      customer.setName("smith");
      address1.setCity("dallas");
      address2.setCity("phoenix");
    });

    // Assert
    assertEquals("smith", customer.getName(), "Customer name should be updated to 'smith'.");
    assertEquals("dallas", address1.getCity(), "Address 1 city should be updated to 'dallas'.");
    assertEquals("phoenix", address2.getCity(), "Address 2 city should be updated to 'phoenix'.");
  }

  @Test
  void testConcurrentModification() throws InterruptedException {
    // Arrange
    Lock lock = new Lock();
    Customer customer = new Customer(55, "john");
    Address address1 = new Address(customer.getCustomerId(), 1, "chicago");
    Address address2 = new Address(customer.getCustomerId(), 2, "houston");

    // Simulate two threads attempting to modify data
    Thread thread1 = new Thread(() -> lock.synchronizedMethod(() -> {
      customer.setName("alice");
      address1.setCity("seattle");
    }));

    Thread thread2 = new Thread(() -> lock.synchronizedMethod(() -> {
      customer.setName("bob");
      address2.setCity("miami");
    }));

    // Act
    thread1.start();
    thread2.start();
    thread1.join();
    thread2.join();

    /*
     Assert
     Only one thread's changes should be applied because of locking.
     The output can be either customer names depending on the thread that will finish last.
     both addresses will update as each thread access only one resource and not both at the same time.
    */
    assertTrue(
        customer.getName().equals("alice") || customer.getName().equals("bob"),
        "Customer name should reflect changes from one thread only."
    );
    assertEquals("seattle", address1.getCity(),
        "Address cities should reflect changes from one thread only.");

    assertEquals("miami", address2.getCity(),
        "Address cities should reflect changes from one thread only.");
  }
}
