package com.iluwatar.builder;

/**
 * 
 * Weapon enumeration
 *
 */
public enum Weapon {

	DAGGER, SWORD, AXE, WARHAMMER, BOW;

	@Override
	public String toString() {
		return name().toLowerCase();
	}

}
