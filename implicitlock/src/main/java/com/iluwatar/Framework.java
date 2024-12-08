package com.iluwatar;

/**
 * Framework class interacts with the LockManager to acquire and release locks.
 * It simplifies the usage of locking mechanisms for the BusinessTransaction.
 */
public class Framework {

  private final LockManager lockManager;

  /**
   * Constructs a Framework instance with the specified LockManager.
   * The LockManager is responsible for managing locks on resources.
   *
   * @param lockManager the LockManager instance used to handle resource locks
   */
  public Framework(LockManager lockManager) {
    this.lockManager = lockManager;
  }

  /**
   * Requests to lock a resource via the LockManager.
   *
   * @param resource the resource to be locked
   * @return true if the lock was acquired, false otherwise
   */
  public boolean tryLockResource(Resource resource) {
    return lockManager.acquireLock(resource);
  }

  /**
   * Notifies the LockManager to release the lock on the resource.
   *
   * @param resource the resource to release the lock for
   * @return true if the lock was released, false otherwise
   */
  public boolean notifyReleaseLock(Resource resource) {
    return lockManager.releaseLock(resource);
  }

  /**
   * Simulates loading customer data.
   *
   * @param resource the resource to load data for
   * @return customer data associated with the resource
   */
  public String loadCustomerData(Resource resource) {
    return "Customer data for " + resource.getId(); // Example of returning customer data
  }
}
