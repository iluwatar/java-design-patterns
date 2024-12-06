package com.iluwatar;

/**
 * App class serves as the entry point for the simulation.
 * It creates resources and processes transactions for different customers.
 */
public class App {

  /**
   * The main method serves as the entry point of the application.
   * It demonstrates the use of the LockManager and Framework classes to manage and process
   * resources through a simulated BusinessTransaction.
   *
   * @param args command-line arguments (not used in this application)
   */
  public static void main(String[] args) {
    // Create some sample resources (could be customers, products, etc.)
    Resource resource1 = new Resource("Resource1");
    Resource resource2 = new Resource("Resource2");
    Resource resource3 = new Resource("Resource3");

    // Create a LockManager instance to manage the locks
    LockManager lockManager = new LockManager();

    // Create a Framework instance with the LockManager
    Framework framework = new Framework(lockManager);

    // Create a BusinessTransaction instance to simulate processing
    BusinessTransaction transaction = new BusinessTransaction(framework);

    // Process customers with their associated resources
    transaction.processCustomer(resource1, "456", "Customer data for 456");
    transaction.processCustomer(resource2, "123", "Customer data for 123");  // This will fail to lock
    transaction.processCustomer(resource3, "789", "Customer data for 789");

    // Attempting to process another customer with the same resource should fail
    transaction.processCustomer(resource1, "111", "Customer data for 111");  // This will fail to lock again
  }
}
