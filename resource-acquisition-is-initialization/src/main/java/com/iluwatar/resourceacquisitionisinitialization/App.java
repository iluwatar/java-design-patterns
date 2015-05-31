package com.iluwatar.resourceacquisitionisinitialization;

/**
 * 
 * Resource Acquisition Is Initialization pattern was developed
 * for exception safe resource management by C++ creator Bjarne
 * Stroustrup.
 * 
 * In RAII resource is tied to object lifetime: resource allocation
 * is done during object creation while resource deallocation is
 * done during object destruction.
 * 
 * In Java RAII is achieved with try-with-resources statement and
 * interfaces Closeable and AutoCloseable. The try-with-resources 
 * statement ensures that each resource is closed at the end of the 
 * statement. Any object that implements java.lang.AutoCloseable, which 
 * includes all objects which implement java.io.Closeable, can be used 
 * as a resource.
 *
 * In this example, SlidingDoor implements AutoCloseable and 
 * TreasureChest implements Closeable. Running the example, we can
 * observe that both resources are automatically closed.
 * 
 * http://docs.oracle.com/javase/7/docs/technotes/guides/language/try-with-resources.html
 *
 */
public class App {
	
	public static void main( String[] args ) throws Exception {
		
    	try (SlidingDoor slidingDoor = new SlidingDoor()) {
    		System.out.println("Walking in.");
    	}
    	
    	try (TreasureChest treasureChest = new TreasureChest()) {
    		System.out.println("Looting contents.");
    	}
    }
}
