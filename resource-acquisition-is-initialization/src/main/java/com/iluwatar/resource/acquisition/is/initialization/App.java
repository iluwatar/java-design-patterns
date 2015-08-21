package com.iluwatar.resource.acquisition.is.initialization;

/**
 * 
 * Resource Acquisition Is Initialization pattern was developed
 * for exception safe resource management by C++ creator Bjarne
 * Stroustrup.
 * <p>
 * In RAII resource is tied to object lifetime: resource allocation
 * is done during object creation while resource deallocation is
 * done during object destruction.
 * <p>
 * In Java RAII is achieved with try-with-resources statement and
 * interfaces {@link Closeable} and {@link AutoCloseable}. The try-with-resources 
 * statement ensures that each resource is closed at the end of the 
 * statement. Any object that implements {@link java.lang.AutoCloseable}, which 
 * includes all objects which implement {@link java.io.Closeable}, can be used 
 * as a resource.
 *
 * In this example, {@link SlidingDoor} implements {@link AutoCloseable} and 
 * {@link TreasureChest} implements {@link Closeable}. Running the example, we can
 * observe that both resources are automatically closed.
 * <p>
 * http://docs.oracle.com/javase/7/docs/technotes/guides/language/try-with-resources.html
 *
 */
public class App {
	
	/**
	 * Program entry point
	 * @param args command line args
	 * @throws Exception
	 */
	public static void main( String[] args ) throws Exception {
		
    	try (SlidingDoor slidingDoor = new SlidingDoor()) {
    		System.out.println("Walking in.");
    	}
    	
    	try (TreasureChest treasureChest = new TreasureChest()) {
    		System.out.println("Looting contents.");
    	}
    }
}
