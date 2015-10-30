package com.iluwatar.flux.action;

/**
 * 
 * Menu items.
 *
 */
public enum MenuItem {
	
	HOME("Home"), PRODUCTS("Products"), COMPANY("Company");
	
	private String title;

	MenuItem(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return title;
	}
}
