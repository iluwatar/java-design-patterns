package com.iluwatar.pessimistic.concurrency;

import java.sql.Time;
import java.util.HashMap;

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
    public boolean requestLock(String username, Object lockable) throws LockingException {
        if (username == null) {
            return false;
        }
        // synchronized object only has one thread accessing it at one time
        synchronized(locks) {
            if (!locks.containsKey(lockable)) {
                locks.put(lockable, username);
                Customer customer = (Customer) lockable;
                customer.lock(username);
                return true;
            }
            return (username.equals(locks.get(lockable)));
        }
    }
    public Object releaseLock(Object lockable) throws LockingException {
        Customer customer = (Customer) lockable;
        customer.unlock(customer.getLockingUser());
        return locks.remove(lockable);
    }

}

