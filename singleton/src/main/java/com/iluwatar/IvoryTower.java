package com.iluwatar;

public class IvoryTower {

	private static IvoryTower instance = new IvoryTower();
	
	private IvoryTower() {
	}

	public static IvoryTower getInstance() {
		return instance;
	}
}
