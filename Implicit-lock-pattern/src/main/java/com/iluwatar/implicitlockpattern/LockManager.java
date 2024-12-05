package com.iluwatar.implicitlockpattern;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockManager {

  // A thread-safe map to track the locks for each resource by their ID
  private final ConcurrentHashMap<String, Lock> lockMap = new ConcurrentHashMap<>();

  /**
   * Acquires a lock for the given resource if it's not already locked.
   *
   * @param resource the resource to acquire the lock for
   * @return true if the lock was successfully acquired, false if the resource is already locked
   */
  public boolean acquireLock(Resource resource) {
    // Acquiring lock for a resource
    Lock lock = lockMap.computeIfAbsent(resource.getId(), k -> new ReentrantLock());

    if (lock.tryLock()) {
      try {
        System.out.println("Lock acquired for resource: " + resource.getId());
        // Perform actions on the resource
        return true;  // Lock successfully acquired, returning true
      } finally {
        lock.unlock();  // Ensure the lock is released
        System.out.println("Lock released for resource: " + resource.getId());
      }
    } else {
      System.out.println("Cannot acquire lock for resource: " + resource.getId() + " - Already locked.");
      return false;  // Lock could not be acquired, returning false
    }
  }

  /**
   * Releases the lock for a given resource if it is locked.
   *
   * @param resource the resource to release the lock for
   * @return true if the lock was successfully released, false otherwise
   */
  public boolean releaseLock(Resource resource) {
    Lock lock = lockMap.get(resource.getId());
    if (lock != null && lock instanceof ReentrantLock && ((ReentrantLock) lock).isHeldByCurrentThread()) {
      ((ReentrantLock) lock).unlock();
      lockMap.remove(resource.getId());  // Remove lock after releasing
      System.out.println("Lock released for resource: " + resource.getId());
      return true;
    }
    return false;
  }
}
