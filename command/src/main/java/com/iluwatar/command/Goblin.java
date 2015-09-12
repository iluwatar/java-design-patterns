package com.iluwatar.command;

/**
 * 
 * Goblin is the target of the spells
 *
 */
public class Goblin extends Target {

	public Goblin() {
		setSize(Size.NORMAL);
		setVisibility(Visibility.VISIBLE);
	}

	@Override
	public String toString() {
		return "Goblin";
	}

}
