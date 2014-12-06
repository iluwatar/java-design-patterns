package com.iluwatar;
/**
 * Service locator pattern, used to lookup jndi services 
 * and cache them for subsequent requests. 
 * @author saifasif
 *
 */
public class App {
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
