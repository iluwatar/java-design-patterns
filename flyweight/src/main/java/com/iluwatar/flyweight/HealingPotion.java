package com.iluwatar.flyweight;

public class HealingPotion implements Potion {

	@Override
	public void drink() {
		System.out.println("You feel healed. (Potion="
				+ System.identityHashCode(this) + ")");
	}

}
