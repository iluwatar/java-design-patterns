package com.iluwatar.implicitlockpattern;


/**
 * BusinessTransaction class handles the logic of processing customer transactions.
 * It works with the Framework to acquire and release locks for resources.
 */
public class BusinessTransaction {

  private final Framework framework;

  // Constructor accepts the Framework to interact with the LockManager
  public BusinessTransaction(Framework framework) {
    this.framework = framework;
  }

  /**
   * Processes a customer transaction by acquiring a lock on the corresponding resource.
   *
   * @param resource the resource to be locked during the transaction
   * @param customerId the ID of the customer being processed
   * @param customerData the data related to the customer being processed
   */
  public void processCustomer(Resource resource, String customerId, String customerData) {
    // Print a message indicating which customer is being processed
    System.out.println("Processing customer " + customerId + " with data: " + customerData);

    // Try to acquire the lock for the resource
    if (framework.tryLockResource(resource)) {
      // Simulate some processing (e.g., sleeping for 500 milliseconds)
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();  // Handle interruption
      }
      // Release the lock after processing is done
      framework.notifyReleaseLock(resource);
    } else {
      System.out.println("Failed to acquire lock for resource: " + resource.getId());
    }
  }
}
