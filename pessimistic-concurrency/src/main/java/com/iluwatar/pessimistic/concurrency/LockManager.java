package com.iluwatar.pessimistic.concurrency;

import java.util.HashMap;

public class LockManager {
  private final HashMap<Object, String> locks;
  private static final HashMap<String, LockManager> managers = new HashMap<String, LockManager>();

  /**
   * gets a lock manager associated with the given name either inside the hashmap or newly created.
   *
   * @param managerName the name of the object manager
   * @return the lock manager, containing a key to locks
   */
  public static synchronized LockManager getLockManager(String managerName) {
    LockManager manager = managers.get(managerName);
    if (manager == null) {
      manager = new LockManager();
      managers.put(managerName, manager);
    }
    return manager;
  }

  /** Initialized the lock manager with a hashmap. */
  public LockManager() {
    locks = new HashMap<Object, String>();
  }

  /**
   * request the lock to be locked by given user.
   *
   * @param username name of user that requests lock
   * @param lockable object to be locked
   * @return status of locking
   * @throws LockingException if resource is already locked
   */
  public boolean requestLock(String username, Object lockable) throws LockingException {
    if (username == null) {
      return false;
    }
    // synchronized object only has one thread accessing it at one time
    synchronized (locks) {
      if (!locks.containsKey(lockable)) {
        locks.put(lockable, username);
        if (lockable instanceof Customer) {
          Customer customer = (Customer) lockable;
          // object is locked with the username
          customer.lock(username);
        }
        return true;
      }
      return username.equals(locks.get(lockable));
    }
  }

  /** releases the lock on the resource.
   * @param lockable the resource that is locked
   * @return the username stored in the map
   * @throws LockingException if locking user is not consistent
   */
  public Object releaseLock(Object lockable) throws LockingException {
    Customer customer = (Customer) lockable;
    customer.unlock(customer.getLockingUser());
    return locks.remove(lockable);
  }
}
