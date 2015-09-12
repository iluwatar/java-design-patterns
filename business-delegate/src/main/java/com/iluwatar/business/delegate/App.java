package com.iluwatar.business.delegate;

/**
 * 
 * The Business Delegate pattern adds an abstraction layer between the presentation and business tiers.
 * By using the pattern we gain loose coupling between the tiers. The Business Delegate encapsulates
 * knowledge about how to locate, connect to, and interact with the business objects that make up
 * the application.
 * <p>
 * Some of the services the Business Delegate uses are instantiated directly, and some can be retrieved
 * through service lookups. The Business Delegate itself may contain business logic too potentially tying
 * together multiple service calls, exception handling, retrying etc.
 * <p>
 * In this example the client ({@link Client}) utilizes a business delegate ({@link BusinessDelegate}) to execute a task.
 * The Business Delegate then selects the appropriate service and makes the service call.
 *
 */
public class App {
	
	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {
		
		BusinessDelegate businessDelegate = new BusinessDelegate();
		businessDelegate.setServiceType(ServiceType.EJB);

		Client client = new Client(businessDelegate);
		client.doTask();

		businessDelegate.setServiceType(ServiceType.JMS);
		client.doTask();
	}
}
