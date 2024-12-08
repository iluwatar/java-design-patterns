package com.iluwatar;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * BusinessTransaction class handles the logic of processing customer transactions.
 * It works with the Framework to acquire and release locks for resources.
 */
public class BusinessTransaction {

  private static final Logger logger = Logger.getLogger(BusinessTransaction.class.getName());
  private final Framework framework;

  /**
   * Constructs a BusinessTransaction instance with the specified Framework.
   * The Framework is used to interact with the LockManager for resource locking.
   *
   * @param framework the Framework instance to manage resource locks
   */
  public BusinessTransaction(Framework framework) {
    this.framework = framework;
  }

  /**
   * Processes a customer transaction by acquiring a lock on the corresponding resource.
   *
   * @param resource the resource to be locked during the transaction
   * @param customerId the ID of the customer being processed
   * @param customerData the data related to the customer being processed
   * @return true if the transaction was processed successfully, false otherwise
   */
  public boolean processCustomer(Resource resource, String customerId, String customerData) {
    // Log a message indicating which customer is being processed
    logger.log(Level.INFO, "Processing customer {0} with data: {1}", new Object[]{customerId, customerData});

    // Try to acquire the lock for the resource
    if (framework.tryLockResource(resource)) {
      // Simulate some processing (e.g., sleeping for 500 milliseconds)
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();  // Handle interruption
        return false;  // Return false if the thread was interrupted
      }

      // Release the lock after processing is done
      framework.notifyReleaseLock(resource);

      // Log success and return true to indicate the transaction was completed
      logger.log(Level.INFO, "Customer {0} processed successfully.", customerId);
      return true;
    } else {
      logger.log(Level.WARNING, "Failed to acquire lock for resource: {0}", resource.getId());
      return false;  // Return false if the lock was not acquired
    }
  }
}
