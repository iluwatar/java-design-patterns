package com.iluwatar.builder;

/**
 * 
 * Armor enumeration
 *
 */
public enum Armor {

	CLOTHES("clothes"), LEATHER("leather"), CHAIN_MAIL("chain mail"), PLATE_MAIL("plate mail");

    private String title;

    Armor(String title) {
        this.title = title;
    }

    @Override
	public String toString() {
		return title;
	}
}
