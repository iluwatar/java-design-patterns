package com.iluwatar;

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
		filterManager.setFilter(new NameFilter());
		filterManager.setFilter(new ContactFilter());
		filterManager.setFilter(new AddressFilter());
		filterManager.setFilter(new DepositFilter());
		filterManager.setFilter(new OrderFilter());

		Client client = new Client();
		client.setFilterManager(filterManager);
	}
}
