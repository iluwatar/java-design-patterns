package com.iluwatar.decorator;

/**
 * 
 * Troll implements {@link Hostile} interface directly.
 *
 */
public class Troll implements Hostile {

	public void attack() {
		System.out.println("The troll swings at you with a club!");
	}

	@Override
	public int getAttackPower() {
		return 10;
	}

	public void fleeBattle() {
		System.out.println("The troll shrieks in horror and runs away!");
	}

}
