package com.iluwatar.flyweight;

/**
 * 
 * PoisonPotion
 *
 */
public class PoisonPotion implements Potion {

	@Override
	public void drink() {
		System.out.println("Urgh! This is poisonous. (Potion="
				+ System.identityHashCode(this) + ")");
	}

}
