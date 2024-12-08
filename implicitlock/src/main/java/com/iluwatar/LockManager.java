package com.iluwatar;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The LockManager class provides functionality to manage locks for resources in a thread-safe manner.
 * It ensures that multiple threads can safely acquire and release locks on shared resources.
 *
 * <p>This class uses a {@link ConcurrentHashMap} to store locks for resources identified by their unique IDs.
 * It supports acquiring locks if they are not already held and releasing locks held by the current thread.
 */
public class LockManager {

  private static final Logger LOGGER = Logger.getLogger(LockManager.class.getName());

  // A thread-safe map to track the locks for each resource by their ID
  private final ConcurrentHashMap<String, Lock> lockMap = new ConcurrentHashMap<>();

  /**
   * Acquires a lock for the given resource if it's not already locked.
   *
   * @param resource the resource to acquire the lock for
   * @return true if the lock was successfully acquired, false if the resource is already locked
   */
  public boolean acquireLock(Resource resource) {
    Lock lock = lockMap.computeIfAbsent(resource.getId(), k -> new ReentrantLock());

    if (lock.tryLock()) {
      try {
        LOGGER.log(Level.INFO, "Lock acquired for resource: {0}", resource.getId());
        return true;
      } finally {
        lock.unlock();
        LOGGER.log(Level.INFO, "Lock released for resource: {0}", resource.getId());
      }
    } else {
      LOGGER.log(Level.WARNING, "Cannot acquire lock for resource: {0} - Already locked.", resource.getId());
      return false;
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
    if (lock instanceof ReentrantLock reentrantLock && reentrantLock.isHeldByCurrentThread()) {
      reentrantLock.unlock();
      lockMap.remove(resource.getId());
      LOGGER.log(Level.INFO, "Lock released for resource: {0}", resource.getId());
      return true;
    }
    LOGGER.log(Level.WARNING, "Failed to release lock for resource: {0} - Lock not held by current thread.", resource.getId());
    return false;
  }
}