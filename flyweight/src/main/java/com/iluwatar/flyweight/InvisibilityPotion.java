package com.iluwatar.flyweight;

/**
 * 
 * InvisibilityPotion
 *
 */
public class InvisibilityPotion implements Potion {

	@Override
	public void drink() {
		System.out.println("You become invisible. (Potion="
				+ System.identityHashCode(this) + ")");
	}

}
