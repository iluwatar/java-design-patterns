package com.iluwatar;

public class DragonSlayer {

	private DragonSlayingStrategy strategy;

	public DragonSlayer(DragonSlayingStrategy strategy) {
		this.strategy = strategy;
	}

	public void changeStrategy(DragonSlayingStrategy strategy) {
		this.strategy = strategy;
	}
	
	public void goToBattle() {
		strategy.execute();
	}
}
