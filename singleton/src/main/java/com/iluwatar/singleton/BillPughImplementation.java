package com.iluwatar.singleton;

/**
 * <p>Bill Pugh Singleton Implementation.</p>
 *
 * <p>This implementation of the singleton design pattern takes advantage of the
 * Java memory model's guarantees about class initialization. Each class is
 * initialized only once, when it is first used. If the class hasn't been used
 * yet, it won't be loaded into memory, and no memory will be allocated for
 * a static instance. This makes the singleton instance lazy-loaded and thread-safe.</p>
 *
 * @author owen.leung2@gmail.com
 */
public class BillPughImplementation {

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private BillPughImplementation() {
        // private constructor
    }

    /**
     * The InstanceHolder is a static inner class and it holds the Singleton instance.
     * It is not loaded into memory until the getInstance() method is called.
     */
    private static class InstanceHolder {
        private static BillPughImplementation instance = new BillPughImplementation();
    }

    /**
     * Public accessor for the singleton instance.
     *
     * When this method is called, the InstanceHolder is loaded into memory
     * and creates the Singleton instance. This method provides a global access point
     * for the singleton instance.
     *
     * @return an instance of the class.
     */
    // global access point
    public static BillPughImplementation getInstance() {
        return InstanceHolder.instance;
    }
}
