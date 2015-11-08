package com.iluwatar.flyweight;

/**
 * 
 * HolyWaterPotion
 *
 */
public class HolyWaterPotion implements Potion {

	@Override
	public void drink() {
		System.out.println("You feel blessed. (Potion="
				+ System.identityHashCode(this) + ")");
	}

}
