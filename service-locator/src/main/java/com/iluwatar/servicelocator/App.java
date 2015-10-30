package com.iluwatar.servicelocator;

/**
 * 
 * The Service Locator pattern is a design pattern used in software development 
 * to encapsulate the processes involved in obtaining a service with a strong 
 * abstraction layer. This pattern uses a central registry known as the "service 
 * locator", which on request returns the information necessary to perform a certain task.
 * <p>
 * In this example we use the Service locator pattern to lookup JNDI-services
 * and cache them for subsequent requests.
 * <p>
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
