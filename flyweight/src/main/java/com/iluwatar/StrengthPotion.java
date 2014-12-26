package com.iluwatar;

public class StrengthPotion implements Potion {

	@Override
	public void drink() {
		System.out.println("You feel strong. (Potion="
				+ System.identityHashCode(this) + ")");
	}
}
