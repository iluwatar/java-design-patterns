package com.iluwatar.flyweight;

public class StrengthPotion implements Potion {

	@Override
	public void drink() {
		System.out.println("You feel strong. (Potion="
				+ System.identityHashCode(this) + ")");
	}
}
