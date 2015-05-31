package com.iluwatar.interceptingfilter;

/**
 *
 * This is an app that checks whether the order request is valid through pre-processing done via Filters
 * Each field has its own corresponding Filter
 * @author joshzambales
 *
 */
public class App{
	
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
