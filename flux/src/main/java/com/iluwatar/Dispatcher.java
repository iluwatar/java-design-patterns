package com.iluwatar;

import java.util.LinkedList;
import java.util.List;

public class Dispatcher {
	
	private static Dispatcher instance = new Dispatcher();
	
	private List<Store> stores = new LinkedList<>();
	
	private Dispatcher() {	
	}

	public Dispatcher getInstance() {
		return instance;
	}
	
	public void registerStore(Store store) {
		stores.add(store);
	}
	
	public void menuItemSelected(MenuItem menuItem) {
		
	}
}
