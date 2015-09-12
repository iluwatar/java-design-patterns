package com.iluwatar.intercepting.filter;

/**
 *
 * This is an app that checks whether the order request is valid through pre-processing done via {@link Filter}.
 * Each field has its own corresponding {@link Filter}
 * @author joshzambales
 *
 */
public class App{
	
	/**
	 * Program entry point
	 * @param args command line args
	 */
	public static void main(String[] args) {
		FilterManager filterManager = new FilterManager(new Target());
		filterManager.addFilter(new NameFilter());
		filterManager.addFilter(new ContactFilter());
		filterManager.addFilter(new AddressFilter());
		filterManager.addFilter(new DepositFilter());
		filterManager.addFilter(new OrderFilter());

		Client client = new Client();
		client.setFilterManager(filterManager);
	}
}
