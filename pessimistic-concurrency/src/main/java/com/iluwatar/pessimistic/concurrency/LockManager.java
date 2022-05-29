package com.iluwatar.pessimistic.concurrency;

import java.beans.Transient;
import java.sql.Time;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class LockManager {
    private final HashMap<Object, String> locks;
    private final static HashMap<String, LockManager> managers = new HashMap<String, LockManager>();
    public static synchronized LockManager getLockManager(String managerName) {
        LockManager manager = (LockManager) managers.get(managerName);
        if (manager == null) {
            manager = new LockManager();
            managers.put(managerName, manager);
        }
        return manager;
    }
    public LockManager() {
        locks = new HashMap<Object, String>();
    }

    /**
     * request the lock to be
     * @param username name of user that requests lock
     * @param lockable  object to be locked
     * @return status of locking
     * @throws LockingException if locking is
     */
    public boolean requestLock(String username, Object lockable) throws LockingException {
        if (username == null) {
            return false;
        }
        // synchronized object only has one thread accessing it at one time
        synchronized(locks) {
            if (!locks.containsKey(lockable)) {
                locks.put(lockable, username);
                if (lockable instanceof Customer) {
                    Customer customer = (Customer) lockable;
                    // object is locked with the username
                    customer.lock(username);
                }
                return true;
            }
            return (username.equals(locks.get(lockable)));
        }
    }

    /**
     *
     * @param lockable
     * @return
     * @throws LockingException
     */
    public Object releaseLock(Object lockable) throws LockingException {
        Customer customer = (Customer) lockable;
        customer.unlock(customer.getLockingUser());
        return locks.remove(lockable);
    }

}

