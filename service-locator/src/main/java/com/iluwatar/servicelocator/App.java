package com.iluwatar.servicelocator;

/**
 * Service locator pattern, used to lookup JNDI-services
 * and cache them for subsequent requests.
 *
 * @author saifasif
 * 
 */
public class App {
	
	/**
	 * Program entry point
	 * @param args command line args
	 */
    public static void main(String[] args) {
        Service service = ServiceLocator.getService("jndi/serviceA");
        service.execute();
        service = ServiceLocator.getService("jndi/serviceB");
        service.execute();
        service = ServiceLocator.getService("jndi/serviceA");
        service.execute();
        service = ServiceLocator.getService("jndi/serviceA");
        service.execute();
    }
}
