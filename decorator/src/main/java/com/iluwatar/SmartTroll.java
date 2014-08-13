package com.iluwatar;

public class SmartTroll extends Troll {

	private Troll decorated;

	public SmartTroll(Troll decorated) {
		this.decorated = decorated;
	}
	
	@Override
	public void attack() {
		System.out.println("The troll throws a rock at you!");
		decorated.attack();
	}

	@Override
	public void fleeBattle() {
		System.out.println("The troll calls for help!");
		decorated.fleeBattle();
	}
	
}
