package src.main.java.com.iluwatar.lockmanager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Manager Class, the entire implementation of the Online Lock-Manager design pattern.
 */
public class Manager {

    private HashMap lockSet;
    private static HashMap managerSet = new HashMap();
    private static Set<String> invalidNames = new HashSet<>();

    /**
     * Constructor for the manager class, creates a new set of locks and adds invalid names for managers
     * to the invalidNames hashmap.
     */
    public Manager() {
        lockSet = new HashMap();
        invalidNames.add(null);
        invalidNames.add("");
    }

    /**
     * Returns the manager object provided that it is managerSet.
     * If the manager is not in the managerSet, the method adds it to the hashmap and then returns.
     * Throws InvalidNameException if the name input is in the invalidNames Set.
     *
     * @param name the name of the manager you wish to retrieve or add.
     * @return Manager object requested.
     * @throws InvalidNameException if the name provided is invalid.
     */
    public static synchronized Manager getManager(String name) throws InvalidNameException {
        // If the name is invalid then throw an InvalidNameException
        if (invalidNames.contains(name)) {
            throw new InvalidNameException("Invalid Name of Manager.");
        }
        // If the set of manager's does not contain the key, create a new manager instance and add it to the set.
        if (!managerSet.containsKey(name)) {
            Manager manager = new Manager();
            managerSet.put(name,manager);
        }

        Manager returnManager = (Manager) managerSet.get(name);
        return returnManager;
    }

    /**
     * Request a lock from the manager for the lockable object.
     * Provided that the lockable object exists, check if the associated key equals the name provided.
     *
     * @param name TreeNode that acts as root of the subtree we're interested in.
     * @param lockable object we are attempting to retrieve a lock for.
     * @throws InvalidNameException if the name provided is invalid.
     */
    public boolean canLock(String name, Object lockable) throws InvalidNameException {
        // If the name is invalid then throw an InvalidNameException
        if (invalidNames.contains(name)) {
            throw new InvalidNameException("Invalid Name of Manager.");
        }
        // As this is an online implementation, we must synchronise lockSet to prevent multiple threads
        // accessing the set at the same time, causing data overwrites.
        synchronized(lockSet) {
            // If the lockSet doesn't contain the lockable object, then add it with the provided name.
            if (!lockSet.containsKey(lockable)) {
                lockSet.put(lockable,name);
                return true;
            }
            // Otherwise, we must check that the name provided matches the key name.
            String lockName = (String) lockSet.get(lockable);
            return name.equals(lockName);
        }
    }

    /**
     * Remove a lockable object from the set of lockable objects.
     *
     * @param lockable lockable object that we wish to remove.
     */
    public Object removeLock(Object lockable) {
        Object removed = lockSet.remove(lockable);
        return removed;
    }
}

//class Manager {
// - lockSet : HashMap
// - managerSet : HashMap {static}
// - invalidNames : Set<String> {static}
// + Manager()
// + getManager(name : String)
// + canLock(name : String, lockable : object)
// + removeLock(lockable : object)
//}