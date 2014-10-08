package com.iluwatar;

public class Goblin extends Target {

	public Goblin() {
		this.setSize(Size.NORMAL);
		this.setVisibility(Visibility.VISIBLE);
	}

	@Override
	public String toString() {
		return "Goblin";
	}

}
