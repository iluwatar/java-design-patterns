package com.iluwatar.builder;

/**
 * 
 * HairColor enumeration
 *
 */
public enum HairColor {

	WHITE, BLOND, RED, BROWN, BLACK;

	@Override
	public String toString() {
		return name().toLowerCase();
	}

}
